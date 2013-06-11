package nl.codebasesoftware.ckfilemanager;

import java.io.File;

/**
 * User: rvanloen
 * Date: 6-6-13
 * Time: 16:21
 */
public class CkProperties {

    private File uploadDir;
    private int maxHeight;
    private int maxWidth;
    private FilePathStrategy strategy;
    private String uploadFieldName;
    private String webPath;


    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public FilePathStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(FilePathStrategy strategy) {
        this.strategy = strategy;
    }

    public File getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(File uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getUploadFieldName() {
        return uploadFieldName;
    }

    public void setUploadFieldName(String uploadFieldName) {
        this.uploadFieldName = uploadFieldName;
    }

    public String getWebPath() {
        return webPath;
    }

    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }
}
