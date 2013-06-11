package nl.codebasesoftware.ckfilemanager;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: rvanloen
 * Date: 6-6-13
 * Time: 15:39
 */
public class ImageUtil {

    public static Dimensions getCurrentDimensions(byte[] imageByteArray) {
        BufferedImage bufferedImage = getBufferedImage(imageByteArray);
        return new Dimensions(bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public static BufferedImage getBufferedImage(byte[] imageByteArray) {
        BufferedImage bufferedImage = null;
        try {
            InputStream in = new ByteArrayInputStream(imageByteArray);
            bufferedImage = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    public static BufferedImage scale(byte[] imageByteArray, int maxWidth, int maxHeight) {
        BufferedImage bufferedImage = getBufferedImage(imageByteArray);
        return Scalr.resize(bufferedImage, Scalr.Method.BALANCED, Scalr.Mode.AUTOMATIC, maxWidth, maxHeight, Scalr.OP_ANTIALIAS);
    }

    public static void saveImage(BufferedImage image, String absoluteServerPath){
        File f = new File(absoluteServerPath);
        try {
            ImageIO.write(image, "png", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
