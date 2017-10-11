package dtu.is31380;

import java.util.ArrayList;

public class SimpleController extends AbstractHouseController{

	double[] setpoint = {21.0,21.0,21.0,21.0,21.0,21.0,21.0,21.0};
	double[] delta = {1.5,1.5,1.5,1.5,1.5,1.5,1.5,1.5};
	String Sensor="s_tempr";
	String Actuator="a_htrr";
	int acc = 1;
	
	public SimpleController() {
	    super(5000); //set timestep to 5000ms
	  }
  
  @Override
  protected void execute() {
	  HouseControllerInterface intf=getInterface();
    // loop through "rooms"
    for (int i=0; i<8; i++) { 
    	if(i==0) {
    		Sensor="s_tempmain";
    		Actuator="a_htrmain";
    		acc = 2;
		}
    	else if(i==7) {
    		Sensor="s_tempr"+i;
    		Actuator="a_htrr"+i;
    		acc=2;
    	}
    	else {
    		Sensor="s_tempr"+i;
    		Actuator="a_htrr"+i;
    		acc=1;
    	}
    	
        System.out.println("T_room"+ (i));
        System.out.println("Temperature is: "+intf.getSensorValue(Sensor));
        System.out.println("Setpoint is: "+setpoint[i]);
        System.out.println(intf.getActuatorSetpoint(Actuator+"_1"));
    
        // conditions for turning on
		if (setpoint[i] - delta[i] > intf.getSensorValue(Sensor)  ) {
				if(intf.getActuatorSetpoint(Actuator+"_1") != 1.0) {
					System.out.println(setpoint[i] - intf.getSensorValue(Sensor)+" : turning ON");
					// turning actuator on
					if(acc==2)
						intf.setActuator((Actuator+"_2"), 1.0);
					intf.setActuator((Actuator+"_1"), 1.0); //switch heater in room i on
				}
			}
		

		// Conditions for turning off
		if (setpoint[i] + delta[i] < intf.getSensorValue(Sensor)  ) {
				if(intf.getActuatorSetpoint(Actuator+"_1") != 0.0) {
					System.out.println(setpoint[i] - intf.getSensorValue(Sensor)+" : turning OFF");
					// Turnin actuator off
					if(acc==2)
						intf.setActuator((Actuator+"_2"), 0.0);
					intf.setActuator((Actuator+"_1"), 0.0); //switch heater in room i off
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
	    for (int i=0; i<8; i++) {
	    	if(i==0) {
	    		intf.setActuator(("a_htrmain_1"), 0.0);
	    		intf.setActuator(("a_htrmain_2"), 0.0);
	    	}
	    	else {
	    		intf.setActuator(("a_htrr"+(i)+"_1"), 0.0);
	    	}
	    
	    }
	    intf.setActuator(("a_htrr7_2"), 0.0);
  }

}
