import gen.japyParser;

import java.util.ArrayList;
import java.util.List;

public class SymbolTableGraph {
    private final SymbolTable rootNode;
    private SymbolTable currentNode;

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
