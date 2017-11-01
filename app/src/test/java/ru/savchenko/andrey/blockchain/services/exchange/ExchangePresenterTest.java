package ru.savchenko.andrey.blockchain.services.exchange;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.reactivex.Observable;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;

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

    @Before
    public void setup() {
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
    }
}