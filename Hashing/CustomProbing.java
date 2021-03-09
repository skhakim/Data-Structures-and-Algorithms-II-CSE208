import java.util.function.BiFunction;

public class CustomProbing extends DoubleHashing {

    CustomProbing(int len, BiFunction<String, Integer, Integer> ab) {
        super(len, ab, Functions::dummy);
    }

    int get(int i, int h1, int h2) {
        return ((h1 + (i * 6) % m + (((5 * i) % m) * i) % m) % m + m) % m;
    }

}
