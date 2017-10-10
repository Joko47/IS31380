package dtu.is31380;

import java.util.ArrayList;

public class SimpleController extends AbstractHouseController{

	double[] setpoint = {21.0,21.0,21.0,21.0,21.0,21.0,21.0,21.0};
	double[] delta = {1.5,1.5,1.5,1.5,1.5,1.5,1.5,1.5};
	
	public SimpleController() {
	    super(5000); //set timestep to 5000ms
	  }
  
  @Override
  protected void execute() {
	  HouseControllerInterface intf=getInterface();
    // loop through "rooms"
    for (int i=0; i<7; i++) { 
    	
    	
        System.out.println("T_room"+ (i+1));
        System.out.println("Temperature is: "+intf.getSensorValue("s_tempr"+ (i+1)));
        System.out.println("Setpoint is: "+setpoint[i+1]);
        System.out.println(intf.getActuatorSetpoint("a_htrr"+(i+1)+"_1"));
    
        // conditions for turning on
		if (setpoint[i+1] - delta[i+1] > intf.getSensorValue("s_tempr"+(i+1))  ) {
				if(intf.getActuatorSetpoint("a_htrr"+(i+1)+"_1") != 1.0) {
					System.out.println(setpoint[i+1] - intf.getSensorValue("s_tempr"+(i+1))+" : turning ON");
					// turning actuator on
					intf.setActuator(("a_htrr"+(i+1)+"_1"), 1.0); //switch heater in room i on
				}
			}
		

		// Conditions for turning off
		if (setpoint[i+1] + delta[i+1] < intf.getSensorValue("s_tempr"+(i+1))  ) {
				if(intf.getActuatorSetpoint("a_htrr"+(i+1)+"_1") != 0.0) {
					System.out.println(setpoint[i+1] - intf.getSensorValue("s_tempr"+(i+1))+" : turning OFF");
					// Turnin actuator off
					intf.setActuator(("a_htrr"+(i+1)+"_1"), 0.0); //switch heater in room i off
				}
			}
		}
    	System.out.println("");
    	//System.out.println("T_room1="+intf.getSensorValue("s_tempr1"));
  	}
 
    
  

  @Override
  protected void init() {
	  // Just for show
	  HouseControllerInterface intf=getInterface();
	    BuildingConfig bc=getInterface().getBuildingConfig();
	    ArrayList<RoomConfig> rooms=bc.getRooms();
	    System.out.println("Rooms: "+rooms.toString());
	    for (int i=0; i<7; i++) {
	    	intf.setActuator(("a_htrr"+(i+1)+"_1"), 0.0);
	    }
	    
	    

	    
  }


}
