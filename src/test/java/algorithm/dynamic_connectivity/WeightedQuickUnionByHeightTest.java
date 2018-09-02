package algorithm.dynamic_connectivity;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WeightedQuickUnionByHeightTest {

	private static final String FILE_PATH_FOLDER = "./src/test/resources/algorithm/dynamic_connectivity/";

	static WeightedQuickUnionByHeight createWeightedQuickUnionFromFile(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		int N = sc.nextInt();

		WeightedQuickUnionByHeight qu = new WeightedQuickUnionByHeight(N);

		while (sc.hasNextLine()) {
			int p = sc.nextInt();
			int q = sc.nextInt();
			qu.union(p,q);
		}

		return qu;
	}

	@Test
	void emptyUnionFindCountIs0() {
		WeightedQuickUnionByHeight qu = new WeightedQuickUnionByHeight(0);
		assertThat(qu.count()).isEqualTo(0);
	}

	@Test
	void cannotInitializeWeightedQuickUnionIfNegativeN() {
		assertThrows(IllegalArgumentException.class, () -> new WeightedQuickUnionByHeight(-1));
	}

	@Test
	void cannotUnionNodeOutOfBound() {
		WeightedQuickUnionByHeight qu = new WeightedQuickUnionByHeight(3);
		assertThrows(IllegalArgumentException.class, () -> qu.union(0, -1));
		assertThrows(IllegalArgumentException.class, () -> qu.union(-1, 1));
		assertThrows(IllegalArgumentException.class, () -> qu.union(3, 1));
		assertThrows(IllegalArgumentException.class, () -> qu.union(1, 3));
	}

	@Test
	void cannotAssessConnectivityOfNodeOutOfBound() {
		WeightedQuickUnionByHeight qu = new WeightedQuickUnionByHeight(3);
		assertThrows(IllegalArgumentException.class, () -> qu.connected(0, -1));
		assertThrows(IllegalArgumentException.class, () -> qu.connected(-1, 1));
		assertThrows(IllegalArgumentException.class, () -> qu.connected(3, 1));
		assertThrows(IllegalArgumentException.class, () -> qu.connected(1, 3));
	}

	@Test
	void nodesAreConnectedIfPartOfSameComponent() {
		WeightedQuickUnionByHeight qu = new WeightedQuickUnionByHeight(2);
		qu.union(0,1);
		assertThat(qu.connected(0,1)).isTrue();
	}

	@Test
	void nodesAreNotConnectIfNotPartOfSameComponent() {
		WeightedQuickUnionByHeight qu = new WeightedQuickUnionByHeight(3);
		qu.union(0,1);
		assertThat(qu.connected(0,2)).isFalse();
		assertThat(qu.connected(1,2)).isFalse();
	}

	@Test
	void union2ComponentsWithMultipleNodes(){
		WeightedQuickUnionByHeight qu = new WeightedQuickUnionByHeight(7);
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
		WeightedQuickUnionByHeight qu = createWeightedQuickUnionFromFile(tinyUF);
		assertThat(qu.connected(1,7)).isTrue();
		assertThat(qu.connected(0,9)).isFalse();
	}

	@Test
	void unionFindBasedMediumUFFile() throws FileNotFoundException {
		File mediumUF = new File(FILE_PATH_FOLDER + "/mediumUF.txt");
		WeightedQuickUnionByHeight qu = createWeightedQuickUnionFromFile(mediumUF);
		assertThat(qu.connected(486,511)).isTrue();
		assertThat(qu.connected(0,9)).isFalse();
	}
}