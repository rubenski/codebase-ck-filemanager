package nl.codebasesoftware.ckfilemanager;

/**
 * User: rvanloen
 * Date: 6-6-13
 * Time: 21:34
 */
public class FilePaths {

    private String absoluteServerPath;
    private String relativeUrl;

    public FilePaths(String absoluteServerPath, String relativeUrl) {
        this.absoluteServerPath = absoluteServerPath;
        this.relativeUrl = relativeUrl;
    }

    public String getAbsoluteServerPath(){
        return absoluteServerPath;
    }

    public String getRelativeUrl(){
        return relativeUrl;
    }
}
