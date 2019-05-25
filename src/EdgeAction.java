import java.util.HashMap;
import java.util.Map;

public class EdgeAction {

    public Map<String, Edge> edges = new HashMap<>();

//    public EdgeAction() {
//        newEdge(Node.ROOT, -1, -1, Node.SOURCE, '-');
//
//    }
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

    }

    public static String generateKey(Edge edge) {
        return String.format("%d_%s", edge.getStartNode(), edge.getStartChar());
    }

}
