package userInterface;

import core.BattleshipClient;
import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import core.Constants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class creates the user interface

public class BattleshipUI extends JFrame
{		
    // Menu components
    private static JMenuBar menuBar;    
    private static JMenu gameMenu;
    private static JMenu optionMenu;
    private static JMenuItem playerPlayer;
    private static JMenuItem playerComputer;
    private static JMenuItem computerComputer;	
    private static JMenuItem exit;	
    private static JMenuItem game;
    private static JMenuItem player;
    
    // Buttons
    private static JButton deploy;

    // Lay out the UI  
    private JPanel shipLayoutPanel;
    private static JPanel playerOnePanel;
    private static JPanel playerTwoPanel;
    private static JPanel statusPanel;
    private static JScrollPane scrollPane;
    private static JTextArea textArea;

    // Data arrays for various components on the UI
    private static String[] rowLetters = {" ","A","B","C","D","E","F","G","H","I","J"};
    private static String[] columnNumbers = {" ","1","2","3","4","5","6","7","8","9","10"};
    private static String[] direction = {"Horizontal","Vertical"};
    private String[] ships = {"Carrier","Battleship","Submarine","Destroyer", "Patrol Boat"};

    // ComboBoxes
    private JComboBox shipCb;
    private JComboBox directionCb;

    // event handlers
    private GameListener gameListener;
    private OptionsListener optionListener;

    // Objects
    private Player playerOne;
    private Player playerTwo;
    private static Player[] players = new Player[2];
    private BattleshipClient gameControl;

    // Custom constructor for Battleship class that calls initComponents to 
    // set up the user interface
    public BattleshipUI()
    {
        initObjects();
        initComponents();
    }

