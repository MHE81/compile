import gen.japyParser;

import java.util.ArrayList;
import java.util.List;

public class SymbolTableGraph {
    public final SymbolTable rootNode;
    public SymbolTable currentNode;

    public String getTypeOfVariable(String variableName) {
        return currentNode.getType(variableName);
    }
    public boolean containsNameInGraph(String name) {
        return containsNameInNode(rootNode, name);
    }
    public boolean containsNameInNode(SymbolTable node, String name) {
        if (node.symbolTable.containsKey(name)) {
            if (!(node.symbolTable.containsKey("Field")) && node.symbolTable.containsKey("Function")) {
                return true;
            } else if (node.symbolTable.containsKey("Field") && !(node.symbolTable.containsKey("Function"))) {
                return true;
            }
        }
        for (SymbolTable child : node.getChildren()) {
            if (containsNameInNode(child, name)) {
                if (!(containsNameInNode(child, "Field")) && containsNameInNode(child, "Function")) {
                    return true;
                } else if (containsNameInNode(child, "Field") && !(containsNameInNode(child, "Function"))) {
                    return true;
                }
            }
        }
        return false;
    }

    public String evaluateExpressionType(japyParser.ExpressionContext ctx) {
        if (ctx.e.ot != null) {
            return "bool"; // assuming || returns a bool
        } else if (ctx.e.a.at != null) {
            return "bool"; // assuming && returns a bool
        } else if (ctx.e.a.e !=null) {
            return "bool"; // assuming == and <> return a bool
        } else if (ctx.e.a.e.c.ct != null) {
            return "bool"; // assuming < and > return a bool
        } else if (ctx.e.a.e.c.a.at != null) {
            // assuming + and - return the type of their operands
            return "double";
        } else if (ctx.e.a.e.c.a.m.mt != null) {
            // assuming *, /, and % return the type of their operands
            return "double";
        } else if (ctx.e.a.e.c.a.m.u != null) {
            // assuming ! returns bool, - returns the type of its operand
            return "bool";
        } else if (ctx.e.a.e.c.a.m.u.m.mt != null) {
            return evaluateExpressionType(ctx);
        } else if (ctx.e.a.e.c.a.m.u.m.o != null) {
            japyParser.ExpressionOtherContext otherCtx =ctx.e.a.e.c.a.m.u.m.o;
            if (otherCtx.CONST_NUM() != null) {
                return "double"; // assuming numbers are double
            } else if (otherCtx.CONST_STR() != null) {
                return "string"; // assuming strings are of type string
            } else if (otherCtx.ID() != null) {
                String variableName = otherCtx.ID().getText();
                if (ctx.parent instanceof japyParser.StatementReturnContext) {
                    String result = getTypeOfVariable(((japyParser.StatementReturnContext) ctx.parent).e.getText());
                    return result;
                }
//                SymbolTableEntry entry = lookup(variableName);
//                if (entry instanceof VariableEntry) {
//                    return ((VariableEntry) entry).type;
//                } else {
//                    return "unknown";
//                }
            } else if (otherCtx.selfModifier != null) {
                return "self"; // assuming self has a specific type
            } else if (otherCtx.trueModifier != null || otherCtx.falseModifier != null) {
                return "bool";
            }
        }
        return "unknown";
    }

    public SymbolTableEntry lookup(String name) {
        SymbolTable current = currentNode;
        while (current != null) {
            if (current.symbolTable.containsKey(name)) {
                return current.symbolTable.get(name);
            }
            current = current.getParent();
        }
        return null;
    }
    public SymbolTableGraph(japyParser.ProgramContext ctx) {
        rootNode = new SymbolTable("program", null, ctx.getStart().getLine(),ctx.getStop().getLine() );
        currentNode = rootNode;
    }

    public void addEntry(String key, String value) {
        SymbolTableEntry entry = new SymbolTableEntry(key, value);
        currentNode.symbolTable.put(key, entry);
    }

    public void enterBlock(String name, int start_line,int stop_line) {
        currentNode = currentNode.addChild(new SymbolTable(name, currentNode, start_line, stop_line));
    }

    public void exitBlock() {
        currentNode = currentNode.getParent();
    }

    public void printSymbolTable() {
        printNode(rootNode);
    }

    private void printNode(SymbolTable node) {
        node.print();
        for (SymbolTable child : node.getChildren()) {
            printNode(child);
        }
    }
    public SymbolTable getCurrentNode(){
        return currentNode;
    }

    public String getCurentNodeName(){
        return this.currentNode.name;
    }
}
