import java.awt.*;
import java.awt.image.BufferedImage;

public class InvariantImage {

    private BufferedImage image;
    private int width, height;

    public InvariantImage(BufferedImage image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
    }

    public BufferedImage getInvariantImage() {
        BufferedImage bmOut = new BufferedImage(width, height, image.getType());
        double A, R, G, B;
        int pixel;

        float min = 1.0f, max = 0.0f ;

        float [][] arr = new float[width][height];

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixel = image.getRGB(x, y);
                Color color = new Color(pixel);
                R = ((double) color.getRed()) / 255;
                G = ((double) color.getGreen()) / 255;
                B = ((double) color.getBlue()) / 255;

                double pm = Math.pow(R * G * B, (1.0/3.0));


//                float xr = (float) Math.abs(Math.log(R / pm));
//                float xg = (float) Math.abs(Math.log(G / pm));
//                float xb = (float) Math.abs(Math.log(B / pm));

                float ch_r = (float) Math.log(R / pm);
                float ch_b = (float) Math.log(B / pm);

                float rad = (float) (Math.PI * (65.4889 - 1) / 180);


                float grayImg = (float) (ch_r * Math.cos(rad) + ch_b * Math.sin(rad));
                if (grayImg < min) {
                    min = grayImg;
                }
                if (grayImg > max) {
                    max = grayImg;
                }

                arr[x][y] = grayImg;
            }

        }
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                float color = (arr[x][y] - min) / (max-min);
                Color color1 = new Color(color, color, color);
                bmOut.setRGB(x, y, color1.getRGB());

            }
        }
        return bmOut;
    }
}
