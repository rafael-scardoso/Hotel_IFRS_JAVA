-- =========================================================
-- Sistema de Gerenciamento de Rede Hoteleira — Script DDL (SQLite)
-- Adaptado da versão v2 (PostgreSQL)
-- =========================================================

-- IMPORTANTE: a cada conexão aberta no Java, execute antes de qualquer outra coisa:
--   PRAGMA foreign_keys = ON;
-- Sem isso, as FKs e o ON DELETE CASCADE abaixo são ignorados silenciosamente.

CREATE TABLE hotel (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    nome            TEXT NOT NULL,
    endereco        TEXT NOT NULL,
    telefone        TEXT
);

-- Tabela-base de Quarto (atributos comuns a todas as subclasses)
CREATE TABLE quarto (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    hotel_id            INTEGER NOT NULL REFERENCES hotel(id),
    numero              INTEGER NOT NULL,
    capacidade          INTEGER NOT NULL,
    valor_diaria_base   NUMERIC NOT NULL,
    tipo                TEXT NOT NULL
                        CHECK (tipo IN ('STANDARD','LUXO','SUITE')), -- discriminador
    situacao            TEXT NOT NULL DEFAULT 'DISPONIVEL'
                        CHECK (situacao IN ('DISPONIVEL','RESERVADO','OCUPADO','MANUTENCAO')),
    UNIQUE (hotel_id, numero)
);

-- Subclasses de Quarto: PK = FK para quarto.id (herança por tabela separada)
CREATE TABLE quarto_standard (
    quarto_id           INTEGER PRIMARY KEY REFERENCES quarto(id) ON DELETE CASCADE
);

CREATE TABLE quarto_luxo (
    quarto_id           INTEGER PRIMARY KEY REFERENCES quarto(id) ON DELETE CASCADE,
    taxa_adicional       NUMERIC NOT NULL DEFAULT 0,
    amenidades           TEXT
);

CREATE TABLE quarto_suite (
    quarto_id           INTEGER PRIMARY KEY REFERENCES quarto(id) ON DELETE CASCADE,
    hidromassagem        INTEGER NOT NULL DEFAULT 0 CHECK (hidromassagem IN (0,1)), -- boolean: 0/1
    numero_comodos       INTEGER NOT NULL DEFAULT 1
);

-- Tabela-base de Funcionario (atributos comuns a todas as subclasses)
CREATE TABLE funcionario (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    hotel_id            INTEGER NOT NULL REFERENCES hotel(id),
    nome                TEXT NOT NULL,
    cpf                 TEXT NOT NULL UNIQUE,
    cargo               TEXT NOT NULL
                        CHECK (cargo IN ('RECEPCIONISTA','GERENTE','ADMINISTRADOR')), -- discriminador
    salario             NUMERIC NOT NULL,
    data_contratacao    TEXT NOT NULL,   -- ISO-8601: 'YYYY-MM-DD'
    horario_trabalho    TEXT,
    ferias_inicio       TEXT,            -- ISO-8601: 'YYYY-MM-DD'
    ferias_fim          TEXT,
    CHECK (ferias_fim IS NULL OR ferias_fim > ferias_inicio)
);

-- Subclasses de Funcionario: PK = FK para funcionario.id
CREATE TABLE recepcionista (
    funcionario_id       INTEGER PRIMARY KEY REFERENCES funcionario(id) ON DELETE CASCADE,
    turno                TEXT
);

CREATE TABLE gerente (
    funcionario_id       INTEGER PRIMARY KEY REFERENCES funcionario(id) ON DELETE CASCADE,
    meta_mensal          NUMERIC
);

CREATE TABLE administrador (
    funcionario_id       INTEGER PRIMARY KEY REFERENCES funcionario(id) ON DELETE CASCADE,
    nivel_acesso         TEXT NOT NULL DEFAULT 'TOTAL'
);

CREATE TABLE hospede (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    nome                TEXT NOT NULL,
    cpf                 TEXT NOT NULL UNIQUE,
    data_nascimento     TEXT NOT NULL,   -- ISO-8601: 'YYYY-MM-DD'
    telefone            TEXT,
    email               TEXT
);

CREATE TABLE reserva (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    quarto_id           INTEGER NOT NULL REFERENCES quarto(id),
    hospede_id          INTEGER NOT NULL REFERENCES hospede(id),
    funcionario_id      INTEGER NOT NULL REFERENCES funcionario(id),
    data_entrada         TEXT NOT NULL,   -- ISO-8601: 'YYYY-MM-DD'
    data_saida           TEXT NOT NULL,
    data_checkin         TEXT,            -- ISO-8601: 'YYYY-MM-DD HH:MM:SS'
    data_checkout        TEXT,
    situacao             TEXT NOT NULL DEFAULT 'PENDENTE'
                         CHECK (situacao IN ('PENDENTE','CONFIRMADA','ATIVA','CANCELADA','FINALIZADA')),
    valor_total          NUMERIC,
    CHECK (data_saida > data_entrada)
);

CREATE TABLE servico (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    hotel_id            INTEGER REFERENCES hotel(id), -- NULL = serviço padrão da rede
    nome                TEXT NOT NULL,
    descricao           TEXT,
    valor               NUMERIC NOT NULL
);

CREATE TABLE reserva_servico (
    id                  INTEGER PRIMARY KEY AUTOINCREMENT,
    reserva_id          INTEGER NOT NULL REFERENCES reserva(id),
    servico_id          INTEGER NOT NULL REFERENCES servico(id),
    quantidade          INTEGER NOT NULL DEFAULT 1,
    valor_cobrado        NUMERIC NOT NULL,
    data_utilizacao       TEXT NOT NULL DEFAULT (datetime('now'))
);

-- Índices para consultas frequentes
CREATE INDEX idx_reserva_quarto    ON reserva(quarto_id);
CREATE INDEX idx_reserva_hospede   ON reserva(hospede_id);
CREATE INDEX idx_reserva_datas     ON reserva(data_entrada, data_saida);
CREATE INDEX idx_quarto_hotel      ON quarto(hotel_id);
CREATE INDEX idx_funcionario_hotel ON funcionario(hotel_id);

-- =========================================================
-- Verificação de sobreposição de datas (sem OVERLAPS no SQLite)
-- Duas faixas [a,b) e [c,d) se sobrepõem quando: a < d AND c < b
-- =========================================================
-- SELECT 1 FROM reserva
-- WHERE quarto_id = ?
--   AND situacao IN ('CONFIRMADA','PENDENTE','ATIVA')
--   AND data_entrada < ?   -- data_saida da nova reserva
--   AND data_saida   > ?;  -- data_entrada da nova reserva
