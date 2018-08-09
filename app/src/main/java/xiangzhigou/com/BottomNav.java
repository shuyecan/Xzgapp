package xiangzhigou.com;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.been.BottomBeen;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import Ui.HomeFragment;
import Ui.MissionFragment;
import Ui.RankingFragment;
import Ui.ShenpiFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import other.User;

public class BottomNav extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home:
                mdrawerlayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar,menu);
//        return true;
//    }
    private RelativeLayout nav_head;
    private NavigationView nav_view;
    private DrawerLayout mdrawerlayout;
    private Toolbar toolbar;
    private  BottomBar bottomBar;
    private HomeFragment homeFragment;
    private RankingFragment rankingFragment;
    private ShenpiFragment shenpiFragment;
    private MissionFragment missionFragment;
    private FragmentManager fragmentManager;
    private String user;
    private ActionBar actionBar;
    private TextView navName_tv,nav_phone,nav_department,nav_banben;
    private ImageView icon_image;
    final BottomNav.MyHandler handler=new BottomNav.MyHandler(this);
    class MyHandler extends Handler {
        private final WeakReference<BottomNav> mActivity;
        public MyHandler(BottomNav activity) {
            mActivity=new WeakReference<BottomNav>(activity);
        }
        public void handleMessage(android.os.Message msg) {
            BottomNav activity=mActivity.get();
            if(activity!=null){
                switch (msg.what){
                    case 1:
                        Bundle b = msg.getData();
                        String phone = b.getString("phone");
                        String department = b.getString("department");
                        String headImg = b.getString("headImg");
                        nav_phone.setText("电话:"+phone);
                        nav_department.setText("部门:"+department);
                        Glide.with(activity).load(headImg) //异常时候显示的图片
                                .placeholder( R.drawable.cuowu).dontAnimate().error( R.drawable.cuowu).into(icon_image);
                        break;
                    case 2:

                        break;
                }
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
//         toolbar = findViewById(R.id.toolbar);
        fragmentManager = getFragmentManager();
//        setSupportActionBar(toolbar);
        initView();
        initData();
    }
//初始化所有数据
    private void initData() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    final MediaType Json = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(Json,"1");
                    List<User> userList = DataSupport.select("token").where("username = ?",user).find(User.class);
                    Request request = new Request.Builder().addHeader("user-token",userList.get(0).getToken())
                            .url("http://120.78.95.148/attendance/user/appuserinfo").post(body)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Message msg = new Message().obtain();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                           try {
                                Log.d("data", "成功");
                                String req = response.body().string();
                                Gson gson = new Gson();
                               Log.d("data",req);
                                BottomBeen bottomBeen = gson.fromJson(req, BottomBeen.class);
                                Message msg = new Message().obtain();
                                msg.what = 1;
                                Bundle b = new Bundle();
                                b.putString("niceName", bottomBeen.getNickName());
                                b.putString("headImg", bottomBeen.getHeadImg());
                                b.putString("department", bottomBeen.getDepartment());
                                b.putString("phone",bottomBeen.getMobile());
                                msg.setData(b);
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

        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(),0);
            String version = packInfo.versionName;
            Menu munu =   nav_view.getMenu();
            munu.findItem(R.id.nav_banben).setTitle("versionCode:"+version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
    //初始化所有布局和控件
    private void initView() {
        Intent intent= getIntent();
        user =  intent.getStringExtra("username");
        @SuppressLint("WrongConstant")
        SharedPreferences sp=getSharedPreferences("abc",MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user",user);
        editor.commit();
        nav_view = findViewById(R.id.nav_view);
        bottomBar = findViewById(R.id.bottomBar);
        //加载侧滑头部布局
        View draw = nav_view.inflateHeaderView(R.layout.nav_header);
       //初始化侧滑头部的布局
        nav_head =  draw.findViewById(R.id.nav_head_color);
        navName_tv = draw.findViewById(R.id.nav_name);
        navName_tv.setText(user);
        nav_banben = draw.findViewById(R.id.nav_banben);
        nav_department = draw.findViewById(R.id.nav_department);
        nav_phone = draw.findViewById(R.id.nav_phone);
        icon_image = draw.findViewById(R.id.icon_image);
        mdrawerlayout = findViewById(R.id.drawer_lay);
        //自定义头部的实现
//        appBarLayout = findViewById(R.id.AppBar);
//         actionBar = getSupportActionBar();
//        if(actionBar !=null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.mipmap.mian);
//        }
        //侧滑菜单点击事件

        nav_view.setCheckedItem(R.id.nav_call);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mdrawerlayout.closeDrawers();
                Log.d("ss",item.toString());
                switch (item.toString()){
                    case "主页":
                        bottomBar.post(new Runnable() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        bottomBar.onClick(bottomBar.getTabAtPosition(0));
                                    }
                                });
                            }
                        });
                        break;
                    case "任务":
                        bottomBar.post(new Runnable() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        bottomBar.onClick(bottomBar.getTabAtPosition(1));
                                    }
                                });
                            }
                        });
                        break;
                    case "审批":
                        bottomBar.post(new Runnable() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        bottomBar.onClick(bottomBar.getTabAtPosition(2));
                                    }
                                });
                            }
                        });
                        break;
                    case "排名":
                        bottomBar.post(new Runnable() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        bottomBar.onClick(bottomBar.getTabAtPosition(3));
                                    }
                                });
                            }
                        });
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        setDefaultFragment();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                hideFragment(fragmentTransaction);
                switch (tabId){
                    case R.id.navigation_home:
                        nav_view.setCheckedItem(R.id.nav_call);
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                            fragmentTransaction.add(R.id.framecontent, homeFragment);
                        } else {
                            fragmentTransaction.show(homeFragment);
                        }
                        break;
                    case R.id.navigation_dashboard:
                        nav_view.setCheckedItem(R.id.nav_friend);
                        if (missionFragment == null) {
                            missionFragment = new MissionFragment();
                            fragmentTransaction.add(R.id.framecontent, missionFragment);
                        } else {
                            fragmentTransaction.show(missionFragment);
                        }
                        break;
                    case R.id.navigation_notifications:
                        nav_view.setCheckedItem(R.id.nav_location);
                        if (shenpiFragment == null) {
                            shenpiFragment = new ShenpiFragment();
                            fragmentTransaction.add(R.id.framecontent, shenpiFragment);
                        } else {
                            fragmentTransaction.show(shenpiFragment);
                        }
                        break;
                    case R.id.navigation_paihang:
                        nav_view.setCheckedItem(R.id.nav_email);
                        if (rankingFragment == null) {
                            rankingFragment = new RankingFragment();
                            fragmentTransaction.add(R.id.framecontent, rankingFragment);
                        } else {
                            fragmentTransaction.show(rankingFragment);
                        }
                        break;
                }
                fragmentTransaction.commit();
            }

//            private void setColor(int color) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    Window window = getWindow();
//                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                    window.setStatusBarColor(getResources().getColor(color));
//                    nav_head.setBackgroundColor(getResources().getColor(color));
//                }
//            }
        });
    }
//设置默认的Fragment布局
    private void setDefaultFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.framecontent, homeFragment);
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (missionFragment != null) {
            fragmentTransaction.hide(missionFragment);
        }
        if (shenpiFragment != null) {
            fragmentTransaction.hide(shenpiFragment);
        }
        if (rankingFragment != null) {
            fragmentTransaction.hide(rankingFragment);
        }
    }
//双击返回退出；
        private long exitTime = 0;
         @Override
         public boolean onKeyDown(int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                         && event.getAction() == KeyEvent.ACTION_DOWN) {
                        if ((System.currentTimeMillis() - exitTime) > 2000) {
                            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                      exitTime = System.currentTimeMillis();
               } else {
                 finish();
                 }return true;
                  }
            return super.onKeyDown(keyCode, event);
          }
}
