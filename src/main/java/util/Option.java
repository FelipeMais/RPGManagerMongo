package util;

import java.util.Objects;
import java.util.function.Supplier;

public class Option implements Comparable<Option> {
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

    @Override
    public int compareTo(Option o) {
        if(o.getOptionNumber() == 0){
            return  -1;
        }
        if(this.getOptionNumber() == 0){
            return  1;
        }
        if(o.getOptionNumber() > this.getOptionNumber()) {
            return -1;
        }
        if(o.getOptionNumber() < this.getOptionNumber()){
            return  1;
        }
        return 0;
    }
}
