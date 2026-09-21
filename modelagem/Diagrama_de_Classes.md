# Sistema de Gerenciamento de Rede Hoteleira — Diagrama de Classes (v2)

## Diagrama

```mermaid
classDiagram
    class Calculavel {
        <<interface>>
        +calcularValor() double
    }

    class Hotel {
        -Long id
        -String nome
        -String endereco
        -String telefone
        +listarQuartos() List~Quarto~
        +listarFuncionarios() List~Funcionario~
    }

    class SituacaoQuarto {
        <<enumeration>>
        DISPONIVEL
        RESERVADO
        OCUPADO
        MANUTENCAO
    }

    class Quarto {
        <<abstract>>
        -Long id
        -int numero
        -int capacidade
        -double valorDiariaBase
        -SituacaoQuarto situacao
        +calcularValor() double
    }
    class QuartoStandard {
        +calcularValor() double
    }
    class QuartoLuxo {
        -double taxaAdicional
        +calcularValor() double
    }
    class QuartoSuite {
        -boolean hidromassagem
        -int numeroComodos
        +calcularValor() double
    }

    class Funcionario {
        <<abstract>>
        -Long id
        -String nome
        -String cpf
        -double salario
        -LocalDate dataContratacao
        -String horarioTrabalho
        -LocalDate feriasInicio
        -LocalDate feriasFim
        +getPermissoes() List~String~
    }
    class Recepcionista {
        -String turno
        +getPermissoes() List~String~
    }
    class Gerente {
        -double metaMensal
        +getPermissoes() List~String~
        +gerarRelatorio() Relatorio
    }
    class Administrador {
        -String nivelAcesso
        +getPermissoes() List~String~
    }

    class Hospede {
        -Long id
        -String nome
        -String cpf
        -LocalDate dataNascimento
        -String telefone
        -String email
        +consultarHistorico() List~Reserva~
    }

    class SituacaoReserva {
        <<enumeration>>
        PENDENTE
        CONFIRMADA
        ATIVA
        CANCELADA
        FINALIZADA
    }

    class Reserva {
        -Long id
        -LocalDate dataEntrada
        -LocalDate dataSaida
        -LocalDateTime dataCheckin
        -LocalDateTime dataCheckout
        -SituacaoReserva situacao
        -double valorTotal
        +calcularValor() double
        +confirmar() void
        +cancelar() void
        +realizarCheckin() void
        +realizarCheckout() void
    }

    class Servico {
        -Long id
        -String nome
        -String descricao
        -double valor
    }

    class ReservaServico {
        -Long id
        -int quantidade
        -double valorCobrado
        -LocalDateTime dataUtilizacao
        +calcularValor() double
    }

    class ReservaService {
        <<service>>
        +verificarDisponibilidade(Quarto, dataEntrada, dataSaida) boolean
        +confirmarReserva(Reserva) void
        +realizarCheckin(Reserva) void
        +realizarCheckout(Reserva) void
    }

    Quarto <|-- QuartoStandard
    Quarto <|-- QuartoLuxo
    Quarto <|-- QuartoSuite
    Funcionario <|-- Recepcionista
    Funcionario <|-- Gerente
    Funcionario <|-- Administrador
    Calculavel <|.. Quarto
    Calculavel <|.. Reserva
    Calculavel <|.. ReservaServico

    Hotel "1" o-- "N" Quarto : possui
    Hotel "1" o-- "N" Funcionario : emprega
    Quarto "1" --> "N" Reserva : é reservado em
    Hospede "1" --> "N" Reserva : realiza
    Funcionario "1" --> "N" Reserva : registra
    Reserva "1" *-- "N" ReservaServico : contém
    Servico "1" --> "N" ReservaServico : é utilizado em
    ReservaService ..> Reserva : orquestra
    ReservaService ..> Quarto : consulta
```

`ReservaService` é uma classe de serviço (camada de negócio, não uma entidade persistida): concentra a verificação de disponibilidade e as transições de estado de `Reserva`/`Quarto`.

---

## Ordem recomendada de implementação

A ordem segue a dependência entre as classes: sempre implemente primeiro o que **não depende de nada**, para nunca travar esperando outra classe ainda não escrita.

| # | Classe(s) | Por quê nessa ordem |
|---|---|---|
| 1 | `SituacaoQuarto`, `SituacaoReserva` (enums) | Não dependem de nada; são usadas por várias classes depois |
| 2 | `Calculavel` (interface) | Não depende de nada; será implementada por três classes depois |
| 3 | `Hotel` | Não depende de nenhuma outra entidade do domínio |
| 4 | `Quarto` (abstract) | Depende só de `SituacaoQuarto` e `Calculavel`, já prontos |
| 5 | `QuartoStandard`, `QuartoLuxo`, `QuartoSuite` | Dependem de `Quarto` já existir |
| 6 | `Funcionario` (abstract) | Não depende de `Quarto`; pode ser feita em paralelo com o passo 4-5 |
| 7 | `Recepcionista`, `Gerente`, `Administrador` | Dependem de `Funcionario` já existir |
| 8 | `Hospede` | Não depende de nada |
| 9 | `Servico` | Não depende de nada |
| 10 | `Reserva` | Depende de `Quarto`, `Hospede`, `Funcionario` e `SituacaoReserva` — só dá para fazer depois de todas elas |
| 11 | `ReservaServico` | Depende de `Reserva` e `Servico` |
| 12 | `ReservaService` | Depende de tudo acima — é a última peça, orquestra as demais |

**Dica prática:** os passos 1–3, e depois 4-5/6-7/8-9 em paralelo, dão pra dividir entre os integrantes do grupo sem gerar conflito de dependência. `Reserva` (passo 10) só deve começar quando todo o resto já estiver pronto e compilando.
