import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class SymbolTableEntry{
    public String key;
    public String value;
    public SymbolTableEntry(String key, String value){
        this.key = key;
        this.value = value;
    }

    public void print(){
        System.out.print(key + "\t|\t");
        System.out.println(value);
    }
}

public class SymbolTable {
    public String name;
    public SymbolTable parent;
    public int start_line = 1;
    public int stop_line;
    public Map<String, SymbolTableEntry> symbolTable;
    private List<SymbolTable> children;

    public SymbolTable(String name, SymbolTable parent){
        this.symbolTable = new LinkedHashMap<>();
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList<>();
    }
    public SymbolTable(String name, SymbolTable parent, int start_line, int stop_line){
        this.symbolTable = new LinkedHashMap<>();
        this.name = name;
        this.parent = parent;
        this.start_line = start_line;
        this.stop_line = stop_line;
        this.children = new ArrayList<>();
    }

    public void print(){
        System.out.println("-------------- " + this.name + ": (" + this.start_line + "," + this.stop_line +  ")--------------");
        if (!this.symbolTable.isEmpty()){
            for(Map.Entry<String, SymbolTableEntry> entry : this.symbolTable.entrySet()){
                entry.getValue().print();
            }
        }
        System.out.println("--------------------------------------------------------\n");

    }
    public SymbolTable addChild(SymbolTable child) {
        children.add(child);
        return child;
    }

    public SymbolTable getParent() {
        return parent;
    }
    public List<SymbolTable> getChildren() {
        return children;
    }
}
