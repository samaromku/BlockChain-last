package ru.savchenko.andrey.blockchain.services.exchange;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
    public void sellUSD() throws Exception {
        PowerMockito.mockStatic(Log.class);
        given(interactor.buyOrSellMethod()).willReturn(Observable.just(moneyCount));
        presenter.sellUSD();
        verify(view).showNotify(moneyCount);
        System.out.println(new Gson().fromJson(usdStr2, USD.class).get5m());
        List<USD>usds = new USDRepository().getUsdStartList();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(USD u:usds){
            System.out.println(u.toString());
            sb.append(u.toString());
        }
        sb.append("]");
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
        List<USD>usds1 = gson.fromJson(sb.toString(), new TypeToken<List<USD>>(){}.getType());
        System.out.println(usds1);
//        USD usd = new Gson().toJson(new USD(594, 6001.08, 6001.63, 6001.08, 6000.52, "$", new Date(1508722909241L), 0, null));
//        System.out.println(new Gson().toJson(new USD(594, 6001.08, 6001.63, 6001.08, 6000.52, "$", new Date(1508722909241L), 0, null)));
    }

    public class JsonDateDeserializer implements JsonDeserializer<Date> {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String s = json.getAsJsonPrimitive().getAsString();
            long l = Long.parseLong(s.substring(6, s.length() - 2));
            Date d = new Date(l);
            return d;
        }
    }
}