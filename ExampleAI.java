public class ExampleAI extends PlayerTTT
{
  public ExampleAI()
  {
    super("Example AI");
  }
  
  public int play(char[] game)
  {
    int spot = (int)(Math.random()*9);
    while(game[spot] != ' ')
    {
      spot = (int)(Math.random()*9);
    }
    return spot;
  }
  
}
