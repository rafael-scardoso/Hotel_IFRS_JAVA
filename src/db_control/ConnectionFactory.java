package db_control;

import static constants.FilePaths.DB_DIR;
import static constants.FilePaths.DB_FILE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionFactory {

    private static final Logger log =
            LoggerFactory.getLogger(ConnectionFactory.class);

    private static Connection conn = null;

    private static final String URL =
            "jdbc:sqlite:" + DB_FILE;

    public static Connection getConnection() {

        if (conn == null) {
            ensureExist(DB_DIR);

            try {
                conn = DriverManager.getConnection(URL);

                try (Statement st = conn.createStatement()) {
                    st.execute("PRAGMA foreign_keys = ON");
                }

                log.info("Banco de dados conectado: {}", URL);

            } catch (SQLException e) {
                log.error(
                    "Falha ao conectar ao banco de dados: {}",
                    URL,
                    e
                );

                throw new DbException(
                    "Falha ao conectar ao banco de dados: " + URL,
                    e
                );
            }
        }

        return conn;
    }

    private static void ensureExist(Path p) {

        if (Files.notExists(p)) {
            try {
                Files.createDirectories(p);

                log.info(
                    "Diretório criado com sucesso: {}",
                    p
                );

            } catch (IOException e) {

                log.error(
                    "Falha ao criar o diretório: {}",
                    p,
                    e
                );

                throw new RuntimeException(
                    "Falha ao criar o diretório: " + p,
                    e
                );
            }
        }
    }

    public static void closeConnection() {

        if (conn != null) {
            try {
                conn.close();

                log.info(
                    "Conexão com banco de dados fechada com sucesso: {}",
                    URL
                );

            } catch (SQLException e) {

                log.error(
                    "Erro ao fechar conexão com banco de dados: {}",
                    URL,
                    e
                );

                throw new DbException(
                    "Falha ao fechar o banco de dados: " + URL,
                    e
                );

            } finally {
                conn = null;
            }
        }
    }

    public static void closeStatement(Statement st) {

        if (st != null) {
            try {
                st.close();

                log.info(
                    "Statement encerrado com sucesso: {}",
                    URL
                );

            } catch (SQLException e) {

                log.error(
                    "Falha ao encerrar Statement: {}",
                    URL,
                    e
                );

                throw new DbException(
                    "Falha ao fechar Statement",
                    e
                );
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();

                log.info(
                    "ResultSet encerrado com sucesso: {}",
                    URL
                );

            } catch (SQLException e) {

                log.error(
                    "Falha ao fechar ResultSet: {}",
                    URL,
                    e
                );

                throw new DbException(
                    "Falha ao fechar ResultSet",
                    e
                );
            }
        }
    }
}