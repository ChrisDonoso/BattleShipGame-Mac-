package userInterface;

import java.awt.*;
import javax.swing.*;

import core.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Player 
{
    // Class members
    private boolean isFirst;
    private Color shipColor;
    private int currentShip; 
    private int currentShipLength;
    private int currentDirection;
    private JButton[][] buttonBoard;
    
    
    // instance variable 
    private String userName;
    
    private BattleshipUI parent;
    private BattleshipUI ui;
    
    private int playMode;
    
    private final static int rows = 10;
    private final static int cols = 10;
    
    private ArrayList<Ship> ships;
    private Battleship battleship;
    private Carrier carrier;
    private Destroyer destroyer;
    private PatrolBoat patrolBoat;
    private Submarine submarine;
    
    private BoardListener boardListener;
    
    public Player()
    {
    }
    
    public Player(String name, BattleshipUI inParent)
    {			
        parent = inParent;
        userName = name;
 
        initObjects();
        initComponents();
    }	

    public void setParent(BattleshipUI inParent)
    {
        parent = inParent;
    }
    
    private void initObjects()
    {
        ships = new ArrayList<Ship>();
        
        carrier = new Carrier();
        ships.add(Constants.CARRIER, carrier);
        battleship = new Battleship();
        ships.add(Constants.BATTLESHIP, battleship);
        submarine = new Submarine();
        ships.add(Constants.SUBMARINE, submarine);
        destroyer = new Destroyer();
        ships.add(Constants.DESTROYER, destroyer);
        patrolBoat = new PatrolBoat();
        ships.add(Constants.PATROL_BOAT, patrolBoat);
        
        boardListener = new BoardListener();
    }
    
    private void initComponents()
    {
        buttonBoard = new JButton [10][10];        
        
        for (int row = 0; row < 10; row++)
        {			
            for (int col = 0; col < 10; col++)
            {
                buttonBoard[row][col] = new JButton();
                buttonBoard[row][col].putClientProperty("row", row);
                buttonBoard[row][col].putClientProperty("col", col);
                setListener(buttonBoard[row][col], boardListener);
            }
        }	
    }
    
    public BoardListener getBoardListener()
    {
        return boardListener;
    }
    
    public void setListener(JButton inButton, ActionListener inListener)
    {
        inButton.addActionListener(inListener);
    }

    public void removeListener(JButton inButton, ActionListener inListnener)
    {
        inButton.removeActionListener(inListnener);
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public JButton[][] getBoard()
    {
        return buttonBoard;
    }
    
    public int getPlayMode()
    {
        return playMode;
    }
    
    public void setPlayMode(int inMode)
    {
        playMode = inMode;
    }
    
    /**
     * @return the rows
     */
    public static int getRows() 
    {
        return rows;
    }

    /**
     * @return the cols
     */
    public static int getCols() 
    {
        return cols;
    }

    /**
     * @return the currentShip
     */
    public int getCurrentShipLength() 
    {
        return currentShipLength;
    }

    /**
     * @param currentShip the currentShip to set
     */
    public void setCurrentShip(int currentShip)
    {
        this.currentShip = currentShip;
        
        if(currentShip == Constants.BATTLESHIP)
            currentShipLength = Constants.BATTLESHIP_LENGTH;
        else if(currentShip == Constants.CARRIER)
            currentShipLength = Constants.CARRIER_LENGTH;
        else if(currentShip == Constants.DESTROYER)
            currentShipLength = Constants.DESTROYER_LENGTH;
        else if(currentShip == Constants.PATROL_BOAT)
            currentShipLength = Constants.PATROL_BOAT_LENGTH;
        else if(currentShip == Constants.SUBMARINE)
            currentShipLength = Constants.SUBMARINE__LENGTH;
    }

    /**
     * @return the currentDirection
     */
    public int getCurrentDirection() 
    {
        return currentDirection;
    }

    /**
     * @param currentDirection the currentDirection to set
     */
    public void setCurrentDirection(int currentDirection) 
    {
        this.currentDirection = currentDirection;
    }

    public JButton[][] getButtonBoard() {
        return buttonBoard;
    }
    
    public void setButtonBoard(JButton[][] buttonBoard) {
        this.buttonBoard = buttonBoard;
    }    
    
    /**
     * @return the isFirst
     */
    public boolean isIsFirst() 
    {
        return isFirst;
    }

    /**
     * @param isFirst the isFirst to set
     */
    public void setIsFirst(boolean isFirst) 
    {
        this.isFirst = isFirst;
    }

    /**
     * @return the shipColor
     */
    public Color getShipColor() 
    {
        return shipColor;
    }

    /**
     * @param shipColor the shipColor to set
     */
    public void setShipColor(Color shipColor) 
    {
        this.shipColor = shipColor;
    }
    
    /**
     * @return the battleship
     */
    public Battleship getBattleship() 
    {
        return battleship;
    }

    /**
     * @return the carrier
     */
    public Carrier getCarrier() 
    {
        return carrier;
    }

    /**
     * @return the destroyer
     */
    public Destroyer getDestroyer() 
    {
        return destroyer;
    }

    /**
     * @return the patrolBoat
     */
    public PatrolBoat getPatrolBoat() 
    {
        return patrolBoat;
    }

    /**
     * @return the submarine
     */
    public Submarine getSubmarine() 
    {
        return submarine;
    }

    private void placeShip(int rowClick, int colClick)
    {
        if(getCurrentShip() == Constants.BATTLESHIP && getBattleship().isShipPlaced())
        {
            removeShip(getBattleship());
        }
        else if(getCurrentShip() == Constants.CARRIER && getCarrier().isShipPlaced())
        {
            removeShip(getCarrier());
        }
        else if(getCurrentShip() == Constants.DESTROYER && getDestroyer().isShipPlaced())
        {
            removeShip(getDestroyer());
        }
        else if(getCurrentShip() == Constants.PATROL_BOAT && getPatrolBoat().isShipPlaced())
        {
            removeShip(getPatrolBoat());
        }
        else if(getCurrentShip() == Constants.SUBMARINE && getSubmarine().isShipPlaced())
        {
            removeShip(getSubmarine());
        }

        switch( getCurrentDirection() )
        {
            case Constants.VERTICAL:
            {        
                for(int row = rowClick; row < (rowClick + getCurrentShipLength()); row++)
                {
                    getButtonBoard()[row][colClick].setBackground(getShipColor());
                    getButtonBoard()[row][colClick].setOpaque(true);                
                }

                break;
            }
            case Constants.HORIZONTAL:
            {
                for(int col = colClick; col < (colClick + getCurrentShipLength()); col++)
                {
                    getButtonBoard()[rowClick][col].setBackground(getShipColor());
                    getButtonBoard()[rowClick][col].setOpaque(true);                
                }
                
                break;
            }
        }

        if(getCurrentShip() == Constants.BATTLESHIP)
        {
            getBattleship().setShipDirection(getCurrentDirection());
            getBattleship().setShipLocation(rowClick, colClick);
            getBattleship().setShipPlaced(true);
        }
        else if(getCurrentShip() == Constants.CARRIER)
        {
            getCarrier().setShipDirection(getCurrentDirection());
            getCarrier().setShipLocation(rowClick, colClick);
            getCarrier().setShipPlaced(true);
        }
        else if(getCurrentShip() == Constants.DESTROYER)
        {
            getDestroyer().setShipDirection(getCurrentDirection());
            getDestroyer().setShipLocation(rowClick, colClick);
            getDestroyer().setShipPlaced(true);
        }
        else if(getCurrentShip() == Constants.PATROL_BOAT)
        {
            getPatrolBoat().setShipDirection(getCurrentDirection());
            getPatrolBoat().setShipLocation(rowClick, colClick);
            getPatrolBoat().setShipPlaced(true);
        }
        else if(getCurrentShip() == Constants.SUBMARINE)
        {
            getSubmarine().setShipDirection(getCurrentDirection());
            getSubmarine().setShipLocation(rowClick, colClick);
            getSubmarine().setShipPlaced(true);
        }                        

        if(getUserName().equals("Player 1"))
        {
            isReadyToDeploy();
        }
    }
    
    private void isReadyToDeploy()
    {
        if( getBattleship().isShipPlaced() == true && 
            getCarrier().isShipPlaced() == true && 
            getDestroyer().isShipPlaced() == true && 
            getPatrolBoat().isShipPlaced() == true &&
            getSubmarine().isShipPlaced() == true)
        {
            parent.setDeployEnabled(true);
        }
    }

    private boolean isValid(int rowClick, int colClick)
    {
        switch( getCurrentDirection() )
        {
            case Constants.VERTICAL:
            {
                if( (rowClick + getCurrentShipLength()) > getRows() )
                {
                    return false;
                }
                
                break;
            }
            case Constants.HORIZONTAL:
            {
                if( (colClick + getCurrentShipLength()) > getCols() )
                {
                    return false;
                }
                
                break;
            }
        }
        
        return true;
    }

    private boolean isOccupied(int rowClick, int colClick)
    {
        switch( getCurrentDirection() )
        {
            case Constants.VERTICAL:
            {
                for(int row = rowClick; row < rowClick + getCurrentShipLength(); row ++)
                {
                    if(buttonBoard[row][colClick].getBackground() == shipColor)
                    {
                        return true;
                    }
                }
                
                break;
            }
            case Constants.HORIZONTAL:
            {
                for(int col = colClick; col < colClick + getCurrentShipLength(); col ++)
                {
                    if(buttonBoard[rowClick][col].getBackground() == shipColor)
                    {
                        return true;
                    }
                }
                break;
            }
        }
        
        return false;
    }
    
    private void removeShip(Ship inShip)
    {
        switch( inShip.getShipDirection() )
        {
            case Constants.VERTICAL:
            {
                for(int row = inShip.getShipStartRow(); row < (inShip.getShipStartRow() + inShip.getShipLength()); row++)
                {
                    buttonBoard[row][inShip.getShipStartCol()].setBackground(null);
                    buttonBoard[row][inShip.getShipStartCol()].setOpaque(true);
                }
                
                break;
            }
            case Constants.HORIZONTAL:
            {
                for(int col = inShip.getShipStartCol(); col < (inShip.getShipStartCol() + inShip.getShipLength()); col++)
                {
                    buttonBoard[inShip.getShipStartRow()][col].setBackground(null);
                    buttonBoard[inShip.getShipStartRow()][col].setOpaque(true);

                }
                break;
            }               
        }
        
        inShip.setShipPlaced(false);
    }

    /**
     * @return the ships
     */
    public ArrayList<Ship> getShips() 
    {
        return ships;
    }

    /**
     * @return the currentShip
     */
    public int getCurrentShip() 
    {
        return currentShip;
    }
    
    public boolean isHit(int rowClick, int colClick)
    {
        for(Ship ship: ships)
        {
            for(int row = ship.getShipStartRow(); row <= ship.getShipStopRow(); row ++)
            {
                for(int col = ship.getShipStartCol(); col <= ship.getShipStopCol(); col++)
                {
                    if(row == rowClick && col == colClick)
                    {
                        ship.minusHitsLeft();
                        
                        if(ship.isShipSunk() == true)
                        {
                            parent.updateTextArea(ship.getShipName() + " is sunk");
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void autoLayout()
    {
        Random random = new Random();
        
        for(int ship = 0; ship < getShips().size(); ship++)
        {
            setCurrentShip(ship);
            setCurrentDirection(random.nextInt(2));
            
            int rowClick = random.nextInt(getRows());
            int colClick = random.nextInt(getCols());
            
            while(!getShips().get(ship).isShipPlaced())
            {
                if(isValid(rowClick, colClick) && !isOccupied(rowClick, colClick))
                {
                    placeShip(rowClick, colClick);
                    getShips().get(ship).setShipPlaced(true);
                }
                
                else
                {
                    rowClick = random.nextInt(getRows());
                    colClick = random.nextInt(getCols());
                    setCurrentDirection(random.nextInt(2));
                }
            }    
        }
    }
        
    public class BoardListener implements ActionListener
    {	
        public void actionPerformed(ActionEvent e) 
        {
            if( e.getSource() instanceof JButton) 
            {               
                JButton button = (JButton)e.getSource();
                int rowClick = (int)button.getClientProperty("row");
                int colClick = (int)button.getClientProperty("col");
                
                if( !isValid(rowClick, colClick) )
                {
                    JOptionPane.showMessageDialog(null, "Ship will not fit in selected location, select a different location", 
                            "Try again", JOptionPane.ERROR_MESSAGE);
                }    
                else if (isOccupied(rowClick, colClick))
                {
                    JOptionPane.showMessageDialog(null, "One or more spaces are occupied, select again", 
                            "Try again", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    placeShip(rowClick, colClick);
                }
            }
        }
    }    
}
