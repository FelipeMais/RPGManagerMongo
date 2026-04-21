package factory;

import connection.ConnectionConfig;
import connection.SqlConnection;
import contracts.DataBaseConnection;
import contracts.ItemAttributeDAO;
import contracts.ItemDAO;
import contracts.LocationDAO;
import contracts.LocationTypeDAO;
import contracts.MagicDAO;
import contracts.MagicAttributeDAO;
import contracts.PlayerDAO;
import contracts.AttributeDAO;
import contracts.RpgClassDAO;
import contracts.SpeciesDAO;
import dao.AttributeSqlDAO;
import dao.ItemAttributeSqlDAO;
import dao.ItemSqlDAO;
import dao.LocationSqlDAO;
import dao.LocationTypeSqlDAO;
import dao.MagicAttributeSqlDAO;
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

    public static ItemDAO getItemDAO() {
        if (sharedConnection == null) init();

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new ItemSqlDAO((Connection) sharedConnection);
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

    public static AttributeDAO getAttributeDAO() {
        if (sharedConnection == null) init();

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new AttributeSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static MagicAttributeDAO getMagicAttributeDAO() {
        if (sharedConnection == null) init();

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new MagicAttributeSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static ItemAttributeDAO getItemAttributeDAO() {
        if (sharedConnection == null) init();

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new ItemAttributeSqlDAO((Connection) sharedConnection);
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

    public static LocationDAO getLocationDAO() {
        if (sharedConnection == null) init();

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new LocationSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static LocationTypeDAO getLocationTypeDAO() {
        if (sharedConnection == null) init();

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new LocationTypeSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static ItemDAO getItemDAO() {
        if (sharedConnection == null) init();;

        if ("SQL".equalsIgnoreCase(dbType)){
            return new ItemSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static ItemAttributeDAO getItemAttributeDAO() {
        if (sharedConnection == null) init();;

        if ("SQL".equalsIgnoreCase(dbType)){
            return new ItemAttributeSqlDAO((Connection) sharedConnection);
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
