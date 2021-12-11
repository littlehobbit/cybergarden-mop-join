package com.example.appmobile;

import android.os.Bundle;
import android.view.Menu;

import com.example.appmobile.net.NetworkService;
import com.example.appmobile.net.entries.NewsListResults;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmobile.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Timer timer = new Timer(true);
        TimerTask task = new TimerTask() {
            boolean inited;
            List<NewsListResults> oldNews;

            @Override
            public void run() {
                if(inited){
                    NetworkService.getInstance().getJSONApi().getNewsList().enqueue(new Callback<ArrayList<NewsListResults>>() {
                        @Override
                        public void onResponse(Call<ArrayList<NewsListResults>> call, Response<ArrayList<NewsListResults>> response) {
                            if(response.isSuccessful()){
                                List<NewsListResults> newNews = new ArrayList<>(response.body());
                                if(newNews.size() > oldNews.size()) {
                                    for(int i = newNews.size() - oldNews.size() - 1; i >=0; i--){
                                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "ИКТИБ Абитуриентам")
                                            .setSmallIcon(R.drawable.ic_logo)
                                            .setContentTitle(newNews.get(i).getTitle())
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                            .setAutoCancel(true);
                                        NotificationManagerCompat nm = NotificationManagerCompat.from(getApplicationContext());
                                        nm.notify(id++, builder.build());
                                    }
                                }
                                oldNews = newNews;
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<NewsListResults>> call, Throwable t) {

                        }
                    });
                }
                else {
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

                        }
                    });
                }
            }
        };

        timer.schedule(task, 30000, 30000);

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_news, R.id.nav_events, R.id.nav_spec, R.id.nav_user, R.id.nav_qa)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}