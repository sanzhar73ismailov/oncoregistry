package kz.kazniioir.oncoregistry.examples;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        
        for (Integer integer : list) {
            if(integer == 3){
                list.remove(integer);
            }
        }
        
        for (Integer integer : list) {
            System.out.println("integer = " + integer);
        }
        
        
        
        
    }
}
