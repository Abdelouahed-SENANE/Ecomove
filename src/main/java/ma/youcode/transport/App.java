package ma.youcode.transport;

import java.sql.SQLException;

import ma.youcode.transport.ui.Menu;

public class App {
    public static void main(String[] args) throws SQLException {

        Menu menu = new Menu();
        menu.start(menu);
        System.out.println("Application has been stoped");

    }

}