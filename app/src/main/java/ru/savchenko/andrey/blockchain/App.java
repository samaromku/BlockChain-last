package ru.savchenko.andrey.blockchain;

import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.victoralbertos.rx2_permissions_result.RxPermissionsResult;
import ru.savchenko.andrey.blockchain.di.ComponentManager;
import ru.savchenko.andrey.blockchain.network.RequestManager;
import ru.savchenko.andrey.blockchain.services.exchange.UpdateExchangeService;
import ru.savchenko.andrey.blockchain.storage.Prefs;

/**
 * Created by Andrey on 12.10.2017.
 */

public class App extends Application {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        RequestManager.init();
        Realm.init(this);
        Prefs.init(this);
        startService(UpdateExchangeService.newIntent(this));
        UpdateExchangeService.setServiceAlarm(this, true);
        ComponentManager.init();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
        RxPermissionsResult.register(this);
        Fabric.with(this, new Crashlytics());
    }
}
