package service;

import util.Option;
import util.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuService {
    public String menuTitle;
    public List<Option> menuOptions;

    public MenuService() {
        this.menuTitle = "";
        this.menuOptions = new ArrayList<>();
        menuOptions.add(new Option(0, "VOLTAR", () -> false));
    }

    public boolean execute() throws SQLException {
        printMenu();
        Integer userOption = getUserOption(menuOptions.size());
        if(userOption ==  null) {
            return false;
        }

        processUserOption(userOption);
        return true;
    }

    private void printMenu() {
        UI.printSubTitle(menuTitle);
        for(Option option : menuOptions) {
            UI.printOption(option);
        }
        UI.printLine();
    }

    private Integer getUserOption(int maxChoice) {
        System.out.print("Digite a sua opção:");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if(choice > maxChoice || choice <= 0) {
            return null;
        }
        return choice;
    }


    private void processUserOption(int option) {
        for(Option menuOption : menuOptions) {
            if(menuOption.getOptionNumber().equals(option)) {
                menuOption.getFunction().get();
                break;
            }
        }
    }
}
