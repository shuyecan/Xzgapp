package Ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.been.Missionbeen;
import com.been.Ranking;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import other.MissionAp;

import other.RankingAp;
import other.User;
import xiangzhigou.com.R;

import static android.content.Context.MODE_PRIVATE;

public class MissionFragment extends android.app.Fragment{
    private String  user;
    private MissionAp adapter;
    private List<Missionbeen.ItemsBean> list = new ArrayList<>();
    private List<Missionbeen.ItemsBean.HeadImgBean> list2 = new ArrayList<>();
    private View fragment_mission;
    final MissionFragment.MyHandler handler= new MissionFragment.MyHandler(this);
    class MyHandler extends Handler {
        private final WeakReference<MissionFragment> mActivity;
        public MyHandler(MissionFragment activity) {
            mActivity=new WeakReference<MissionFragment>(activity);
        }
        public void handleMessage(android.os.Message msg) {
            MissionFragment activity=mActivity.get();
            if(activity!=null){
                switch (msg.what){
                    case 1:
                        RecyclerView recyclerView = getActivity().findViewById(R.id.mission_recy);
                        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
                        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new MissionAp(list,list2);
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
        if ( fragment_mission == null) {
            fragment_mission = inflater.inflate(R.layout.fragment_mission,
                    container, false);
            return  fragment_mission;
        }
        return  fragment_mission;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMyView();
        initMyDate();
    }

    private void initMyDate() {
        SharedPreferences sp= getActivity().getSharedPreferences("abc",MODE_PRIVATE);
        user=sp.getString("user","null");
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                MediaType Json = MediaType.parse("application/json; charset=utf-8");
                Map map=new HashMap();
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH)+1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String date = year+"-"+month+"-"+day;
                date = date+"/"+date;
                map.put("rangeDate",date);
                String  param= JSON.toJSONString(map);
                RequestBody body = RequestBody.create(Json,param);
                List<User> userList = DataSupport.select("token").where("username = ?",user).find(User.class);
                Request request = new Request.Builder().addHeader("user-token",userList.get(0).getToken())
                        .url("http://120.78.95.148/attendance/mission/part").post(body)
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
                            Missionbeen missionbeen = gson.fromJson(req,Missionbeen.class);

                            list = missionbeen.getItems();
                            Log.d("==============date",req);
                            for (int i=0;i<list.size();i++){
                                list2.add(list.get(i).getHeadImg());
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

    private void initMyView() {

    }
}
