package ru.mtech.moneymate.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mtech.moneymate.MoneyMateApplication;
import ru.mtech.moneymate.R;
import ru.mtech.moneymate.helper.Settings;
import ru.mtech.moneymate.object.AuthObject;
import ru.mtech.moneymate.object.Code;
import ru.mtech.moneymate.object.InAppAuth;

/**
 * Created by Artyom Vlasov on 21.10.2017.
 * Activity авторизации в сети Вконтакте и регистрации/авторизации во внутренней системе приложения
 * getUrl - метод получения ссылки для авторизации через VK API. Запрос к серверу выполняется без
 * каких-либо параметров. Результатом получаем ссылку для авторизации и сразу переходим по ней.
 * WebClient - клиент для работы с web-окном. Отслеживается redirect после авторизации, из полученной
 * ссылки извлекается токен для совершения последующих операций с VK API.
 * getAppToken - метод получения токена внутренней системы. В теле запроса отправляется ранее полученный
 * токен из авторизации через ВК. Результатом получается и сохраняется в кэш внутренний токен.
 *
 */

public class AuthWebActivity extends AppCompatActivity {

    @BindView(R.id.web)
    public WebView mWebView;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    private WebClient client;
    @BindView(R.id.progress)
    public ProgressBar progress;
    public String code;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_auth);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Авторизация через VK");
        mWebView.getSettings().setJavaScriptEnabled(true);
        client = new WebClient();
        mWebView.setWebViewClient(client);
        preferences = getSharedPreferences(Settings.SETTINGS, MODE_PRIVATE);
        getUrl();
    }

    public void getUrl() {
        MoneyMateApplication.response().getLink().enqueue(new Callback<AuthObject>() {
            @Override
            public void onResponse(Call<AuthObject> call, Response<AuthObject> response) {
                if (!response.equals(null)) {
                    Log.e("response", new Gson().toJson(response));
                    String url = response.body().getUrl();
                    progress.setVisibility(View.GONE);
                    mWebView.setVisibility(View.VISIBLE);
                    mWebView.loadUrl(url);
                }
            }
            @Override
            public void onFailure(Call<AuthObject> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    class WebClient extends WebViewClient {
        private WeakReference<WebView> mReference;
        private boolean mLoadingFinished = false;
        private String mOnErrorUrl;
        private String mUrl;

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.e("WebActivity", "onPageFinished: " + url);
            url = removeLastSlash(url);
            if (!url.equals(null) && url.contains("https://oauth.vk.com/blank.html")) {
                code = url.substring(37, url.length());
                Log.e("code: ", code);
                getAppToken();
            }
            if (startsWith(url, mUrl) && !mLoadingFinished) {
                Log.e("WebActivity", "Page ");
                mLoadingFinished = true;
                view.removeCallbacks(mPageLoadingTimeoutHandlerTask);
                mOnErrorUrl = null;
                mUrl = null;
            } else if (mUrl == null) {
                view.setWebViewClient(new WebClient());
                mLoadingFinished = true;
            }
        }

        private String removeLastSlash(String url) {
            while (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);
            }
            return url;
        }

        private boolean startsWith(String str, String prefix) {
            return str != null && prefix != null && str.startsWith(prefix);
        }

        private final Runnable mPageLoadingTimeoutHandlerTask = new Runnable() {
            @Override
            public void run() {
                Log.e("WebActivity", "Web page loading timeout: " + mUrl);
                mUrl = null;
                mLoadingFinished = true;
                if (mReference != null) {
                    WebView webView = mReference.get();
                    if (webView != null) {
                        webView.stopLoading();
                    }
                }
            }
        };
    }

    public void getAppToken() {
        progress.setVisibility(View.VISIBLE);
        mWebView.setVisibility(View.GONE);
        Code code = new Code();
        code.setCode(this.code);
        MoneyMateApplication.response().getCode(code).enqueue(new Callback<InAppAuth>() {
            @Override
            public void onResponse(Call<InAppAuth> call, Response<InAppAuth> response) {
                if (!response.equals(null)) {
                    Log.e("responseSend", new Gson().toJson(response.body()));
                    if (response.body().getSuccess().equals("true")) {
                        editor = preferences.edit();
                        editor.putString(Settings.TOKEN, response.body().getCode());
                        editor.putBoolean(Settings.IF_AUTH, true);
                        editor.apply();
                        startActivity(new Intent(AuthWebActivity.this, MainActivity.class));
                    } else {
                        progress.setVisibility(View.GONE);
                        mWebView.setVisibility(View.VISIBLE);
                        Toast.makeText(AuthWebActivity.this, "Произошла ошибка", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<InAppAuth> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });
    }
}
