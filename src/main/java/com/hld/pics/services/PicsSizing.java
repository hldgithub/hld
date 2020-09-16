package com.hld.pics.services;

import static com.hld.pics.utils.PicUtils.determineProperDimensions;
import static com.hld.pics.utils.PicUtils.writeImage;

import com.hld.pics.constants.ImageSize;
import com.hld.pics.constants.Side;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

public class PicsSizing {

    public static BufferedImage getScalrImage(File source, ImageSize imageSize) throws Exception {
        BufferedImage resizeMe = ImageIO.read(source);

        int width = determineProperDimensions(resizeMe, imageSize, Side.WIDTH);
        int height = determineProperDimensions(resizeMe, imageSize, Side.HEIGHT);

        return Scalr.resize(resizeMe, Method.ULTRA_QUALITY, width, height);
    }

    public static void createBorderImage(BufferedImage original, File newFile, ImageSize imageSize)
            throws Exception {
        int borderedImageWidth = determineProperDimensions(original, imageSize, Side.WIDTH);
        int borderedImageHeight = determineProperDimensions(original, imageSize, Side.HEIGHT);

        BufferedImage img = new BufferedImage(borderedImageWidth, borderedImageHeight,
                BufferedImage.TYPE_INT_RGB);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, borderedImageWidth, borderedImageHeight);
        g.drawImage(original, null, 0, 0);

        writeImage(img, newFile);
    }
}
