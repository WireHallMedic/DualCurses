package DualCurses;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class DCStringTest {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp(){}

   @Test public void newlineTest() 
   {
      DCString dcStr = new DCString("\n");
      Assert.assertTrue("Newline recognized", dcStr.isNewline());
      dcStr.setText("\n ");
      Assert.assertFalse("Newline with trailing not recognized", dcStr.isNewline());
      dcStr.setText("x");
      Assert.assertFalse("Non-newline not recognized", dcStr.isNewline());
   }

   @Test public void trailingSpaceTest() 
   {
      DCString dcStr = new DCString(" pants");
      Assert.assertFalse("No trailing space returns false", dcStr.hasTrailingSpace());
      Assert.assertTrue("No trailing space returns full text", dcStr.getTrimmedText().equals(" pants"));
      dcStr.setText("pants ");
      Assert.assertTrue("Trailing space returns true", dcStr.hasTrailingSpace());
      Assert.assertTrue("Trailing space returns trimmed text", dcStr.getTrimmedText().equals("pants"));
   }
}
