package util;

public enum Colors {
    RESET("\u001B[0m"),
    BOLD("\u001B[1m"),
    GOLD("\u001B[33m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    GRAY("\u001B[90m"),
    BLUE("\u001B[34m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m");

    private final String code;

    Colors(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    public static String strip(String text) {
        return text.replaceAll("\u001B\\[[;\\d]*m", "");
    }
}
