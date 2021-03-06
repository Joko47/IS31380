package dtu.is31380.drools;

import java.util.Arrays;



public class VirtualRoom {
	private String[] containedRooms;
	private String[] containedSensors;
	private String[] containedActuators;
	private Double[] containedSetpoints;
	private double containedAir;
	private double containedAvSp; /////////////////////
	private Double[] containedWeight;
	private Double[] containedCubic;
	private Double[] containedTemps;
	
	//Init
	public VirtualRoom(String[] Room, String[] Sens, String[] Act, Double[] Set, double Air, double AvSp, Double[] weight, Double[] cubic, Double[] temps) { //////////////////
	    containedRooms = Room;
	    containedSensors = Sens;
		containedActuators = Act;
		containedSetpoints = Set;
		containedAir = Air;
		containedAvSp = AvSp; /////////////////////
		containedWeight = weight;
		containedCubic = cubic;
		containedTemps = temps;
	}
	
	//Getters
	public String[] getContainedRooms() {
		return containedRooms;
	}
	public String[] getContainedSensors() {
		return containedSensors;
	}
	public String[] getContainedActuators() {
		return containedActuators;
	}
	public Double[] getContainedSetpoints() {
		return containedSetpoints;
	}
	public double getContainedAir() {
		return containedAir;
	}
	public double getContainedAvSp() { ////////////////////
		return containedAvSp;
	}
	public Double[] getContainedWeight() { ////////////////////
		return containedWeight;
	}
	public Double[] getContainedCubic() { ////////////////////
		return containedCubic;
	}
	public Double[] getContainedTemps() { ////////////////////
		return containedTemps;
	}
	
	//Setters
	public void setContainedRooms(String[] rooms) {
		containedRooms = rooms;
	}
	public void setContainedSensors(String[] sensors) {
		containedSensors = sensors;
	}
	public void setContainedActuators(String[] actuators) {
		containedActuators = actuators;
	}
	public void setContainedSetpoints(Double[] setpoints) {
		containedSetpoints = setpoints;
	}
	public void setContainedAir(double Air) {
		containedAir = Air;
	}
	public void setContainedWeight(Double[] Weight) {
		containedWeight = Weight;
	}
	public void setContainedCubic(Double[] cubic) {
		containedWeight = cubic;
	}
	public void setContainedTemps(Double[] temps) {
		containedTemps = temps;
	}
	
	@Override
	public String toString() {
		return "VirtualRoom [containedRooms=" + Arrays.toString(containedRooms) + ", containedSensors="
				+ Arrays.toString(containedSensors) + ", containedActuators=" + Arrays.toString(containedActuators)
				+ ", containedSetpoints=" + Arrays.toString(containedSetpoints) + ", containedAir=" + containedAir
				+ ", containedAvSp=" + containedAvSp + ", containedWeight=" + Arrays.toString(containedWeight)
				+ ", containedCubic=" + Arrays.toString(containedCubic) + ", containedTemps="
				+ Arrays.toString(containedTemps) + "]";
	}
}