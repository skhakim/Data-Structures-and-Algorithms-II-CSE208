import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import javax.crypto.spec.PSource;
import java.util.function.Function;

public class Node<T extends Comparable> implements Comparable {

    Node<T> parent, child, sibling;
    // child stores the left-most child
    // sibling stores the immediate right sibling

    T key;
    int degree;
    String[] info;

    public Node() { degree = 0; }

    public Node(T k){
        degree = 0;
        key = k;
    }

    public int no(){
        if(sibling == null)
            return 1;
        else
            return 1 + sibling.no();
    }


    void print(){
        if(key == null)
            return;
        System.out.println("Binomial Tree, B" + degree);
        int level = 0;
        info = new String[degree+1];
        for(int i=0; i<=degree; ++i)
            info[i] = "";
        printUtil(info, 0);
        for(int i=0; i<=degree; ++i)
            System.out.println("Level " + i + ": " + info[i]);
    }

    public void printUtil(String[] info, int level){
        if(degree == 0)
            info[level] += " " + key;
        else{
            Node<T> x = child;
            info[level] += " " + key;
            while(x != null){
                x.printUtil(info, level+1);
                x = x.sibling;
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        return key.compareTo(((Node<T>) o).key);
    }
}


