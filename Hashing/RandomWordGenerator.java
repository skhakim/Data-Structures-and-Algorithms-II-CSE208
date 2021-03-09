import java.util.*;

public class RandomWordGenerator {

    int length;
    ArrayList<Character> letters = new ArrayList<>();


    RandomWordGenerator(){
        length = 7;
        for(char c = 'A'; c <= 'Z'; ++c)
            letters.add(c);
    }

    RandomWordGenerator(int x){
        length = x;
        for(char c = 'A'; c <= 'Z'; ++c)
            letters.add(c);
    }

    String getRandomWord(){
        Collections.shuffle(letters);
        String s = "";
        for(int i=0; i<length; ++i)
            s += letters.get(i);
        return s;
    }

//    public static void main(String ... args){
//
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        RandomWordGenerator generator = new RandomWordGenerator();
//        for(int i=0; i<n; ++i){
//            System.out.println(generator.getRandomWord());
//        }
//    }
}
