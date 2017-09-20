package johnfatso.seriestimer;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

/**
 * Created by janakiraman on 20-09-2017.
 * This class should be used to define all kind of generic functions related to data conversions
 */

class ConversionManager {
    static byte[] convertLongArrayToByteArray(long[] longArray){
        ByteBuffer byteBuffer=ByteBuffer.allocate(longArray.length*8);
        LongBuffer longBuffer=byteBuffer.asLongBuffer();
        longBuffer.put(longArray);

        return byteBuffer.array();
    }

    static long[] convertByteArrayToLongArray(byte[] byteArray){
        long[] longArray=new long[byteArray.length/8];
        for(int i=0;i<byteArray.length/8;i++){
            longArray[i]=bytesToLong(byteArrayToLongSizedArray(byteArray,i));
        }
        return longArray;
    }

    private static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    private static byte[] byteArrayToLongSizedArray(byte[] byteArray, int index){
        byte[] array=new byte[8];
        for(int i=0;i<8;i++){
            array[i]=byteArray[(index*8)+i];
        }
        return array;
    }
}
