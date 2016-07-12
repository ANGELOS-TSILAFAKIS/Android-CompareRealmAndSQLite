package vn.fstyle.realmandroiddemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;
import vn.fstyle.realmandroiddemo.common.Constant;
import vn.fstyle.realmandroiddemo.models.TestRealmObj2;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int ID_INSERT_SQLITE = 0;
    public static final int ID_INSERT_REALM = 1;
    public static final int ID_UPDATE_SQLITE = 2;
    public static final int ID_UPDATE_REALM = 3;
    public static final int ID_DELETE_SQLITE = 4;
    public static final int ID_DELETE_REALM = 5;

    public static final int[] COLORFUL_COLORS = {
            Color.rgb(193, 37, 82), Color.rgb(193, 37, 82),
            Color.rgb(255, 102, 0), Color.rgb(255, 102, 0),
            Color.rgb(245, 199, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(106, 150, 31),
            Color.rgb(179, 100, 53), Color.rgb(179, 100, 53)
    };

    protected String[] optionTests = new String[]{
            "INSERT-R", "INSERT-S", "UPDATE-R", "UPDATE-S", "DELETE-R", "DELETE-S"
    };

    private BarChart mChart;
    private Button mBtnTest;

    private TaskManager mTaskManager;
    private Realm mRealm;
    private long timeInsertRealm = 0, timeInsertSQLite = 0;
    private long timeUpdateRealm = 0, timeUpdateSQLite = 0;
    private long timeDeleteRealm = 0, timeDeleteSQLite = 0;
    private boolean mIsExecute = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTaskManager = new TaskManager(getApplicationContext(), Realm.getDefaultInstance());
        mRealm = Realm.getDefaultInstance();
        mBtnTest = (Button) findViewById(R.id.btnTest);
        mChart = (BarChart) findViewById(R.id.barChart);

        mChart.animateY(2000);
        mChart.animateX(2000);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setDescription("");
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(1);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(6, false);
        leftAxis.setValueFormatter(new MyYAxisValueFormatter());
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = mChart.getLegend();
        l.setEnabled(false);

        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsExecute) {
                    return;
                }
                timeInsertRealm = 0;
                timeInsertSQLite = 0;
                timeUpdateRealm = 0;
                timeUpdateSQLite = 0;
                timeDeleteRealm = 0;
                timeDeleteSQLite = 0;
                fillDataIntoChart();
                mIsExecute = true;
                mTaskManager.execute(new TaskManager.Callback() {
                    @Override
                    public void onSQLiteInsertComplete(Long l) {
                        timeInsertSQLite = l;
                        fillDataIntoChart();
                    }

                    @Override
                    public void onRealmInsertComplete(Long l) {
                        timeInsertRealm = l;
                        fillDataIntoChart();
                    }

                    @Override
                    public void onSQLiteUpdateComplete(Long l) {
                        timeUpdateSQLite = l;
                        fillDataIntoChart();
                    }

                    @Override
                    public void onRealmUpdateComplete(Long l) {
                        timeUpdateRealm = l;
                        fillDataIntoChart();
                    }

                    @Override
                    public void onSQLiteDeleteComplete(Long l) {
                        timeDeleteSQLite = l;
                        fillDataIntoChart();
                        mIsExecute = false;
                    }

                    @Override
                    public void onRealmDeleteComplete(Long l) {
                        timeDeleteRealm = l;
                        fillDataIntoChart();
                    }
                });
            }
        });
        fillDataIntoChart();
        findViewById(R.id.btnDemo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TestRealmObj obj = new TestRealmObj();
//                obj.setId(1);
//                obj.setName("AAA");
//                obj.setAge(10);
//                mRealm.beginTransaction();
//                mRealm.insertOrUpdate(obj);
//                mRealm.commitTransaction();
//
//                TestRealmObj obj1 = mRealm.where(TestRealmObj.class).equalTo(Constant.ID, 1).findFirst();
//                Log.d(TAG, "DebugLog value 1: " + obj1.getAge());
//                TestRealmObj obj2 = mRealm.where(TestRealmObj.class).equalTo(Constant.ID, 1).findFirst();
//                mRealm.beginTransaction();
//                obj2.setAge(20);
//                mRealm.commitTransaction();
//                Log.d(TAG, "DebugLog value 2: " + obj1.getAge());
//
//                mRealm.beginTransaction();
//                obj2.deleteFromRealm();
//                mRealm.commitTransaction();
                mRealm.beginTransaction();
                TestRealmObj2 obj = mRealm.createObject(TestRealmObj2.class);
                obj.setId(1);
                obj.setName("AAA");
                obj.setAge(10);
                mRealm.commitTransaction();

                TestRealmObj2 obj1 = mRealm.where(TestRealmObj2.class).equalTo(Constant.ID, 1).findFirst();
                Log.d(TAG, "DebugLog value 1: " + obj1.getAge());
                TestRealmObj2 obj2 = mRealm.where(TestRealmObj2.class).equalTo(Constant.ID, 1).findFirst();
                mRealm.beginTransaction();
                obj2.setAge(11);
                mRealm.commitTransaction();
                Log.d(TAG, "DebugLog value 2: " + obj1.getAge());
                mRealm.beginTransaction();
                RealmObject.deleteFromRealm(obj2);
                mRealm.commitTransaction();
            }
        });
    }

    private void fillDataIntoChart() {

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < optionTests.length; i++) {
            strings.add(optionTests[i % optionTests.length]);
        }
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(timeInsertRealm, ID_INSERT_SQLITE));
        barEntries.add(new BarEntry(timeInsertSQLite, ID_INSERT_REALM));
        barEntries.add(new BarEntry(timeUpdateRealm, ID_UPDATE_SQLITE));
        barEntries.add(new BarEntry(timeUpdateSQLite, ID_UPDATE_REALM));
        barEntries.add(new BarEntry(timeDeleteRealm, ID_DELETE_SQLITE));
        barEntries.add(new BarEntry(timeDeleteSQLite, ID_DELETE_REALM));

        // DataSet
        BarDataSet set1;
        set1 = new BarDataSet(barEntries, "Operations");
        set1.setBarSpacePercent(35f);
        set1.setColors(COLORFUL_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(strings, dataSets);
        data.setValueTextSize(10f);
        mChart.setData(data);
        mChart.getData().notifyDataChanged();
        mChart.notifyDataSetChanged();
        mChart.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
