package connection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import connection.config.ConnectionConfig;
import contracts.DataBaseConnection;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoConnection implements DataBaseConnection<MongoDatabase> {

    private MongoClient mongoClient;

    public MongoConnection() {}

    @Override
    public MongoDatabase connect(ConnectionConfig config) {
        try {
            this.mongoClient = MongoClients.create(config.getUrl());
            return this.mongoClient.getDatabase(config.getDatabaseName());
        } catch (Exception ex) {
            Logger.getLogger(MongoConnection.class.getName()).log(Level.SEVERE, "Erro ao conectar ao MongoDB", ex);
            ex.printStackTrace();
            throw new RuntimeException("Não foi possível se conectar ao MongoDB");
        }
    }

    @Override
    public void closeConnection() {
        try {
            if (this.mongoClient != null) {
                this.mongoClient.close();
                System.out.println("Conexão MongoDB encerrada com segurança.");
            }
        } catch (Exception ex) {
            Logger.getLogger(MongoConnection.class.getName()).log(Level.SEVERE, "Erro ao fechar conexão MongoDB", ex);
            ex.printStackTrace();
        }
    }
}