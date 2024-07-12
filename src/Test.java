import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        // ایجاد یک HashMap
        HashMap<String, String> symbolTable = new HashMap<>();

        // اضافه کردن چند مقدار به HashMap
        symbolTable.put("Field_name", "value1");
        symbolTable.put("Function_name", "value2");
        symbolTable.put("Field_age", "value3");

        // بررسی وجود کلیدهای مختلف با استفاده از containsKey
        String keyToCheck1 = "Field_name";
        String keyToCheck2 = "Field_address";

        if (symbolTable.containsKey(keyToCheck1)) {
            System.out.println("کلید '" + keyToCheck1 + "' وجود دارد و مقدار آن: " + symbolTable.get(keyToCheck1));
        } else {
            System.out.println("کلید '" + keyToCheck1 + "' وجود ندارد.");
        }

        if (symbolTable.containsKey(keyToCheck2)) {
            System.out.println("کلید '" + keyToCheck2 + "' وجود دارد و مقدار آن: " + symbolTable.get(keyToCheck2));
        } else {
            System.out.println("کلید '" + keyToCheck2 + "' وجود ندارد.");
        }
    }
}
