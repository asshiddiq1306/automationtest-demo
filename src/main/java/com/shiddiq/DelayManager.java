package com.shiddiq;

public class DelayManager {
    
    public static void delay(DelayTime delayTime){
        try {
            Thread.sleep(delayTime.getDelayTime());
        } catch (Exception e) {
            System.out.println("[delay_Exception] - " + e.getMessage());
        }
    }

    public enum DelayTime{
        SHORT(1000), MEDIUM(2000), LONG(10000);

        private long delayTime;
        
        DelayTime(long delayTime){
            this.delayTime = delayTime;
        }
        public long getDelayTime(){
            return this.delayTime;
        }
    }
}
