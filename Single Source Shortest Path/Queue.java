import java.util.NoSuchElementException;
import java.util.Scanner;

public class Queue<T> {

    private T[] arr;
    private int n;
    private int begin;
    private int end;

    public Queue(){
        arr = (T[]) new Object[4];
        n = 0;
        begin = 0;
        end = 0;
    }

    public int getSize(){
        return n;
    }

    public boolean isEmpty(){
        return (n==0);
    }

    public void enqueue(T obj){
        if(n == arr.length)
            resize(arr.length * 2);
        arr[end%arr.length] = obj;
        end = (end+1)%arr.length;
        n++;
    }

    public T peek(){
        if(n==0)
            throw new NoSuchElementException("Queue empty!");
        return arr[begin];
    }

    public T poll(){
        if(n==0)
            throw new NoSuchElementException("Queue empty!");
        T toReturn = arr[begin];
        arr[begin] = null;
        begin = (begin+1)%arr.length;
        n--;
        if(n*2 == arr.length)
            resize(arr.length/2);
        return toReturn;
    }

    protected void resize(int capacity){
        if(capacity >= n){
            T[] temporary = (T[]) new Object[capacity];
            for(int i=0; i<n; ++i)
                temporary[i] = arr[(begin+i)%arr.length];

            begin = 0;
            end = n;
            arr = temporary;
        }
        else{
            throw new AssertionError("Capacity must be greater than the no of elements");
        }
    }

    public static void main(String ... args){

        Scanner scanner = new Scanner(System.in);
        Queue<Integer> integerQueue = new Queue<>();
        while(true){

            System.out.println("1. ENQUEUE 2. FRONT 3. DEQUEUE 4. SIZE\n");
            int choice = scanner.nextInt();
            if(choice>4)
                return;
            switch(choice){
                case 1:
                    int val = scanner.nextInt();
                    integerQueue.enqueue(val);
                    break;
                case 2:
                    System.out.println(integerQueue.peek());
                    break;
                case 3:
                    System.out.println(integerQueue.poll());
                    break;
                case 4:
                    System.out.println(integerQueue.getSize());
            }
        }
    }
}
