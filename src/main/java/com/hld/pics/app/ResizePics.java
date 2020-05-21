package com.hld.pics.app;

import static com.hld.pics.app.ResizePics.Orientation.LANDSCAPE;
import static com.hld.pics.app.ResizePics.Orientation.PORTRAIT;

import com.hld.pics.services.PicsSizing;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javaxt.io.Image;

public class ResizePics {

    public static final String ROOT_FOLDER = "/home/hld29/Downloads/2015_Maryland";

    public static void main(String[] args) throws Exception {
        String filename = ROOT_FOLDER + "/ChurchOfRedeemer.jpg";
        String newFilename = ROOT_FOLDER + "/ChurchOfRedeemerv2.jpg";

        Image image = new Image(filename);
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        System.out.println("Original=" + image.getWidth() + "x" + image.getHeight());

        Orientation imageOrientation = imageWidth < imageHeight ? LANDSCAPE : PORTRAIT;

        File inFile = new File(filename);
        File resizedFile = new File(newFilename);

        try {
            BufferedImage resizeImageJpg = PicsSizing.getScalrImage(inFile, imageOrientation);

            ImageIO.write(resizeImageJpg, "jpg", resizedFile);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        image = new Image(newFilename);
        System.out.println("New=" + image.getWidth() + "x" + image.getHeight());

        File outputFile = new File("/home/hld29/Downloads/2015_Maryland/output.jpg");
        PicsSizing.createBorderImage(resizedFile, outputFile, imageOrientation);
    }

    public enum Orientation {
        LANDSCAPE,
        PORTRAIT
    }
}
