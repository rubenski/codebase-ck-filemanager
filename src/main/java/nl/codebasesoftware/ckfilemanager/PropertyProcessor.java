package nl.codebasesoftware.ckfilemanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * User: rvanloen
 * Date: 6-6-13
 * Time: 12:13
 */
public class PropertyProcessor {

    private static final String UPLOAD_DIR = "cbck.uploaddir";
    private static final String IMG_MAX_HEIGHT = "image.maxheight";
    private static final String IMG_MAX_WIDTH = "image.maxwidth";
    private static final String FILENAMING_STRATEGY_CLASS = "cbck.filenamingstrategyclass";
    private static final String UPLOADFORM_FIELDNAME = "uploadform.uploadfieldname";
    private static final String UPLOAD_PATH = "cbck.uploadpath";
    private static final String FILESERVE_PATH= "cbck.fileservepath";
    private static final String FILEBROWSER_PATH = "cbck.filebrowserpath";
    private static final String LANG_NO_IMAGE = "lang.isnoimage.error";
    private static final String LANG_FILEBROWSER_INSERT = "lang.filebrowser.insert";
    private static final String LANG_FILEBROWSER_REMOVE = "lang.filebrowser.remove";


    private static final Logger LOG = Logger.getLogger("propertyprocessor");
    private static PropertyProcessor instance;
    private CkProperties properties = new CkProperties();

    private PropertyProcessor(String contextPath) {
        properties = processProperties(contextPath);
    }

    public static PropertyProcessor getInstance(String contextPath) {
        if (instance == null) {
            instance = new PropertyProcessor(contextPath);
        }
        return instance;
    }

    public CkProperties getProperties() {
        return properties;
    }

    private CkProperties processProperties(String contextPath) {

        Properties props = new Properties();

        try {
            Thread thread = Thread.currentThread();
            InputStream stream1 = thread.getContextClassLoader().getResourceAsStream("/ckmanager.properties");
            props.load(stream1);
        } catch (IOException e) {
            LOG.severe("An error occurred while trying to read the config file from the class path. Is there a config file called 'ckmanager.example.properties' on your classpath?");
            e.printStackTrace();
        }



        String uploadDir = props.getProperty(UPLOAD_DIR);
        String maxHeight = props.getProperty(IMG_MAX_HEIGHT);
        String maxWidth = props.getProperty(IMG_MAX_HEIGHT);
        String fileNamingStrategyClass = props.getProperty(FILENAMING_STRATEGY_CLASS);
        String uploadFieldName = props.getProperty(UPLOADFORM_FIELDNAME);
        String uploadPath = props.getProperty(UPLOAD_PATH);
        String fileServePath = props.getProperty(FILESERVE_PATH);
        String fileBrowserPath = props.getProperty(FILEBROWSER_PATH);
        String noImageError = props.getProperty(LANG_NO_IMAGE);
        String langInsert = props.getProperty(LANG_FILEBROWSER_INSERT);
        String langRemove = props.getProperty(LANG_FILEBROWSER_REMOVE);

        try {
            File uploadDirectory = createDir(uploadDir);
            File thumbsDirectory = createDir(String.format("%s/thumbs", uploadDir));
            int maxWidthInt = checkDimensionProperty(maxWidth, IMG_MAX_WIDTH);
            int maxHeightInt = checkDimensionProperty(maxHeight, IMG_MAX_HEIGHT);
            FilePathStrategy strategy = checkFileNamingStrategy(fileNamingStrategyClass);
            checkProperty(uploadFieldName, UPLOADFORM_FIELDNAME);
            checkProperty(uploadPath, UPLOAD_PATH);
            checkProperty(fileServePath, FILESERVE_PATH);
            checkProperty(fileBrowserPath, FILEBROWSER_PATH);
            checkProperty(noImageError, LANG_NO_IMAGE);
            checkProperty(langInsert, LANG_FILEBROWSER_INSERT);
            checkProperty(langInsert, LANG_FILEBROWSER_REMOVE);

            properties.setUploadServerDir(uploadDirectory);
            properties.setContextPath(contextPath);
            properties.setMaxHeight(maxHeightInt);
            properties.setMaxWidth(maxWidthInt);
            properties.setStrategy(strategy);
            properties.setUploadFieldName(uploadFieldName);
            properties.setFileBrowserPath(fileBrowserPath);
            properties.setFileServePath(fileServePath);
            properties.setUploadPath(uploadPath);
            properties.setNoImageError(noImageError);
            properties.setLangInsert(langInsert);
            properties.setLangRemove(langRemove);
            properties.setThumbsServerDir(thumbsDirectory);


        } catch (CkFileManagerPropertyException e) {
            e.printStackTrace();
        }

        return properties;
    }

    private void checkProperty(String path, String propertyName) throws CkFileManagerPropertyException {
        if (path == null || path.trim().length() == 0) {
            throw new CkFileManagerPropertyException(String.format("The property '%s' doesn't exist or has no value", propertyName));
        }
    }

    private File createDir(String dirPath) throws CkFileManagerPropertyException {

        File directory = new File(dirPath);

        if(!directory.exists()){
            boolean created;
            try{
                created = directory.mkdir();
            }catch(SecurityException e){
                throw new CkFileManagerPropertyException(String.format("Directory '%s' doesn't exist and can't be created", directory.getPath()));
            }

            if(!created){
                throw new CkFileManagerPropertyException(String.format("Directory '%s' doesn't exist and can't be created", directory.getPath()));
            }
        }

        if (!directory.canWrite()) {
            throw new CkFileManagerPropertyException(String.format("Please review ckmanager.properties. " +
                    "The directory %s doesn't exist, is not a directory or the directory is not writable. " +
                    "Please make sure the directory exists and is writable", dirPath));
        }

        return directory;
    }

    private int checkDimensionProperty(String dimensionProperty, String name) throws CkFileManagerPropertyException {

        int dimension = 0;

        if (dimensionProperty != null) {
            try {
                dimension = Integer.parseInt(dimensionProperty);
            } catch (NumberFormatException e) {
                throw new CkFileManagerPropertyException(String.format("The property %s is invalid. Please use an " +
                        "integer value", name));
            }

            if (dimension <= 0) {
                throw new CkFileManagerPropertyException(String.format("The property %s is invalid. Please use a " +
                        "positive integer value", name));
            }
        }

        return dimension;
    }

    private FilePathStrategy checkFileNamingStrategy(String strategyClass) throws CkFileManagerPropertyException {
        if (strategyClass == null) {
            return null;
        }

        FilePathStrategy strategyInstance = null;

        try {
            Class<?> strategy = Class.forName(strategyClass);

            boolean b = FilePathStrategy.class.isAssignableFrom(strategy);

            if (!b) {
                throw new CkFileManagerPropertyException(String.format("Error in property cbck.filenamingstrategyclass. " +
                        "Class %s must implement interface FilePathStrategy", strategyClass));
            }

            strategyInstance = (FilePathStrategy) strategy.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new CkFileManagerPropertyException(String.format("Class %s was not found", strategyClass));
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new CkFileManagerPropertyException(String.format("Class %s was found, but cannot ne instantiated", strategyClass));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return strategyInstance;
    }


}
