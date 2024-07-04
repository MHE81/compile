import gen.japyListener;
import gen.japyListener;
import gen.japyParser;
import java.util.Hashtable;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

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
        System.out.println("<Main class>");
    }

    @Override
    public void exitEntryClassDeclaration(japyParser.EntryClassDeclarationContext ctx) {
        System.out.println("</Main class>");
    }

    @Override
    public void enterFieldDeclaration(japyParser.FieldDeclarationContext ctx) {
        String str = ctx.fieldName.getText();
        if(ctx.ii != null){
            str = str.concat("," + ctx.ii.getText());
        }
        str = str.concat("(field");
        if(ctx.access_modifier() != null){
            str =str.concat(", "+ ctx.access_modifier().getText());
        }
        str = str.concat(", " + ctx.fieldType.getText() + ")");
        System.out.println(str);
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

//    public String multi_param(japyParser.MethodDeclarationContext ctx){
//        String str = ", (" + ctx.param2.getText() + "," + ctx.typeP2.getText() + ")";
////        if(ctx.param2 !=null) {
////            str = multi_param(ctx);
////        }
////        return str;
//
//        // Base case: if ctx.param2 is null, return an empty string
//        if (ctx.param2 == null) {
//            return "";
//        }
//
//        return str + multi_param(ctx);
//    }

    @Override
    public void enterMethodDeclaration(japyParser.MethodDeclarationContext ctx) {
        String str = "<function '" + ctx.methodName.getText()+"'";
        if(ctx.access_modifier()!= null){
            str = str.concat(","+ctx.access_modifier().getText());
        }
        str = str.concat("parameters: [(" + ctx.param1.getText() + "," + ctx.typeP1.getText() + ")");
        if(ctx.param2 !=null){
            str = str.concat(", (" + ctx.param2.getText() + "," + ctx.typeP2.getText() + ")");
//            while (ctx.param2 != null) {
//                str = str.concat(", (" + ctx.param2.getText() + "," + ctx.typeP2.getText() + ")");
//            }
        }
        System.out.println(str + "]>");
    }

    @Override
    public void exitMethodDeclaration(japyParser.MethodDeclarationContext ctx) {
        if(ctx.s != null) {
            System.out.println("</function return (" + ctx.s.getText() + "," + ctx.t.getText() + ")>");
        }
    }

    @Override
    public void enterClosedStatement(japyParser.ClosedStatementContext ctx) {
        if(ctx.s1 != null){
            System.out.println(ctx.s1.getText());
        }
    }

    @Override
    public void exitClosedStatement(japyParser.ClosedStatementContext ctx) {
    }

    @Override
    public void enterClosedConditional(japyParser.ClosedConditionalContext ctx) {
        String str = "<if condition: <" + ctx.ifExp.getText() + "> ";
//        str = str.concat();
        System.out.println(str);
//        System.out.println(ctx.ifStat.getText());
        if(ctx.elifExp != null) {
            String str2 = "<elif condition: <" + ctx.elifExp.getText() + ">";
            System.out.println(str2);
//            System.out.println(ctx.elifStat.getText());
        }
        String str3 = "<else>";
        System.out.println(str3);
//        System.out.println(ctx.elseStmt.getText());
    }

    @Override
    public void exitClosedConditional(japyParser.ClosedConditionalContext ctx) {
        System.out.println("</else>");
        System.out.println("</if>");
    }

    @Override
    public void enterOpenConditional(japyParser.OpenConditionalContext ctx) {
        String str = "<if condition: <" + ctx.ifExp.getText() + "> ";
//        str = str.concat();
        System.out.println(str);
//        System.out.println(ctx.ifStat.getText());
        if(ctx.elifExp != null) {
            String str2 = "<elif condition: <" + ctx.elifExp.getText() + ">";
            System.out.println(str2);
//            System.out.println(ctx.elifStat.getText());
        }
        if(ctx.elseStmt != null ){
            String str3 = "<else>";
            System.out.println(str3);
//            System.out.println(ctx.elseStmt.getText());
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
    }

    @Override
    public void exitStatement(japyParser.StatementContext ctx) {

    }

    @Override
    public void enterStatementVarDef(japyParser.StatementVarDefContext ctx) {
        String str = ctx.e1.getText() + "-> (" + ctx.i1.getText() + ", " + "var) ";
        if(ctx.i2 != null){
            str = str.concat("," + ctx.e2.getText() + "-> (" + ctx.i2.getText() + ", " + "var)");
        }
        System.out.println(str);
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
//        System.out.println("goto" + N);
    }

    @Override
    public void exitStatementContinue(japyParser.StatementContinueContext ctx) {
    }

    @Override
    public void enterStatementBreak(japyParser.StatementBreakContext ctx) {
//        System.out.println("goto "+ N);
    }

    @Override
    public void exitStatementBreak(japyParser.StatementBreakContext ctx) {
    }

    @Override
    public void enterStatementReturn(japyParser.StatementReturnContext ctx) {
        System.out.println(ctx.e.getText());
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

    }

    @Override
    public void exitStatementDec(japyParser.StatementDecContext ctx) {

    }

    @Override
    public void enterExpression(japyParser.ExpressionContext ctx) {
//        System.out.println(ctx.e.getText());
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
