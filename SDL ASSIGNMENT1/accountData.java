package sdl_Assign;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class accountData {
	private String userName,userDOB,userPAN,userAddress,nominee,accountPassword;
	private String phoneNumber,userAadhar;
	private int accountNumber;
	private double accountBalance;
	Scanner scanner=new Scanner(System.in);
	public ArrayList<transaction> transactionStatement;
	private static int latestAccNo = 14010;
	public static int generateAccNo() {
	return ++latestAccNo;
}
public void getData(){
	boolean stat = false;
	System.out.println("Please fill the details to open your account\n");
	Random rand = new Random();
	System.out.print("Enter Full Name : ");
	userName=scanner.nextLine();
	System.out.print("Enter Date Of Birth : ");
	userDOB=scanner.nextLine();
	System.out.print("Enter Address : ");
	userAddress=scanner.nextLine();
	System.out.print("Enter Nominee : ");
	nominee = scanner.nextLine();
	System.out.print("Enter PAN : ");
	userPAN = scanner.nextLine();
	do {
	System.out.print("Enter Phone Number : ");
	phoneNumber = scanner.nextLine();
	if(phoneNumber.length()!=10){
	System.out.print("Phone Number Is Not Valid");
	System.out.println();
	stat=true;
	phoneNumber=null;
	}
	else{
	for(int i=0;i<10;i++){
	assert phoneNumber != null;
	if(phoneNumber.charAt(i) >='0' && phoneNumber.charAt(i)<='9'){
	stat=false;
	
	}
	else{
	System.out.print("Phone Number Should Not Contain Any Letters ");
	System.out.println();
	phoneNumber=null;
	stat=true;
	}
	}
	}
	}while(stat);
	do {
	System.out.print("Enter Aadhar : ");
	userAadhar=scanner.nextLine();
	if(userAadhar.length()!=12){
	System.out.print(" Aadhar Number Is Not Valid.");
	System.out.println();
	stat=true;
	userAadhar=null;
	}
	else{
	for(int i=0;i<12;i++){
		if(userAadhar.charAt(i) >='0' && userAadhar.charAt(i)<='9'){
		stat=false;
		}
		else{
		System.out.print("Aadhar Should Not Contain Any Letters");
		System.out.println();
		userAadhar=null;
		stat=true;
		}
	}
	}
	}while(stat);
	do {
	System.out.print("Enter Initial Balance (Must Be Above Rs.5000) : ");
	accountBalance = scanner.nextDouble();
	}while(accountBalance<5000);
	System.out.print("Creating An Account.....");
	accountNumber = generateAccNo();
	scanner.nextLine();
	boolean stat1;
	String pass1;
		do {
		System.out.print("\nPlease Create a Password that Should be of minimum size 8 with Letter, Digit And Special Characters : ");
		pass1 = scanner.nextLine();
		if (pass1.length() >= 8) {
		Pattern letter = Pattern.compile("[a-zA-z]");
		Pattern digit = Pattern.compile("[0-9]");
		
		Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
		//Pattern eight = Pattern.compile (&quot;.{8}&quot;);
		Matcher hasLetter = letter.matcher(pass1);
		Matcher hasDigit = digit.matcher(pass1);
		Matcher hasSpecial = special.matcher(pass1);
		if (hasDigit.find() && hasLetter.find() && hasSpecial.find()) {
		stat1=false;
		} else {
		System.out.println("Should be of minimum size 8 with LetterDigit And Special Characters");
		pass1 = null;
		stat1=true;
		}
		} else {
		System.out.println(" Invalid Pass. Should be of minimum size 8 ");
		stat1=true;
		}
	}while (stat1);
	System.out.print("Please Enter Password Again To Confirm :");
	String pass2 = scanner.nextLine();
	if (pass1.isEmpty()) {
	System.out.println("Password cant be empty");
	} else {
	if (pass2.isEmpty()) {
	System.out.println("Password cant be empty");
	} else {
	if (pass1.equals(pass2)) {
	accountPassword = pass1;
	System.out.println("Account Created With Account Number :"+ accountNumber);
	} else {
	System.out.println("Password Do Not Match");
	}
	}
	}
	transactionStatement=new ArrayList<>();
}
public String getUserName() {
return userName;
}
public String getUserDOB() {
return userDOB;
}
public String getUserPAN() {
return userPAN;
}
public String getUserAddress() {
return userAddress;
}

public String getNominee() {
return nominee;
}
public String getAccountPassword() {
return accountPassword;
}
public String getPhoneNumber() {
return phoneNumber;
}
public String getUserAadhar() {
return userAadhar;
}
public long getAccountNumber() {
return accountNumber;
}
public double getAccountBalance() {
return accountBalance;
}
public void setAccountBalance(double accountBalance) {
this.accountBalance = accountBalance;
}
public void setUserName(String userName) {
this.userName = userName;
}
public void setUserDOB(String userDOB) {
this.userDOB = userDOB;
}
public void setUserPAN(String userPAN) {
this.userPAN = userPAN;
}
public void setUserAddress(String userAddress) {
this.userAddress = userAddress;
}
public void setNominee(String nominee) {
this.nominee = nominee;
}
public void setPhoneNumber(String phoneNumber) {
this.phoneNumber = phoneNumber;
}
public void setUserAadhar(String userAadhar) {
this.userAadhar = userAadhar;
}
}