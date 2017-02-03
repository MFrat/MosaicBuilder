/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaicbuilder;

import expcetion.InvalidInputImagePath;
import expcetion.InvalidTileFolderPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import logic.MosaicBuilder;

/**
 *
 * @author Max
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
       //String tilePath = "D:\\Arquivos\\Downloads\\mega image pack - Copia";
       //String tilePath = "D:\\Arquivos\\Pictures\\dayz_pack_50x50";
       String tilePath = "D:\\Arquivos\\Pictures\\50x50_packs\\Bohemia_pack_50x50";
       String inputImagePath = "D:\\Arquivos\\Pictures\\teste20001111.jpg";
       final String caminhoFinal = "D:\\Arquivos\\Pictures\\mosaico\\teste2001.png";
        
        MosaicBuilder mosaicBuilder = new MosaicBuilder(tilePath, inputImagePath, 50);
        
        mosaicBuilder.setListener(new MosaicBuilder.MosaicBuilderListener() {
           @Override
           public void onMosaicFinished(BufferedImage img) {
               try {
                   ImageIO.write(img, "png", new File(caminhoFinal));
               } catch (IOException ex) {
                   System.out.println("Invalid output image path: " + caminhoFinal);
               }
           }

           @Override
           public void onProgressChanged(int status) {
               switch (status){
                    case MosaicBuilder.BUILDING_OUTPUT_IMAGE:
                        System.out.println("Building final image...");
                        break;
                     case MosaicBuilder.READING_INPUT_IMAGE_INTO_MEMORY:
                        System.out.println("Reading input image into memory...");
                        break;
                     case MosaicBuilder.READING_TILES_INTO_MEMORY:
                        System.out.println("Reading tiles into memory...");
                        break;
                     case MosaicBuilder.SELECTING_TILES:
                        System.out.println("Selecting best tiles for each input image's pixel...");
                        break;
               }
           }
       });
        
        try{
            mosaicBuilder.build();
        }catch(InvalidInputImagePath e){
            System.out.println("Invalid input path: " + inputImagePath);
        }catch(InvalidTileFolderPath e){
            System.out.println("Invalid tile folder path: " + tilePath);
        }
    }
}
