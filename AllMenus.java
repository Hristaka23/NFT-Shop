package NFTShop;

import java.sql.*;
import java.util.Scanner;

public class AllMenus {
    public static int generalMenu(Scanner scan) {
        System.out.println("======= YOU ARE ON THE GENERAL MENU! DO YOU HAVE AN ACCOUNT? :)  ========");
        System.out.println("Choose option 1 to sign up");
        System.out.println("Choose option 2 to log in");
        System.out.print("OPTION: ");
        int option = Integer.parseInt(scan.nextLine());

        return option;
    }

    public static int storeMenu(Scanner scan) {
        System.out.println();
        System.out.println("======= YOU ARE ON THE STORE MENU ========");
        System.out.println("Choose option 1 to buy ZCoins");
        System.out.println("Choose option 2 to use ZCoins[in progress]");
        System.out.println("Choose option 3 to sell ZCoins");
        System.out.println("Choose option 4 to check balance");
        System.out.println("Choose option 100 to exit");
        System.out.print("OPTION: ");
        int option = Integer.parseInt(scan.nextLine());

        return option;
    }

    public static boolean loginMenu(Scanner scan, String email, String pass, Person p1) {
        int counts = 0;
        while (counts < 2) {

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registered_people", "root", "");
                String query = "select email_person, pass_person from people_info ";
                Statement state = con.createStatement();
                ResultSet res = state.executeQuery(query);
                while (res.next()) {
                    String checkEmail = res.getString("email_person");
                    String checkPass = res.getString("pass_person");
                    if (checkEmail.equals(email) && checkPass.equals(pass)) {
                        // System.out.println("You entered in your profile!");
                        p1.setPassword(pass);
                        p1.setEmail(email);
                        return true;
                    }
                }
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println("Wrong filled information. TRY AGAIN");
            System.out.print("Enter email to log in: ");
            email = scan.nextLine();
            System.out.print("Enter your password to log in: ");
            pass = scan.nextLine();
            counts++;
        }

        return false;
    }
}
