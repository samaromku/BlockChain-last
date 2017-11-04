package ru.savchenko.andrey.blockchain.di.main;

import dagger.Subcomponent;
import ru.savchenko.andrey.blockchain.activities.MainPresenter;
import ru.savchenko.andrey.blockchain.dialogs.settings.SettingsPresenter;

/**
 * Created by Andrey on 01.11.2017.
 */
@MainScope
@Subcomponent(modules = MainModule.class)
public interface MainComponent {
    public void inject(MainPresenter presenter);
    public void inject(SettingsPresenter presenter);
}
