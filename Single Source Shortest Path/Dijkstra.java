import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra extends SingleSourceShortestPath {

    /*public class Node extends Pair<Integer, Integer> implements Comparable<Node>{
        public Node(Integer f, Integer s) {
            super(f, s);
        }

        @Override
        public int compareTo(Node o) {
            return second-o.second;
        }
    }*/

    public Dijkstra(GraphDirectedWeightedList graphDirectedWeightedList) {
        super(graphDirectedWeightedList);
    }

    @Override
    Boolean execute(int source) {
        currentSource = source;
        PriorityQueue<Integer> queue = new PriorityQueue<>(
                Comparator.comparingInt((Integer a) -> dist[a])
        );

        dist[source] = 0;

        boolean extracted[] = new boolean[graph.noVertices];
        Arrays.fill(extracted, false);

        //for(int i=0; i<n; ++i)
        //    queue.add(i);

        queue.offer(source);

        while(!queue.isEmpty()){
            int u = queue.poll();
            extracted[u] = true;
            for(Pair<Integer, Integer> vw: (ArrayList<Pair<Integer, Integer>>) graph.adj[u]){
                if(!extracted[vw.getFirst()] && relax(u, vw.getFirst(), vw.getSecond())){
                    //System.out.println(u + " " + vw.getFirst() + " " + vw.getSecond());
                    queue.offer(vw.getFirst());
                }
            }
        }

        //list.printElements();
        return null;
    }
}
