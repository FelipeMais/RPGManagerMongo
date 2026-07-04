package connection.config;

import factory.DatabaseType;
import util.UI;

import java.util.Scanner;

public class ConnectionConfig {
    private String driver;
    private String user;
    private String senha;
    private String url;
    private String databaseName;

    public ConnectionConfig(DatabaseType type) {
        Scanner scanner = new Scanner(System.in);
        UI.printSubTitle("CONECTAR AO BANCO DE DADOS (" + type.name() + ")");

        if (DatabaseType.POSTGRES.equals(type)) {
            System.out.print("Usuário da conexão:");
            this.user = scanner.next();

            System.out.print("Senha da conexão:");
            this.senha = scanner.next();

            System.out.print("Url da conexão (ex: localhost:5432/principal): jdbc:postgresql://");
            String finalUrl = scanner.next();

            this.driver = "org.postgresql.Driver";
            this.url = "jdbc:postgresql://" + finalUrl;

        } else if (DatabaseType.MONGO.equals(type)) {

            System.out.print("Usuário da conexão (digite '-' se não houver): ");
            this.user = scanner.next();

            System.out.print("Senha da conexão (digite '-' se não houver): ");
            this.senha = scanner.next();

            System.out.print("Host e porta (ex: localhost:27017): ");
            String hostPort = scanner.next();

            System.out.print("Nome do Banco de Dados (ex: rpg_database): ");
            this.databaseName = scanner.next();

            this.driver = null;

            if (!"-".equals(this.user) && !"-".equals(this.senha)) {
                this.url = "mongodb://" + this.user + ":" + this.senha + "@" + hostPort;
            } else {
                this.url = "mongodb://" + hostPort;
            }
        }
    }

    public String getDriver() {
        return driver;
    }

    public String getUser() {
        return user;
    }

    public String getSenha() {
        return senha;
    }

    public String getUrl() {
        return url;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}