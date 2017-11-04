package ru.savchenko.andrey.blockchain.activities;

import com.arellomobile.mvp.MvpView;

/**
 * Created by Andrey on 01.11.2017.
 */

public interface MainView extends MvpView {
    void updateAdapter();
    void showToast(String text);
}
