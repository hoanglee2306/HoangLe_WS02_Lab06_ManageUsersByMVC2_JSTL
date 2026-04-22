package Controllers.User;

import Models.DAO.UserDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SearchController", urlPatterns = {"", "/SearchController"})
public class SearchController extends HttpServlet {
    private final String searchPage = "Search.jsp";

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = searchPage;

        try {
            String searchValue = request.getParameter("txtSearchValue");
            if (searchValue != null && !searchValue.isEmpty()) {
                UserDAO userDao = new UserDAO();
                List<?> userList = userDao.searchUserByLastName(searchValue);
                request.setAttribute("searchResult", userList);
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
