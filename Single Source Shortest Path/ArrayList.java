import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class ArrayList<T> implements Iterable<T> {

    private T[] array;
    int end;

    public ArrayList(int capacity) {
        int val = (int) (Math.pow(2.000, (Math.ceil((Math.log(capacity)) / Math.log(2)) + .01)) + .01);
        array = (T[]) new Object[capacity];
        end = 0;
    }

    public ArrayList() {
        this(16);
    }

    protected void resize(int capacity) {
        assert capacity >= end;
        array = Arrays.copyOf(array, capacity);
    }

    public int getLength() {
        return end;
    }

    public int getCapacity() {
        return array.length;
    }

    public boolean isEmpty() {
        return (end == 0);
    }

    public void insertItem(T val) {
        array[end++] = val;
        if (end == array.length)
            resize(2 * array.length);
    }

    @NotNull
    public void insertItems(T... values) {
        for (T val : values) {
            insertItem(val);
        }
    }

    public int searchItem(T val) {
        for (int i = 0; i < end; ++i) {
            if (array[i] == val)
                return i;
        }
        return -1;
    }

    public T getItem(int idx) {
        return array[idx];
    }

    public void removeItemAt(int idx) {
        for (int i = idx; i < (end - 1); ++i)
            array[i] = array[i + 1];
        array[end] = null;
        --end;
        if (end * 2 == array.length)
            resize(array.length / 2);
    }

    public boolean removeFirstInstanceOfItem(T item) {
        int idx = searchItem(item);
        if (idx == -1)
            return false; // not removed
        else
            removeItemAt(idx);
        return true;
    }

    public void removeAllInstancesOfItem(T item) {
        while (removeFirstInstanceOfItem(item)) ;
    }

    public void printElements() {
        for (int i = 0; i < end; ++i)
            System.out.print(array[i] + ", ");
        System.out.println();
    }

    @Override
    public String toString() {
        return "ArrayList{\n" +
                "array=" + Arrays.toString(array) +
                "\nend=" + end +
                '}';
    }

    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>() {

            private int cur = 0;

            @Override
            public boolean hasNext() {
                return cur < end && array[cur] != null;
            }

            @Override
            public T next() {
                return array[cur++];
            }
        };
        return it;
    }

    //Implemented for a check
    public static void main(String... args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> list = new ArrayList<>();
        while (true) {

            System.out.println("1. ADD 2. REMOVE_1_ITEM 3. REMOVE_all_ITEMS 4. SIZE 5. SEARCH 6. PRINT 7. ITERATE\n");
            int val;
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    val = scanner.nextInt();
                    list.insertItem(val);
                    break;
                case 2:
                    val = scanner.nextInt();
                    list.removeFirstInstanceOfItem(val);
                    break;
                case 3:
                    val = scanner.nextInt();
                    list.removeAllInstancesOfItem(val);
                    break;
                case 4:
                    System.out.println(list.getLength());
                    break;
                case 5:
                    val = scanner.nextInt();
                    System.out.println(list.searchItem(val));
                    break;
                case 6:
                    list.printElements();
                    break;
                case 7:
                    for (Integer integer : list) {
                        System.out.println(integer * integer);
                    }
                    break;
                default:
                    return;
            }
        }
    }
}
