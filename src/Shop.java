import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Shop {
	private Map<SportEquipment, Integer> goods;
	private final String fileName;
	private BufferedReader reader;
	private RentUnit rentUnit;

	private Shop(String fileName){
		this.goods = new HashMap<>();
		this.fileName = Shop.class.getProtectionDomain().getCodeSource().getLocation().getPath() + fileName;
		this.reader = new BufferedReader(new InputStreamReader(System.in));
		this.rentUnit = new RentUnit();
	}

	public static void main(String...args) throws IOException{
		Shop shop = new Shop("goods.txt");
		shop.initializeGoods();
		shop.showStoredEquipment();
		System.out.println("\nIf you want complete the purchase just type \"EXIT\"!\n");
		while(true){
			shop.takeEquipment();
		}
	}
	
	private void initializeGoods() throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
			while(reader.ready()){
				String line = reader.readLine();
				parseGood(line);
			}
		}
	}
	//TAKE equipment from goods and PUT it in RentUnit
	private void takeEquipment() throws IOException {
        System.out.println("Please, Enter name of one equipment from list above!");
        String title;
        SportEquipment unit;
		while(true){
			title = readString();
			unit = findEquipment(title);
			if (Objects.nonNull(unit)) break;
        	else {
				System.out.println("Equipment with this name is not exist. Please, Enter correct name.");
			}
		}
        System.out.println("Please, Enter count of it!");
		int unitCount = goods.get(unit);
		int rentedCount = getRentedCount(unitCount);
		rentUnit.addUnit(unit);
		goods.put(unit, unitCount - rentedCount);
		System.out.println(String.format("%s was purchased. The remaining %d.", title, goods.get(unit)));
	}

	private SportEquipment findEquipment(final String title){
		return goods.keySet().stream().filter(se -> title.equalsIgnoreCase(se.getTitle())).findFirst().orElse(null);
	}

	private int getRentedCount(final int unitCount){
		int rentedCount;
		while(true){
			try {
				rentedCount = Integer.parseInt(readString());
			} catch (NumberFormatException e){
				System.out.println("Please, Enter correct count");
				continue;
			}
			if (unitCount < rentedCount) System.out.println(String.format("You may buy only %d things. Please, Enter correct count", unitCount));
			else break;
		}
		return rentedCount;
	}

	private String readString(){
		String string = null;
		try {
			string = reader.readLine();
			if (string.equalsIgnoreCase("exit")) exit();
		} catch (IOException e) {/*ignored*/}
		return string;
	}

	private void exit() throws IOException {
		System.out.println(rentUnit);
		System.out.println("\nThank you for your purchase!");
		reader.close();
		System.exit(0);
	}

	private void parseGood(String line){
		String[] parts = line.split("/");
		SportEquipment equipment = new SportEquipment(new Category(parts[0]), parts[1], Integer.valueOf(parts[2]));
		goods.put(equipment, Integer.valueOf(parts[3]));		
	}
	
	private void showStoredEquipment(){
		goods.entrySet().forEach(e -> System.out.println(e.getKey() + ", Count: " + e.getValue()));
	}
}
