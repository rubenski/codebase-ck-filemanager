package nl.codebasesoftware.ckfilemanager;

import java.io.File;

/**
 * User: rvanloen
 * Date: 6-6-13
 * Time: 16:21
 */
public class CkProperties {

    private File uploadBaseDir;
    private int maxHeight;
    private int maxWidth;
    private FilePathStrategy strategy;
    private String uploadFieldName;
    private String uploadDirWebPath;


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

    public File getUploadBaseDir() {
        return uploadBaseDir;
    }

    public void setUploadBaseDir(File uploadBaseDir) {
        this.uploadBaseDir = uploadBaseDir;
    }

    public String getUploadFieldName() {
        return uploadFieldName;
    }

    public void setUploadFieldName(String uploadFieldName) {
        this.uploadFieldName = uploadFieldName;
    }

    public String getUploadDirWebPath() {
        return uploadDirWebPath;
    }

    public void setUploadDirWebPath(String uploadDirWebPath) {
        this.uploadDirWebPath = uploadDirWebPath;
    }
}
