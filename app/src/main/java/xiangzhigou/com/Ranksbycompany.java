package xiangzhigou.com;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.been.RankbycompanyBeen;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import other.RankcompanyAp;
import other.User;

/**
 * Created by Administrator on 2018/8/6.
 */

public class Ranksbycompany extends AppCompatActivity{
    private RecyclerView rankRecycler;
    private RankcompanyAp adapter;
    private  String user;
    private List<RankbycompanyBeen.ItemsBean> list = new ArrayList<>();
    private List<RankbycompanyBeen.ItemsBean.IntegralListBean> list2 = new ArrayList<>();
    final Ranksbycompany.MyHandler handler= new Ranksbycompany.MyHandler(this);
    class MyHandler extends Handler {
        private final WeakReference<Ranksbycompany> mActivity;
        public MyHandler(Ranksbycompany activity) {
            mActivity=new WeakReference<Ranksbycompany>(activity);
        }
        public void handleMessage(android.os.Message msg) {
            Ranksbycompany activity=mActivity.get();
            if(activity!=null){
                switch (msg.what){
                    case 1:
                        GridLayoutManager layoutManager = new GridLayoutManager(activity,1);
                        rankRecycler.addItemDecoration(new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL));
                        rankRecycler.setLayoutManager(layoutManager);
                        adapter = new RankcompanyAp(list,list2);
                        rankRecycler.setAdapter(adapter);
                        break;
                    case 2:
                        Toast.makeText(activity, "失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(activity, "错误", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranksbycompany);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }else  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initView();
        initMyData();
    }

    private void initMyData() {
        SharedPreferences sp= getSharedPreferences("abc",MODE_PRIVATE);
        user=sp.getString("user","null");
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                MediaType Json = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(Json,"1");
                List<User> userList = DataSupport.select("token").where("username = ?",user).find(User.class);
                Request request = new Request.Builder().addHeader("user-token",userList.get(0).getToken())
                        .url("http://120.78.95.148/attendance/user/allRanking").post(body)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message msg = new Message().obtain();
                        msg.what = 3;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String req = response.body().string();
                            Gson gson = new Gson();
                            RankbycompanyBeen ranking = gson.fromJson(req,RankbycompanyBeen.class);
                            list = ranking.getItems();
                            for (int i=0;i<list.size();i++){
                                list2.add(ranking.getItems().get(i).getIntegralList().get(0));
                            }
                            Message msg = new Message().obtain();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }catch (Exception e){
                            Message msg = new Message().obtain();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();
    }

    private void initView() {
        rankRecycler = findViewById(R.id.rankRecycler);
        Toolbar toolbar = findViewById(R.id.toolbar_companyranks);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
