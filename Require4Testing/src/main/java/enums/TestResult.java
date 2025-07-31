package enums;


public enum TestResult {
    NOT_TESTED("ungetestet"),
    IN_PROCESS("wird getestet"),
    FAILED("durchgefallen"),
    PASSED("bestanden");

    private final String displayName;

    TestResult(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}