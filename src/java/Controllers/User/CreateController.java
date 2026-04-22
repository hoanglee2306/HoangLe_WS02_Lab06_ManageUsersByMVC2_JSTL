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

@WebServlet(name = "CreateController", urlPatterns = {"/CreateController"})
public class CreateController extends HttpServlet {
    private final String createPage = "CreateUser.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        boolean isError = false;
        boolean isAdmin = false;
        String message = null;
        String url = createPage;
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
                if (userDao.addUser(user)) {
                    message = "Create user successfully.";
                } else {
                    userError.setDuplicateUserName("The user name already exists.");
                    isError = true;
                }
            }
        } catch (Exception ex) {
            log(ex.getMessage());
            message = "An error occurred while creating user.";
            isError = true;
        } finally {
            if (isError) {
                request.setAttribute("errorDetails", userError);
            }
            request.setAttribute("message", message);
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
