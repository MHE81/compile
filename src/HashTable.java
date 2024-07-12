import gen.japyListener;
import gen.japyParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HashTable implements japyListener {
    private SymbolTableGraph stg;
    private static int indent = 0;
//    List<japyParser.ExpressionContext> returnStatements = new ArrayList<>();
    private static String errorMessages = null;
    private DirectedGraph graph = new DirectedGraph();
    private void InheritErrorsInCode(){
        invalidInheritance();
//        if (errorMessages != null)
//            System.err.println(errorMessages);
    }
    private void invalidInheritance(){
        List<String> cycle = graph.findAllCycleNodes();
        if (cycle.size() > 0) {
            String errorMessage = "Error410 : Invalid inheritance ";
            for(int i = 0 ; i < cycle.size() ; i++){
                errorMessage += cycle.get(i);
                if (i < cycle.size() - 1)
                    errorMessage += " -> ";
            }
            System.err.println(errorMessage);
        }
    }
//    private String changeType(String type){
//        String str = type;
//        if (str != null) str = (str.contains("number")) ? str.replace("number", "int") : str;
//        return str;
//    }
//
//    private void printTab(int tabCount){
//        for (int i=0; i<tabCount; i++)
//            System.out.print("\t");
//    }

    @Override
    public void enterProgram(japyParser.ProgramContext ctx) {
        System.out.println("\nprogram started");
        this.stg = new SymbolTableGraph(ctx);
    }

    @Override
    public void exitProgram(japyParser.ProgramContext ctx) {
        this.stg.printSymbolTable();
        InheritErrorsInCode();
    }

    @Override
    public void enterClassDeclaration(japyParser.ClassDeclarationContext ctx) {
        String accessModifier = "public";
        // create symbol entry
        String className = ctx.className.getText();
        String classNameSymbol = "class_" + className;
        String key = "key = " + classNameSymbol;
        String value = "Value = (name: " + className + ")"; // (extends: " + parent + ")";
        if (ctx.access_modifier() != null){
            accessModifier = ctx.access_modifier().getText();
        }
        value.concat("(accessModifier: " + accessModifier + ")");
        if (ctx.classParent != null){
            value.concat("(inherits: " + ctx.classParent.getText() + ")");
        }
        int start_lineNumber = ctx.getStart().getLine();
        int stop_lineNumber = ctx.getStop().getLine();
        stg.addEntry(key, value);
        // symbol table creation
        stg.enterBlock(className, start_lineNumber, stop_lineNumber);
        if (ctx.classParent != null) {
            graph.addEdge(className, ctx.classParent.getText());
        }
        indent ++;
    }

    @Override
    public void exitClassDeclaration(japyParser.ClassDeclarationContext ctx) {
//        System.out.print("}\n");
        indent -= 1;
        stg.exitBlock();
    }

    @Override
    public void enterEntryClassDeclaration(japyParser.EntryClassDeclarationContext ctx) {
        indent++;
        // Symbol table entry
        String key =  "Key = class_" + ctx.classDeclaration().className.getText();
        String value = "Value = (name: " + ctx.classDeclaration().className.getText() + ") (main)";
        stg.addEntry(key, value);

        // symbol table creation
        int start_lineNumber = ctx.getStart().getLine();
        int stop_lineNumber = ctx.getStop().getLine();
        stg.enterBlock(ctx.classDeclaration().className.getText(), start_lineNumber, stop_lineNumber);
    }

    @Override
    public void exitEntryClassDeclaration(japyParser.EntryClassDeclarationContext ctx) {
        indent--;
        // changing scope
        stg.exitBlock();
    }

    @Override
    public void enterFieldDeclaration(japyParser.FieldDeclarationContext ctx) {
        String key = "";
        String value = "";
        for (int i = 0; i < ctx.ID().size(); i++) {
            String accessModifier = "public";
            // create symbol table entry
            key = "key = Field_" + ctx.ID(i).getText();
            value = "Value = (name: " + ctx.ID(i).getText() + ")";
            if (ctx.access_modifier() != null) {
                accessModifier = ctx.access_modifier().getText();
            }
            value += "(accessModifier: " + accessModifier + ")";
            value += "(Type: " + ctx.fieldType.getText() + ")";
            stg.addEntry(key, value);
        }
        boolean repeated = stg.containsNameInGraph(ctx.fieldName.getText());
        if (!repeated) {
            String errorMessage = "Error104 : in line [" + ctx.getStart().getLine() + ":" + ctx.fieldName.getCharPositionInLine() + "], field [" + ctx.fieldName.getText() + "] has been defined already";
            System.out.println(errorMessage);
            stg.addEntry(key, value);
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
    public static String
    concatenateParameters(String methodDeclaration) {
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
        indent ++;
        // create symbol table entry and symbol table
        String accessModifier = "public";
        if(ctx.access_modifier() != null) {
            accessModifier = ctx.access_modifier().getText();
        }
        String methodName = ctx.methodName.getText();
//        miniJavaClassDetail.addMethod(stg.getCurentNodeName(), methodName , ctx.access_modifier().getText());
        String key = "Key = Function_" + methodName;
        String value = new String("Value = (name: " + methodName + ")" + "(AccessModifier: " + accessModifier + ")(Return: " + ctx.t.getText() + ")");
        value += "\n" + concatenateParameters(ctx.getText());
        stg.addEntry(key, value);
        stg.enterBlock(methodName, ctx.getStart().getLine(), ctx.getStop().getLine());
        Details.addMethod(stg.getCurentNodeName(), ctx.methodName.getText(), accessModifier);
        boolean repeated = stg.containsNameInGraph(ctx.methodName.getText());
        if (!repeated){
            String errorMessage = "Error102 : in line [" + ctx.getStart().getLine() + ":" + ctx.methodName.getCharPositionInLine() + "], method [" + ctx.methodName.getText() + "] has been defined already";
            System.out.println(errorMessage);
        }
        if (ctx.s.s1 !=null) {
            if (ctx.s.s1.s6 != null) {
                String returnn = stg.evaluateExpressionType(ctx.s.s1.s6.e);
                if (ctx.s.s1.s6.getText().contains("\"")) {
                    returnn = "string";
                }
                // Regular expression to match any alphabetic character
                Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
                Matcher matcher = pattern.matcher(ctx.s.s1.s6.e.getText());

                // Check if the string contains any alphabetic character
                boolean hasAlphabet = matcher.find();
                if (hasAlphabet && !ctx.s.s1.s6.e.getText().contains("true") && !ctx.s.s1.s6.e.getText().contains("false")){
                    returnn = stg.getTypeOfVariable(ctx.s.s1.s6.e.getText());
                }
                if (!returnn.equals(ctx.t.getText())) {
                    System.out.println("Error210 : in line [" + ctx.getStart().getLine() + ":" + ctx.methodName.getCharPositionInLine() + "],ReturnType of this method must be " + ctx.t.getText() );
                }
            }
        }
    }

    @Override
    public void exitMethodDeclaration(japyParser.MethodDeclarationContext ctx) {
        indent --;
        stg.exitBlock();
    }

    @Override
    public void enterClosedStatement(japyParser.ClosedStatementContext ctx) {
        indent++;
    }

    @Override
    public void exitClosedStatement(japyParser.ClosedStatementContext ctx) {

    }
    public void enterClosedonditional_if(japyParser.ClosedConditionalContext ctx) {
        stg.enterBlock("If", ctx.getStart().getLine(), ctx.getStop().getLine());
    }
    public void enterClosedConditional_elif(japyParser.ClosedConditionalContext ctx) {
        for (int i = 0; i < ctx.expression().size()-1; i++) {
            stg.enterBlock("Elif", ctx.getStart().getLine(), ctx.getStop().getLine());
        }
    }
    public void enterClosedConditional_else(japyParser.ClosedConditionalContext ctx) {
        stg.enterBlock("Else", ctx.getStart().getLine(), ctx.getStop().getLine());
    }
    @Override
    public void enterClosedConditional(japyParser.ClosedConditionalContext ctx) {
        enterClosedonditional_if(ctx);
        if (ctx.elifExp != null) {
            enterClosedConditional_elif(ctx);
        }
        enterClosedConditional_else(ctx);
    }
    public void exitClosedConditional_if(japyParser.ClosedConditionalContext ctx) {
        stg.exitBlock();
    }
    public void exitClosedConditional_elif(japyParser.ClosedConditionalContext ctx) {
        for (int i = 0; i < ctx.expression().size()-1; i++) {
            stg.exitBlock();
        }
    }
    public void exitClosedConditional_else(japyParser.ClosedConditionalContext ctx) {
        stg.exitBlock();
    }
    @Override
    public void exitClosedConditional(japyParser.ClosedConditionalContext ctx) {
        exitClosedConditional_if(ctx);
        if (ctx.elifExp != null) {
            exitClosedConditional_elif(ctx);
        }
//        exitClosedConditional_elif(ctx);
        exitClosedConditional_else(ctx);
    }

    public void enterOpenConditional_if1(japyParser.OpenConditionalContext ctx) {
        stg.enterBlock("If", ctx.ifStat.getStart().getLine(), ctx.getStop().getLine());
    }
    public void enterOpenConditional_if2(japyParser.OpenConditionalContext ctx) {
        stg.enterBlock("If", ctx.secondIfStat.getStart().getLine(), ctx.getStop().getLine());
    }
    public void enterOpenConditional_if3(japyParser.OpenConditionalContext ctx) {
        stg.enterBlock("If", ctx.thirdIfStat.getStart().getLine(), ctx.getStop().getLine());
    }
    public void enterOpenConditional_if_elif(japyParser.OpenConditionalContext ctx) {
        stg.enterBlock("Elif", ctx.elifStat.getStart().getLine(), ctx.getStop().getLine());
    }
    public void enterOpenConditional_lastElif(japyParser.OpenConditionalContext ctx) {
        stg.enterBlock("If", ctx.lastElifStmt.getStart().getLine(), ctx.getStop().getLine());
    }
    public void enterOpenConditional_else(japyParser.OpenConditionalContext ctx) {
        stg.enterBlock("Else", ctx.elseStmt.getStart().getLine(), ctx.getStop().getLine());
    }
    @Override
    public void enterOpenConditional(japyParser.OpenConditionalContext ctx) {
        if (ctx.ifStat != null) {
            enterOpenConditional_if1(ctx);
        }
        if (ctx.secondIfStat != null && ctx.lastElifStmt != null) {
            enterOpenConditional_if2(ctx);
            if (ctx.elifStat != null) {
                for (int i = 0; i < ctx.closedStatement().size() - 1; i++) {
//                enterOpenConditional_if_elif(ctx);

                    for (int j = 0; j < ctx.expression().size(); j++) {
//                    Token startToken = ctx.elifStat(i).getStart();
//                    int line = startToken.getLine();
                        String elifExpression = ctx.expression(i + 1).getText();
                        String elifStatement = ctx.closedStatement(i - 1).getText();
                        String str = "(expression: " + elifExpression + ") (statement: " + elifStatement + ")";
                        System.out.println(str);
//                    elif_table.put("elif_" + i, str);
                    }
                }
                enterOpenConditional_lastElif(ctx);
            }

        }
        if (ctx.thirdIfStat != null && ctx.elseStmt != null) {
            enterOpenConditional_if3(ctx);
            if (ctx.elifStat != null) {
                for (int i = 0; i <ctx.closedStatement().size()-1; i++) {
                enterOpenConditional_if_elif(ctx);
                }
            }
            enterOpenConditional_else(ctx);
        }
    }
    public void exitOpenConditional_if1(japyParser.OpenConditionalContext ctx) {
        stg.exitBlock();
    }
    public void exitOpenConditional_if2(japyParser.OpenConditionalContext ctx) {
        stg.exitBlock();
    }
    public void exitOpenConditional_if3(japyParser.OpenConditionalContext ctx) {
        stg.exitBlock();
    }
    public void exitOpenConditional_if_elif(japyParser.OpenConditionalContext ctx) {
        stg.exitBlock();
    }
    public void exitOpenConditional_else(japyParser.OpenConditionalContext ctx) {
        stg.exitBlock();
    }
    public void exitOpenConditional_lastElif(japyParser.OpenConditionalContext ctx) {
        stg.exitBlock();
    }
    @Override
    public void exitOpenConditional(japyParser.OpenConditionalContext ctx) {
        if (ctx.ifStat != null) {
            exitOpenConditional_if1(ctx);
        }
        if (ctx.secondIfStat != null && ctx.lastElifStmt != null) {
            exitOpenConditional_if2(ctx);
            if (ctx.elifStat != null) {
                for (int i = 0; i < ctx.closedStatement().size() - 1; i++) {
                exitOpenConditional_if_elif(ctx);
                }
                exitOpenConditional_lastElif(ctx);
            }

        }
        if (ctx.thirdIfStat != null && ctx.elseStmt != null) {
            exitOpenConditional_if3(ctx);
            if (ctx.elifStat != null) {
                for (int i = 0; i <ctx.closedStatement().size()-1; i++) {
                exitOpenConditional_if_elif(ctx);
                }
            }
            exitOpenConditional_else(ctx);
        }
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
        for (int i = 0; i < ctx.ID().size(); i++) {
            // create symbol table entry
            String key = "key = Var_" + ctx.ID(i).getText();
            String value = "Value = (name: " + ctx.ID(i).getText() + ")(first appearance: " + ctx.start.getLine() + ")";
            stg.addEntry(key, value);
        }
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
        stg.enterBlock("while", ctx.getStart().getLine(), ctx.getStop().getLine());
    }

    @Override
    public void exitStatementClosedLoop(japyParser.StatementClosedLoopContext ctx) {
        stg.exitBlock();

    }

    @Override
    public void enterStatementOpenLoop(japyParser.StatementOpenLoopContext ctx) {
        stg.enterBlock("while", ctx.getStart().getLine(), ctx.getStop().getLine());
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
        // create symbol table entry
        String key = "key = Assign_" + ctx.left.getText();
        String value = "Value = (name: " + ctx.left.getText() + ")(first appearance: " + ctx.start.getLine() + ")";
        stg.addEntry(key, value);

//        String leftType = stg.evaluateExpressionType(ctx.left);
//        String rightType = stg.evaluateExpressionType(ctx.right);
//        System.out.println(leftType);
//        System.out.println(rightType);
//        if (!leftType.equals(rightType)) {
//            System.err.println("Error105 : Type mismatch error: Left expression type "+ leftType + " does not match right expression type " + rightType);
//            // Or handle the error as per your application's requirements
//        }

        // Finding the type of variable 'x'
        String typeOfX = stg.getTypeOfVariable(ctx.left.getText());
        String typeOfY = stg.getTypeOfVariable(ctx.right.getText());
        if (typeOfX != null && typeOfY != null) {
            if (!typeOfX.equals(typeOfY)) {
                System.out.println("Error105 : in line [" + ctx.start.getLine() + ":" + ctx.left.getStart().getCharPositionInLine()+ "] Type mismatch error: Left expression type \""+ typeOfX + "\" does not match right expression type \"" + typeOfY +"\"");
            }
        } else if (typeOfX == null) {
            System.out.println("Error106: in line [" + ctx.start.getLine() + ":_" + "] Variable \"" + ctx.left.getText() + "\" not found or type information not available.");
        }else if (typeOfY == null) {
            System.out.println("Error106: in line [" + ctx.start.getLine() + "] Variable \"" + ctx.right.getText() + "\" not found or type information not available.");
        }

    }

    @Override
    public void exitStatementAssignment(japyParser.StatementAssignmentContext ctx) {
    }

    @Override
    public void enterStatementInc(japyParser.StatementIncContext ctx) {
        // create symbol table entry
        String key = "key = Inc_" + ctx.lvalExpr.getText();
        String value = "Value = (name: " + ctx.lvalExpr.getText() + ")(first appearance: " + ctx.start.getLine() + ")";
        stg.addEntry(key, value);
    }
    @Override
    public void exitStatementInc(japyParser.StatementIncContext ctx) {

    }

    @Override
    public void enterStatementDec(japyParser.StatementDecContext ctx) {
        // create symbol table entry
        String key = "key = Dec_" + ctx.lvalExpr.getText();
        String value = "Value = (name: " + ctx.lvalExpr.getText() + ")(first appearance: " + ctx.start.getLine() + ")";
        stg.addEntry(key, value);
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
