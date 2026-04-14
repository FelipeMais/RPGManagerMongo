package service;

import factory.DaoFactory;
import util.enums.MenuUserOption;
import util.UI;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        DaoFactory.init();

        UI.printTitle("INICIO DA EXECUÇÃO");

        boolean validAction = true;
        while (validAction) {
            MainMenuService menu = new MainMenuService();
            Boolean opcaoValida = menu.execute();
            if(!opcaoValida){
                return;
            }
        }

        UI.printTitle("FIM DA EXECUÇÃO");
    }

}