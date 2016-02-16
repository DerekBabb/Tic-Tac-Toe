/**
 * Setup class is used to list the possible AI players for Tic-Tac-Toe
 * This class will initalize the players and will be accessed by the TicTacToe.java program
 * for a list of potential players.
 */


public class Setup
{
  private static PlayerTTT[] players;
  public static void initalizePlayers()
  {
    players = new PlayerTTT[2];          //Modify the number of players you have
    players[0] = new ExampleAI();        //The right side should be the AI you've created.  
    players[1] = new HumanPlayer();      //Your AI must extend a PlayerTTT.
    
  }
  
  public static PlayerTTT[] getPlayers()
  {
    return players;
  }
}