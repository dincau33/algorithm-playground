package algorithm.dynamic_connectivity;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QuickUnionTest {

	private static final String FILE_PATH_FOLDER = "./src/test/resources/algorithm/dynamic_connectivity/";

	static QuickUnion createQuickUnionFromFile(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		int N = sc.nextInt();

		QuickUnion qu = new QuickUnion(N);

		while (sc.hasNextLine()) {
			int p = sc.nextInt();
			int q = sc.nextInt();
			qu.union(p,q);
		}

		return qu;
	}

	@Test
	void emptyUnionFindCountIs0() {
		QuickUnion qu = new QuickUnion(0);
		assertThat(qu.count()).isEqualTo(0);
	}

	@Test
	void cannotInitializeQuickUnionIfNegativeN() {
		assertThrows(IllegalArgumentException.class, () -> new QuickUnion(-1));
	}

	@Test
	void cannotUnionNodeOutOfBound() {
		QuickUnion qu = new QuickUnion(3);
		assertThrows(IllegalArgumentException.class, () -> qu.union(0, -1));
		assertThrows(IllegalArgumentException.class, () -> qu.union(-1, 1));
		assertThrows(IllegalArgumentException.class, () -> qu.union(3, 1));
		assertThrows(IllegalArgumentException.class, () -> qu.union(1, 3));
	}

	@Test
	void cannotAssessConnectivityOfNodeOutOfBound() {
		QuickUnion qu = new QuickUnion(3);
		assertThrows(IllegalArgumentException.class, () -> qu.connected(0, -1));
		assertThrows(IllegalArgumentException.class, () -> qu.connected(-1, 1));
		assertThrows(IllegalArgumentException.class, () -> qu.connected(3, 1));
		assertThrows(IllegalArgumentException.class, () -> qu.connected(1, 3));
	}

	@Test
	void nodesAreConnectedIfPartOfSameComponent() {
		QuickUnion qu = new QuickUnion(2);
		qu.union(0,1);
		assertThat(qu.connected(0,1)).isTrue();
	}

	@Test
	void nodesAreNotConnectIfNotPartOfSameComponent() {
		QuickUnion qu = new QuickUnion(3);
		qu.union(0,1);
		assertThat(qu.connected(0,2)).isFalse();
		assertThat(qu.connected(1,2)).isFalse();
	}

	@Test
	void union2ComponentsWithMultipleNodes(){
		QuickUnion qu = new QuickUnion(7);
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
		QuickUnion qu = createQuickUnionFromFile(tinyUF);
		assertThat(qu.connected(1,7)).isTrue();
		assertThat(qu.connected(0,9)).isFalse();
	}

	@Test
	void unionFindBasedMediumUFFile() throws FileNotFoundException {
		File mediumUF = new File(FILE_PATH_FOLDER + "/mediumUF.txt");
		QuickUnion qu = createQuickUnionFromFile(mediumUF);
		assertThat(qu.connected(486,511)).isTrue();
		assertThat(qu.connected(0,9)).isFalse();
	}
	
}