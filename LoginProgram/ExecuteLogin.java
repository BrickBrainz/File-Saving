import java.io.Serializable;
import java.util.*;

public class ExecuteLogin implements Serializable
{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		String dashesS = charMuliply('-', 23) + "Signup" + charMuliply('-', 23);
		String dashesL = charMuliply('-', 23) + "Login" + charMuliply('-', 24); 
		System.out.printf("\n\n\n\n\n\n" + charMuliply(' ', 26) + charMuliply('-', 20) + "Signup/Login" + charMuliply('-', 20) + "\n\n" + charMuliply(' ', 21) + "If you dont have an account and would like to make one, type %d.\n" + charMuliply(' ', 33) + "If you already have an account, type %d.\n\n" + charMuliply(' ', 52), 1, 2);
		int choice = input.nextInt();
		input.nextLine();
		System.out.println("\n" + charMuliply(' ', 26) + charMuliply('-', 20) + "Signup/Login" + charMuliply('-', 20));
		UserLogin user = new UserLogin("", "");
		boolean executing = true;

		while(executing){
			if(choice == 1){
				boolean signingUp = true;
				int numSignupFails = 0;
				ArrayList<UserLogin> storedLogins = new ArrayList<UserLogin>();
				System.out.println("\n" + charMuliply(' ', 26) + dashesS);
				while(signingUp){
					if(numSignupFails == 0){
					System.out.println("________________________________________________________________________________________________________________");
					System.out.println("|                          Username > 3 letters, Username < 10 letters, NO SPACES                               |");
					System.out.println("|                                                                                                               |");
					System.out.println("|     Password contains at least one special character, number, and capital letter. Password > 6, NO SPACES     |");
					System.out.println("|_______________________________________________________________________________________________________________|");
					}
					System.out.print("\n" + charMuliply(' ', 15) + "Please enter a username: ");
					String username = input.nextLine();
					System.out.print(charMuliply(' ', 15) + "Please enter a password: ");
					String password = input.nextLine();
					System.out.println("");

					ArrayList<String> problems = usernameAndPasswordCheck(username, password);
					if(problems.size() > 0){
						for(String i : problems){
							System.out.println(i);
						}
						numSignupFails += 1;
						continue;
					}

					UserLogin newAccount = new UserLogin(username, password);
					storedLogins.add(newAccount);

					signingUp = false;
					numSignupFails = 0;
				}
				user.addLogins(storedLogins); 
				System.out.print("Congragulations! you have made a new account\nIf you would like to login with your new account, type 2. If you would like to exit type 3.\n\n" + charMuliply(' ', 52));
				choice = input.nextInt();
				input.nextLine();
				System.out.println("\n" + charMuliply(' ', 26) + dashesS);
			}
			if(choice == 2){
				boolean notLoggedIn = true;
				int numLoginFails = 0;
				System.out.println("\n" + charMuliply(' ', 26) + dashesL);
				while(notLoggedIn){
					System.out.print("\nPlease enter your username: ");
					String username = input.nextLine();
					System.out.print("Please enter your password: ");
					String password = input.nextLine();

					ArrayList<UserLogin> accounts = user.load();

					for(UserLogin i : accounts){
						if(i.getUsername().equals(username) && i.getPassword().equals(password)) {
							System.out.println("\nLogin was successful\n");
							System.out.println(charMuliply(' ', 26) + dashesL);
							choice = 3;
							notLoggedIn = false;
						}
						else{
							numLoginFails += 1;
						}
					}
					if(numLoginFails == accounts.size()){
						System.out.println("\nLogin failed, please try again");
						numLoginFails = 0;
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
			if(i >= 'A' && i <= 'Z' || i >= 'a' && i <= 'z'|| i >= '0' && i <= '9' || i == ' '){ //Can likely make this more optimized
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