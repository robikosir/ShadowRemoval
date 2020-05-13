import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class ImageProcessing {
    private final int width, height;

    public ImageProcessing(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = image.getWidth();
        height = image.getHeight();

        removeShadow(image);
    }

    private BufferedImage removeShadow(BufferedImage image) {
        BufferedImage bmOut = new BufferedImage(width, height, image.getType());
        InvariantImage invariantImage = new InvariantImage(deepCopy(image));

        Gradient originalPhotoGrad = new Gradient(deepCopy(image));
        Gradient invariantImageGrad = new Gradient(invariantImage.getInvariantImage());

        int finalRed = 0, finalGreen = 0, finalBlue = 0;
        GradVector[][] gradChannel = originalPhotoGrad.getGradArray();
        GradVector[][] gradInvariant = invariantImageGrad.getGradArray();

        //check for channel gradient and invariatnImage gradient
        try {
            ImageIO.write(invariantImage.getInvariantImage(), "jpg", new File("gradient.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }


//    private BufferedImage findChromaticityImage(BufferedImage image) {
//        BufferedImage bmOut = new BufferedImage(width, height, this.image.getType());
//        float A, R, G, B;
//        int pixel;
//        for (int x = 0; x < width; ++x) {
//            for (int y = 0; y < height; ++y) {
//                // get pixel color
//                pixel = image.getRGB(x, y);
//                Color color = new Color(pixel);
//                A = color.getAlpha();
//                R = color.getRed();
//                G = color.getGreen();
//                B = color.getBlue();
//
//                float sumRGB = R+G+B;
//
//                float r = R/sumRGB;
//                float g = G/sumRGB;
//                float b = B/sumRGB;
//                Color newColor = new Color(r,g,b);
//                bmOut.setRGB(x, y, newColor.getRGB());
//
//            }
//        }
//        return bmOut;
//    }
//    private BufferedImage createMask(){
//        BufferedImage src = this.image;
//        // create output bitmap
//        BufferedImage bmOut = new BufferedImage(width, height, src.getType());
//        // color information
//        int A, R, G, B;
//        int pixel;
//        for (int x = 0; x < width; ++x) {
//            for (int y = 0; y < height; ++y) {
//                // get pixel color
//                pixel = src.getRGB(x, y);
//                Color color = new Color(pixel);
//                A = color.getAlpha();
//                R = color.getRed();
//                G = color.getGreen();
//                B = color.getBlue();
//
//
//                int gray = (int) (0.299 * R + 0.587 * G + 0.114 * B);
//                // use 128 as threshold, above -> white, below -> black
//                if (gray > 128) {
//                    gray = 255;
//                }
//                else{
//                    gray = 0;
//                }
//                // set new pixel color to output bitmap
//                Color newColor = new Color(gray,gray,gray,A);
//                bmOut.setRGB(x, y, newColor.getRGB());
//            }
//        }
//        return bmOut;
//    }
}
