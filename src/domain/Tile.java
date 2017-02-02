package domain;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import thread.ThreadTile;

/**
 *
 * @author Max
 */
public class Tile extends Image {
    private Color avarageColor;
    private int usedTimes;
    private boolean isEnabled;
    
    public Tile(String imagePath) throws IOException {
        super(imagePath);
        build(img);
        usedTimes = 0;
    }
    
    private void build(BufferedImage img){
        int r = 0;
        int g = 0;
        int b = 0;
        
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Color color = new Color(img.getRGB(i, j));
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
    public static List<Tile> getTiles(String path) throws IOException{
        File folder = new File(path);
        
        if(!folder.exists()){
            throw new IllegalArgumentException("Invalid tile's folder path: " + path);
        }
        
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg");
            }
         });
        
        List<Tile> tileArray = new LinkedList<>();
        
        for(int i = 0; i < files.length; i++){
            BufferedImage img = ImageIO.read(files[i]);

            if(img.getWidth() != 50){
                continue;
            }

            if(img.getHeight() != 50){
                continue;
            }

            Tile tile = new Tile(files[i].getAbsolutePath());

            tileArray.add(tile);
        }
        
        return tileArray;
    }
    
    public static List<Tile> getTiles2(String path) throws IOException, InterruptedException{
        File folder = new File(path);
        
        if(!folder.exists()){
            throw new IllegalArgumentException("Invalid tile's folder path: " + path);
        }
        
        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg");
            }
         });
        
        int aux = files.length / 4;
        
        ThreadTile runnable1 = new ThreadTile(0, aux - 1, files);
        ThreadTile runnable2 = new ThreadTile(aux, (aux*2) - 1, files);
        ThreadTile runnable3 = new ThreadTile((aux*2), (aux*3) - 1, files);
        ThreadTile runnable4 = new ThreadTile((aux*3), files.length - 1, files);
        
        final List<Tile> finalTile = new LinkedList<>();
        
        runnable1.setThreadTilesListener(new ThreadTilesListener() {
            @Override
            public void onThreadTilesFinished(Tile[] tileArray, int start, int end) {
                fill(finalTile, tileArray);
            }
            
        });
        
        runnable2.setThreadTilesListener(new ThreadTilesListener() {
            @Override
            public void onThreadTilesFinished(Tile[] tileArray, int start, int end) {
                fill(finalTile, tileArray);
            }
            
        });
        
        runnable3.setThreadTilesListener(new ThreadTilesListener() {
            @Override
            public void onThreadTilesFinished(Tile[] tileArray, int start, int end) {
                fill(finalTile, tileArray);
            }
            
        });
        
        runnable4.setThreadTilesListener(new ThreadTilesListener() {
            @Override
            public void onThreadTilesFinished(Tile[] tileArray, int start, int end) {
                fill(finalTile, tileArray);
            }
            
        });
        
        runnable1.start();
        runnable2.start();
        runnable3.start();
        runnable4.start();
        
        runnable1.join();
        runnable2.join();
        runnable3.join();
        runnable4.join();

        return finalTile;
    }
    
    private static List<Tile> fill(List<Tile> tileArray, Tile[] tileArray1){
        for(int i = 0; i < tileArray1.length; i++){
            tileArray.add(tileArray1[i]);
        }
        
        return tileArray;
    }
    
    public void useTile(){
        this.isEnabled = false;
    }
    
    public boolean reachLimit(){
        return usedTimes > 20;
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

        @Override
        public String toString() {
            return "Position{" + "x=" + x + ", y=" + y + '}';
        }
    }
    
    public interface ThreadTilesListener{
        public void onThreadTilesFinished(Tile[] tileArray, int start, int end);
    }
}
