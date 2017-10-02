package dtu.is31380.drools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
//import org.kie.api.runtime.StatelessKieSession;
//import org.kie.api.runtime.rule.AgendaFilter;

import dtu.is31380.AbstractHouseController;
import dtu.is31380.Actuator;
import dtu.is31380.BuildingConfig;
import dtu.is31380.HouseControllerInterface;
import dtu.is31380.RoomConfig;
import dtu.is31380.Sensor;

/**
 * This class is to launch a rule set.
 */
public class FlexhouseDroolsFireUntilHalt extends AbstractHouseController {

	private KieServices kieServices;
	private KieContainer kContainer;
	private KieSession kSession;
	
	  public FlexhouseDroolsFireUntilHalt() {
	    super(5000); //set timestep to 5000ms
        try {
            // load up the knowledge base
        	 kieServices = KieServices.Factory.get();
        	 kContainer = kieServices.getKieClasspathContainer();
        	 kSession = kContainer.newKieSession("ksession-rules-stateful");  
        			// this name for the rule package "rulesStateful" as "ksession-rules-stateful" is configured in META-INF/kmodule.xml     	
        	 System.out.println("Rule engine initialized successfully.");
        } catch (Throwable t) {
            t.printStackTrace();
        }
	  }
	  
	  @Override
	  protected void execute() {
//        try {
//        	// just  Monitor Temperature
//     	    //kSession.execute( news); 
//     	    // just Monitor Actuators 
//     	    //kSession.execute( newa); 
//     	         	    
//     	    // combined monitor 
//     	   kSession.fireAllRules();
//     	  //, (Object[])actlist }  new Object[] buildg 
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }

 	  //ksession.execute( Arrays.asList( new Object[] { application, applicant } ) );
 	  
 	    
 //	    for (i=0; i< senslist.length; i++) {
	/*    if (intf.getSimulationTime()>100) {
	      if (intf.getActuatorSetpoint("a_htrr1_1")<0.5) {
	        intf.setActuator("a_htrr1_1", 1.0); //switch heater in room 1 on
	      }
	    }
	    System.out.println("T_room1="+intf.getSensorValue("s_tempr1"));
	  */  
	  }

	  @Override
	  protected void init() {
	    BuildingConfig bc=getInterface().getBuildingConfig();
	    ArrayList<RoomConfig> rooms=bc.getRooms();
	    System.out.println("Rooms: "+rooms.toString());
	    getInterface().setActuator("a_htrr1_1", 0.0);
	    
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
 	    
 	    for (Object jobj : listC) {
 	    	kSession.insert(jobj);
 	    }
	    kSession.fireUntilHalt();
 	    
	  }
	  
	  
/*
    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	 kSession = kContainer.newKieSession("ksession-rules");
        	
        	
//        	KnowledgeBase kbase = readKnowledgeBase();
//        	 StatefulKnowledgeSession ksession =    	kbase.newStatefulKnowledgeSession();
//
//            // go !
//            Message message = new Message();
//            message.setMessage("Hello World");
//            message.setStatus(Message.HELLO);
//            kSession.insert(message);
//            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
*/

}
