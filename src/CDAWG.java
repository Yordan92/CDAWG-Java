import java.util.HashMap;
import java.util.Map;

public class CDAWG {

    EdgeAction edgeAction;
    NodeAction nodeAction;
    Node root, source, sink;

    public CDAWG() {
        edgeAction = new EdgeAction();
        nodeAction = new NodeAction();
        root = new Node(Node.ROOT, Node.NO_SUF, -1);
        source = new Node(Node.SOURCE, Node.ROOT, 0);
        Edge edge = new Edge(Node.ROOT, -1, -1, Node.SOURCE, '-');
        nodeAction.storeNode(root);
        nodeAction.storeNode(source);
        edgeAction.storeEdge(edge);
    }

    public void create(final String W) {
        long k = 0;
        Node s = source;
        sink = nodeAction.createNode(Node.NO_SUF, 0);
        for (long i = 0; i < W.length(); i++) {
           update(s, k, i);
        }
    }
    public void update(Node s, long k, long p) {

    }

    public boolean checkEndPoint() {
        return false;
    }

    public void redirectEdge() {}





}
