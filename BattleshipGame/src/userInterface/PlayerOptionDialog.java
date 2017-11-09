package userInterface;

import core.Constants;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 *
 * @author kwhiting
 */
public class PlayerOptionDialog
{
    private JDialog dialog;
    private JPanel playerOptionPanel;
    private JPanel optionsPanel;
    private JPanel buttonPanel;
    private JLabel shipColorLbl; 
    private JLabel firstPlayerLbl;
    private JComboBox shipColor;
    private JComboBox firstPlayer;
    private JButton saveBtn;
    private JButton canxBtn;
    private JRadioButton player1;
    private JRadioButton player2;
    private ButtonGroup playerOptions;
    private static Color[] color = {Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE,
                                    Color.PINK,	Color.WHITE, Color.YELLOW};		 	

    private static String[] colors = {"Cyan", "Green", "Magenta", "Magenta", 
                                      "Orange", "Pink", "White", "Yellow"};
    private static String[] players = {"Player 1", "Player 2", "Random"};

    private String selectdColor;
    private String selectedPlayer;
  
    private Player currentPlayer;
    private Player[] playerArray;
    
    public PlayerOptionDialog(JFrame parent, Player[] inPlayers)
    {
        playerArray = inPlayers;
        initComponents(parent);
    }
    
    private void initComponents(JFrame parent)
    {
        // Dialog
        dialog = new JDialog(parent, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // Options panels
        optionsPanel = new JPanel();
        optionsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        playerOptionPanel = new JPanel(new GridLayout(3, 2));
        playerOptionPanel.setBorder(BorderFactory.createTitledBorder("Player Options"));
        playerOptionPanel.setPreferredSize(new Dimension(275, 125));
        
        // playerOptionPanel
        playerOptions = new ButtonGroup();
        player1 = new JRadioButton("Player 1");
        player1.addActionListener(new PlayerListener());
        player2 = new JRadioButton("Player 2");
        player2.addActionListener(new PlayerListener());
        playerOptions.add(player1);
        playerOptions.add(player2);
        playerOptionPanel.add(player1);
        playerOptionPanel.add(player2);
        
        shipColorLbl = new JLabel("Ship Color");
        playerOptionPanel.add(shipColorLbl);				
        shipColor = new JComboBox(colors);
        shipColor.setSelectedIndex(0);	
        playerOptionPanel.add(shipColor);

        firstPlayerLbl = new JLabel("Who Plays First?");
        playerOptionPanel.add(firstPlayerLbl);
        firstPlayer = new JComboBox(players);
        firstPlayer.setSelectedIndex(0);			
        playerOptionPanel.add(firstPlayer);		

        // optionsPanel
        optionsPanel.add(playerOptionPanel);
        
        // Button Panel
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(new SaveListener());		
        
        canxBtn = new JButton("Cancel");
        canxBtn.addActionListener(new CancelListener());		
        
        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        buttonPanel.add(saveBtn);
        buttonPanel.add(canxBtn);
        
        // Final setup
        dialog.setTitle("Options");
        dialog.setLayout(new BorderLayout());        
        dialog.getContentPane().add(optionsPanel, BorderLayout.CENTER);
        dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        dialog.setMinimumSize(new Dimension(300, 225));
        dialog.setLocation(200,200);
        dialog.setVisible(true);		
    }
    
    private void initObjects()
    {
        currentPlayer = new Player();
    }
    
    private class PlayerListener implements ActionListener
    {	
        public void actionPerformed(ActionEvent e)
        {
            if(player1.isSelected())
            {
                int colorIndex = Arrays.asList(color).indexOf(playerArray[Constants.PLAYER_ONE].getShipColor());
                shipColor.setSelectedIndex(colorIndex);
                
                if(playerArray[Constants.PLAYER_ONE].isIsFirst())
                    firstPlayer.setSelectedIndex(Constants.PLAYER_ONE);
                else
                    firstPlayer.setSelectedIndex(Constants.PLAYER_TWO);
                
                currentPlayer = playerArray[Constants.PLAYER_ONE];
            }
            else if(player2.isSelected())
            {
                int colorIndex = Arrays.asList(color).indexOf(playerArray[Constants.PLAYER_TWO].getShipColor());
                shipColor.setSelectedIndex(colorIndex);
                
                if(playerArray[Constants.PLAYER_TWO].isIsFirst())
                    firstPlayer.setSelectedIndex(Constants.PLAYER_TWO);
                else
                    firstPlayer.setSelectedIndex(Constants.PLAYER_ONE);

                currentPlayer = playerArray[Constants.PLAYER_TWO];
            }
        }            
    }	

    private class SaveListener implements ActionListener
    {	
        public void actionPerformed(ActionEvent e)
        {
            if(player1.isSelected())
            {
                playerArray[Constants.PLAYER_ONE].setShipColor(color[shipColor.getSelectedIndex()]);
                boolean isFirst = firstPlayer.getSelectedIndex() == 0 ? true : false;
                playerArray[Constants.PLAYER_ONE].setIsFirst(isFirst);
                playerArray[Constants.PLAYER_TWO].setIsFirst(!isFirst);
            }
            else if(player2.isSelected())
            {
                playerArray[Constants.PLAYER_TWO].setShipColor(color[shipColor.getSelectedIndex()]);
                boolean isFirst = firstPlayer.getSelectedIndex() == 0 ? true : false;
                playerArray[Constants.PLAYER_TWO].setIsFirst(isFirst);
                playerArray[Constants.PLAYER_ONE].setIsFirst(!isFirst);
            }                
            
            dialog.dispose();
        }            
    }	

    private class CancelListener implements ActionListener
    {	
        public void actionPerformed(ActionEvent e)
        {
            dialog.dispose();
        }	
    }	
}
