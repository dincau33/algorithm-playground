package algorithm.dynamic_connectivity;

// Problem: Find whether 2 nodes are connected. It is not required to know the path connecting the 2 nodes.
public class QuickUnion {

	// 2 nodes are part of the same component if they have the same root
	// id[i] represents the parent of node i
	// id[i] = i if the node is not connected to any other node.
	// Space complexity: count (int) + id (int[]) = 4bytes + N * 4bytes + 24bytes
	// 24 bytes = (Array length 8bytes + Reference to Object class 8bytes + GC flags 8bytes)
	private int[] id;
	private int count;

	// Time complexity: O(N)
	public QuickUnion(int N) {
		if (N < 0) throw new IllegalArgumentException();
		count = N;
		id = new int[N];
		for (int i = 0; i < N; i++) id[i] = i;
	}

	// Time complexity: O(tree height)
	public int find(int p) {
		if (p < 0 || p >= count) throw new IllegalArgumentException();
		while (id[p] != p) p = id[p];
		return p;
	}

	// Time complexity: O(tree height)
	public void union(int p, int q) {
		int rootp = find(p);
		int rootq = find(q);
		id[rootq] = rootp;
	}

	// Time complexity: O(tree height)
	public boolean connected(int p, int q) {
		if (p < 0 || p >= count) throw new IllegalArgumentException();
		if (q < 0 || q >= count) throw new IllegalArgumentException();
		return find(p) == find(q);
	}

	// Time complexity: O(1)
	public int count() {
		return count;
	}
}
