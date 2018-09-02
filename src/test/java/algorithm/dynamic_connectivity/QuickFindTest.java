package algorithm.dynamic_connectivity;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.*;

class QuickFindTest {

	private static final String FILE_PATH_FOLDER = "./src/test/resources/algorithm/dynamic_connectivity/";

	static QuickFind createQuickFindFromFile(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		int N = sc.nextInt();

		QuickFind qf = new QuickFind(N);

		while (sc.hasNextLine()) {
			int p = sc.nextInt();
			int q = sc.nextInt();
			qf.union(p,q);
		}

		return qf;
	}

	@Test
	void emptyUnionFindCountIs0() {
		QuickFind qf = new QuickFind(0);
		assertThat(qf.count()).isEqualTo(0);
	}

	@Test
	void cannotInitializeQuickFindIfNegativeN() {
		assertThrows(IllegalArgumentException.class, () -> new QuickFind(-1));
	}

	@Test
	void cannotUnionNodeOutOfBound() {
		QuickFind qf = new QuickFind(3);
		assertThrows(IllegalArgumentException.class, () -> qf.union(0, -1));
		assertThrows(IllegalArgumentException.class, () -> qf.union(-1, 1));
		assertThrows(IllegalArgumentException.class, () -> qf.union(3, 1));
		assertThrows(IllegalArgumentException.class, () -> qf.union(1, 3));
	}

	@Test
	void cannotAssessConnectivityOfNodeOutOfBound() {
		QuickFind qf = new QuickFind(3);
		assertThrows(IllegalArgumentException.class, () -> qf.connected(0, -1));
		assertThrows(IllegalArgumentException.class, () -> qf.connected(-1, 1));
		assertThrows(IllegalArgumentException.class, () -> qf.connected(3, 1));
		assertThrows(IllegalArgumentException.class, () -> qf.connected(1, 3));
	}

	@Test
	void nodesAreConnectedIfPartOfSameComponent() {
		QuickFind qf = new QuickFind(2);
		qf.union(0,1);
		assertThat(qf.connected(0,1)).isTrue();
		assertThat(qf.count()).isEqualTo(1);
	}

	@Test
	void union2NodesAlreadyConnectedHasNoSideEffect() {
		QuickFind qf = new QuickFind(2);
		qf.union(0, 1);
		qf.union(0, 1);
		assertThat(qf.connected(0, 1)).isTrue();
		assertThat(qf.count()).isEqualTo(1);
	}

	@Test
	void nodesAreNotConnectIfNotPartOfSameComponent() {
		QuickFind qf = new QuickFind(3);
		qf.union(0,1);
		assertThat(qf.connected(0,2)).isFalse();
		assertThat(qf.connected(1,2)).isFalse();
	}

	@Test
	void union2ComponentsWithMultipleNodes(){
		QuickFind qf = new QuickFind(7);
		qf.union(0,1);
		qf.union(1,2);
		qf.union(3,4);
		qf.union(4,6);
		qf.union(0,6);
		assertThat(qf.connected(0,3)).isTrue();
		assertThat(qf.connected(0,5)).isFalse();
	}

	@Test
	void unionFindBasedTinyUFFile() throws FileNotFoundException {
		File tinyUF = new File(FILE_PATH_FOLDER + "/tinyUF.txt");
		QuickFind qf = createQuickFindFromFile(tinyUF);
		assertThat(qf.connected(1,7)).isTrue();
		assertThat(qf.connected(0,9)).isFalse();
	}

	@Test
	void unionFindBasedMediumUFFile() throws FileNotFoundException {
		File mediumUF = new File(FILE_PATH_FOLDER + "/mediumUF.txt");
		QuickFind qf = createQuickFindFromFile(mediumUF);
		assertThat(qf.connected(486,511)).isTrue();
		assertThat(qf.connected(0,9)).isFalse();
	}
}