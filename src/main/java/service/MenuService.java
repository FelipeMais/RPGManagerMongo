package service;

import util.Option;
import util.UI;

import java.sql.SQLException;
import java.util.*;

public class MenuService {
    public String menuTitle;
    public Set<Option> menuOptions;

    public MenuService() {
        this.menuTitle = "";
        this.menuOptions = new TreeSet<>();
        menuOptions.add(new Option(0, "VOLTAR", () -> false));
    }

    public boolean execute() throws SQLException {
        printMenu();
        Integer userOption = getUserOption();
        if (userOption == null) {
            return false;
        }

        return processUserOption(userOption);
    }

    private void printMenu() {
        UI.printSubTitle(menuTitle);
        for (Option option : menuOptions) {
            UI.printOption(option);
        }
        UI.printLine();
    }

    private Integer getUserOption() {
        int maxChoice = menuOptions.stream()
                .mapToInt(Option::getOptionNumber)
                .max()
                .orElse(0);

        System.out.print("Digite a sua opcao:");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if (choice > maxChoice || choice < 0) {
            return null;
        }
        return choice;
    }

    private Boolean processUserOption(int option) {
        for (Option menuOption : menuOptions) {
            if (menuOption.getOptionNumber().equals(option)) {
                return menuOption.getFunction().get();
            }
        }
        return false;
    }
}
