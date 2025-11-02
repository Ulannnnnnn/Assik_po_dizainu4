package smartcity;

import org.junit.jupiter.api.Test;
import smartcity.model.Graph;
import smartcity.graph.dagsp.DagShortestPath;
import smartcity.graph.topo.KahnToposort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DagSpTest {

    @Test
    public void testShortestOnDag() {
        // DAG:
        // 0 -> 1 (1)
        // 0 -> 2 (4)
        // 1 -> 2 (1)
        Graph g = new Graph(3, true, "edge");
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 4);
        g.addEdge(1, 2, 1);

        KahnToposort topo = new KahnToposort(g);
        List<Integer> order = topo.sort();

        DagShortestPath sp = new DagShortestPath(g);
        int[] dist = sp.shortestFrom(0, order);

        // кратчайший путь 0 -> 2 должен быть 0->1->2 = 2
        assertEquals(2, dist[2]);
    }
}
