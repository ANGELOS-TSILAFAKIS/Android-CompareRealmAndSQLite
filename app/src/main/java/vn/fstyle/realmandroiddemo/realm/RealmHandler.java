package vn.fstyle.realmandroiddemo.realm;

import android.support.annotation.NonNull;

import java.util.List;

import io.realm.Realm;
import vn.fstyle.realmandroiddemo.TaskManager;
import vn.fstyle.realmandroiddemo.models.TestRealmObj;

/**
 * DatabaseHandler.
 *
 * @author DaoLQ
 */
public class RealmHandler {

    private Realm mRealm;

    private long mTimeStart;
    private long mTimeEnd;

    public RealmHandler(@NonNull Realm realm) {
        mRealm = realm;
    }

    private long getTimeCurrent() {
        return System.currentTimeMillis();
    }

    public long getDuration() {
        return mTimeEnd - mTimeStart;
    }

    public void insert(final List<TestRealmObj> objs, final TaskManager.Callback callback) {
        mTimeStart = getTimeCurrent();
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(objs);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                mTimeEnd = getTimeCurrent();
                callback.onRealmInsertComplete(getDuration());
            }
        });
    }

    public int select() {
        mTimeStart = getTimeCurrent();
        int size = mRealm.where(TestRealmObj.class).findAll().size();
        mTimeEnd = getTimeCurrent();
        return size;
    }

    public void update(final List<TestRealmObj> objs, final TaskManager.Callback callback) {
        mTimeStart = getTimeCurrent();
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(objs);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                mTimeEnd = getTimeCurrent();
                callback.onRealmUpdateComplete(getDuration());
            }
        });
    }

    public void delete(final List<TestRealmObj> objs, final TaskManager.Callback callback) {
        mTimeStart = getTimeCurrent();
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (TestRealmObj obj : objs) {
                    TestRealmObj objDelete = realm.copyToRealmOrUpdate(obj);
                    objDelete.deleteFromRealm();
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                mTimeEnd = getTimeCurrent();
                callback.onRealmDeleteComplete(getDuration());
            }
        });
    }
}
