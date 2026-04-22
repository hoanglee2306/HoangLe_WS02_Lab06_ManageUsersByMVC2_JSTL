package Controllers.User;

import Models.DTO.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {
    private static final String CREATE_CONTROLLER = "CreateController";
    private static final String SEARCH_CONTROLLER = "SearchController";
    private static final String UPDATE_CONTROLLER = "UpdateController";
    private static final String DELETE_CONTROLLER = "DeleteController";
    private static final String USER_DETAILS_CONTROLLER = "UserDetailsController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = SEARCH_CONTROLLER;
        try {
            HttpSession session = request.getSession(false);
            User userLoggedIn = session == null ? null : (User) session.getAttribute("userLoggedIn");
            String action = request.getParameter("action");

            if (userLoggedIn == null) {
                if (action != null && action.equalsIgnoreCase("Create")) {
                    url = CREATE_CONTROLLER;
                } else {
                    url = "LogoutController";
                }
            } else if (action == null || action.isEmpty()) {
                if (userLoggedIn.isIsAdmin()) {
                    url = SEARCH_CONTROLLER;
                } else {
                    url = USER_DETAILS_CONTROLLER + "?UserName=" + userLoggedIn.getUserName();
                }
            } else if (action.equalsIgnoreCase("Create")) {
                url = CREATE_CONTROLLER;
            } else if (action.equalsIgnoreCase("Delete")) {
                url = DELETE_CONTROLLER;
            } else if (action.equalsIgnoreCase("Search")) {
                url = SEARCH_CONTROLLER;
            } else if (action.equalsIgnoreCase("Details")) {
                url = USER_DETAILS_CONTROLLER;
            } else if (action.equalsIgnoreCase("Update")) {
                url = UPDATE_CONTROLLER;
            }
        } catch (Exception ex) {
            log(ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
