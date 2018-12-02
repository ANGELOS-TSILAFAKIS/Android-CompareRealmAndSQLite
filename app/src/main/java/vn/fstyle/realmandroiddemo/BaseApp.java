package vn.fstyle.realmandroiddemo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * BaseApp.
 *
 * @author DaoLQ
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        // The realm file will be located in Context.getFilesDir() with name "default.realm"
//        RealmConfiguration config = new RealmConfiguration.Builder(this)
//                .deleteRealmIfMigrationNeeded()
//                .build();
//
//        Realm.setDefaultConfiguration(config);


        Realm.init(this);
        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();

        // Deletes the realm
        // Use when you want a clean slate for dev
//        Realm.deleteRealm(realmConfiguration);
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
