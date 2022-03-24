import java.io.Serializable;
import java.util.*;

public class ExecuteLogin implements Serializable
{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);

		System.out.printf("If you dont have an account and would like to make one, type %d.\nIf you already have an account, type %d.\nChoice: ", 1, 2);
		int choice = input.nextInt();
		UserLogin user = new UserLogin("", "");
		boolean executing = true;

		while(executing){
			if(choice == 1){
				System.out.print("Please enter a username: ");
				String username = input.next();
				System.out.print("Please enter a password: ");
				String password = input.next();
				UserLogin newAccount = new UserLogin(username, password);
				newAccount.save(newAccount); 
				System.out.println("\nCongragulations, you have made a new account\n");
				System.out.println("If you would like to login with your new account, type 2. If you would like to exit type 3.");
				choice = input.nextInt();
			}
			if(choice == 2){
				boolean login = false;
				while(login == false){
					System.out.print("Please enter your username: ");
					String username = input.next();
					System.out.print("Please enter your password: ");
					String password = input.next();
					UserLogin account = user.load();
					if(account.getUsername().equals(username) && account.getPassword().equals(password)) {
						System.out.println("Login was successful");
						choice = 3;
						login = true;
					}
					else{
						System.out.println("Login failed");
					}
				}
			}
			if(choice == 3){
				executing = false;
			}
		}
	}
}