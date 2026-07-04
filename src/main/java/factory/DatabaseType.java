package factory;

import com.mongodb.lang.NonNull;
import connection.MongoConnection;
import connection.PostgresConnection;
import contracts.DataBaseConnection;

public enum DatabaseType {
    POSTGRES,
    MONGO;

    @NonNull
    public static DataBaseConnection<?> getDataBaseConnection(DatabaseType dbType) {
        if(dbType.equals(POSTGRES)) {
            return new PostgresConnection();
        } else if (dbType.equals(MONGO)) {
            return new MongoConnection();
        }
        throw new RuntimeException("Tipo de banco de dados não configurado!");

    }
}
