package servlets;

import beans.SubCalculator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "SubServlet",
        description = "Soustracteur Calculatrice",
        displayName = "Soustracteur",
        urlPatterns = "/sub"
)
@ApplicationScoped
public class SubServlet extends HttpServlet {

    private Long subValue = null;

    @Inject
    private SubCalculator subCalculator;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getOutputStream().println((subValue == null) ? "Undefined" : subValue.toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        subValue = subCalculator.calculate(Long.parseLong(request.getParameter("first")),Long.parseLong(request.getParameter("second")));
        this.doGet(request, response);
    }
}
