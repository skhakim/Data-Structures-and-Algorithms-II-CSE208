#include <bits/stdc++.h>
#include <ostream>

#define INF 1E30

using namespace std;
typedef std::pair<int, double> item;
typedef std::pair<double, int> util;

class edge {
    int a, b;
    double w;


public:
    edge() : a(-1), b(-1), w(-5000) {}

    edge(int a, int b, double w) : a(a), b(b), w(w) {}

    double getW() const {
        return w;
    }

    int getA() const {
        return a;
    }

    int getB() const {
        return b;
    }

    void setW(double w) {
        edge::w = w;
    }

    friend std::ostream &operator<<(std::ostream &os, const edge &edge) {
        os << "a: " << edge.a << " b: " << edge.b << " w: " << edge.w;
        return os;
    }

    bool checkEdgeEquality(int _a, int _b){
        return (a==_a) && (b==_b);
    }
};

class graph_adj_list
{
    int noVertices;
    int noEdges;
    vector<vector<item>> adj;
    vector<edge> edgeList;
    double** dis;
    int** parent;
    int offset=1;

    double* bfpath;

public:

    void setOffset(int a){
        offset=a;
    }

    graph_adj_list(int noVertices=0){
        setNoVertices(noVertices);
    }

    void setNoVertices(int _noVertices){
        noVertices = _noVertices;
        noEdges = 0;
        adj = vector<vector<item>>(noVertices);
        edgeList = vector<edge>();

        dis = new double*[noVertices];
        parent = new int*[noVertices];

        for(int i=0; i<noVertices; ++i){
            dis[i] = new double[noVertices];
            parent[i] = new int[noVertices];
        }

        bfpath = new double[noVertices];

        cleanSPInfo();
    }

    void cleanSPInfo(){
        for(int i=0; i<noVertices; ++i){
            for(int j=0; j<noVertices; ++j){
                dis[i][j] = INF;
                parent[i][j] = -1;
            }
            bfpath[i] = 0x3f3f3f3f;
        }
    }

    void printPredecessorMatrix(){
        cout << "\nPredecessor Matrix:\n";
        for(int i=0; i<noVertices; ++i){
            for(int j=0; j<noVertices; ++j){
                if(parent[i][j] == -1)
                    cout << "NIL\t";
                else
                    cout << offset+parent[i][j] << "\t";
            }
            cout << "\n";
        }
    }

    void printGraph(){
        cout << "Graph:\n";
        for(int i=0; i<noVertices; ++i){
            cout << offset+i << ": ";
            for(item& it:adj[i]){
                cout << offset+it.first << "(" << it.second << ")\t";
            }
            cout << "\n";
        }
    }

    void printDistanceMatrix(){
        cout << "\nDistance Matrix:\n";
        for(int i=0; i<noVertices; ++i){
            for(int j=0; j<noVertices; ++j){
                if(dis[i][j] > 0.9E30)
                    cout << "INF\t";
                else
                    cout << dis[i][j] << "\t";
            }
            cout << "\n";
        }
    }

    void printPath(int u, int v, int method=0)
    {
        switch(method){
            case 1:
                FloydWarshall();
                break;
            case 2:
                Johnson();
                break;
        }

        if(parent[u][v] == -1 && u!=v){
            cout << "No path exists!\n";
            return;
        }

        cout << "Shortest Path Weight: " << dis[u][v] << endl;

        printUtil(u,v);

        cout << endl;
    }

    void printUtil(int u, int v){
        if(u==v)
            cout << offset+u << "\t";
        else if(parent[u][v] == -1)
            return;
        else{
            double x = dis[u][v] - dis[u][parent[u][v]];
            printUtil(u, parent[u][v]);
            cout << " --> " << offset+v << "(" << x << ")\t";
        }
    }

    virtual ~graph_adj_list() {
        for(int i=0; i<noVertices; ++i){
            delete[] dis[i];
            delete[] parent[i];
        }
        delete[] dis;
        delete[] parent;

        adj.clear();
        edgeList.clear();
        noEdges = noVertices = 0;
    }

    bool addEdge(int u, int v, double w){
        if(searchEdge(u, v))
            return false;
        adj[u].emplace_back(v, w);
        edgeList.emplace_back(u, v, w);
        ++noEdges;
        //cout << "noEdges: " << noEdges << "\n";
        return true;
    }

    void removeEdge(int u, int v) {
        for(auto it=adj[u].begin(); it != adj[u].end(); ){
            if(it->first == v)
                it = adj[u].erase(it);
            else
                it++;
        }
        for(auto it=edgeList.begin(); it != edgeList.end(); ){
            if(it->checkEdgeEquality(u, v))
                it = edgeList.erase(it);
            else
                it++;
        }
        --noEdges;
    }

    edge* searchEdge(int u, int v){
        for(auto it=edgeList.begin(); it != edgeList.end(); ){
            if(it->checkEdgeEquality(u, v))
                return addressof(*it);
            else
                it++;
        }
        return NULL;
    }

    bool isEdge(int u, int v){
        return searchEdge(u, v)!=NULL;
    }

    void reweightEdge(int u, int v, double w){
        for(auto it=adj[u].begin(); it != adj[u].end(); it++){
            if(it->first == v)
                it->second = w;
        }
        for(edge& e:edgeList){
            if(e.checkEdgeEquality(u, v)){
                e.setW(w);
            }
        }
    }

    void incrementEdgeWeight(int u, int v, double w){
        for(auto it=adj[u].begin(); it != adj[u].end(); it++){
            if(it->first == v)
                it->second += w;
        }
        for(edge& e:edgeList){
            if(e.checkEdgeEquality(u, v)){
                e.setW(e.getW()+w);
            }
        }
    }

