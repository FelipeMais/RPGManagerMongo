package service;

import factory.DaoFactory;
import util.UI;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        DaoFactory.init();

        UI.printTitle("INICIO DA EXECUÇÃO");

        MainMenuService menu = new MainMenuService();
        while (menu.execute()) { }

        DaoFactory.close();
        UI.printTitle("FIM DA EXECUÇÃO");
    }

}
