package algorithm.dynamic_connectivity;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WeightedQuickUnionPathCompressionTest {

	private static final String FILE_PATH_FOLDER = "./src/test/resources/algorithm/dynamic_connectivity/";

	static WeightedQuickUnionPathCompression createWeightedQuickUnionFromFile(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		int N = sc.nextInt();

		WeightedQuickUnionPathCompression qu = new WeightedQuickUnionPathCompression(N);

		while (sc.hasNextLine()) {
			int p = sc.nextInt();
			int q = sc.nextInt();
			qu.union(p,q);
		}

		return qu;
	}

	@Test
	void emptyUnionFindCountIs0() {
		WeightedQuickUnionPathCompression qu = new WeightedQuickUnionPathCompression(0);
		assertThat(qu.count()).isEqualTo(0);
	}

	@Test
	void cannotInitializeWeightedQuickUnionIfNegativeN() {
		assertThrows(IllegalArgumentException.class, () -> new WeightedQuickUnionPathCompression(-1));
	}

	@Test
	void cannotUnionNodeOutOfBound() {
		WeightedQuickUnionPathCompression qu = new WeightedQuickUnionPathCompression(3);
		assertThrows(IllegalArgumentException.class, () -> qu.union(0, -1));
		assertThrows(IllegalArgumentException.class, () -> qu.union(-1, 1));
		assertThrows(IllegalArgumentException.class, () -> qu.union(3, 1));
		assertThrows(IllegalArgumentException.class, () -> qu.union(1, 3));
	}

	@Test
	void cannotAssessConnectivityOfNodeOutOfBound() {
		WeightedQuickUnionPathCompression qu = new WeightedQuickUnionPathCompression(3);
		assertThrows(IllegalArgumentException.class, () -> qu.connected(0, -1));
		assertThrows(IllegalArgumentException.class, () -> qu.connected(-1, 1));
		assertThrows(IllegalArgumentException.class, () -> qu.connected(3, 1));
		assertThrows(IllegalArgumentException.class, () -> qu.connected(1, 3));
	}

	@Test
	void nodesAreConnectedIfPartOfSameComponent() {
		WeightedQuickUnionPathCompression qu = new WeightedQuickUnionPathCompression(2);
		qu.union(0,1);
		assertThat(qu.connected(0,1)).isTrue();
		assertThat(qu.count()).isEqualTo(1);
	}

	@Test
	void union2NodesAlreadyConnectedHasNoSideEffect() {
		WeightedQuickUnionPathCompression qu = new WeightedQuickUnionPathCompression(2);
		qu.union(0, 1);
		qu.union(0, 1);
		assertThat(qu.connected(0, 1)).isTrue();
		assertThat(qu.count()).isEqualTo(1);
	}

	@Test
	void nodesAreNotConnectIfNotPartOfSameComponent() {
		WeightedQuickUnionPathCompression qu = new WeightedQuickUnionPathCompression(3);
		qu.union(0,1);
		assertThat(qu.connected(0,2)).isFalse();
		assertThat(qu.connected(1,2)).isFalse();
	}

	@Test
	void union2ComponentsWithMultipleNodes(){
		WeightedQuickUnionPathCompression qu = new WeightedQuickUnionPathCompression(7);
		qu.union(0,1);
		qu.union(1,2);
		qu.union(3,4);
		qu.union(4,6);
		qu.union(0,6);
		assertThat(qu.connected(0,3)).isTrue();
		assertThat(qu.connected(0,5)).isFalse();
	}

	@Test
	void unionFindBasedTinyUFFile() throws FileNotFoundException {
		File tinyUF = new File(FILE_PATH_FOLDER + "/tinyUF.txt");
		WeightedQuickUnionPathCompression qu = createWeightedQuickUnionFromFile(tinyUF);
		assertThat(qu.connected(1,7)).isTrue();
		assertThat(qu.connected(0,9)).isFalse();
	}

	@Test
	void unionFindBasedMediumUFFile() throws FileNotFoundException {
		File mediumUF = new File(FILE_PATH_FOLDER + "/mediumUF.txt");
		WeightedQuickUnionPathCompression qu = createWeightedQuickUnionFromFile(mediumUF);
		assertThat(qu.connected(486,511)).isTrue();
		assertThat(qu.connected(0,9)).isFalse();
	}
}