package com.example.assignmentapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.assignmentapp.dao.UserDao;
import com.example.assignmentapp.database.AppDatabase;
import com.example.assignmentapp.utils.DatabaseInitializer;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PlaceAdapter placeAdapter;
    private List<POJO> list = new ArrayList<>();
    private IRetrofit apiService;
    private String baseurl = "https://restcountries.eu/rest/v2/region/";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("ACT",MODE_PRIVATE);
        mRecyclerView = findViewById(R.id.countryrec);
        setupRetrofitAndOkHttp();
        if(!sharedPreferences.getString("ACT","No").equals("yes")) {
            runAPI();
            Toast.makeText(MainActivity.this, "Data from API", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this,"Data from Database",Toast.LENGTH_SHORT).show();
            GetDbAsync task = new GetDbAsync(MainActivity.this);
            task.execute();
        }
    }

    public void runAPI(){

        Call<List<POJO>> call = apiService.getData();
        call.enqueue(new Callback<List<POJO>>() {
            @Override
            public void onResponse(Call<List<POJO>> call, Response<List<POJO>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    try {
                        list = response.body();
                        DatabaseInitializer.populateAsync(AppDatabase.getAppDatabase(MainActivity.this), list,MainActivity.this);
                        buildrecyclerview();
                        editor = sharedPreferences.edit();
                        editor.putString("ACT", "yes");
                        editor.apply();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i("Error", e.getLocalizedMessage() + " " + response.toString());
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "SORRY", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<POJO>> call, Throwable t) {
                Log.i("Error", t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void buildrecyclerview(){
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        placeAdapter = new PlaceAdapter(this,list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(placeAdapter);
    }

    private void setupRetrofitAndOkHttp() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File httpCacheDirectory = new File(getCacheDir(), "offlineCache");

        //10 MB
        Cache cache = new Cache(httpCacheDirectory, 15 * 1024 * 1024);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(provideCacheInterceptor())
                .addInterceptor(provideOfflineCacheInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(httpClient)
                .baseUrl(baseurl)
                .build();

        apiService = retrofit.create(IRetrofit.class);

    }

    private Interceptor provideCacheInterceptor() {

        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                okhttp3.Response originalResponse = chain.proceed(request);
                String cacheControl = originalResponse.header("Cache-Control");

                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                        cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")) {


                    CacheControl cc = new CacheControl.Builder()
                            .maxStale(1, TimeUnit.DAYS)
                            .build();



                    request = request.newBuilder()
                            .cacheControl(cc)
                            .build();

                    return chain.proceed(request);

                } else {
                    return originalResponse;
                }
            }
        };

    }


    private Interceptor provideOfflineCacheInterceptor() {

        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                try {
                    return chain.proceed(chain.request());
                } catch (Exception e) {


                    CacheControl cacheControl = new CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(1, TimeUnit.DAYS)
                            .build();

                    Request offlineRequest = chain.request().newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                    return chain.proceed(offlineRequest);
                }
            }
        };
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void rundatabase(List<POJO> country){
        list = country;
        buildrecyclerview();
    }

    private class GetDbAsync extends AsyncTask<Void, Void, Void> {


        private Context context;
        private List<POJO> countries;

        GetDbAsync(Context context) {
            this.context = context;

        }

        @Override
        protected Void doInBackground(final Void... params) {
            //populateWithTestData(mDb);
            AppDatabase db = Room.databaseBuilder(context,
                    AppDatabase.class, "Countries").build();
            UserDao userDao = db.userDao();
            List<POJO> country = userDao.getAll();
            countries = country;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            rundatabase(countries);
        }
    }
}