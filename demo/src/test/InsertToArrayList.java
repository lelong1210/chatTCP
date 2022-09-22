package test;

import java.util.ArrayList;

public class InsertToArrayList {
 
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<String>();
         
        names.add("Java");
        names.add("Kotlin");
        names.add("Android");
        names.add("Python"); 
        names.remove(names.indexOf("Python"));
        names.add(0, "python");
         
        names.forEach(name -> {
            System.out.println(name);
        });
    }
}