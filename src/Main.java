import Parser.MxLexer;
import Parser.MxParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) throws Exception {
        //String name = "test.mx";
        //InputStream input = new FileInputStream(name);
        InputStream input = System.in;

        try {
            MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            ParseTree parseTreeRoot = parser.program();

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            throw new RuntimeException();
        }
    }
}