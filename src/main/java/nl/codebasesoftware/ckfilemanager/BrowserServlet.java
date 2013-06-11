package nl.codebasesoftware.ckfilemanager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: rvanloen
 * Date: 9-6-13
 * Time: 22:35
 */
public class BrowserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CkProperties properties = PropertyProcessor.getInstance().getProperties();
        File uploadBaseDir = properties.getUploadDir();
        String[] list = uploadBaseDir.list();
        req.setAttribute("fileList", list);

        String jsp = "webapp/jsp/browser.jsp";
        ServletContext sc = getServletContext();

        RequestDispatcher rd = sc.getRequestDispatcher(jsp);
        rd.forward(req, resp);
    }
}
