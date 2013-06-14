package nl.codebasesoftware.ckfilemanager;

import org.apache.commons.fileupload.FileItem;

import java.io.File;

/**
 * User: rvanloen
 * Date: 6-6-13
 * Time: 16:53
 */
public class DefaultFilePathStrategy implements FilePathStrategy {

    private String fileName;

    public FilePaths createPaths(String fileName, CkProperties properties) {

        this.fileName = fileName;

        String dirPath = properties.getUploadServerDir().getPath();


        String uniqueFileName = getUniqueName(dirPath, fileName, null);

        String displayImageUrl = getDisplayImageUrl(properties.getContextPath(), properties.getFileServePath(), uniqueFileName);
        String displayImageServerPath = getDisplayImageServerPath(properties.getUploadServerDir(), uniqueFileName);
        String thumbnailImageUrl = getThumnailImageUrl(properties.getContextPath(), properties.getFileServePath(), uniqueFileName);
        String thumbnailImageServerPath = getThumbnailImageServerPath(properties.getThumbsServerDir(), uniqueFileName);


        return new FilePaths(displayImageServerPath, displayImageUrl, thumbnailImageServerPath, thumbnailImageUrl);
    }

    private String getThumbnailImageServerPath(File thumbsDir, String uniqueFileName) {
        return String.format("%s/thumb_%s", thumbsDir, uniqueFileName);
    }

    private String getThumnailImageUrl(String webpath, String fileServePath, String uniqueFileName) {
        return String.format("%s/%s/thumbs/thumb_%s", webpath, fileServePath, uniqueFileName);
    }

    private String getDisplayImageServerPath(File uploadDir, String uniqueFileName) {
        return String.format("%s/%s", uploadDir, uniqueFileName);
    }

    private String getDisplayImageUrl(String webPath, String fileServePath, String uniqueFileName){
        return String.format("%s/%s/%s", webPath, fileServePath, uniqueFileName);
    }

    private String getUniqueName(String dirPath, String name, Integer prefix) {

        File tryFile;
        String uniqueName;
        if (prefix == null) {
            uniqueName =  name;
        } else {
            uniqueName = String.format("%s-%s", prefix, name);
        }

        String path = String.format("%s%s", dirPath, uniqueName);

        tryFile = new File(path);

        if (tryFile.isFile() && tryFile.exists()) {
            return getUniqueName(dirPath, name, prefix == null ? 1 : prefix + 1);
        }

        return uniqueName;
    }

}
