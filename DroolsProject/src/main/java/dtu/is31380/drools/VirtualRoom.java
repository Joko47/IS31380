package dtu.is31380.drools;

import java.util.Arrays;



public class VirtualRoom {
	private String[] containedRooms;
	private String[] containedSensors;
	private String[] containedActuators;
	private Double[] containedSetpoints;
	private double containedAir;
	
	public VirtualRoom(String[] Room) {
	    containedRooms = Room;

	}
	
	
	public String[] getContainedRooms() {
		return containedRooms;
	}
	public String[] getContainedSensors() {
		return containedSensors;
	}
	public String[] getContainedActuators() {
		return containedActuators;
	}
	public Double[] getContainedSetpoint() {
		return containedSetpoints;
	}
	public double getContainedAir() {
		return containedAir;
	}
	
	
	public void setContainedRooms(String[] rooms) {
		containedRooms = rooms;
	}
	public void setContainedSensors(String[] sensors) {
		containedSensors = sensors;
	}
	public void setContainedActuators(String[] actuators) {
		containedActuators = actuators;
	}
	public void setContainedSetpoint(Double[] setpoints) {
		containedSetpoints = setpoints;
	}
	public void getContainedAir(double Air) {
		containedAir = Air;
	}
	@Override
	public String toString() {
		return "VirtualRoom [containedRooms=" + Arrays.toString(containedRooms) + ", containedSensors="
				+ Arrays.toString(containedSensors) + ", containedActuators=" + Arrays.toString(containedActuators)
				+ ", containedSetpoints=" + Arrays.toString(containedSetpoints) + ", containedAir=" + containedAir
				+ "]";
	}
}
