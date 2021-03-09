#include "flow.h"

int main()
{
    freopen("input03.txt", "r", stdin);
    //freopen("f_out0.txt", "w", stdout);

    int n, m, u, v;
    ll w;

    cin >> n >> m;
    flow_graph graph(n);

    for(int i=0; i<m; ++i){
        cin >> u >> v >> w;
        graph.add_edge(u, v, w);
    }

    cin >> u >> v;
    graph.set_source_sink(u, v);

    cout << graph.find_flow() << endl;
    graph.print_graph();

    /*cout << "\nResidual:\n";
    graph.getResidual().print_graph();*/

    return 0;
}
