import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Offline_2_Testing {

    public static void main(String... args) throws Exception {

        //try(Scanner scanner = new Scanner(System.in)) {
        try (Scanner scanner = new Scanner(new File("src\\input.txt"), StandardCharsets.UTF_8.name())) {

            try (PrintWriter writer = new PrintWriter(System.out)) {
                //try (PrintWriter writer = new PrintWriter(new File("src\\offline2.txt"))) {

                int N = scanner.nextInt();
                int M = scanner.nextInt();
                int u, v, w;

                GraphDirectedWeightedList graph, graphAbs;

                graph = new GraphDirectedWeightedList(N);
                graphAbs = new GraphDirectedWeightedList(N);

                for (int i = 0; i < M; ++i) {
                    u = scanner.nextInt();
                    v = scanner.nextInt();
                    w = scanner.nextInt();

                    graph.addEdge(u, v, w);
                    graphAbs.addEdge(u, v, abs(w));
                }

                BellmanFord bellmanFord = new BellmanFord(graph);
                Dijkstra dijkstra = new Dijkstra(graphAbs);

                u = scanner.nextInt();
                v = scanner.nextInt();

                boolean negativeCycle = !bellmanFord.execute(u);
                dijkstra.execute(u);

                writer.println("Bellman Ford Algorithm:");
                if (negativeCycle) {
                    writer.println("Negative cycle detected");
                } else {
                    writer.println(bellmanFord.getDist(v));
                    bellmanFord.printPath(v, writer);
                }

                writer.println("\n");

                writer.println("Dijkstra's Algorithm:");
                writer.println(dijkstra.getDist(v));
                dijkstra.printPath(v, writer);

                writer.flush();

            }

        }
    }

    private static int abs(int x) {
        if (x < 0)
            return -x;
        else
            return x;
    }
}


