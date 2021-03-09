import java.io.PrintWriter;

public abstract class SingleSourceShortestPath {

    GraphDirectedWeightedList graph;
    int n;
    int[] dist;
    Integer[] parent;
    Integer currentSource = null;

    SingleSourceShortestPath(GraphDirectedWeightedList graphDirectedWeightedList){
        graph = graphDirectedWeightedList;
        n = graph.noVertices;
        initializeSingleSource();
    }

    protected void initializeSingleSource(){
        dist = new int[n];
        parent = new Integer[n];
        for(int i=0; i<n; ++i){
            dist[i] = 0x3f3f3f3f;
            parent[i] = null;
        }
    }

    protected boolean relax(int u, int v, int w){
        if(dist[v] > dist[u] + w){
            dist[v] = dist[u] + w;
            parent[v] = u;
            return true;
        }
        return false;
    }

    abstract Boolean execute(int source);
    Boolean execute(int source, boolean reset){
        if(reset)
            initializeSingleSource();
        return execute(source);
    }

    public int getDist(int dest){
        return dist[dest];
    }

    public int getDist(int source, int dest){
        if(source != currentSource)
            execute(source, true);
        return dist[dest];
    }

    public void printPath(int dest, PrintWriter writer){
        if(parent[dest] == null){
            writer.print(dest);
            return;
        }
        else{
            printPath(parent[dest], writer);
            writer.print(" -> " + dest + " ");
        }
    }

    public void printPath(int source, int dest, PrintWriter string){
        if(source != currentSource)
            execute(source, true);
        printPath(dest, string);
    }

}
