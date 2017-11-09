package core;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import userInterface.BattleshipUI;
import userInterface.Player;

public class BattleshipClient 
{   
    private Player[] players;
    private Player currentPlayer;
    private BattleshipUI ui;
    private BattleshipUI parent;
//    private ClickListener clickListener;
    private int clickCount;
//    private 
    
    public BattleshipClient(Player[] p, BattleshipUI u)
    {
        players = p;
        ui = u;
    }
    
    public void play()
    {
        changeListener();
        
        if(players[Constants.PLAYER_ONE].isIsFirst())
        {
            ui.updateTextArea("Player 1, your turn!");
            currentPlayer = players[Constants.PLAYER_ONE];
        }
        
        else if(players[Constants.PLAYER_TWO].isIsFirst())
        {
            ui.updateTextArea("Player 2, your turn!");
            currentPlayer = players[Constants.PLAYER_TWO];
            computerPick();
        }
        
        else 
        {
            ui.updateTextArea("Player 1, your turn!");
            currentPlayer = players[Constants.PLAYER_ONE];
        }
    }
    
    private void changeListener()
    {
        for(Player player: players)
        {
            for(int row = 0; row < player.getRows(); row++)
            {
                for(int col = 0; col < player.getCols(); col++)
                {
                    player.removeListener(player.getBoard()[row][col], player.getBoardListener());
                }
            }
        }
        
        for(int row = 0; row < players[Constants.PLAYER_TWO].getRows(); row++)
        {
            for(int col = 0; col < players[Constants.PLAYER_TWO].getCols(); col++)
            {
                players[Constants.PLAYER_TWO].setListener(players[Constants.PLAYER_TWO].getBoard()[row][col], new PlayListener());
            }
        }
    }
    
    public class PlayListener implements ActionListener
    {
        @Override
        
        public void actionPerformed(ActionEvent ae)
        {
            if(ae.getSource() instanceof JButton)
            {
                JButton button = (JButton) ae.getSource();
                int row = (int) button.getClientProperty("row");
                int col = (int) button.getClientProperty("col");
                
                if(players[Constants.PLAYER_TWO].getBoard()[row][col].getBackground() == Color.RED ||
                    players[Constants.PLAYER_TWO].getBoard()[row][col].getBackground() == Color.BLUE)
                {
                    ui.updateTextArea("Square already selected, lose turn");
                }  
                
                else if(players[Constants.PLAYER_TWO].isHit(row,col))
                {
                    players[Constants.PLAYER_TWO].getBoard()[row][col].setBackground(Color.RED);
                    players[Constants.PLAYER_TWO].getBoard()[row][col].setOpaque(true);
                }
                
                else
                {
                    players[Constants.PLAYER_TWO].getBoard()[row][col].setBackground(Color.BLUE);
                    players[Constants.PLAYER_TWO].getBoard()[row][col].setOpaque(true);
                }
            }
            switchPlayers();
        }    
    }
    
    private void computerPick()
    {
        Random random = new Random();
        
        int row = random.nextInt(10);
        int col = random.nextInt(10);
        
        if(players[Constants.PLAYER_ONE].getBoard()[row][col].getBackground() == Color.RED ||
           players[Constants.PLAYER_ONE].getBoard()[row][col].getBackground() == Color.BLUE)
        {
            ui.updateTextArea("Square already selected, lose turn!");
        }
        
        else if(players[Constants.PLAYER_ONE].isHit(row, col))
        {
            players[Constants.PLAYER_ONE].getBoard()[row][col].setBackground(Color.RED);
            players[Constants.PLAYER_ONE].getBoard()[row][col].setOpaque(true);
        }
        
        else
        {
            players[Constants.PLAYER_ONE].getBoard()[row][col].setBackground(Color.BLUE);
            players[Constants.PLAYER_ONE].getBoard()[row][col].setOpaque(true);
        }
        
        switchPlayers();
    }
    
    private void switchPlayers()
    {
        if(checkForWinner())
            endGame();
        
        if(currentPlayer == players[Constants.PLAYER_ONE])
        {
            currentPlayer = players[Constants.PLAYER_TWO];
            ui.updateTextArea("Player 2, your turn!");
            computerPick();
        }
        
        else
        {
            currentPlayer = players[Constants.PLAYER_ONE];
            ui.updateTextArea("Player 1, your turn!");
        }
        
    }
    
    private boolean checkForWinner()
    {
        int ships = 0;
        
        for(Player player: players)
        {
            for(Ship ship: player.getShips())
            {
                if(ship.isShipSunk())
                    ships++;
            }
            
            if(ships == player.getShips().size())
            {
                JOptionPane.showMessageDialog(parent, player.getUserName() + " has lost");
                return true;
            }
            
//            ships = 0;
        }
        
        return false;
    }
    
    private void endGame()
    {
        for(Player player: players)
        {
            for(int row = 0; row < player.getRows(); row++)
            {
                for(int col = 0; col < player.getCols(); col++)
                {
                    player.getBoard()[row][col].setEnabled(false);
                }
            }
        }
    }
}

