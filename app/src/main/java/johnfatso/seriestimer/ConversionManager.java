package johnfatso.seriestimer;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

/**
 * Created by janakiraman on 20-09-2017.
 * This class should be used to define all kind of generic functions related to data conversions
 */

class ConversionManager {
    private static final int intLength=4,longLength=8;
    
    static byte[] convertLongArrayToByteArray(long[] longArray){
        ByteBuffer byteBuffer=ByteBuffer.allocate(longArray.length*longLength);
        LongBuffer longBuffer=byteBuffer.asLongBuffer();
        longBuffer.put(longArray);

        return byteBuffer.array();
    }

    static byte[] convertIntArrayToByteArray(int[] intArray){
        for(int a: intArray){
            Log.v("JJT","integer "+a);
        }

        ByteBuffer byteBuffer=ByteBuffer.allocate(intArray.length*intLength);
        IntBuffer longBuffer=byteBuffer.asIntBuffer();
        longBuffer.put(intArray);

        return byteBuffer.array();
    }

    static long[] convertByteArrayToLongArray(byte[] byteArray){
        long[] longArray=new long[byteArray.length/longLength];
        for(int i=0;i<byteArray.length/longLength;i++){
            longArray[i]=bytesToLong(byteArrayToLongSizedArray(byteArray,i));
        }
        return longArray;
    }

    private static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(longLength);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    private static byte[] byteArrayToLongSizedArray(byte[] byteArray, int index){
        byte[] array=new byte[longLength];
        for(int i=0;i<longLength;i++){
            array[i]=byteArray[(index*longLength)+i];
        }
        //System.arraycopy(byteArray,index*this.longLength,array,0,this.longLength);
        return array;
    }

    static int[] convertByteArrayToIntArray(byte[] byteArray){
        int[] intArray=new int[byteArray.length/intLength];
        for(int i=0;i<byteArray.length/intLength;i++){
            intArray[i]=bytesToInt(byteArrayToIntSizedArray(byteArray,i));
        }
        return intArray;
    }

    private static int bytesToInt(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(longLength);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getInt();
    }

    private static byte[] byteArrayToIntSizedArray(byte[] byteArray, int index){
        byte[] array=new byte[intLength];
        for(int i=0;i<intLength;i++){
            array[i]=byteArray[(index*intLength)+i];
        }
        //System.arraycopy(byteArray,index*this.longLength,array,0,this.longLength);
        return array;
    }

    public static int totalSecondsToMinute(int seconds){
        return seconds/60;
    }

    public static int totalSecondsToSeconds(int seconds){
        return seconds%60;
    }
}
