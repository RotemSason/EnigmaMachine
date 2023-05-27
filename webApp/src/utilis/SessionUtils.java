package utilis;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {

    public static String getUsername (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute("contestname") : null;
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }
    public static String getAlliesname (HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute("alliesname") : null;
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }
    
    public static void clearSession (HttpServletRequest request) {
        request.getSession().invalidate();
    }
}