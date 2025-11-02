package smartcity;

import org.junit.jupiter.api.Test;
import smartcity.model.Graph;
import smartcity.graph.topo.KahnToposort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TopoTest {

    @Test
    public void testSimpleDagOrder() {
        // 0 -> 1 -> 2, 0 -> 3
        Graph g = new Graph(4, true, "edge");
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(0, 3, 1);

        KahnToposort topo = new KahnToposort(g);
        List<Integer> order = topo.sort();

        // топопорядок должен начинаться с 0
        assertEquals(0, order.get(0));
        // 1 не может идти раньше 0
        assertTrue(order.indexOf(1) > order.indexOf(0));
        // 2 не может идти раньше 1
        assertTrue(order.indexOf(2) > order.indexOf(1));
    }
}
