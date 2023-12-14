package NFTShop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.regex.Matcher;

public class MyExceptions extends Exception {
    public MyExceptions(String errMessage) {
        super(errMessage);
    }

    public MyExceptions() {

    }
}
 class ConnectingDBException{
    static void connectingDB(Connection con) throws MyExceptions {

    }

}
