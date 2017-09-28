package commexercise;

import static org.junit.Assert.*;

import org.junit.Test;

import commexercise.rpc.RpcClient;
import commexercise.rpc.RpcClientImpl;

public class TestPiSyncMasterTest {

	@Test
	public void test() throws Exception {
		long rounds = 1000000;
		
		RpcClient client = new RpcClientImpl("http://localhost:8080");
		
		String[] reply = client.callSync(TestPiSlave.PIFUNCTION,
                new String[]{Long.toString(rounds)});
		
		long slavescore=Long.valueOf(reply[0]).longValue();
		
		double pi = 4.0 * (double)(slavescore)/(double)(rounds);
		
		assertEquals(3.14,pi,0.005);
		
	}

}
