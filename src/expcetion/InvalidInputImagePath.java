/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expcetion;

/**
 *
 * @author Max
 */
public class InvalidInputImagePath extends IllegalArgumentException {
    
    public InvalidInputImagePath(String msg){
        super(msg);
    }
}
