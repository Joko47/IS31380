package commexercise;

import java.util.Arrays;
import commexercise.rpc.CallListener;
import commexercise.rpc.RpcServer;
import commexercise.rpc.RpcServerImpl;
import dtu.is31380.Leibniz;

public class TestPiSlave {
  
  public static final String PIFUNCTION="picalc";
  public static final String LEIBNITZ="lebinitzcalc";
  
 
  
  public static void main(String[] args) throws Exception {

	  
	  
	int server_port = 8080;
	RpcServer server = new RpcServerImpl(server_port).start();
    System.out.println("RPC server started on port: "+server_port);

    // add a call listener that will get called when a client does an RPC call to the server
    server.setCallListener(new CallListener() {
      @Override
      public String[] receivedSyncCall(String function, String[] fargs) throws Exception {
        if (function.equals(PIFUNCTION)) {
          if ((fargs!=null) && (fargs.length>0)) {
        	  long rounds=Long.valueOf(fargs[0]).longValue();
        	  double sum = Leibniz.pi((int)rounds);
        	  String string = Double.toString(sum);
        	  System.out.println("Returning result of "+string);
        	  return new String[]{string};
        	  
        	  
        	  /*long rounds=Long.valueOf(fargs[0]).longValue();
            System.out.println("PIFUNCTION called with "+rounds+" rounds");
            long score=PiCalculator.picalc(rounds);
            System.out.println("Returning score of: "+score);
            return new String[]{Long.toString(score)};
          }
        }
        else if (function.equals(LEIBNITZ)){
        	if ((fargs!=null) && (fargs.length>0)) {
        		Long n = Long.valueOf(fargs[0]).longValue();
        		System.out.println("Pi calculated with "+n+" iterations");
        		double sum = 0;
        	    for (int i=0; i < n; i++) {
        	    sum += Math.pow(-1, i)/(2.0 * i + 1);
        	    System.out.println("Returning score of: "+sum);    
        	    }
        	    System.out.println("Returning score of: "+4*sum); 
        	    return new String[]{Double.toString(4.0 * sum)};
        	}*/
          }
        	
        }
        else {
          return new String[]{"Function '",function,"' does not exist."};
        }
        return null;
      }

      @Override
      public String[] receivedAsyncCall(String function, String[] args, long callID) throws Exception {
        return receivedSyncCall(function,args);
      }
    });
  }
  
}
