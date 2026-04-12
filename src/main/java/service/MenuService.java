package service;

import util.enums.MenuUserOption;
import util.UI;
import util.UserOption;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuService {

    /// importante ver como vai ficar distribuido as opções menu
    public static void printMenu() {
        UI.printSubTitle("OPÇÕES");
        List<UserOption> optionsList = new ArrayList<>(List.of(MenuUserOption.values()));
        for(UserOption option : optionsList) {
            UI.printOption(option);
        }
        UI.printLine();
    }

    public static Integer getUserChoice(int maxChoice) {
        System.out.println("Digite a sua opção:");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if(choice > maxChoice) {
            return null;
        }
        return choice;
    }
    //basicamente para adicionar uma opção é só adicionar no enum e aqui
    public static void processUserOption(int option) throws SQLException {
        MenuUserOption userOption = MenuUserOption.getByNumber(option);
        switch (userOption) {
            case GERENCIAR_MAGIAS:
                new MagicService().manage();
                break;
            case null:
                break;
            default:
                break;
        }
    }


}
