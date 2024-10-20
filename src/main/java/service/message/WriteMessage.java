package service.message;

public class WriteMessage {
    private static final String RESET = "\033[0m";
    private static final String RED = "\033[0;31m";
    private static final String RED_BOLD = "\033[1;31m";
    private static final String YELLOW = "\033[0;33m";
    private static final String YELLOW_BOLD = "\033[1;33m";
    private static final String BLUE = "\033[0;34m";
    private static final String BLUE_BOLD = "\033[1;34m";
    private static final String PURPLE = "\033[0;35m";
    private static final String PURPLE_BOLD = "\033[1;35m";
    private static final String CYAN = "\033[0;36m";
    private static final String CYAN_BOLD = "\033[1;36m";

    public void printMessage(String message, Title title) {
        switch (title) {
            case OUT_PRINT:
                System.out.printf("%s%s%s %s\n", PURPLE, "[OUT_PRINT]", RESET, message);
                break;
            case INFO:
                System.out.printf("%s%s%s %s\n", PURPLE, "[INFO]", RESET, message);
                break;
            case ERROR:
                System.out.printf("%s%s%s %s\n", RED, "[ERROR]", RESET, message);
                break;
        }
    }
}
