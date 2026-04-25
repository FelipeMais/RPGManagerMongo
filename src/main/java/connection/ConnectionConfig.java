package connection;

import util.UI;

import java.util.Scanner;

public class ConnectionConfig {
    private String driver = "org.postgresql.Driver";
    private String user = "postgres";
    private String senha = "1234";
    private String url = "jdbc:postgresql://localhost:5432/principal";

    public ConnectionConfig() {
        Scanner scanner = new Scanner(System.in);
        UI.printSubTitle("CONECTAR AO BANCO DE DADOS");
        System.out.print("Usuário da conexão:");
        String user = scanner.next();
        System.out.print("Senha da conexão:");
        String senha = scanner.next();
        System.out.print("Url da conexão: jdbc:postgresql://");
        String finalUrl = scanner.next();
        this.driver = "org.postgresql.Driver";
        this.user = user;
        this.senha = senha;
        this.url = "jdbc:postgresql://"+finalUrl;
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
}
