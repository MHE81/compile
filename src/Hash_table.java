import gen.japyListener;
import gen.japyParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hash_table implements japyListener {

//    public static void insert(Hashtable<String, String> hashtable, String key, String value) {
//        hashtable.put(key, value);
//    }
//
//    public static void lookup(Hashtable<String, String> hashtable, String key) {
//        hashtable.get(key);
//    }
//    public void program_ci(japyParser.ProgramContext ctx, String ci, Hashtable<String,String> program){
//        String str = "";
//        program.put("class_" + ctx.c1.className.getText(),str);
//        str= str.concat("(name:" + ctx.c1.className.getText() + ")");
//        if (ctx.c1.access_modifier() != null){
//            str = str.concat("(accessModifier: " + ctx.c1.access_modifier().getText());
//        }
//        if (ctx.c1.classParent != null){
//            str = str.concat("inherits: " + ctx.c1.classParent.getText() + ")");
//        }
//        if(ci.equals(ctx.mainclass.getText())){
//            str = str.concat("(main)");
//        }
//        program.put("class_" + ctx.c1.className.getText(),str);
//        for (String key: program.keySet()) {
//            System.out.println("key = " + key + ", value =" + program.get(key));
//        }
//    }
    @Override
    public void enterProgram(japyParser.ProgramContext ctx) {
        Hashtable<String, String> program = new Hashtable<>();
        System.out.println();
        System.out.println("program started");

        // Count the number of c1 classDeclarations
        int countC1 = 0;
        for (japyParser.ClassDeclarationContext classCtx : ctx.classDeclaration()) {
            if (classCtx.start.getTokenIndex() < ctx.entryClassDeclaration().start.getTokenIndex()) {
                countC1++;
            }
        }

        // Count the number of c3 classDeclarations
        int countC3 = 0;
        for (japyParser.ClassDeclarationContext classCtx : ctx.classDeclaration()) {
            if (classCtx.start.getTokenIndex() > ctx.entryClassDeclaration().start.getTokenIndex()) {
                countC3++;
            }
        }

        // Print the counts
        System.out.println("Number of c1: " + countC1);
        System.out.println("Number of c3: " + countC3);

        if (ctx.c1 != null){
            for (int i = 0; i <countC1; i++) {
                String str = "";
                program.put("class_" + ctx.c1.className.getText(),str);
                str= str.concat("(name:" + ctx.c1.className.getText() + ")");
                if (ctx.c1.access_modifier() != null){
                    str = str.concat("(accessModifier: " + ctx.c1.access_modifier().getText() + ")");
                }
                if (ctx.c1.classParent != null){
                    str = str.concat("(inherits: " + ctx.c1.classParent.getText() + ")");
                }
                program.put("class_" + ctx.c1.className.getText(),str);
                for (String key: program.keySet()) {
                    System.out.println("key = " + key + ", value =" + program.get(key));
                }
            }

        }
        String str1 = "";
        program.put("class_" + ctx.mainclass.classDeclaration().className.getText(),str1);
        str1= str1.concat("(name:" + ctx.mainclass.classDeclaration().className.getText() + ")");
        if (ctx.mainclass.classDeclaration().access_modifier() != null){
            str1 = str1.concat("(accessModifier: " + ctx.c1.access_modifier().getText());
        }
        if (ctx.mainclass.classDeclaration().classParent != null){
            str1 = str1.concat("inherits: " + ctx.mainclass.classDeclaration().classParent.getText() + ")");
        }
        str1 = str1.concat("(main)");
        program.put("class_" + ctx.mainclass.classDeclaration().className.getText(),str1);
        for (String key: program.keySet()) {
            System.out.println("key = " + key + ", value =" + program.get(key));
        }
        if (ctx.c3 != null){
            for (int i = 0; i <countC3; i++) {
                String str = "";
                program.put("class_" + ctx.c3.className.getText(),str);
                str= str.concat("(name:" + ctx.c3.className.getText() + ")");
                if (ctx.c3.access_modifier() != null){
                    str = str.concat("(accessModifier: " + ctx.c3.access_modifier().getText());
                }
                if (ctx.c3.classParent != null){
                    str = str.concat("inherits: " + ctx.c3.classParent.getText() + ")");
                }
                program.put("class_" + ctx.c3.className.getText(),str);
                for (String key: program.keySet()) {
                    System.out.println("key = " + key + ", value =" + program.get(key));
                }
            }
        }
        System.out.println("--------------------------------------------------");
    }

    @Override
    public void exitProgram(japyParser.ProgramContext ctx) {
        System.out.println("program ended");
    }

    @Override
    public void enterClassDeclaration(japyParser.ClassDeclarationContext ctx) {
        Hashtable<String, String> class_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();
        System.out.println(ctx.className.getText() + ":(" + line1 + "," + line2 + ")");
//        System.out.println(ctx.methodDeclaration() + ":(" + line1 + "," + line2 + ")");
//        if (ctx.fieldDeclaration() != null){
//            enterFieldDeclaration(ctx.fieldDeclaration());
//        }
//        if (ctx.methodDeclaration() != null){
//
//        }
    }

    @Override
    public void exitClassDeclaration(japyParser.ClassDeclarationContext ctx) {

    }

    @Override
    public void enterEntryClassDeclaration(japyParser.EntryClassDeclarationContext ctx) {

    }

    @Override
    public void exitEntryClassDeclaration(japyParser.EntryClassDeclarationContext ctx) {

    }

    @Override
    public void enterFieldDeclaration(japyParser.FieldDeclarationContext ctx) {
        Hashtable<String, String> field_table = new Hashtable<>();
        String str = "";
        str = str.concat("(name:" + ctx.fieldName.getText() + ")");
        if (ctx.access_modifier() != null){
            str = str.concat("(accessModifier: " + ctx.access_modifier().getText() + ")");
        }
        str = str.concat("(type: " + ctx.fieldType.getText() +")");
        field_table.put("field_" + ctx.fieldName.getText(),str);
        for (String key: field_table.keySet()) {
            System.out.println("key = " + key + ", value =" + field_table.get(key));
        }
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

    @Override
    public void enterMethodDeclaration(japyParser.MethodDeclarationContext ctx) {



//        // Initialize parameter count
//        int paramCount = 0;
//
//        // Check if there are any parameters
//        if (ctx.param1 != null) {
//            paramCount = 1; // At least one parameter exists
//            List<japyParser.MethodDeclarationContext.param2Context> param2List = ctx.param2();
//            paramCount += param2List.size(); // Count additional parameters
//        }
//
//        // Print the number of parameters
//        System.out.println("Number of parameters: " + paramCount);

        Hashtable<String, String> func_table = new Hashtable<>();
        String str = "";
        str = str.concat("(name: " + ctx.methodName.getText() + ")");
        if (ctx.access_modifier() != null){
            str = str.concat("(accessModifier: " + ctx.access_modifier().getText() + ")");
        }
        str = str.concat("(return: " + ctx.t.getText() + ")");
        int index = 0;
        if (ctx.param1 != null){
            str = str.concat("\n parameters: [" + "[(index: " + index + "), (name: " + ctx.param1.getText() + "), (type: " + ctx.typeP1.getText()+ ")");
            index++;
            if (ctx.param2 != null){
                str = str.concat("\n parameters: [" + "[(index: " + index + "), (name: " + ctx.param2.getText() + "), (type: " + ctx.typeP2.getText() + ")");
                index++;
            }
        }
        func_table.put("function_" + ctx.methodName.getText(), str);
        for (String key: func_table.keySet()) {
            System.out.println("key = " + key + ", value =" + func_table.get(key));
        }
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();
        System.out.println(ctx.methodName.getText() + ":(" + line1 + "," + line2 + ")");
        System.out.println("!NO KEY FOUND!");
    }

    @Override
    public void exitMethodDeclaration(japyParser.MethodDeclarationContext ctx) {

    }

    @Override
    public void enterClosedStatement(japyParser.ClosedStatementContext ctx) {

    }

    @Override
    public void exitClosedStatement(japyParser.ClosedStatementContext ctx) {

    }
    public void enterClosedConditional_if(japyParser.ClosedConditionalContext ctx){
        Hashtable<String, String> if_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();
        System.out.println("if: (" + line1 + "," + line2 + ")");
        if (ctx.ifStat.s7 != null){
            for (int i = 0; i < ctx.ifStat.s7.ID().size(); i++) {
                String str = "";
                String id = ctx.ifStat.s7.ID(i).getText();
                str = str.concat("(name: " + id + ")(first appearance: " + (line1+1) +")");
                if_table.put("var_" + id , str);

            }
        }
        for (String key: if_table.keySet()) {
            System.out.println("key = " + key + ", value =" + if_table.get(key));
        }
    }

    public static void concatenateElifConditions(String conditionalStatement, japyParser.ClosedConditionalContext ctx) {
        Hashtable<String, String> elif_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();
        System.out.println("elif: (" + line1 + "," + line2 + ")");
        // Regular expression to match the 'elif' statements
        String elifPattern = "elif\\s*\\((.*?)\\)";

        // Compile the pattern
        Pattern pattern = Pattern.compile(elifPattern);

        // Match the pattern with the conditional statement
        Matcher matcher = pattern.matcher(conditionalStatement);

        // Initialize a StringBuilder to concatenate elif conditions
//        StringBuilder concatenatedElifConditions = new StringBuilder();

        // Count the number of 'elif' statements
        int count = 0;
        while (matcher.find()) {
            count++;
            String elifCondition = matcher.group(1).trim();
            String str2 = "(name: " + ctx.ifStat.getText() + ")(first appearance: " + (line1+1) +")";
            elif_table.put("var_" + ctx.ifStat.getText(), str2);
            for (String key: elif_table.keySet()) {
                System.out.println("key = " + key + ", value =" + elif_table.get(key));
            }
//            concatenatedElifConditions.append(str2).append("\n");
        }

        // Print the count (optional, to verify the count)
        System.out.println("Number of 'elif' statements: " + count);

//        return concatenatedElifConditions.toString();
    }
    public void enterClosedConditional_elif(japyParser.ClosedConditionalContext ctx) {
//        System.out.println("elifs"+ctx.expression().size());

        if (ctx.elifExp != null) {
//            concatenateElifConditions(ctx.getText(), ctx);
            Hashtable<String, String> elif_table = new Hashtable<>();
            for (int i = 0; i <ctx.expression().size(); i++) {
                Token token1 = ctx.getStart();
                Token token2 = ctx.getStop();
                int line1 = token1.getLine();
                int line2 = token2.getLine();
                System.out.println("elif: (" + line1 + "," + line2 + ")");
                if (ctx.elifStat.s7 != null){
                    for (int j = 0; j < ctx.elifStat.s7.ID().size(); j++) {
                        String str = "";
                        String id = ctx.elifStat.s7.ID(j+1).getText();
                        str = str.concat("(name: " + id + ")(first appearance: " + (line1+1) +")");
                        elif_table.put("var_" + id , str);

                    }
                }
                for (String key: elif_table.keySet()) {
                    System.out.println("key = " + key + ", value =" + elif_table.get(key));
                }

            }
        }
    }
    public void enterClosedConditional_else(japyParser.ClosedConditionalContext ctx){
        Hashtable<String, String> else_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();
        System.out.println("else: (" + line1 + "," + line2 + ")");
        String str = "";
        str = str.concat("(name: " + ctx.elseStmt.getText() + ")(first appearance: " + (line1+1) +")");
        else_table.put("var_" + ctx.elseStmt.getText(), str);
        for (String key: else_table.keySet()) {
            System.out.println("key = " + key + ", value =" + else_table.get(key));
        }
    }

    @Override
    public void enterClosedConditional(japyParser.ClosedConditionalContext ctx) {
//        String str = "";
        enterClosedConditional_if(ctx);
        enterClosedConditional_elif(ctx);
        enterClosedConditional_else(ctx);

    }

    @Override
    public void exitClosedConditional(japyParser.ClosedConditionalContext ctx) {

    }

    public static void concatenateElifConditions(String conditionalStatement, japyParser.OpenConditionalContext ctx) {
        Hashtable<String, String> elif_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();
        System.out.println("elif: (" + line1 + "," + line2 + ")");
        // Regular expression to match the 'elif' statements
        String elifPattern = "elif\\s*\\((.*?)\\)";

        // Compile the pattern
        Pattern pattern = Pattern.compile(elifPattern);

        // Match the pattern with the conditional statement
        Matcher matcher = pattern.matcher(conditionalStatement);

        // Initialize a StringBuilder to concatenate elif conditions
//        StringBuilder concatenatedElifConditions = new StringBuilder();

        // Count the number of 'elif' statements
        int count = 0;
        while (matcher.find()) {
            count++;
            String elifCondition = matcher.group(1).trim();
            String str2 = "(name: " + ctx.ifStat.getText() + ")(first appearance: " + (line1+1) +")";
            elif_table.put("var_" + ctx.ifStat.getText(), str2);
            for (String key: elif_table.keySet()) {
                System.out.println("key = " + key + ", value =" + elif_table.get(key));
            }
//            concatenatedElifConditions.append(str2).append("\n");
        }

        // Print the count (optional, to verify the count)
        System.out.println("Number of 'elif' statements: " + count);

//        return concatenatedElifConditions.toString();
    }
    public void enterClosedConditional_if(japyParser.OpenConditionalContext ctx){
        Hashtable<String, String> if_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();
        System.out.println("if: (" + line1 + "," + line2 + ")");
        String str = "";
        str = str.concat("(name: " + ctx.ifStat.getText() + ")(first appearance: " + (line1+1) +")");
        if_table.put("var_" + ctx.ifStat.getText(), str);
        for (String key: if_table.keySet()) {
            System.out.println("key = " + key + ", value =" + if_table.get(key));
        }

//        String str = "<if condition: <" + ctx.ifExp.getText() + "> ";
//        System.out.println(str);
    }
    public void enterClosedConditional_elif(japyParser.OpenConditionalContext ctx) {
        if (ctx.elifExp != null) {
            concatenateElifConditions(ctx.getText(), ctx);
//            enterClosedConditional_elif(ctx);
//            System.out.println(str2);
//            Token token1 = ctx.getStart();
//            Token token2 = ctx.getStop();
//            int line1 = token1.getLine();
//            int line2 = token2.getLine();
//            System.out.println("elif: (" + line1 + "," + line2 + ")");
        }
    }
    public void enterClosedConditional_else(japyParser.OpenConditionalContext ctx){
        Hashtable<String, String> else_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();
        System.out.println("else: (" + line1 + "," + line2 + ")");
        String str = "";
        str = str.concat("(name: " + ctx.elifStat.getText() + ")(first appearance: " + (line1+1) +")");
        else_table.put("var_" + ctx.elifStat.getText(), str);
        for (String key: else_table.keySet()) {
            System.out.println("key = " + key + ", value =" + else_table.get(key));
        }
    }
    @Override
    public void enterOpenConditional(japyParser.OpenConditionalContext ctx) {
        enterClosedConditional_if(ctx);
        enterClosedConditional_elif(ctx);
        enterClosedConditional_else(ctx);    }

    @Override
    public void exitOpenConditional(japyParser.OpenConditionalContext ctx) {

    }

    @Override
    public void enterOpenStatement(japyParser.OpenStatementContext ctx) {

    }

    @Override
    public void exitOpenStatement(japyParser.OpenStatementContext ctx) {

    }

    @Override
    public void enterStatement(japyParser.StatementContext ctx) {

    }

    @Override
    public void exitStatement(japyParser.StatementContext ctx) {

    }

    @Override
    public void enterStatementVarDef(japyParser.StatementVarDefContext ctx) {

    }

    @Override
    public void exitStatementVarDef(japyParser.StatementVarDefContext ctx) {

    }

    @Override
    public void enterStatementBlock(japyParser.StatementBlockContext ctx) {

    }

    @Override
    public void exitStatementBlock(japyParser.StatementBlockContext ctx) {

    }

    @Override
    public void enterStatementContinue(japyParser.StatementContinueContext ctx) {

    }

    @Override
    public void exitStatementContinue(japyParser.StatementContinueContext ctx) {

    }

    @Override
    public void enterStatementBreak(japyParser.StatementBreakContext ctx) {

    }

    @Override
    public void exitStatementBreak(japyParser.StatementBreakContext ctx) {

    }

    @Override
    public void enterStatementReturn(japyParser.StatementReturnContext ctx) {

    }

    @Override
    public void exitStatementReturn(japyParser.StatementReturnContext ctx) {

    }

    @Override
    public void enterStatementClosedLoop(japyParser.StatementClosedLoopContext ctx) {
//        Hashtable<String, String> while_table = new Hashtable<>();
        System.out.println("!NO KEY FOUND!");
    }

    @Override
    public void exitStatementClosedLoop(japyParser.StatementClosedLoopContext ctx) {

    }

    @Override
    public void enterStatementOpenLoop(japyParser.StatementOpenLoopContext ctx) {
        Hashtable<String, String> while_table = new Hashtable<>();
    }

    @Override
    public void exitStatementOpenLoop(japyParser.StatementOpenLoopContext ctx) {

    }

    @Override
    public void enterStatementWrite(japyParser.StatementWriteContext ctx) {

    }

    @Override
    public void exitStatementWrite(japyParser.StatementWriteContext ctx) {

    }

    @Override
    public void enterStatementAssignment(japyParser.StatementAssignmentContext ctx) {

    }

    @Override
    public void exitStatementAssignment(japyParser.StatementAssignmentContext ctx) {

    }

    @Override
    public void enterStatementInc(japyParser.StatementIncContext ctx) {

    }

    @Override
    public void exitStatementInc(japyParser.StatementIncContext ctx) {

    }

    @Override
    public void enterStatementDec(japyParser.StatementDecContext ctx) {

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
