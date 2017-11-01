package ru.savchenko.andrey.blockchain.activities;

import io.reactivex.Completable;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.repositories.IBaseRepository;

/**
 * Created by Andrey on 01.11.2017.
 */

public class MainInterActor {
    private IBaseRepository<MoneyCount>baseRepository;

    public MainInterActor(IBaseRepository<MoneyCount> baseRepository) {
        this.baseRepository = baseRepository;
    }

    Completable initMoneyCount(){
        return Completable.fromAction(() -> {
            MoneyCount moneyCount =baseRepository.getItem();
            if(moneyCount==null){
                moneyCount =new MoneyCount(1, (double)1000, (double)0);
                baseRepository.addItem(moneyCount);
            }
        });
    }

}
