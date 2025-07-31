package enums;


public enum Role {
    TESTER("Tester:in"),
    TESTMANAGER("Testmanager:in"),
    TESTFALLERSTELLER("Testfallersteller:in"),
    REQUIREMENTS_ENGINEER("Requirements Engineer");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}