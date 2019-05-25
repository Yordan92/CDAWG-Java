public class Edge {
	
	long startNode;
	long endNode;
	char startChar;
	long k;
	long p;
	public static long E;

	public Edge(long startNode, long endNode, long k, long p, char startChar) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.k = k;
		this.p = p;
		this.startChar = startChar;
	}


	public char getStartChar() {
		return startChar;
	}

	public void setStartChar(char startChar) {
		this.startChar = startChar;
	}


	public long getStartNode() {
		return startNode;
	}

	public void setStartNode(long startNode) {
		this.startNode = startNode;
	}

	public long getEndNode() {
		return endNode;
	}

	public void setEndNode(long endNode) {
		this.endNode = endNode;
	}

	public long getK() {
		return k;
	}

	public void setK(long k) {
		this.k = k;
	}

	public long getP() {
		return p;
	}

	public void setP(long p) {
		this.p = p;
	}

}
