package smartcity.graph.scc;

import smartcity.model.Graph;
import smartcity.model.Edge;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TarjanSCC {

    private final Graph g;
    private int time = 0;
    private final int[] disc;
    private final int[] low;
    private final boolean[] inStack;
    private final Stack<Integer> stack;
    private final List<List<Integer>> components = new ArrayList<>();

    public TarjanSCC(Graph g) {
        this.g = g;
        int n = g.getN();
        disc = new int[n];
        low = new int[n];
        inStack = new boolean[n];
        stack = new Stack<>();
    }

    public SCCResult run() {
        for (int i = 0; i < g.getN(); i++) {
            if (disc[i] == 0) {
                dfs(i);
            }
        }
        return new SCCResult(components, g);
    }

    private void dfs(int u) {
        disc[u] = low[u] = ++time;
        stack.push(u);
        inStack[u] = true;

        for (Edge e : g.getAdj().get(u)) {
            int v = e.v;
            if (disc[v] == 0) {
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (inStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            List<Integer> comp = new ArrayList<>();
            while (true) {
                int v = stack.pop();
                inStack[v] = false;
                comp.add(v);
                if (v == u) break;
            }
            components.add(comp);
        }
    }
}
