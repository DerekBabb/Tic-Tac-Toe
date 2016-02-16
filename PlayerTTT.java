public abstract class PlayerTTT
{
 private char letter;
 private String name;
 
 public PlayerTTT(String name)
 {
   this.name = name;
 }
 
 public void setName(String name)
 {
   this.name = name;
 }
 
 public void setLetter(char letter)
 {
   this.letter = letter;
 }

 public char getLetter()
 {
  return this.letter;
 }
 
 public String getName()
 {
   return this.name;
 }

 public abstract int play(char[] game);
 
}