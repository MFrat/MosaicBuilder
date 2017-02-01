/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author Max
 */
public class OutputImage extends Image {
    
    private String fileName;
    
    public OutputImage(String imagePath, int width, int height) throws IOException {
        super(imagePath, width, height);
        
    }
    
    public void setFileName(String fileName){
        this.fileName = fileName;
    }
    
    public String getFileName(){
        return fileName;
    }
    
    public int getTotalPixel(){
        return width * height;
    }
    
    public void setPixelColor(int i, int j, Color color){
        img.setRGB(j, j, color.getRGB());
    }
}
