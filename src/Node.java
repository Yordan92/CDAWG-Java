public class Node {

	long id;
	long suf;
	long length;

	public static final long ROOT = -1;
	public static final long SOURCE = 0;
	public static final long NO_SUF = -2;

	public Node(long id, long suf, long length) {
		this.suf = suf;
		this.length = length;
		this.id = id;
	}

	public Node(long id, long suf) {
		this.suf = suf;
		this.id = id;
	}


	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public long getSuf() {
		return suf;
	}

	public void setSuf(long suf) {
		this.suf = suf;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
