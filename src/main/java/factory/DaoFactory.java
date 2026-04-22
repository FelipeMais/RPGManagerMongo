package factory;

import connection.ConnectionConfig;
import connection.SqlConnection;
import contracts.*;
import dao.*;

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

    public static CharacterDAO getCharacterDAO() {
        if (sharedConnection == null) init();;

        if ("SQL".equalsIgnoreCase(dbType)){
            return new CharacterSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static CharacterSheetDAO getCharacterSheetDAO() {
        if (sharedConnection == null) init();

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new CharacterSheetSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static SkillDAO getSkillDAO() {
        if (sharedConnection == null) init();

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new SkillSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static CharacterSheetSkillDAO getCharacterSheetSkillDAO() {
        if (sharedConnection == null) init();

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new CharacterSheetSkillSqlDAO((Connection) sharedConnection);
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
