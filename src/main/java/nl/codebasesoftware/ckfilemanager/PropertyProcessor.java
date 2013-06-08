package nl.codebasesoftware.ckfilemanager;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * User: rvanloen
 * Date: 6-6-13
 * Time: 12:13
 */
public class PropertyProcessor {

    private static final Logger LOG = Logger.getLogger("propertyprocessor");
    private static PropertyProcessor instance;
    private CkProperties properties = new CkProperties();

    private PropertyProcessor(){
        properties = processProperties();
    }

    public static PropertyProcessor getInstance(){
        if(instance == null){
            instance = new PropertyProcessor();
        }
        return instance;
    }

    public CkProperties getProperties() {
        return properties;
    }

    private CkProperties processProperties() {

        Properties props = new Properties();

        try {
            props.load(getClass().getResourceAsStream("/ckmanager.example.properties"));
        } catch (IOException e) {
            LOG.severe("An error occurred while trying to read the config file from the class path. Is there a config file called 'ckmanager.example.properties' on your classpath?");
            e.printStackTrace();
        }

        String uploadDir = props.getProperty("image.baseuploaddir");
        String maxHeight = props.getProperty("image.maxheight");
        String maxWidth = props.getProperty("image.maxwidth");
        String fileNamingStrategyClass = props.getProperty("image.filenamingstrategyclass");
        String uploadFieldName = props.getProperty("image.uploadfieldname");
        String uploadDirWebPath = props.getProperty("image.baseuploaddir.webpath");

        try {
            File dir = checkUploadDirProperty(uploadDir);
            int maxWidthInt = checkDimensionProperty(maxWidth, "image.maxwidth");
            int maxHeightInt = checkDimensionProperty(maxHeight, "image.maxheight");
            FilePathStrategy strategy = checkFileNamingStrategy(fileNamingStrategyClass);
            checkUploadFieldName(uploadFieldName);
            checkUploadDirWebPath(uploadDirWebPath);

            properties.setUploadBaseDir(dir);
            properties.setUploadDirWebPath(uploadDirWebPath);
            properties.setMaxHeight(maxHeightInt);
            properties.setMaxWidth(maxWidthInt);
            properties.setStrategy(strategy);
            properties.setUploadFieldName(uploadFieldName);


        } catch (CkFileManagerPropertyException e) {
            e.printStackTrace();
        }

        return properties;
    }

    private void checkUploadDirWebPath(String uploadDirWebPath) throws CkFileManagerPropertyException {
        if(uploadDirWebPath == null || uploadDirWebPath.trim().length() == 0){
            throw new CkFileManagerPropertyException("The property 'image.baseuploaddir.webpath' is not found or doesn't have a value. Please add it to ckmanager.example.properties");
        }

        if(!uploadDirWebPath.startsWith("/") || !uploadDirWebPath.endsWith("/")){
            throw new CkFileManagerPropertyException("The property 'image.baseuploaddir.webpath' must have a leading and trailing slash. PLease fix this in ckmanager.example.properties");
        }
    }

    private void checkUploadFieldName(String uploadFieldName) throws CkFileManagerPropertyException {
        if(uploadFieldName == null || uploadFieldName.trim().length() == 0){
            throw new CkFileManagerPropertyException("The property 'image.uploadfieldname' is not found or doesn't have a value. Please add it to ckmanager.example.properties");
        }
    }

    private File checkUploadDirProperty(String uploadDir) throws CkFileManagerPropertyException {
        if (uploadDir == null || uploadDir.length() == 0) {
            throw new CkFileManagerPropertyException("The property 'articleimage.baseuploaddir' is not found or doesn't have a value. Please add it to ckmanager.example.properties");
        }

        File dir = new File(uploadDir);
        if (!dir.isDirectory() || !dir.canWrite()) {
            throw new CkFileManagerPropertyException(String.format("The directory %s doesn't exist, is not a directory or the directory is not writable. Please make sure the directory exists and is writable", dir));
        }

        return dir;
    }

    private int checkDimensionProperty(String dimensionProperty, String name) throws CkFileManagerPropertyException {

        int dimension = 0;

        if (dimensionProperty != null) {
            try {
                dimension = Integer.parseInt(dimensionProperty);
            } catch (NumberFormatException e) {
                throw new CkFileManagerPropertyException(String.format("The property %s is invalid. Please use an integer value", name));
            }

            if (dimension <= 0) {
                throw new CkFileManagerPropertyException(String.format("The property %s is invalid. Please use a positive integer value", name));
            }
        }

        return dimension;
    }

    private FilePathStrategy checkFileNamingStrategy(String strategyClass) throws CkFileManagerPropertyException {
        if(strategyClass == null){
            return null;
        }

        FilePathStrategy strategyInstance = null;

        try {
            Class<?> strategy = Class.forName(strategyClass);

            boolean b = FilePathStrategy.class.isAssignableFrom(strategy);

            if(!b){
                throw new CkFileManagerPropertyException(String.format("Class %s must implement interface FilePathStrategy", strategyClass));
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
