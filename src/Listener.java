import gen.japyLexer;
import gen.japyListener;
import gen.japyParser;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class Listener implements japyListener {

    @Override
    public void enterProgram(japyParser.ProgramContext ctx) {
        System.out.println("program started");
    }

    @Override
    public void exitProgram(japyParser.ProgramContext ctx) {
        System.out.println("program ended");
    }

    @Override
    public void enterClassDeclaration(japyParser.ClassDeclarationContext ctx) {
        String str = "<class '"+ctx.className.getText()+"'";
        if(ctx.access_modifier()!= null){
            str = str.concat(","+ctx.access_modifier().getText());
        }
        if(ctx.classParent != null){
            str = str.concat(", inherits '"+ctx.classParent.getText()+"'");
        }
        System.out.println(str + ">");
    }

    @Override
    public void exitClassDeclaration(japyParser.ClassDeclarationContext ctx) {
        System.out.println("</class>");
    }

    @Override
    public void enterEntryClassDeclaration(japyParser.EntryClassDeclarationContext ctx) {
        Hashtable<String, String> Main_class_table = new Hashtable<>();
        System.out.println("<Main class>");
    }

    @Override
    public void exitEntryClassDeclaration(japyParser.EntryClassDeclarationContext ctx) {
        System.out.println("</Main class>");
    }

    @Override
    public void enterFieldDeclaration(japyParser.FieldDeclarationContext ctx) {
        String str = ctx.fieldName.getText();
//        if(ctx.ii != null){
//            List id = printNumberOfIIs(ctx.getText());
        List idd = ctx.ID().subList(1, ctx.ID().size());
//        System.out.println(idd);
        for (Object o : idd) {
            str = str.concat("," + o);
        }
//        }
        str = str.concat("(field");
        if(ctx.access_modifier() != null){
            str =str.concat(", "+ ctx.access_modifier().getText());
        }
        str = str.concat(", " + ctx.fieldType.getText() + ")");
        System.out.println(str);
//        printNumberOfIIs(ctx.getText());
    }

    @Override
    public void exitFieldDeclaration(japyParser.FieldDeclarationContext ctx) {
    }

    @Override
    public void enterAccess_modifier(japyParser.Access_modifierContext ctx) {
    }

    @Override
    public void exitAccess_modifier(japyParser.Access_modifierContext ctx) {
    }

    public static String concatenateParameters(String methodDeclaration) {
        // Regular expression to match the parameter list in the method declaration
        String paramPattern = "\\(\\s*(.*?)\\s*\\)";

        // Compile the pattern
        Pattern pattern = Pattern.compile(paramPattern);

        // Match the pattern with the method declaration
        Matcher matcher = pattern.matcher(methodDeclaration);

        if (!matcher.find()) {
            return "";
        }

        String paramList = matcher.group(1);

        if (paramList.isEmpty()) {
            return "";
        }

        // Split the parameter list by comma
        String[] params = paramList.split(",");

        // Initialize an empty string for concatenation
        StringBuilder concatenatedParams = new StringBuilder();

        // Iterate over each parameter and concatenate it with its type
        for (String param : params) {
            // Split each parameter by ':' to separate parameter name and type
            String[] paramParts = param.trim().split(":");
            if (paramParts.length == 2) {
                concatenatedParams.append(", (")
                        .append(paramParts[0].trim())
                        .append(", ")
                        .append(paramParts[1].trim())
                        .append(")");
            }
        }

        // Return the concatenated string
        return concatenatedParams.toString();
    }

    @Override
    public void enterMethodDeclaration(japyParser.MethodDeclarationContext ctx) {
        String str = "<function '" + ctx.methodName.getText()+"'";
        if(ctx.access_modifier()!= null){
            str = str.concat(","+ctx.access_modifier().getText());
        }
        str = str.concat(concatenateParameters(ctx.getText()));
        System.out.println(str + "]>");
    }

    @Override
    public void exitMethodDeclaration(japyParser.MethodDeclarationContext ctx) {
        if(ctx.s != null) {
            System.out.println("</function return (" + ctx.s.s1.s6.e.getText() + "," + ctx.t.getText() + ")>");
        }
    }

    @Override
    public void enterClosedStatement(japyParser.ClosedStatementContext ctx) {
//        if(ctx.s1 != null){
//            enterStatementBlock(ctx.s1);
//        }
//        if (ctx.conditionalStat != null ){
//            enterClosedConditional(ctx.conditionalStat);
//        }
//        if(ctx.s3 != null){
//            enterStatementClosedLoop(ctx.s3);
//        }
//        if(ctx.s4 != null){
//            enterStatementWrite(ctx.s4);
//        }
//        if(ctx.s5 != null){
//            enterStatementAssignment(ctx.s5);
//        }
//        if(ctx.s6 != null){
//            enterStatementReturn(ctx.s6);
//        }
//        if(ctx.s7 != null){
//            enterStatementVarDef(ctx.s7);
//        }
//        if(ctx.s8 != null){
//            enterStatementContinue(ctx.s8);
//        }
//        if(ctx.s9 != null){
//            enterStatementBreak(ctx.s9);
//        }
//        if(ctx.incStat != null){
//            enterStatementInc(ctx.incStat);
//        }
//        if(ctx.decStat != null){
//            enterStatementDec(ctx.decStat);
//        }

    }

    @Override
    public void exitClosedStatement(japyParser.ClosedStatementContext ctx) {
    }

    public static String concatenateElifConditions(String conditionalStatement) {
        // Regular expression to match the 'elif' statements
        String elifPattern = "elif\\s*\\((.*?)\\)";

        // Compile the pattern
        Pattern pattern = Pattern.compile(elifPattern);

        // Match the pattern with the conditional statement
        Matcher matcher = pattern.matcher(conditionalStatement);

        // Initialize a StringBuilder to concatenate elif conditions
        StringBuilder concatenatedElifConditions = new StringBuilder();

        // Count the number of 'elif' statements
        int count = 0;
        while (matcher.find()) {
            count++;
            String elifCondition = matcher.group(1).trim();
            String str2 = "<elif condition: <" + elifCondition + ">>";
            concatenatedElifConditions.append(str2).append("\n");
        }

        // Print the count (optional, to verify the count)
        System.out.println("Number of 'elif' statements: " + count);
        return concatenatedElifConditions.toString();
    }

    public void enterClosedConditional_if(japyParser.ClosedConditionalContext ctx){
        String str = "<if condition: <" + ctx.ifExp.getText() + "> ";
        System.out.println(str);
    }
    public void enterClosedConditional_elif(japyParser.ClosedConditionalContext ctx) {
        if (ctx.elifExp != null) {
            String str2 = concatenateElifConditions(ctx.getText());
            System.out.print(str2);
        }
    }
    public void enterClosedConditional_else(japyParser.ClosedConditionalContext ctx){
        String str3 = "<else>";
        System.out.println(str3);
    }

    @Override
    public void enterClosedConditional(japyParser.ClosedConditionalContext ctx) {
        enterClosedConditional_if(ctx);
//        enterClosedStatement(ctx.ifStat);
        enterClosedConditional_elif(ctx);
//        enterClosedStatement(ctx.elifStat);
        enterClosedConditional_else(ctx);
//        enterClosedStatement(ctx.elseStmt);
    }

    @Override
    public void exitClosedConditional(japyParser.ClosedConditionalContext ctx) {;
        System.out.println("</else>");
        System.out.println("</if>");
    }
    public void enterOpenConditional_if(japyParser.OpenConditionalContext ctx) {
        String str = "<if condition: <" + ctx.ifExp.getText() + "> ";
        System.out.println(str);
    }
    public void enterOpenConditional_if_elif(japyParser.OpenConditionalContext ctx) {
        String str = "<if condition: <" + ctx.secondIfStat.getText() + "> ";
        System.out.println(str);
        if(ctx.elifExp != null) {
            String str2 = concatenateElifConditions(ctx.getText());;
            System.out.println(str2);
//            String str2 = "";
//            for (int i = 0; i <ctx.expression().size()-1; i++) {
//                str2 = "<elif condition: <" + ctx.elifExp.getText() + ">>";
//            }
            System.out.println(str2);
        }
        String str2 = "<elif condition: " + ctx.lastElifExp.getText();
        System.out.println(str2);
    }
//    public void enterOpenConditional_elif(japyParser.OpenConditionalContext ctx) {
//
//    }
    public void enterOpenConditional_else(japyParser.OpenConditionalContext ctx) {
        String str = "<if condition: <" + ctx.thirdIfStat.getText() + "> ";
        System.out.println(str);
        if(ctx.elifExp != null) {
            String str2 = concatenateElifConditions(ctx.getText());;
            System.out.println(str2);
//            String str2 = "";
//            for (int i = 0; i <ctx.expression().size()-1; i++) {
//                str2 = "<elif condition: <" + ctx.elifExp.getText() + ">>";
//            }
            System.out.println(str2);
        }
        if(ctx.elseStmt != null ){
            String str3 = "<else>";
            System.out.println(str3);
        }
    }

    @Override
    public void enterOpenConditional(japyParser.OpenConditionalContext ctx) {
        enterOpenConditional_if(ctx);
        if (ctx.elifExp != null){
            enterOpenConditional_if_elif(ctx);
        }
        if (ctx.elseStmt != null){
            enterOpenConditional_else(ctx);
        }
    }

    @Override
    public void exitOpenConditional(japyParser.OpenConditionalContext ctx) {
        System.out.println("</else>");
        System.out.println("</if>");
    }

    @Override
    public void enterOpenStatement(japyParser.OpenStatementContext ctx) {
    }

    @Override
    public void exitOpenStatement(japyParser.OpenStatementContext ctx) {
    }
    @Override
    public void enterStatement(japyParser.StatementContext ctx) {
//        if (ctx.s1 != null){
//            if (ctx.s1.statementBlock() != null){
//                enterStatementBlock(ctx.s1.s1);
//            }
//        }
    }
    @Override
    public void exitStatement(japyParser.StatementContext ctx) {
    }

//    public static void countIDsInStatementVarDef(japyParser.StatementVarDefContext ctx) {
//        // Return the number of ID tokens in the context
////        return ctx.ID().size();
//        for (int i = 0; i < ctx.ID().size(); i++) {
//            String expression = ctx.expression(i).getText();
//            String id = ctx.ID(i).getText();
//            System.out.print(expression + "-> (" + id + ", var)");
//            if (i != ctx.ID().size()-1){
//                System.out.print(", ");
//            }
//        }
//        System.out.println("");
//    }
    @Override
    public void enterStatementVarDef(japyParser.StatementVarDefContext ctx) {
//        countIDsInStatementVarDef(ctx);
        for (int i = 0; i < ctx.ID().size(); i++) {
            String expression = ctx.expression(i).getText();
            String id = ctx.ID(i).getText();
            System.out.print(expression + "-> (" + id + ", var)");
            if (i != ctx.ID().size()-1){
                System.out.print(", ");
            }
        }
        System.out.println("");
    }

    @Override
    public void exitStatementVarDef(japyParser.StatementVarDefContext ctx) {
    }

    @Override
    public void enterStatementBlock(japyParser.StatementBlockContext ctx) {
        for (int i = 0; i <ctx.statement().size(); i++) {
            enterStatement(ctx.s);
        }
    }

    @Override
    public void exitStatementBlock(japyParser.StatementBlockContext ctx) {

    }

    @Override
    public void enterStatementContinue(japyParser.StatementContinueContext ctx) {
        ParserRuleContext parent = ctx.getParent();
        while (parent != null && !(parent instanceof japyParser.StatementClosedLoopContext)) {
            parent = parent.getParent();
        }
        if (parent != null) {
            Token token = parent.getStart();
            int line = token.getLine();
            System.out.println("goto: " + line);
        }
    }

    @Override
    public void exitStatementContinue(japyParser.StatementContinueContext ctx) {
    }



    @Override
    public void enterStatementBreak(japyParser.StatementBreakContext ctx) {
        ParserRuleContext parent = ctx.getParent();
        while (parent != null && !(parent instanceof japyParser.StatementClosedLoopContext)) {
            parent = parent.getParent();
        }
        if (parent != null) {
            Token token = parent.getStop();
            int line = token.getLine()+1;
            System.out.println("goto: " + line);
        }
    }

    @Override
    public void exitStatementBreak(japyParser.StatementBreakContext ctx) {
    }

    @Override
    public void enterStatementReturn(japyParser.StatementReturnContext ctx) {
//        System.out.println(c);
    }

    @Override
    public void exitStatementReturn(japyParser.StatementReturnContext ctx) {

    }

    @Override
    public void enterStatementClosedLoop(japyParser.StatementClosedLoopContext ctx) {
        String str = "<while condition: <" + ctx.e.getText() + ">>";
        System.out.println(str);
    }

    @Override
    public void exitStatementClosedLoop(japyParser.StatementClosedLoopContext ctx) {
        System.out.println("</while>");
    }

    @Override
    public void enterStatementOpenLoop(japyParser.StatementOpenLoopContext ctx) {
        String str = "while condition: " + "<" + ctx.e.getText();
        System.out.println(str);
    }

    @Override
    public void exitStatementOpenLoop(japyParser.StatementOpenLoopContext ctx) {
        System.out.println("</while>");
    }

    @Override
    public void enterStatementWrite(japyParser.StatementWriteContext ctx) {
        System.out.println(ctx.getText());
    }

    @Override
    public void exitStatementWrite(japyParser.StatementWriteContext ctx) {
    }

    @Override
    public void enterStatementAssignment(japyParser.StatementAssignmentContext ctx) {
        String str = ctx.right.getText() + " -> " + ctx.left.getText();
        System.out.println(str);
    }

    @Override
    public void exitStatementAssignment(japyParser.StatementAssignmentContext ctx) {

    }

    @Override
    public void enterStatementInc(japyParser.StatementIncContext ctx) {
        String str = "1+" + ctx.expression().getText() + " -> " + ctx.expression().getText();
        System.out.println(str);
    }

    @Override
    public void exitStatementInc(japyParser.StatementIncContext ctx) {

    }

    @Override
    public void enterStatementDec(japyParser.StatementDecContext ctx) {
        String str = "1-" + ctx.expression().getText() + " -> " + ctx.expression().getText();
        System.out.println(str);
    }

    @Override
    public void exitStatementDec(japyParser.StatementDecContext ctx) {

    }

    @Override
    public void enterExpression(japyParser.ExpressionContext ctx) {
    }

    @Override
    public void exitExpression(japyParser.ExpressionContext ctx) {
    }

    @Override
    public void enterExpressionOr(japyParser.ExpressionOrContext ctx) {
//        System.out.println(ctx.a.getText() + ctx.ot.getText());
    }

    @Override
    public void exitExpressionOr(japyParser.ExpressionOrContext ctx) {
    }

    @Override
    public void enterExpressionOrTemp(japyParser.ExpressionOrTempContext ctx) {
    }

    @Override
    public void exitExpressionOrTemp(japyParser.ExpressionOrTempContext ctx) {
    }

    @Override
    public void enterExpressionAnd(japyParser.ExpressionAndContext ctx) {
    }

    @Override
    public void exitExpressionAnd(japyParser.ExpressionAndContext ctx) {
    }

    @Override
    public void enterExpressionAndTemp(japyParser.ExpressionAndTempContext ctx) {
    }

    @Override
    public void exitExpressionAndTemp(japyParser.ExpressionAndTempContext ctx) {
    }

    @Override
    public void enterExpressionEq(japyParser.ExpressionEqContext ctx) {
    }

    @Override
    public void exitExpressionEq(japyParser.ExpressionEqContext ctx) {
    }

    @Override
    public void enterExpressionEqTemp(japyParser.ExpressionEqTempContext ctx) {
    }

    @Override
    public void exitExpressionEqTemp(japyParser.ExpressionEqTempContext ctx) {
    }

    @Override
    public void enterExpressionCmp(japyParser.ExpressionCmpContext ctx) {
    }

    @Override
    public void exitExpressionCmp(japyParser.ExpressionCmpContext ctx) {
    }

    @Override
    public void enterExpressionCmpTemp(japyParser.ExpressionCmpTempContext ctx) {
    }

    @Override
    public void exitExpressionCmpTemp(japyParser.ExpressionCmpTempContext ctx) {
    }

    @Override
    public void enterExpressionAdd(japyParser.ExpressionAddContext ctx) {
    }

    @Override
    public void exitExpressionAdd(japyParser.ExpressionAddContext ctx) {
    }

    @Override
    public void enterExpressionAddTemp(japyParser.ExpressionAddTempContext ctx) {
    }

    @Override
    public void exitExpressionAddTemp(japyParser.ExpressionAddTempContext ctx) {
    }

    @Override
    public void enterExpressionMultMod(japyParser.ExpressionMultModContext ctx) {
    }

    @Override
    public void exitExpressionMultMod(japyParser.ExpressionMultModContext ctx) {
    }

    @Override
    public void enterExpressionMultModTemp(japyParser.ExpressionMultModTempContext ctx) {
    }

    @Override
    public void exitExpressionMultModTemp(japyParser.ExpressionMultModTempContext ctx) {
    }

    @Override
    public void enterExpressionUnary(japyParser.ExpressionUnaryContext ctx) {
    }

    @Override
    public void exitExpressionUnary(japyParser.ExpressionUnaryContext ctx) {
    }

    @Override
    public void enterExpressionMethods(japyParser.ExpressionMethodsContext ctx) {
    }

    @Override
    public void exitExpressionMethods(japyParser.ExpressionMethodsContext ctx) {
    }

    @Override
    public void enterExpressionMethodsTemp(japyParser.ExpressionMethodsTempContext ctx) {
    }

    @Override
    public void exitExpressionMethodsTemp(japyParser.ExpressionMethodsTempContext ctx) {
    }

    @Override
    public void enterExpressionOther(japyParser.ExpressionOtherContext ctx) {
    }

    @Override
    public void exitExpressionOther(japyParser.ExpressionOtherContext ctx) {
    }

    @Override
    public void enterJapyType(japyParser.JapyTypeContext ctx) {

    }

    @Override
    public void exitJapyType(japyParser.JapyTypeContext ctx) {

    }

    @Override
    public void enterSingleType(japyParser.SingleTypeContext ctx) {

    }

    @Override
    public void exitSingleType(japyParser.SingleTypeContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
