package vn.fstyle.realmandroiddemo.realm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * RealmAutoIncrement.
 *
 * @author DaoLQ
 */
public final class RealmAutoIncrement {

    private Map<Class<? extends RealmObject>, AtomicInteger> mModelMap = new HashMap<>();
    private static RealmAutoIncrement sAutoIncrementMap;
    private Class<? extends RealmObject> mObj;

    private RealmAutoIncrement(Class<? extends RealmObject> obj) {
        mObj = obj;
        mModelMap.put(obj, new AtomicInteger(getLastIdFromModel(mObj)));
    }

    /**
     * Utility method which query for all models saved and get the bigger model id saved
     * Used to guarantee which the last model id saved is really the last
     *
     * @param clazz Model which should get the last id
     * @return The last id saved from model passed
     */
    private int getLastIdFromModel(Class<? extends RealmObject> clazz) {

        String primaryKeyColumnName = "id";
        Number lastId = Realm.getDefaultInstance().where(clazz).max(primaryKeyColumnName);

        return lastId == null ? 0 : lastId.intValue();
    }

    /**
     * Search in mModelMap for the last saved id from model passed and return the next one
     *
     * @return The next id which can be saved in database for that model,
     * {@code null} will be returned when this method is called by reflection
     */
    public Integer getNextIdFromModel() {


        if (isValidMethodCall()) {

            AtomicInteger modelId = mModelMap.get(mObj);

            if (modelId == null) {
                return 0;
            }
            return modelId.incrementAndGet();
        }
        return 0;
    }

    /**
     * Utility method to validate if the method is called from reflection,
     * in this case is considered a not valid call otherwise is a valid call
     *
     * @return The boolean which define if the method call is valid or not
     */
    private boolean isValidMethodCall() {

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        for (StackTraceElement stackTraceElement : stackTraceElements) {

            if (stackTraceElement.getMethodName().equals("newInstance")) {
                return false;
            }
        }
        return true;
    }

    public static RealmAutoIncrement getInstance(Class<? extends RealmObject> obj) {

        if (sAutoIncrementMap == null) {
            sAutoIncrementMap = new RealmAutoIncrement(obj);
        }

        return sAutoIncrementMap;
    }
}
