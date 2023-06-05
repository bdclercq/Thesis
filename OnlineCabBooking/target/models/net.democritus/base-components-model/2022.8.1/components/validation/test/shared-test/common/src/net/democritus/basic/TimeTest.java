package net.democritus.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import net.democritus.valuetype.basic.Time;
import org.junit.Test;

public class TimeTest {

  @Test public void testGetValue()  {
    assertEquals("1:01", new Time(1, 1).getValue());
    assertEquals("12:34", new Time(12, 34).getValue());
  }

  @Test public void testSetValue()  {
    Time time = new Time();
    time.setValue("");
    assertEquals("0:00", time.getValue());

    time = new Time();
    time.setValue("  :  ");
    assertEquals("0:00", time.getValue());

    time = new Time();
    time.setValue(" 1 : 3 ");
    assertEquals("1:03", time.getValue());
  }

  @Test public void testSetValue_broken_input() {
    Time time = new Time();
    time.setValue(" A : 3 ");
    assertEquals("0:00", time.getValue());

    time = new Time();
    time.setValue(" 1 : A ");
    assertEquals("1:00", time.getValue());

    time = new Time();
    time.setValue(" 1 : 2 A ");
    assertEquals("1:00", time.getValue());

    time = new Time();
    time.setValue(" 1:03:04 ");
    assertEquals("1:03", time.getValue());
  }
}

