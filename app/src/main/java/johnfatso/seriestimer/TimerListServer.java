

package johnfatso.seriestimer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 Created by janakiraman on 08-09-2017.
 */

public class TimerListServer {
    private ArrayList<TimeHolder> timerList=new ArrayList<TimeHolder>();
    private SQLiteDatabase db;
    private Cursor cursor;

    public TimerListServer(SQLiteOpenHelper dbhelper){
        this.db=dbhelper.getWritableDatabase();
        this.cursor=this.db.query(DatabaseHelper.DB_NAME,new String[] {"_id",DatabaseHelper.DB_CYCLE_NAME, DatabaseHelper.DB_CYCLE_COUNT, DatabaseHelper.DB_CYCLE_DESCRIPTION},null,null,null,null,null);
        for(int i=0;i<cursor.getCount();i++){
            timerList.add(new TimeHolder(cursor.getString(1),cursor.getInt(2),cursor.getBlob(3)));
            cursor.moveToNext();
        }
    }

    public TimeHolder getTimer(int position){
        return timerList.get(position);
    }


}
