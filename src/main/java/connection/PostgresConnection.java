package connection;

import connection.config.ConnectionConfig;
import contracts.DataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgresConnection implements DataBaseConnection<Connection> {
    private Connection connection;

    public PostgresConnection() {
    }

    public Connection connect(ConnectionConfig config) {
        try {
            Class.forName(config.getDriver());
            this.connection = (Connection) DriverManager.getConnection(config.getUrl(), config.getUser(), config.getSenha());
            return this.connection;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PostgresConnection.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            throw new RuntimeException("Não foi possivel se conectar ao banco de dados");
        }
    }

    public void closeConnection(){
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                System.out.println("Conexão SQL encerrada com segurança.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgresConnection.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            System.exit(1);
        }
    }
}


