package smartcity;

import smartcity.model.Graph;
import smartcity.model.GraphLoader;
import smartcity.graph.scc.SCCResult;
import smartcity.graph.scc.TarjanSCC;
import smartcity.graph.topo.KahnToposort;
import smartcity.graph.dagsp.DagShortestPath;
import smartcity.graph.dagsp.DagLongestPath;

import java.nio.file.Paths;
import java.util.List;

public class App {

    public static void main(String[] args) {
        // дефолты, если запускать просто так из IDE
        String file = "data/small_graphs.json";
        String graphId = "S1";

        // можно передать аргументы: --file data/medium_graphs.json --graph M2
        for (int i = 0; i + 1 < args.length; i += 2) {
            if (args[i].equals("--file")) {
                file = args[i + 1];
            } else if (args[i].equals("--graph")) {
                graphId = args[i + 1];
            }
        }

        try {
            GraphLoader loader = new GraphLoader();
            Graph g = loader.loadGraph(Paths.get(file).toString(), graphId);

            System.out.println("Loaded graph: " + graphId + " from " + file);
            System.out.println("n = " + g.getN() + ", directed=" + g.isDirected());
            System.out.println("edges = " + g.countEdges());
            System.out.println();

            // 1) SCC
            TarjanSCC tarjan = new TarjanSCC(g);
            SCCResult scc = tarjan.run();
            System.out.println("SCC components:");
            scc.printComponents();
            System.out.println();

            // 2) condensation + topo
            Graph condensed = scc.buildCondensationGraph();
            System.out.println("Condensation graph has " + condensed.getN() + " nodes (SCCs).");

            KahnToposort topo = new KahnToposort(condensed);
            List<Integer> order = topo.sort();
            System.out.println("Topological order of components: " + order);
            System.out.println();

            // 3) DAG shortest / longest
            // возьмем источник из исходного файла, если он был
            int source = g.getSourceOrDefault(0);

            DagShortestPath sp = new DagShortestPath(condensed);
            int[] dist = sp.shortestFrom(source % condensed.getN(), order); // source может быть > кол-ва компонент
            System.out.println("Shortest distances on condensation DAG (from comp " + (source % condensed.getN()) + "):");
            for (int i = 0; i < dist.length; i++) {
                System.out.println("comp " + i + " = " + dist[i]);
            }

            DagLongestPath lp = new DagLongestPath(condensed);
            int[] longDist = lp.longest(order);
            System.out.println("\nLongest path (critical) length in condensation DAG = " + lp.getMaxDistance(longDist));

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
