package ru.mtech.moneymate.helper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.mtech.moneymate.object.AuthObject;
import ru.mtech.moneymate.object.Code;
import ru.mtech.moneymate.object.Feed;
import ru.mtech.moneymate.object.Friend;
import ru.mtech.moneymate.object.InAppAuth;
import ru.mtech.moneymate.object.Message;

/**
 * Created by Artyom Vlasov on 21.10.2017.
 * Интерфейс, в котором прописаны заготовки для выполнения запросов к серверу.
 */

public interface ServerRequests {
    @GET("/moneymate_api/register/")
    Call<AuthObject> getLink();

    @POST("/moneymate_api/register/")
    Call<InAppAuth> getCode(@Body Code code);

    @GET("/moneymate_api/roomsAndUsers/lastUpdates/")
    Call<List<Friend>> getLastFriendList(@Header("token") String token);

    @GET("/moneymate_api/roomsAndUsers/getRoomsAndUsers/")
    Call<List<Message>> getFriendsAndRooms(@Header("token") String token);

    @GET("/moneymate_api/transations/feed/")
    Call<List<Feed>> getFeed(@Header("token") String token);

}
