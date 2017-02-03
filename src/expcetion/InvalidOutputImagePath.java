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
public class InvalidOutputImagePath extends IllegalArgumentException {
    
    public InvalidOutputImagePath(String msg){
        super(msg);
    }
}
