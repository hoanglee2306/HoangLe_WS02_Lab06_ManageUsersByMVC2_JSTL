package Controllers.Authentication;

import Models.DAO.UserDAO;
import Models.DTO.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {
    private final String searchPage = "Search.jsp";
    private final String userController = "UserController";
    private final String loginPage = "Login.jsp";

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = loginPage;
        try {
            String userName = request.getParameter("txtUserName");
            String password = request.getParameter("txtPassword");
            UserDAO userDao = new UserDAO();
            User user = userDao.login(userName, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userLoggedIn", user);
                if (user.isIsAdmin()) {
                    url = searchPage;
                } else {
                    url = userController + "?action=Details&UserName=" + userName;
                }
            } else {
                request.setAttribute("message", "The user name or password is invalid");
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
