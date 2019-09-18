import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
/*
Jacob Leczycki
9/18/2019

This program takes in an image and turns it into a textfile that looks like the image
 */
public class Driver {
    public static void main(String[] args) {
        BufferedImage img = null;
        File dark = null;
        Scanner in = null;
        PrintWriter out = null;
        ArrayList<PixelChar> pixelCharArr = new ArrayList<PixelChar>();// an array containing the info from the darkness.txt textfile
        try {
            img = ImageIO.read(new File("sekiro.jpg")); //the image that will be converted
            dark = new File("darkness.txt"); //a file I found online that correlates characters with darkness levels
            in = new Scanner(dark);
            out = new PrintWriter("out.txt");//output textfile
        } catch (IOException e) {
            System.out.println("File not found");
        }
        //turns the darkness.txt file into an arraylist of PixelChar objects
        while(in.hasNext()){
            int num = in.nextInt();
            String text = in.next();
            //the first line of the textfile was being annoying, this fixed it
            if(num == 32){
                text = " ";
            }
            else {
                in.next();
            }
            String darkness = in.next();
            //the text file had a comma instead pf a decimal so I had to change that and then turn it into a double
            double darkDouble = Double.parseDouble(darkness.replace(",","." ));
            in.nextLine();
            //creates the pixelchar objects and adds them to the arraylist
            //the text file darkness level range is from 0 to 19.12, i mapped it to 0 to 255 to match the greyscale level
            PixelChar pc1 = new PixelChar(num, text, map(19.12,255,darkDouble));
            pixelCharArr.add(pc1);
        }

        int scanSize = 5; //resolution of the textfile image
        int width = img.getWidth(); //pixel width of the image
        int height = img.getHeight(); //pixel height of the image

        //printing into the textfile
        for (int i = 0; i < height-scanSize; i+=scanSize) {
            out.println();
            for(int j = 0; j < width-scanSize; j+=scanSize){
                int[] pixArray = (img.getRGB(j,i, scanSize, scanSize, null, 0, scanSize));
                double grey = greyScale(pixArray); //the greyScale value of resolution square at index j,i
                out.print(getPixChar(pixelCharArr,grey).getText() + " ");
            }

        }
        in.close();
        out.close();

    }
    //average of all the rgb values in the pixelArray
    public static int greyScale(int[] pixArray){
        int total = 0;
        for (int value : pixArray) {
            Color c = new Color(value);
            total += (c.getRed() + c.getGreen() + c.getBlue());
        }
        return total/(3*pixArray.length);
    }
    //maps a value from one range to another
    public static double map(double max1, double max2, double value){
        return (value*max2)/max1;
    }
    //return the PixelChar with closest darkness value to the one given
    public static PixelChar getPixChar(ArrayList<PixelChar> arr, double dark){
        dark = 255.0 - dark;
        PixelChar closestPC = null;
        for (PixelChar pc : arr) {
            if(pc.getDarkness() <= dark){
                closestPC = pc;
            }
        }
        return closestPC;
    }
}
