package smartcity.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private final int n;
    private final boolean directed;
    private final List<List<Edge>> adj;
    private final String weightModel; // "edge" или "node"
    private int source = 0;

    public Graph(int n, boolean directed, String weightModel) {
        this.n = n;
        this.directed = directed;
        this.weightModel = weightModel != null ? weightModel : "edge";
        this.adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(u, v, w));
        if (!directed) {
            adj.get(v).add(new Edge(v, u, w));
        }
    }

    public int getN() {
        return n;
    }

    public boolean isDirected() {
        return directed;
    }

    public List<List<Edge>> getAdj() {
        return adj;
    }

    public String getWeightModel() {
        return weightModel;
    }

    public void setSource(int s) {
        this.source = s;
    }

    public int getSourceOrDefault(int def) {
        return source >= 0 ? source : def;
    }

    public int countEdges() {
        int c = 0;
        for (List<Edge> list : adj) {
            c += list.size();
        }
        return c;
    }
}
