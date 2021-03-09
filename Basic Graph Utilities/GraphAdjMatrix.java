public class GraphAdjMatrix extends Graph {

    private Integer[][] matrix;

    GraphAdjMatrix(int noNodes, boolean directed) {
        super.directed = directed;
        setNoVertices(noNodes);
        matrix = new Integer[noNodes][noNodes];
        for (int i = 0; i < noNodes; ++i) {
            for (int j = 0; j < noNodes; ++j)
                matrix[i][j] = 0;
        }
        resetBFS();
        resetDFS();
    }

    GraphAdjMatrix(int noVertices) {
        this(noVertices, true);
    }

    @Override
    public void setNoVertices(int givenNoVertices) {
        super.setNoVertices(givenNoVertices);
    }

    @Override
    boolean addEdge(int u, int v) {
        if (u >= noVertices || v >= noVertices)
            return false;
        if (matrix[u][v] != 0)
            return false;
        else {
            matrix[u][v] = 1;
            if (!directed)
                matrix[v][u] = 1;
            ++noEdges;
        }
        return true;
    }

    @Override
    void printGraph() {
        System.out.println("No of vertices: " + noVertices +
                "\nNo of edges: " + noEdges + "\nEdges: ");
        for (int u = 0; u < noVertices; ++u) {
            for (int v = 0; v < u; ++v)
                if (matrix[u][v] != 0)
                    System.out.print("(" + u + ", " + v + ")\t");
            System.out.println();
        }
    }

    @Override
    void removeEdge(int u, int v) {
        matrix[u][v] = 0;
        if (!directed)
            matrix[v][u] = 0;
        --noEdges;
    }

    @Override
    boolean isEdge(int u, int v) {
        assert u < noVertices;
        assert v < noVertices;
        return (matrix[u][v] != 0);
    }

    @Override
    int getOutDegree(int u) {
        assert u < noVertices;
        int count = 0;
        for (int i = 0; i < noVertices; ++i)
            if (matrix[u][i] != 0)
                count++;
        return count;
    }

    @Override
    boolean hasCommonAdjacent(int u, int v) {
        for (int i = 0; i < noVertices; ++i) {
            if (matrix[u][i] != 0)
                if (matrix[i][v] != 0)
                    return true;
        }
        return false;
    }

    void dfs(int source, boolean reset) {
        if (reset)
            resetDFS();

        if (dfsColours[source] != Colours.WHITE)
            return;
        if (verbose)
            System.out.println("DFS has found vertex " + source);

        dfsStartTime[source] = ++dfsTime;
        dfsColours[source] = Colours.GRAY;
        for (int u = 0; u < noVertices; ++u) {
            if (matrix[source][u] != 0 && dfsColours[u] == Colours.WHITE)
                dfs(u, false);
        }
        dfsEndTime[source] = ++dfsTime;
        dfsColours[source] = Colours.BLACK;
    }

    @Override
    void dfs(int source) {
        dfs(source, false);
    }

    void bfs(int source, boolean reset) {
        if (reset)
            resetBFS();
        bfsDistance[source] = 0;
        bfsColours[source] = Colours.GRAY;
        bfsParent[source] = null;

        Queue<Integer> queue = new Queue<>();
        queue.enqueue(source);
        if (verbose)
            System.out.println("BFS has enqueued vertex " + source + " at distance=" + bfsDistance[source]);


        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < noVertices; ++v)
                if (matrix[u][v] != 0) {
                    if (bfsColours[v] == Colours.WHITE) {
                        bfsColours[v] = Colours.GRAY;
                        bfsParent[v] = u;
                        bfsDistance[v] = 1 + bfsDistance[u];
                        queue.enqueue(v);
                        if (verbose)
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
