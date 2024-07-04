import java.util.Hashtable;

public class test {
    public static void main(String[] args) {
        // Creating a Hashtable
        Hashtable<String, Integer> hashtable = new Hashtable<>();

        // Adding key-value pairs to the Hashtable
        hashtable.put("Apple", 1);
        hashtable.put("Banana", 2);
        hashtable.put("Cherry", 3);

        System.out.println(hashtable);
        // Accessing elements from the Hashtable
        System.out.println("Value for Apple: " + hashtable.get("Apple"));
        System.out.println("Value for Banana: " + hashtable.get("Banana"));

        // Checking if a key exists
        if (hashtable.containsKey("Cherry")) {
            System.out.println("Hashtable contains key 'Cherry'");
        }

        // Iterating over the Hashtable
        for (String key : hashtable.keySet()) {
            System.out.println("Key: " + key + ", Value: " + hashtable.get(key));
        }

        // Removing an element
        hashtable.remove("Banana");

        // Checking the size of the Hashtable
        System.out.println("Size of Hashtable: " + hashtable.size());
    }
}

