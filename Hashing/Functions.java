import java.util.HashMap;

public class Functions {

    // utils for rotations

    static int rtRotate(int n, int d) {
        return (n << (Integer.SIZE - d)) | (n >> d);
    }

    static int ltRotate(int n, int d) {
        return (n >> (Integer.SIZE - d)) | (n << d);
    }


    // our first hash function
    // 75
    static int iota(String x, int mod){
        int h = 14, l = x.length();
        char c;
        for(int i=0; i<l; ++i){
            c = x.charAt(i);
            h ^= (rtRotate(h, 2) + ltRotate(h, 6) + c);
            h += 13;
            //System.out.println(h);
        }
        return (h%mod+mod)%mod;
    }


    // our second hash function
    // 75
    static int zeta(String x, int mod){
        int h = 19, l = x.length();
        char c;
        for(int i=0; i<l; ++i){
            c = x.charAt(i);
            h ^= (h<<3) ^ (h>>5) ^ c;
            h += 41;
            //System.out.println(h);
        }
        return (h%mod+mod)%mod;
    }


    // our third (aux) hash function
    // 75%
    static int beta(String x, int mod){
        int h = 0, l = x.length();
        char c;
        for(int i=0; i<l; ++i){
            c = x.charAt(i);
            h += ltRotate(h, 1) + rtRotate(h, 11) + c;
            //System.out.println(h);
        }
        return (h%mod+mod)%mod;
    }

    static Integer dummy(String x, int y){
        return 0;
    }


//    public static void main(String ... args){
//        RandomWordGenerator generator = new RandomWordGenerator();
//        HashMap<Integer, Integer> map = new HashMap<>();
//        for(int i=0; i<10000; ++i){
//            String s = generator.getRandomWord();
//            int x = iota(s, 10000);
//            map.put(x, map.getOrDefault(x, 0)+1);
//            //System.out.println(s + " " + iota(s, 10000));
//        }
//        System.out.println(map.size());
//        System.out.println(map.getOrDefault(0, 0));
//        map = new HashMap<>();
//        for(int i=0; i<10000; ++i){
//            String s = generator.getRandomWord();
//            int x = zeta(s, 10000);
//            map.put(x, map.getOrDefault(x, 0)+1);
//            //System.out.println(s + " " + iota(s, 10000));
//        }
//        System.out.println(map.size());
//        System.out.println(map.getOrDefault(0, 0));
//        map = new HashMap<>();
//        for(int i=0; i<10000; ++i){
//            String s = generator.getRandomWord();
//            int x = beta(s, 10000);
//            map.put(x, map.getOrDefault(x, 0)+1);
//            //System.out.println(s + " " + iota(s, 10000));
//        }
//        System.out.println(map.size());
//        System.out.println(map.getOrDefault(0, 0));
//        for(int x: map.keySet()){
//            //System.out.println(x + " " + map.get(x));
//        }
//    }
}
