import java.awt.*;
import javax.swing.*;

public class HumanPlayer extends PlayerTTT
{
  public HumanPlayer()
  {
    super("Human");
  }
  
  public int play(char[] game)
  {
    while(TicTacToe.clickSpot < 0 || TicTacToe.clickSpot > 8 || game[TicTacToe.clickSpot] != ' ')
    {
      try{Thread.sleep(10);}
      catch(Exception e){}
    }
    return TicTacToe.clickSpot;
  }
}