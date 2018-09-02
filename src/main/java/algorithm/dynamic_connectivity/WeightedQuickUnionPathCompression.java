package algorithm.dynamic_connectivity;

// Problem: Find whether 2 nodes are connected. It is not required to know the path connecting the 2 nodes.
public class WeightedQuickUnionPathCompression {

	// 2 nodes are part of the same component if they have the same root
	// id[i] represents the parent of node i
	// id[i] = i if the node is not connected to any other node.
	// Space complexity: count (int) + id (int[]) + size (int[])
	// = 4bytes + (N * 4bytes + 24bytes) + (N * 1byte + 24bytes)
	// 24 bytes = (Array length 8bytes + Reference to Object class 8bytes + GC flags 8bytes)
	private int[] id;
	private int count; //number of components
	private byte[] size;

	// Time complexity: O(N)
	public WeightedQuickUnionPathCompression(int N) {
		if (N < 0) throw new IllegalArgumentException();
		count = N;
		id = new int[N];
		size = new byte[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
			size[i] = 1;
		}
	}

	private void validate(int p) {
		int N = id.length;
		if (p < 0 || p >= N) throw new IllegalArgumentException();
	}

	// Time complexity: O(log N*) better than log N overtime because of the path compression
	public int find(int p) {
		validate(p);
		while (id[p] != p) {
			id[p] = id[id[p]]; // path compression by halving
			p = id[p];
		}
		return p;
	}

	// Time complexity: O(log N*)
	public void union(int p, int q) {
		validate(p);
		validate(q);
		if (!connected(p, q)) {
			int rootp = find(p);
			int rootq = find(q);
			// Link the smaller component to the larger component
			if (size[rootp] > size[rootq]) {
				id[rootq] = rootp;
				size[rootp] += size[rootq];
			} else {
				id[rootp] = rootq;
				size[rootq] += size[rootp];
			}
			count--;
		}
	}

	// Time complexity: O(log N*)
	public boolean connected(int p, int q) {
		validate(p);
		validate(q);
		return find(p) == find(q);
	}

	// Time complexity: O(1)
	public int count() {
		return count;
	}

}
