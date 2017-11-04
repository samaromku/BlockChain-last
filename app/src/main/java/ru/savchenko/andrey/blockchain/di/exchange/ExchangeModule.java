package ru.savchenko.andrey.blockchain.di.exchange;

import dagger.Module;
import dagger.Provides;
import ru.savchenko.andrey.blockchain.services.exchange.ExchangeInteractor;
import ru.savchenko.andrey.blockchain.services.exchange.ExchangePresenter;
import ru.savchenko.andrey.blockchain.services.exchange.UpdateExchangeService;

/**
 * Created by Andrey on 30.10.2017.
 */
@Module
public class ExchangeModule {
    private UpdateExchangeService service;

    public ExchangeModule(UpdateExchangeService service) {
        this.service = service;
    }

    @ExchangeScope
    @Provides
    ExchangePresenter presenter(ExchangeInteractor exchangeInteractor){
        return new ExchangePresenter(service, exchangeInteractor);
    }
}
