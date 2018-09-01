package algorithm.dynamic_connectivity;

// Problem: Find whether 2 nodes are connected. It is not required to know the path connecting the 2 nodes.
public class QuickFind {

	// 2 nodes are part of the same component if they are connected
	// id[i] represents the component identifier the node is part of
	// id[i] = i if the node is not connected to any other node.
	// Space complexity: count (int) + id (int[]) = 4bytes + N * 4bytes + 24bytes
	// (Array length 8bytes + Reference to Object class 8bytes + GC flags 8bytes)
	private int[] id;
	private int count;

	public QuickFind(int N) {

	}

	public void union(int p, int q) {

	}

	public boolean connected(int p, int q) {
		return false;
	}

	public int count() {
		return count;
	}

}
