package dtu.is31380;

import java.util.ArrayList;
import java.util.Collection;


public class HouseControllerSlaves extends AbstractHouseController {

	
	private Collection<Thread> slaves; 
	// define local a local field variables for setpoints & deltas for each room.
	double[] setpoint = {20,20,20,20,20,20,20,20};
	double delta = 0.5;
		
  public HouseControllerSlaves() {
    super(5000); //set timestep to 5000ms
  }
    
  @Override
  protected void execute() {
    HouseControllerInterface intf=getInterface();
    
    
    /*
     * HERE take the per room control and implement setpoints - as in 
     * Q1.1)
     */
    while(1==1) {
    	try {
    	    Thread.sleep(10000);
    	} catch(InterruptedException e) {
    	    System.out.println("got interrupted!");
    	}
    	System.out.print(" Setpoints: ");
    	System.out.print(setpoint[0]+", ");
    	System.out.print(setpoint[1]+", ");
    	System.out.print(setpoint[2]+", ");
    	System.out.print(setpoint[3]+", ");
    	System.out.print(setpoint[4]+", ");
    	System.out.print(setpoint[5]+", ");
    	System.out.print(setpoint[6]+", ");
    	System.out.print(setpoint[7]);
    	System.out.println("");
    	System.out.print("Temperature: ");
    	System.out.print(intf.getSensorValue("s_tempmain")+", ");
    	System.out.print(intf.getSensorValue("s_tempr1")+", ");
    	System.out.print(intf.getSensorValue("s_tempr2")+", ");
    	System.out.print(intf.getSensorValue("s_tempr3")+", ");
    	System.out.print(intf.getSensorValue("s_tempr4")+", ");
    	System.out.print(intf.getSensorValue("s_tempr5")+", ");
    	System.out.print(intf.getSensorValue("s_tempr6")+", ");
    	System.out.print(intf.getSensorValue("s_tempr7"));
    	System.out.println("");
    	
    	
    }
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
	            	String[] names = {"Unkown","Unkown"};
	            	int acc = 0;
	            	int p = 0;
	            	double spOld = 0;
	            	
	            	if(room.getName().equals("Main hall")) {
	            		names[0]="s_tempmain";
	            		names[1]="a_htrmain";
	            		acc = 2;
	            		p = 0;
	    			}
	    			else if(room.getName().equals("room1")) {
	    				names[0]="s_tempr1";
	            		names[1]="a_htrr1";
	            		acc = 1;
	            		p = 1;
	    			}
	    			else if(room.getName().equals("room2")) {
	    				names[0]="s_tempr2";
	            		names[1]="a_htrr2";
	            		acc = 1;
	            		p = 2;
	    			}
	    			else if(room.getName().equals("room3")) {
	    				names[0]="s_tempr3";
	            		names[1]="a_htrr3";
	            		acc = 1;
	            		p = 3;
	    			}
	    			else if(room.getName().equals("room4")) {
	    				names[0]="s_tempr4";
	            		names[1]="a_htrr4";
	            		acc = 1;
	            		p = 4;
	    			}
	    			else if(room.getName().equals("room5")) {
	    				names[0]="s_tempr5";
	            		names[1]="a_htrr5";
	            		acc = 1;
	            		p = 5;
	    			}
	    			else if(room.getName().equals("room6")) {
	    				names[0]="s_tempr6";
	            		names[1]="a_htrr6";
	            		acc = 1;
	            		p = 6;
	    			}
	    			else if(room.getName().equals("room7")) {
	    				names[0]="s_tempr7";
	            		names[1]="a_htrr7";
	            		acc = 2;
	            		p = 7;
	    			}
	    			else {
	    				System.out.println("Error in room: "+Thread.currentThread().getName());
	    			}
	            
	    
	            	            	
	            	HouseControllerInterface intf=getInterface();
	            	System.out.println(Thread.currentThread().getName() + " is running. I will take care of "+ room.getName()+"'s setpoints");
	            	/*
	            	 * This is the run method of the slave thread. 
	            	 * Make it listen to the "master".
	            	 */

	            	
	            	while(1==1)
	                {
	                /*
	                 * Read setpoints from master
	                 * Set the setpoint
	                 */
	                
	                
	                if(setpoint[p]!=spOld) {
	    			System.out.println(room.getName());
	    			System.out.println("Temperature is: "+intf.getSensorValue(names[0]));
	    			System.out.println("Setpoint is: "+setpoint[p]);
	    			spOld = setpoint[p];
	                }
	    			
	    			// conditions for turning on
	    			if (setpoint[p] - delta > intf.getSensorValue(names[0])  ) {
	    				for(int i=1;i<acc+1;i++){
	    					if(intf.getActuatorSetpoint(names[1]+"_"+i)!=1.0) {
	    						//System.out.println(setPoint- intf.getSensorValue("s_tempr"+(i+1))+" : turning ON");
	    						System.out.println("Turning actuator "+names[1]+"_"+i+" ON");
	    						// turning actuator on
	    						intf.setActuator((names[1]+"_"+i), 1.0); //switch heater in room i on
	    					}
	    				}
	    			}
	    			
    	
	    			// Conditions for turning off
	    			if (setpoint[p] + delta < intf.getSensorValue(names[0])  ) {
	    				for(int i=1;i<acc+1;i++){
	    					if(intf.getActuatorSetpoint(names[1]+"_"+i)!=0.0) {
	    						//System.out.println(setPoint- intf.getSensorValue("s_tempr1")+" : turning OFF");
	    						System.out.println("Turning actuator "+names[1]+"_"+i+" Off");
	    						// Turnin actuator off
	    						intf.setActuator((names[1]+"_"+i), 0.0); //switch heater in room i off
	    					}
	    				}
	    			}
    			
	                
	                
	                }
	         }
	        }));
	  }
	  
	  class ReadCommand implements Runnable
	  {
		  
	      public void run()
	      {
	    	  System.out.println(Thread.currentThread().getName() + " is running. I will take care of inputs");
	    	  while(1==1)	{
	    		  System.out.println("Enter setpoints: ");
	    		  String input = System.console().readLine();
	    		  String[] ar=input.split(",");
			  

	    		  for(int i=0; 2*i<ar.length;i++) {
	    			  System.out.println("Parsing values in set: "+i);
	    			  
	    			  if(ar[2*i].equals("rm")) {
	    				  setpoint[0]=Double.parseDouble(ar[2*i+1]);
	    				  System.out.println(ar[2*i]+" "+Double.parseDouble(ar[2*i+1]));
	    			  }
	    			  else if(ar[2*i].equals("r1")) {
	    				  setpoint[1]=Double.parseDouble(ar[2*i+1]);
	    			  }
	    			  else if(ar[2*i].equals("r2")) {
	    				  setpoint[2]=Double.parseDouble(ar[2*i+1]);
	    			  }
	    			  else if(ar[2*i].equals("r3")) {
	    				  setpoint[3]=Double.parseDouble(ar[2*i+1]);
	    			  }
	    			  else if(ar[2*i].equals("r4")) {
	    				  setpoint[4]=Double.parseDouble(ar[2*i+1]);
	    			  }
	    			  else if(ar[2*i].equals("r5")) {
	    				  setpoint[5]=Double.parseDouble(ar[2*i+1]);
	    			  }
	    			  else if(ar[2*i].equals("r6")) {
	    				  setpoint[6]=Double.parseDouble(ar[2*i+1]);
	    			  }
	    			  else if(ar[2*i].equals("r7")) {
	    				  setpoint[7]=Double.parseDouble(ar[2*i+1]);
	    			  }
	    			  else {
	    				  System.out.println("NoRoomsChanged");
	    			  }
	    				  
	    			  
	    		  }
	    		  
	    		  
	    		  
	    	  }
	      }
	  }
	  /*
	  new Thread(new Runnable() {
		  @Override
		  public void run() {
			  System.out.print("Enter something:");
			  String input = System.console().readLine();
			  System.out.print(input);
		  }
	  });
	  *
	  /*
	   * The following is an illustration on how you can start the slaves.
	   */
	  for (Thread slave : slaves) {
		  slave.start();
	  }
	  new Thread(new ReadCommand()).start();
	  
  }
  
  

}
