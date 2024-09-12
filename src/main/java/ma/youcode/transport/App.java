package ma.youcode.transport;
import ma.youcode.transport.ui.Menu;

public class App {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start(menu);
        System.out.println("Application has been stopped");
        System.exit(0);
    }
}