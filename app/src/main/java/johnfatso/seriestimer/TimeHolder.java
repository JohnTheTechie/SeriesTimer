package johnfatso.seriestimer;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * TimeHolder for representing the timers in a structured object form
 * Created by user on 07-09-2017.
 */

class TimeHolder {

    private static int a;

     class TimeItem{
        int minute,second;

        public TimeItem(){
            this.minute=0;
            this.minute=0;
        }

        TimeItem(int minute, int second){
            this.minute=minute;
            this.second=second;
        }

        TimeItem(long milliseconds){
            this.minute=(int)(milliseconds/60000);
            this.second=(int)((milliseconds/1000)%60);
        }

        TimeItem(int totalSeconds){
            this.minute=ConversionManager.totalSecondsToMinute(totalSeconds);
            this.second=ConversionManager.totalSecondsToSeconds(totalSeconds);
        }

        int getTotalSeconds(){
            int totalSeconds=0;
            totalSeconds+=this.minute*60;
            totalSeconds+=this.second;
            return totalSeconds;
        }


    }


    private String nameOfTheTimer;
    private int numberOfCycles;
    private TimeItem[] timeItem;

    /*
    *Constructors definitions
    */

    TimeHolder(){
        a=a+1;
        this.nameOfTheTimer="empty"+a;
        this.numberOfCycles=0;
        this.timeItem=new TimeItem[1];
        this.timeItem[0]=new TimeItem(0,0);
    }

    public TimeHolder(String nameOfTheTimer, int numberOfCycles, TimeItem[] timeItems){
        this.nameOfTheTimer=nameOfTheTimer;
        this.numberOfCycles=numberOfCycles;
        setTimeItem(timeItems);
    }

    public TimeHolder(String nameOfTheTimer, int numberOfCycles, int[] timeItems){
        this.nameOfTheTimer=nameOfTheTimer;
        this.numberOfCycles=numberOfCycles;
        setTimeItem(timeItems);
    }

    TimeHolder(String nameOfTheTimer, int numberOfCycles, byte[] timeItems){
        this.nameOfTheTimer=nameOfTheTimer;
        this.numberOfCycles=numberOfCycles;
        setTimeItem(timeItems);
    }

    TimeHolder(String nameOfTheTimer, int numberOfCycles, long[] timeItems){
        this.nameOfTheTimer=nameOfTheTimer;
        this.numberOfCycles=numberOfCycles;
        setTimeItem(timeItems);
    }

    public void finalize() throws java.lang.Throwable{
        a=a-1;
        try{
            super.finalize();
        }catch (java.lang.Throwable e){
            Log.v("JJT","Exception in TimeHolder object finalization");
        }
    }

    //set get functions

    public void setNameOfTheTimer(String nameOfTheTimer) {
        this.nameOfTheTimer = nameOfTheTimer;
    }

    public void setNumberOfCycles(int numberOfCycles) {
        this.numberOfCycles = numberOfCycles;
    }

    public void setTimeItem(int minute, int second) {
        this.timeItem[this.timeItem.length] = new TimeItem(minute,second);
    }

    public void setTimeItem(long milliseconds) {
        this.timeItem[this.timeItem.length] = new TimeItem(milliseconds);
    }

    public void setTimeItem(int totalSeconds) {
        this.timeItem[this.timeItem.length] = new TimeItem(totalSeconds);
    }

    void setTimeItem(byte[] byteArray){
        ByteBuffer byteBuffer=ByteBuffer.wrap(byteArray);
        IntBuffer intBuffer=IntBuffer.allocate(byteBuffer.array().length/4);
        intBuffer.put(byteBuffer.asIntBuffer());
        this.timeItem=new TimeItem[byteBuffer.array().length/8];
        for(int i=0;i<intBuffer.array().length/2;i++){
            this.timeItem[i]=new TimeItem(intBuffer.array()[i*2],intBuffer.array()[i*2+1]);
        }
    }

    void setTimeItem(int[] intArray){
        this.timeItem=new TimeItem[intArray.length/2];
        for(int i=0;i<intArray.length/2;i++){
            this.timeItem[i]=new TimeItem(intArray[i*2],intArray[i*2+1]);
        }
    }

    void setTimeItem(long[] longArray){
        this.timeItem=new TimeItem[longArray.length];
        for(int i=0;i<longArray.length;i++){
            this.timeItem[i]=new TimeItem(longArray[i]);
        }
    }

    void setTimeItem(TimeItem[] timeItem) {
        this.timeItem = timeItem;
    }

    String getNameOfTheTimer() {
        return nameOfTheTimer;
    }

    int getNumberOfCycles() {
        return numberOfCycles;
    }

    public TimeItem[] getTimeItem() {
        return timeItem;
    }

    byte[] getTimeItemAsByteArray() {
        int[] intArray=getTimeItemAsIntArray();
        return ConversionManager.convertIntArrayToByteArray(intArray);
                /*new int[this.timeItem.length*2];
        for (int i=0;i<this.timeItem.length;i++){
            intArray[i*2]=this.timeItem[i].minute;
            intArray[i*2+1]=this.timeItem[i].second;
        }
        ByteBuffer byteBuffer=ByteBuffer.allocate(intArray.length*4);
        IntBuffer intBuffer=byteBuffer.asIntBuffer();
        intBuffer.put(intArray);*/


    }

    public int[] getTimeItemAsIntArray(){
        int[] milliseconds=new int[this.timeItem.length];
        for(int i=0;i<this.timeItem.length;i++){
            milliseconds[i]=this.timeItem[i].getTotalSeconds();
        }
        return milliseconds;
    }


}
