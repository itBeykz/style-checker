package bg.sofia.uni.fmi.mjt.stylechecker.ruleCheck;

public class LineLenghtCheck extends CodeCheck {
    private int lengthLimit;

    public LineLenghtCheck(String errorMessage, int lengthLimit) {
        super(String.format(errorMessage,lengthLimit));
        this.lengthLimit=lengthLimit;

    }

    @Override
    public boolean checkForError(String line) {
        line = line.trim();
        if(line.startsWith("import")) {
            return false;
        }
        return (line.trim().length()>lengthLimit);
    }
}
