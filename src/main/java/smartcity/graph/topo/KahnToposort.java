package smartcity.graph.topo;

import smartcity.model.Graph;
import smartcity.model.Edge;

import java.util.*;

public class KahnToposort {

    private final Graph g;

    public KahnToposort(Graph g) {
        this.g = g;
    }

    public List<Integer> sort() {
        int n = g.getN();
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++) {
            for (Edge e : g.getAdj().get(u)) {
                indeg[e.v]++;
            }
        }

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indeg[i] == 0) q.add(i);
        }

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (Edge e : g.getAdj().get(u)) {
                indeg[e.v]--;
                if (indeg[e.v] == 0) {
                    q.add(e.v);
                }
            }
        }

        return order;
    }
}
