package smartcity.graph.dagsp;

import smartcity.model.Edge;
import smartcity.model.Graph;

import java.util.Arrays;
import java.util.List;

public class DagLongestPath {

    private final Graph g;

    public DagLongestPath(Graph g) {
        this.g = g;
    }

    public int[] longest(List<Integer> order) {
        int n = g.getN();
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MIN_VALUE / 4);
        // можно считать, что старт — это все с indegree=0, но мы просто нулим всё
        for (int i = 0; i < n; i++) {
            if (hasNoInEdges(i)) {
                dist[i] = 0;
            }
        }

        for (int u : order) {
            if (dist[u] == Integer.MIN_VALUE / 4) continue;
            for (Edge e : g.getAdj().get(u)) {
                dist[e.v] = Math.max(dist[e.v], dist[u] + e.w);
            }
        }

        return dist;
    }

    private boolean hasNoInEdges(int v) {
        for (int u = 0; u < g.getN(); u++) {
            for (Edge e : g.getAdj().get(u)) {
                if (e.v == v) return false;
            }
        }
        return true;
    }

    public int getMaxDistance(int[] dist) {
        int mx = Integer.MIN_VALUE;
        for (int d : dist) {
            mx = Math.max(mx, d);
        }
        return mx;
    }
}
