package xiangzhigou.com;
import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.been.LoginBeen;
import com.dx.dxloadingbutton.lib.LoadingButton;
import com.google.gson.Gson;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.lang.ref.WeakReference;
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
import other.User;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView Username_view;
    private EditText Password_view;
    private LoadingButton Login_btn;
    private String req;
    private SharedPreferences sp;
    final MyHandler handler= new MyHandler(this);
    class MyHandler extends Handler {
        private final WeakReference<LoginActivity> mActivity;
        public MyHandler(LoginActivity activity) {
            mActivity=new WeakReference<LoginActivity>(activity);
        }
        public void handleMessage(android.os.Message msg) {
            LoginActivity activity=mActivity.get();
            if(activity!=null){
                switch (msg.what){
                    case 1:
                        Bundle b = msg.getData();
                        int Code = b.getInt("Code");
                        if(Code==200) {
                            String username = b.getString("username");
                            String password = b.getString("password");
                            String token = b.getString("token");
                            Intent intent = new Intent(activity, BottomNav.class);
                            intent.putExtra("username",username);
                            intent.putExtra("key", 4);
                            startActivity(intent);
                            List<User> userList = DataSupport.where("username =?",username).find(User.class);
                            User user = new User();
                            if(userList.size()!=0){
                                user.setUsername(username);
                                user.setPassword(password);
                                user.setToken(token);
                                user.updateAll("username=?",username);
                                finish();
                                return;
                            }
                            user.setUsername(username);
                            user.setPassword(password);
                            user.setToken(token);
                            user.save();
                            finish();
                        }else {
                            Login_btn.loadingFailed();
                            Login_btn.setEnabled(true);
                            Toast.makeText(activity, "登录失败，密码错误", Toast.LENGTH_SHORT).show();
                        }
                            break;
                    case 2:
                        Login_btn.loadingFailed();
                        Login_btn.setEnabled(true);
                        Toast.makeText(activity, "网络或服务器错误！", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.Login_back));
        }
        intnView();
        Password_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int usertext = Username_view.getText().length();
                if(s.length()>=6&&usertext!=0){
                    Login_btn.setEnabled(true);
                }else {
                    Login_btn.setEnabled(false);
                }
            }
        });
        Login_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // 隐藏软键盘
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
               Login_btn.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       String username = Username_view.getText().toString();
                       String password = Password_view.getText().toString();
                       if(username.isEmpty()){
                           Username_view.setError("用户名格式不正确");
                           Login_btn.cancelLoading();
                           return;
                       }
                       if (password.isEmpty()) {
                           Password_view.requestFocus();
                           Password_view.setError("密码为空");
                           return;
                       } else if (password.length() < 6) {
                           Password_view.requestFocus();
                           Password_view.setError("密码长度过短");
                           return;
                       }
                       Login(username,password);
                   }
               },1000);
                Login_btn.startLoading();
                Login_btn.setEnabled(false);
            }
        });
    }

    public void Login(final String username, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                MediaType Json = MediaType.parse("application/json; charset=utf-8");
                Map map=new HashMap();
                map.put("username",username);
                map.put("password",password);
                map.put("CID","123");
                map.put("active","yes");
                String  param= JSON.toJSONString(map);
                RequestBody body = RequestBody.create(Json,param);
                Request request = new Request.Builder()
                        .url("http://120.78.95.148/attendance/user/checkAPP").post(body)
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
                        if (response.isSuccessful()){
                            req= response.body().string();
                            Gson gson = new Gson();
//                                java.lang.reflect.Type type = new TypeToken<LoginBeen>() {}.getType();
                            LoginBeen login = gson.fromJson(req, LoginBeen.class);
                            Message msg = new Message().obtain();
                            msg.what = 1;
                            Bundle b = new Bundle();
                            b.putString("username", username);
                            b.putString("password",password);
                            b.putInt("Code",login.getCode());
                            b.putString("token",login.getData2());
                            msg.setData(b);
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();
    }
    private void intnView() {
        LitePal.getDatabase();
       Username_view =findViewById(R.id.username);
       Password_view = findViewById(R.id.password);
       Login_btn = findViewById(R.id.Login_button);
        Login_btn.setBackgroundShader(new LinearGradient(0f,0f,1000f,100f, Color.parseColor("#2E9AFE") , Color.parseColor("#4F8DFE"), Shader.TileMode.CLAMP));
        Login_btn.setEnabled(false);
    }
}

