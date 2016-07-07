package vn.fstyle.realmandroiddemo;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import vn.fstyle.realmandroiddemo.models.TestRealmObj;
import vn.fstyle.realmandroiddemo.realm.RealmHandler;
import vn.fstyle.realmandroiddemo.sqlite.SQLiteHandler;

/**
 * TaskManager.
 *
 * @author DaoLQ
 */
public class TaskManager {

    public interface Callback {
        void onSQLiteInsertComplete(Long l);

        void onRealmInsertComplete(Long l);

        void onSQLiteUpdateComplete(Long l);

        void onRealmUpdateComplete(Long l);

        void onSQLiteDeleteComplete(Long l);

        void onRealmDeleteComplete(Long l);
    }

    public interface CallbackQueryDatabase {
        void onInsertComplete();

        void onUpdateComplete();
    }

    // SQLite
    private SQLiteHandler mSqLiteHandler;
    // Realm
    private RealmHandler mRealmHandler;

    List<TestRealmObj> mTestRealmObjs;

    public TaskManager(Context context, Realm realm) {
        mSqLiteHandler = new SQLiteHandler(context);
        mRealmHandler = new RealmHandler(realm);
        mTestRealmObjs = initData();
    }

    public void execute(final Callback callback) {
        new SQLiteExecuteTask(mSqLiteHandler, mTestRealmObjs, Crub.INSERT, new CallbackQueryDatabase() {
            @Override
            public void onInsertComplete() {
                new SQLiteExecuteTask(mSqLiteHandler, mTestRealmObjs, Crub.UPDATE, new CallbackQueryDatabase() {
                    @Override
                    public void onInsertComplete() {
                        // No-op
                    }

                    @Override
                    public void onUpdateComplete() {
                        new SQLiteExecuteTask(mSqLiteHandler, mTestRealmObjs, Crub.DELETE, null, callback).execute();
                        mRealmHandler.delete(mTestRealmObjs, callback);
                    }
                }, callback).execute();
                mRealmHandler.update(mTestRealmObjs, callback);
            }

            @Override
            public void onUpdateComplete() {
                // No-op
            }
        }, callback).execute();
        mRealmHandler.insert(mTestRealmObjs, callback);
    }

    private class SQLiteExecuteTask extends AsyncTask<Void, Void, Void> {

        private SQLiteHandler mSqLiteHandler;
        private List<TestRealmObj> mObjs;
        private Crub mCrub;
        private CallbackQueryDatabase mCallbackSQLite;
        private TaskManager.Callback mCallback;

        public SQLiteExecuteTask(SQLiteHandler sqLiteHandler, List<TestRealmObj> objs, Crub crub, CallbackQueryDatabase callbackSQLite, TaskManager.Callback callback) {
            mSqLiteHandler = sqLiteHandler;
            mObjs = objs;
            mCrub = crub;
            mCallbackSQLite = callbackSQLite;
            mCallback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSqLiteHandler.beginTransaction();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            switch (mCrub) {
                case INSERT:
                    for (TestRealmObj mObj : mObjs) {
                        mSqLiteHandler.insert(mObj);
                    }
                    break;
                case UPDATE:
                    for (TestRealmObj mObj : mObjs) {
                        mSqLiteHandler.update(mObj);
                    }
                    break;
                case DELETE:
                    for (TestRealmObj mObj : mObjs) {
                        mSqLiteHandler.delete(mObj);
                    }
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mSqLiteHandler.closeTransaction();
            switch (mCrub) {
                case INSERT:
                    mCallback.onSQLiteInsertComplete(mSqLiteHandler.getDuration());
                    mCallbackSQLite.onInsertComplete();
                    break;
                case UPDATE:
                    mCallback.onSQLiteUpdateComplete(mSqLiteHandler.getDuration());
                    mCallbackSQLite.onUpdateComplete();
                    break;
                case DELETE:
                    mCallback.onSQLiteDeleteComplete(mSqLiteHandler.getDuration());
                    break;
            }
        }

    }

    private List<TestRealmObj> initData() {
        int totalItem = 1000;
        List<TestRealmObj> testRealmObjs = new ArrayList<>();
        for (int i = 0; i < totalItem; i++) {
            TestRealmObj obj = new TestRealmObj();
            obj.setId(i);
            obj.setName(String.format("name %d", i));
            testRealmObjs.add(obj);
        }
        return testRealmObjs;
    }
}
