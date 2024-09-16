package ma.youcode.ecomove.utils;

public class Session {
    private static String loggedEmail;

    public static String getLoggedEmail() {
        return loggedEmail;
    }

    public static void setLoggedEmail(String loggedEmail) {
        Session.loggedEmail = loggedEmail;
    }
}
