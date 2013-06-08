package nl.codebasesoftware.ckfilemanager;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * User: rvanloen
 * Date: 5-6-13
 * Time: 14:42
 */
public class CkUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String funcNum = req.getParameter("CKEditorFuncNum");

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        PropertyProcessor instance = PropertyProcessor.getInstance();
        CkProperties properties = instance.getProperties();
        FileItem fileItem = fetchFileFromRequest(req, properties.getUploadFieldName());


        BufferedImage image = processImage(properties, fileItem.get());

        FilePathStrategy strategy = properties.getStrategy();
        FilePaths paths = strategy.createPaths(fileItem, properties);

        ImageUtil.saveImage(image, paths.getAbsoluteServerPath());

        writer.write(String.format("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(%s, '%s', '%s');</script>",
                funcNum, paths.getRelativeUrl(), "File upload failed"));


    }

    private BufferedImage processImage(CkProperties properties, byte[] imageByteArray) {
        Dimensions currentDimensions = ImageUtil.getCurrentDimensions(imageByteArray);
        BufferedImage image;
        if (currentDimensions.width > properties.getMaxWidth() || currentDimensions.height > properties.getMaxHeight()) {
            image = ImageUtil.scale(imageByteArray, properties.getMaxWidth(), properties.getMaxHeight());
        } else {
            image = ImageUtil.getBufferedImage(imageByteArray);
        }
        return image;
    }

    private FileItem fetchFileFromRequest(HttpServletRequest request, String imageFieldName) {

        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String fieldname = item.getFieldName();

                    if (fieldname.equals(imageFieldName)) {
                        return item;
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        try {
            throw new CkFileManagerPropertyException(String.format("Field name %s was not found in the request. Are you sure this is the correct image field name?", imageFieldName));
        } catch (CkFileManagerPropertyException e) {
            e.printStackTrace();
        }

        return null;
    }
}
