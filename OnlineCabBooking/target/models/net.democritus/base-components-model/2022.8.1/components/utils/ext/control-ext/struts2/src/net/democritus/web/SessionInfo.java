package net.democritus.web;

import java.io.Serializable;

//@feature:session-info
public class SessionInfo implements Serializable {

  private int timeOut;

  public int getTimeOut() {
    return timeOut;
  }

  public SessionInfo setTimeOut(int timeOut) {
    this.timeOut = timeOut;
    return this;
  }
}