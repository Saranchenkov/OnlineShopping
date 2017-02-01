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

	public static void main(String...args) throws IOException{
		try{
			initializeGoods();
			showStoredEquipment();
			while(true){
				takeEquipment();
			}
		} catch(ExitException e){
			System.out.println("Thank you for your purchase!");
		}
	}
	
	private static void initializeGoods() throws IOException{
		goods = new HashMap<>();
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
			while(reader.ready()){
				String line = reader.readLine();
				parseGood(line);
			}
		}
	}
	
	private static void takeEquipment() throws IOException, ExitException{
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
			String title = reader.readLine();
			if(title.equals("exit")) throw new ExitException();
			int count = Integer.parseInt(reader.readLine());
			
			/*for(Map.Entry<SportEquipment, Integer> good : goods.entrySet()){
				if (good.getKey().getTitle().equals(title) && count <=3){
					good.setValue(good.getValue() - count);
				} 
			}*/
			
			Iterator<SportEquipment> iterator = goods.keySet().iterator();
			
			while(iterator.hasNext()){
				SportEquipment equipment = iterator.next();
				if (equipment.getTitle().equals(title) && count <= 3){
					goods.put(equipment, goods.get(equipment) - count);
					
				} 
			}
		}
		
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
	
/*	private static void showRentedEquipment(){
		for(SportEquipment equipment :){
			System.out.println(.getKey() + ", Count: " + .getValue());
		}
	}*/

}
