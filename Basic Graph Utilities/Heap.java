import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Heap<T extends Comparable>{

    public Node<T> head, tail;
    public T inf;

    public void setINF(T i){inf = i;}

    Heap(){ }

    T findMin(){
        Node<T> x = head, y;
        T min = inf;
        while(x != null){
            if(min.compareTo(x.key) > 0)
                min = x.key;
            x = x.sibling;
        }
        return min;
    }

    void addTree(Node<T> node){
        if(head == null){
            head = node;
            tail = node;
        }
        else{
            tail.sibling = node;
            tail = node;
        }
    }

    void addTreeBeg(Node<T> node){
        // System.out.println("NodeKey :" + node.key);
        if(head == null){
            head = node;
            tail = node;
            node.sibling = null;
        }
        else{
            node.sibling = head;
            head = node;
        }
    }

    static<Q extends Comparable> Node<Q> binomialLink(Node<Q> z, Node<Q> y){
        /*
            Links two nodes of same height
        */
        if(y.compareTo(z) > 0) { // y is the one with the smaller key
            Node<Q> temp = y;
            y = z;
            z = temp;
        }
        z.sibling = y.child;
        z.parent = y;
        y.child = z;
        ++y.degree;
        return y;
    }

    static<Q extends Comparable> Heap<Q> merge(Heap<Q> a, Heap<Q> b){
        Heap<Q> list = new Heap<>();
        Node<Q> p = a.head, q = b.head;
        Node<Q> it = list.head;
        while(p != null && q != null){
            if(p.degree < q.degree) {
                list.addTree(p);
                p = p.sibling;
            }
            else {
                list.addTree(q);
                q = q.sibling;
            }
        }

        while(p != null) {
            list.addTree(p);
            p = p.sibling;
        }

        while(q != null){
            list.addTree(q);
            q = q.sibling;
        }
        return list;
    }

    T extractMin(){
        Node<T> x = head, y, minNode = null, prev = null, minNodePrev = null;
        T min = inf;
        /*
            find the reference to the min node
         */
        while(x != null){
            if(min.compareTo(x.key) > 0) {
                min = x.key;
                minNode = x;
                minNodePrev = prev;
            }
            prev = x;
            x = x.sibling;
        }
        if(minNodePrev == null)
            head = minNode.sibling;
        else
            minNodePrev.sibling = minNode.sibling;
        Heap<T> h2 = new Heap<>();
        x = minNode.child;
        if(x==null)
            return min;
        while(x != null){
            Node<T> temp = x.sibling;
            x.parent = null;
            h2.addTreeBeg(x); // add x in the beginning of the new heap
            x = temp;
        }
        union(h2);
        return min;
    }

    void union(Heap<T> b){
        if(b == null)
            return;

        Heap<T> h = merge(this, b);
        // uniting two empty heaps
        if(h.head == null)
            return;
        Node<T> prev = null, x = h.head;
        Node<T> next = x.sibling;
        /*
            x : node currently examined
            prev : sibling[prev] = x  -----> <= x
            next: sibling[x] = next  ----> >= x
         */
        while(next != null){

            // Case 1 : two nodes of unequal degree
            // Case 2 : three nodes of same degree (x, next, sibling[next])
            if((x.degree != next.degree) ||
                    ((next.sibling != null) && (x.degree == next.sibling.degree))) {
                // we can just propagate one step further
                x.sibling = next; // why this???
                prev = x;
                x = next;
            }
            else{
                // x is of same degree as next
                // Case 3 : x has a smaller key than next
                if(x.compareTo(next) <= 0){
                    x.sibling = next.sibling;
                    binomialLink(next, x);
                }
                else{
                    if(prev == null)
                        h.head = next;
                    else
                        prev.sibling = next;
                    binomialLink(x, next);
                    x = next;
                }
            }
            next = x.sibling;
        }

        head = h.head;
    }

    void insert(T x){
        Heap<T> unit = new Heap<>();
        unit.head = new Node(x);
        union(unit);
        //printHeads();
    }

    void printHeads(){
        System.out.println("Printing Binomial Heap ... ");
        Node<T> x = head;
        while(x != null){
            x.print();
            x = x.sibling;
        }
    }

    <Q extends Iterable<T>> void multipleInsert(Q xs){
        Heap<T> mul = new Heap<>();
        for(T x:xs)
            mul.insert(x);
        union(mul);
    }

    public static void main(String ... args) throws Exception{

        System.setIn(new FileInputStream("in.txt"));
        System.setOut(new PrintStream("out.txt"));

        Heap<Integer> heap = new Heap<>();
        ArrayList<Integer> upd = new ArrayList<>();
        heap.setINF(Integer.MAX_VALUE);
        Scanner scanner = new Scanner(System.in);
        int i;
        char c;

        while(scanner.hasNext()){
            c = scanner.next().charAt(0);
            switch (c){
                case 'I':
                    i = scanner.nextInt();
                    heap.insert(i);
                    break;
                case 'P':
                    heap.printHeads();
                    break;
                case 'F':
                    i = heap.findMin();
                    System.out.println("Find-Min returned " + i);
                    break;
                case 'E':
                    i = heap.extractMin();
                    System.out.println("Extract-Min returned " + i);
                    break;
                case 'U':
                    upd.clear();
                    while(scanner.hasNextInt())
                        upd.add(scanner.nextInt());
                    heap.multipleInsert(upd);
                    break;
                default:
                    System.out.println("Default reached with" + c);
            }
        }
    }

}
