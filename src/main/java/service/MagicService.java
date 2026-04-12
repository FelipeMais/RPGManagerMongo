package service;

import dao.MagicSqlDAO;
import factory.DaoFactory;
import model.Magic;
import util.UI;
import util.UserOption;
import util.enums.CrudUserOptions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MagicService {


    public void manage() throws SQLException {
        printMenu();
        Integer option = MenuService.getUserChoice(CrudUserOptions.getMaxOption());
        processOption(option);
    }

    public void printMenu() {
        UI.printSubTitle("GERENCIAR MAGIAS");
        List<UserOption> optionsList = new ArrayList<>(List.of(CrudUserOptions.values()));
        for(UserOption option : optionsList) {
            UI.printOption(option);
        }
        UI.printLine();
    }

    private void processOption(Integer option) throws SQLException {
        CrudUserOptions userOptions = CrudUserOptions.getByNumber(option);
        switch (userOptions) {
            case INSERT:
                Magic newMagic = instantiateMagic();
                DaoFactory.getMagicDAO().insert(newMagic);
                break;
            case REMOVE:
                break;
            case UPDATE:
                break;
            case LIST_ALL:
                break;
            case FIND_BY_ID:
                break;
            case null:
                break;
        }
    }

    private Magic instantiateMagic() {
        Integer id = 1;
        String name = "";
        String description = "";
        Integer manaCost = 1;
        Integer minLevel = 1;
        String dices = "2d6";

        return new Magic(id, name, description, manaCost, minLevel, dices);

    }
}
