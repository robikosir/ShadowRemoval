import java.awt.*;
import java.awt.image.BufferedImage;

public class Gradient {
    private final int width, height;
    private Color[][] pixelArray;
    private GradColor[][] gradArray;
    private BufferedImage image;
    public Gradient(BufferedImage image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        pixelArray = new Color[width][height];
        gradArray = new GradColor[width][height];

        constructImage();
        constructGradientArray();
    }

    private void constructImage() {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                Color color = new Color(image.getRGB(x,y));
                pixelArray[x][y] = color;
            }
        }
    }

    public void constructGradientArray() {
        float R, G, B;
        int gradSumXRed, gradSumYRed;
        int gradSumXBlue, gradSumYBlue;
        int gradSumXGreen, gradSumYGreen;
        int finalRed = 0, finalGreen = 0, finalBlue = 0;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                gradSumXRed = getChangeX(pixelArray, x, y, 'r');
                gradSumXGreen = getChangeX(pixelArray, x, y, 'g');
                gradSumXBlue = getChangeX(pixelArray, x, y, 'b');

                gradArray[x][y] = new GradColor(gradSumXRed, gradSumXGreen, gradSumXBlue);
//                finalRed = pixelArray[x][y].getRed();
//                finalGreen = pixelArray[x][y].getGreen();
//                finalBlue = pixelArray[x][y].getBlue();
//
//                if (Math.abs(gradSumXRed) > 45) {
//                    finalRed = 0;
//                }
//                if (Math.abs(gradSumXGreen) > 45) {
//                    finalGreen = 0;
//                }
//
//                if (Math.abs(gradSumXBlue) > 45) {
//                    finalBlue = 0;
//                }
//
//                this.image.setRGB(x,y, new Color(finalRed, finalGreen, finalBlue).getRGB());
            }
        }
    }

    private int getChangeX(Color[][] pixelArray, int x, int y, char type) {
        if (y == 0) {
            switch (type) {
                case 'r':
                    return pixelArray[x][y + 1].getRed() - pixelArray[x][y].getRed();
                case 'b':
                    return pixelArray[x][y + 1].getBlue() - pixelArray[x][y].getBlue();
                case 'g':
                    return pixelArray[x][y + 1].getGreen() - pixelArray[x][y].getGreen();
                default:
                    return 0;
            }
        } else if (y == height - 1) {
            switch (type) {
                case 'r':
                    return pixelArray[x][y].getRed() - pixelArray[x][y - 1].getRed();
                case 'b':
                    return pixelArray[x][y].getBlue() - pixelArray[x][y - 1].getBlue();
                case 'g':
                    return pixelArray[x][y].getGreen() - pixelArray[x][y - 1].getGreen();
                default:
                    return 0;
            }
        } else {
            switch (type) {
                case 'r':
                    return pixelArray[x][y + 1].getRed() - pixelArray[x][y - 1].getRed();
                case 'b':
                    return pixelArray[x][y + 1].getBlue() - pixelArray[x][y - 1].getBlue();
                case 'g':
                    return pixelArray[x][y + 1].getGreen() - pixelArray[x][y - 1].getGreen();
                default:
                    return 0;
            }
        }
    }

    private int getChangeY(Color[][] pixelArray, int x, int y, char type) {
        if (x == 0) {
            switch (type) {
                case 'r':
                    return pixelArray[x + 1][y].getRed() - pixelArray[x][y].getRed();
                case 'b':
                    return pixelArray[x + 1][y].getBlue() - pixelArray[x][y].getBlue();
                case 'g':
                    return pixelArray[x + 1][y].getGreen() - pixelArray[x][y].getGreen();
                default:
                    return 0;
            }
        } else if (x == height - 1) {
            switch (type) {
                case 'r':
                    return pixelArray[x][y].getRed() - pixelArray[x - 1][y].getRed();
                case 'b':
                    return pixelArray[x][y].getBlue() - pixelArray[x - 1][y].getBlue();
                case 'g':
                    return pixelArray[x][y].getGreen() - pixelArray[x - 1][y].getGreen();
                default:
                    return 0;
            }
        } else {
            switch (type) {
                case 'r':
                    return pixelArray[x + 1][y].getRed() - pixelArray[x - 1][y].getRed();
                case 'b':
                    return pixelArray[x + 1][y].getBlue() - pixelArray[x - 1][y].getBlue();
                case 'g':
                    return pixelArray[x + 1][y].getGreen() - pixelArray[x - 1][y].getGreen();
                default:
                    return 0;
            }
        }
    }

    public GradColor[][] getGradArray() {
        return gradArray;
    }
}
