/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

public interface IShip 
{  

    public static final int MAX_SHIPS = 5;
    
    // method signatures
    // similar to a function prototype in C
    // no implementation
    boolean isShipPlaced();
    void setShipLocation(int row, int column);
    
    int getShipLength();
    void setShipLength(int inLength);
    
    boolean isShipSunk();
    
    String getShipName();
    void setShipName(String inName);
    
    int getHitsLeft();
    int getMaxHits();
    void setMaxHits(int inHits);
   
    int getShipDirection();
    void setShipDirection(int inDirection);
    
}
