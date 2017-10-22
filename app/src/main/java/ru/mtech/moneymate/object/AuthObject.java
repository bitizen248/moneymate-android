package ru.mtech.moneymate.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Artyom Vlasov on 21.10.2017.
 * Объект в который получается ссылка для авторизации в ВК.
 */

public class AuthObject {

    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }
}
