package dataaccess;

import chess.ChessGame;

import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static final String DATABASE_NAME;
    private static final String USER;
    private static final String PASSWORD;
    private static final String CONNECTION_URL;


    private static final String USER_TABLE = """
            CREATE TABLE IF NOT EXISTS user(
            'username' VARCHAR(50) PRIMARY KEY,
            'password' VARCHAR(50) NOT NULL,
            'email' VARCHAR(50) NOT NULL
            );
            """;

    private static final String AUTH_TABLE = """
            CREATE TABLE IF NOT EXISTS auth(
            'username' VARCHAR(50) PRIMARY KEY,
            'authToken' VARCHAR(50) NOT NULL
            );
            """;

    private static final String GAME_TABLE = """
            CREATE TABLE IF NOT EXISTS game(
            gameID INT NOT NULL AUTOINCREMENT PRIMARY KEY,
            'whiteUsername' VARCHAR(50),
            'blackUsername' VARCHAR(50),
            'gameName' VARCHAR(50) NOT NULL,
            'chessGame' JSON NOT NULL,
            );
            """;

    private static final String[] TABLES = {USER_TABLE, AUTH_TABLE, GAME_TABLE};

    /*
     * Load the database information for the db.properties file.
     */
    static {
        try {
            try (var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
                if (propStream == null) throw new Exception("Unable to load db.properties");
                Properties props = new Properties();
                props.load(propStream);
                DATABASE_NAME = props.getProperty("db.name");
                USER = props.getProperty("db.user");
                PASSWORD = props.getProperty("db.password");

                var host = props.getProperty("db.host");
                var port = Integer.parseInt(props.getProperty("db.port"));
                CONNECTION_URL = String.format("jdbc:mysql://%s:%d", host, port);
            }
        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties. " + ex.getMessage());
        }
    }

    /**
     * Creates the database if it does not already exist.
     */
    static void createDatabase() throws DataAccessException {
        try {
            var statement = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
            for (var table : TABLES) {
                try (var preparedStatement = conn.prepareStatement(table)){
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    public static void executeUpdate(String statement, Object... params) throws Exception {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) {
                        preparedStatement.setString(i + 1, p);
                    }
                    else if (param instanceof Integer p) {
                        preparedStatement.setInt(i + 1, p);
                    }
                    else if (param instanceof ChessGame p) {
                        preparedStatement.setString(i + 1, p.toString());
                    }
                    else if (param == null){
                        preparedStatement.setNull(i + 1, Types.NULL);
                    }
                }
                preparedStatement.executeUpdate();
                var rs = preparedStatement.getGeneratedKeys();
                rs.next();
                System.out.println(rs.getInt(1));
            }
        } catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Create a connection to the database and sets the catalog based upon the
     * properties specified in db.properties. Connections to the database should
     * be short-lived, and you must close the connection when you are done with it.
     * The easiest way to do that is with a try-with-resource block.
     * <br/>
     * <code>
     * try (var conn = DbInfo.getConnection(databaseName)) {
     * // execute SQL statements.
     * }
     * </code>
     */
    static Connection getConnection() throws DataAccessException {
        try {
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            conn.setCatalog(DATABASE_NAME);
            return conn;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
