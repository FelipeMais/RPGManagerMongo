package util.enums;

import util.UserOption;

public enum MenuUserOption implements UserOption {
    GERENCIAR_MAGIAS(1, "GERENCIAR MAGIAS"),
    GERENCIAR_JOGADORES(2, "GERENCIAR JOGADORES");

    private Integer optionNumber;
    private String optionDescription;

    MenuUserOption(Integer optionNumber, String optionDescription) {
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

    public static MenuUserOption getByNumber(Integer optionNumber) {
        for(MenuUserOption option : values()) {
            if(optionNumber.equals(option.getOptionNumber())){
                return option;
            }
        }
        return null;
    }
}
