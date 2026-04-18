package util;

import java.util.function.Supplier;

public class Option {
    private final Integer optionNumber;
    private final String name;
    private final Supplier<Boolean> function;

    public Option(Integer optionNumber, String name, Supplier<Boolean> function) {
        this.optionNumber = optionNumber;
        this.name = name;
        this.function = function;
    }

    public Integer getOptionNumber() {
        return optionNumber;
    }

    public String getName() {
        return name;
    }

    public Supplier<Boolean> getFunction() {
        return function;
    }
}
