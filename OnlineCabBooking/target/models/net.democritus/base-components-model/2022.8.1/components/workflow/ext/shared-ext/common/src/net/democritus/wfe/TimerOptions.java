package net.democritus.wfe;

// @feature:engine-service
public class TimerOptions {
  //delayTime in seconds
  private double delayTime;


  public TimerOptions(){
    this.delayTime = 10L;
  }

  public double getDelayTime() {
    return delayTime;
  }

  public void setDelayTime(double delayTime) {
    this.delayTime = delayTime;
  }
}
