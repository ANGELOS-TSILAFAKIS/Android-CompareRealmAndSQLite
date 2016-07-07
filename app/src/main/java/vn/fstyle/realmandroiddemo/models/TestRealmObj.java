package vn.fstyle.realmandroiddemo.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * TestRealmObj.
 *
 * @author DaoLQ
 */
public class TestRealmObj extends RealmObject {

    @PrimaryKey
    protected int id;
    protected String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
