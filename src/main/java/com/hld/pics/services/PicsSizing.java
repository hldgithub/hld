package com.hld.pics.services;

import static com.hld.pics.app.ResizePics.Orientation.LANDSCAPE;
import static com.hld.pics.app.ResizePics.Orientation.PORTRAIT;

import com.hld.pics.app.ResizePics.Orientation;
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

    public static BufferedImage getScalrImage(File source, Orientation imageOrientation)
            throws Exception {
        int width = LANDSCAPE.equals(imageOrientation) ? THREE_POINT_FIVE_INCH_PIXELS : FIVE_INCH_PIXELS;
        int height = PORTRAIT.equals(imageOrientation) ? THREE_POINT_FIVE_INCH_PIXELS : FIVE_INCH_PIXELS;

        BufferedImage resizeMe = ImageIO.read(source);
        Dimension newMaxSize = new Dimension(width, height);
        return Scalr.resize(resizeMe, Method.QUALITY, newMaxSize.width, newMaxSize.height);
    }

    public static void createBorderImage(File original, File newFile, Orientation imageOrientation) throws Exception {
        int borderedImageWidth = LANDSCAPE.equals(imageOrientation) ? FOUR_INCH_PIXELS : SIX_INCH_PIXELS;
        int borderedImageHeight = PORTRAIT.equals(imageOrientation) ? FOUR_INCH_PIXELS : SIX_INCH_PIXELS;

        Image image = new Image(original);
        int x = (borderedImageWidth - image.getWidth())/2;
        int y = (borderedImageHeight - image.getHeight())/2;
        BufferedImage source = ImageIO.read(original);

        BufferedImage img = new BufferedImage(borderedImageWidth, borderedImageHeight, BufferedImage.TYPE_3BYTE_BGR);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, borderedImageWidth, borderedImageHeight);
        g.drawImage(source, null, x, y);
        ImageIO.write(img, "jpg", newFile);
    }
}
