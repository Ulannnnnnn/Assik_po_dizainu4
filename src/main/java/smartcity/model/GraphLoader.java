package smartcity.model;

import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GraphLoader {

    private final Gson gson = new Gson();

    public Graph loadGraph(String filePath, String graphId) throws IOException {
        String json = Files.readString(Path.of(filePath));
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();

        // формат: { "category": "...", "graphs": [ {...}, {...} ] }
        JsonArray graphs = root.getAsJsonArray("graphs");
        if (graphs == null) {
            throw new IOException("No 'graphs' array in " + filePath);
        }

        JsonObject target = null;
        for (JsonElement el : graphs) {
            JsonObject g = el.getAsJsonObject();
            if (g.get("id").getAsString().equals(graphId)) {
                target = g;
                break;
            }
        }

        if (target == null) {
            throw new IOException("Graph id " + graphId + " not found in " + filePath);
        }

        int n = target.get("n").getAsInt();
        boolean directed = target.get("directed").getAsBoolean();
        String weightModel = target.has("weight_model") ? target.get("weight_model").getAsString() : "edge";

        Graph graph = new Graph(n, directed, weightModel);

        if (target.has("source")) {
            graph.setSource(target.get("source").getAsInt());
        }

        JsonArray edges = target.getAsJsonArray("edges");
        for (JsonElement e : edges) {
            JsonObject eo = e.getAsJsonObject();
            int u = eo.get("u").getAsInt();
            int v = eo.get("v").getAsInt();
            int w = eo.has("w") ? eo.get("w").getAsInt() : 1;
            graph.addEdge(u, v, w);
        }

        return graph;
    }
}
