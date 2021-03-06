package dtu.is31380;


import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

public class SimpleController extends AbstractHouseController{

	private final SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss.SSS]");
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	double[] setpoint = {22.0,21.0,20.5,18.2,20.4,20.1,19.3,18.9};
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
	  log.info(sdf.format(new Date()) +"-Sim time("+ intf.getSimulationTime()+")"+ " Starting execution");
	  
    // loop through "rooms"
    for (int i=0; i<8; i++) { 
    	log.info(sdf.format(new Date()) +"-Sim time("+ intf.getSimulationTime()+")"+ " Looping through room" +i);
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
    	
    	/*
        System.out.println("T_room"+ (i));
        System.out.println("Temperature is: "+intf.getSensorValue(Sensor));
        System.out.println("Setpoint is: "+setpoint[i]);
        System.out.println(intf.getActuatorSetpoint(Actuator+"_1"));
        */
    	log.info(sdf.format(new Date()) +"-Sim time("+ intf.getSimulationTime()+")"+" Temperature in room"+i+" is: "+intf.getSensorValue(Sensor)+", setpoint is: "+setpoint[i]+", delta is: "+delta[i]+", difference is: "+(intf.getSensorValue(Sensor)-setpoint[i]));
        // conditions for turning on
		if (setpoint[i] - delta[i] > intf.getSensorValue(Sensor)  ) {
				if(intf.getActuatorSetpoint(Actuator+"_1") != 1.0) {
					//System.out.println(setpoint[i] - intf.getSensorValue(Sensor)+" : turning ON");
					// turning actuator on
					if(acc==2)
						intf.setActuator((Actuator+"_2"), 1.0);
					intf.setActuator((Actuator+"_1"), 1.0); //switch heater in room i on
					log.info(sdf.format(new Date()) +"-Sim time("+ intf.getSimulationTime()+")"+ " Turning actuator on");
					
				}
			}
		

		// Conditions for turning off
		if (setpoint[i] + delta[i] < intf.getSensorValue(Sensor)  ) {
				if(intf.getActuatorSetpoint(Actuator+"_1") != 0.0) {
					//System.out.println(setpoint[i] - intf.getSensorValue(Sensor)+" : turning OFF");
					// Turnin actuator off
					if(acc==2)
						intf.setActuator((Actuator+"_2"), 0.0);
					intf.setActuator((Actuator+"_1"), 0.0); //switch heater in room i off
					log.info(sdf.format(new Date()) +"-Sim time("+ intf.getSimulationTime()+")"+ " Turning actuator off");
				}
			}
		}
    	System.out.println("");
    	//System.out.println("T_room1="+intf.getSensorValue("s_tempr1"));
  	}
 
    
  

  @Override
  protected void init() {
	  HouseControllerInterface intf=getInterface();
	  log.info(sdf.format(new Date()) +"-Sim time("+ intf.getSimulationTime()+")"+ " Starting initiation");
	  // Just for show
	    BuildingConfig bc=getInterface().getBuildingConfig();
	    ArrayList<RoomConfig> rooms=bc.getRooms();
	    System.out.println("Rooms: "+rooms.toString());
	    log.info(sdf.format(new Date()) +"-Sim time("+ intf.getSimulationTime()+")"+ " Rooms: " +rooms.toString());
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
	    log.info(sdf.format(new Date()) +"-Sim time("+ intf.getSimulationTime()+")"+ " All rooms initiated");
  }

}
