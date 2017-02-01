import java.util.ArrayList;

class RentUnit {
	private static ArrayList<SportEquipment> units;
	
	static {
		units = new ArrayList<>();
	}
	
	public void addUnits(SportEquipment equipment){
		units.add(equipment);
	}
}
