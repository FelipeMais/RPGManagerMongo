package contracts;

import connection.ConnectionConfig;

public interface DataBaseConnection <T>{

    public T connect(ConnectionConfig config);
    public void closeConnection();
}
