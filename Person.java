package NFTShop;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
    String name;
    String surname;
    String password;
    String email;
    int volumeOfZCoins;

    public Person() {
    }

    public Person(String name, String surname, String password, String email) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }

    public static Person personRegistration(Scanner scan) {
        System.out.println();
        System.out.println("======= PLEASE FILL IN YOUR INFORMATION ========");
        System.out.println();
        String regForFirstName = "\\b[A-Z][a-z]+\\b";
        String regForLastName = "\\b[A-Z][a-z]+[o][v]a?\\b";
        String regForPass = "^.*(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!*-]).*$";
        String regForEmail = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        Pattern patternForFirstName = Pattern.compile(regForFirstName);
        Pattern patternForLastName = Pattern.compile(regForLastName);
        Pattern patternForPass = Pattern.compile(regForPass);
        Pattern patternForEmail = Pattern.compile(regForEmail);

        System.out.print("Enter your first name: ");
        String firstName = scan.nextLine();
        Matcher firstNameMatcher = patternForFirstName.matcher(firstName);
        while (!firstNameMatcher.matches()) {
            System.out.println("Wrong written first name!");
            System.out.print("Please write it again: ");
            firstName = scan.nextLine();
            firstNameMatcher = patternForFirstName.matcher(firstName);
        }

        System.out.print("Enter your last name: ");
        String lastName = scan.nextLine();
        Matcher lastNameMatcher = patternForFirstName.matcher(lastName);
        while (!lastNameMatcher.matches()) {
            System.out.println("Wrong written surname");
            System.out.print("Please write it again: ");
            lastName = scan.nextLine();
            lastNameMatcher = patternForFirstName.matcher(lastName);
        }

        System.out.print("Enter your email: ");
        String email = scan.nextLine();
        Matcher emailMather = patternForEmail.matcher(email);
        while (!emailMather.matches()) {
            System.out.println("Wrong written email");
            System.out.print("Please write it again: ");
            email = scan.nextLine();
            emailMather = patternForEmail.matcher(email);
        }

        System.out.print("Now create your password[at least 8symbols, must have UPPER CASE LETTER, lower case letter");
        System.out.print(", number and special symbol: ");
        String pass = scan.nextLine();
        Matcher passMatcher = patternForPass.matcher(pass);
        while (!passMatcher.matches()) {
            System.out.println("Pass not written in the right way");
            System.out.print("Your password must be: [at least 8symbols, must have UPPER CASE LETTER, lower case letter");
            System.out.print(", number and special symbol: ");
            pass = scan.nextLine();
            passMatcher = patternForPass.matcher(pass);
        }

        Person p1 = new Person(firstName, lastName, pass, email);


        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registered_people", "root", "");
            String query = "insert into people_info (name_person, surname_person, pass_person,email_person)" + "values(?,?,?,?)";
            PreparedStatement prep = con.prepareStatement(query);//, Statement.RETURN_GENERATED_KEYS


            prep.setString(1, p1.getName());
            prep.setString(2, p1.getSurname());
            prep.setString(3, p1.getPassword());
            prep.setString(4, p1.getEmail());

            prep.execute();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Successfully registered!");
        return p1;
    }

    public int getVolumeOfZCoins() {

        return volumeOfZCoins;
    }

    public void setVolumeOfZCoins(int volumeOfZCoins) {
        if (volumeOfZCoins > 5000) {
            System.out.println("Can't hold that much ZCoins!");
        } else {
            this.volumeOfZCoins = volumeOfZCoins;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void checkBalance(Person p1) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registered_people", "root", "");

            String query = "select zcoin_person,pass_person, email_person from people_info";
            PreparedStatement state = con.prepareStatement(query);
            ResultSet res = state.executeQuery(query);
            while (res.next()) {
                String checkEmail = res.getString("email_person");
                String checkPass = res.getString("pass_person");
                int crrVolumeZCoins = res.getInt("zcoin_person");
                if (checkEmail.equals(p1.getEmail()) && checkPass.equals(p1.getPassword())) {
                    // System.out.println("You entered in your profile!");
                    p1.setVolumeOfZCoins(crrVolumeZCoins);
                    break;
                }
            }
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
