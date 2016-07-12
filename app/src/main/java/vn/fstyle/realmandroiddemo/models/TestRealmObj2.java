package vn.fstyle.realmandroiddemo.models;

import io.realm.RealmModel;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * TestRealmObj2.
 *
 * @author DaoLQ
 */
@RealmClass
public class TestRealmObj2 implements RealmModel {

    @PrimaryKey
    protected int id;
    @Ignore
    protected String name;
    protected int age;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
