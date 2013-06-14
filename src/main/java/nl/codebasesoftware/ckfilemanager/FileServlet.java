package nl.codebasesoftware.ckfilemanager;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: rvanloen
 * Date: 8-6-13
 * Time: 14:09
 */
public class FileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String requestURI = req.getRequestURI();

        String context = getServletContext().getContextPath();

        CkProperties properties = PropertyProcessor.getInstance(context).getProperties();
        String filename;

        String filePathInUploadDir = "";
        filePathInUploadDir = requestURI.substring(context.length());
        filePathInUploadDir = filePathInUploadDir.substring(filePathInUploadDir.indexOf(properties.getFileServePath()) + properties.getFileServePath().length());

        filename = requestURI.substring(requestURI.lastIndexOf('/') + 1);

        String path = String.format("%s/%s", properties.getUploadServerDir(), filePathInUploadDir);

        File f = new File(path);

        if (!f.isFile()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        BufferedImage image = ImageIO.read(f);

        String type = getFileExtension(filename);

        OutputStream out = resp.getOutputStream();
        ImageIO.write(image, type, out);
        out.close();

    }

    private String getFileExtension(String fileName) {
        if (fileName.endsWith("jpeg")) {
            return "jpg";
        }

        String[] parts = fileName.split("\\.");
        return parts[parts.length - 1];

    }
}
