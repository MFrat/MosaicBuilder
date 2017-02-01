/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaicbuilder;

import domain.Tile;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Max
 */
public class MosaicBuilder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        List<Tile> list = new LinkedList<>();
        
        String tilePath = "D:\\Arquivos\\Downloads\\mega image pack - Copia";
        
        File folder = new File(tilePath);
        
        for(File file : folder.listFiles()){
            System.out.println("Lendo: " + file.getName());
            Tile tile = new Tile(file.getAbsolutePath());
            list.add(tile);
        }
    }
}
