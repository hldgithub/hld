package com.hld.pics.services;

import static com.hld.pics.constants.Orientation.LANDSCAPE;
import static com.hld.pics.constants.Orientation.PORTRAIT;

import com.hld.pics.constants.ImageSize;
import com.hld.pics.constants.Orientation;
import com.hld.pics.constants.Side;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javaxt.io.Image;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

public class PicsSizing {

    public static final int THREE_POINT_FIVE_INCH_PIXELS = 1050;
    public static final int FIVE_INCH_PIXELS = 1500;
    public static final int FOUR_INCH_PIXELS = 1200;
    public static final int SIX_INCH_PIXELS = 1800;

    public static BufferedImage getScalrImage(File source, ImageSize imageSize) throws Exception {
        BufferedImage resizeMe = ImageIO.read(source);

        int width = determineProperDimensions(resizeMe, imageSize, Side.WIDTH);
        int height = determineProperDimensions(resizeMe, imageSize, Side.HEIGHT);

        Dimension newMaxSize = new Dimension(width, height);
        return Scalr.resize(resizeMe, Method.QUALITY, newMaxSize.width, newMaxSize.height);
    }

    public static void createBorderImage(BufferedImage original, File newFile, ImageSize imageSize)
            throws Exception {
        int borderedImageWidth = determineProperDimensions(original, imageSize, Side.WIDTH);
        int borderedImageHeight = determineProperDimensions(original, imageSize, Side.HEIGHT);

        Image image = new Image(original);
        int x = (borderedImageWidth - image.getWidth())/2;
        int y = (borderedImageHeight - image.getHeight())/2;

        BufferedImage img = new BufferedImage(borderedImageWidth, borderedImageHeight,
                BufferedImage.TYPE_3BYTE_BGR);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, borderedImageWidth, borderedImageHeight);
        g.drawImage(original, null, x, y);
        ImageIO.write(img, "jpg", newFile);
    }

    public static Orientation determineOrientation(BufferedImage filename) {
        Image image = new Image(filename);
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        return imageWidth <= imageHeight ? LANDSCAPE : PORTRAIT;
    }

    public static int determineProperDimensions(BufferedImage source, ImageSize imageSize, Side side) {
        Orientation imageOrientation = determineOrientation(source);
        if (side.equals(Side.WIDTH)) {
            return imageSize.equals(ImageSize.FOUR_BY_SIX)
                    ? (LANDSCAPE.equals(imageOrientation) ? FOUR_INCH_PIXELS : SIX_INCH_PIXELS)
                    : (LANDSCAPE.equals(imageOrientation) ? THREE_POINT_FIVE_INCH_PIXELS : FIVE_INCH_PIXELS);
        } else if (side.equals(Side.HEIGHT)) {
            return imageSize.equals(ImageSize.FOUR_BY_SIX)
                    ? (PORTRAIT.equals(imageOrientation) ? FOUR_INCH_PIXELS : SIX_INCH_PIXELS)
                    : (PORTRAIT.equals(imageOrientation) ? THREE_POINT_FIVE_INCH_PIXELS : FIVE_INCH_PIXELS);
        } else {
            return 0;
        }
    }
}
