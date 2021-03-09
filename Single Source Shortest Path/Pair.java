public class Pair<First, Second> {
    
    protected First first;
    protected Second second;

    public First getFirst() {
        return first;
    }

    public void setFirst(First f) {
        this.first = f;
    }

    public Second getSecond() {
        return second;
    }

    public void setSecond(Second s) {
        this.second = s;
    }

    public Pair(First f, Second s) {
        this.first = f;
        this.second = s;
    }

}
