package net.democritus.wfe;

// expanded with version 2.1

import net.democritus.jndi.ComponentJNDI;
import net.democritus.sys.UserContext;

/**
 * TimerHandlerClient: client class to control the TimerHandlerBean
 */
// @feature:engine-service
public class TimerHandlerClient {
  public static void main(String args[]) {

    TimerHandlerAgent timerHandler = TimerHandlerAgent.getTimerHandlerAgent(UserContext.NO_USER_CONTEXT);

    // Check if there are valid args
    if ((args.length != 2) || (!(args[0].equals("start") || args[0].equals("stop")))) {
      System.err.println("Syntax is : java -cp ... net.democritus.wfe.TimerHandlerClient <start>/<stop> <engineServiceName>");
      System.exit(2);
    }
    String timerCommand = args[0];
    System.out.println("Timer command = " + timerCommand);
    String engineServiceName = args[1];
    System.out.println("Engine service name = " + engineServiceName);

    try {
      if (args[0].equals("start")) {
        System.out.println("start timer for " + engineServiceName);
        timerHandler.startTimer(engineServiceName);
      }
      else {
        System.out.println("stop timer for " + engineServiceName);
        timerHandler.stopTimer(engineServiceName);
      }
    }
    catch (Exception e) {
      System.err.println("Failed to control Timer : " + engineServiceName);
      e.printStackTrace();
    }
  }
}


