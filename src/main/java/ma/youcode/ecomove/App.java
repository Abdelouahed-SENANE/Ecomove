package ma.youcode.ecomove;
import ma.youcode.ecomove.ui.Menu;

public class App {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start(menu);
        System.out.println("Application has been stopped");
        System.exit(0);
    }
}