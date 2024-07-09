import gen.japyListener;
import gen.japyParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;

public class HashTable implements japyListener {
    private SymbolTableGraph stg;
    private boolean nestedBlockForStatement = false;
    private final Stack<Boolean> nestedBlockStack = new Stack<>();
    private static int indent = 0;

    private String changeType(String type){
        String str = type;
        if (str != null) str = (str.contains("number")) ? str.replace("number", "int") : str;
        return str;
    }

    private void printTab(int tabCount){
        for (int i=0; i<tabCount; i++)
            System.out.print("\t");
    }

    @Override
    public void enterProgram(japyParser.ProgramContext ctx) {
        System.out.println("\nprogram started");
        this.stg = new SymbolTableGraph();
    }

    @Override
    public void exitProgram(japyParser.ProgramContext ctx) {
        this.stg.printSymbolTable();
    }

    private void classSymbolTableCreation (japyParser.ClassDeclarationContext ctx, String parent){
        // create symbol entry
        String className = ctx.className.getText();
        String classNameSymbol = "class_" + className;
        String key = "key = " + classNameSymbol;
        String value = "Value = Class: (name: " + className + ") (extends: " + parent + ")";

        int start_lineNumber = ctx.getStart().getLine();
        int stop_lineNumber = ctx.getStop().getLine();
        stg.addEntry(key, value);

        // symbol table creation
        stg.enterBlock(className, start_lineNumber, stop_lineNumber);
    }
    @Override
    public void enterClassDeclaration(japyParser.ClassDeclarationContext ctx) {
        String output = "class " + ctx.className.getText();
        String parent;
        int hasParent = 1;
        if(ctx.classParent != null){
            parent = ctx.classParent.getText();
            output = output.concat(" extends " + ctx.classParent);
            hasParent ++;
            classSymbolTableCreation(ctx, parent);
        }
        else {
            classSymbolTableCreation(ctx, null);
        }
//        String stringToConcat = "";
//        if(ctx.getText().contains("implements")){
//            for (int i = hasParent; i < ctx.Identifier().size(); i++) {
//                if (i == ctx.Identifier().size() -1)
//                    stringToConcat = stringToConcat.concat(ctx.Identifier(i).getText());
//                else
//                    stringToConcat = stringToConcat.concat(ctx.Identifier(i).getText() + ", ");
//            }
//            output = output.concat(" implements " + stringToConcat);
//        }
        indent ++;
//        System.out.println(output + " {\n");
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
        String key =  "Key = MainClass_" + ctx.classDeclaration().className.getText();
        String value = "MainClass: (name: " + ctx.classDeclaration().className.getText() + ")";
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
        // convert to java
        String output = "";
        if(ctx.access_modifier() != null){
            output = output.concat(ctx.access_modifier().getText() + " ");
        }
        output = output.concat(changeType(ctx.fieldType.getText()) + " " + ctx.fieldName.getText() + " ");
//        System.out.print(output);

        // create symbol table entry
        String key = "key = var_" + ctx.fieldName.getText();
        String value = "Value = Field: (name: " + ctx.fieldName.getText() + ")";
//        if(ctx.fieldType.Identifier() != null){
//            value += "[ classType: " + ctx.type().Identifier().getText() + " ])";
//        }
//        else {
//            value += ctx.type().javaType().getText() + ")";
//        }
        if(ctx.access_modifier() != null){
            value += " (accessModifier: " + ctx.access_modifier().getText() + ")";
        }
        stg.addEntry(key, value);
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

    private void createMethodEntry(japyParser.MethodDeclarationContext ctx){
        String accessModifier = "public";
        if(ctx.access_modifier() != null)
            accessModifier = ctx.access_modifier().getText();

        String methodName = ctx.methodName.getText();
//        miniJavaClassDetail.addMethod(stg.getCurentNodeName(), methodName , ctx.access_modifier().getText());
        String key = "key = method_" + methodName;
        StringBuilder value = new StringBuilder("Value = Method: (name: " + methodName + ")" + "(returnType: " + ctx.typeP1.getText()+ ") (accessModifier: ACCESS_MODIFIER_" + accessModifier);

        if(ctx.param2 != null){
            int i = 0;
            int paramCount = ctx.ID().size();
            value.append(" (parametersType: ");
            for (;i < paramCount; i ++) {
                if (ctx.ID(i) != null) {
                    value.append("[").append(ctx.ID(i).getText()).append(", ").append("index: ").append(i + 1).append("]");
                }
//                else {
//                    value.append("[ classType = ").append(ctx.ID(i).Identifier().getText()).append(", ").append("index: ").append(i + 1).append("]");
//                }
            }
        }
        value.append(")");
        stg.addEntry(key, value.toString());
        stg.enterBlock(methodName, ctx.getStart().getLine(), ctx.getStop().getLine());
    }
    @Override
    public void enterMethodDeclaration(japyParser.MethodDeclarationContext ctx) {
        String output = "\t";
        if (ctx.access_modifier() != null) {
            output = output.concat(ctx.access_modifier().getText() + " ");
        }
        if (ctx.t != null) {
            output = output.concat(changeType(ctx.t.getText()) + " ");
        }

        output = output.concat(ctx.methodName.getText() + " (");
        if (ctx.param1 != null){
            for (int i = 0; i < ctx.ID().size(); i++) {
                if(!(i == ctx.ID().size() - 1)) {
                    output = output.concat(ctx.ID().get(i).getText() + " " + ctx.ID().get(i) + ", ");
                }
                else {
                    output = output.concat(ctx.ID().get(i).getText() + " " + ctx.ID().get(i) + " ) {\n");
                }
            }
        }else {
            output = output.concat(") {\n");

        }
//        System.out.println(output);
        indent ++;

        // create symbol table entry and symbol table
        createMethodEntry(ctx);
    }

    @Override
    public void exitMethodDeclaration(japyParser.MethodDeclarationContext ctx) {
        indent --;
//        if (ctx.methodBody().RETURN() != null)
//            System.out.println("\t\treturn " + expressionHandler(ctx.methodBody().expression()) + ";");
//        System.out.print("\t}\n");

        stg.exitBlock();
    }

    @Override
    public void enterClosedStatement(japyParser.ClosedStatementContext ctx) {
        indent++;
    }

    @Override
    public void exitClosedStatement(japyParser.ClosedStatementContext ctx) {

    }

    @Override
    public void enterClosedConditional(japyParser.ClosedConditionalContext ctx) {

    }

    @Override
    public void exitClosedConditional(japyParser.ClosedConditionalContext ctx) {

    }

    @Override
    public void enterOpenConditional(japyParser.OpenConditionalContext ctx) {

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
