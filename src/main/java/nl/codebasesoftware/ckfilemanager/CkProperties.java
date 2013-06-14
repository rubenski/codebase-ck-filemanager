package nl.codebasesoftware.ckfilemanager;

import java.io.File;

/**
 * User: rvanloen
 * Date: 6-6-13
 * Time: 16:21
 */
public class CkProperties {

    private File uploadServerDir;
    private File thumbsServerDir;
    private int maxHeight;
    private int maxWidth;
    private FilePathStrategy strategy;
    private String uploadFieldName;
    private String uploadPath;
    private String fileServePath;
    private String fileBrowserPath;
    private String noImageError;
    private String langInsert;
    private String langRemove;
    private String contextPath;


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

    public File getUploadServerDir() {
        return uploadServerDir;
    }

    public void setUploadServerDir(File uploadServerDir) {
        this.uploadServerDir = uploadServerDir;
    }

    public String getUploadFieldName() {
        return uploadFieldName;
    }

    public void setUploadFieldName(String uploadFieldName) {
        this.uploadFieldName = uploadFieldName;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getFileServePath() {
        return fileServePath;
    }

    public void setFileServePath(String fileServePath) {
        this.fileServePath = fileServePath;
    }

    public String getFileBrowserPath() {
        return fileBrowserPath;
    }

    public void setFileBrowserPath(String fileBrowserPath) {
        this.fileBrowserPath = fileBrowserPath;
    }

    public String getNoImageError() {
        return noImageError;
    }

    public void setNoImageError(String noImageError) {
        this.noImageError = noImageError;
    }

    public String getLangInsert() {
        return langInsert;
    }

    public void setLangInsert(String langInsert) {
        this.langInsert = langInsert;
    }

    public String getLangRemove() {
        return langRemove;
    }

    public void setLangRemove(String langRemove) {
        this.langRemove = langRemove;
    }

    public File getThumbsServerDir() {
        return thumbsServerDir;
    }

    public void setThumbsServerDir(File thumbsServerDir) {
        this.thumbsServerDir = thumbsServerDir;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}
