import java.util.ArrayList;

class RentUnit {
	private ArrayList<SportEquipment> units = new ArrayList<>();

	void addUnits(SportEquipment equipment){
		if (!units.contains(equipment)) units.add(equipment);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("List of rented units:\n");
		for(SportEquipment eq : units){
			sb.append(eq.getTitle()).append("\n");
		}
		return sb.toString();
	}
}
