package bg.sofia.uni.fmi.mjt.stylechecker.ruleCheck;

public class BracketsCheck extends CodeCheck{
    public BracketsCheck(String errorMessage){
        super(errorMessage);
    }

    @Override
    public boolean checkForError(String line) {
        return line.trim().startsWith("{");
    }
}
