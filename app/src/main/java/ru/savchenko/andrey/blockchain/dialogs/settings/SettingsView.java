package ru.savchenko.andrey.blockchain.dialogs.settings;

/**
 * Created by Andrey on 04.11.2017.
 */

public interface SettingsView {
    void setErrorText(String text);
    void setTestValue(String text);
    void showProgressDialog();
    void hideProgressDialog();
}
