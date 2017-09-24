package dtu.is31380;

import java.util.ArrayList;

public class MyController extends AbstractHouseController implements TemperatureGUIListener {

  public MyController() {
    super(1000); 										//set timestep to 5000ms
  }

  private double t1 = 0;
  
  
  @Override
  protected void execute() {
    HouseControllerInterface intf=getInterface();

    if (this.t1 >= intf.getSensorValue("s_tempr1")) {
    	System.out.println("Setpoint above temperature, turning heater on!");
    	intf.setActuator("a_htrr1_1", 1.0); 			//switch heater in room 1 on
        
    }
    
    else {
    	System.out.println("Setpoint below temperature, turning heater off!");   	
    	intf.setActuator("a_htrr1_1", 0.0); 			//switch heater in room 1 off
        
    }
    
    System.out.println("Temperature is "+intf.getSensorValue("s_tempr1"));
    //System.out.println("SP: " + this.t1 + " Sensor: " + intf.getSensorValue("s_tempr1"));
    //System.out.println("T_room1="+intf.getSensorValue("s_tempr1"));    
  }

  @Override
  protected void init() {
    BuildingConfig bc=getInterface().getBuildingConfig();
    ArrayList<RoomConfig> rooms=bc.getRooms();
    System.out.println("Rooms: "+rooms.toString());
    getInterface().setActuator("a_htrr1_1", 0.0);  
    System.out.println("Program starts here");
    TemperatureGUI t=new TemperatureGUI();
    t.addListener(this);
  }

  @Override
  public void setpointChanged(double setpoint) {
	this.t1 = setpoint;
	  System.out.println("Setpoint is: " + setpoint);
  }
  
  
}
