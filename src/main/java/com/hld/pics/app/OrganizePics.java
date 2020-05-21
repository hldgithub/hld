package com.hld.pics.app;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrganizePics {

    private static final String ROOT_FOLDER = "/home/hld29/";

    public static void main(String[] args) {
		/*
		// prompt for folder
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter folder name: ");
		String folderName = reader.next();
		String fullFolderName = ROOT_FOLDER + folderName + "\\";

		// prompt for starting number
		System.out.println("Enter starting value: ");
		String startingValueYear = reader.next();
		String startingValueNumber = reader.next();
		reader.close();
		*/

        String folderName = "Israel"; // "pics"; "1997-08" //
        String startingValueYear = "2018-10";
        String startingValueNumber = "439";
        boolean reverseNumber = false;

        String fullFolderName = ROOT_FOLDER + folderName + "/";

        //parse starting number
        String fileNamePrefix = startingValueYear + " ";
        int fileNumberStart = Integer.parseInt(startingValueNumber);

        //get list of files
        File folder = new File(fullFolderName);
        List<String> fileList = Arrays.asList(folder.list());
        Collections.sort(fileList);		//now in ascending order

//		String firstFileName = fileList.get(0);
//		int dotIndexFirstFileName = firstFileName.indexOf(".");
//		String firstFileNameNoString = firstFileName.substring(8, dotIndexFirstFileName);
//		String line = firstFileNameNoString;
//		Pattern p = Pattern.compile("\\p{Alpha}");
//		Matcher m = p.matcher(line);
//		int foundAlphaAt = -1;
//		if (m.find()) {
//		    foundAlphaAt = m.start();
//		}
//		if (foundAlphaAt != -1) {
//			firstFileNameNoString = firstFileNameNoString.substring(0, foundAlphaAt);
//		}
//		int firstFileNameNo = Integer.parseInt(firstFileNameNoString);
//
//		String lastFileName = fileList.get(fileList.size() - 1);
//		int dotIndexLastFileName = lastFileName.indexOf(".");
//		String lastFileNameNoString = lastFileName.substring(8, dotIndexLastFileName);
//		line = lastFileNameNoString;
//		m = p.matcher(line);
//		if (m.find()) {
//		    foundAlphaAt = m.start();
//		}
//		if (foundAlphaAt != -1) {
//			lastFileNameNoString = lastFileNameNoString.substring(0, foundAlphaAt);
//		}
//		int lastFileNameNo = Integer.parseInt(lastFileNameNoString);

        //format
        DecimalFormat intFormatter = new DecimalFormat("000");

        //determine if list should be ascending or descending
        int countRenumbered = 0;
        if (!reverseNumber /*&& fileNumberStart <= firstFileNameNo*/) {
            System.out.println("Renumbering in Ascending Order");
            int fileNumber = fileNumberStart;
            for (String fileName : fileList) {
                File fileBefore = new File(fullFolderName + fileName);
                File fileAfter = new File (fullFolderName + fileNamePrefix + intFormatter.format(fileNumber) + ".jpg");
                boolean success = fileBefore.renameTo(fileAfter);
                if (!success) {
                    System.out.println("Failed to rename '" + fileBefore.getAbsolutePath() + "' to '" + fileAfter.getAbsolutePath() + "'");
                } else countRenumbered++;
                fileNumber++;
            }
        } else /*if (reverseNumber || fileNumberStart > firstFileNameNo)*/ {
            System.out.println("Renumbering in Descending Order");
            Collections.sort(fileList, Collections.reverseOrder());		//now in ascending order
            int fileNumber = fileNumberStart + (fileList.size() - 1);
            for (String fileName : fileList) {
                File fileBefore = new File(fullFolderName + fileName);
                File fileAfter = new File (fullFolderName + fileNamePrefix + intFormatter.format(fileNumber) + ".jpg");
                boolean success = fileBefore.renameTo(fileAfter);
                if (!success) {
                    System.out.println("Failed to rename '" + fileBefore.getAbsolutePath() + "' to '" + fileAfter.getAbsolutePath() + "'");
                } else countRenumbered++;
                fileNumber--;
            }
//		} else {
//			System.out.println("ERROR :: Cannot renumber when the starting value already exists");
        }

        System.out.println("FINISHED: Renumbered " + countRenumbered + " files!");

    }

}
