package bg.sofia.uni.fmi.mjt.stylechecker;

import bg.sofia.uni.fmi.mjt.stylechecker.ruleCheck.*;

import java.io.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


public class StyleChecker {
    private static final String WILDCARD_IMPORT_CHECK = "wildcard.import.check.active";
    private static final String STATEMENTS_PER_LINE_CHECK = "statements.per.line.check.active";
    private static final String LENGTH_OF_LINE_CHECK = "length.of.line.check.active";
    private static final String OPENING_BRACKET_CHECK = "opening.bracket.check.active";
    private static final String LINE_LENGTH_LIMIT = "line.length.limit";

    private static final String WILDCARD_IMPORT_ERROR = "// FIXME Wildcards are not allowed in import statements";
    private static final String STATEMENTS_PER_LINE_ERROR = "// FIXME Only one statement per line is allowed";
    private static final String LENGTH_OF_LINE_ERROR = "// FIXME Length of line should not exceed %s characters";
    private static final String OPENING_BRACKET_ERROR = "// FIXME Opening brackets" +
            " should be placed on the same line as the declaration";

    private static final String DEFAULT_LINE_LENGTH_LIMIT = "100";
    private static final String DEFAULT_CHECK_MODE = "true";

    private Properties properties = new Properties();
    private Set<CodeCheck> checks = new HashSet<>();


    public StyleChecker() {
        this(null);
    }


    public StyleChecker(InputStream inputStream) {
        setDefaultProperties();
        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (IOException ex) {
                properties = new Properties();
                setDefaultProperties();
            }
        }
        setChecks();
    }


    public void checkStyle(InputStream source, OutputStream output) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(source));
            PrintWriter writer = new PrintWriter(output)) {

            String line;
            Set<String> lineErrors;

            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("")) {
                    writer.println(line);
                    continue;

                }
                setChecks();

                lineErrors = getErrors(line);

                if (!lineErrors.isEmpty()) {
                    for (String error : lineErrors) {

                        String whitespaces = line.substring(0, line.indexOf(line.trim()));
                        writer.println(whitespaces + error);
                    }
                }

                writer.println(line);
            }

        } catch (IOException ioExc) {
            throw new RuntimeException("Error occurred while checking style", ioExc);
        }
    }

    private void setDefaultProperties() {
        properties.setProperty(STATEMENTS_PER_LINE_CHECK, DEFAULT_CHECK_MODE);
        properties.setProperty(LENGTH_OF_LINE_CHECK, DEFAULT_CHECK_MODE);
        properties.setProperty(WILDCARD_IMPORT_CHECK, DEFAULT_CHECK_MODE);
        properties.setProperty(OPENING_BRACKET_CHECK, DEFAULT_CHECK_MODE);
        properties.setProperty(LINE_LENGTH_LIMIT, DEFAULT_LINE_LENGTH_LIMIT);
    }

    private boolean isPropertySet(String propertyKey) {
        String tempProperty = properties.getProperty(propertyKey);
        return Boolean.parseBoolean(tempProperty);

    }

    private void setChecks() {
        if (isPropertySet(STATEMENTS_PER_LINE_CHECK)) {
            checks.add(new OneStatementCheck(STATEMENTS_PER_LINE_ERROR));
        }
        if(isPropertySet(LENGTH_OF_LINE_CHECK)){
            int lineLimit = Integer.parseInt(properties.getProperty(LINE_LENGTH_LIMIT));
            checks.add(new LineLenghtCheck(LENGTH_OF_LINE_ERROR,lineLimit));
        }
        if(isPropertySet(WILDCARD_IMPORT_CHECK)){
            checks.add(new WildCardCheck(WILDCARD_IMPORT_ERROR));
        }
        if(isPropertySet(OPENING_BRACKET_CHECK)){
            checks.add(new BracketsCheck(OPENING_BRACKET_ERROR));
        }

    }
    private Set<String> getErrors(String line) {
        Set<String> errors = new HashSet<>();

        for (CodeCheck codeCheck : checks) {
            if (codeCheck.checkForError(line)) {
                errors.add(codeCheck.getErrorMessage());
            }
        }

        return errors;
    }
}
