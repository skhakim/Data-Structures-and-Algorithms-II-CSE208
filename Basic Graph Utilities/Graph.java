import java.lang.reflect.Array;
import java.util.Arrays;

abstract public class Graph {

    int noVertices = 0;
    int noEdges = 0;
    boolean directed = true;

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    boolean verbose = true;

    Colours[] dfsColours, bfsColours;
    Integer[] dfsParent, bfsParent;
    int[] bfsDistance, dfsStartTime, dfsEndTime;
    int dfsTime;

    protected void resetDFS(){
        dfsColours = new Colours[noVertices];
        Arrays.fill(dfsColours, Colours.WHITE);
        dfsParent = new Integer[noVertices];
        Arrays.fill(dfsParent, null);
        dfsTime = 0;
        dfsStartTime = new int[noVertices];
        dfsEndTime = new int[noVertices];
    }

    protected void resetBFS(){
        bfsColours = new Colours[noVertices];
        bfsParent = new Integer[noVertices];
        bfsDistance = new int[noVertices];
        Arrays.fill(bfsColours, Colours.WHITE);
        Arrays.fill(bfsParent, null);
        Arrays.fill(bfsParent, 0);
    }


    public void setNoVertices(int givenNoVertices){
        noVertices = givenNoVertices;
    }

    abstract boolean addEdge(int u, int v);
    abstract void printGraph();
    abstract void removeEdge(int u, int v);
    abstract boolean isEdge(int u, int v);
    abstract int getOutDegree(int u);

    int getInDegree(int u) {
        assert u < noVertices;
        int count=0;
        for(int i=0; i<noVertices; ++i){
            if(isEdge(i, u))
                ++count;
        }
        return count;
    }

    abstract boolean hasCommonAdjacent(int u, int v);

    abstract void dfs(int source);
    abstract void bfs(int source);

    int getDist(int u, int v) {
        bfs(u);
        return bfsDistance[v];
    };


}
