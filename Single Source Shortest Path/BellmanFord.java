public class BellmanFord extends SingleSourceShortestPath {

    public BellmanFord(GraphDirectedWeightedList graphDirectedWeightedList) {
        super(graphDirectedWeightedList);
    }

    @Override
    Boolean execute(int s) {
        currentSource = s;
        dist[s]=0;
        for(int counter=1; counter<n; ++counter){
            for(int u=0; u<n; ++u){
                for(Pair<Integer, Integer> vw : (ArrayList<Pair<Integer, Integer>>) graph.adj[u]){ //For all edges
                    relax(u, vw.getFirst(), vw.getSecond());
                }
            }
        }

        for(int u=0; u<n; ++u){
            for(Pair<Integer, Integer> vw : (ArrayList<Pair<Integer, Integer>>) graph.adj[u]){ //For all edges
                if(dist[vw.getFirst()] > dist[u] + vw.getSecond())
                    return false;
            }
        }
        return true;
    }
}
