package factory;

import connection.ConnectionConfig;
import connection.SqlConnection;
import contracts.DataBaseConnection;
import contracts.MagicDAO;
import contracts.PlayerDAO;
import contracts.RpgClassDAO;
import contracts.SpeciesDAO;
import dao.MagicSqlDAO;
import dao.PlayerSqlDAO;
import dao.RpgClassSqlDAO;
import dao.SpeciesSqlDAO;

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
                // Logica para NoSQL
            }
        }
    }

    public static MagicDAO getMagicDAO() {
        if (sharedConnection == null) init();

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new MagicSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static RpgClassDAO getRpgClassDAO() {
        if (sharedConnection == null) init();

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new RpgClassSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static PlayerDAO getPlayerDAO() {
        if (sharedConnection == null) init();

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new PlayerSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static SpeciesDAO getSpeciesDAO() {
        if (sharedConnection == null) init();

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new SpeciesSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static void close() {
        if (dbManager != null) {
            dbManager.closeConnection();
            dbManager = null;
            sharedConnection = null;
            System.out.println("Fabrica de DAOs encerrada.");
        }
    }
}
