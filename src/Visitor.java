import gen.japyBaseVisitor;
import gen.japyParser;
import org.antlr.v4.runtime.Token;

public class Visitor extends japyBaseVisitor {
    @Override
    public Void visitStatementBreak(japyParser.StatementBreakContext ctx) {
        Token token = ctx.getStart();
        int line = token.getLine();
        System.out.println("Break encountered at line: " + line);
        return (Void) visitChildren(ctx);
    }

    @Override
    public Void visitStatementClosedLoop(japyParser.StatementClosedLoopContext ctx) {
        super.visitStatementClosedLoop(ctx); // Visit the loop body first
        Token token = ctx.getStop();
        int line = token.getLine();
        System.out.println("Program continues after loop at line: " + (line + 1)); // Assuming program continues on the next line
        return null;
    }

    @Override
    public Void visitProgram(japyParser.ProgramContext ctx) {
        return (Void) visitChildren(ctx);
    }
}
