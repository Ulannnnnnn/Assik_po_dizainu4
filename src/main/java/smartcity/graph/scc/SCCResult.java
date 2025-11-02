package smartcity.graph.scc;

import smartcity.model.Edge;
import smartcity.model.Graph;

import java.util.*;

public class SCCResult {

    private final List<List<Integer>> components;
    private final Graph original;

    public SCCResult(List<List<Integer>> components, Graph original) {
        this.components = components;
        this.original = original;
    }

    public void printComponents() {
        int i = 0;
        for (List<Integer> comp : components) {
            System.out.println("SCC #" + i + " size=" + comp.size() + " -> " + comp);
            i++;
        }
    }

    public Graph buildCondensationGraph() {
        int k = components.size();
        Graph dag = new Graph(k, true, original.getWeightModel());

        // сопоставим вершину исходного графа компоненте
        int[] compId = new int[original.getN()];
        for (int i = 0; i < components.size(); i++) {
            for (int v : components.get(i)) {
                compId[v] = i;
            }
        }

        // добавляем рёбра между компонентами
        Set<String> used = new HashSet<>();
        for (int u = 0; u < original.getN(); u++) {
            for (Edge e : original.getAdj().get(u)) {
                int cu = compId[u];
                int cv = compId[e.v];
                if (cu != cv) {
                    String key = cu + "->" + cv;
                    if (!used.contains(key)) {
                        dag.addEdge(cu, cv, e.w);
                        used.add(key);
                    }
                }
            }
        }
        return dag;
    }

    public List<List<Integer>> getComponents() {
        return components;
    }
}
