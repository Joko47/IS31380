package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import dtu.is31380.Leibniz;

public class LeibnizTest {

	@Test
	public void test() {
		double res = Leibniz.pi(1000);
		assertEquals(3.14,res,0.005);
	}

}
