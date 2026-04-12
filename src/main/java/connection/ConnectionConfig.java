package connection;

public class ConnectionConfig {
    private String driver = "org.postgresql.Driver";
    private String user = "postgres";
    private String senha = "1234";
    private String url = "jdbc:postgresql://localhost:5432/principal";

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
