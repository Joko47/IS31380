package dtu.is31380.drools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
//import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import dtu.is31380.AbstractHouseController;
import dtu.is31380.Actuator;
import dtu.is31380.BuildingConfig;
import dtu.is31380.HouseControllerInterface;
import dtu.is31380.RoomConfig;
import dtu.is31380.Sensor;


public class FlexhouseDroolsCtrlTEMPLATE extends AbstractHouseController {

	private KieServices kieServices;
	private KieContainer kContainer;
	private StatelessKieSession kSession;
	
	public class VirtualRoom {
		private String[] containedRooms;
		private String[] containedSensors;
		private String[] containedActuators;
		private Double[] containedSetpoints;
		private double containedAir;

		public String[] getRooms() {
			return containedRooms;
		}
		public String[] getSensors() {
			return containedSensors;
		}
		public String[] getActuators() {
			return containedActuators;
		}
		public Double[] getSetpoint() {
			return containedSetpoints;
		}
		public double getAir() {
			return containedAir;
		}
		
		
		public void setRooms(String[] rooms) {
			containedRooms = rooms;
		}
		public void setSensors(String[] sensors) {
			containedSensors = sensors;
		}
		public void setActuators(String[] actuators) {
			containedActuators = actuators;
		}
		public void setSetpoint(Double[] setpoints) {
			containedSetpoints = setpoints;
		}
		public void getAir(double Air) {
			containedAir = Air;
		}
	}
	
	  public FlexhouseDroolsCtrlTEMPLATE() {
	    super(5000); //set timestep to 5000ms
        try {
            // load up the knowledge base
        	 kieServices = KieServices.Factory.get();
        	 kContainer = kieServices.getKieClasspathContainer();
        	 // OBS! due to REACTIVE AGENT design, we use a "STATELESS" session
        	 kSession = kContainer.newStatelessKieSession("ksession-q23rules");  
        			// this name for the rule package "ksession-rules" is configured in kmodule.xml     	
        	 System.out.println("Rule engine initialized successfully.");
        } catch (Throwable t) {
            t.printStackTrace();
        }


	  }
	  
	  @Override
	  protected void execute() {
 	    HouseControllerInterface intf=getInterface();	
 	    Sensor[] senslist = intf.getSensors();
 	    Actuator[] actlist = intf.getActuators();
 	    BuildingConfig buildg = intf.getBuildingConfig();
 	    
 	    List<Object> news =  Arrays.asList( (Object[]) senslist );
 	    List<Object> newa = Arrays.asList( (Object[]) actlist);
 	    List<Object> listC = new ArrayList<>();
 	    listC.addAll(news);
 	    listC.addAll(newa);	
 	    listC.add((Object) buildg);
 	    listC.add((Object) intf);

        try {
        	/*
        	 * CHOOSE here which variable are passed to the rule engine.
        	 */
        	// *just*  Monitor Temperature
     	    //kSession.execute( news); 
     	    // *just* Monitor Actuators 
     	    //kSession.execute( newa); 
     	         	    
     	    // *combined monitor & control* 
     	   	kSession.execute(listC);
        } catch (Throwable t) {
            t.printStackTrace();
        }

	  }

	  @Override
	  protected void init() {
		  // just some stimulation
	    BuildingConfig bc=getInterface().getBuildingConfig();
	    ArrayList<RoomConfig> rooms=bc.getRooms();
	    System.out.println("Rooms: "+rooms.toString());
	    getInterface().setActuator("a_htrr1_1", 1.0);
	  }
	  
	  

}
