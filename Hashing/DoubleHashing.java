import javafx.util.Pair;

import java.util.function.BiFunction;

public class DoubleHashing extends AbstractHash {

    Pair<String, Integer>[] table;
    BiFunction<String, Integer, Integer> aux;
    final Pair<String, Integer> DELETED = new Pair<>("DELETED", -10);

    DoubleHashing(int len, BiFunction<String, Integer, Integer> ab,
                  BiFunction<String, Integer, Integer> auxiliary) {
        m = len;
        table = new Pair[m];
        hm = ab;
        aux = auxiliary;
    }

    public void setAux(BiFunction<String, Integer, Integer> aux) {
        this.aux = aux;
    }

    int get(int i, int h1, int h2) {
        return (m + (h1 + (i * h2) % m) % m) % m;
    }


    @Override
    int insert(String x, int val) {
        //System.out.println(x + " " + val);
        int h1 = hm.apply(x, m);
        int h2 = aux.apply(x, m);
        int i, j;

        for (i = 0; i < m; ++i) {
            j = get(i, h1, h2);
            if (table[j] == null) {
                table[j] = new Pair<>(x, val);
                col += i;
                //System.out.println(i + " " + j);
                tot += 1;
                return i; // i probes needed
            } else if (table[j].getKey().toString().equals(x)) {
                return -1; //not inserted
            }
        }
        throw new StackOverflowError("Hash Table Overflow");

    }

    Integer search(String x) {
        int h1 = hm.apply(x, m);
        int h2 = aux.apply(x, m);
        int i, j;
        for (i = 0; i < m; ++i) {
            j = get(i, h1, h2);
            if (table[j] == null) {
                return null;
            } else {
                if (table[j].getKey().equals(x))
                    return (int) table[j].getValue();
            }
        }
        return null;
    }

    boolean delete(String x) {
        int h1 = hm.apply(x, m);
        int h2 = aux.apply(x, m);
        int i, j;

        // special values of j
        int p = -1, q = -1; // p -> last not null // q -> where matches

        for (i = 0; i < m; ++i) {
            j = get(i, h1, h2);
            if (table[j] == null) {
                break;
            } else {
                p = j;
                if (table[j].getKey().equals(x)) {
                    q = j;
                }
            }
        }
        if (q == -1)
            return false;

        table[q] = table[p];
        table[p] = null;
        tot -= 1;
        return true;
    }

    @Override
    float avg_probes() {
        return (tot+col)/(float) tot;
    }

//    public static void main(String... args) {
//
//        DoubleHashing c = new DoubleHashing(10007, Functions::iota, Functions::beta);
//        RandomWordGenerator gen = new RandomWordGenerator();
//        int i = 1, p = 0;
//        String z;
//
//        while (i <= 10000) {
//            z = gen.getRandomWord();
//            try {
//                p = c.insert(z, i);
//            } catch (StackOverflowError error) {
//                System.out.println(i);
//                System.out.println(error);
//                break;
//            }
//            //System.out.println(p);
//            if (p == -1)
//                continue;
//            ++i;
//        }
//        System.out.println("Inserted: " + c.tot);
//        System.out.println("Collision count: " + c.col);
//        System.out.println("Average probes: " + (10000 + c.col) / 10000.0);
//    }

}
/*

10007
1 dwgr
1 defbg
1 ram
2 ram
3 ram
2 ram
3 ram
2 ram
1 ram
2 ram
 */