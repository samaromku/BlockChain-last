package ru.savchenko.andrey.blockchain.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.savchenko.andrey.blockchain.entities.livecoin.Params;

import static ru.savchenko.andrey.blockchain.storage.Const.BASE_URL;

/**
 * Created by Andrey on 12.09.2017.
 */

public class RequestManager {
    private static Retrofit retrofit;
    private static LiveCoinService liveCoinService;

    public static void init() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    private static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public static BitcoinService getRetrofitService() {
        return createService(BitcoinService.class);
    }

    private static String API_KEY = "nzBsPDwCUhu2huNpfjGGtQVw4rWU6eSX";
    private static String SECRET_KEY = "xRxbYC2qCr8vrKUkFUaCwUJYQbRUmjG2";
    public static final java.lang.String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    public static final java.lang.String UNICODE_CODE = "UTF-8";

    private static String buildQueryString(Map<String, String> args) {
        StringBuilder result = new StringBuilder();
        for (String hashKey : args.keySet()) {
            if (result.length() > 0) result.append('&');
            try {
                result.append(URLEncoder.encode(hashKey, "UTF-8"))
                        .append("=").append(URLEncoder.encode(args.get(hashKey), "UTF-8"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    private static String createSignature(String paramData, String plainSecretKey) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(plainSecretKey.getBytes(UNICODE_CODE), HMAC_SHA256_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(secretKey);
            byte[] hmacData = mac.doFinal(paramData.getBytes(UNICODE_CODE));
            return byteArrayToHexString(hmacData).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String byteArrayToHexString(byte[] bytes) {
        final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String getSignedHeader(Params param){
//        Map<String, String> postData = new TreeMap<>();
//        postData.put("currencyPair", param);
        String queryString = buildQueryString(param.getQueryMap());
        return createSignature(queryString, SECRET_KEY);
    }

    public static LiveCoinService getLiveCoinService(){
        if(liveCoinService==null){
            Map<String, String> postData = new TreeMap<>();
//            postData.put("orderId", "88504958");
//            postData.put("currencyPair", "BTC/USD");
//            postData.put("price", "60");
//            postData.put("quantity", "1");
            String queryString = buildQueryString(postData);
            String signature = createSignature(queryString, SECRET_KEY);

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(chain -> {
                        Request request = chain.request();
                        Request newRequest = request.newBuilder()
                                .addHeader("Api-key", API_KEY)
//                                .addHeader("Sign", signature)
                                .build();
                        return chain.proceed(newRequest);
                    })
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();

            liveCoinService = new Retrofit.Builder()
                    .baseUrl("https://api.livecoin.net")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build()
                    .create(LiveCoinService.class);
        }
        return liveCoinService;
    }
}
