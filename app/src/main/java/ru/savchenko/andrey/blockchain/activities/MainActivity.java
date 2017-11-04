package ru.savchenko.andrey.blockchain.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.victoralbertos.rx2_permissions_result.RxPermissionsResult;
import ru.savchenko.andrey.blockchain.R;
import ru.savchenko.andrey.blockchain.adapters.USDAdapter;
import ru.savchenko.andrey.blockchain.base.BaseActivity;
import ru.savchenko.andrey.blockchain.dialogs.DateDialog;
import ru.savchenko.andrey.blockchain.dialogs.settings.SettingsDialog;
import ru.savchenko.andrey.blockchain.dialogs.buyorsell.BuyOrSellDialog;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.interfaces.OnItemClickListener;
import ru.savchenko.andrey.blockchain.interfaces.OnRefreshAdapter;
import ru.savchenko.andrey.blockchain.interfaces.SetDataFromDialog;
import ru.savchenko.andrey.blockchain.network.RequestManager;
import ru.savchenko.andrey.blockchain.repositories.BaseRepository;
import ru.savchenko.andrey.blockchain.repositories.USDRepository;
import ru.savchenko.andrey.blockchain.storage.Utils;

import static ru.savchenko.andrey.blockchain.storage.Const.USD_ID;

public class MainActivity extends BaseActivity implements OnItemClickListener, SetDataFromDialog, OnRefreshAdapter, MainView {
    @BindView(R.id.constraintMain)
    LinearLayout constraintMain;
    @BindView(R.id.rvExchange)
    RecyclerView rvExchange;
    @BindView(R.id.srlRefresher)
    SwipeRefreshLayout srlRefresher;
    USDAdapter adapter;
    private List<USD> usds;
    @InjectPresenter
    MainPresenter presenter;

    @OnClick(R.id.fab)
    void fabClick() {
        rvExchange.smoothScrollToPosition(adapter.getItemCount());
    }

    public static final String TAG = "MainActivity";

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        requestPermissions();
        presenter.initUsdList();
        presenter.initMoneyCount();
        initRv();
//        presenter.testFileList();
        int usdId = getIntent().getIntExtra(USD_ID, 0);

        if (usdId != 0) {
            startByOrSellDialog(usdId);
        }



        srlRefresher.setOnRefreshListener(() ->
                RequestManager.getRetrofitService().getExchange()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(exchange -> {
                            new USDRepository().writeIdDb(exchange.getUSD());
                            adapter.notifyDataSetChanged();
                            srlRefresher.setRefreshing(false);
                        }, Throwable::printStackTrace));
    }

    @Override
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    private void requestPermissions() {
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        RxPermissionsResult.on(this).requestPermissions(PERMISSIONS_STORAGE)
                .subscribe(result ->
                        result.targetUI()
                                .showPermissionStatus(result.grantResults())
                );
    }

    void showPermissionStatus(int[] grantResults) {
        boolean granted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        if (granted) {
            presenter.writeFile();
        }
    }

    private void initRv() {
        rvExchange.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvExchange.getContext(), DividerItemDecoration.HORIZONTAL);
        rvExchange.addItemDecoration(dividerItemDecoration);
        adapter = new USDAdapter();
        adapter.setClickListener(this);
        usds = Utils.getUSDListByDate(new Date());
        new USDRepository().addChangeListener(adapter, rvExchange);
        adapter.setDataList(usds);
        rvExchange.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_date:
                DateDialog dateDialog = new DateDialog();
                dateDialog.setSetDataFromDialog(this);
                dateDialog.show(getSupportFragmentManager(), "date");
                return true;
            case R.id.nav_settings:
                SettingsDialog settingsDialog = new SettingsDialog();
                settingsDialog.show(getSupportFragmentManager(), "settings");
                return true;
            case R.id.nav_send:
                presenter.writeFile();
//                requestPermissions();
//                sendMessage();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendMessage() {
        List<USD> usds = new BaseRepository<>(USD.class).getAll();
        String bigString = "";
        for (int i = 0; i < usds.size(); i++) {
            bigString = bigString + usds.get(i).addIntList() + "\n";
        }
        Log.i(TAG, "sendMessage: " + bigString);
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(bigString)
                .getIntent();
        startActivity(Intent.createChooser(shareIntent, "choose"));
    }

    private void startByOrSellDialog(Integer id) {
        BuyOrSellDialog buyOrSellDialog = new BuyOrSellDialog();
        buyOrSellDialog.setUsd(new BaseRepository<>(USD.class).getItemById(id));
        buyOrSellDialog.setOnRefreshAdapter(this);
        buyOrSellDialog.show(getSupportFragmentManager(), "buyOrSell");
    }

    @Override
    public void onClick(int position) {
        startByOrSellDialog(usds.get(position).getId());
    }

    @Override
    public void setData(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0);

        Maybe.fromCallable(() -> new USDRepository().getUSDByCalendarOneDayForward(calendar))
                .subscribe(usds1 -> {
                    usds = usds1;
                    adapter.setDataList(usds1);
                    adapter.notifyDataSetChanged();
                });
    }

    @Override
    public void refreshAdapter() {
        adapter.notifyDataSetChanged();
    }
}
