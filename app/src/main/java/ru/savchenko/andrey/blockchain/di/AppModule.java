package ru.savchenko.andrey.blockchain.di;

import dagger.Module;
import dagger.Provides;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.interfaces.IChecker;
import ru.savchenko.andrey.blockchain.interfaces.IUSDRepository;
import ru.savchenko.andrey.blockchain.repositories.BaseRepository;
import ru.savchenko.andrey.blockchain.repositories.IBaseRepository;
import ru.savchenko.andrey.blockchain.repositories.MoneyCountRepository;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;
import ru.savchenko.andrey.blockchain.services.exchange.ExchangeInteractor;
import ru.savchenko.andrey.blockchain.storage.Checker;

/**
 * Created by Andrey on 01.11.2017.
 */
@Module
public class AppModule {
    @Provides
    public IBaseRepository<MoneyCount> baseRepository(){
        return new BaseRepository<>(MoneyCount.class);
    }

    @Provides
    IUSDRepository iusdRepository(){
        return new USDRepository();
    }

    @Provides
    IChecker checker(){
        return new Checker();
    }

    @Provides
    ExchangeInteractor exchangeInteractor(IBaseRepository<MoneyCount> baseRepository, IUSDRepository iusdRepository, IChecker checker, MoneyCountRepository moneyCountRepository){
        return new ExchangeInteractor(baseRepository, iusdRepository, checker, moneyCountRepository);
    }

    @Provides
    MoneyCountRepository moneyCountRepository(){
        return new MoneyCountRepository();
    }
}
