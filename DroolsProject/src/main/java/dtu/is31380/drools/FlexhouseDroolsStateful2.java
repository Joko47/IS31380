package dtu.is31380.drools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
//import org.kie.api.runtime.StatelessKieSession;
//import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.FactHandle;

import dtu.is31380.AbstractHouseController;
import dtu.is31380.Actuator;
import dtu.is31380.BuildingConfig;
import dtu.is31380.HouseControllerInterface;
import dtu.is31380.RoomConfig;
import dtu.is31380.Sensor;

/**
 * This class is to launch a rule set.
 */
public class FlexhouseDroolsStateful2 extends AbstractHouseController {

	private KieServices kieServices;
	private KieContainer kContainer;
	private KieSession kSession;
	private List<FactHandle> facts;
	private List<Object> objList;
	//public double[] setpoint = {20,20,20,20,20,20,20,20};

	
	  public FlexhouseDroolsStateful2() {
	    super(5000); //set timestep to 5000ms
        try {
            // load up the knowledge base
        	 kieServices = KieServices.Factory.get();
        	 kContainer = kieServices.getKieClasspathContainer();
        	 kSession = kContainer.newKieSession("ksession-q23statefulrules");  
        			// this name for the rule package "rulesStateful" as "ksession-rules-stateful" is configured in META-INF/kmodule.xml     	
        	 System.out.println("Rule engine initialized successfully.");
        } catch (Throwable t) {
            t.printStackTrace();
        }
	  }
	  
	  
	  
	   	
	   	
	  @Override
	  protected void execute() {
        try {
        	// get 
	    	Iterator<FactHandle> itfact = facts.iterator();
	  
	    	// UPDATE drools with changed facts
        	for (Object jobj : objList) {
        		// a bit simplistic: assuming order in list remains the same for both facts and objList. 
        		if (itfact.hasNext()) {
        			FactHandle fact = itfact.next();
	    			kSession.update(fact, jobj);	    			        			
        		}       				 
     	    }
        	// Now fire all rules!
        	kSession.fireAllRules();
        	// or just a subset filtered by rule name
    	    //kSession.fireAllRules(new RuleNameStartsWithAgendaFilter("turn"));
    	    //kSession.fireAllRules(new RuleNameEndsWithAgendaFilter("on"));
        	//kSession.fireAllRules(new RuleNameStartsWithAgendaFilter("test"));
     	 
           	
        	
        } catch (Throwable t) {
            t.printStackTrace();
        }

	  }

	  @Override
	  protected void init() {
		  // the usual starter-code. 
	    BuildingConfig bc=getInterface().getBuildingConfig();
	    ArrayList<RoomConfig> rooms=bc.getRooms();
	    System.out.println("Rooms: "+rooms.toString());
	    getInterface().setActuator("a_htrr1_1", 0.0);
	    
	    // get FlexHouse Objects of interest
 	    HouseControllerInterface intf=getInterface();	
 	    Sensor[] senslist = intf.getSensors();
 	    Actuator[] actlist = intf.getActuators();
 	    BuildingConfig buildg = intf.getBuildingConfig();
 	    
 	    // collect Objects to watch from Drools engine ...
 	    List<Object> news =  Arrays.asList( (Object[]) senslist );
 	    List<Object> newa = Arrays.asList( (Object[]) actlist);
 	    objList = new ArrayList<>();
 	    objList.addAll(news);
 	    objList.addAll(newa);	
 	    objList.add(buildg);
 	    objList.add(intf);
 	    objList.add(rooms);
  
 	    facts = new ArrayList<FactHandle>();
	    
 	    // iterate through list of objects and Insert to Drools engine.
 	    for (Object jobj : objList) {
 	 	    try {
 	    	facts.add(kSession.insert(jobj));
 	 	    } catch (Throwable t) {
 	            t.printStackTrace();
 	    }
 	    // only fire rules whose name starts with ...
	    //kSession.fireAllRules(new RuleNameStartsWithAgendaFilter("check"));
        // fire all rules   
	    //kSession.fireAllRules();
        }

 	    
	  }
	  
	 

}
