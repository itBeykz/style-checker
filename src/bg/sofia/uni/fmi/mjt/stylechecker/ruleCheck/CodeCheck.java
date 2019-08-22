package bg.sofia.uni.fmi.mjt.stylechecker.ruleCheck;

public abstract class CodeCheck {
    private String errorMessage;

    public CodeCheck(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public abstract boolean checkForError(String line);
}
