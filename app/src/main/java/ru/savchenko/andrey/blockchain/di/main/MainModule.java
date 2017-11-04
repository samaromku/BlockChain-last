package ru.savchenko.andrey.blockchain.di.main;

import dagger.Module;
import dagger.Provides;
import ru.savchenko.andrey.blockchain.activities.MainInterActor;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.repositories.BaseRepository;
import ru.savchenko.andrey.blockchain.repositories.IBaseRepository;

/**
 * Created by Andrey on 01.11.2017.
 */
@Module
public class MainModule {

    @MainScope
    @Provides
    public MainInterActor mainInterActor(IBaseRepository<MoneyCount>moneyCountBaseRepository, IBaseRepository<USD>usdiBaseRepository){
        return new MainInterActor(moneyCountBaseRepository, usdiBaseRepository);
    }

    @MainScope
    @Provides
    IBaseRepository<USD>usdiBaseRepository(){
        return new BaseRepository<>(USD.class);
    }
}
