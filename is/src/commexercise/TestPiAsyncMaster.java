package commexercise;

import java.util.Arrays;
import commexercise.rpc.CallbackListener;
import commexercise.rpc.RpcClient;
import commexercise.rpc.RpcClientImpl;

public class TestPiAsyncMaster {

  private static long slavescore;
  private static boolean slave_finished;
  private static final Object slave_monitor = new Object();
  private static long starttime;
  
  public static void main(String[] args) throws Exception {
    if (args.length<1) {
      System.out.println("Usage: TestPiAsyncMaster <rounds>");
      System.exit(1);
    }
    slave_finished=false;
    int n=3;
    long rounds=Long.valueOf(args[0]).longValue()/n;
    
    RpcClient client1 = new RpcClientImpl("http://localhost:8080");
    RpcClient client2 = new RpcClientImpl("http://localhost:8082");
    
    // create a callback listener for use with the call
    CallbackListener asyncListener = new CallbackListener() {
        @Override
        public void functionExecuted(long callID, String[] response) {
          if (callID==1L) {
            slavescore=Long.valueOf(response[0]).longValue();
            slave_finished=true;
            synchronized(slave_monitor) {
              slave_monitor.notify(); 
            }
            System.out.println("Slave finished in "+
                ((double)(System.currentTimeMillis()-starttime)/1000)+"s; score="+
                slavescore+"/"+rounds+".");
          }
        }

        @Override
        public void functionFailed(long callID, Exception e) {
          e.printStackTrace();
        }
    };
    
    starttime=System.currentTimeMillis();

    int client1load=80;
    int client2load=40;
    System.out.println("Asking slave1 to calculate "+(rounds*client1load)/100+" rounds.");
    client1.callAsync(TestPiSlave.PIFUNCTION,
                     new String[]{Long.toString((rounds*client1load)/100)}, 1L, asyncListener);
    
    System.out.println("Asking slave2 to calculate "+(rounds*client2load)/100+" rounds.");
    client2.callAsync(TestPiSlave.PIFUNCTION,
                     new String[]{Long.toString((rounds*client2load)/100)}, 1L, asyncListener);
    
    System.out.println("Asking master (myself) to calculate "+rounds+" rounds.");
    long starttime2=System.currentTimeMillis();
    long masterscore=PiCalculator.picalc(rounds);
    System.out.println("Master finished in "+
        ((double)(System.currentTimeMillis()-starttime2)/1000)+"s; score="+
        masterscore+"/"+rounds+".");
    
    synchronized(slave_monitor) {
      while (!slave_finished) { 
        slave_monitor.wait();
      }
    }
    
    double pi = 4.0 * (double)(masterscore+slavescore)/(double)(rounds*2);
    
    System.out.println("Estimating Pi="+pi+", total time="+
        ((double)(System.currentTimeMillis()-starttime)/1000)+"s.");
    
  }
  
}
