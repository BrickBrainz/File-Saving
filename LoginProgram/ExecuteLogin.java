import java.lang.NullPointerException;
import java.io.Serializable;
import java.util.*;

/**
 * @author Message me if you want my name
 * @version 1.0
 * 
 * This program executes a signup/login environment
 * 
 */ 
public class ExecuteLogin implements Serializable
{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in); //New scanner object to take input from user

		//Variables that handle spacing and cosmetics
		String space26 = charMuliply(' ', 26);
		String signupLogin = charMuliply('-', 20) + "Signup/Login" + charMuliply('-', 20);
		String dashesS = charMuliply('-', 23) + "Signup" + charMuliply('-', 23);
		String dashesL = charMuliply('-', 23) + "Login" + charMuliply('-', 24);

		System.out.printf("\n\n\n\n\n\n" + space26 + signupLogin + "\n\n" + charMuliply(' ', 21) + "If you dont have an account and would like to make one, type %d.\n" + charMuliply(' ', 33) + "If you already have an account, type %d.\n\n" + charMuliply(' ', 52), 1, 2);
		int choice = input.nextInt(); //This variable is used throughout the program to determine what is run based on the users input
		input.nextLine();
		System.out.println("\n" + space26 + signupLogin);

		UserLogin user = new UserLogin("", ""); //An object that will allow access to the UserLogin class
		ArrayList<UserLogin> storedLogins = new ArrayList<UserLogin>(); //An ArrayList that stores the current login info the user has inputted
		boolean executing = true; //Determines if the program is kept running or exited

		while(executing){
			if(choice == 1){
				boolean signingUp = true; //True - if user is still signing up. False - if user finishes signing up
				int numSignupFails = 0; //Number of times the user has failed 
				System.out.println("\n" + space26 + dashesS);

				while(signingUp){
					//Prints only if the user has not failed once
					if(numSignupFails == 0){
					System.out.println("________________________________________________________________________________________________________________");
					System.out.println("|                          Username > 3 letters, Username < 10 letters, NO SPACES                               |");
					System.out.println("|                                                                                                               |");
					System.out.println("|     Password contains at least one special character, number, and capital letter. Password > 6, NO SPACES     |");
					System.out.println("|_______________________________________________________________________________________________________________|");
					}
					System.out.print("\n" + space26 + "Please enter a username: ");
					String username = input.nextLine(); //variable that holds the username
					System.out.print(space26 + "Please enter a password: ");
					String password = input.nextLine(); //variable that holds the password
					System.out.println("");

					//A function that checks if the username and password meed the requirenments
					ArrayList<String> problems = usernameAndPasswordCheck(username, password , user);

					//If there is a problem, print it, and restart the while loop
					//If there is no problem, continue signingUp
					if(problems.size() > 0){
						for(String i : problems){
							System.out.println(space26 + i);
						}
						numSignupFails += 1;
						continue;
					}
					
					//Creates a new UserLogin object and stores it in the ArrayList "storedLogins"
					UserLogin newAccount = new UserLogin(username, password);
					storedLogins.add(newAccount);

					signingUp = false; //If the program has succesfully gotten this far, exit the while loop
					numSignupFails = 0;
				}
				user.addLogins(storedLogins); //Transfers the logins stored in "storedLogins" into an ArrayList that is contained in UserLogin
				System.out.print(space26 + "Congragulations! you have made a new account\n" + space26 + "If you would like to login with your new account, type 2. If you would like to exit type 3.\n\n" + charMuliply(' ', 52));
				choice = input.nextInt();
				input.nextLine();
				System.out.println("\n" + space26 + dashesS);
			}
			if(choice == 2){
				boolean notLoggedIn = true; //Self-explanatory
				int numLoginFails = 0; //Keeps number on how many times the user has failed the login
				System.out.println("\n" + space26 + dashesL);

				while(notLoggedIn){
					System.out.print("\n" + space26 + "Please enter your username: ");
					String username = input.nextLine(); //Variable for holding the username
					System.out.print(space26 + "Please enter your password: ");
					String password = input.nextLine(); //Variable for holding the password

					ArrayList<UserLogin> accounts = user.load(); //Loads an ArrayList into this variable from a file. For more information, see the comments in the UserLogin class

					//compares the logininfo in "accounts" to what the user entered 
					//If both the username and password match one of the accounts login info, the user is logged in successfully
					try 
					{
						for(UserLogin i : accounts){
							if(i.getUsername().equals(username) && i.getPassword().equals(password)) {
								System.out.println("\n" + charMuliply(' ', 45) + "Login was successful\n");
								System.out.println(space26 + dashesL);
								choice = 3;
								notLoggedIn = false;
							}
							else{
								numLoginFails += 1;
							}
						}
					}
					//If the file is empty, or there is no file, the login is failed and the while loop is restarted
					catch (NullPointerException e)
					{
						System.err.println("\n" + space26 + "Login failed, please try again");
						continue;
					}
					//If what the user entered does not equal any of the accounts login info, then the login is failed
					if(numLoginFails == accounts.size()){
						System.out.println("\n" + space26 + "Login failed, please try again");
						numLoginFails = 0;
					}
				}
			}
			//Program is exited
			if(choice == 3){
				executing = false;
			}
		}
	}

	//A funciton that checks to see if the username and password meet the requirenments
	public static ArrayList<String> usernameAndPasswordCheck(String username, String password, UserLogin user){
		ArrayList<String> problems = new ArrayList<String>(); //An ArrayList where all the problems are added to
		char[] usernameSplit = username.toCharArray();
		char[] passwordSplit = password.toCharArray();
		int regularChar = 0;
		int numCount = 0;
		int capChar = 0;

		//Checks to see if there is another account with the same username
		ArrayList<UserLogin> accounts = user.load();

		try 
		{
			for(UserLogin i : accounts){
				if(i.getUsername().equals(username)) {
					problems.add("The username " + username + " is already taken, please choose another one");
				}
			}
		}
		//same as before, just checks if the file is empty
		catch (NullPointerException e)
		{
			System.err.print("");
		}

		//Everything below is self-explanatory
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
			if(i >= 'a' && i <= 'z' || i == ' '){
				regularChar += 1;
			}
			if(i >= '0' && i <= '9'){
				numCount += 1;
				regularChar += 1;
			}
			if(i >= 'A' && i <= 'Z'){
				capChar += 1;
				regularChar += 1;
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

		return problems; //returns the ArrayList that holds all the problems

	}

	//A method that multiplies a character a number of times
	//This is used throughout my whole program.
	//There is probably an easier way to do this without making a function
	public static String charMuliply(char character, int size){
		String multipliedString = "";
		for(int i = 0; i <= size; i++){
			multipliedString += character;
		}
		return multipliedString;
	}
}