package com.hld.pics.apps;

import static com.hld.pics.constants.ImageSize.FOUR_BY_SIX;
import static com.hld.pics.constants.ImageSize.THREE_AND_A_HALF_BY_FIVE;
import static com.hld.pics.services.PicsSizing.createBorderImage;
import static com.hld.pics.services.PicsSizing.getScalrImage;
import static java.lang.System.exit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResizePics {

    public static final String ROOT_FOLDER = "/home/hld29/Downloads/";

    public static void main(String[] args) throws Exception {
        String inFolder = ROOT_FOLDER + "petra";
        String outFolder = inFolder + "_4x6";

        //get list of files
        File folder = new File(inFolder);
        String[] fileList = folder.list();
        if (fileList == null || fileList.length == 0) {
            System.out.println(inFolder + " is Empty!");
            exit(1);
        }

        // go through each file
        for (String fileToChange : fileList) {
            System.out.println("Working on '" + fileToChange + "'");

            String filename = inFolder + "/" + fileToChange;
            String newFilename = outFolder + "/" + fileToChange;

            File inFile = new File(filename);
            File resizedFile = new File(newFilename);

            try {
                // resize image to 3.5 x 5
                BufferedImage resizeImageJpg = getScalrImage(inFile, THREE_AND_A_HALF_BY_FIVE);

                // write image to 4x6 print
                createBorderImage(resizeImageJpg, resizedFile, FOUR_BY_SIX);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
