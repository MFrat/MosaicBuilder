package logic;

import domain.InputImage;
import domain.OutputImage;
import domain.Tile;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Max
 */
public class MosaicBuilder {
    /**
     * Callback interface
     */
    private MosaicBuilderListener listener;
    
    /**
     * 
     */
    private InputImage inputImage;
    private OutputImage outputImage;
    
    /**
     * Path for input image and tile folder.
     */
    private final String inputImagePath;
    private final String tileFolderPath;
    
    /**
     * List with tile instances.
     */
    private List<Tile> tiles;
    
    /**
     * Tile dimension (e.g 50x50)
     */
    private final int tileDimen;
    
    /**
     * Aux variable for avoiding same tile being choose in sequence.
     */
    private final List<Tile> lastUsedTiles;
    
    /**
     * Progress Status static constants
     */
    public static final int READING_INPUT_IMAGE_INTO_MEMORY = 0;
    public static final int READING_TILES_INTO_MEMORY = 1;
    public static final int SELECTING_TILES = 2;
    public static final int BUILDING_OUTPUT_IMAGE = 3;
    
    public MosaicBuilder(String tileFolderPath, String inputImagePath, int tileDimen) throws IOException, InterruptedException{    
        this.tileFolderPath = tileFolderPath;
        this.lastUsedTiles = new ArrayList<>();
        this.tileDimen = tileDimen;
        this.inputImagePath = inputImagePath;
    }

    public void build() throws IOException, InterruptedException{
        if(listener != null){
            listener.onProgressChanged(READING_INPUT_IMAGE_INTO_MEMORY);
        }
        this.inputImage = new InputImage(inputImagePath);
        this.inputImage.build();
        
        this.outputImage = new OutputImage(inputImage.width * tileDimen, inputImage.height * tileDimen);
        
        if(listener != null){
            listener.onProgressChanged(READING_TILES_INTO_MEMORY);
        }
        this.tiles = Tile.getTiles2(tileFolderPath);

        if(listener != null){
            listener.onProgressChanged(SELECTING_TILES);
        }
        for(int i = 0; i < inputImage.getWidth(); i++){
            for(int j = 0; j < inputImage.getHeight(); j++){
                Color inputImgPxColor = inputImage.getPixelColor(i, j);
                Tile bestTile = chooseBestTile(inputImgPxColor, i, j);
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
    
    /**
     * Choose best tile for a given pixel color.
     * @param pixelColor pixel color.
     * @return Tile instance.
     */
    private Tile chooseBestTile(Color pixelColor, int i, int j){
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
    
    /**
     * Compare tow tiles with a pixel color.
     * @param pixelColor pixel color
     * @param tile1 Tile1
     * @param tile2 Tile2
     * @return The tile with the closest avarege color compared to the pixelColor.
     */
    private Tile compareTile(Color pixelColor, Tile tile1, Tile tile2){
        int difTile1 = difColor(pixelColor, tile1.getAvarageColor());
        int difTile2 = difColor(pixelColor, tile2.getAvarageColor());

        return difTile1 < difTile2? tile1 : tile2;
    }
    
    /**
     * Calculates de difference between two colors.
     * @param color1 Color1.
     * @param color2 Color2.
     * @return integer (>=0) representing the difference.
     */
    private int difColor(Color color1, Color color2){
        int deltaR = (int) Math.pow(color1.getRed() - color2.getRed(), 2);
        int deltaG = (int) Math.pow(color1.getGreen() - color2.getGreen(), 2);
        int deltaB = (int) Math.pow(color1.getBlue() - color2.getBlue(), 2);
        
        return (int) Math.sqrt((deltaR)*2 + (deltaG)*4 + (deltaB)*3);
    }

    /**
     * Callback interface.
     */
    public interface MosaicBuilderListener{
        public void onMosaicFinished(BufferedImage img);
        public void onProgressChanged(int status);
    }

    public void setListener(MosaicBuilderListener listener) {
        this.listener = listener;
    }
}
