/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.Tile.Position;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Max
 */
public class OutputImage extends Image {
    
    private final Tile[][] tileMatrix;
    
    public OutputImage(int width, int height){
        super(width, height);
        
        tileMatrix = new Tile[width/50][height/50];
    }
    
    public int getTotalPixel(){
        return width * height;
    }
    
    private void drawTile(Tile tile, int x, int y){
        int x0 = x * 50;
        int y0 = y * 50;
        
        for(int i = x0, k = 0; i < x0 + 50; i++, k++){
            for(int j = y0, l = 0; j < y0 + 50; j++, l++){
                setPixelColor(i, j, tile.getPixelColor(k, l));
            }
        }
    }
    
    public void buildImage(){
        for(int i = 0; i < width/50; i++){
            for(int j = 0; j < height/50; j++){
                Tile tile = tileMatrix[i][j];
                drawTile(tile, i, j);
            }
        }
    }
    
    public List<Tile> getNearTiles(int level, int i, int j){
        List<Tile> left = new ArrayList<>();
        List<Tile> top = new ArrayList<>();
        
        //Left
        for(int k = j, l = 0; l < level; k--, l++){
            if(validPosition(k, i)){
                try{
                    left.add(tileMatrix[k][i]);
                }catch(Exception e){
                    //caguei.
                }
            }
        }
        
        //top
        for(int k = i,  l = 0; l < level; k++, l++){
            if(validPosition(k, j)){
                try{
                    top.add(tileMatrix[k][j]);
                }catch(Exception e){
                    //caguei.
                }
            }
        }
        
        left.addAll(top);
        
        return left;
    }
    
    private boolean validPosition(int i, int j){
        return !(i < 0 || i >= height/50 || i >= width/50) || (j < 0 || j >= width/50 || j >= height/50);
    }
    
    private void setPixelColor(int i, int j, Color color){
        img.setRGB(i, j, color.getRGB());
    }
    
    public void setTile(Tile tile, int i, int j){
        tileMatrix[i][j] = tile;
    }
}
