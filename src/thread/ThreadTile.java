/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import domain.Tile;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Max
 */
public class ThreadTile extends Thread{
        /**
         * Files array containing tiles
         */
        private final File[] fileArray;
        
        /**
         * Start and final index of the tileArray that this thread will process.
         */
        private final int startIndex;
        private final int finalIndex;
        
        private Tile.ThreadTilesListener threadTilesListener;
        
        public ThreadTile(int startIndex, int finalIndex, File[] fileArray){
            this.fileArray = fileArray;
            
            if(startIndex > finalIndex){
                throw new IllegalArgumentException("Start index is higher then final index: Start("+ String.valueOf(startIndex) +", Final("+ String.valueOf(finalIndex) +"))");
            }
            
            this.startIndex = startIndex;
            this.finalIndex = finalIndex;
        }

        @Override
        public void run() {
            try {
                Tile[] finalArray = getTiles();
                
                if(threadTilesListener != null){
                    threadTilesListener.onThreadTilesFinished(finalArray, startIndex, finalIndex);
                }
            } catch (IOException ex) {
                Logger.getLogger(Tile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public Tile[] getTiles() throws IOException{
            Tile[] tileArray = new Tile[finalIndex - startIndex];
        
            for(int i = startIndex, j = 0; i < finalIndex; i++, j++){
                BufferedImage img = ImageIO.read(fileArray[i]);

                if(img.getWidth() != 50){
                    continue;
                }

                if(img.getHeight() != 50){
                    continue;
                }

                Tile tile = new Tile(fileArray[i].getAbsolutePath());
                tile.build();

                tileArray[j] = tile;
            }
        
            return tileArray;
        }
        

        public void setThreadTilesListener(Tile.ThreadTilesListener threadTilesListener) {
            this.threadTilesListener = threadTilesListener;
        }
    }
