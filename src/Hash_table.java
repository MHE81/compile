import gen.japyListener;
import gen.japyParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Hashtable;
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

        if (ctx.c1 != null) {
            for (int i = 0; i < countC1; i++) {
                String str = "";
                program.put("class_" + ctx.c1.className.getText(), str);
                str = str.concat("(name:" + ctx.c1.className.getText() + ")");
                if (ctx.c1.access_modifier() != null) {
                    str = str.concat("(accessModifier: " + ctx.c1.access_modifier().getText() + ")");
                }
                if (ctx.c1.classParent != null) {
                    str = str.concat("(inherits: " + ctx.c1.classParent.getText() + ")");
                }
                program.put("class_" + ctx.c1.className.getText(), str);
                for (String key : program.keySet()) {
                    System.out.println("key = " + key + ", value =" + program.get(key));
                }
            }

        }
        String str1 = "";
        program.put("class_" + ctx.mainclass.classDeclaration().className.getText(), str1);
        str1 = str1.concat("(name:" + ctx.mainclass.classDeclaration().className.getText() + ")");
        if (ctx.mainclass.classDeclaration().access_modifier() != null) {
            str1 = str1.concat("(accessModifier: " + ctx.c1.access_modifier().getText());
        }
        if (ctx.mainclass.classDeclaration().classParent != null) {
            str1 = str1.concat("inherits: " + ctx.mainclass.classDeclaration().classParent.getText() + ")");
        }
        str1 = str1.concat("(main)");
        program.put("class_" + ctx.mainclass.classDeclaration().className.getText(), str1);
        for (String key : program.keySet()) {
            System.out.println("key = " + key + ", value =" + program.get(key));
        }
        if (ctx.c3 != null) {
            for (int i = 0; i < countC3; i++) {
                String str = "";
                program.put("class_" + ctx.c3.className.getText(), str);
                str = str.concat("(name:" + ctx.c3.className.getText() + ")");
                if (ctx.c3.access_modifier() != null) {
                    str = str.concat("(accessModifier: " + ctx.c3.access_modifier().getText());
                }
                if (ctx.c3.classParent != null) {
                    str = str.concat("inherits: " + ctx.c3.classParent.getText() + ")");
                }
                program.put("class_" + ctx.c3.className.getText(), str);
                for (String key : program.keySet()) {
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
        if (ctx.access_modifier() != null) {
            str = str.concat("(accessModifier: " + ctx.access_modifier().getText() + ")");
        }
        str = str.concat("(type: " + ctx.fieldType.getText() + ")");
        field_table.put("field_" + ctx.fieldName.getText(), str);
//        List idd = ctx.japyType().subList(1, ctx.ID().size());
//        System.out.println(idd);
//        for (Object o : idd) {
//            str = str.concat("," + o);
//        }
//        }
//        str = str.concat("(field");
//        if(ctx.access_modifier() != null){
//            str =str.concat(", "+ ctx.access_modifier().getText());
//        str = str.concat(", " + ctx.fieldType.getText() + ")");
//        System.out.println(str);
        for (String key : field_table.keySet()) {
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
        StringBuilder concatenatedParams = new StringBuilder("parameters: [");
        // Iterate over each parameter and concatenate it with its type
        for (int i = 0; i < params.length; i++) {
            String param = params[i];
            // Split each parameter by ':' to separate parameter name and type
            String[] paramParts = param.trim().split(":");
            if (paramParts.length == 2) {
                String paramName = paramParts[0].trim();
                String paramType = paramParts[1].trim();
                boolean isArray = paramType.endsWith("[]");
                String typeStr = isArray ? "(" + paramType.replace("[]", "") + ", is_array)" : paramType;

                concatenatedParams.append("[(index: ")
                        .append(i)
                        .append("), (name: ")
                        .append(paramName)
                        .append("), (type: ")
                        .append(typeStr)
                        .append(")]");
                if ((params.length - 1) > i) {
                    concatenatedParams.append(",\n");
                }
            }
        }
        concatenatedParams.append("]");
        // Return the concatenated string
        return concatenatedParams.toString();
    }

    @Override
    public void enterMethodDeclaration(japyParser.MethodDeclarationContext ctx) {
        Hashtable<String, String> func_table = new Hashtable<>();
        String result = concatenateParameters(ctx.getText());
        System.out.println(result);
        func_table.put("function_" + ctx.methodName.getText(), result);
        for (String key : func_table.keySet()) {
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

    public void enterClosedConditional_if(japyParser.ClosedConditionalContext ctx) {
        Hashtable<String, Hashtable> closed_if_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();
        System.out.println("if: (" + line1 + "," + line2 + ")");
//        if (ctx.ifStat.s5 != null){
//            enterStatementAssignment(ctx.ifStat.s5);
//        }
//        if (ctx.ifStat.s7 != null) {
//            for (int i = 0; i < ctx.ifStat.s7.ID().size(); i++) {
//                enterStatementVarDef(ctx.ifStat.s7);
//            }
//        }
//        if (ctx.ifStat.incStat != null){
//            enterStatementInc(ctx.ifStat.incStat);
//        }
//        if (ctx.ifStat.decStat != null){
//            enterStatementDec(ctx.ifStat.decStat);
//        }
    }

    public static void concatenateElifConditions(String conditionalStatement, japyParser.ClosedConditionalContext ctx) {
        Hashtable<String, String> elif_table = new Hashtable<>();
//        Token token1 = ctx.getStart();
//        Token token2 = ctx.getStop();
//        int line1 = token1.getLine();
//        int line2 = token2.getLine();
//        System.out.println("elif: (" + line1 + "," + line2 + ")");
        // Regular expression to match the 'elif' statements
        String elifPattern = "elif\\s*\\((.*?)\\)";

        // Compile the pattern
        Pattern pattern = Pattern.compile(elifPattern);

        // Match the pattern with the conditional statement
        Matcher matcher = pattern.matcher(conditionalStatement);

        // Initialize a StringBuilder to concatenate elif conditions
        StringBuilder concatenatedElifStatements = new StringBuilder();

        // Count the number of 'elif' statements
        int count = 0;
        while (matcher.find()) {
            count++;
            Token token1 = ctx.getStart();
            Token token2 = ctx.getStop();
            int line1 = token1.getLine();
            int line2 = token2.getLine();
            System.out.println("elif: (" + line1 + "," + line2 + ")");
            String elifStatement = matcher.group(1).trim();
            String str2 = "(name: " + elifStatement + ")(first appearance: " + line1 + ")";
            concatenatedElifStatements.append(str2).append("\n");
            elif_table.put("var_" + elifStatement, str2);
            for (String key : elif_table.keySet()) {
                System.out.println("key = " + key + ", value =" + elif_table.get(key));
            }
        }
        // Print the count (optional, to verify the count)
        System.out.println("Number of 'elif' statements: " + count);

//        return concatenatedElifStatements.toString();
    }

    public void enterClosedConditional_elif(japyParser.ClosedConditionalContext ctx) {
//        System.out.println("elifs"+ctx.expression().size());

//        if (ctx.elifExp != null) {
////            concatenateElifConditions(ctx.getText(), ctx);
//            Hashtable<String, Hashtable> elif_table = new Hashtable<>();
////            for (int i = 0; i <ctx.expression().size()-1; i++) {
////                Token token1 = ctx.getStart();
////                Token token2 = ctx.getStop();
////                int line1 = token1.getLine();
////                int line2 = token2.getLine();
////                System.out.println("elif: (" + line1 + "," + line2 + ")");
//            if (ctx.elifStat.s5 != null){
//                enterStatementAssignment(ctx.elifStat.s5);
//            }
//            if (ctx.elifStat.s7 != null) {
//                for (int i = 0; i < ctx.elifStat.s7.ID().size(); i++) {
//                    enterStatementVarDef(ctx.elifStat.s7);
//                }
//            }
//            if (ctx.elifStat.incStat != null){
//                enterStatementInc(ctx.elifStat.incStat);
//            }
//            if (ctx.elifStat.decStat != null){
//                enterStatementDec(ctx.elifStat.decStat);
//            }
//        }
    }

    public void enterClosedConditional_else(japyParser.ClosedConditionalContext ctx){
        Hashtable<String, Hashtable> else_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();
        System.out.println("else: (" + line1 + "," + line2 + ")");
//        if (ctx.elseStmt.s5 != null){
//            enterStatementAssignment(ctx.elseStmt.s5);
//        }
//        if (ctx.elseStmt.s7 != null){
//            for (int i = 0; i <ctx.elseStmt.s7.ID().size(); i++) {
//                enterStatementVarDef(ctx.elseStmt.s7);
//            }
//        }
//        if (ctx.elseStmt.incStat != null){
//            enterStatementInc(ctx.elseStmt.incStat);
//        }
//        if (ctx.elseStmt.decStat != null){
//            enterStatementDec(ctx.elseStmt.decStat);
//        }
//        for (String key: else_table.keySet()) {
//            System.out.println("key = " + key + ", value =" + else_table.get(key));
//        }
    }

    @Override
    public void enterClosedConditional(japyParser.ClosedConditionalContext ctx) {
        enterClosedConditional_if(ctx);
//        enterStatement(ctx.ifStat.s1.s);
        enterClosedConditional_elif(ctx);
//        enterStatement(ctx.elifStat.s1.s);
        enterClosedConditional_else(ctx);
//        enterStatement(ctx.elseStmt.s1.s);

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
            String str2 = "(name: " + ctx.ifStat.getText() + ")(first appearance: " + line1 +")";
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
    public void enterOpenConditional_if(japyParser.OpenConditionalContext ctx){
        Hashtable<String, String> open_if_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();
        System.out.println("if: (" + line1 + "," + line2 + ")");
//        if (ctx.ifStat.s1.s5 != null){
//            enterStatementAssignment(ctx.ifStat.s1.s5);
//        }
//        if (ctx.ifStat.s1.s7 != null) {
//            for (int i = 0; i < ctx.ifStat.s1.s7.ID().size(); i++) {
//                enterStatementVarDef(ctx.ifStat.s1.s7);
//            }
//        }
//        if (ctx.ifStat.s1.incStat != null){
//            enterStatementInc(ctx.ifStat.s1.incStat);
//        }
//        if (ctx.ifStat.s1.decStat != null){
//            enterStatementDec(ctx.ifStat.s1.decStat);
//        }
//        if (ctx.ifStat.s2.s1 != null){
//            enterStatementOpenLoop(ctx.ifStat.s2.s1);
//        }
//        if (ctx.ifStat.s2.conditionalStat != null){
//            enterOpenConditional(ctx.ifStat.s2.conditionalStat);
//        }
//        for (String key : open_if_table.keySet()) {
//            System.out.println("key = " + key + ", value =" + open_if_table.get(key));
//        }

//        String str = "<if condition: <" + ctx.ifExp.getText() + "> ";
//        System.out.println(str);
    }
    public void enterOpenConditional_if_elif(japyParser.OpenConditionalContext ctx) {
        Hashtable<String, String> open_if_elif_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();
        System.out.println("if: (" + line1 + "," + line2 + ")");
//        if (ctx.secondIfStat.s5 != null){
//            enterStatementAssignment(ctx.secondIfStat.s5);
//        }
//        if (ctx.secondIfStat.s7 != null) {
//            for (int i = 0; i < ctx.secondIfStat.s7.ID().size(); i++) {
//                enterStatementVarDef(ctx.secondIfStat.s7);
//            }
//        }
//        if (ctx.secondIfStat.incStat != null){
//            enterStatementInc(ctx.secondIfStat.incStat);
//        }
//        if (ctx.secondIfStat.decStat != null){
//            enterStatementDec(ctx.secondIfStat.decStat);
//        }
////        for (String key : open_if_table.keySet()) {
////            System.out.println("key = " + key + ", value =" + open_if_table.get(key));
////        }
//
////        String str = "<if condition: <" + ctx.ifExp.getText() + "> ";
////        System.out.println(str);
//        if (ctx.elifExp != null) {
//            concatenateElifConditions(ctx.getText(), ctx);
////            enterClosedConditional_elif(ctx);
////            System.out.println(str2);
////            Token token1 = ctx.getStart();
////            Token token2 = ctx.getStop();
////            int line1 = token1.getLine();
////            int line2 = token2.getLine();
////            System.out.println("elif: (" + line1 + "," + line2 + ")");
//
//        }
//        Token token3 = ctx.getStart();
//        Token token4 = ctx.getStop();
//        int line3 = token3.getLine();
//        int line4 = token4.getLine();
//        System.out.println("if: (" + line3 + "," + line4 + ")");
//        if (ctx.lastElifStmt.s1.s5 != null){
//            enterStatementAssignment(ctx.ifStat.s1.s5);
//        }
//        if (ctx.lastElifStmt.s1.s7 != null) {
//            for (int i = 0; i < ctx.lastElifStmt.s1.s7.ID().size(); i++) {
//                enterStatementVarDef(ctx.lastElifStmt.s1.s7);
//            }
//        }
//        if (ctx.lastElifStmt.s1.incStat != null){
//            enterStatementInc(ctx.lastElifStmt.s1.incStat);
//        }
//        if (ctx.lastElifStmt.s1.decStat != null){
//            enterStatementDec(ctx.lastElifStmt.s1.decStat);
//        }
//        if (ctx.lastElifStmt.s2.s1 != null){
//            enterStatementOpenLoop(ctx.lastElifStmt.s2.s1);
//        }
//        if (ctx.lastElifStmt.s2.conditionalStat != null){
//            enterOpenConditional(ctx.lastElifStmt.s2.conditionalStat);
//        }

    }
    public void enterOpenConditional_else(japyParser.OpenConditionalContext ctx){
        Hashtable<String, String> else_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();
        System.out.println("if: (" + line1 + "," + line2 + ")");
//        if (ctx.thirdIfStat.s5 != null){
//            enterStatementAssignment(ctx.thirdIfStat.s5);
//        }
//        if (ctx.thirdIfStat.s7 != null) {
//            for (int i = 0; i < ctx.thirdIfStat.s7.ID().size(); i++) {
//                enterStatementVarDef(ctx.thirdIfStat.s7);
//            }
//        }
//        if (ctx.thirdIfStat.incStat != null){
//            enterStatementInc(ctx.thirdIfStat.incStat);
//        }
//        if (ctx.thirdIfStat.decStat != null){
//            enterStatementDec(ctx.thirdIfStat.decStat);
//        }
//        Token token3 = ctx.getStart();
//        Token token4 = ctx.getStop();
//        int line3 = token3.getLine();
//        int line4 = token4.getLine();
//        System.out.println("else: (" + line3 + "," + line4 + ")");
//        String str = "";
//        str = str.concat("(name: " + ctx.elifStat.getText() + ")(first appearance: " + line1 +")");
//        else_table.put("var_" + ctx.elifStat.getText(), str);
//        for (String key: else_table.keySet()) {
//            System.out.println("key = " + key + ", value =" + else_table.get(key));
//        }
//        if (ctx.elifExp != null) {
//            concatenateElifConditions(ctx.getText(), ctx);
////            enterClosedConditional_elif(ctx);
////            System.out.println(str2);
////            Token token1 = ctx.getStart();
////            Token token2 = ctx.getStop();
////            int line1 = token1.getLine();
////            int line2 = token2.getLine();
////            System.out.println("elif: (" + line1 + "," + line2 + ")");
//
//        }
//        if (ctx.elseStmt.s1 != null){
//            enterStatementOpenLoop(ctx.elseStmt.s1);
//        }
//        if (ctx.elseStmt.conditionalStat != null){
//            enterOpenConditional(ctx.elseStmt.conditionalStat);
//        }
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
        Hashtable<String, String> var_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        int line1 = token1.getLine();

        for (int i = 0; i < ctx.ID().size(); i++) {
            String str = "";
            String id = ctx.ID(i).getText();
            str = str.concat("(name: " + id + ")(first appearance: " + (line1) +")");
            var_table.put("var_" + id , str);

        }
        for (String key: var_table.keySet()) {
            System.out.println("key = " + key + ", value =" + var_table.get(key));
        }
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
        Hashtable<String, String> while_table = new Hashtable<>();
        System.out.println("!NO KEY FOUND!");
    }

    @Override
    public void exitStatementClosedLoop(japyParser.StatementClosedLoopContext ctx) {

    }

    @Override
    public void enterStatementOpenLoop(japyParser.StatementOpenLoopContext ctx) {
        Hashtable<String, String> while_table = new Hashtable<>();
        System.out.println(ctx.getText());
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
        Hashtable<String, String> assign_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();

//        for (int i = 0; i < ctx.ID().size(); i++) {
            String str = "";
//            String id = ctx.ID(i).getText();
            str = str.concat("(name: " + ctx.left.getText() + ")(value: "+ ctx.right.getText() + ")" +  "(first appearance: " + (line1+1) +")");
            assign_table.put("assign_" + ctx.left.getText() , str);
        for (String key: assign_table.keySet()) {
            System.out.println("key = " + key + ", value =" + assign_table.get(key));
        }

        }

    @Override
    public void exitStatementAssignment(japyParser.StatementAssignmentContext ctx) {

    }

    @Override
    public void enterStatementInc(japyParser.StatementIncContext ctx) {
        Hashtable<String, String> inc_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();

//        for (int i = 0; i < ctx.ID().size(); i++) {
            String str = "";
//            String id = ctx.ID(i).getText();
            str = str.concat("(name: " + ctx.lvalExpr.getText() + ")(first appearance: " + line1 +")");
            inc_table.put("Inc_" + ctx.lvalExpr.getText() , str);
            inc_table.put("Inc_" + ctx.lvalExpr.getText() , str);

//        }
        for (String key: inc_table.keySet()) {
            System.out.println("key = " + key + ", value =" + inc_table.get(key));
        }
    }

    @Override
    public void exitStatementInc(japyParser.StatementIncContext ctx) {

    }

    @Override
    public void enterStatementDec(japyParser.StatementDecContext ctx) {
        Hashtable<String, String> dec_table = new Hashtable<>();
        Token token1 = ctx.getStart();
        Token token2 = ctx.getStop();
        int line1 = token1.getLine();
        int line2 = token2.getLine();

//        for (int i = 0; i < ctx.ID().size(); i++) {
        String str = "";
//            String id = ctx.ID(i).getText();
        str = str.concat("(name: " + ctx.lvalExpr.getText() + ")(first appearance: " + line1 +")");
        dec_table.put("Dec_" + ctx.lvalExpr.getText() , str);

//        }
        for (String key: dec_table.keySet()) {
            System.out.println("key = " + key + ", value =" + dec_table.get(key));
        }
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
