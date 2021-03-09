public class Node<T extends Comparable<T>> implements Comparable {

    T key;
    char color;
    Node<T> par;
    Node<T> lt;
    Node<T> rt;

    public Node() {
        key = null;
        color = 'b';
        par = null;
        lt = null;
        rt = null;
    }

    public Node(T _key, char _color) {
        key = _key;
        color = _color;
        par = new Node();
        lt = new Node();
        rt = new Node();
    }

    public boolean isNil() {
        return (key == null);
    }

    public boolean isRed() {
        return color == 'r';
    }

    public void setBlack() {
        color = 'b';
    }

    public void setRed() {
        color = 'r';
    }

    @Override
    public int compareTo(Object o) {
        return key.compareTo((T) ((Node) o).key);
    }

    public boolean find(T x) {
        int c = key.compareTo(x);
        if (c == 0)
            return true;
        else if (c < 0 && rt != null && !rt.isNil())
            return rt.find(x);
        else if (c > 0 && lt != null && !lt.isNil())
            return lt.find(x);
        else
            return false;
    }

    public Node<T> findNode(T x) {
        int c = key.compareTo(x);
        if (c == 0)
            return this;
        else if (c < 0 && rt != null && !rt.isNil())
            return rt.findNode(x);
        else if (c > 0 && lt != null && !lt.isNil())
            return lt.findNode(x);
        else
            return null;
    }

    public Node<T> findMinimumNode() {
        Node<T> z = this;
        while (!z.lt.isNil())
            z = z.lt;
        return z;
    }

    public int blackHeight() {
        if (isNil())
            return 1;
        else if (lt.isRed())
            return lt.blackHeight();
        else
            return 1 + lt.blackHeight();
    }

    public int blackHeightRightLeaning() {
        if (isNil())
            return 1;
        else if (rt.isRed())
            return rt.blackHeight();
        else
            return 1 + rt.blackHeight();
    }

    public boolean check() {

        boolean okay = true;

        if (key == null && color == 'r') {
            okay = false;
            System.out.println("A leaf is red. Property III violated.");
        }

        if (color == 'r') {
            if ((lt != null && lt.color == 'r') || (rt != null && rt.color == 'r')) {
                okay = false;
                System.out.println("Some child of a red node is red. Property IV violated.");
            }
        }

        if (blackHeight() != blackHeightRightLeaning()) {
            okay = false;
            System.out.println("Black height imbalance detected. Property V violated.");
        }

        if (!okay) {
            System.out.printf("A violation detected at key=" + key);
        }

        if (lt != null && !lt.isNil())
            okay &= lt.check();

        if (rt != null && !rt.isNil())
            okay &= rt.check();

        return okay;
    }

    @Override
    public String toString() {
        if ((lt == null || lt.isNil()) && (rt == null || rt.isNil()))
            return key.toString() + ":" + color;
        String lstring = "", rstring = "";
        if (lt != null && !lt.isNil())
            lstring = lt.toString();
        if (rt != null && !rt.isNil())
            rstring = rt.toString();
        return key.toString() + ":" + color + "(" + lstring + ")(" + rstring + ")";
    }
}
/*
I 1
I 2
I 3
I 4
I 5
I 6
D 5
F 5
I 7
I 8
I 9
D 1
I 1
F 1
D 2
 */