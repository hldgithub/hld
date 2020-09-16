package com.hld.pics.utils;

import static com.hld.pics.constants.Orientation.LANDSCAPE;
import static com.hld.pics.constants.Orientation.PORTRAIT;

import com.hld.pics.constants.ImageSize;
import com.hld.pics.constants.Orientation;
import com.hld.pics.constants.PicConstants;
import com.hld.pics.constants.Side;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javaxt.io.Image;

public class PicUtils {

    public static int determineProperDimensions(File source, ImageSize imageSize, Side side) throws Exception {
        BufferedImage resizeMe = ImageIO.read(source);
        return determineProperDimensions(resizeMe, imageSize, side);
    }

    public static int determineProperDimensions(BufferedImage source, ImageSize imageSize, Side side) {
        Orientation imageOrientation = determineOrientation(source);
        if (side.equals(Side.WIDTH)) {
            return imageSize.equals(ImageSize.FOUR_BY_SIX)
                    ? (LANDSCAPE.equals(imageOrientation) ? PicConstants.FOUR_INCH_PIXELS : PicConstants.SIX_INCH_PIXELS)
                    : (LANDSCAPE.equals(imageOrientation) ? PicConstants.THREE_POINT_FIVE_INCH_PIXELS : PicConstants.FIVE_INCH_PIXELS);
        } else if (side.equals(Side.HEIGHT)) {
            return imageSize.equals(ImageSize.FOUR_BY_SIX)
                    ? (PORTRAIT.equals(imageOrientation) ? PicConstants.FOUR_INCH_PIXELS : PicConstants.SIX_INCH_PIXELS)
                    : (PORTRAIT.equals(imageOrientation) ? PicConstants.THREE_POINT_FIVE_INCH_PIXELS : PicConstants.FIVE_INCH_PIXELS);
        } else {
            return 0;
        }
    }

    public static void writeImage(BufferedImage image, File newFile) throws Exception {
        JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
        jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpegParams.setCompressionQuality(1f);

        ImageOutputStream outputStream = new FileImageOutputStream(newFile);
        IIOImage outputImage = new IIOImage(image, null, null);

        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        jpgWriter.setOutput(outputStream);
        jpgWriter.write(null, outputImage, jpegParams);
        jpgWriter.dispose();
    }

    private static Orientation determineOrientation(BufferedImage filename) {
        Image image = new Image(filename);
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        return imageWidth <= imageHeight ? LANDSCAPE : PORTRAIT;
    }
}
