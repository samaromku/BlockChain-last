package ru.savchenko.andrey.blockchain.network;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import ru.savchenko.andrey.blockchain.entities.livecoin.ClientOrder;
import ru.savchenko.andrey.blockchain.entities.livecoin.LiveCoinModel;
import ru.savchenko.andrey.blockchain.entities.livecoin.Order;

/**
 * Created by Andrey on 06.11.2017.
 */

public interface LiveCoinService {
    @GET("exchange/ticker?currencyPair=BTC/USD")
    public Observable<LiveCoinModel> getCurrentLiveCoin();

    @GET("/exchange/client_orders")
    public Observable<List<ClientOrder>> getClientOrders();


    @GET("/exchange/order")
    public Observable<Order> getOrderInfo(@Header("Sign")String header, @Query("orderId") String order);

    @POST("/exchange/buymarket")
    public Observable<Order> openOrderLimit(@Header("Sign")String header, @QueryMap Map<String, String>stringMap);
}
