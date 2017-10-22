package ru.mtech.moneymate;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mtech.moneymate.helper.ServerRequests;

/**
 * Created by Artyom Vlasov on 21.10.2017.
 * Класс приложения, в котором происходит иициализация HTTP - клиента Retrofit 2.
 */

public class MoneyMateApplication extends Application {

    private Retrofit retrofit;
    private static ServerRequests server;
    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("ссылка на сервер")
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        server = retrofit.create(ServerRequests.class);
    }

    public static ServerRequests response() {
        return server;
    }

}
