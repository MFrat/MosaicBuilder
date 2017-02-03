/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import expcetion.InvalidInputImagePath;
import expcetion.InvalidOutputImagePath;
import expcetion.InvalidTileFolderPath;
import java.awt.Color;
import java.io.File;

import java.awt.image.BufferedImage; 
import java.io.File; 
import java.io.IOException; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Classs representing a input image.
 * @author Max
 */
public abstract class Image {
    /**
     * Image's dimensions.
     */
    public final int width;
    public final int height;
    
    public File file;
    
    public final BufferedImage img;
    
    /**
     * Matrix representing the image.
     */
    protected Color[][] pixelMatrix;
    
    public Image(String imagePath){
        file = new File(imagePath);
        
        try {
            img = ImageIO.read(file);
        } catch (IOException ex) {
            if(this instanceof InputImage){
                throw new InvalidInputImagePath("Invalid input image path: " + imagePath);
            }else{
                throw new InvalidOutputImagePath("Invalid tile image path: " + imagePath);
            }
        }
        
        width = img.getWidth();
        height = img.getHeight();
        
        pixelMatrix = new Color[width][height];
    }
    
    public Image(int width, int height){
        this.width = width;
        this.height = height;
        
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    
    public Color getPixelColor(int x, int y){
        return pixelMatrix[x][y];
    }
    
    public boolean equals(Image image){
        return file.getName().equals(image.file.getName());
    }
}