    double getWeight(int u, int v){
        for(auto it=edgeList.begin(); it != edgeList.end(); ){
            if(it->checkEdgeEquality(u, v))
                return it->getW();
            else
                it++;
        }
        return INF;
    }

    void FloydWarshall(){

        cleanSPInfo();

        for(edge& e : edgeList){
            dis[e.getA()][e.getB()] = min(dis[e.getA()][e.getB()], e.getW());
        }

        for(int i=0; i<noVertices; ++i)
            dis[i][i] = min(dis[i][i], 0.0);

        for(int i=0; i<noVertices; ++i){
            for(int j=0; j<noVertices; ++j){
                if(i!=j && dis[i][j] < INF){
                    parent[i][j] = i;
                }
            }
        }

        for(int k=0; k<noVertices; ++k){
            for(int i=0; i<noVertices; ++i){
                for(int j=0; j<noVertices; ++j){
                    if(dis[i][j] > dis[i][k]+dis[k][j]) {
                        dis[i][j] = dis[i][k] + dis[k][j];
                        parent[i][j] = parent[k][j];
                    }
                }
            }
        }
    }

    bool BellmanFord(int source){

        int u, v;
        double w;

        for(int i=0; i<noVertices; ++i){
            bfpath[i] = INF;
        }
        bfpath[source] = 0.0;

        for(int c=1; c<noVertices; ++c){
            for(edge& e:edgeList){
                u = e.getA(), v = e.getB(), w = e.getW();
                if(bfpath[v] > bfpath[u]+w){
                    bfpath[v] = bfpath[u]+w;
                    //cout << v << ": " << bfpath[v] << " with " << u << endl;
                }
            }
        }

        for(edge& e:edgeList){
            u = e.getA(), v = e.getB(), w = e.getW();
            if(bfpath[v] > bfpath[u]+w){
                return true; //Negative Cycle Detected
            }
        }
        return false; //Negative Cycle Not Detected
    }

    void Dijkstra(int source){

        int u, v, *par = parent[source];
        double w, *d = dis[source];

        for(int i=0; i<noVertices; ++i){
            par[i] = -1;
            d[i] = INF;
        }

        d[source]=0;

        priority_queue<util> queue;
        queue.emplace(-0.0, source);

        vector<bool>vis;
        vis.assign(noVertices, false);
        //vis[source] = true;

        while(!queue.empty()){
            util x = queue.top();
            queue.pop();
            u = x.second;
            if(vis[u])
                continue;
            vis[u] = true;

            for(item y:adj[u]){
                v = y.first;
                w = y.second;
                if(d[v] > d[u]+w){
                    d[v] = d[u]+w;
                    par[v] = u;
                    queue.emplace(-d[v], v);
                }
            }
        }


    }

    bool Johnson(){
        graph_adj_list mod_graph(1+noVertices);
        int s=noVertices;
        for(edge& e:edgeList){
            mod_graph.addEdge(e.getA(), e.getB(), e.getW());
        }
        for(int i=0; i<noVertices; ++i){
            mod_graph.addEdge(s, i, 0.0);
        }
        if(mod_graph.BellmanFord(s))
            return true;


        for(edge& e:edgeList) {
            // cout << "Before: " << mod_graph.searchEdge(e.getA(), e.getB())->getW() << " And: " << mod_graph.bfpath[e.getA()]-mod_graph.bfpath[e.getB()] << endl;
            mod_graph.incrementEdgeWeight(e.getA(), e.getB(), mod_graph.bfpath[e.getA()]-mod_graph.bfpath[e.getB()]);
            // cout << "After: " << mod_graph.searchEdge(e.getA(), e.getB())->getW() << endl;
        }

        for(int i=0; i<noVertices; ++i){
            mod_graph.Dijkstra(i);
        }


        for(int i=0; i<noVertices; ++i){
            for(int j=0; j<noVertices; ++j){
                dis[i][j] = mod_graph.dis[i][j] - mod_graph.bfpath[i] + mod_graph.bfpath[j];
                parent[i][j] = mod_graph.parent[i][j];
            }
            //cout << '\n';
        }

        return false;
    }
};

int main()
{
    //freopen("infile.txt", "r", stdin);
    //freopen("outfile.txt", "w", stdout);

    cout.setf(ios::fixed);
    cout.precision(2);

    int n, m, a, b;
    double w;
    cin >> n >> m;

    graph_adj_list graph(n);

    for(int i=0; i<m; ++i){
        cin >> a >> b >> w;
        graph.addEdge(--a, --b, w);
        //cout << "I: " << i;
    }

    cout << "Graph Created!\n";

    int choice;
    while(true){
        cout << endl;
        cin >> choice;
        switch(choice){
            case 1:
                graph.cleanSPInfo();
                cout << "APSP Matrices Cleared\n";
                break;
            case 2:
                graph.FloydWarshall();
                cout << "Floyd Warshall Algorithm Implemented\n";
                break;
            case 3:
                a = graph.Johnson();
                cout << "Johnson's Algorithm Implemented\n";
                if(a){
                    cout << "Negative Cycle Detected!\n";
                }
                break;
            case 4:
                cin >> a >> b;
                graph.printPath(--a, --b);
                break;
            case 5:
                graph.printGraph();
                break;
            case 6:
                graph.printDistanceMatrix();
                break;
            case 7:
                graph.printPredecessorMatrix();
                break;
            default:
                return 0;
        }
    }
}
