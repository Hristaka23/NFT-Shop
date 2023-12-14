package NFTShop;

import java.sql.*;
import java.util.Scanner;

public class ZCoin {
    public static int quantity;

    public static void buyZCoin(Scanner scan, Person p1) {
        //checkBalance

        String command = "buy";
        System.out.println("The maximum volume of ZCoins is 5000 per person!");
        System.out.print("Enter how much coins do you want buy: ");
        int wantedCoins = Integer.parseInt(scan.nextLine());
        boolean canYouBuy = setQuantity(wantedCoins, command, scan, p1);
        if (canYouBuy) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registered_people", "root", "");
                String query = "update people_info set zcoin_person = ?  where pass_person = '" + p1.getPassword() + "' and email_person = '" + p1.getEmail() + "'";
                PreparedStatement prep = con.prepareStatement(query);


                prep.setInt(1, p1.getVolumeOfZCoins());


                prep.executeUpdate();
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void sellZCoin(Scanner scan, Person p1) {
        //checkBalance

        String command = "sell";
        System.out.print("Enter how much coins do you want to sell: ");
        int wantedCoins = Integer.parseInt(scan.nextLine());
        boolean canYouSell = setQuantity(wantedCoins, command, scan, p1);


        if (canYouSell) {
            if (p1.getVolumeOfZCoins() < 0) {
                System.out.println("Not enough ZCoins to sell!");
            }
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registered_people", "root", "");
                String query = "update people_info set zcoin_person = ?  where pass_person = '" + p1.getPassword() + "' and email_person = '" + p1.getEmail() + "'";
                PreparedStatement prep = con.prepareStatement(query);


                prep.setInt(1, p1.getVolumeOfZCoins());


                prep.executeUpdate();
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


    }

    public static boolean setQuantity(int crrZCoinsForOperations, String command, Scanner scan, Person p1) {
        p1.checkBalance(p1);
      //  p1.getVolumeOfZCoins();//she stane recursiq ako go sloja v set method

        if (command.equals("buy") && p1.getVolumeOfZCoins() < 5000) {
            while (crrZCoinsForOperations > 5000 || crrZCoinsForOperations <= 0 || (p1.getVolumeOfZCoins() + crrZCoinsForOperations) > 5000) {
                System.out.print("Enter smaller volume of ZCoins[max you can buy is " + (5000 - p1.getVolumeOfZCoins()) + "]: ");
                crrZCoinsForOperations = Integer.parseInt(scan.nextLine());

            }
            if (p1.getVolumeOfZCoins() == 0) {
                p1.setVolumeOfZCoins(crrZCoinsForOperations);
                System.out.println();
                System.out.println("Successfully transferred "+crrZCoinsForOperations+" ZCoins to your profile");

            } else {
                p1.setVolumeOfZCoins(p1.getVolumeOfZCoins() + crrZCoinsForOperations);
                System.out.println();
                System.out.println("Successfully transferred "+crrZCoinsForOperations+" ZCoins to your profile");
            }
        } else if (command.equals("sell")) {


            if (p1.getVolumeOfZCoins() == 0) {
                System.out.println("No ZCoins to sell!");
                return false;
            } else {
                while (crrZCoinsForOperations > p1.getVolumeOfZCoins() || crrZCoinsForOperations <= 0) {
                    System.out.print("Enter volume of ZCoins between " + p1.getVolumeOfZCoins() + " and 1 ");
                    crrZCoinsForOperations = Integer.parseInt(scan.nextLine());
                }
            }


        } else {
            System.out.println("Can't buy any ZCoins");
            return false;

        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registered_people", "root", "");
            String query = "select volume_of_zcoins from only_volume_of_zcoins ";
            PreparedStatement state = con.prepareStatement(query);
            ResultSet res = state.executeQuery(query);
            while (res.next()) {
                quantity = res.getInt("volume_of_zcoins");
                if (command.equals("buy")) {
                    if (quantity <= 0) {
                        System.out.println("SORRY NO MORE ZCoins in the blockchain!");
                        return false;
                    } else {
                        quantity = quantity - crrZCoinsForOperations;
                        query = "update only_volume_of_zcoins set volume_of_zcoins = ? ";
                        state = con.prepareStatement(query);
                        state.setInt(1, quantity);
                        state.executeUpdate();

                    }
                } else if (command.equals("sell")) {
                    quantity = quantity + crrZCoinsForOperations;
                    query = "update only_volume_of_zcoins set volume_of_zcoins = ? ";
                    state = con.prepareStatement(query);
                    state.setInt(1, quantity);
                    state.executeUpdate();
                    p1.setVolumeOfZCoins(p1.getVolumeOfZCoins() - crrZCoinsForOperations);
                    System.out.println();
                    System.out.println("Successfully sold " + crrZCoinsForOperations + " ZCoins!");
                    return true;
                }
            }

            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public int getQuantity() {
        return quantity;
    }
}
