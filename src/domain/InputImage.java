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
public class InputImage extends Image {
    
    public InputImage(String imagePath) throws IOException {
        super(imagePath);
        build(img);
    }
    
    protected void build(BufferedImage img){
       for(int i = 0; i < this.width; i++){
           for(int j = 0; j < this.height; j++){
               pixelMatrix[i][j] = new Color(img.getRGB(i, j));
           }
       }
    }
    
    public int getWidth(){
        return img.getWidth();
    }
    
    public int getHeight(){
        return img.getHeight();
    } 
}
