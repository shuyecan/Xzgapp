package Ui;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.been.Ranking;
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
import other.RankingAp;
import other.User;
import xiangzhigou.com.R;

import static android.content.Context.MODE_PRIVATE;

public class RankingFragment extends android.app.Fragment{
    private View fragment_ranking;
    private RankingAp adapter;
    private String  user;
    private ImageView img_rank;
    private List<Ranking.DataBean> list = new ArrayList<>();
    private List<Ranking.DataBean.IntegralListBean> list2 = new ArrayList<>();
    final RankingFragment.MyHandler handler= new RankingFragment.MyHandler(this);
    class MyHandler extends Handler {
        private final WeakReference<RankingFragment> mActivity;
        public MyHandler(RankingFragment activity) {
            mActivity=new WeakReference<RankingFragment>(activity);
        }
        public void handleMessage(android.os.Message msg) {
            RankingFragment activity=mActivity.get();
            if(activity!=null){
                switch (msg.what){
                    case 1:
                        RecyclerView recyclerView = getActivity().findViewById(R.id.RecyclerView);
                        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
                        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new RankingAp(list,list2);
                        recyclerView.setAdapter(adapter);
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "错误", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    };
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if ( fragment_ranking == null) {
            fragment_ranking = inflater.inflate(R.layout.fragment_ranking,
                    container, false);
            return  fragment_ranking;
        }
        return  fragment_ranking;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMyView();
    }
   /*
   *@作者:舒椰
   *@date: 2018/8/3 15:44
   * 一下方法可以将状态栏背景设置为透明
   */

//    @TargetApi(19)
//    private void setTranslucentStatus(boolean on) {
//        Window win = getActivity().getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }

    private void initMyView() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar_rank);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar =  ((AppCompatActivity) getActivity()).getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.mian);
        }
        img_rank = getActivity().findViewById(R.id.img_rank);
        Glide.with(this).load(R.drawable.beit).into(img_rank);
        initMyData();
    }


/*
*@作者:舒椰
*@date: 2018/7/12 13:55
* 此处设置下拉刷新和上拉加载
*/


    private void initMyData() {
        SharedPreferences sp= getActivity().getSharedPreferences("abc",MODE_PRIVATE);
                user=sp.getString("user","null");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    MediaType Json = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(Json,"1");
                    List<User> userList = DataSupport.select("token").where("username = ?",user).find(User.class);
                    Request request = new Request.Builder().addHeader("user-token",userList.get(0).getToken())
                            .url("http://120.78.95.148/attendance/user/ranking").post(body)
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
                                Ranking ranking = gson.fromJson(req,Ranking.class);
                                list = ranking.getData();
                                for (int i=0;i<list.size();i++){
                                    list2.add(ranking.getData().get(i).getIntegralList().get(0));
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
}
