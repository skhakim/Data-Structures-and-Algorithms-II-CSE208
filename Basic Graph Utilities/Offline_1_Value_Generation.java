import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;

public class Offline_1_Value_Generation {

    public static void main(String ... args) throws Exception{

        int[] vertexOptions = {1000, 2000, 4000, 8000, 16000};
        int noEdges, u, v;
        ThreadLocalRandom random =  ThreadLocalRandom.current();
        Graph matrixGraph, listGraph;

        PrintWriter matrixWriter = new PrintWriter("1_matrix.csv");
        PrintWriter listWriter = new PrintWriter("1_list.csv");

        for(int n : vertexOptions){
            noEdges = n;
            while(noEdges < ((n*n-n)/8)){
                matrixGraph = new GraphAdjMatrix(n);
                listGraph = new GraphAdjList(n);

                matrixGraph.setVerbose(false);
                listGraph.setVerbose(false);

                for(int i=0; i<noEdges; ++i){
                    u = random.nextInt(0, n);
                    v = random.nextInt(0, n);

                    matrixGraph.addEdge(u, v);
                    listGraph.addEdge(u, v);
                }

                long t1 = System.nanoTime();

                for(int i=0; i<10; ++i){
                    u = random.nextInt(0,n);
                    matrixGraph.bfs(u);
                }

                long t2 = System.nanoTime();

                for(int i=0; i<10; ++i){
                    u = random.nextInt(0,n);
                    listGraph.bfs(u);
                }

                long t3 = System.nanoTime();

                matrixWriter.println(n + ", " + noEdges + ", " + (t2-t1)/10.0);
                listWriter.println(n + ", " + noEdges + ", " + (t3-t2)/10.0);
                matrixWriter.flush();
                listWriter.flush();

                noEdges = noEdges * 2;
                System.out.println(noEdges + ", " + n + ", " + ((n*n-n)/8) + ", " + (t3-t2) + ", " + (t2-t1));
            }
        }
    }
}


