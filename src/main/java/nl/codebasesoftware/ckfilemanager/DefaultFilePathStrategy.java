package nl.codebasesoftware.ckfilemanager;

import org.apache.commons.fileupload.FileItem;

import java.io.File;

/**
 * User: rvanloen
 * Date: 6-6-13
 * Time: 16:53
 */
public class DefaultFilePathStrategy implements FilePathStrategy {


    public FilePaths createPaths(FileItem item, CkProperties properties) {

        String name = item.getName();
        String dirPath = properties.getUploadBaseDir().getPath();
        String uniqueFileName = getUniqueName(dirPath, name, null);
        String url = getUrl(properties.getUploadDirWebPath(), uniqueFileName);

        return new FilePaths(String.format("%s/%s", dirPath, uniqueFileName), url);
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

    private String getUrl(String webPath, String uniqueFileName){
        return String.format("%s%s", webPath, uniqueFileName);
    }

}
