package factory;

import connection.ConnectionConfig;
import connection.SqlConnection;
import contracts.DataBaseConnection;
import dao.MagicSqlDAO;
import contracts.MagicDAO;

import java.sql.Connection;

public class DaoFactory {

    private static Object sharedConnection;
    private static DataBaseConnection<?> dbManager;
    private final static String dbType = "SQL";

    public static void init() {
        if (sharedConnection == null) {
            if ("SQL".equalsIgnoreCase(dbType)) {
                ConnectionConfig config = new ConnectionConfig();

                dbManager = new SqlConnection();
                sharedConnection = dbManager.connect(config);

            } else {
                // Lógica para NoSQL
            }
        }
    }

    public static MagicDAO getMagicDAO() {
        if (sharedConnection == null) init();

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new MagicSqlDAO((Connection) sharedConnection);
        }
        return null; // caso do NOSQL
    }

    // Metodo para fechar no final da execução
    public static void close() {
        if (dbManager != null) {
            dbManager.closeConnection();
            dbManager = null;
            sharedConnection = null;
            System.out.println("Fábrica de DAOs encerrada.");
        }
    }
}