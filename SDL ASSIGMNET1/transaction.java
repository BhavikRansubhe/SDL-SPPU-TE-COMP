package sdl_Assign;

public class transaction {
	private String date;
	private double amount;
	private String type;
	private double totalBalance;
	private String description;
	public double getAmount() {
	return amount;
}
public String getDate() {
	return date;
}
public transaction(String date, double amount, String type, double totalBalance,String description) {
	this.date = date;
	this.amount = amount;
	this.type = type;
	this.totalBalance = totalBalance;
	this.description =description;
}
public String getType() {
	return type;
}
public double getTotalBalance() {
	return totalBalance;
}
public String getDescription() {
	return description;
}
}
