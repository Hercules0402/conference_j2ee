package servlets;

import beans.AddCalculator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "AddServlet",
        description = "Additionneur Calculatrice",
        displayName = "Additionneur",
        urlPatterns = "/add"
)
@ApplicationScoped
public class AddServlet extends HttpServlet {

    private Long addValue = null;

    @Inject
    private AddCalculator addCalculator;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getOutputStream().println((addValue == null) ? "Undefined" : addValue.toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addValue = addCalculator.calculate(Long.parseLong(request.getParameter("first")),Long.parseLong(request.getParameter("second")));
        this.doGet(request, response);
    }
}
