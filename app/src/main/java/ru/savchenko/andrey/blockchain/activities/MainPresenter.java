package ru.savchenko.andrey.blockchain.activities;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.savchenko.andrey.blockchain.di.ComponentManager;

/**
 * Created by Andrey on 01.11.2017.
 */
@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    @Inject MainInterActor interActor;

    MainPresenter() {
        ComponentManager.getMainComponent().inject(this);
    }

    void initMoneyCount(){
        interActor.initMoneyCount()
                .subscribe();
    }

    void writeFile(){
        interActor.writeJsonToFile().subscribe();
    }

    void initUsdList(){
        interActor.readFile()
                .subscribe(() -> getViewState().updateAdapter());
    }

//    void testFileList(){
//        interActor.testFileList().subscribe(() -> {
//            getViewState().setErrorText("Конец");
//        }, throwable -> {
//            getViewState().setErrorText("Ошибка");
//        });
//    }
}
