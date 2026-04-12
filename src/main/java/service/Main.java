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
            MenuService.printMenu();
            Integer userOption = MenuService.getUserChoice(MenuUserOption.getMaxOption());
            if(userOption == null) {
                validAction = false;
                continue;
            }
            MenuService.processUserOption(userOption);



        }

        UI.printTitle("FIM DA EXECUÇÃO");

    }
}