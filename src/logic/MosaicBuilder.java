/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.InputImage;
import domain.OutputImage;
import domain.Tile;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Max
 */
public class MosaicBuilder {
    
    private MosaicBuilderListener listener;
    
    private final InputImage inputImage;
    private final OutputImage outputImage;
    private final String tileFolderPath;
    private final List<Tile> tiles;
    private List<Tile> lastUsedTiles;
    
    public static final int READING_INPUT_IMAGE_INTO_MEMORY = 0;
    public static final int READING_TILES_INTO_MEMORY = 1;
    public static final int SELECTING_TILES = 2;
    public static final int BUILDING_OUTPUT_IMAGE = 3;
    
    public MosaicBuilder(String tileFolderPath, String inputImagePath, int tileDimen) throws IOException, InterruptedException{
        this.inputImage = new InputImage(inputImagePath);
        this.outputImage = new OutputImage(inputImage.width * tileDimen, inputImage.height * tileDimen);        
        this.tileFolderPath = tileFolderPath;       
        //this.tiles = Tile.getTiles(tileFolderPath);
        this.tiles = Tile.getTiles2(tileFolderPath);
        this.lastUsedTiles = new ArrayList<>();
    }
    
    public MosaicBuilder(String tileFolderPath, String inputImagePath, int tileDimen, MosaicBuilderListener listener) throws IOException, InterruptedException{
        this.listener = listener;
        
        if(listener != null){
            listener.onProgressChanged(READING_INPUT_IMAGE_INTO_MEMORY);
        }
        this.inputImage = new InputImage(inputImagePath);
        
        this.outputImage = new OutputImage(inputImage.width * tileDimen, inputImage.height * tileDimen);        
        this.tileFolderPath = tileFolderPath;     
        
        if(listener != null){
            listener.onProgressChanged(READING_TILES_INTO_MEMORY);
        }
        this.tiles = Tile.getTiles(tileFolderPath);
        //this.tiles = Tile.getTiles2(tileFolderPath);
        
        this.lastUsedTiles = new ArrayList<>();
    }
    
    
    public void build(){
        if(listener != null){
            listener.onProgressChanged(SELECTING_TILES);
        }
        
        for(int i = 0; i < inputImage.getWidth(); i++){
            for(int j = 0; j < inputImage.getHeight(); j++){
                Color inputImgPxColor = inputImage.getPixelColor(i, j);
                Tile bestTile = chooseBestTile(inputImgPxColor);
                outputImage.setTile(bestTile, i, j);
            }
        }
        
        if(listener != null){
            listener.onProgressChanged(BUILDING_OUTPUT_IMAGE);
        }
        
        outputImage.buildImage();
        
        if(listener != null){
            listener.onMosaicFinished(outputImage.img);
        }
    }
    
    private Tile chooseBestTile(Color pixelColor){
        Tile bestTile = tiles.get(0);
        
        for(Tile tile : tiles){
            if(lastUsedTiles.indexOf(tile) == -1){
                bestTile = compareTile(pixelColor, bestTile, tile);
            }
        }
        
        if(lastUsedTiles.size() == 30){
            lastUsedTiles.remove(0);
        }
        
        lastUsedTiles.add(bestTile);
        
        return bestTile;
    }
    
    private Tile compareTile(Color pixelColor, Tile tile1, Tile tile2){
        int difTile1 = difColor(pixelColor, tile1.getAvarageColor());
        int difTile2 = difColor(pixelColor, tile2.getAvarageColor());

        return difTile1 < difTile2? tile1 : tile2;
    }
    
    private int difColor(Color color1, Color color2){
        int deltaR = (int) Math.pow(color1.getRed() - color2.getRed(), 2);
        int deltaG = (int) Math.pow(color1.getGreen() - color2.getGreen(), 2);
        int deltaB = (int) Math.pow(color1.getBlue() - color2.getBlue(), 2);
        
        return (int) Math.sqrt((deltaR)*2 + (deltaG)*4 + (deltaB)*3);
    }
    
    public interface MosaicBuilderListener{
        public void onMosaicFinished(BufferedImage img);
        public void onProgressChanged(int status);
    }

    public void setListener(MosaicBuilderListener listener) {
        this.listener = listener;
    }
}
