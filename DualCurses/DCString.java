package DualCurses;

import java.awt.*;

public class DCString
{
   private String text;
	private int fgColor;
	private int bgColor;


	public String getText(){return text;}
	public int getFGColor(){return fgColor;}
	public int getBGColor(){return bgColor;}


	public void setText(String t){text = t;}
	public void setFGColor(int f){fgColor = f;}
	public void setBGColor(int b){bgColor = b;}

   // constructor for text, foreground color, background color
   public DCString(String str, int fg, int bg)
   {
      text = str;
      fgColor = fg;
      bgColor = bg;
   }

   // constructor for text, foreground color, default background color
   public DCString(String str, int fg)
   {
      this(str, fg, Color.BLACK.getRGB());
   }

   // constructor for text, default foreground color, default background color
   public DCString(String str)
   {
      this(str, Color.WHITE.getRGB());
   }
   
   // returns the length of the text. Semantically equivalent to this.text.length()
   public int length()
   {
      return text.length();
   }
   
   // returns true if this consists entirely of a newline character
   public boolean isNewline()
   {
      return length() == 1 && text.charAt(0) == '\n';
   }
   
   // returns true if the last character of text is a space
   public boolean hasTrailingSpace()
   {
      return length() > 0 && text.charAt(length() - 1) == ' ';
   }
   
   // if hasTrailingSpace returns text minus that space, else returns text
   public String getTrimmedText()
   {
      if(hasTrailingSpace())
         return text.substring(0, length() - 1);
      return getText();
   }
}