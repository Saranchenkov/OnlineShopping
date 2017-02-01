import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Shop {
	private static Map<SportEquipment, Integer> goods;
	private final static String fileName = Shop.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "goods.txt";
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static RentUnit rentUnit = new RentUnit();

	public static void main(String...args) throws IOException{
		try{
			initializeGoods();
			showStoredEquipment();
			while(true){
				takeEquipment();
			}
		} catch(ExitException e){
			showRentedEquipment();
			System.out.println("Thank you for your purchase!");
		}
	}
	
	private static void initializeGoods() throws ExitException, IOException {
		goods = new HashMap<>();
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
			while(reader.ready()){
				String line = reader.readLine();
				parseGood(line);
			}
		}
	}
	//TAKE equipment from goods and PUT it in RentUnit
	private static void takeEquipment() throws IOException, ExitException{
			String title = readString();
			int count = Integer.parseInt(readString());
			boolean isFound = false;

			for(Map.Entry<SportEquipment, Integer> good : goods.entrySet()){
				if (isFound) break;
				if (good.getKey().getTitle().equals(title)){
					isFound = true;
					rentUnit.addUnits(good.getKey());
					if (good.getValue() < count) System.out.println(String.format("You may buy only %d things", good.getValue()));

					goods.put(good.getKey(), count <= good.getValue() ? good.getValue() - count : 0);
					System.out.println(String.format("%s was purchased. The remaining %d.", title, good.getValue()));
				}
			}

			if (!isFound) System.out.println(String.format("Equipment \"%s\" is not found", title));
	}

	private static String readString() throws ExitException {
		String string = null;
		try {
			string = reader.readLine();
			if (string.equals("exit")) throw new ExitException();
		} catch (IOException e) {/*ignored*/}
		return string;
	}

	private static void parseGood(String line){
		String[] parts = line.split("/");
		SportEquipment equipment = new SportEquipment(new Category(parts[0]), parts[1], Integer.valueOf(parts[2]));
		goods.put(equipment, Integer.valueOf(parts[3]));		
	}
	
	private static void showStoredEquipment(){
		for(Map.Entry<SportEquipment, Integer> good : goods.entrySet()){
			System.out.println(good.getKey() + ", Count: " + good.getValue());
		}
	}
	
	private static void showRentedEquipment(){
		System.out.println(rentUnit);
	}

}
