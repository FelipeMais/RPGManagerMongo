package factory;

import com.mongodb.client.MongoDatabase;
import connection.config.ConnectionConfig;
import contracts.*;
import dao.mongo.*;
import dao.postgres.*;

import java.sql.Connection;

public class DaoFactory {

    private static Object sharedConnection;
    private static DataBaseConnection<?> dbManager;
    private final static DatabaseType dbType = DatabaseType.MONGO;

    public static void init() {
        if (sharedConnection == null) {
            dbManager = DatabaseType.getDataBaseConnection(dbType);
            ConnectionConfig config = new ConnectionConfig(dbType);
            sharedConnection = dbManager.connect(config);
        }
    }

    public static MagicDAO getMagicDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new MagicSqlDAO((Connection) sharedConnection);
        }
        return new MagicMongoDAO((MongoDatabase) sharedConnection);
    }

    public static RpgClassDAO getRpgClassDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new RpgClassSqlDAO((Connection) sharedConnection);
        }
        return new RpgClassMongoDAO((MongoDatabase) sharedConnection);
    }

    public static PlayerDAO getPlayerDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new PlayerSqlDAO((Connection) sharedConnection);
        }
        return new PlayerMongoDAO((MongoDatabase) sharedConnection);
    }

    public static AttributeDAO getAttributeDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new AttributeSqlDAO((Connection) sharedConnection);
        }
        return new AttributeMongoDAO((MongoDatabase) sharedConnection);
    }

    public static AbilityDAO getAbilityDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new AbilitySqlDAO((Connection) sharedConnection);
        }
        return new AbilityMongoDAO((MongoDatabase) sharedConnection);
    }

    public static MagicAttributeDAO getMagicAttributeDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new MagicAttributeSqlDAO((Connection) sharedConnection);
        }
        return new MagicAttributeMongoDAO((MongoDatabase) sharedConnection);
    }

    public static ItemAttributeDAO getItemAttributeDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new ItemAttributeSqlDAO((Connection) sharedConnection);
        }
        return new ItemAttributeMongoDAO((MongoDatabase) sharedConnection);
    }

    public static SpeciesDAO getSpeciesDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new SpeciesSqlDAO((Connection) sharedConnection);
        }
        return new SpeciesMongoDAO((MongoDatabase) sharedConnection);
    }

    public static LocationDAO getLocationDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new LocationSqlDAO((Connection) sharedConnection);
        }
        return new LocationMongoDAO((MongoDatabase) sharedConnection);
    }

    public static LocationTypeDAO getLocationTypeDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new LocationTypeSqlDAO((Connection) sharedConnection);
        }
        return new LocationTypeMongoDAO((MongoDatabase) sharedConnection);
    }

    public static ItemDAO getItemDAO() {
        if (sharedConnection == null) init();;

        if (DatabaseType.POSTGRES.equals(dbType)){
            return new ItemSqlDAO((Connection) sharedConnection);
        }
        return new ItemMongoDAO((MongoDatabase) sharedConnection);
    }

    public static InventoryDAO getInventoryDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new InventorySqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static CharacterDAO getCharacterDAO() {
        if (sharedConnection == null) init();;

        if (DatabaseType.POSTGRES.equals(dbType)){
            return new CharacterSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static CharacterSheetDAO getCharacterSheetDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new CharacterSheetSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static CombatActionTypeDAO getCombatActionTypeDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new CombatActionTypeSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static CombatDAO getCombatDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new CombatSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static CombatActionDAO getCombatActionDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new CombatActionSqlDAO((Connection) sharedConnection);
        }
        return null;
    }

    public static CombatantDAO getCombatantDAO() {
        if (sharedConnection == null) init();

        if (DatabaseType.POSTGRES.equals(dbType)) {
            return new CombatantSqlDAO((Connection) sharedConnection);
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
