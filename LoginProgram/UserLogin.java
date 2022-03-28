import java.io.Serializable;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import java.util.*;

/**
 * 
 * @author Message me if you want my name
 * @version 1.0
 * 
 * A class that carries out object serialization within a login program
 */ 
public class UserLogin implements Serializable
{
	//Instance variables
	private String username; //Holds the username
	private String password; //Holds the password
	private UserLogin savedLogin; //I honestly forgot what this is for, might be for nothing
	ArrayList<UserLogin> users = new ArrayList<UserLogin>(); //An ArrayList that holds all the users in a file
	ArrayList<UserLogin> savedUsers;

	//Object serialization variables
	private transient FileOutputStream outputLoginFile;
	private transient FileInputStream inputLoginFile;
	private transient ObjectOutputStream userLogin;
	private transient ObjectInputStream savedUserLogin;

	//Constructor
	public UserLogin(String username, String password){
		this.username = username;
		this.password = password;
	}

	public String getUsername(){
		return this.username;
	}

	public String getPassword(){
		return this.password;
	}

	//Transfers a UserLogin ArrayList into a file called savedLogin
	public void save(ArrayList<UserLogin> object){
		try
		{
			outputLoginFile = new FileOutputStream("savedLogin.dat");
			userLogin = new ObjectOutputStream(outputLoginFile);
			userLogin.writeObject(object);
			userLogin.close();
		}
		catch (IOException ioException)
        {
            System.err.println("Error writing to file.");
            ioException.printStackTrace();
        }
	}

	//Reads the contents of savedLogin and saves it to a declared variable 
	public ArrayList<UserLogin> load(){
		try 
		{
			inputLoginFile = new FileInputStream("savedLogin.dat");
			savedUserLogin = new ObjectInputStream(inputLoginFile);
			savedUsers = (ArrayList<UserLogin>) savedUserLogin.readObject();
			savedUserLogin.close();
		}
		catch (IOException ioException)
        {
            System.err.print("");
        }
        catch (ClassNotFoundException errorClassNotFoundException)
        {
            System.err.println("Cannot find class");
        }
        return savedUsers; 
	}

	//An extra-step function. It takes a UserLogin ArrayList and sends it to the save function. Probably could cut this out and make the code shorter.
	public void addLogins(ArrayList<UserLogin> storedLogins){
		save(storedLogins);
	}
}