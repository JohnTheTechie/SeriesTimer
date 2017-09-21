package johnfatso.seriestimer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Created by user on 07-09-2017.
 */

public class TimeHolder {

    private int a;

    public class TimeItem{
        public int minute,second;

        public TimeItem(){
            this.minute=0;
            this.minute=0;
        }

        public TimeItem(int minute, int second){
            this.minute=minute;
            this.second=second;
        }

        public TimeItem(long milliseconds){
            this.minute=(int)(milliseconds/60000);
            this.second=(int)((milliseconds/1000)%60);
        }

        public long getMilliseconds(){
            long milliseconds=0;
            milliseconds+=this.minute*60000;
            milliseconds+=this.second*1000;
            return milliseconds;
        }
    }


    private String nameOfTheTimer;
    private int numberOfCycles;
    private TimeItem[] timeItem;

    /*
    *Constructors definitions
    */

    public TimeHolder(){
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

    public TimeHolder(String nameOfTheTimer, int numberOfCycles, byte[] timeItems){
        this.nameOfTheTimer=nameOfTheTimer;
        this.numberOfCycles=numberOfCycles;
        setTimeItem(timeItems);
    }

    public TimeHolder(String nameOfTheTimer, int numberOfCycles, long[] timeItems){
        this.nameOfTheTimer=nameOfTheTimer;
        this.numberOfCycles=numberOfCycles;
        setTimeItem(timeItems);
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

    public void setTimeItem(byte[] byteArray){
        ByteBuffer byteBuffer=ByteBuffer.wrap(byteArray);
        IntBuffer intBuffer=IntBuffer.allocate(byteBuffer.array().length/4);
        intBuffer.put(byteBuffer.asIntBuffer());
        this.timeItem=new TimeItem[byteBuffer.array().length/8];
        for(int i=0;i<intBuffer.array().length/2;i++){
            this.timeItem[i]=new TimeItem(intBuffer.array()[i*2],intBuffer.array()[i*2+1]);
        }
    }

    public void setTimeItem(int[] intArray){
        this.timeItem=new TimeItem[intArray.length/2];
        for(int i=0;i<intArray.length/2;i++){
            this.timeItem[i]=new TimeItem(intArray[i*2],intArray[i*2+1]);
        }
    }

    public void setTimeItem(long[] longArray){
        this.timeItem=new TimeItem[longArray.length];
        for(int i=0;i<longArray.length;i++){
            this.timeItem[i]=new TimeItem(longArray[i]);
        }
    }

    public void setTimeItem(TimeItem[] timeItem) {
        this.timeItem = timeItem;
    }

    public String getNameOfTheTimer() {
        return nameOfTheTimer;
    }

    public int getNumberOfCycles() {
        return numberOfCycles;
    }

    public TimeItem[] getTimeItem() {
        return timeItem;
    }

    public byte[] getTimeItemAsByteArray() {
        int[] intArray=new int[this.timeItem.length*2];
        for (int i=0;i<this.timeItem.length;i++){
            intArray[i*2]=this.timeItem[i].minute;
            intArray[i*2+1]=this.timeItem[i].second;
        }
        ByteBuffer byteBuffer=ByteBuffer.allocate(intArray.length*4);
        IntBuffer intBuffer=byteBuffer.asIntBuffer();
        intBuffer.put(intArray);

        return byteBuffer.array();
    }

    public long[] getTimeItemAsLongArray(){
        long[] milliseconds=new long[this.timeItem.length];
        for(int i=0;i<this.timeItem.length;i++){
            milliseconds[i]=this.timeItem[i].getMilliseconds();
        }
        return milliseconds;
    }


}
