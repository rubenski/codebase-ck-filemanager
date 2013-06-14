package nl.codebasesoftware.ckfilemanager;

/**
 * User: rvanloen
 * Date: 6-6-13
 * Time: 21:34
 */
public class FilePaths {

    private String displayImageServerPath;
    private String displayImageRelativeUrl;
    private String thumbnailServerpath;
    private String thumbnailImageRelativeUrl;

    public FilePaths(String displayImageServerPath, String displayImageRelativeUrl, String thumbnailServerpath,
                     String thumbnailImageRelativeUrl) {
        this.displayImageServerPath = displayImageServerPath;
        this.displayImageRelativeUrl = displayImageRelativeUrl;
        this.thumbnailServerpath = thumbnailServerpath;
        this.thumbnailImageRelativeUrl = thumbnailImageRelativeUrl;
    }

    public String getDisplayImageServerPath() {
        return displayImageServerPath;
    }

    public void setDisplayImageServerPath(String displayImageServerPath) {
        this.displayImageServerPath = displayImageServerPath;
    }

    public String getDisplayImageRelativeUrl() {
        return displayImageRelativeUrl;
    }

    public void setDisplayImageRelativeUrl(String displayImageRelativeUrl) {
        this.displayImageRelativeUrl = displayImageRelativeUrl;
    }

    public String getThumbnailServerpath() {
        return thumbnailServerpath;
    }

    public void setThumbnailServerpath(String thumbnailServerpath) {
        this.thumbnailServerpath = thumbnailServerpath;
    }

    public String getThumbnailImageRelativeUrl() {
        return thumbnailImageRelativeUrl;
    }

    public void setThumbnailImageRelativeUrl(String thumbnailImageRelativeUrl) {
        this.thumbnailImageRelativeUrl = thumbnailImageRelativeUrl;
    }
}
