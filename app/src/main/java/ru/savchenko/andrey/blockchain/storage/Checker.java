package ru.savchenko.andrey.blockchain.storage;

import android.util.Log;

import java.util.List;

import ru.savchenko.andrey.blockchain.entities.MoneyScore;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.interfaces.IChecker;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;

import static ru.savchenko.andrey.blockchain.activities.MainActivity.TAG;
import static ru.savchenko.andrey.blockchain.storage.Const.BUY_OPERATION;
import static ru.savchenko.andrey.blockchain.storage.Const.SELL_OPERATION;

/**
 * Created by Andrey on 30.10.2017.
 */

public class Checker implements IChecker {

    public int previousMaxOrMinFourHours(USD lastUSD){
        MoneyScore todayMoneyScore = new USDRepository().getMaxFourHours(lastUSD.getDate());
//        USD lastUSD = new USDRepository().getLastUSD();
        Log.i(TAG, "previousMaxOrMin: " + todayMoneyScore);
        Log.i(TAG, "previousMaxOrMin: preLastUSD " + lastUSD.getLast());
        if(todayMoneyScore!=null){
            if(todayMoneyScore.getMax().equals(lastUSD.getLast())){
                Log.i(TAG, "previousMaxOrMin: значит цена с максимума пошла на спад, надо продавать биткоин");
                //значит цена с максимума пошла на спад, надо продавать биткоин
                return SELL_OPERATION;
            }else if(todayMoneyScore.getMin().equals(lastUSD.getLast())){
                Log.i(TAG, "previousMaxOrMin: значит цена с минимума пошла на повышение надо покупать биткоин");
                //значит цена с минимума пошла на повышение надо покупать биткоин
                return BUY_OPERATION;
            }
        }
        return 0;
    }

    @Override
    public int buyOrSell(USD lastUSD) {
        List<USD> lastValues = new USDRepository().getLastFiveValues();
        Double firstFromLast = lastValues.get(0).getLast();
        Double secondFromLast = lastValues.get(1).getLast();
        Double thirdFromLast = lastValues.get(2).getLast();
        Double fourthFromLast = lastValues.get(3).getLast();
        Log.i(TAG, "buyOrSell: " + firstFromLast + " " + secondFromLast + " " + thirdFromLast + " " + fourthFromLast);

        if ((firstFromLast > secondFromLast) && (secondFromLast > thirdFromLast) && (fourthFromLast > thirdFromLast)) {
            Log.i(TAG, "first condition");
            return -1;
        } else if ((fourthFromLast > thirdFromLast) && (thirdFromLast > secondFromLast) && (firstFromLast > secondFromLast)) {
            Log.i(TAG, "second condition");
            return 1;
        }
        return 0;
    }

    //20 $ в день с 1000$ когда как....(
    @Override
    public int otherValues(USD lastUsd) {
        List<USD> lastValues = new USDRepository().getLastFiveValues();
        Double firstFromLast = lastValues.get(0).getLast();
        Double secondFromLast = lastValues.get(1).getLast();
        Double thirdFromLast = lastValues.get(2).getLast();
        Double fourthFromLast = lastValues.get(3).getLast();
        Log.i(TAG, "buyOrSell: " + firstFromLast + " " + secondFromLast + " " + thirdFromLast + " " + fourthFromLast);

        if ((fourthFromLast > thirdFromLast) && (thirdFromLast > secondFromLast) && (firstFromLast > secondFromLast)) {
            Log.i(TAG, "first condition");
            return 1;
        } else if ((fourthFromLast < thirdFromLast) && (thirdFromLast < secondFromLast) && (firstFromLast < secondFromLast)) {
            Log.i(TAG, "second condition");
            return -1;
        }
        return 0;
    }

}
