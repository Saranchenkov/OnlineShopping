class SportEquipment {
	private Category category;
	private String title;
	private int price;
	
	SportEquipment(Category category, String title, int price){
		this.category = category;
		this.title = title;
		this.price = price;
	}
	
	public String toString(){
		return String.format("Category: %s, Name: %s, Price: %d$", category, title, price);
	}
	
	public String getTitle(){
		return title;
	}
}
