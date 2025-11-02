package smartcity.graph.dagsp;

import smartcity.model.Edge;
import smartcity.model.Graph;

import java.util.Arrays;
import java.util.List;

public class DagShortestPath {

    private final Graph g;

    public DagShortestPath(Graph g) {
        this.g = g;
    }

    // order — уже топологический порядок
    public int[] shortestFrom(int source, List<Integer> order) {
        int n = g.getN();
        int INF = 1_000_000_000;
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[source] = 0;

        for (int u : order) {
            if (dist[u] == INF) continue;
            for (Edge e : g.getAdj().get(u)) {
                if (dist[e.v] > dist[u] + e.w) {
                    dist[e.v] = dist[u] + e.w;
                }
            }
        }

        return dist;
    }
}
