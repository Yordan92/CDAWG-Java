import javafx.util.Pair;

import java.util.List;

public class CDAWG {

    EdgeAction edgeAction;
    NodeAction nodeAction;
    Node root, source, sink;
    String W;

    public CDAWG() {
        edgeAction = new EdgeAction();
        nodeAction = new NodeAction();
        root = new Node(Node.ROOT, Node.NO_SUF, -1);
        source = new Node(Node.SOURCE, Node.ROOT, 0);
//        Edge edge = new Edge(Node.ROOT, -1, -1, Node.SOURCE, '-');
        nodeAction.storeNode(root);
        nodeAction.storeNode(source);
//        edgeAction.storeEdge(edge);
    }

    public void create(final String W) {
        this.W = W;
        int k = 0;
        Node s = source;
        sink = nodeAction.createNode(Node.NO_SUF, 0);
        for (int i = 0; i < W.length(); i++) {
           Pair<Node, Integer> active = update(s, k, i);
           s = active.getKey();
           k = active.getValue();
        }
    }
    public Pair<Node, Integer> update(Node s, int k, int p) {
        char c = W.charAt(p);
        Node oldr = null;
        Node s1 = null;

        while(!checkEndPoint(s, k,p - 1, c)) {
            Node r = null;
            if(k <= p - 1) { // Implicit case. This is when you are in the middle of the edge
                Node sExt = extension(s, k, p - 1);
                if (sExt.equals(s1)) {
                    /*
                        After the following of the suffix links of split node
                        the extension node will be the same as of the split node
                     */
                    redirectEdge(s, k, p - 1, r);
                    Node sSuf = nodeAction.getNode(s.getSuf());
                    Pair<Node, Integer> canonized = canonize(sSuf, k, p - 1);
                    s = canonized.getKey();
                    k = canonized.getValue();
                    continue;

                } else {
                    /*
                        We are int Implicit case and the first time there is no between c and the following letter on that edge
                     */
                    s1 = sExt;
                    r = splitEdge(s, k, p - 1);
                }
            } else { // Explicit case This is when you are on some node
                r = s;
            }
            if (oldr != null) {
                oldr.setSuf(r.getId());
            }
            oldr = r;
            Node sSuf = nodeAction.getNode(s.getSuf());
            Pair<Node, Integer> canonized = canonize(sSuf, k, p - 1);
            s = canonized.getKey();
            k = canonized.getValue();
        }
        if (oldr != null) {
            oldr.setSuf(s.getId());
        }
        return separateNode(s, k, p);
    }

    public Node extension(Node s, int k, int p) {
        if (k > p) {
            return s;
        }
        Edge edge = edgeAction.getEdge(s, W.charAt(k));
        return nodeAction.getNode(edge.endNode);
    }

    public boolean checkEndPoint(Node s, int k, int p, char c) {
       if (k <= p) { // Implicit case.
            Edge edge = edgeAction.getEdge(s, W.charAt(k));
            if (edge == null) {
               return false;
            }
            int k1 = (int) edge.getK();
            return c == W.charAt(k1 + p - k + 1);
        } else {
            return edgeAction.containsEdge(s, c);
        }
    }

    public void redirectEdge(Node s, int k, int p, Node r) {
        Edge edge = edgeAction.getEdge(s, W.charAt(k));
        int p1 = (int) edge.getP();
        edge.setP(p1 + p - k);
        edge.setEndNode(r.getId());
    }

    public Pair<Node, Integer> canonize(Node s, int k, int p) {
        if (k > p) { //Explicit case
            return new Pair<>(s, k);
        }
        Edge e = edgeAction.getEdge(s, W.charAt(k));
        int k1 = (int) e.getK();
        int p1 = (int) e.getP();
        while(p1 - k1 <= p - k) {
            k = k + p1 - k1 + 1; // Go to next node
            s = nodeAction.getNode(e.getEndNode());
            if (k <= p) {
                e = edgeAction.getEdge(s, W.charAt(k));
                k1 = (int) e.getK();
                p1 = (int) e.getP();
            }
        }
        return new Pair<>(s, k);
    }

    public Node splitEdge(Node s, int k, int p) {
        Edge e = edgeAction.getEdge(s, W.charAt(k));
        int k1 = (int) e.getK();
        int p1 = (int) e.getP();
        long sSuccessor = e.getEndNode();
        Node r = nodeAction.createNode(Integer.MAX_VALUE, s.getLength() + p - k + 1);
        //Change s to point to the new Node
        e.setP(k1 + p - k);
        e.setEndNode(r.getId());
        //Create new edge for r to point to successor of s
        edgeAction.newEdge(r.getId(), k1 + p - k + 1, p1, sSuccessor, W.charAt(k1 + p - k + 1));
        return r;
    }

    public Pair<Node,Integer> separateNode(Node s, int k, int p) {
        Pair<Node, Integer> canonized = canonize(s, k, p);
        Node s1 = canonized.getKey();
        int k1 = canonized.getValue();
        if (k1 <= p) { //Implicit case when we are on middle of an edge we don't separate node
            return canonized;
        }
        //Explicit case Solid case
        if (s1.getLength() == s.getLength() + (p - k  + 1)) {
            return canonized;
        }

//          Non-solid case.
//          Create node r1 as a duplication of s1, together with the out-going
//          edges of s1
        Node r1 = nodeAction.createNode(s1.getSuf(), s.getLength() + (p - k + 1));
        s1.setSuf(r1.getId());
        cloneNode(s1, r1);
        while(true) {
           Edge edgeFromSToS1 = edgeAction.getEdge(s, W.charAt(k));
           edgeAction.newEdge(edgeFromSToS1.getStartNode(), edgeFromSToS1.getK(), edgeFromSToS1.getP(), r1.getId(), edgeFromSToS1.getStartChar());
            Node sSuf = nodeAction.getNode(s.getSuf());
            Pair<Node, Integer> canonized1 = canonize(sSuf, k, p - 1);
            s = canonized1.getKey();
            k = canonized1.getValue();
            if (s.equals(s1) && k == k1) {
                break;
            }
        }
        return new Pair<>(r1, p + 1);

    }

    public void cloneNode(Node from, Node to) {
        List<Edge> fromNodeEdges = edgeAction.getEdgesForNode(from);
        for (Edge edge : fromNodeEdges) {
            edgeAction.newEdge(to.getId(), edge.getK(), edge.getP(), edge.getEndNode(), edge.getStartChar());
        }
    }






}
