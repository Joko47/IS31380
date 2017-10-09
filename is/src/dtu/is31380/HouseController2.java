package dtu.is31380;

import java.util.ArrayList;

public class HouseController2 extends AbstractHouseController implements TemperatureGUIListener{

	double setPoint = 20;
	double delta = 1.5;
	int roomNumber;
	
	public HouseController2(int number) {
	    super(5000); //set timestep to 5000ms
	    roomNumber = number;
	  }
  
  @Override
  protected void execute() {
    HouseControllerInterface intf=getInterface();
	  System.out.println("T_room"+roomNumber);
      System.out.println("Temperature is: "+intf.getSensorValue("s_tempr"+roomNumber));
      System.out.println("Setpoint is: "+setPoint);
  
      // conditions for turning on
  	if (setPoint- intf.getSensorValue("s_tempr"+roomNumber) > delta ) {
          //System.out.println(setPoint- intf.getSensorValue("s_tempr"+(i+1))+" : turning ON");
  		System.out.println("Turning actuator ON");
 		// turning actuator on
         	intf.setActuator("a_htrr"+roomNumber+"_1", 1.0); //switch heater in room i on
  	}
  	
  	// Conditions for turning off
  	if (setPoint- intf.getSensorValue("s_tempr"+roomNumber) <= delta ) {
          //System.out.println(setPoint- intf.getSensorValue("s_tempr1")+" : turning OFF");
  		System.out.println("Turning actuator Off");
  	// Turnin actuator off
         	intf.setActuator("a_htrr"+roomNumber+"_1", 0.0); //switch heater in room i off
  	}
  	System.out.println("");
  	//System.out.println("T_room1="+intf.getSensorValue("s_tempr1"));
	
}
  
	  
  

  @Override
  protected void init() {
	  // Just for show
	    BuildingConfig bc=getInterface().getBuildingConfig();
	    ArrayList<RoomConfig> rooms=bc.getRooms();
	    System.out.println("Rooms: "+rooms.toString());
	    TemperatureGUI t=new TemperatureGUI();
	    t.addListener(this);
	    System.out.println("Controller for room "+roomNumber+" is set up");
  }

  
  @Override
  public void setpointChanged(double setpoint) {
	  System.out.println("Setpoint changed from: "+setPoint+ " to: " + setpoint);
	  this.setPoint = setpoint;
  
  } 
  

}
