/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

/**
 *
 * @author kwhiting
 */
public class Submarine extends Ship
{
    public Submarine()
    {
        setShipLength(Constants.SUBMARINE__LENGTH);
        setShipName("Submarine");
        setMaxHits(Constants.SUBMARINE__LENGTH);            
    }
}
