package ru.savchenko.andrey.blockchain.dialogs.settings;

import javax.inject.Inject;

import ru.savchenko.andrey.blockchain.activities.MainInterActor;
import ru.savchenko.andrey.blockchain.di.ComponentManager;
import ru.savchenko.andrey.blockchain.interfaces.ChangeTextViewTest;

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
                                view.setTestValue("USD:" + moneyCount.getUsdCount() + " BTC:" + moneyCount.getBitCoinCount()),
                        throwable -> {
                            view.setErrorText(throwable.getMessage());
                        });
    }

    @Override
    public void changeTextView(String text) {

        view.setTestValue(text);
    }
}
