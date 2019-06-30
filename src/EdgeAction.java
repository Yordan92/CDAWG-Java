import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EdgeAction {

    public Map<String, Edge> edges = new HashMap<>();
    public Map<Integer, List<Edge>>nodeEdgesMap = new HashMap<>();
    public final Edge ROOT_EDGE = new Edge(Node.ROOT, Node.SOURCE, -1, -1, '-');

    public EdgeAction() {
        storeEdge(ROOT_EDGE);
    }

    public void newEdge(Node node, long k, long p, Node endNode, char startChar) {
        Edge newEdge = new Edge(node.getId(), endNode.getId(), k, p, startChar);
        edges.put(generateKey(newEdge), newEdge);

    }

    public void storeEdge(Edge edge) {
        edges.put(generateKey(edge), edge);
    }

    public void newEdge(long node, long k, long p, long endNode, char startChar) {
        Edge newEdge = new Edge(node, endNode, k, p, startChar);
        edges.put(generateKey(newEdge), newEdge);
        nodeEdgesMap.putIfAbsent((int) node, new ArrayList<>()).add(newEdge);

    }

    public Edge getEdge(Node node, char c) {
        if (Node.ROOT == node.getId()) {
            return ROOT_EDGE;
        }
        return edges.get(generateKey(node, c));
    }

    public List<Edge> getEdgesForNode(Node node) {
        return nodeEdgesMap.get(node.getId());
    }

    public boolean containsEdge(Node node, char c) {
        if (node.getId() == Node.ROOT) {
            return true;
        }
        return edges.containsKey(generateKey(node, c));
    }

    public static String generateKey(Edge edge) {
        return String.format("%d_%s", edge.getStartNode(), edge.getStartChar());
    }

    public static String generateKey(Node node, char c) {
        return String.format("%d_%s", node.getId(), c);
    }

}
