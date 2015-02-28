/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package serviceclient;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author G62
 */
public class ImgCodec {
        
    public static String encodeImage(String path) {
        BufferedImage img;
        ByteArrayOutputStream baos = null;
        String encodedImage;
        try {
            img = ImageIO.read(new File(path));    
            baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);    
            baos.flush();
            encodedImage = Base64.encodeBase64String(baos.toByteArray());
            return encodedImage;
        } catch(IOException ioE) {
            System.out.println(ioE.getMessage());
        } finally {
            try {
                baos.close();
            } catch(IOException ioE2) {
                System.out.println(ioE2.getMessage());
            }
        }
        return null;
    }
    
    public static BufferedImage decodeImage(String encoded) {
        try {
            byte[] bytes = Base64.decodeBase64(encoded);
            return ImageIO.read(new ByteArrayInputStream(bytes));
        } catch(IOException ioE) {
            System.out.println(ioE.getMessage());
        }
        return null;
    }
}
