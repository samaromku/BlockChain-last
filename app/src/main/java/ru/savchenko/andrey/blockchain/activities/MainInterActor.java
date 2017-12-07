package ru.savchenko.andrey.blockchain.activities;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.savchenko.andrey.blockchain.di.ComponentManager;
import ru.savchenko.andrey.blockchain.entities.MoneyCount;
import ru.savchenko.andrey.blockchain.entities.USD;
import ru.savchenko.andrey.blockchain.interfaces.ChangeTextViewTest;
import ru.savchenko.andrey.blockchain.repositories.BaseRepository;
import ru.savchenko.andrey.blockchain.repositories.IBaseRepository;
import ru.savchenko.andrey.blockchain.services.exchange.ExchangeInteractor;

/**
 * Created by Andrey on 01.11.2017.
 */

public class MainInterActor {
    public static final String TAG = "MainInterActor";
    private IBaseRepository<MoneyCount> moneyCountRepository;
    private IBaseRepository<USD> usdRepository;
    @Inject
    ExchangeInteractor exchangeInteractor;


    public MainInterActor(IBaseRepository<MoneyCount> baseRepository, IBaseRepository<USD> usdRepository) {
        this.moneyCountRepository = baseRepository;
        this.usdRepository = usdRepository;
        ComponentManager.getAppComponent().inject(this);
    }

    Completable initMoneyCount(){
        return Completable.fromAction(() -> {
            MoneyCount moneyCount = moneyCountRepository.getItem();
            if(moneyCount==null){
                moneyCount =new MoneyCount(1, (double)1000, (double)0);
                moneyCountRepository.addItem(moneyCount);
            }
        });
    }

    Completable writeJsonToFile() {
        File file = getFile();
        return Completable.fromAction(() -> {
            if (!file.createNewFile()) {
                Log.i(TAG, "writeJsonToFile: not created");
            } else {
                Log.i(TAG, "writeJsonToFile: success");
            }
            PrintWriter writer = new PrintWriter(file);
            List<USD> usds = new BaseRepository<>(USD.class).getAll();
            for (USD u : usds) {
                writer.println(u.toJson());
            }
            writer.close();
            Log.i(TAG, "writeJsonToFile: " + file.exists() + " " + file.length());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Completable readFile() {
        return Completable.fromAction(() -> {
            if(!usdRepository.getAll().isEmpty())return;
            List<USD> usds = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(getFile()));

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
            Log.i(TAG, "readFile: " + usds.size());
            usdRepository.addAll(usds);
            Log.i(TAG, "readFile: " + usdRepository.getAll());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private File getAlbumStorageDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File dir = new File(Environment.getExternalStorageDirectory() + "/test");
            if (!dir.mkdir()) {
                Log.e(TAG, "Directory not created");
            }
            return dir;
        } else return null;
    }

    private File getFile(){
        File dir = getAlbumStorageDir();
        Log.i(TAG, "onCreate: " + dir.exists() + " " + dir.getPath());
        File file = new File(dir, "text.txt");
        return file;
    }

    public Observable<MoneyCount> testFileList(ChangeTextViewTest changeTextViewTest){
        return Observable.fromCallable(() -> {
            List<USD>usds = listFromFile();
//            for (int i = 0; i < 100; i++) {
//                USD u = usds.get(i);
            for (USD u:usds) {
                exchangeInteractor.testUSDList(u)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(moneyCount -> {
                    if(moneyCount.isBuyOrSell()!=null){
                        changeTextViewTest.changeTextView(moneyCount.getUsdCount() + " " + moneyCount.getBitCoinCount());
                        Log.i(TAG, "testFileList: " + moneyCount + " usd " + u.minStr());
                    }
                });
            }
            return moneyCountRepository.getItemCopy();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    private List<USD> listFromFile(){
//        File file = new File("C:\\Users\\Andrey\\Desktop\\BlockChain-last\\app\\src\\test\\java\\ru\\savchenko\\andrey\\blockchain\\services\\exchange\\text.txt");
        File file = getFile();
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
