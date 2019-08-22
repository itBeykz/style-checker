package bg.sofia.uni.fmi.mjt.stylechecker.ruleCheck;

public class OneStatementCheck extends CodeCheck{
    public OneStatementCheck(String errorMessage){
        super(errorMessage);
    }

    @Override
    public boolean checkForError(String line) {
        int counter = 0;
        for(int i =0;i<line.length();i++)
        {
            if(line.charAt(i)==';'){
                counter++;
            }
        }
        if(counter>1)
            return true;
        else
            return false;
    }
}
