package factory;

import connection.ConnectionConfig;
import connection.SqlConnection;
import dao.MagicSqlDAO;
import dao.contracts.MagicDAO;

import java.sql.Connection;

//basicamente essa classe vai centralizar todos os ifs possiveis ne if SQL / NOSQL
public class DaoFactory {
    // Variável estática para manter a conexão viva durante toda a execução
    private static Object sharedConnection;
    private static String dbType = "SQL";

    // Metodo para inicializar a conexão no início do programa (Main)
    public static void init() {
        if (sharedConnection == null) {
            if ("SQL".equalsIgnoreCase(dbType)) {
                ConnectionConfig config = new ConnectionConfig();
                sharedConnection = new SqlConnection().connect(config);
            } else {
                // Lógica para NoSQL
            }
        }
    }

    public static MagicDAO getMagicDAO() {
        if (sharedConnection == null) init(); // Garante que está conectado

        if ("SQL".equalsIgnoreCase(dbType)) {
            return new MagicSqlDAO((Connection) sharedConnection);
        }
        return null;//caso do NOSQL
    }

    // Metodo para fechar no final da execução
    public static void close() {
        if (sharedConnection != null) {
            System.out.println("Conexão global fechada com sucesso.");
        }
    }
}

