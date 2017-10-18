package dtu.is31380;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commexercise.pubsub.PubSubCallbackListener;
import commexercise.pubsub.PubSubClient;
import commexercise.pubsub.PubSubClientImpl;
import commexercise.pubsub.PubSubServer;
import commexercise.pubsub.PubSubServerImpl;
import commexercise.pubsub.PubSubSubscriberListener;



public class HouseControllerSlavesBackup3 extends AbstractHouseController {
	private final SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss.SSS]");
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private Collection<Thread> slaves; 
	// define local a local field variables for setpoints & deltas for each room.
    private double[] setpoint = {20,20,20,20,20,20,20,20};
	private double delta = 0.5;
	private double[] oldSetpoint = {20,20,20,20,20,20,20,20};

		
  public HouseControllerSlavesBackup3() {
    super(5000); //set timestep to 5000ms
    
  }
  
  @Override
  protected void execute() {
	  HouseControllerInterface intf=getInterface();
	String[] roomname= {"Main hall","room1","room2","room3","room4","room5","room6","room7"};
    //HouseControllerInterface intf=getInterface();
    PubSubServer pubSubServer = null;
	try {
		pubSubServer = new PubSubServerImpl(9090).start();
	} catch (Exception e1) {
		log.info(sdf.format(new Date()) +"-Sim time: "+intf.getSimulationTime()+" ERROR(starting master server)");
		e1.printStackTrace();
	}
	pubSubServer.addSubscriberListener(new PubSubSubscriberListener() {
        public void subscriberJoined(String topic, String id) {
        	log.info(sdf.format(new Date()) +"-Sim time: "+intf.getSimulationTime()+" ["+id+"] is getting setpoints for: "+topic );
        }

        public void subscriberLeft(String topic, String id) {
        	log.info(sdf.format(new Date()) +"-Sim time: "+intf.getSimulationTime()+" ["+id+"] has left for topic: "+topic );
        }
    });
    
    
    /*
     * HERE take the per room control and implement setpoints - as in 
     * Q1.1)
     */
    while(1==1) {
        // add subscriber listener (gets called when a client subscribes or unsubscribes)
        
    	for(int x=0;x<8;x++) {
    		if(oldSetpoint[x] != setpoint[x]) {
        		pubSubServer.send(""+roomname[x], new String[]{""+x,""+setpoint[x]});
        		log.info(sdf.format(new Date()) +"-Sim time: "+intf.getSimulationTime()+" "+"["+Thread.currentThread().getName()+"]"+" Sent setpoint "+setpoint[x]+" to "+roomname[x]);
        		oldSetpoint[x] = setpoint[x];
        	}
    	}
    }    
  }

  @Override
  protected void init(){
	  log.info(sdf.format(new Date()) + " Starting initiation");
	  
	  BuildingConfig bc=getInterface().getBuildingConfig(); 
	  Collection<RoomConfig> rooms =  bc.getRooms();
	  slaves = new ArrayList<Thread>();
	  log.info(sdf.format(new Date()) + " Starting slaves");
	  for (RoomConfig room : rooms) {
		  slaves.add(new Thread(new Runnable() {
			  double[] thisSetpoint = {20,20,20,20,20,20,20,20};
			  	// This is an inline anonymous class definition for your convenience).
	            @Override
	            public void run(){
	            	HouseControllerInterface intf=getInterface();
	            	log.info(sdf.format(new Date()) +"-Sim time: "+intf.getSimulationTime()+" "+"["+Thread.currentThread().getName()+"]"+" is initializing");
	            	
	            	PubSubClient pubSubClient = null;
	            	try {
						pubSubClient = new PubSubClientImpl("localhost",9090,Thread.currentThread().getName());
					} catch (MalformedURLException e) {
						log.info(sdf.format(new Date()) +"-Sim time: "+intf.getSimulationTime()+" "+"["+Thread.currentThread().getName()+"]"+" ERROR(Malformed URL)");
						e.printStackTrace();
					}
	            	
	            	PubSubCallbackListener clockListener = new PubSubCallbackListener() {
	                    public void messageReceived(String[] msg) {
	                    	Double d = new Double(1.23);
	                    	d = Double.valueOf(msg[0]);
	                    	int i = d.intValue();
	                    	thisSetpoint[i] = Double.valueOf(msg[1]);
	                    	log.info(sdf.format(new Date()) +"-Sim time: "+intf.getSimulationTime()+" "+"["+Thread.currentThread().getName()+"]"+" Recieved new setpoint: "+msg[0]);
	                    }
	                };
	                pubSubClient.subscribe(room.getName(), clockListener);
	                
	                log.info(sdf.format(new Date()) +"-Sim time: "+intf.getSimulationTime()+" "+"["+Thread.currentThread().getName()+"]"+" pubSub has been setup");

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
	    				System.out.println("ERROR(Room not found): in "+Thread.currentThread().getName());
	    				log.info(sdf.format(new Date()) +"-Sim time: "+intf.getSimulationTime()+" "+"["+Thread.currentThread().getName()+"]"+" is ready, and will take care of "+room.getName());

	    			}
	            	log.info(sdf.format(new Date()) +"-Sim time: "+intf.getSimulationTime()+" "+"["+Thread.currentThread().getName()+"]"+" is set up for "+room.getName()+" containing sensor "+names[0]
	            				+" and "+acc+ " actuator(s) named "+names[1]);

	            	            	
	            	
	            	
	            	/*
	            	 * This is the run method of the slave thread. 
	            	 * Make it listen to the "master".
	            	 */
	            	
	            	
	            	log.info(sdf.format(new Date()) +"-Sim time: "+intf.getSimulationTime()+" "+"["+Thread.currentThread().getName()+"]"+" is ready, and will take care of "+room.getName());
	            	while(1==1)
	                {
	            /*		log.info(sdf.format(new Date()) +"-Sim time "+intf.getSimulationTime(
	            				)+" "+"["+Thread.currentThread().getName()+"]"++": "+room.getName()+"Temp is "+intf.getSensorValue(names[0])+", Setpoint is "+setpoint[p]
	            				 +", delta is "+delta+", diferense is "+(intf.getSensorValue(names[0])-setpoint[p]));
	            */		if(thisSetpoint[p]!=spOld) {
	            	log.info(sdf.format(new Date()) +"-Sim time "+intf.getSimulationTime(
            				)+" "+"["+Thread.currentThread().getName()+"]"+": "+room.getName()+"Temp is "+intf.getSensorValue(names[0])+", Setpoint is "+setpoint[p]
            				 +", delta is "+delta+", difference is "+(intf.getSensorValue(names[0])-setpoint[p]));
	            			spOld = setpoint[p];
	            		}
	    			
	            		// conditions for turning on
	            		if (setpoint[p] - delta > intf.getSensorValue(names[0])  ) {
	            			for(int i=1;i<acc+1;i++){
	            				if(intf.getActuatorSetpoint(names[1]+"_"+i)!=1.0) {
	            					
	            					// turning actuator on
	            					log.info(sdf.format(new Date()) +"-Sim time: "+intf.getSimulationTime()+" "+"["+Thread.currentThread().getName()+"]"+" Turning on actuator(s) in "+room.getName());
	            					intf.setActuator((names[1]+"_"+i), 1.0); //switch heater in room i on
	            				}
	            			}
	            		}
	    			
    	
	            		// Conditions for turning off
	            		if (setpoint[p] + delta < intf.getSensorValue(names[0])  ) {
	            			for(int i=1;i<acc+1;i++){
	            				if(intf.getActuatorSetpoint(names[1]+"_"+i)!=0.0) {
	            					// Turnin actuator off
	            					log.info(sdf.format(new Date()) +"-Sim time: "+intf.getSimulationTime()+" "+"["+Thread.currentThread().getName()+"]"+" Turning off actuator(s) in "+room.getName());
	            					intf.setActuator((names[1]+"_"+i), 0.0); //switch heater in room i off
	            				}
	            			}
	            		}
	                }
	         }
	        }));
		  log.info(sdf.format(new Date()) + " Slaves created");
	  }
	  
	  class ReadCommand implements Runnable
	  {
		  
	      public void run()
	      {
	    	  log.info(sdf.format(new Date())+" "+"["+Thread.currentThread().getName()+"]"+" is ready, and will take care of inputs");
	    	  while(1==1)	{
	    		  String input = System.console().readLine();
	    		  String[] ar=input.split(",");
			  

	    		  for(int i=0; 2*i<ar.length;i++) {
	    			  log.info(sdf.format(new Date())+" "+"["+Thread.currentThread().getName()+"]"+" analyzing input "+i+" of "+(ar.length/2));
	    			  
	    			  if(ar[2*i].equals("rm")) {
	    				  setpoint[0]=Double.parseDouble(ar[2*i+1]);
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
	    				  
	    				  log.info(sdf.format(new Date())+" "+"["+Thread.currentThread().getName()+"]"+" wrong input, nothing changed");
	    			  }
	    		  }
	      }
	      }
	  }
	  for (Thread slave : slaves) {
		  slave.start();
	  }
	  new Thread(new ReadCommand()).start();
	  }
  		
  }


	  
  
  
  


