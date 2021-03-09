import java.util.Scanner;

public class Tree<T extends Comparable<T>> {

    Node<T> root;
    Node<T> nil;

    public Tree() {
        root = new Node<>();
        nil = new Node<>();
    }

    public boolean find(T key) {
        if (root == null || root.isNil())
            return false;
        return root.find(key);
    }

    public void insert(T key) {
        Node<T> z = new Node<T>(key, 'r');
        Node<T> y = nil;
        Node<T> x = root;

        while (!(x == null || x.isNil())) {
            y = x;
            if (z.compareTo(x) > 0)
                x = x.rt;
            else
                x = x.lt;
        }

        z.par = y;

        if (y == null || y.isNil())
            root = z;
        else if (z.compareTo(y) > 0)
            y.rt = z;
        else
            y.lt = z;

        insertFixUp(z);
    }

    void rotateLeft(Node<T> x) {
        Node<T> y = x.rt;

        x.rt = y.lt;
        if (y.lt != null)
            y.lt.par = x;

        y.par = x.par;
        if (x.par == null || x.par.isNil())
            root = y;
        else if (x == x.par.lt)
            x.par.lt = y;
        else
            x.par.rt = y;

        y.lt = x;
        x.par = y;
    }

    void rotateRight(Node<T> x) {
        Node<T> y = x.lt;

        x.lt = y.rt;
        if (y.rt != null)
            y.rt.par = x;

        y.par = x.par;
        if (x.par == null || x.par.isNil())
            root = y;
        else if (x == x.par.rt)
            x.par.rt = y;
        else
            x.par.lt = y;

        y.rt = x;
        x.par = y;
    }

    public void insertFixUp(Node z) {
        while (z != null && z.par.isRed()) {
            if (z.par == z.par.par.lt) {
                Node<T> y = z.par.par.rt;
                if (y != null && y.isRed()) {
                    z.par.setBlack();
                    y.setBlack();
                    z.par.par.setRed();
                    z = z.par.par;
                } else {
                    if (z == z.par.rt) {
                        z = z.par;
                        rotateLeft(z);
                    }
                    z.par.setBlack();
                    z.par.par.setRed();
                    rotateRight(z.par.par);
                }
            } else {
                Node<T> y = z.par.par.lt;
                if (y != null && y.isRed()) {
                    z.par.setBlack();
                    y.setBlack();
                    z.par.par.setRed();
                    z = z.par.par;
                } else {
                    if (z == z.par.lt) {
                        z = z.par;
                        rotateRight(z);
                    }
                    z.par.setBlack();
                    z.par.par.setRed();
                    rotateLeft(z.par.par);
                }
            }
        }
        root.setBlack();
    }

    void transplant(Node<T> u, Node<T> v) {
        if (u.par == null || u.par.isNil())
            root = v;
        else if (u == u.par.lt)
            u.par.lt = v;
        else
            u.par.rt = v;
        v.par = u.par;
    }

    void delete(T key) {
        Node<T> z = root.findNode(key);
        Node<T> y = z, x;
        char org = y.color;

        if (z.lt.isNil()) {
            x = z.rt;
            transplant(z, z.rt);
        } else if (z.rt.isNil()) {
            x = z.lt;
            transplant(z, z.lt);
        } else {
            y = z.rt.findMinimumNode();
            org = y.color;
            x = y.rt;
            if (y.par == z)
                x.par = y;
            else {
                transplant(y, y.rt);
                y.rt = z.rt;
                y.rt.par = y;
            }
            transplant(z, y);
            y.lt = z.lt;
            y.lt.par = y;
            y.color = z.color;
        }

        if (org == 'b')
            deleteFixUp(x);
    }

    void deleteFixUp(Node<T> x) {
        //System.out.println(x.key + " " + x.color);
        while (x != root && (x == null || !x.isRed())) {
            if (x == x.par.lt) {
                Node<T> w = x.par.rt;
                if (w.isRed()) { // sibling is red
                    w.setBlack();
                    w.par.setRed(); // interchange colors of w and p[w]
                    rotateLeft(w.par);
                    w = x.par.rt;
                }
                // w is now black
                if ((w.lt == null || !w.lt.isRed()) && (w.rt == null || !w.rt.isRed())) {
                    w.setRed();
                    x = x.par;
                    continue;
                }
                //if rt child is black, rotate right
                if (w.rt == null || !w.rt.isRed()) {
                    w.setRed();
                    w.lt.setBlack();
                    rotateRight(w);
                    w = x.par.rt;
                }

                w.color = x.par.color;
                w.par.setBlack();
                w.rt.setBlack();
                rotateLeft(x.par);
                x = root;
            } else {
                Node<T> w = x.par.lt;
                if (w.isRed()) { // sibling is red
                    w.setBlack();
                    w.par.setRed(); // interchange colors of w and p[w]
                    rotateRight(w.par);
                    w = x.par.lt;
                }
                // w is now black
                if (!w.rt.isRed() && !w.lt.isRed()) {
                    w.setRed();
                    x = x.par;
                    continue;
                }
                //if lt child is black, rotate left
                if (!w.lt.isRed()) {
                    w.setRed();
                    w.rt.setBlack();
                    rotateLeft(w);
                    w = x.par.lt;
                }

                w.color = x.par.color;
                w.par.setBlack();
                w.lt.setBlack();
                rotateRight(x.par);
                x = root;
            }
        }
        x.setBlack();
    }

    public void check() {

        boolean okay = true;

        if (root != null && root.isRed()) {
            okay = false;
            System.out.println("Root is NOT black. Property II violated.");
        }

        okay &= root.check();

        if (okay) {
            //System.out.println("All properties satisfied!");
        }

    }

    @Override
    public String toString() {
        if (root == null || root.isNil())
            return "";
        else
            return root.toString();
    }

    public static void main(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        //Scanner scanner = new Scanner(new FileReader("input4.txt"));
        char c;
        int i;

        Tree<Integer> rbt = new Tree<>();

        while (scanner.hasNext()) {
            c = scanner.next().charAt(0);
            i = scanner.nextInt();

            switch (c) {
                case 'F':
                case 'f':
                    if (rbt.find(i))
                        System.out.println("True");
                    else
                        System.out.println("False");
                    break;

                case 'I':
                case 'i':
                    rbt.insert(i);
                    System.out.println(rbt);
                    break;

                case 'D':
                case 'd':
                    rbt.delete(i);
                    System.out.println(rbt);
                    break;
            }

            rbt.check();
        }
    }
}
