import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.Hashtable;

public class test {
    public static void main(String[] args) {
        // Creating a Hashtable
        Hashtable<String, Integer> hashtable = new Hashtable<>();

        // Adding key-value pairs to the Hashtable
        hashtable.put("Apple", 1);
        hashtable.put("Banana", 2);
        hashtable.put("Cherry", 3);
        for (String key: hashtable.keySet()) {
            System.out.println("key = " + key + ", value =" + hashtable.get(key));
        }
//        for (int i = 0; i <hashtable.size(); i++) {
//
//        }
//        System.out.println(hashtable);
    }
}

