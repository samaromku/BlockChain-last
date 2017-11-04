package ru.savchenko.andrey.blockchain.services.exchange;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.savchenko.andrey.blockchain.di.ComponentManager;
import ru.savchenko.andrey.blockchain.dialogs.buyorsell.BuyOrSellInteractor;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.interfaces.IChecker;
import ru.savchenko.andrey.blockchain.interfaces.IUSDRepository;
import ru.savchenko.andrey.blockchain.repositories.IBaseRepository;
import ru.savchenko.andrey.blockchain.repositories.MoneyCountRepository;

import static ru.savchenko.andrey.blockchain.activities.MainActivity.TAG;
import static ru.savchenko.andrey.blockchain.storage.Const.NO_OPERATION;

/**
 * Created by Andrey on 17.10.2017.
 */
public class ExchangeInteractor {
    @Inject
    BuyOrSellInteractor interactor;
    private IBaseRepository<MoneyCount>baseRepository;
    private IUSDRepository iusdRepository;
    private IChecker checker;
    private MoneyCountRepository moneyCountRepository;

    public ExchangeInteractor(IBaseRepository<MoneyCount> baseRepository, IUSDRepository iusdRepository, IChecker checker, MoneyCountRepository moneyCountRepository) {
        this.baseRepository = baseRepository;
        this.iusdRepository = iusdRepository;
        this.checker = checker;
        this.moneyCountRepository = moneyCountRepository;
        ComponentManager.getAppComponent().inject(this);
    }

    Observable<MoneyCount> buyOrSellMethod() {
//        int buyOrSell = otherValues();
//        if(Utils.saver()==-1){
//            moneyCount.setBuyOrSell(true);
//            return interactor.sellUSDInteractor(moneyCount.getUsdCount(), moneyCount.getBitCoinCount());
//        }else if(Utils.saver()==1){
//            moneyCount.setBuyOrSell(false);
//            return interactor.sellBTCInteractor(moneyCount.getUsdCount(), moneyCount.getBitCoinCount());
//        }
        MoneyCount moneyCount = baseRepository.getItem();
        int trueSellOrBuy = checker.previousMaxOrMinFourHours(iusdRepository.getLastUSD());
        Log.i(TAG, "buyOrSellMethod: " + trueSellOrBuy);
        USD lastUsd = iusdRepository.getLastUSD();
        if(trueSellOrBuy == -1){
            moneyCountRepository.setBuyOrSell(false);
            return interactor.sellUSDInteractor(moneyCount.getUsdCount()*0.5, moneyCount.getBitCoinCount(), moneyCount, lastUsd);
        }else if(trueSellOrBuy==1){
            moneyCountRepository.setBuyOrSell(true);
            return interactor.sellBTCInteractor(moneyCount.getUsdCount(), moneyCount.getBitCoinCount()*0.5, moneyCount, lastUsd);
        }else {
            moneyCountRepository.setBuyOrSell(null);
        }


//        if (buyOrSell == -1) {
//            moneyCount.setBuyOrSell(true);
//            return interactor.sellUSDInteractor(moneyCount.getUsdCount() * 0.5, moneyCount.getBitCoinCount());
//        } else if (buyOrSell == 1) {
//            moneyCount.setBuyOrSell(false);
//            return interactor.sellBTCInteractor(moneyCount.getUsdCount(), moneyCount.getBitCoinCount() * 0.5);
//        }
        return writeInDBWithoutChange(moneyCount, lastUsd);
    }

    public Observable<MoneyCount>testUSDList(USD usd){
        MoneyCount moneyCount = baseRepository.getItemCopy();
        int trueSellOrBuy = checker.previousMaxOrMinFourHours(usd);
        Log.i(TAG, "buyOrSellMethod: " + trueSellOrBuy);
        if(trueSellOrBuy == -1){
            moneyCountRepository.setBuyOrSell(false);
            return interactor.sellUSDInteractor(moneyCount.getUsdCount()*0.5, moneyCount.getBitCoinCount(), moneyCount, usd);
        }else if(trueSellOrBuy==1){
            moneyCountRepository.setBuyOrSell(true);
            return interactor.sellBTCInteractor(moneyCount.getUsdCount(), moneyCount.getBitCoinCount()*0.5, moneyCount, usd);
        }else {
            moneyCountRepository.setBuyOrSell(null);
        }
        return writeInDBWithoutChange(moneyCount, usd);
    }

    Observable<MoneyCount>writeInDBWithoutChange(MoneyCount moneyCount, USD lastUsd){
        if(lastUsd!=null) {
            iusdRepository.setBuyOrSell(lastUsd, NO_OPERATION);
            return Observable.fromCallable(() -> moneyCount);
        }else return Observable.empty();
    }


}