package vn.fstyle.realmandroiddemo.realm;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * RealmString.
 *
 * @author DaoLQ
 */
public class RealmString extends RealmObject {

    String stringValue;

    /**
     * constructor
     */
    public RealmString() {
    }

    /**
     * @param stringValue value
     */
    public RealmString(String stringValue) {
        this.stringValue = stringValue;
    }


    /**
     * @return value
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * @param stringValue input
     */
    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }
                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .registerTypeAdapter(new TypeToken<RealmList<RealmString>>() {
            }.getType(), new RealmStringDeserializer())
            .create();
}
