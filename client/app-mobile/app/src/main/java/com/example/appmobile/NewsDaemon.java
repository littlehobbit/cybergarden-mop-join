package com.example.appmobile;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.appmobile.net.JSONPlaceHolderApi;
import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.News;
import com.example.appmobile.net.entries.NewsListResults;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class NewsDaemon extends Service {

    boolean mAllowRebind;
    List<NewsListResults> oldNews;
    static int notigficationID = 0;
    boolean inited = false;

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "News";
            String description = "You have fresh news";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Abitur_news", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onCreate() {
        Context ctx = this;
        createNotificationChannel();
        NetworkService.getInstance().getJSONApi().getNewsList().enqueue(new Callback<ArrayList<NewsListResults>>() {
            @Override
            public void onResponse(Call<ArrayList<NewsListResults>> call, Response<ArrayList<NewsListResults>> response) {
                if(response.isSuccessful()){
                    oldNews = new ArrayList<>(response.body());
                    inited = true;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NewsListResults>> call, Throwable t) {
                Toast.makeText(ctx, "update failed", Toast.LENGTH_SHORT);
            }
        });
        TimerTask task = new TimerTask() {
            public void run(){
                Log.w("tick", "tack");
                NetworkService.getInstance().getJSONApi().getNewsList().enqueue(new Callback<ArrayList<NewsListResults>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NewsListResults>> call, Response<ArrayList<NewsListResults>> response) {
                        if(!inited){
                            oldNews = new ArrayList<>(response.body());
                            inited = true;
                        }
                        else{
                            Log.w("tack", "tick");
                            List<NewsListResults> newNews = new ArrayList<>(response.body());
                            if(newNews.size() > oldNews.size()){
                                Log.w("tack", "tack");
                                Intent intent = new Intent(getApplicationContext(), SignInScreen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "Abitur_news")
                                        .setSmallIcon(R.mipmap.ic_launcher_round)
                                        .setContentTitle("You received fresh news")
                                        .setContentText(newNews.get(0).getTitle())
                                        .setContentIntent(pendingIntent)
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                oldNews = newNews;
                                NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                                manager.notify(notigficationID++, builder.build());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<NewsListResults>> call, Throwable t) {

                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 60000, 60000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    @Override
    public void onRebind(Intent intent) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
        Toast.makeText(this, "killed", Toast.LENGTH_SHORT).show();
    }
}
