import gen.japyLexer;
import gen.japyListener;
import gen.japyParser;
import gen.japyBaseVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
//        System.out.println("Hello world!");
        CharStream stream = CharStreams.fromFileName("1.txt");
        japyLexer lexer = new japyLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        japyParser parser = new japyParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.program();
        ParseTreeWalker walker = new ParseTreeWalker();
        japyListener listener = new Listener();
        japyListener hash_table = new HashTable();
//        System.out.println("Faze1");
//        walker.walk(listener, tree);
        System.out.println("Faze2 & 3");
        System.out.print("---------------1.txt-------------------");
        walker.walk(hash_table,tree);
    }
}