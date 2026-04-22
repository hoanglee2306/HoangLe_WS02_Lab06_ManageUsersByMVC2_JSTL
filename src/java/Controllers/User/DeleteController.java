package Controllers.User;

import Models.DAO.UserDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteController", urlPatterns = {"/DeleteController"})
public class DeleteController extends HttpServlet {
    private final String userController = "UserController";
    private final String searchPage = "Search.jsp";

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = searchPage;
        String message = null;
        try {
            UserDAO userDao = new UserDAO();
            String userName = request.getParameter("UserName");
            String searchValue = request.getParameter("SearchValue");

            if (userDao.deleteUser(userName)) {
                message = "Delete user successfully.";
            } else {
                message = "Delete user failed.";
            }

            if (searchValue == null) {
                searchValue = "";
            }
            url = userController + "?action=Search&txtSearchValue=" + searchValue;
        } catch (Exception ex) {
            log(ex.getMessage());
        } finally {
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
