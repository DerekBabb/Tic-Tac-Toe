/*
 * Author: Derek Babb
 * Project: Tic-Tac-Toe
 * Purpose: The goal of this project is to create an AI for the game of Tic-Tac-Toe
 * To begin, extend a PlayerTTT and overwrite the abstract methods.
 * Finally, add your class to the Setup.java file.  Your player will be avaliable when you run TicTacToe.java
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;


public class TicTacToe extends JPanel implements ActionListener, MouseListener
{
  public static int clickSpot;
  private char[] game = new char[9];
  private boolean active;
  private String winner;
  private int width, height;
  private Image imgX, imgO;
  private PlayerTTT[] players, players2;
  private PlayerTTT p1, p2;
  private boolean GUI;
  private JRadioButtonMenuItem[] player1Options;
  private JRadioButtonMenuItem[] player2Options;
  private int gamesLeft;
  private int delay;
  private int p1Wins, p2Wins, ties;
  private JLabel statsLabel;
  
  public TicTacToe()
  {
    clickSpot = -1;
    imgX = new ImageIcon("images/cross.gif").getImage();
    imgO = new ImageIcon("images/not.gif").getImage();
    width = 600;
    height = 750;
    winner = " ";
    active = false;
    for (int i = 0; i < 9; i++)
      game[i] = ' ';
    Setup.initalizePlayers();
    players = Setup.getPlayers();
    Setup.initalizePlayers();
    players2 = Setup.getPlayers();
    
    setupGUI();
  }
  
  public void setupGUI()
  {
    JFrame jf = new JFrame("Tic-Tac-Toe");
    Container cp = jf.getContentPane();
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf.setSize(width, height);
    jf.setLocationRelativeTo(null);
    cp.setLayout(null);
    //jf.setResizable(false);
    JMenuBar menuBar = new JMenuBar();
    JMenu player1Menu = new JMenu("Player 1");
    JMenu player2Menu = new JMenu("Player 2");
    player1Options = new JRadioButtonMenuItem[players.length];
    player2Options = new JRadioButtonMenuItem[players.length];
    ButtonGroup player1Group = new ButtonGroup();
    ButtonGroup player2Group = new ButtonGroup();
    
    for(int i = 0; i < player1Options.length; i++)
    {
      player1Options[i] = new JRadioButtonMenuItem(players[i].getName(), i==0);
      player2Options[i] = new JRadioButtonMenuItem(players[i].getName(), i==0);
      player1Menu.add(player1Options[i]);
      player1Group.add(player1Options[i]);
      player2Menu.add(player2Options[i]);
      player2Group.add(player2Options[i]);
    }
    
    JMenu simulateMenu = new JMenu("Simulate");
    JMenuItem reset = new JMenuItem("Reset Stats");
    JMenuItem oneGame = new JMenuItem("One Game");
    JMenuItem tenGames = new JMenuItem("Ten Games");
    JMenuItem hundredGames = new JMenuItem("100 Games");
    JMenuItem thousandGames = new JMenuItem("1000 Games");
    JMenuItem tenThousandGames = new JMenuItem("10,000 Games");
    JMenuItem tournament = new JMenuItem("Tournament");
    
    simulateMenu.add(reset);
    simulateMenu.addSeparator();
    simulateMenu.add(oneGame);
    simulateMenu.add(tenGames);
    simulateMenu.add(hundredGames);
    simulateMenu.add(thousandGames);
    simulateMenu.add(tenThousandGames);
    simulateMenu.addSeparator();
    simulateMenu.add(tournament);
    
    menuBar.add(player1Menu);
    menuBar.add(player2Menu);
    menuBar.add(simulateMenu);
    
    jf.setJMenuBar(menuBar);
    
    statsLabel = new JLabel("", JLabel.CENTER);
    this.setBounds(0,0,width,width);
    statsLabel.setBounds(0,width,width,50);
    cp.add(this);
    cp.add(statsLabel);
    jf.setVisible(true);
    
    
    reset.addActionListener(this);
    oneGame.addActionListener(this);
    tenGames.addActionListener(this);
    hundredGames.addActionListener(this);
    thousandGames.addActionListener(this);
    tenThousandGames.addActionListener(this);
    tournament.addActionListener(this);
    width = this.getWidth();
    height = this.getHeight();
    
    cp.addMouseListener(this);
  }
  
  public void paintComponent(Graphics g)
  {
    // if(GUI)
    {
      super.paintComponent(g);
      setBackground(Color.BLACK);
      int dx = width/3;
      int dy = (height-24)/3;
      g.setColor(Color.WHITE);
      //draw Grid
      for(int x = 1; x < 3; x ++)
      {
        int xPos = x * dx;
        g.drawLine(xPos, 0, xPos, height);
      }
      
      for(int y = 1; y < 3; y ++)
      {
        int yPos = y * dy;
        g.drawLine(0, yPos, width, yPos);
      }
      
      for (int i = 0; i < 9; i++)
      {
        if(game[i] == 'X')
        {
          g.drawImage(imgX, i%3 * dx, i/3 * dy, dx, dy, this);
        }
        else if(game[i] == 'O')
        {
          g.drawImage(imgO, i%3 * dx, i/3 * dy, dx, dy, this);
        }
        
      }
    }
    
  }
  
  public void setStats()
  {
    String statsText = "X - " + p1.getName() + ": " + p1Wins + "     ";
    statsText += "O - " + p2.getName() + ": " + p2Wins + "     ";
    statsText += "Ties: "+ ties;
    
    statsLabel.setText(statsText);
  }
  
  public void drawBoard()
  {
    System.out.println("_"+game[0]+"_|_"+game[1]+"_|_"+game[2]+"_");
    System.out.println("_"+game[3]+"_|_"+game[4]+"_|_"+game[5]+"_");
    System.out.println(" "+game[6]+" | "+game[7]+" | "+game[8]+" ");
    System.out.println();
  }
  
  public boolean playTurn(int spot, char player)
  {
    if (game[spot] != ' ')
      return false;
    game[spot] = player;
    checkTie();
    checkWin();
    repaint();
    return true;
  }
  
  public void checkWin()
  {
    for (int i = 0; i < 3; i++)
    {
      //across
      if (game[i*3] != ' ' && game[i*3] == game[i*3 + 1] && game[i*3 + 1] == game[i*3 + 2])
      {
        winner = game[i*3] + "";
        active = false;
        
      }
      //up & down
      if (game[i] != ' ' && game[i] == game[i + 3] && game[i + 3] == game[i + 6])
      {
        winner = game[i] + "";
        active = false;
        
      }
      
    }
    //diagonal
    if (game[0] != ' ' && game[0] == game[4] && game[4] == game[8])
    {
      winner = game[0] + "";
      active = false;
    }
    if (game[2] != ' ' && game[2] == game[4] && game[4] == game[6])
    {
      winner = game[2] + "";
      active = false;
    }
    
  }
  
  public void checkTie()
  {
    boolean tie = true;
    for(int i = 0; i < 9; i++)
    {
      if (game[i] == ' ')
        tie = false;
    }
    if (tie)
    {
      winner = "TIE";
      active = false;
    }
    
  }
  
  public String getWinner()
  {
    return winner;
  }
  
  public char[] getGame()
  {
    return game;
  }
  
  public boolean activeGame()
  {
    return active;
  }
  
  public void play()
  {
    boolean playerOne = true;
    //if(GUI)
    //setupGUI();
    while(true)
    {
      
      repaint();
      
      if(activeGame())
      {
        clickSpot=-1;
        setStats();
        if (GUI)
        {
          try{Thread.sleep(delay);}
          catch (Exception e){}
        }
        
        char[] tempBoard = new char[9];
        for(int i = 0; i < 9; i++)
          tempBoard[i] = game[i];
        
        int count = 0;
        if (playerOne)
        {
          while(!playTurn(p1.play(tempBoard), p1.getLetter()) && count < 3)
          {
            //System.out.println("Invalid move.");
            count++;
          }
          playerOne = false;
        }
        else
        {
          while(!playTurn(p2.play(tempBoard), p2.getLetter()) && count < 3)
          {
            //System.out.println("Invalid move.");
            count++;
          }
          playerOne = true;
        }
        if(!active)
        {
          if(getWinner().equals("X"))
          {
            p1Wins++;
          }
          else if(getWinner().equals("O"))
          {
            p2Wins++;
          }
          else
          {
            ties++;
          }
          gamesLeft--;
          setStats();
        }
      }
      if(!active && gamesLeft > 0)
      {
        active = true;
        for (int i = 0; i < 9; i++)
          game[i] = ' ';
      }
    }
  }
  
  
  public static void main(String[] args)
  {
    
    TicTacToe g = new TicTacToe();
    g.play();
    
  }
  
  
  public void actionPerformed(ActionEvent e)
  {
    for(int i = 0; i < players.length; i++)
    {
      if(player1Options[i].isSelected())
      {
        p1=players[i];
      }
      if(player2Options[i].isSelected())
      {
        p2 = players2[i];
      }
      
    }
    
    if(e.getActionCommand().equals("Reset Stats"))
    {
      System.out.println("RESET");
      resetStats();
    }
    
    if(e.getActionCommand().equals("One Game"))
    {
      System.out.println("GAME ON");
      gamesLeft = 1;
      for (int i = 0; i < 9; i++)
        game[i] = ' ';
      p1.setLetter('X');
      p2.setLetter('O');
      GUI = true;
      active = true;
      delay = 500;
    }
    if(e.getActionCommand().equals("Ten Games"))
    {
      System.out.println("GAME ON");
      gamesLeft = 10;
      for (int i = 0; i < 9; i++)
        game[i] = ' ';
      p1.setLetter('X');
      p2.setLetter('O');
      GUI = true;
      active = true;
      delay = 10;
    }    
    if(e.getActionCommand().equals("100 Games"))
    {
      System.out.println("GAME ON");
      gamesLeft = 100;
      for (int i = 0; i < 9; i++)
        game[i] = ' ';
      p1.setLetter('X');
      p2.setLetter('O');
      GUI = false;
      active = true;
      delay = 1;
    }    
    
    if(e.getActionCommand().equals("1000 Games"))
    {
      System.out.println("GAME ON");
      gamesLeft = 1000;
      for (int i = 0; i < 9; i++)
        game[i] = ' ';
      p1.setLetter('X');
      p2.setLetter('O');
      GUI = false;
      active = true;
      delay = 0;
    }    
    
    if(e.getActionCommand().equals("10,000 Games"))
    {
      System.out.println("GAME ON");
      gamesLeft = 10000;
      for (int i = 0; i < 9; i++)
        game[i] = ' ';
      p1.setLetter('X');
      p2.setLetter('O');
      GUI = false;
      active = true;
      delay = 0;
    }
    
    if(e.getActionCommand().equals("Tournament"))
    {
      playTournament();
    }
  }
  
  public void resetStats()
  {
    p1Wins=0;
    p2Wins=0;
    ties = 0;
    setStats();
  }
  
  public void playTournament()
  {
    PrintWriter fout = null;
    try{
      fout = new PrintWriter(new File("tournamentResults.csv"));
    }
    catch (Exception e)
    {
      System.out.println("Tournament file failed.");
    }
    String resultHeader = ",";
    String resultHeader2 = "";
    String resultTable = "";
    for(int player1 = 0; player1 < players.length; player1++)
    {
      if (players[player1].getName().equals("Human"))
        player1++;
      resultHeader += players[player1].getName() +", , ,";
      resultHeader2 +=",Wins,Losses,Ties";
      resultTable += players[player1].getName();
      for(int player2 = 0; player2 < players2.length; player2++)
      {
        if (players2[player2].getName().equals("Human"))
          player2++;
        p1 = players[player1];
        p2 = players2[player2];
        resetStats();
        gamesLeft = 1000;
        for (int i = 0; i < 9; i++)
          game[i] = ' ';
        p1.setLetter('X');
        p2.setLetter('O');
        GUI = false;
        active = true;
        delay = 0;
        while(gamesLeft >0);
        
        try{Thread.sleep(10);}
        catch(Exception e){};
        resultTable += ", " + p2Wins + ", " + p1Wins + ", " + ties;
      }
      resultTable += "\n";
    }
    fout.println(resultHeader);
    fout.println(resultHeader2);
    fout.println(resultTable);
    fout.close();
    
    System.out.println("Tournament results ready in TournamentResults.csv");
  }
  
  public void mouseClicked(MouseEvent e)
  {
    int squareSize = width/3;
    clickSpot = e.getX()/squareSize + e.getY()/squareSize * 3;
    System.out.println(clickSpot);
  }
  
  public void mousePressed(MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
}