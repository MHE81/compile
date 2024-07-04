import gen.japyLexer;
import gen.japyListener;
import gen.japyParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        CharStream stream = CharStreams.fromFileName("1.txt");
        japyLexer lexer = new japyLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        japyParser parser = new japyParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.program();
        ParseTreeWalker walker = new ParseTreeWalker();
        japyListener listener = new Listener();
        walker.walk(listener, tree);
    }
}