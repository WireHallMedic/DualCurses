package DualCurses;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;


public class DCTextAreaTest {


   /** Fixture initialization (common initialization for all tests). **/
   @Before public void setUp(){}

   @Test public void testStringSplitsSpaces() 
   {
      Vector<String> expected = new Vector<String>();
      expected.add("This ");
      expected.add("is ");
      expected.add("a ");
      expected.add("test ");
      expected.add("string");
      DCTextArea area = new DCTextArea(10, 10, 0, 0);
      DCString dcStr = new DCString("This is a test string");
      Vector<DCString> dcStrVect = area.split(dcStr);
      Assert.assertEquals("\nResult is expected size.", expected.size(), dcStrVect.size());
      
      Vector<String> results = new Vector<String>();
      for(int i = 0; i < dcStrVect.size(); i++)
      {
         results.add(dcStrVect.elementAt(i).getText());
      }
      for(int i = 0; i < expected.size(); i++)
         Assert.assertTrue(expected.elementAt(i).equals(results.elementAt(i)));
   }

   @Test public void testStringSplitsNewline() 
   {
      Vector<String> expected = new Vector<String>();
      expected.add("Before");
      expected.add("\n");
      expected.add("After");
      DCTextArea area = new DCTextArea(10, 10, 0, 0);
      DCString dcStr = new DCString("Before\nAfter");
      Vector<DCString> dcStrVect = area.split(dcStr);
      Assert.assertEquals("\nResult is expected size.", expected.size(), dcStrVect.size());
      
      Vector<String> results = new Vector<String>();
      for(int i = 0; i < dcStrVect.size(); i++)
      {
         results.add(dcStrVect.elementAt(i).getText());
      }
      for(int i = 0; i < expected.size(); i++)
         Assert.assertTrue(expected.elementAt(i).equals(results.elementAt(i)));
   }

   @Test public void testStringSplitsHyphen() 
   {
      Vector<String> expected = new Vector<String>();
      expected.add("Beginning-");
      expected.add("Middle-");
      expected.add("End");
      DCTextArea area = new DCTextArea(10, 10, 0, 0);
      DCString dcStr = new DCString("Beginning-Middle-End");
      Vector<DCString> dcStrVect = area.split(dcStr);
      Assert.assertEquals("\nResult is expected size.", expected.size(), dcStrVect.size());
      
      Vector<String> results = new Vector<String>();
      for(int i = 0; i < dcStrVect.size(); i++)
      {
         results.add(dcStrVect.elementAt(i).getText());
      }
      for(int i = 0; i < expected.size(); i++)
         Assert.assertTrue(expected.elementAt(i).equals(results.elementAt(i)));
   }

   @Test public void testStringSplitsAll() 
   {
      Vector<String> expected = new Vector<String>();
      expected.add("This ");
      expected.add("is-");
      expected.add("a");
      expected.add("\n");
      expected.add("test ");
      expected.add("string");
      DCTextArea area = new DCTextArea(10, 10, 0, 0);
      DCString dcStr = new DCString("This is-a\ntest string");
      Vector<DCString> dcStrVect = area.split(dcStr);
      Assert.assertEquals("\nResult is expected size.", expected.size(), dcStrVect.size());
      
      Vector<String> results = new Vector<String>();
      for(int i = 0; i < dcStrVect.size(); i++)
      {
         results.add(dcStrVect.elementAt(i).getText());
      }
      for(int i = 0; i < expected.size(); i++)
         Assert.assertTrue(expected.elementAt(i).equals(results.elementAt(i)));
   }
}
