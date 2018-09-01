package algorithm.dynamic_connectivity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class QuickFindTest {

	@Test
	void emptyUnionFindCountIs0(){
		QuickFind qf = new QuickFind(0);
		assertThat(qf.count()).isEqualTo(0);
	}

}