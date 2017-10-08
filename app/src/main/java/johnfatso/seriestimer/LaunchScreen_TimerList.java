package johnfatso.seriestimer;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;

public class LaunchScreen_TimerList extends AppCompatActivity  {

    private SQLiteDatabase db;
    private Cursor cursor;
    private TimerListServer timerListServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen__timer_list);
        ListView listView=(ListView) findViewById(R.id.timerList);


        try{
            SQLiteOpenHelper databaseHelper=new DatabaseHelper(this);
            db=databaseHelper.getWritableDatabase();
            timerListServer=new TimerListServer(databaseHelper);
            /*
            TimeHolder timeHolder=new TimeHolder();
            timeHolder.setNameOfTheTimer("testTimer");
            timeHolder.setNumberOfCycles(10);
            timeHolder.setTimeItem(new int[] {2,10});

            ContentValues contentValues= DatabaseHelper.createContentValuesForCompleteRecord(timeHolder.getNameOfTheTimer(),timeHolder.getNumberOfCycles(),timeHolder.getTimeItemAsByteArray());

            db.update(DatabaseHelper.DB_NAME, contentValues, DatabaseHelper.DB_CYCLE_NAME,  new String[] {"Default1"});
            */
            cursor=db.query(DatabaseHelper.DB_NAME,new String[] {"_id",DatabaseHelper.DB_CYCLE_NAME, DatabaseHelper.DB_CYCLE_COUNT, DatabaseHelper.DB_CYCLE_DESCRIPTION},null,null,null,null,null);

            CursorAdapter listAdapter=new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,cursor,new String[] {DatabaseHelper.DB_CYCLE_NAME},new int[] {android.R.id.text1},0);



            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    callTimerSettingActivity(timerListServer.getTimer(position));
                }
            });
        }catch (SQLiteException e){
            Toast.makeText(this, "Database missing",Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu_action_item_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.createNewItem:
                callTimerSettingActivity(new TimeHolder());
                break;
        }

        return true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

    private void callTimerSettingActivity(){
        Intent intent=new Intent(this, SetTimerActivity.class);
        startActivity(intent);
    }

    private void callTimerSettingActivity(String cycleName){
        Intent intent=new Intent(this, SetTimerActivity.class);
        intent.putExtra(DatabaseHelper.DB_CYCLE_NAME, cycleName);
        startActivity(intent);
    }

    private void callTimerSettingActivity(TimeHolder timeHolder){
        Intent intent=new Intent(this, SetTimerActivity.class);
        intent.putExtra(DatabaseHelper.DB_CYCLE_NAME, timeHolder.getNameOfTheTimer());
        intent.putExtra(DatabaseHelper.DB_CYCLE_COUNT,timeHolder.getNumberOfCycles());
        intent.putExtra(DatabaseHelper.DB_CYCLE_DESCRIPTION,timeHolder.getTimeItemAsByteArray());
        /*for(TimeHolder.TimeItem item: timeHolder.getTimeItem()){

            Log.v("JJT","minute "+item.minute+" seconds "+item.second);
        }*/
        startActivity(intent);
    }
}
