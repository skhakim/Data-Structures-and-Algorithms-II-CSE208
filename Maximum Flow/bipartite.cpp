#include "flow.h"

struct match{
    int u, v;
    match(int x, int y) : u(x), v(y) {}
};

class bipartite_graph : flow_graph
{
    int source;
    int* color;
    flow_graph _graph;
    vector<match> ret;
public:
    bipartite_graph(int _noVertices)
            : _graph(_noVertices+2, _noVertices, _noVertices+1),
                   flow_graph(_noVertices) {
        source = _noVertices;
    }

    bool add_edge(int u, int v){
        //_graph.add_edge(u, v);
        flow_graph::add_edge(u, v);
        flow_graph::add_edge(v, u);
    }

    bool is_bipartite(){
        int u, v, noVertices = get_noVertices();
        color = new int[noVertices];
        for(int i=0; i<noVertices; ++i)
            color[i] = -1;

        for(int s=0; s<noVertices; ++s){
            if(color[s] == -1){
                color[s] = 0;
                queue<int>q;
                q.push(s);

                while(!q.empty()){
                    u = q.front();
                    //    cout << "U: " << u << endl;
                    q.pop();

                    for(node& n:graph[u]){
                        v = n.dest;
                        if(color[v] == -1){
                            q.push(v);
                            color[v] = 1-color[u];
                            //cout << "V: " << v << " " << q.size() << " " << q.front() << endl;
                        }
                        else if(color[v] == color[u]){
                            //cout << u << " " << v << " FALSE\n";
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    int find_matching(){
        if(!is_bipartite())
            return 0;

        for(int a=0, b; a<source; ++a){
            //cout << a << " " << color[a] << endl;
            if(color[a]){
                _graph.add_edge(source, a);
                for(node& n: graph[a]){
                    b = n.dest;
                    _graph.add_edge(a, b);
                }
            }
            else
                _graph.add_edge(a, source+1);


        }


        int to_ret = _graph.find_flow();

        for(int a=0; a<source; ++a){
            for(node& n:_graph.get_adjacency(a)){
                if(n.flow == 1 && n.dest < source){
                    ret.emplace_back(a, n.dest);
                }
            }
        }

        return to_ret;
    }



    void print_matching(){
        cout << ret.size() << endl;
        for(match& m:ret)
            cout << m.u << " " << m.v << endl;
    }

    ~bipartite_graph(){
        if(color)
            delete[] color;
        ret.clear();
    }
};

int main()
{
    freopen("in7.txt", "r", stdin);
    //freopen("b_out0.txt", "w", stdout);
    int n, m, u, v;

    cin >> n >> m;
    bipartite_graph graph(n);

    for(int i=0; i<m; ++i){
        cin >> u >> v;
        graph.add_edge(u, v);
    }
    //cout << graph.find_matching() << " ";

    int mat = graph.find_matching();
    if(!mat){
        cout << "The graph is NOT bipartite!";
        return 0;
    }
    graph.print_matching();

    //cout << "EVEN HERE!\n";
    return 0;
}
/*

9 8
0 5
1 5
1 7
2 6
2 7
2 8
3 7
4 7

7 6
0 4
1 4
2 4
2 5
2 6
3 6

*/
