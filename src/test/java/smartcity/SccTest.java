package smartcity;

import org.junit.jupiter.api.Test;
import smartcity.model.Graph;
import smartcity.graph.scc.SCCResult;
import smartcity.graph.scc.TarjanSCC;

import static org.junit.jupiter.api.Assertions.*;

public class SccTest {

    @Test
    public void testSimpleCycle() {
        // граф: 0 -> 1 -> 2 -> 0 (одна SCC) и 3 -> 4 (отдельно)
        Graph g = new Graph(5, true, "edge");
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 0, 1);
        g.addEdge(3, 4, 1);

        TarjanSCC tarjan = new TarjanSCC(g);
        SCCResult res = tarjan.run();

        // должно быть как минимум 1 компонента размера 3
        boolean has3 = res.getComponents().stream().anyMatch(c -> c.size() == 3);
        assertTrue(has3, "SCC with 3 nodes should exist");
    }
}
