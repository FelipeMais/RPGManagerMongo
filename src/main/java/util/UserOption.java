package util;

public interface UserOption {
    public String getOptionDescription();
    public Integer getOptionNumber();

    public static Integer getMaxOption() {
        return 0;
    }

    public static UserOption getByNumber(Integer optionNumber) {
        return null;
    }
}
