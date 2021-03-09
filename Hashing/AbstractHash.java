import java.util.Scanner;
import java.util.function.BiFunction;

public abstract class AbstractHash {

    int m;
    int col = 0;// no of collisions
    int row = 0;
    int tot = 0;
    BiFunction<String, Integer, Integer> hm;

    abstract int insert(String x, int val);

    abstract Integer search(String x);

    abstract boolean delete(String x);

    abstract float avg_probes();


    public static void main(String... args) {

        //reportGeneration();

        Scanner scanner = new Scanner(System.in);

        int num, c, i = 1;
        boolean flag = true;
        Integer val;
        String x;

        System.out.println("Collision Resolution Methods\n1. Chaining\n2. Double Hashing" +
                "\n3. Custom (Quadratic) Probing");
        c = scanner.nextInt();

        System.out.println("Size of Hash Table: ");
        num = scanner.nextInt();


        AbstractHash chaining = (c == 3) ? new CustomProbing(num, Functions::iota) :
                (c == 1) ? new Chaining(num, Functions::iota) :
                        new DoubleHashing(num, Functions::iota, Functions::zeta);


        while (flag) {
            System.out.println("1. Insert 2. Find 3. Delete 4. Terminate");
            c = scanner.nextInt();
            try {
                switch (c) {
                    case 1:
                        x = scanner.next();
                        try {
                            if (chaining.insert(x, i) != -1) {
                                System.out.println("Inserted");
                                ++i;
                            } else
                                System.out.println("Not Inserted");
                        } catch (StackOverflowError error) {
                            System.out.println("Could not be inserted for overflow");
                        }
                        break;
                    case 2:
                        x = scanner.next();
                        val = chaining.search(x);
                        if (val == null)
                            System.out.println("Not found");
                        else
                            System.out.println("Found with value: " + val);
                        break;
                    case 3:
                        x = scanner.next();
                        if (chaining.delete(x))
                            System.out.println("Successfully deleted");
                        else
                            System.out.println("Not deleted");
                        break;
                    case 4:
                        flag = false;
                }
            } catch (Exception e) {
                System.out.println("EXCEPTION\t" + e);
            }
        }
    }

    static void reportGeneration() {
        BiFunction<String, Integer, Integer>[] functions = new BiFunction[3];
        RandomWordGenerator gen = new RandomWordGenerator();

        functions[0] = Functions::iota;
        functions[1] = Functions::zeta;
        functions[2] = Functions::beta;

        AbstractHash hash;
        for (int r = 0, p, i; r < 3; ++r) {
            switch (r) {
                case 0:
                    System.out.print("Chaining Method & ");
                    break;
                case 1:
                    System.out.print("Double Hashing Method  & ");
                    break;
                case 2:
                    System.out.print("Custom (Quadratic) Probing Method  & ");
            }
            for (int s = 0; s < 2; ++s) {
                i = 1;

                hash = (r == 2) ? new CustomProbing(10007, functions[s]) :
                        (r == 0) ? new Chaining(10007, functions[s]) :
                                new DoubleHashing(10007, functions[s], functions[2]);

                while (i <= 10000) {
                    String z = gen.getRandomWord();
                    try {
                        p = hash.insert(z, i);
                    } catch (StackOverflowError error) {
                        //System.out.println(i);
                        //System.out.println(error);
                        //++i;
                        //break;
                        p = -1;
                    }
                    //System.out.println(p);
                    if (p == -1)
                        continue;
                    ++i;
                }

                System.out.print(hash.col + " & " + hash.avg_probes());
                if (s == 0)
                    System.out.print(" &  ");

//                System.out.println(hash.getClass());
//                System.out.println("Inserted: " + (i - 1));
//                System.out.println("Collision count: " + hash.col);
//                System.out.println("Average probes: " + hash.avg_probes() + "\n\n");

                hash = null;
            }

            System.out.println(" \\\\ \\hline");
        }


    }
}
