#include<iostream>
#include<vector>
#include<queue>
#include<algorithm>
#include<cstdlib>
#include<cassert>
using namespace std;

typedef long long ll;

struct node
{
    int dest;
    ll flow, capacity;

    node(int des, ll cap, ll fl = 0) : dest(des), flow(fl), capacity(cap) {}

    void augFlow(int x){
        flow += x;
        //assert(flow <= capacity);
    }

    void increaseCapacity(int x){
        capacity += x;
    }

    ~node(){}
};

class flow_graph
{
protected:
    int noVertices;
    int noEdges;
    vector<vector<node>> graph;
private:
    int source, sink;
    ll *neck=0;
    int *dist=0, *par=0;
public:
    flow_graph();
    flow_graph(int _noVertices, int s = -1, int t = -1) : source(s), sink(t){
        noVertices = _noVertices, noEdges = 0;
        graph.assign(noVertices, vector<node>());
    }

    int get_noVertices(){
        return noVertices;
    }

    vector<node> get_adjacency(int i){
        return graph[i];
    }

    void set_source_sink(int a, int b){
        source = a, sink = b;
        resetFlow();
    }

    void resetFlow(){
        for(int a=0; a<noVertices; ++a){
            for(node n:graph[a]){
                n.flow = 0;
            }
        }
    }

    void add_edge(int a, int b, ll c = 1){
        if(c<=0) return;
        for(node& n:graph[a]){
            if(n.dest == b){
                n.increaseCapacity(c);
                return;
            }
        }
        graph[a].emplace_back(b, c);
        noEdges++;
    }

    flow_graph getResidual(){
        flow_graph res = flow_graph(noVertices, source, sink);
        for(int a=0, b; a<noVertices; ++a){
            for(node n:graph[a]){
                b = n.dest;
                res.add_edge(a, b, n.capacity - n.flow);
                res.add_edge(b, a, n.flow);
            }
        }
        return res;
    }

    void BFS(){
        dist = new int[noVertices];
        par = new int[noVertices];
        neck = new ll[noVertices];

        int u, v;
        bool* vis = new bool[noVertices];

        for(int i=0; i<noVertices; ++i){
            dist[i] = 0x3f3f3f3f;
            par[i] = -4;
            neck[i] = 0x3f3f3f3f3f3f3f3f;
            vis[i] =false;
        }

        queue<int> q;
        q.push(source);
        dist[source] = 0;
        par[source] = -1;
        vis[source] = true;

        while(!q.empty()){
            u = q.front();
            q.pop();

            for(node& n:graph[u]){
                v = n.dest;
                if(!vis[v]){
                    q.push(v);
                    dist[v] = dist[u] + 1;
                    par[v] = u;
                    neck[v] = min(n.capacity, neck[u]);
                    vis[v] = true;
                }
            }
        }

        delete[] vis;
    }

    ll augmentPath(){
        flow_graph res = getResidual();
        res.BFS();
        //cout << "ERR!!!";
        if(res.par[sink] == -4){
            return 0;
        }

        int s, t = sink;
        ll aug = res.neck[sink];

        while((s = res.par[t]) != -1){
            aug = res.neck[sink];
            for(node& n:graph[s]){
                if(n.dest == t){
                    //cout << n.capacity << " " << n.flow << " " << aug << endl;
                    if(n.capacity >= n.flow+aug){
                        n.augFlow(aug);
                        aug = 0;
                    }
                    else{
                        aug -= (n.capacity - n.flow);
                        n.augFlow(n.capacity - n.flow);
                    }
                    break;
                }
            }
            if(aug){
                for(node& n:graph[t]){
                    if(n.dest == s){
                        n.augFlow(-aug);
                        break;
                    }
                }
            }
            t = s;
        }

        return res.neck[sink];
    }

    ll find_flow()
    {
        ll x, tot_flow = 0;

        while(x = augmentPath()){
            //cout << "Flow augmented: " << x << endl;
            tot_flow += x;
            //print_graph();
            //cout << "\n";
            //getResidual().print_graph();
        }

        return tot_flow;
    }

    void print_graph()
    {
        for(int a=0, b; a<noVertices; ++a){
            for(node n:graph[a]){
                cout << a << " " << n.dest << " ";
                cout << n.flow << "/" << n.capacity << "\n";
            }
        }
    }

    ~flow_graph(){
        //cout << "'\n1\n";
        if(par)
            delete[] par;
        //cout << "'\n2\n";
        if(dist)
            delete[] dist;
        //cout << "'\n3\n";
        if(neck)
            delete[] neck;
            //cout << "HERE?";
        //cout << par << " " << dist << " " << neck << "\n";*/
        graph.clear();
    }
};

