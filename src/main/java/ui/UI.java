package ui;

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
