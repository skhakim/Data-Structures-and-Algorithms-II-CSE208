import java.util.Scanner;

public class Offline_1_Testing {

    public static void main(String ... args){

        //ArrayList<Integer>[] list = (ArrayList<Integer>[]) new Object[10];

        int choice, u, v;
        Scanner scanner = new Scanner(System.in);

        System.out.println("No of Vertices: ");
        choice = scanner.nextInt();
        System.out.println("1. Matrix Representation 2. List Representation");
        u = scanner.nextInt();
        System.out.println("1. Directed Graph 2. Undirected Graph");
        v = scanner.nextInt();

        Graph graph;
        if(u==1)
            graph = new GraphAdjMatrix(choice, (v==1));
        else
            graph = new GraphAdjList(choice, (v==1));

        while(true) {

            System.out.println("1. Add Edge 2. Remove Edge 3. Indegree 4. Outdegree 5. Print Graph 6. BFS 7. DFS 6. Exit");
            choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1:
                        u = scanner.nextInt();
                        v = scanner.nextInt();
                        if (graph.addEdge(u, v))
                            System.out.println("Edge added");
                        else
                            System.out.println("Edge adding failed");
                        break;
                    case 2:
                        u = scanner.nextInt();
                        v = scanner.nextInt();
                        graph.removeEdge(u, v);
                        break;
                    case 3:
                        u = scanner.nextInt();
                        System.out.println(graph.getInDegree(u));
                        break;
                    case 4:
                        u = scanner.nextInt();
                        System.out.println(graph.getOutDegree(u));
                        break;
                    case 5:
                        graph.printGraph();
                        break;
                    case 6:
                        v = scanner.nextInt();
                        graph.bfs(v);
                        break;
                    case 7:
                        v = scanner.nextInt();
                        graph.dfs(v);
                        break;
                    default:
                        return;
                }
            } catch (Exception e){
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }
}
