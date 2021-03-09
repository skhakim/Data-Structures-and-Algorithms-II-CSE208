public class GraphDirectedWeightedList extends GraphAdjList {

    GraphDirectedWeightedList(int noVertices){
        super(noVertices, true);
        for(int i=0; i<noVertices; ++i)
            adj[i] = new ArrayList<Pair<Integer, Integer>>();
    }

    @Override
    public boolean addEdge(int u, int v) {
        return this.addEdge(u, v, 1);
    }

    public boolean addEdge(int u, int v, int w) {
        if(u >= noVertices || v >= noVertices)
            return false;
        else{
            ((ArrayList<Pair<Integer, Integer>>) adj[u]).insertItem(new Pair<>(v, w));
            ++noEdges;
            return true;
        }
    }

    @Override
    public void printGraph() {
        System.out.println("No of vertices: " + noVertices +
                "\nNo of edges: " + noEdges + "\nEdges: ");
        for(int u=0; u<noVertices; ++u){
            for(Pair<Integer, Integer> vw:((ArrayList<Pair<Integer, Integer>>) adj[u]))
                System.out.print("(" + u + ", " + vw.getFirst() + ", " + vw.getSecond() + ")\t");
            System.out.println();
        }
        if(!directed)
            System.out.println("As undirected graph is bidirectional graph, every edge is printed in both directions.");
    }

    @Override
    public void removeEdge(int u, int v) {
        int i=0;
        for(Pair<Integer, Integer> vw:((ArrayList<Pair<Integer, Integer>>) adj[u])){
            if(vw.getFirst() == v){
                ((ArrayList<Pair<Integer, Integer>>) adj[u]).removeItemAt(i);
            }
            ++i;
        }
    }

    public void removeEdge(int u, int v, int w) {
        int i=0;
        for(Pair<Integer, Integer> vw:((ArrayList<Pair<Integer, Integer>>) adj[u])){
            if(vw.getFirst() == v && vw.getSecond() == w){
                ((ArrayList<Pair<Integer, Integer>>) adj[u]).removeItemAt(i);
            }
            ++i;
        }
    }

    @Override
    public boolean isEdge(int u, int v) {
        for(Pair<Integer, Integer> vw:((ArrayList<Pair<Integer, Integer>>) adj[u])){
            if(vw.getFirst() == v){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOutDegree(int u) {
        return ((ArrayList) adj[u]).getLength();
    }

    @Override
    public boolean hasCommonAdjacent(int u, int v) {
        return super.hasCommonAdjacent(u, v);
    }
}
