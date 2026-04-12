package util.enums;

import util.UserOption;

public enum CrudUserOptions implements UserOption {
    INSERT(1, "Inserir"),
    REMOVE(2, "Remover"),
    UPDATE(3, "Alterar"),
    LIST_ALL(4, "Listar todos"),
    FIND_BY_ID(5, "Encontrar pelo id");

    private Integer optionNumber;
    private String optionDescription;

    CrudUserOptions(Integer optionNumber, String optionDescription) {
        this.optionNumber = optionNumber;
        this.optionDescription = optionDescription;
    }

    @Override
    public String getOptionDescription() {
        return optionDescription;
    }

    @Override
    public Integer getOptionNumber() {
        return optionNumber;
    }

    public static Integer getMaxOption() {
        return values().length;
    }

    public static CrudUserOptions getByNumber(Integer optionNumber) {
        for(CrudUserOptions option : values()) {
            if(optionNumber.equals(option.getOptionNumber())){
                return option;
            }
        }
        return null;
    }
}
