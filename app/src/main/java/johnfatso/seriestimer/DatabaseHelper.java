package johnfatso.seriestimer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Database helper
 * Created by janakiraman on 03-09-2017.
 */

class DatabaseHelper extends SQLiteOpenHelper{

    //timer table details
    static final String DB_NAME="trialDB";
    private static final int DB_VERSION=1;

    //Timer cycle details Column name definitions
    static final String DB_CYCLE_NAME="cycleName";
    static final String DB_CYCLE_COUNT="cycleCount";
    static final String DB_CYCLE_DESCRIPTION="cycleDescription";

    //initialize the database helper. The function shall check the device for the existing DB and takes decision whether to call onCreate or onUpgrade
    DatabaseHelper(Context context){
        super(context, DB_NAME, null,DB_VERSION);
    }

    //this function is called when the intended db is not already available in the installed device
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+DB_NAME+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+DB_CYCLE_NAME+" TEXT, "+DB_CYCLE_COUNT+" INTEGER, "+DB_CYCLE_DESCRIPTION+" BLOB);");
        insertDataToDB(db, "Default1", 4, new int[] {4,10,1,23});
        insertDataToDB(db, "Default2", 3, new int[] {6,59,2,12});
        insertDataToDB(db, "Default3", 1, new int[] {1,10,0,10});
    }

    //this function is called when there is already a old version of the DB is available in the device and need to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }

    //this function should be called when a complete new record has to be created or handled
    public static   ContentValues createContentValuesForCompleteRecord(String cycleName, int cycleCount, int[] cycleDescription){
        byte[] cycleDescriptionByteArray=convertIntArrayToByteArray(cycleDescription);

        ContentValues contentValues=new ContentValues();
        contentValues.put(DB_CYCLE_NAME,cycleName);
        contentValues.put(DB_CYCLE_COUNT,cycleCount);
        contentValues.put(DB_CYCLE_DESCRIPTION,cycleDescriptionByteArray);

        return contentValues;
    }

    public static   ContentValues createContentValuesForCompleteRecord(String cycleName, int cycleCount, byte[] cycleDescription){


        ContentValues contentValues=new ContentValues();
        contentValues.put(DB_CYCLE_NAME,cycleName);
        contentValues.put(DB_CYCLE_COUNT,cycleCount);
        contentValues.put(DB_CYCLE_DESCRIPTION,cycleDescription);

        return contentValues;
    }

    public static byte[] convertIntArrayToByteArray(int[] cycleDescriptionArray){
        /*ByteBuffer byteBuffer=ByteBuffer.allocate(cycleDescriptionArray.length*4);
        IntBuffer intBuffer=byteBuffer.asIntBuffer();
        intBuffer.put(cycleDescriptionArray);

        return byteBuffer.array();*/

        return ConversionManager.convertIntArrayToByteArray(cycleDescriptionArray);
    }
    
    private ContentValues createContentValuesforCycleDescription(int[] cycleDescription){
        ContentValues contentValues=new ContentValues();

        byte[] cycleDescriptionByteArray=convertIntArrayToByteArray(cycleDescription);
        
        contentValues.put(DB_CYCLE_DESCRIPTION,cycleDescriptionByteArray);

        return contentValues;
    }

    private ContentValues createContentValuesForCycleCount(int cycleCount){
        ContentValues contentValues=new ContentValues();
        
        contentValues.put(DB_CYCLE_COUNT,cycleCount);
        
        return contentValues;
    }

    private ContentValues createContentValuesForCycleName(String cycleName){
        ContentValues contentValues=new ContentValues();
        
        contentValues.put(DB_CYCLE_NAME,cycleName);
        
        return contentValues;
    }

    //insert a record into the database
    public void insertDataToDB(SQLiteDatabase db, ContentValues timerCycleContentValues ){
        db.insert(DB_NAME, null, timerCycleContentValues);
    }

    public void insertDataToDB(SQLiteDatabase db, String newCycleName, int cycleCount, int[] cycleDescription){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DB_CYCLE_NAME, newCycleName);
        contentValues.put(DB_CYCLE_COUNT, cycleCount);
        contentValues.put(DB_CYCLE_DESCRIPTION, convertIntArrayToByteArray(cycleDescription));

        db.insert(DB_NAME, null, contentValues);
    }


    //update name of the cycle of an existing record
    public void updateCycleName(SQLiteDatabase db, String newCycleName, String oldCycleName){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DB_CYCLE_NAME,newCycleName);

        db.update(DB_NAME, contentValues, DB_CYCLE_NAME+" = ?", new String[]{oldCycleName});
    }

    public void updateCycleCount(SQLiteDatabase db, String cycleName, int cycleCount){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DB_CYCLE_COUNT,cycleCount);

        db.update(DB_NAME, contentValues, DB_CYCLE_NAME+" = ?", new String[] {cycleName});
    }

    public void updateCycleDefinition(SQLiteDatabase db, String cycleName, int[] cycleDefinition){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DB_CYCLE_DESCRIPTION, convertIntArrayToByteArray(cycleDefinition));

        db.update(DB_NAME, contentValues, DB_CYCLE_NAME+" = ?", new String[] {cycleName});
    }

    public void updateCompleteRecord(SQLiteDatabase db, String oldCycleName, String newCycleName, int cycleCount, int[] cycleDescription){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DB_CYCLE_NAME, newCycleName);
        contentValues.put(DB_CYCLE_COUNT, cycleCount);
        contentValues.put(DB_CYCLE_DESCRIPTION, convertIntArrayToByteArray(cycleDescription));

        db.update(DB_NAME, contentValues, DB_CYCLE_NAME+" = ?", new String[] {oldCycleName});
    }


    //delete record block
    public void deleteRecord(SQLiteDatabase db, String cycleName){
        db.delete(DB_NAME, DB_CYCLE_NAME+"= ?", new String[] {cycleName});
    }

    public void deleteAllRecords(SQLiteDatabase db){
        db.delete(DB_NAME,null,null);
    }

}