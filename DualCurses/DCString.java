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

   public DCString(String str, int fg, int bg)
   {
      text = str;
      fgColor = fg;
      bgColor = bg;
   }

   public DCString(String str, int fg)
   {
      this(str, fg, Color.BLACK.getRGB());
   }

   public DCString(String str)
   {
      this(str, Color.WHITE.getRGB());
   }
   
   public int length()
   {
      return text.length();
   }
   
   public boolean isNewline()
   {
      return length() == 1 && text.charAt(0) == '\n';
   }
   
   public boolean hasTrailingSpace()
   {
      return length() > 0 && text.charAt(length() - 1) == ' ';
   }
   
   public String getTrimmedText()
   {
      if(hasTrailingSpace())
         return text.substring(0, length() - 1);
      return getText();
   }
}