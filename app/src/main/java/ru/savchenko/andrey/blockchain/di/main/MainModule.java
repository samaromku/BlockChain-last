package ru.savchenko.andrey.blockchain.di.main;

import dagger.Module;
import dagger.Provides;
import ru.savchenko.andrey.blockchain.activities.MainInterActor;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.repositories.IBaseRepository;

/**
 * Created by Andrey on 01.11.2017.
 */
@Module
public class MainModule {

    @MainScope
    @Provides
    public MainInterActor mainInterActor(IBaseRepository<MoneyCount>moneyCountBaseRepository){
        return new MainInterActor(moneyCountBaseRepository);
    }

}
