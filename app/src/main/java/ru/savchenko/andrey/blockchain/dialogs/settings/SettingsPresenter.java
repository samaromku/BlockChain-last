package ru.savchenko.andrey.blockchain.dialogs.settings;

import java.util.Arrays;

import javax.inject.Inject;

import ru.savchenko.andrey.blockchain.activities.MainInterActor;
import ru.savchenko.andrey.blockchain.di.ComponentManager;
import ru.savchenko.andrey.blockchain.interfaces.ChangeTextViewTest;
import ru.savchenko.andrey.blockchain.storage.Utils;

/**
 * Created by Andrey on 04.11.2017.
 */

public class SettingsPresenter implements ChangeTextViewTest {
    private SettingsView view;
    @Inject
    MainInterActor interActor;

    SettingsPresenter(SettingsView view) {
        this.view = view;
        ComponentManager.getMainComponent().inject(this);
    }

    void testFileList() {
        interActor.testFileList(this)
                .doOnSubscribe(disposable -> view.showProgressDialog())
                .doAfterTerminate(() -> view.hideProgressDialog())
                .subscribe(moneyCount ->
                                view.setTestValue("USD:" + Utils.getFormattedStringOfDouble(moneyCount.getUsdCount())
                                        + "\nBTC:" + Utils.getFormattedStringOfDouble(moneyCount.getBitCoinCount())),
                        throwable -> {
                            throwable.printStackTrace();
                            view.setErrorText(Arrays.toString(throwable.getStackTrace()));
                        });
    }

    @Override
    public void changeTextView(String text) {

        view.setTestValue(text);
    }
}