    // layout the user interface
    private void initComponents()
    {	
        this.setTitle("Battleship");		
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 500));
        this.setMinimumSize(new Dimension(500, 500));
        
        //create menubar, menu, submenu, and menuitems
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        gameMenu.setMnemonic('G');
        optionMenu = new JMenu("Options");
        optionMenu.setMnemonic('O');
        menuBar.add(gameMenu);
        menuBar.add(optionMenu);
        this.setJMenuBar(menuBar);

        //game menu
        gameListener = new GameListener();
        
        playerPlayer = new JMenuItem("Player vs. Player");		
        playerPlayer.addActionListener(gameListener);
        playerPlayer.setEnabled(false);
        gameMenu.add(playerPlayer);

        playerComputer = new JMenuItem("Player vs. Computer");
        playerComputer.addActionListener(gameListener);
        playerComputer.setSelected(true);
        gameMenu.add(playerComputer);

        computerComputer = new JMenuItem("Computer vs. Computer");
        computerComputer.addActionListener(gameListener);
        computerComputer.setEnabled(false);
        gameMenu.add(computerComputer);

        exit = new JMenuItem("Exit");
        exit.addActionListener(new ExitListener());
        gameMenu.add(exit);	
        
        // options menu 
        optionListener = new OptionsListener();
        
        game = new JMenuItem("Game Options");
        game.addActionListener(optionListener);		
        optionMenu.add(game);
        
        player = new JMenuItem("Player Options");
        player.addActionListener(optionListener);		
        optionMenu.add(player);
        
        // buttons
        deploy = new JButton("Deploy Ships");
        deploy.setEnabled(false);
        deploy.addActionListener(new DeployListener());
               
        shipCb = new JComboBox();

        for(String ship : ships)
        {
            shipCb.addItem(ship);   
        }        
        
        shipCb.addActionListener(new ShipListener());
        shipCb.setSelectedIndex(0);
        
        directionCb = new JComboBox(direction);
        directionCb.addActionListener(new DirectionListener());
        directionCb.setSelectedIndex(0);
        
        // set up the ship layout panel
        shipLayoutPanel = new JPanel(new FlowLayout());
        shipLayoutPanel.setBorder(BorderFactory.createTitledBorder("Select Ship and Direction"));
        shipLayoutPanel.add(shipCb);
        shipLayoutPanel.add(directionCb);
        shipLayoutPanel.add(deploy);
        
        setupPlayerPanels();       

        // Status panel setup
        statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createTitledBorder("Game Status"));
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(180, 350));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        statusPanel.add(scrollPane);

        this.add(shipLayoutPanel, BorderLayout.NORTH);
        this.add(playerOnePanel, BorderLayout.WEST);
        this.setVisible(true);
    }	
	
    private void setupPlayerPanels()
    {
        // set up Player 1 display
        playerOnePanel = new JPanel(new GridLayout(11, 11));
        playerOnePanel.setMinimumSize(new Dimension(400, 400));
        playerOnePanel.setPreferredSize(new Dimension(400, 400));
        playerOnePanel.setBorder(BorderFactory.createTitledBorder("Player One"));
        
        // set up Player 2 display
        playerTwoPanel = new JPanel(new GridLayout(11, 11));
        playerTwoPanel.setMinimumSize(new Dimension(400, 400));
        playerTwoPanel.setPreferredSize(new Dimension(400, 400));
        playerTwoPanel.setBorder(BorderFactory.createTitledBorder("Player Two"));
        
        // Get player one's JButton array
        JButton[][] playerOneArray = playerOne.getBoard();
        
        // The GridLayout is 11 x 11 but the JButton array is only 10 x 10
        // need to account for the first row being the numbers 1 - 10
        // and the first column being letters A - J
        for(int row = 0; row <= playerOne.getRows(); row++)
        {
            for(int col = 0; col <= playerOne.getCols(); col++)
            {
                // The first row displays the numbers
                if(row == 0)
                {
                    JLabel colLabel = new JLabel(columnNumbers[col], SwingConstants.CENTER);
                    colLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    playerOnePanel.add(colLabel);
                }
                // now have to consider the first col is a letter for each row > 0
                else if(row > 0 && col == 0)
                {
                    JLabel rowLabel = new JLabel(rowLetters[row], SwingConstants.CENTER);
                    rowLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    playerOnePanel.add(rowLabel);
                }
                else
                {
                    playerOnePanel.add(playerOneArray[row - 1][col - 1]);
                }
            }
        }
        
        JButton[][] playerTwoArray = new JButton[10][10];
        playerTwoArray = playerTwo.getBoard();
        
        for(int row = 0; row <= playerTwo.getRows(); row++)
        {
            for(int col = 0; col <= playerTwo.getCols(); col++)
            {
                // The first row displays the numbers
                if(row == 0)
                {
                    JLabel colLabel = new JLabel(columnNumbers[col], SwingConstants.CENTER);
                    colLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    playerTwoPanel.add(colLabel);
                }
                // now have to consider the first col is a letter for each row > 0
                else if(row > 0 && col == 0)
                {
                    JLabel rowLabel = new JLabel(rowLetters[row], SwingConstants.CENTER);
                    rowLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    playerTwoPanel.add(rowLabel);
                }
                else
                {
                    playerTwoPanel.add(playerTwoArray[row - 1][col - 1]);
                }
            }
        }
        
    }
       
    private void initObjects()
    {        
        // Initialize the player objects and add them to the array
        playerOne = new Player("Player 1", this);
        playerTwo = new Player("Player 2", this);
        
        players[Constants.PLAYER_ONE]= playerOne;
        players[Constants.PLAYER_TWO]= playerTwo;
        
        for(Player player : players)
        {
            System.out.println("Player " + player.getUserName() + " is playing in the game");
        }
        
        players[Constants.PLAYER_ONE].setPlayMode(Constants.HUMAN);
        players[Constants.PLAYER_TWO].setPlayMode(Constants.COMPUTER);
        
        if(players[Constants.PLAYER_ONE].getPlayMode() == Constants.COMPUTER)
        {
            players[Constants.PLAYER_ONE].autoLayout();
        }
        
        if(players[Constants.PLAYER_TWO].getPlayMode() == Constants.COMPUTER)
        {
            players[Constants.PLAYER_TWO].autoLayout();
        }

        gameControl = new BattleshipClient(players, this);
    }
    
    private JFrame getThisParent()
    {
        return this;
    }
    
    public static void setDeployEnabled(boolean deployed) 
    {
        deploy.setEnabled(deployed);
    }    // exit menu item selected
    
    public void updateTextArea(String data)
    {
        textArea.append(data + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
    private class ExitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            int response = JOptionPane.showConfirmDialog(null, "Confirm to exit Battleship?", 
                    "Exit?", JOptionPane.YES_NO_OPTION);
            
            if (response == JOptionPane.YES_OPTION)
                System.exit(0);	
        }	
    }

    //listener for game menu selection		
    private class GameListener implements ActionListener
    {	
        public void actionPerformed(ActionEvent e)
        {
            if(e.getActionCommand().equals("Player vs. Player"))                   
            {
                players[Constants.PLAYER_ONE].setPlayMode(Constants.HUMAN);
                players[Constants.PLAYER_TWO].setPlayMode(Constants.HUMAN);
            }
            else if(e.getActionCommand().equals("Player vs. Computer"))
            {
                players[Constants.PLAYER_ONE].setPlayMode(Constants.HUMAN);
                players[Constants.PLAYER_TWO].setPlayMode(Constants.COMPUTER);   
            }
            else if(e.getActionCommand().equals("Computer vs. Computer"))
            {
                players[Constants.PLAYER_ONE].setPlayMode(Constants.COMPUTER);
                players[Constants.PLAYER_TWO].setPlayMode(Constants.COMPUTER);
            }
        }	
    }	
	
    //Listener for Options menu
    private class OptionsListener implements ActionListener
    {	
        GameOptionDialog gameOptions;
        PlayerOptionDialog playerOptions;

        public void actionPerformed(ActionEvent e) 
        {
            if(e.getActionCommand().equals("Game Options"))
                gameOptions = new GameOptionDialog(getThisParent(), players);
            else if(e.getActionCommand().equals("Player Options"))
                playerOptions = new PlayerOptionDialog(getThisParent(), players);
        }
    }	

    private class ShipListener implements ActionListener
    {	
        public void actionPerformed(ActionEvent e) 
        {
            int index = shipCb.getSelectedIndex();
            
            if(shipCb.getSelectedItem().equals("Carrier") )
                playerOne.setCurrentShip(Constants.CARRIER);
            else if (shipCb.getSelectedItem().equals("Battleship"))
                playerOne.setCurrentShip(Constants.BATTLESHIP);
            else if (shipCb.getSelectedItem().equals("Submarine"))
                playerOne.setCurrentShip(Constants.SUBMARINE);
            else if (shipCb.getSelectedItem().equals("Destroyer"))
                playerOne.setCurrentShip(Constants.DESTROYER);
            else if (shipCb.getSelectedItem().equals("Patrol Boat"))
                playerOne.setCurrentShip(Constants.PATROL_BOAT);
        }	
    }	

    private class DirectionListener implements ActionListener
    {	
        public void actionPerformed(ActionEvent e) 
        {
            if(directionCb.getSelectedItem().equals("Horizontal") )
                playerOne.setCurrentDirection(Constants.HORIZONTAL);
            else if (directionCb.getSelectedItem().equals("Vertical"))
                playerOne.setCurrentDirection(Constants.VERTICAL);
        }	
    }	
    
    
    private class DeployListener implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            getThisParent().setMinimumSize(new Dimension(1000, 500));
            getThisParent().add(statusPanel, BorderLayout.CENTER);
            getThisParent().add(playerTwoPanel, BorderLayout.EAST);
            
            updateTextArea("Let's Play!");
            
            setDeployEnabled(false);
            shipCb.setEnabled(false);
            directionCb.setEnabled(false);
            
            gameControl.play();
        }
    }
}
