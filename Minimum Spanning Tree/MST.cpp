#include <bits/stdc++.h>
#include <ostream>

using namespace std;
typedef std::pair<int, int> item;

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

    bool checkBidirectionalEdgeEquality(int _a, int _b){
        return (a==_a && b==_b) || (a==_b && b==_a);
    }

    friend bool operator<(const edge &lhs, const edge &rhs) {
        return lhs.w < rhs.w;
    }
};

struct element
{
    int number;
    int par;
    int rank;

    element(int number) : number(number) { //constructor performs the make-set operation
        par = number;
        rank = 0;
    }

    friend void link(element &x, element &y){
        if(x.rank > y.rank)
            y.par = x.number;
        else{
            x.par = y.number;
            if(x.rank == y.rank){
                ++y.rank;
            }
        }
    }

    friend ostream &operator<<(ostream &os, const element &element) {
        os << "number: " << element.number << " par: " << element.par << " rank: " << element.rank;
        return os;
    }
};

class disjointSetForMST
{
    int noVertices;
    vector<element>sets;

public:
    disjointSetForMST(int noVertices) : noVertices(noVertices) {
        for(int i=0; i<noVertices; ++i){
            sets.emplace_back(i); // n make set ops performed
        }
    }

    ~disjointSetForMST(){
        sets.clear();
    }

    int find_set(int n){
        element x = sets[n];
        if(n != x.par)
            x.par = find_set(x.par);
        return x.par;
    }

    void make_union(int m, int n){
        link(sets[m], sets[n]);
    }

    void print()
    {
        for(int i=0; i<noVertices; ++i){
            cout << sets[i] << endl;
        }
    }
};

class graph_MST
{
    int noEdges;

protected:
    int noVertices;
    vector<vector<item>> adj;
    vector<edge> edgeList;
    disjointSetForMST dsu;

public:
    graph_MST(int noVertices) : noVertices(noVertices), dsu(noVertices) {
        noEdges=0;
        adj = vector<vector<item>>(noVertices);
        edgeList = vector<edge>();
    }

    ~graph_MST(){
        adj.clear();
        edgeList.clear();
    }

    void addEdge(int a, int b, int w){
        adj[a].emplace_back(b, w);
        adj[b].emplace_back(a, w);
        edgeList.emplace_back(a, b, w);
        ++noEdges;
    }

    int getNoVertices() const {
        return noVertices;
    }

};

class Prim : public graph_MST
{
    vector<int> par;
    vector<int> key;
    vector<bool> vis;
    priority_queue<item>queue;
    int total_weight;
    int root=-1;

public:
    Prim(int noVertices) : graph_MST(noVertices) {
        total_weight = 0;
    }

    Prim(const graph_MST& a) : graph_MST(a){
        par.assign(noVertices, -1);
        key.assign(noVertices, 0x3f3f3f3f);
        vis.assign(noVertices, false);
        par.shrink_to_fit();
        key.shrink_to_fit();
        vis.shrink_to_fit();
        total_weight = 0;
    }

    ~Prim(){
        par.clear();
        key.clear();
        vis.clear();
    }

    void execute(int source=0){
        root = source;
        key[source] = 0;
        queue.emplace(-0.0, source);
        int u, v, w;
        while(!queue.empty()){
            tie(w, u) = queue.top();
            queue.pop();
            if(vis[u])
                continue;
            vis[u] = true, total_weight -= w;

            for(item& it:adj[u]){
                tie(v, w) = it;
                if(!vis[v] && key[v] > w){
                    par[v] = u;
                    key[v] = w;
                    queue.emplace(-w, v);
                }
            }
        }
    }

    void print()
    {
        if(root==-1){
            execute();
        }
        cout << "Prim-Jarnik Algorithm: \nTotal weight = " << total_weight << endl;
        cout << "Root node = " << root << endl;
        for(int i=0; i<noVertices; ++i){
            if(par[i] != -1)
                cout << par[i] << " " << i << endl;
        }
        cout << endl;
    }
};

class Kruskal : public graph_MST
{
    vector<edge> KruskalEdgeList;
    int total_weight;

public:
    Kruskal(int noVertices) : graph_MST(noVertices) {
        KruskalEdgeList = vector<edge>();
        total_weight = 0;
    }

    Kruskal(const graph_MST &a) :  graph_MST(a){
        KruskalEdgeList = vector<edge>();
        total_weight = 0;
    }

    ~Kruskal(){
        KruskalEdgeList.clear();
    }

    void execute(){
        int x, y;
        sort(edgeList.begin(), edgeList.end());
        //dsu.print();
        for(edge& e:edgeList){
            x = dsu.find_set(e.getA());
            y = dsu.find_set(e.getB());
            if(x!=y){
                KruskalEdgeList.emplace_back(e);
                total_weight += e.getW();
                // cout << e << endl;
                dsu.make_union(x, y);
                // dsu.print();
            }
        }
        // cout << "Executed with weight=" << total_weight << endl;
    }

    void print(){

        cout << "Kruskal's Algorithm:\n";
        cout << "Total weight = " << total_weight << endl;

        for(edge& e : KruskalEdgeList){
            cout << e.getA() << " " << e.getB() << endl;
        }

        //cout << "Size: " << KruskalEdgeList.size() << endl;

        cout << endl;
    }

};


int main()
{
    freopen("in4.txt", "r", stdin);
    freopen("out4.txt", "w", stdout);

    int n, m, a, b;
    int w;
    cin >> n >> m;
    // cout << n << endl;
    //cout << "Hello, world! " << n << " & " << m << "\n";

    graph_MST graph(n);

    for(int i=0; i<m; ++i){
        cin >> a >> b >> w;
        graph.addEdge(a, b, w);
        //cout << "I: " << i;
    }

    //cout << "Graph Created!\n";

    Kruskal kr(graph);
    kr.execute();
    kr.print();

    Prim pr(graph);
    pr.execute();
    pr.print();

    return 0;

}
