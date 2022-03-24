import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import java.util.*;

/**
 * 
 * @author George Guilford
 * @version 1.0
 * 
 * A class that tests object serialization within a login program
 */ 
public class UserLogin implements Serializable
{
	private String username;
	private String password;
	private UserLogin savedLogin;

	private transient FileOutputStream outputLoginFile;
	private transient FileInputStream inputLoginFile;
	private transient ObjectOutputStream userLogin;
	private transient ObjectInputStream savedUserLogin;

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

	public void save(UserLogin object){
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

	public UserLogin load(){
		try 
		{
			inputLoginFile = new FileInputStream("savedLogin.dat");
			savedUserLogin = new ObjectInputStream(inputLoginFile);
			savedLogin = (UserLogin) savedUserLogin.readObject();
			savedUserLogin.close();
		}
		catch (IOException ioException)
        {
            System.err.println("Error reading to file.");
        }
        catch (ClassNotFoundException errorClassNotFoundException)
        {
            System.err.println("Cannot find class");
        }
        return savedLogin;
	}
}