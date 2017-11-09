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
public class Carrier extends Ship 
{
    public Carrier()
    {
        setShipLength(Constants.CARRIER_LENGTH);
        setShipName("Carrier");
        setMaxHits(Constants.CARRIER_LENGTH);
    }
}
