package NFTShop;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);


        int generalMenu = AllMenus.generalMenu(scan);
        if (generalMenu == 1) {
            //register a profile
            Person p1 = Person.personRegistration(scan);
            //choosing what to do
            int storeMenu = AllMenus.storeMenu(scan);
            while (storeMenu == 1 || storeMenu == 2 || storeMenu == 3 || storeMenu == 4) {
                if (storeMenu == 1) {
                    //buy coins
                    ZCoin.buyZCoin(scan, p1);

                } else if (storeMenu == 3) {
                    ZCoin.sellZCoin(scan, p1);
                } else if (storeMenu == 4) {
                    //check balance
                    System.out.println();
                    p1.checkBalance(p1);
                    System.out.println("You have "+ p1.getVolumeOfZCoins()+" ZCoins!");
                } else {
                    System.out.println("Wrong command!");
                }
                storeMenu = AllMenus.storeMenu(scan);
            }

        } else if (generalMenu == 2) {
            //log in profile
            Person p1 = new Person();
            System.out.print("Enter email to log in: ");
            String email = scan.nextLine();
            System.out.print("Enter your password to log in: ");
            String pass = scan.nextLine();
            boolean isInProfile = AllMenus.loginMenu(scan, email, pass, p1);

            if (!isInProfile) {
                System.out.println("Too many tries. If you forgot your pass use this link.[in progress]");
            } else {
                System.out.println("Successfully logged in!");

                int storeMenu = AllMenus.storeMenu(scan);
                while (storeMenu == 1 || storeMenu == 2 || storeMenu == 3 || storeMenu == 4) {
                    if (storeMenu == 1) {// kak e po dobre s if ili switch (predpolagam che if she e po baven)
                        //buy coins
                        ZCoin.buyZCoin(scan, p1);

                    } else if (storeMenu == 3) {
                        //sell coins
                        ZCoin.sellZCoin(scan, p1);
                    } else if (storeMenu == 4) {
                        //check balance
                        System.out.println();
                        p1.checkBalance(p1);
                        System.out.println("You have "+ p1.getVolumeOfZCoins()+" ZCoins!");
                    } else {
                        System.out.println("Wrong command!!");
                    }
                    storeMenu = AllMenus.storeMenu(scan);
                }
            }
        } else {
            System.out.println("Wrong command!");
        }
        System.out.println("GOODBYE");
        //  generalMenu = AllMenus.generalMenu(scan);
    }

}


