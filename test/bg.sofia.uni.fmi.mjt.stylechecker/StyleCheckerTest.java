package bg.sofia.uni.fmi.mjt.stylechecker;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Properties;

import static junit.framework.TestCase.assertEquals;

public class StyleCheckerTest {
    private static StyleChecker checker;


    @Before
    public void setUp() {
        checker = new StyleChecker();
    }

    @Test
    public void test() {
        ByteArrayInputStream input = new ByteArrayInputStream("import java.util.*;".getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        checker.checkStyle(input, output);
        String actual = new String(output.toByteArray());

        assertEquals("// FIXME Wildcards are not allowed in import statements\nimport java.util.*;", actual.trim());
    }

    @Test
    public void testShouldOpenAndFormatFileFromResources() throws IOException {
        InputStream inputFile = new FileInputStream("resources/example.txt");
        OutputStream output = new ByteArrayOutputStream();
        checker.checkStyle(inputFile, output);
        String expected = "// FIXME Wildcards are not allowed in import statements\n" +
                "import java.lang.*;\n" +
                "\n" +
                "public class Test {\n" +
                "\n" +
                "    public static void sayHello()\n" +
                "    // FIXME Opening brackets should be placed on the same line as the declaration\n" +
                "    {\n" +
                "        // FIXME Length of line should not exceed 100 characters\n" +
                "        String hello = \"Hey, it's Hannah, Hannah Baker. That's right. Don't adjust your... whatever device you're listening to this on. It's me, live and in stereo.\";\n" +
                "        System.out.println(hello);\n" +
                "    }\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        // FIXME Length of line should not exceed 100 characters\n" +
                "        // FIXME Only one statement per line is allowed\n" +
                "        sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();\n" +
                "    }\n" +
                "}\n" +
                "";

        assertEquals(expected, output.toString());
    }
}
