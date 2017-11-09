package userInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class GameOptionDialog 
{
    private JDialog dialog;
    private JPanel gameOptionPanel;
    private JPanel optionsPanel;
    private JPanel buttonPanel;
    private JLabel computerAiLbl;
    private JLabel shipLayoutLbl;
    private JComboBox computerAi;
    private JComboBox shipLayout;
    private JButton saveBtn;
    private JButton canxBtn;
    
    private static String[] level = {"Normal", "Ridiculously Hard"}; 
    private static String[] layout = {"Manual","Automatic"};

    private String selectedLevel; 
    private String selectedLayout;
  
    private Player[] playerArray;
    
    public GameOptionDialog(JFrame parent, Player[] inPlayers)
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
        gameOptionPanel = new JPanel(new GridLayout(2, 2));
        gameOptionPanel.setBorder(BorderFactory.createTitledBorder("Game Options"));
        gameOptionPanel.setPreferredSize(new Dimension(275, 100));
        
        // gameOptionPanel
        computerAiLbl = new JLabel("Computer AI");
        gameOptionPanel.add(computerAiLbl);
        computerAi = new JComboBox(level);
        computerAi.setSelectedIndex(0);			
        gameOptionPanel.add(computerAi);
        
        shipLayoutLbl = new JLabel("Ship Layout");
        gameOptionPanel.add(shipLayoutLbl);
        shipLayout = new JComboBox(layout);
        shipLayout.setSelectedIndex(0);			
        gameOptionPanel.add(shipLayout);
        
        // optionsPanel
        optionsPanel.add(gameOptionPanel);
        
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
        dialog.setMinimumSize(new Dimension(300, 190));
        dialog.setLocation(200,200);
        dialog.setVisible(true);		
    }
    
    //Listener for ok button in statistics menu
    private class SaveListener implements ActionListener
    {	
        public void actionPerformed(ActionEvent e)
        {
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
