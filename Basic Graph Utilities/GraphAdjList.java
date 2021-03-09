import java.lang.reflect.Array;

public class GraphAdjList extends Graph {

    Object adj[];
    Array arr;

    GraphAdjList(int noVertices){
        this(noVertices, true);
    }

    GraphAdjList(int noVertices, boolean directed){
        super.directed = directed;
        super.setNoVertices(noVertices);
        adj = new Object[noVertices];
        for(int i=0; i<noVertices; ++i)
            adj[i] = new ArrayList<Integer>();
        resetBFS();
        resetDFS();
    }

    @Override
    public void setNoVertices(int givenNoVertices) {
        super.setNoVertices(givenNoVertices);
    }

    @Override
    boolean addEdge(int u, int v) {
        //System.out.println(u + " " + v + " " + noVertices);
        if(u >= noVertices || v >= noVertices)
            return false;
        if(((ArrayList<Integer>) adj[u]).searchItem(v) != -1 || u==v)
            return false;
        else{
            ((ArrayList<Integer>) adj[u]).insertItem(v);
            if(!directed)
                ((ArrayList<Integer>) adj[v]).insertItem(u);
            ++noEdges;
            return true;
        }
    }

    @Override
    void printGraph() {
        System.out.println("No of vertices: " + noVertices +
                "\nNo of edges: " + noEdges + "\nEdges: ");
        for(int u=0; u<noVertices; ++u){
            for(int v:((ArrayList<Integer>) adj[u]))
                System.out.print("(" + u + ", " + v + ")\t");
            System.out.println();
        }
        System.out.println("As undirected graph is bidirectional graph, every edge is printed in both directions.");
    }

    @Override
    void removeEdge(int u, int v) {
        ((ArrayList<Integer>) adj[u]).removeAllInstancesOfItem(v);
        if(!directed)
            ((ArrayList<Integer>) adj[v]).removeAllInstancesOfItem(u);
        --noEdges;
    }

    @Override
    boolean isEdge(int u, int v) {
        return (((ArrayList<Integer>) adj[u]).searchItem(v)!=-1);
    }

    @Override
    int getOutDegree(int u) {
        return ((ArrayList<Integer>) adj[u]).getLength();
    }

    @Override
    boolean hasCommonAdjacent(int u, int v) {
        for(Integer i:((ArrayList<Integer>) adj[u]))
            if(isEdge(i, v))
                return true;
        return false;
    }

    void dfs(int source, boolean reset){
        if(reset)
            resetDFS();
        if(dfsColours[source] != Colours.WHITE)
            return;
        else if(verbose)
            System.out.println("DFS has found vertex " + source);

        dfsStartTime[source] = ++dfsTime;
        dfsColours[source] = Colours.GRAY;
        for(int u:((ArrayList<Integer>) adj[source])){
            dfs(u, false);
        }
        dfsEndTime[source] = ++dfsTime;
        dfsColours[source] = Colours.BLACK;
    }

    @Override
    void dfs(int source) {
        dfs(source, false);
    }

    void bfs(int source, boolean reset){
        if(reset)
            resetBFS();
        bfsDistance[source] = 0;
        bfsColours[source] = Colours.GRAY;
        bfsParent[source] = null;

        Queue<Integer> queue = new Queue<>();
        queue.enqueue(source);
        if(verbose)
            System.out.println("BFS has enqueued vertex " + source + " at distance=" + 0);


        while(!queue.isEmpty()){
            int u = queue.poll();

            for(int v: ((ArrayList<Integer>) adj[u])){
                if(bfsColours[v] == Colours.WHITE){
                    bfsParent[v] = u;
                    bfsColours[v] = Colours.GRAY;
                    bfsDistance[v] = 1+bfsDistance[u];
                    queue.enqueue(v);
                    if(verbose)
                        System.out.println("BFS has enqueued vertex " + v + " at distance=" + bfsDistance[v]);
                }
            }

            bfsColours[u] = Colours.GRAY;
        }
    }

    @Override
    void bfs(int source) {
        bfs(source, true); //In DFS, reset was "false" by default
    }


}
