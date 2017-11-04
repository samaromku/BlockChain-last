package ru.savchenko.andrey.blockchain.services.exchange;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.entities.MoneyScore;
import ru.savchenko.andrey.blockchain.entities.USD;

import static org.mockito.Mockito.mock;
import static ru.savchenko.andrey.blockchain.activities.MainActivity.TAG;
import static ru.savchenko.andrey.blockchain.storage.Const.BUY_OPERATION;
import static ru.savchenko.andrey.blockchain.storage.Const.SELL_OPERATION;

/**
 * Created by Andrey on 30.10.2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class ExchangePresenterTest {
    ExchangePresenter presenter;
    ExchangeView view;
    MoneyCount moneyCount = new MoneyCount(1, 1000.0, 0.01);
    @Mock
    ExchangeInteractor interactor;
    private String usdStr = "{\"buyOrSell\":0,\"date\":\"Oct 23, 2017 1:41:49 AM\",\"id\":594,\"15m\":6001.08,\"buy\":6001.63,\"last\":6001.08,\"sell\":6000.52,\"symbol\":\"$\"}";
    private String usdStr2 = "{\"id\":594,\"15m\":6001.08,\"buy\":6001.63,\"last\":6001.08,\"sell\":6000.52,\"symbol\":\"$\",\"date\":\"Oct 23, 2017 4:41:49 AM\",\"buyOrSell\":0}";
    @Before
    public void setup() {
        new SimpleDateFormat("MMM dd, yyyy, HH:mm:SS a").format(new Date());
        MockitoAnnotations.initMocks(this);
        view = mock(ExchangeView.class);
        presenter = new ExchangePresenter(view, interactor);
    }

    @Test
    public void testFile(){
//        File file = new File("C:\\Users\\Andrey\\Desktop\\BlockChain-last\\app\\src\\test\\java\\ru\\savchenko\\andrey\\blockchain\\services\\exchange\\text.txt");
//        System.out.println(file.exists() + " " + file.getAbsolutePath() + " " + file.length());
//        List<USD>usds = listFromFile(file);
////        MoneyScore moneyScore = new MoneyScore(1,)
//        for (int i = 0; i < usds.size(); i++) {
//            USD usd = usds.get(i);
//            int trueSellOrBuy = checkUSD(usds.get(i), );
//            if(trueSellOrBuy == -1){
//                moneyCount.setBuyOrSell(false);
//                sellUSDInteractor(moneyCount.getUsdCount()*0.5, moneyCount.getBitCoinCount(), moneyCount, usd);
//            }else if(trueSellOrBuy==1){
//                moneyCount.setBuyOrSell(true);
//                sellBTCInteractor(moneyCount.getUsdCount(), moneyCount.getBitCoinCount()*0.5, moneyCount, usd);
//            }else {
//                moneyCount.setBuyOrSell(null);
//            }
//        }
    }

    public void sellUSDInteractor(Double usdSize, Double btcSize, MoneyCount moneyCount, USD lastUsd) {
//        iusdRepository.setBuyOrSell(lastUsd, SELL_OPERATION);
        lastUsd.setBuyOrSelled(usdSize);
        Double btcValue = btcSize + usdSize / lastUsd.getBuy();
        Double restUsd = moneyCount.getUsdCount() - usdSize;

        moneyCount.setUsdCount(restUsd);
        moneyCount.setBitCoinCount(btcValue);
    }

    public void sellBTCInteractor(Double usdSize, Double btcSize, MoneyCount moneyCount, USD lastUsd) {
//        iusdRepository.setBuyOrSell(lastUsd, BUY_OPERATION);
        lastUsd.setBuyOrSelled(btcSize * lastUsd.getSell());
        Double usdValue = usdSize + btcSize * lastUsd.getSell();
        Double restBtc = moneyCount.getBitCoinCount() - btcSize;

        moneyCount.setUsdCount(usdValue);
        moneyCount.setBitCoinCount(restBtc);
    }

    private int checkUSD(USD lastUSD, MoneyScore todayMoneyScore){
//        MoneyScore todayMoneyScore = new USDRepository().getMaxFourHours();
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

    private List<USD> listFromFile(){
        File file = new File("C:\\Users\\Andrey\\Desktop\\BlockChain-last\\app\\src\\test\\java\\ru\\savchenko\\andrey\\blockchain\\services\\exchange\\text.txt");
        List<USD> usds = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
            while ((line = br.readLine()) != null) {
                usds.add(gson.fromJson(line, USD.class));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usds;
    }
}