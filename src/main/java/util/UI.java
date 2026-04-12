package util;

public class UI {

    private static final int width = 50;
    private static final String separator = "=";

    public static void printLine(){
        System.out.println(separator.repeat(Math.max(0, width)));
    }

    public static void printTitle(String title){
        printLine();
        System.out.println(insertTextInLine(title, separator, width));
        printLine();
    }

    public static void printSubTitle(String subTitle){
        System.out.println(insertTextInLine(subTitle, separator, width));
    }

    public static void printOption(UserOption userOption) {
        printOption(userOption.getOptionNumber(), userOption.getOptionDescription());
    }

    public static void printOption(Integer optionNumber, String optionName) {
        StringBuilder option = new StringBuilder();
        option.append("[").append(optionNumber).append("]").append(":");
        option.append(optionName.toUpperCase());
        System.out.println(option);
    }

    private static String insertTextInLine(String text, String lineCharacter, int lineSize) {
        StringBuilder resultString = new StringBuilder();
        int lineSizeWithoutText = lineSize - text.length();

        String leftRightText = lineCharacter.repeat(Math.max(0, lineSizeWithoutText / 2));
        resultString.append(leftRightText);
        resultString.append(text);
        resultString.append(leftRightText);

        return resultString.toString();

    }
}
