import javafx.util.Pair;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.function.BiFunction;

public class Chaining extends AbstractHash {


    LinkedList[] table;

    Chaining(int len, BiFunction<String, Integer, Integer> ab) {
        m = len;
        table = new LinkedList[m];
        hm = ab;
    }

    int insert(String x, int val) {
        int key = hm.apply(x, m);
        //System.out.println(x + " " + key);


        if (table[key] == null) {
            table[key] = new LinkedList<Pair<String, Integer>>();
            table[key].addFirst(new Pair<String, Integer>(x, val));
            row += 1;
            tot += 1;
            return 0; // inserted without collision
        } else {
            for (Object p : table[key]) {
                Pair<String, Integer> pair = (Pair<String, Integer>) p;
                if (pair.getKey().equals(x))
                    return -1; // not inserted
            }
            //col += table[key].size();
            table[key].addFirst(new Pair<>(x, val));
            col += 1;
            tot += 1;
            return 1; // collison
        }
    }

    Integer search(String x){
        int key = hm.apply(x, m);
        if(table[key] == null) {
            return null;
        }
        for(Object op : table[key]){
            Pair<String, Integer> pair = (Pair<String, Integer>) op;
            if(pair.getKey().equals(x)){
                return pair.getValue();
            }
        }
        return null;
    }

    boolean delete(String x){
        int key = hm.apply(x, m);
        if(table[key] == null) {
            return false;
        }
        for(Object op : table[key]){
            Pair<String, Integer> pair = (Pair<String, Integer>) op;
            if(pair.getKey().equals(x)){
                table[key].remove(op);
                tot -= 1;
                return true;
            }
        }
        return false;
    }

    @Override
    float avg_probes() {
        return tot/(float) row;
    }

//    public static void main(String... args) {
//
//        Chaining ccc = new Chaining(10000, Functions::zeta);
//        RandomWordGenerator gen = new RandomWordGenerator();
//        int i, p;
//        i = 1;
//        //System.out.println(c.table[100]);
//
//        while (i <= 10000) {
//            String z = gen.getRandomWord();
//            try{
//                p = ccc.insert(z, i);
//            } catch (StackOverflowError error){
//                System.out.println(i);
//                System.out.println(error);
//                return;
//            }
//            System.out.println(p);
//            if (p == -1)
//                continue;
//            ++i;
//        }
//        System.out.println("Collision count: " + ccc.col);
//        System.out.println("Average probes: " + ccc.avg_probes());
//        System.out.println(ccc.tot + " " + ccc.row);
//    }


}
