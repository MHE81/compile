import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class MethodEntry extends SymbolTableEntry {
    public String returnType;
    public MethodEntry(String key, String value, String returnType) {
        super(key, value);
        this.returnType = returnType;
    }
    @Override
    public void print() {
        super.print();
        System.out.println("Return Type: " + returnType);
    }
}


class VariableEntry extends SymbolTableEntry {
    public String type;

    public VariableEntry(String key, String value, String type) {
        super(key, value);
        this.type = type;
    }
    @Override
    public void print() {
        super.print();
        System.out.println("Type: " + type);
    }
}

class SymbolTableEntry{
    public String key;
    public String value;
    public SymbolTableEntry(String key, String value){
        this.key = key;
        this.value = value;
    }

    public void print(){
        System.out.print(key + ", ");
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

    public String getType(String variableName) {
        SymbolTable current = this;
        while (current != null) {
            for (Map.Entry<String, SymbolTableEntry> entry : current.symbolTable.entrySet()) {
                if (entry.getKey().contains("Field_")) {
                    if (entry.getValue().value.contains(variableName)) {
                        String value = entry.getValue().value;
                        int typeStartIndex = value.indexOf("(Type: ") + "(Type: ".length();
                        int typeEndIndex = value.indexOf(")", typeStartIndex);
                        if (typeStartIndex != -1 && typeEndIndex != -1) {
                            return value.substring(typeStartIndex, typeEndIndex);
                        }
                    }
                } else if (entry.getValue().value.contains("parameters")) {
                    if (entry.getValue().value.contains(variableName)) {
                        String value = entry.getValue().value;
                        int typeStartIndex = value.indexOf("(type: ") + "(Type: ".length();
                        int typeEndIndex = value.indexOf(")", typeStartIndex);
                        if (typeStartIndex != -1 && typeEndIndex != -1) {
                            return value.substring(typeStartIndex, typeEndIndex);
                        }
                    }
                }
            }
            current = current.getParent();
        }
        return null;
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
