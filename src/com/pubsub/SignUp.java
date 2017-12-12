package com.pubsub;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pubsub.configuration.Category;
import com.pubsub.configuration.impl.CategoryImpl;
import com.pubsub.utils.PasswordHash;
import com.pubsub.utils.PubSubConstants;

public class SignUp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public void signup() throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {
		User user = new User();
		List<User> allUsers = readUser();
		Scanner sc = new Scanner(System.in);
		boolean flag = false;
		System.out.println("Enter name: ");
		String username = sc.nextLine();
		
		if (allUsers != null) {
			for (User users : allUsers) {
				String name = users.getName();
				if (!name.equalsIgnoreCase(username)) {
					System.out.println("Enter password: ");
					String password = sc.nextLine();
					password = PasswordHash.generatePasswordHash(password);
					user = chooseInterest(user);
					
					user.setName(username);
					user.setPassword(password);
					allUsers.add(user);
					writeUser(allUsers);
					break;
				} else {
					flag = true;
				}
			}
		} else {
			allUsers = new ArrayList<>();
			
			System.out.println("Enter password: ");
			String password = sc.nextLine();
			password = PasswordHash.generatePasswordHash(password);
			
			user = chooseInterest(user);
			user.setName(username);
			user.setPassword(password);
			allUsers.add(user);
			writeUser(allUsers);
		}

		if (flag) {
			System.out.println("Username already exist choose another!");
			signup();
		}
		sc.close();
	}

	private User chooseInterest(User user)
			throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		Category category = new CategoryImpl();
		List<String> categories = category.getCategoryList();
		List<String> userChoiceCategory = new ArrayList<String>();
		int i;
		while (true) {
			for (i = 0; i < categories.size(); i++) {
				System.out.println(i + 1 + ". " + categories.get(i));
			}

			System.out.println(i + 1 + ". EXIT");
			Scanner sc = new Scanner(System.in);
			System.out.println("Choose your interest");
			int choice = sc.nextInt();

			if (choice > (i + 1)) {
				System.out.println("Invalid Choice");
			} else if (choice == (i + 1)) {
				sc.close();
				user.setUserInterest(userChoiceCategory);
				return user;
			} else {
				System.out.println(categories.get(choice - 1));
				userChoiceCategory.add(categories.get(choice - 1));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static List<User> readUser() {
		// TODO Auto-generated method stub
		List<User> users = null;
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(PubSubConstants.USER_FILE);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			users = (List<User>) objectInputStream.readObject();
			objectInputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return users;
	}

	private static void writeUser(List<User> users) throws IOException {
		// TODO Auto-generated method stub
		FileOutputStream fileOutputStream = new FileOutputStream(PubSubConstants.USER_FILE);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(users);
		objectOutputStream.close();
	}
}
