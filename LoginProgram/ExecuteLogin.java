import java.io.Serializable;
import java.util.*;

public class ExecuteLogin implements Serializable
{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		String dashesS = charMuliply(' ', 12) + charMuliply('-', 20) + "Signup" + charMuliply('-', 20);
		String dashesL = charMuliply(' ', 12) + charMuliply('-', 20) + "Login" + charMuliply('-', 20);
 
		System.out.printf(charMuliply(' ', 12) + charMuliply('-', 20) + "Signup/Login" + charMuliply('-', 20) + "\nIf you dont have an account and would like to make one, type %d.\nIf you already have an account, type %d.\n" + charMuliply(' ', 12) + charMuliply('-', 20) + "Signup/Login" + charMuliply('-', 20) + "\nChoice: ", 1, 2);
		int choice = input.nextInt();
		input.nextLine();
		UserLogin user = new UserLogin("", "");
		boolean executing = true;

		while(executing){
			if(choice == 1){
				boolean keepAdding = true;
				ArrayList<UserLogin> storedLogins = new ArrayList<UserLogin>();
				while(keepAdding){
					System.out.print(dashesS + "\nPlease enter a username (Username > 3 letters, Username < 10 letters, NO SPACES): ");
					String username = input.nextLine();
					System.out.print("Please enter a password (Password contains at least one special character, number, and capital letter. Password > 6, NO SPACES): ");
					String password = input.nextLine();
					System.out.println(dashesS);

					ArrayList<String> problems = usernameAndPasswordCheck(username, password);
					if(problems.size() > 0){
						for(String i : problems){
							System.out.println(i);
						}
						continue;
					}

					UserLogin newAccount = new UserLogin(username, password);
					storedLogins.add(newAccount);

					System.out.println("\n" + charMuliply(' ', 12) + charMuliply('*', 47) + "\nCongragulations" + username + ", you have made a new account");
					System.out.print("If you would like to create another acount type 1. If not, type 2.\n" + charMuliply(' ', 12) + charMuliply('*', 47) + "\nChoice: ");

					int choice2 = input.nextInt();
					input.nextLine();
					if(choice2 == 1){
						continue;
					}
					else{
						keepAdding = false;
					}
				}
				user.addLogins(storedLogins); 
				System.out.print(charMuliply(' ', 12) + charMuliply('-', 20) + "Signup/Exit" + charMuliply('-', 20) + "\nIf you would like to login with your new account, type 2. If you would like to exit type 3.\n" + charMuliply(' ', 12) + charMuliply('-', 20) + "Signup/Exit" + charMuliply('-', 20) + "\nChoice: ");
				choice = input.nextInt();
				input.nextLine();
			}
			if(choice == 2){
				boolean notLoggedIn = true;
				int numFails = 0;
				while(notLoggedIn){
					System.out.print(dashesL + "\nPlease enter your username: ");
					String username = input.nextLine();
					System.out.print("Please enter your password: ");
					String password = input.nextLine();
					System.out.println(dashesL);

					ArrayList<UserLogin> accounts = user.load();

					for(UserLogin i : accounts){
						if(i.getUsername().equals(username) && i.getPassword().equals(password)) {
							System.out.println(charMuliply(' ', 12) + charMuliply('*', 47) + "\nLogin was successful\n" + charMuliply(' ', 12) + charMuliply('*', 47));
							choice = 3;
							notLoggedIn = false;
						}
						else{
							numFails += 1;
						}
					}
					if(numFails == accounts.size()){
						System.out.println("Login failed, please try again");
						numFails = 0;
					}
				}
			}
			if(choice == 3){
				executing = false;
			}
		}
	}

	public static ArrayList<String> usernameAndPasswordCheck(String username, String password){
		ArrayList<String> problems = new ArrayList<String>();
		char[] usernameSplit = username.toCharArray();
		char[] passwordSplit = password.toCharArray();
		int regularChar = 0;
		int numCount = 0;
		int capChar = 0;

		if(usernameSplit.length < 3){
			problems.add("Username has to be atleast 3 characters");
		}
		if(usernameSplit.length > 10){
			problems.add("Username cannot be more than 10 characters");
		}
		for(char i : usernameSplit){
			if(i == ' '){
				problems.add("Username cannot contain spaces");
				break;
			}
		}

		if(passwordSplit.length < 6){
			problems.add("Password has to be atleast 6 characters long");
		}
		for(char i : passwordSplit){
			if(i == ' '){
				problems.add("Password cannot contain spaces");
			}
			if(i >= 'A' && i <= 'Z' || i >= 'a' && i <= 'z'|| i >= '0' && i <= '9' || i == ' '){
				regularChar += 1;
			}
			if(i >= '0' && i <= '9'){
				numCount += 1;
			}
			if(i >= 'A' && i <= 'Z'){
				capChar += 1;
			}
		}
		if(regularChar == passwordSplit.length){
			problems.add("There has to be at least one special character in the password");
		}
		if(numCount == 0){
			problems.add("There has to be at least one number in password");
		}
		if(capChar == 0){
			problems.add("There has to be at least one capital letter");
		}

		return problems;

	}

	public static String charMuliply(char character, int size){
		String multipliedString = "";
		for(int i = 0; i <= size; i++){
			multipliedString += character;
		}
		return multipliedString;
	}
}