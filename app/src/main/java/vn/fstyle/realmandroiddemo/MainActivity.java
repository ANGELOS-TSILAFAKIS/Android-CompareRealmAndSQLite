package vn.fstyle.realmandroiddemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;

public class MainActivity extends Activity {

    private Button mBtnTest;

    private TaskManager mTaskManager;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTaskManager = new TaskManager(getApplicationContext(), Realm.getDefaultInstance());
        mRealm = Realm.getDefaultInstance();
        mBtnTest = (Button) findViewById(R.id.btnTest);
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskManager.execute(new TaskManager.Callback() {
                    @Override
                    public void onSQLiteInsertComplete(Long l) {
                        Log.d("", "DebugLog onSQLiteInsertComplete: " + l);
                    }

                    @Override
                    public void onRealmInsertComplete(Long l) {
                        Log.d("", "DebugLog onRealmInsertComplete: " + l);
                    }

                    @Override
                    public void onSQLiteUpdateComplete(Long l) {
                        Log.d("", "DebugLog onSQLiteUpdateComplete " + l);
                    }

                    @Override
                    public void onRealmUpdateComplete(Long l) {
                        Log.d("", "DebugLog onRealmUpdateComplete " + l);
                    }

                    @Override
                    public void onSQLiteDeleteComplete(Long l) {
                        Log.d("", "DebugLog onSQLiteDeleteComplete " + l);
                    }

                    @Override
                    public void onRealmDeleteComplete(Long l) {
                        Log.d("", "DebugLog onRealmDeleteComplete " + l);
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
