package servlets;

import beans.MulCalculator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "MulServlet",
        description = "Multiplicateur Calculatrice",
        displayName = "Multiplicateur",
        urlPatterns = "/mul"
)
@ApplicationScoped
public class MulServlet extends HttpServlet {
    private Long mulValue = null;

    @Inject
    private MulCalculator mulCalculator;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getOutputStream().println((mulValue == null) ? "Undefined" : mulValue.toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mulValue = mulCalculator.calculate(Long.parseLong(request.getParameter("first")),Long.parseLong(request.getParameter("second")));
        this.doGet(request, response);
    }
}
