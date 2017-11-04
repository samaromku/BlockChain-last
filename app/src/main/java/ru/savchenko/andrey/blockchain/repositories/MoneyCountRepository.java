package ru.savchenko.andrey.blockchain.repositories;

import io.realm.Realm;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;

/**
 * Created by Andrey on 05.11.2017.
 */

public class MoneyCountRepository {
    private Realm realmInstance() {
        return Realm.getDefaultInstance();
    }

    public void setBuyOrSell(Boolean buyOrSell) {
        realmInstance().executeTransaction(realm -> realm.where(MoneyCount.class).findFirst().setBuyOrSell(buyOrSell));
        realmInstance().close();
    }

    public void setUsdCount(Double usdCount) {
        realmInstance().executeTransaction(realm -> realm.where(MoneyCount.class).findFirst().setUsdCount(usdCount));
        realmInstance().close();
    }

    public void setBitCoinCount(Double bitCoinCount) {
        realmInstance().executeTransaction(realm -> realm.where(MoneyCount.class).findFirst().setBitCoinCount(bitCoinCount));
        realmInstance().close();
    }
}
