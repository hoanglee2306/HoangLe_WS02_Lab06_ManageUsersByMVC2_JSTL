package Controllers.User;

import Models.DAO.UserDAO;
import Models.DTO.User;
import Models.DTO.UserError;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateController", urlPatterns = {"/UpdateController"})
public class UpdateController extends HttpServlet {
    private final String userController = "UserController";

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        boolean isAdmin = false;
        boolean isError = false;
        String message = null;
        String url = userController;
        UserError userError = new UserError();
        try {
            UserDAO userDao = new UserDAO();
            String userName = request.getParameter("txtUserName");
            String password = request.getParameter("txtPassword");
            String lastName = request.getParameter("txtLastName");
            String admin = request.getParameter("chkIsAdmin");

            if (userName == null || !userName.matches("^[\\w]{1,15}$")) {
                userError.setUserNameError("The UserName must be 1 to 15 characters (letters, digits, underscore).");
                isError = true;
            }
            if (password == null || !password.matches("^[\\w]{1,15}$")) {
                userError.setPasswordError("The Password must be 1 to 15 characters.");
                isError = true;
            }
            if (lastName == null || !lastName.matches("^[\\w ]{1,50}$")) {
                userError.setLastNameError("The LastName must be 1 to 50 characters.");
                isError = true;
            }

            if (!isError) {
                isAdmin = admin != null;
                User user = new User(userName, password, lastName, isAdmin);
                if (userDao.updateUser(user)) {
                    message = "The user has been updated successfully.";
                } else {
                    message = "Update user failed.";
                }
            } else {
                request.setAttribute("errorDetails", userError);
                request.setAttribute("userDetails", new User(userName, password, lastName, admin != null));
                url = "UserDetails.jsp";
            }
        } catch (Exception ex) {
            log(ex.getMessage());
        } finally {
            request.setAttribute("message", message);
            if ("UserController".equals(url)) {
                String userName = request.getParameter("txtUserName");
                url = userController + "?action=Details&UserName=" + userName;
            }
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
