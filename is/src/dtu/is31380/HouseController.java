package dtu.is31380;

import java.util.ArrayList;

public class HouseController extends AbstractHouseController implements TemperatureGUIListener{

	double setPoint = 20.0;
	double delta = 0.0;
	
	public HouseController() {
	    super(5000); //set timestep to 5000ms
	  }
  
  @Override
  protected void execute() {
    HouseControllerInterface intf=getInterface();
    // loop through "rooms"
    for (int i=0; i<7; i++) { 
    	
        System.out.println("T_room"+ (i+1));
        System.out.println("Temperature is: "+intf.getSensorValue("s_tempr"+ (i+1)));
        System.out.println("Setpoint is: "+setPoint);
    
        // conditions for turning on
    	if (setPoint- intf.getSensorValue("s_tempr"+(i+1)) > delta ) {
            //System.out.println(setPoint- intf.getSensorValue("s_tempr"+(i+1))+" : turning ON");
    		System.out.println("Turning actuator ON");
   		// turning actuator on
           	intf.setActuator("a_htrr"+(i+1)+"_1", 1.0); //switch heater in room i on
    	}
    	
    	// Conditions for turning off
    	if (setPoint- intf.getSensorValue("s_tempr"+(i+1)) <= delta ) {
            //System.out.println(setPoint- intf.getSensorValue("s_tempr1")+" : turning OFF");
    		System.out.println("Turning actuator Off");
    	// Turnin actuator off
           	intf.setActuator("a_htrr"+(i+1)+"_1", 0.0); //switch heater in room i off
    	}
    	System.out.println("");
    	//System.out.println("T_room1="+intf.getSensorValue("s_tempr1"));
  	}
 }
    
  

  @Override
  protected void init() {
	  // Just for show
	    BuildingConfig bc=getInterface().getBuildingConfig();
	    ArrayList<RoomConfig> rooms=bc.getRooms();
	    System.out.println("Rooms: "+rooms.toString());
	    TemperatureGUI t=new TemperatureGUI();
	    t.addListener(this);
	    
  }

  
  @Override
  public void setpointChanged(double setpoint) {
	  System.out.println("Setpoint changed from: "+setPoint+ " to: " + setpoint);
	  this.setPoint = setpoint;
	  
  } 
  

}