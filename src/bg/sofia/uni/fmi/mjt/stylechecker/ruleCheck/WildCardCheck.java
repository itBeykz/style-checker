package bg.sofia.uni.fmi.mjt.stylechecker.ruleCheck;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WildCardCheck extends CodeCheck{
   public WildCardCheck(String errorMessage){
       super(errorMessage);
   }

    @Override
    public boolean checkForError(String line) {
        return line.trim().split(";")[0].endsWith(".*");
    }
}
