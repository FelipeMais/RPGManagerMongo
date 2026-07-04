package contracts;

import connection.config.ConnectionConfig;

public interface DataBaseConnection <T>{

    public T connect(ConnectionConfig config);
    public void closeConnection();
}
