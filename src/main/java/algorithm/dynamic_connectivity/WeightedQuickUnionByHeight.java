package algorithm.dynamic_connectivity;

// Problem: Find whether 2 nodes are connected. It is not required to know the path connecting the 2 nodes.
public class WeightedQuickUnionByHeight {

	// 2 nodes are part of the same component if they have the same root
	// id[i] represents the parent of node i
	// id[i] = i if the node is not connected to any other node.
	// Space complexity: count (int) + id (int[]) + height (int[])
	// = 4bytes + (N * 4bytes + 24bytes) + (N * 1byte + 24bytes)
	// 24 bytes = (Array length 8bytes + Reference to Object class 8bytes + GC flags 8bytes)
	private int[] id;
	private int count;
	private byte[] height;

	// Time complexity: O(N)
	public WeightedQuickUnionByHeight(int N) {
		if (N < 0) throw new IllegalArgumentException();
		count = N;
		id = new int[N];
		height = new byte[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
			height[i] = 0;
		}
	}


	// Time complexity: O(log N)
	public int find(int p) {
		if (p < 0 || p >= count) throw new IllegalArgumentException();
		while (id[p] != p) {
			p = id[p];
		}
		return p;
	}

	// Time complexity: O(log N)
	public void union(int p, int q) {
		int rootp = find(p);
		int rootq = find(q);
		// Increase height of root only when linking 2 components of same height
		if (height[rootp] == height[rootq]) {
			id[rootq] = rootp;
			height[rootp]++;
		} else {
			// Link the smaller component to the taller component
			if (height[rootp] > height[rootq]) {
				id[rootq] = rootp;

			} else {
				id[rootp] = rootq;
			}
		}
	}

	// Time complexity: O(log N)
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
