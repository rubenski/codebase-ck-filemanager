package nl.codebasesoftware.ckfilemanager;

import org.apache.commons.fileupload.FileItem;

/**
 * User: rvanloen
 * Date: 6-6-13
 * Time: 0:18
 */
public interface FilePathStrategy {

    FilePaths createPaths(FileItem item, CkProperties properties);

}
