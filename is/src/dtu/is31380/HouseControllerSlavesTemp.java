package dtu.is31380;

import java.util.ArrayList;
import java.util.Collection;

public class HouseControllerSlavesTemp extends AbstractHouseController {

	
	private Collection<Thread> slaves; 
	// define local a local field variables for setpoints & deltas for each room.
	
		
  public HouseControllerSlavesTemp() {
    super(5000); //set timestep to 5000ms
  }
  
  @Override
  protected void execute() {
    HouseControllerInterface intf=getInterface();

    /*
     * HERE take the per room control and implement setpoints - as in 
     * Q1.1)
     */
    //System.out.println("s_door1="+intf.getSensorValue("s_door1"));
    //System.out.println("s_doorx1="+intf.getSensorValue("s_doorx1"));
    
  }

  @Override
  protected void init() {
	  BuildingConfig bc=getInterface().getBuildingConfig();
	  Collection<RoomConfig> rooms =  bc.getRooms();
	  slaves = new ArrayList<Thread>();
	  for (RoomConfig room : rooms) {
		  slaves.add(new Thread(new Runnable() {  
			  	// This is an inline anonymous class definition for your convenience).
	            @Override
	            public void run(){
	                System.out.println(Thread.currentThread().getName() + " is running. I will take care of Room: "+ room.getName()+"'s setpoints");
	            	/*
	            	 * This is the run method of the slave thread. 
	            	 * Make it listen to the "master".
	            	 */
	                
	            }
	        }));
	  }
	  /*
	   * The following is an illustration on how you can start the slaves.
	   */
	  for (Thread slave : slaves) {
		  slave.start();
	  }
  }
  
  

}
