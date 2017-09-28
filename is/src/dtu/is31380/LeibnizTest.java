package dtu.is31380;

import static org.junit.Assert.*;

import org.junit.Test;

public class LeibnizTest {

	@Test
	public void test() {
		double res = Leibniz.pi(1000);
		assertEquals(3.14,res,0.005);
	}

}
