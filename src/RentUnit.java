import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

class RentUnit {
	private List<SportEquipment> units = new ArrayList<>();

	void addUnit(SportEquipment equipment){
		if (!units.contains(equipment)) units.add(equipment);
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner("\n");
		joiner.add("List of rented units:");
		units.forEach(u -> joiner.add(u.getTitle()));
		return joiner.toString();
	}
}
