package nl.codebasesoftware.ckfilemanager;

/**
 * User: rvanloen
 * Date: 6-6-13
 * Time: 0:18
 */
public interface FilePathStrategy {

    FilePaths createPaths(String fileName, CkProperties properties);

}
