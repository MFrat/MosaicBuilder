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
