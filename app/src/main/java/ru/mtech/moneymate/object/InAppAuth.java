package ru.mtech.moneymate.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Artyom Vlasov on 21.10.2017.
 * Объект авторизации во внутренней системе.
 */

public class InAppAuth {

    @SerializedName("success")
    @Expose
    private String success;

    @SerializedName("token")
    @Expose
    private String code;

    public String getSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }
}
