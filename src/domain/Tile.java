/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Max
 */
public class Tile extends Image {
    private Color avarageColor;
    
    public Tile(String imagePath) throws IOException {
        super(imagePath);
    }
    
    @Override
    protected void buildImage(BufferedImage img){
        int r = 0;
        int g = 0;
        int b = 0;
        
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Color color = new Color(img.getRGB(j, j));
                pixelMatrix[i][j] = color;
                
                r += color.getRed();
                g += color.getGreen();
                b += color.getBlue();
            }
        }
        
        int totalPixels = width * height;
        r = r / totalPixels;
        g = g / totalPixels;
        b = b / totalPixels;
        
        this.avarageColor = new Color(r, g, b);
    }
    
    /**
     * Return the avarege color of the tile image.
     * @return 
     */
    public Color getAvarageColor(){
        return avarageColor;
    }
    
    /**
     * Return a list of tile instances for each image in the given path.
     * @param path folder's path containing tiles.
     * @return 
     */
    public static List<Tile> getTiles(String path){
        return null;
    }
    
    /**
     * For a given tile index, returns a array of positions where it will be drew in the final image.
     * @param i coord i of the tile.
     * @param j coord j of the tile.
     * @return array of positions
     */
    public static Position[] getDrawableArea(int i, int j){
        return null;
    }
    
    
    public class Position{
        private int x;
        private int y;
        
        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
    
}
