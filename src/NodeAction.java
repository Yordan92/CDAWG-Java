import java.util.HashMap;
import java.util.Map;

public class NodeAction {

    public Map<Long, Node> nodes = new HashMap<>();
    public long numberOfNodes = 1;

    public void storeNode(Node newNode) {
        nodes.put(newNode.getId(), newNode);
    }

    public Node createNode(long suf, long length) {
        Node node = new Node(numberOfNodes++, suf, length);
        storeNode(node);
        return node;
    }

    public Node getNode(long id) {
        return nodes.get(id);
    }

//    public void NodeAction() {
//        Node root = new Node(Node.ROOT, Node.NO_SUF);
//        Node source = new Node(Node.SOURCE, Node.ROOT);
//        nodes.put(root.getId(), root);
//        nodes.put(source.getId(), source);
//    }
}
