package sdl_Assign;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
public static ArrayList<accountData>accountDataArrayList;
public static Scanner scanner;

public static void main(String[] args) {
	accountDataArrayList = new ArrayList<>();
	scanner = new Scanner(System.in);
	int ch;
	do {
	System.out.print("*********************\nBR14x Bank System\n*********************\n1-Login User\n2-Create an Account\n3-Deactivate Account\n4-Show Bank Accounts\n5-Exit \n : ");
	ch = scanner.nextInt();
	switch (ch) {
	case 1:
	System.out.print("Enter Account Number:");
	int accNo = scanner.nextInt();
	scanner.nextLine();
	System.out.print("Enter Password :");
	String pass = scanner.nextLine();
	boolean stat = false;
	int index = 0;
	for (int i = 0; i < accountDataArrayList.size(); i++) {
	if (accountDataArrayList.get(i).getAccountNumber() ==
	accNo) {
	stat = true;
	index = i;
	break;
	}
	}
	if (stat) {
	String pass1 =
	accountDataArrayList.get(index).getAccountPassword();
	if (pass1.equals(pass)) {
	System.out.println("WELCOME TO BR14x SALISBURY BRANCH BANK");
	showInformation(2,index);
	loggedInUser(index);
	} else {
	System.out.println("Wrong Password");
	break;
	
	}
	} else {
	System.out.println("Account Not Found");
	}
	System.out.println("Pass :" + pass);
	break;
	case 2:
	accountData account = new accountData();
	account.getData();
	System.out.println("Account Created");
	accountDataArrayList.add(account);
	break;
	case 3:
	System.out.print("Enter Account Number :");
	accNo = scanner.nextInt();
	scanner.nextLine();
	System.out.print("Enter Password :");
	pass = scanner.nextLine();
	stat = false;
	index = 0;
	for (int i = 0; i < accountDataArrayList.size(); i++) {
	if (accountDataArrayList.get(i).getAccountNumber() ==
	accNo) {
	stat = true;
	index = i;
	break;
	}
	}
	if (stat) {
	String pass1 =
	accountDataArrayList.get(index).getAccountPassword();
	if (pass1.equals(pass)) {
	System.out.print("Do you really want to delete your account ?[Y/N]");
	String ch1 = scanner.next();
	if (ch1.equals("Y")) {
	accountDataArrayList.remove(index);
	System.out.println("Account Deleted\nThanks for banking with us!");
	} else {
	System.out.println("Account Not Deleted");
	}
	} else {
	System.out.println("Wrong Password");
	break;
	}
	} else {
	System.out.println("Account Not Found");
	}
	break;
	case 4:
	System.out.print("Enter Master Password :");
	int passM = scanner.nextInt();
	if (passM == 9669) {
	System.out.println("Loading Data");
	
	} else {
	System.out.println("Wrong Password");
	}
	showInformation(1,0);
	break;
	}
	} while (ch < 5);
}
public static void loggedInUser(int index) {
int ch;
do {
System.out.print("\n| CUSTOMER PORTAL | \n1-Show Account Details \n2-Withdraw Amount\n3-Deposit Amount\n4-Transfer Amount\n5-Update Account Details\n6-Show Statement\n7-LogOut : ");
ch = scanner.nextInt();
switch (ch) {
case 1:
showInformation(2,index);
break;
case 2:
withdraw(index);
break;
case 3:
deposit(index);
break;
case 4:
transferAmount(index);
break;
case 5:
updateData(index);
break;
case 6:
showStatement(index);
break;
}
} while (ch < 7);
}
public static void transferAmount(int indexSender) {
int accNo;
boolean stat = false;
int indexReceiver = 0;
double amount;
System.out.println("TRANSFER MONEY PORTAL");
System.out.print("Enter The Name of Account Holder :");
scanner.nextLine();
String Name = scanner.nextLine();
System.out.print("Enter The Account Number Of Holder To Transfer Amount:");
accNo = scanner.nextInt();

for (int i = 0; i < accountDataArrayList.size(); i++) {

if (accountDataArrayList.get(i).getAccountNumber() == accNo &&
Name.equals(accountDataArrayList.get(i).getUserName())) {
stat = true;
indexReceiver = i;
break;
}
}
if (stat) {
scanner.nextLine();
System.out.print("Enter Amount to Transfer :");
amount = scanner.nextDouble();
double Amt1 =
accountDataArrayList.get(indexSender).getAccountBalance();
double Amt2 =
accountDataArrayList.get(indexReceiver).getAccountBalance();
if (Amt1 > amount + 3000.0) {
Amt1 -= amount;
Amt2 += amount;
accountDataArrayList.get(indexSender).setAccountBalance(Amt1);
accountDataArrayList.get(indexReceiver).setAccountBalance(Amt2);
SimpleDateFormat formattedDate = new SimpleDateFormat("dd:MM:yyyy hh:mm a");
String date = formattedDate.format(System.currentTimeMillis());
transaction Trans = new transaction(date, amount, "Withdraw",amount, "Transferred To " +
accountDataArrayList.get(indexReceiver).getUserName());
accountDataArrayList.get(indexSender).transactionStatement.add(Trans);
transaction Trans1 = new transaction(date, amount, "Deposit",
amount, "Transferred From " +
accountDataArrayList.get(indexSender).getUserName());
accountDataArrayList.get(indexReceiver).transactionStatement.add(Trans1);
} else {
System.out.print("You Dont Have Enough Balance To Transfer The Money");
}
} else {
System.out.println("Wrong Account Details");
}
}
public static void withdraw(int index) {
double amount = accountDataArrayList.get(index).getAccountBalance();
double withdrawAmount;
System.out.print("Enter The Amount You Withdraw : ");
withdrawAmount = scanner.nextDouble();
if (withdrawAmount > amount) {
System.out.println("Account Balance is low to withdraw amount " +
withdrawAmount);
} else {
amount -= withdrawAmount;
System.out.println("Amount Withdrawn \nAccount Balance : " + amount);
accountDataArrayList.get(index).setAccountBalance(amount);

SimpleDateFormat formattedDate = new SimpleDateFormat("dd:MM:yyyy hh:mm a");
String date = formattedDate.format(System.currentTimeMillis());
transaction Trans = new transaction(date, withdrawAmount, "Withdraw",
amount, "Withdrawn Self");
accountDataArrayList.get(index).transactionStatement.add(Trans);
}
}
public static void deposit(int index) {
	double amount = accountDataArrayList.get(index).getAccountBalance();
	double depositAmount;
	System.out.print("Enter The Amount You Deposit : ");
	depositAmount = scanner.nextDouble();
	amount += depositAmount;
	System.out.println("Amount Deposited \nAccount Balance :" + amount);
	accountDataArrayList.get(index).setAccountBalance(amount);
	SimpleDateFormat formattedDate = new SimpleDateFormat("dd:MM:yyyy hh:mm a");
	String date = formattedDate.format(System.currentTimeMillis());
	transaction Trans = new transaction(date, depositAmount, "Deposit",
	amount, "Deposited Self");
	accountDataArrayList.get(index).transactionStatement.add(Trans);
}
public static void showStatement(int index) {
	for (int i = 0; i < 110; i++)
	System.out.print('_');
	System.out.println();
	System.out.format("%1$-21s%2$-10s%3$-12s%4$-10s%5$-21s",
	"Date", "Amount", "Type", "Balance", "Description");
	System.out.println();
	for (int i = 0; i < 110; i++)
	System.out.print('_');
	System.out.println();
	for (int i = 0; i <
	accountDataArrayList.get(index).transactionStatement.size(); i++) {
	if(i!=0) {
	System.out.println();
	}
	transaction trans =
	accountDataArrayList.get(index).transactionStatement.get(i);
	System.out.format("%1$-21s%2$-10s%3$-12s%4$-10s%5$-21s",
	trans.getDate(),
	trans.getAmount(),
	trans.getType(),
	trans.getTotalBalance(),
	trans.getDescription());
	}
	
	System.out.println();
	for (int i = 0; i < 110; i++)
	System.out.print('_');
}
public static void updateData(int index) {
int ch;
String field2;
int ch2;
System.out.print("\nEnter Field To Change\n1.Name\n2.DOB\n3.PAN\n4.Address\n5.Nominee\n6.PhoneNumber\n7.Aadhar No");
ch = scanner.nextInt();
switch (ch) {
case 1:
scanner.nextLine();
System.out.println("Current Name :"
+accountDataArrayList.get(index).getUserName());
System.out.print("Enter Change Name To :");
field2 = scanner.nextLine();
System.out.print("Change Name [1/0]?");
ch2 = scanner.nextInt();
if (ch2 == 1) {
accountDataArrayList.get(index).setUserName(field2);
} else {
System.out.print("Name Unchanged");
}
break;
case 2:
scanner.nextLine();
System.out.println("Current DOB :"
+accountDataArrayList.get(index).getUserDOB());
System.out.print("Enter Change DOB To :");
field2 = scanner.nextLine();
System.out.print("Change DOB [1/0]?");
ch2 = scanner.nextInt();
if (ch2 == 1) {
accountDataArrayList.get(index).setUserDOB(field2);
} else {
System.out.print("DOB Unchanged");
}
break;
case 3:
scanner.nextLine();
System.out.println("Current PAN :"
+accountDataArrayList.get(index).getUserPAN());
System.out.print("Enter Change PAN To :");
field2 = scanner.nextLine();
System.out.print("Change PAN [1/0]?");
ch2 = scanner.nextInt();
if (ch2 == 1) {
accountDataArrayList.get(index).setUserPAN(field2);
} else {
System.out.print("PAN Unchanged");
}
break;
case 4:
scanner.nextLine();
System.out.println("Current Address :"
+accountDataArrayList.get(index).getUserAddress());

System.out.print("Enter Change Address :");
field2 = scanner.nextLine();
System.out.print("Change Address [1/0]?");
ch2 = scanner.nextInt();
if (ch2 == 1) {
accountDataArrayList.get(index).setUserAddress(field2);
} else {
System.out.print("Address Unchanged");
}
break;
case 5:
scanner.nextLine();
System.out.println("Current Nominee :"
+accountDataArrayList.get(index).getNominee());
System.out.print("Enter Change Nominee :");
field2 = scanner.nextLine();
System.out.print("Change Nominee [1/0]?");
ch2 = scanner.nextInt();
if (ch2 == 1) {
accountDataArrayList.get(index).setNominee(field2);
} else {
System.out.print("Nominee Unchanged");
}
break;
case 6:
scanner.nextLine();
System.out.println("Current Phone Number :"
+accountDataArrayList.get(index).getPhoneNumber());
boolean stat=false;
do {
System.out.print("Enter Change Phone Number To : ");
String phoneNum = scanner.nextLine();
if(phoneNum.length()!=10){
System.out.print("Phone Number Is Not Valid");
System.out.println();
stat=true;
phoneNum=null;
}
else{
for(int i=0;i<10;i++){
assert phoneNum != null;
if(phoneNum.charAt(i) >='0'&&
phoneNum.charAt(i)<='9'){
System.out.print("Change Phone [1/0]?");
ch2 = scanner.nextInt();
if (ch2 == 1) {
accountDataArrayList.get(index).setPhoneNumber(phoneNum);
} else {
System.out.print("Phone Number Unchanged");
}
stat=false;
}
else{
System.out.print("Phone Number Should Not Contain Any Letters");
System.out.println();
phoneNum=null;

stat=true;
}
}
}
}while(stat);
break;
case 7:
scanner.nextLine();
System.out.println("Current Aadhar :"
+accountDataArrayList.get(index).getUserAadhar());
stat=false;
do {
System.out.print("Enter Aadhar : ");
String userAadhar=scanner.nextLine();
if(userAadhar.length()!=12){
System.out.print("Aadhar Number Is Not Valid.");
System.out.println();
stat=true;
userAadhar=null;
}
else{
for(int i=0;i<12;i++){
if(userAadhar.charAt(i) >='0' &&
userAadhar.charAt(i)<='9'){
System.out.print("Change Aadhar [1/0]?");
ch2 = scanner.nextInt();
if (ch2 == 1) {
accountDataArrayList.get(index).setUserAadhar(userAadhar);
} else {
System.out.print("Aadhar Number Unchanged");
}
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
break;
}
}
public static void showInformation(int type, int index) { //1=whole data //2=single
	for (int i = 0; i < 155; i++)
	System.out.print('_');
	
	System.out.println();
	System.out.format("%1$-13s%2$-22s%3$-15s%4$-15s%5$-12s%6$-12s%7$-30s%8$-20s%9$-17s",
	"Account No", "Name", "Phone Number", "Aadhar No", "PAN No",
	"DOB", "Address", "Nominee","Account Balance");
	System.out.println();
	for (int i = 0; i < 155; i++)
	System.out.print('_');
	System.out.println();
	if(type==1){
	for (int i = 0; i < accountDataArrayList.size(); i++) {
	if(i!=0) {
	System.out.println();
	}
	show(i);
	if(i != accountDataArrayList.size()-1){
	for ( int j = 0; j<155; j++)
	System.out.print('_');
	}
	}
	}else if(type==2 ) {
	show(index);
	}
	for (int i = 0; i < 155; i++)
	System.out.print('_');
}
private static void show(int i) {
	System.out.format("%1$-13s%2$-22s%3$-15s%4$-15s%5$-12s%6$-12s%7$-30s%8$-20s%9$-17s",
	accountDataArrayList.get(i).getAccountNumber(),
	accountDataArrayList.get(i).getUserName(),
	accountDataArrayList.get(i).getPhoneNumber(),
	accountDataArrayList.get(i).getUserAadhar(),
	accountDataArrayList.get(i).getUserPAN(),
	accountDataArrayList.get(i).getUserDOB(),
	accountDataArrayList.get(i).getUserAddress(),
	accountDataArrayList.get(i).getNominee(),
	accountDataArrayList.get(i).getAccountBalance());
	System.out.println();
}
}