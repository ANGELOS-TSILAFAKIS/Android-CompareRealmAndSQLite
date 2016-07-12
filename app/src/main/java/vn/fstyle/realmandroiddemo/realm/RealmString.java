package vn.fstyle.realmandroiddemo.realm;

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
}
