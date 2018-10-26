package servlets;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "HelloServlet",
        description = "Hello World",
        displayName = "Hello",
        urlPatterns = "/hello"
)
public class HelloServlet extends HttpServlet {

    @Resource(lookup = "java:jboss/exported/Test")
    String value;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getOutputStream().println(value);
    }
}
