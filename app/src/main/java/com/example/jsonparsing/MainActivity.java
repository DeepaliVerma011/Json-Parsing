package com.example.jsonparsing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "json";
    Button button;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateTextView();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void updateTextView() throws IOException {
                //Make the network call here
                // tv=findViewById(R.id.tv);
              //  NetworkTask networkTask= new NetworkTask();
               // networkTask.execute("https://api.github.com/search/users?q=harshit","https://www.google.com","https://www.github.com");
                makeNetworkCall("https://api.github.com/search/users?q=harshit");

            }

            void makeNetworkCall(String url) throws IOException {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                 client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                         String result= response.body().string();
                        //ArrayList<GithubUser> users= null;
                        //GithubAdapter githubAdapter= new GithubAdapter(users);
//                        RecyclerView recyclerView=findViewById(R.id.rv);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//                        recyclerView.setAdapter(githubAdapter);

                      Gson gson =new Gson();
                      ApiResult apiResult=gson.fromJson(result,ApiResult.class);
                        GithubAdapter githubAdapter= new GithubAdapter(apiResult.getItems());

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //code here runs on Main thread
                                RecyclerView recyclerView=findViewById(R.id.rv);
                 recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                 recyclerView.setAdapter(githubAdapter);
                            }
                        });

                    }
                });
            }
            class NetworkTask extends AsyncTask<String,Void,String> {



                @Override
                protected String doInBackground(String... strings) {
                    String stringurl=strings[0];
                    try {
                        URL url = new URL(stringurl);
                        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                        InputStream inputStream=httpURLConnection.getInputStream();
                        Scanner scanner=new Scanner(inputStream);
//scanner.next(); or we can use delinitar then call next which allow us to entire stream in one go
                        scanner.useDelimiter("//A");
                        if(scanner.hasNext()){
                            String s= scanner.next();
                            return s;
                        }

                    }
                    catch (MalformedURLException e){
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return "Failed to load";
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                   // tv=findViewById(R.id.tv);
                    //tv.setText(s);
                    ArrayList<GithubUser> users= null;

                    try {
                        users = parseJson(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                   // Log.e(TAG,"onPostExecute:"+users.size());
                   // Log.e(TAG,"onPostExecute:"+users.get(7).getHtml_url());
                    GithubAdapter githubAdapter= new GithubAdapter(users);
                    RecyclerView recyclerView=findViewById(R.id.rv);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    recyclerView.setAdapter(githubAdapter);
                }

            }

            //we don't need this json parse function when we import gson we just have to make apiResult class and GithubUser
//JSON PARSING FOR STRUCTURED DATA:we are making an array of type GithubUser called githubusers which
// will return a "githubuser" of type GithubUser from the item array of url(String s)[login,id,avatar_url,score,html_url)
            //then using Adapter we will show this data in Activity_main

            ArrayList<GithubUser> parseJson(String s) throws JSONException {
                ArrayList<GithubUser> githubUsers= new ArrayList<>();
                JSONObject root= new JSONObject(s);
                JSONArray item= root.getJSONArray("items");
                for(int i=0;i<item.length();i++){
                    JSONObject object=item.getJSONObject(i);
                    String login= object.getString("login");
                    Integer id= object.getInt("id");
                    String avatar_url=object.getString("avatar_url");
                    Double score=object.getDouble("score");
                    String html_url=object.getString("html_url");
                    GithubUser githubUser=new GithubUser(login,id,avatar_url,score,html_url);
                    githubUsers.add(githubUser);

                }
                return githubUsers;
                //parseJson

            }
        }
        );


    }
}