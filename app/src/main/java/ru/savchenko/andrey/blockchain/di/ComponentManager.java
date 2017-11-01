package ru.savchenko.andrey.blockchain.di;

import ru.savchenko.andrey.blockchain.di.exchange.ExchangeModule;
import ru.savchenko.andrey.blockchain.di.exchange.ExchangeSubComponent;
import ru.savchenko.andrey.blockchain.di.main.MainComponent;
import ru.savchenko.andrey.blockchain.di.main.MainModule;
import ru.savchenko.andrey.blockchain.services.exchange.UpdateExchangeService;

/**
 * Created by Andrey on 25.09.2017.
 */

public class ComponentManager {
    private static AppComponent appComponent;
    private static ExchangeSubComponent exchangeSubComponent;
    private static MainComponent mainComponent;

    public static AppComponent getAppComponent() {
        if(appComponent ==null){
            appComponent = DaggerAppComponent
                    .builder()
                    .build();
        }
        return appComponent;
    }

    public static ExchangeSubComponent getExchangeSubComponent(UpdateExchangeService service){
        if(exchangeSubComponent==null){
            exchangeSubComponent = getAppComponent().exchangeSubComponent(new ExchangeModule(service));
        }
        return exchangeSubComponent;
    }

    public static MainComponent getMainComponent(){
        if(mainComponent==null){
            mainComponent = getAppComponent().mainComponent(new MainModule());
        }
        return mainComponent;
    }

    public static void init(){
        appComponent = DaggerAppComponent
                .builder()
                .build();
    }
}
