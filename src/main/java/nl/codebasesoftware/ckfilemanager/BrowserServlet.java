package nl.codebasesoftware.ckfilemanager;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: rvanloen
 * Date: 9-6-13
 * Time: 22:35
 */
public class BrowserServlet extends HttpServlet {

    Logger LOG = Logger.getLogger("BrowserServlet");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String funcNum = req.getParameter("CKEditorFuncNum");
        CkProperties properties = PropertyProcessor.getInstance(getServletContext().getContextPath()).getProperties();
        File uploadBaseDir = properties.getUploadServerDir();
        String[] list = uploadBaseDir.list();

        req.setAttribute("properties", properties);

        FilePathStrategy strategy =  properties.getStrategy();
        Map<String, FilePaths> paths = new HashMap<String, FilePaths>();

        for (String s : list) {
            File f = new File(String.format("%s/%s", uploadBaseDir, s));
            if(f.isFile()){
                try{
                    ImageIO.read(f); // test if it is an image
                    paths.put(s, strategy.createPaths(s, properties));
                }catch(IIOException e){
                    LOG.warning(String.format("Can't create image from '%s'", s));
                }
            }
        }

        req.setAttribute("filePathsList", paths);
        req.setAttribute("funcNum", funcNum);

        String jsp = "/jsp/browser.jsp";

        getServletContext().getRequestDispatcher(jsp).forward(req, resp);
    }

}
