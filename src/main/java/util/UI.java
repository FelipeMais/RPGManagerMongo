package util;

import java.util.List;
import java.util.Scanner;

public class UI {

    private static final int width = 100;
    private static final String separator = "=";
    private static final String columnSeparator = " | ";

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

    public static void printOption(Option option) {
        printOption(option.getOptionNumber(), option.getName());
    }

    public static void printOption(Integer optionNumber, String optionName) {
        StringBuilder option = new StringBuilder();
        option.append("[").append(optionNumber).append("]").append(":");
        option.append(optionName.toUpperCase());
        System.out.println(option);
    }

    public static void printTable(String[] headers, int[] widths, List<String[]> rows) {
        validateTable(headers, widths, rows);

        printTableSeparator(widths);
        System.out.println(buildTableRow(headers, widths));
        printTableSeparator(widths);

        for (String[] row : rows) {
            System.out.println(buildTableRow(row, widths));
        }

        printTableSeparator(widths);
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

    private static void validateTable(String[] headers, int[] widths, List<String[]> rows) {
        if (headers.length != widths.length) {
            throw new IllegalArgumentException("Headers e widths devem ter o mesmo tamanho.");
        }

        for (String[] row : rows) {
            if (row.length != headers.length) {
                throw new IllegalArgumentException("Cada linha da tabela deve ter a mesma quantidade de colunas do cabecalho.");
            }
        }
    }

    private static void printTableSeparator(int[] widths) {
        int tableWidth = (widths.length - 1) * columnSeparator.length();
        for (int columnWidth : widths) {
            tableWidth += columnWidth;
        }

        System.out.println("-".repeat(Math.max(0, tableWidth)));
    }

    private static String buildTableRow(String[] values, int[] widths) {
        StringBuilder row = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                row.append(columnSeparator);
            }
            row.append(formatCell(values[i], widths[i]));
        }

        return row.toString();
    }

    private static String formatCell(String value, int width) {
        String normalizedValue = normalizeCell(value);
        if (normalizedValue.length() > width) {
            if (width <= 3) {
                return normalizedValue.substring(0, width);
            }
            return normalizedValue.substring(0, width - 3) + "...";
        }

        return normalizedValue + " ".repeat(width - normalizedValue.length());
    }

    private static String normalizeCell(String value) {
        if (value == null || value.isBlank()) {
            return "-";
        }

        return value
                .replace("\r", " ")
                .replace("\n", " ");
    }

    public static void enterAnythingToContinue() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite qualquer coisa para continuar:");
        sc.nextLine();
    }
}
