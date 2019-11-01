package com.rustli.rxmodule.rxjava;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.rustli.rxmodule.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxjavaActivity extends AppCompatActivity {

    @BindView(R.id.iv_rxtest)
    ImageView ivRxtest;
    @BindView(R.id.tv_rxjava_test)
    TextView tvRxjavaTest;

    private static final String TAG = "RxjavaActivity";
    private Context mContext;
    private OkHttpClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(this);
        mContext = this;
        showHttpLog();
        request();
    }

    @OnClick({R.id.tv_rxjava_test, R.id.iv_rxtest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_rxjava_test:
//                showImageView();
//                retrofit2Test();
                retrofitTest();
                break;
            case R.id.iv_rxtest:
                break;
        }
    }


    public void request() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        PostRequest request = retrofit.create(PostRequest.class);

        //对 发送请求 进行封装(设置需要翻译的内容)
        Call<Translation> call = request.getCall("you are a child");

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation>() {

            //请求成功时回调
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                // 请求处理,输出结果
                // 输出翻译的内容
                System.out.println("翻译是："+ response.body().getTranslateResult().get(0).get(0).getTgt());
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Translation> call, Throwable throwable) {
                System.out.println("请求失败");
                System.out.println(throwable.getMessage());
            }
        });
    }


    private void showImageView() {

        Observable.create(new ObservableOnSubscribe<Drawable>() {

            @Override
            public void subscribe(ObservableEmitter<Drawable> e) throws Exception {
                Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher);
                e.onNext(drawable);
            }
        }).subscribe(new Consumer<Drawable>() {

            @Override
            public void accept(Drawable s) {
                ivRxtest.setImageDrawable(s);
            }
        }).dispose();
    }


    private void retrofit2Test(){
        String baseUrl = "http://apis.juhe.cn/";
        String url = baseUrl + "mobile/get?phone=18856907654&key=5778e9d9cf089fc3b093b162036fc0e1";

        // 设置网络请求的Url地址
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                // 支持RxJava平台
                //compile 'com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0'   是他 就是他
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        retrofit.create(ApiService.class)
                .getData(url)//注意看，这里返回的对象时什么，和原生的返回不同，也是我们把上面接口改的原因
                //在子线程取数据
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                //在主线程显示ui
                // compile 'io.reactivex.rxjava2:rxandroid:2.0.1'  这个库下才有AndroidSchedulers.mainThread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.d(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void retrofitTest(){

        // 设置网络请求的Url地址
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                // 支持RxJava平台
                //compile 'com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0'   是他 就是他
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        retrofit.create(ApiService.class)
                .getCall("it is a apple")//注意看，这里返回的对象时什么，和原生的返回不同，也是我们把上面接口改的原因
                //在子线程取数据
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                //在主线程显示ui
                // compile 'io.reactivex.rxjava2:rxandroid:2.0.1'  这个库下才有AndroidSchedulers.mainThread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Translation translation1) {
                        Log.d(TAG, "onNext: translation1 = " + translation1.getTranslateResult().get(0).get(0).getTgt());
//                        System.out.println("翻译是："+ response.body().getTranslateResult().get(0).get(0).getTgt());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void showHttpLog(){
        long mTimeOut = 500;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog","retrofitBack = "+message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

         client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(mTimeOut, TimeUnit.SECONDS)
                .readTimeout(mTimeOut, TimeUnit.SECONDS)
                .writeTimeout(mTimeOut, TimeUnit.SECONDS)
                .build();
    }
}
