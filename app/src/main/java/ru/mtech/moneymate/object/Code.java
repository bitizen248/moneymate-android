package ru.mtech.moneymate.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Artyom Vlasov on 21.10.2017.
 * Объект, который отправляется в запросе getAppToken.
 * code - код из ссылки авторизации ВК.
 */

public class Code {

    @SerializedName("code")
    @Expose
    private String code;

    public void setCode(String code) {
        this.code = code;
    }
}
