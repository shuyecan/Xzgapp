package Ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import xiangzhigou.com.R;
public class HomeFragment extends android.app.Fragment{

    private View fragment_home;
    private TextView fragment_home_tv1;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMyView();
        initMyData();
    }

    private void initMyData() {
        fragment_home_tv1.setText("hehe");
    }

    private void initMyView() {
        fragment_home_tv1 = getActivity().findViewById(R.id.fragment_home_tv);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        if (fragment_home == null) {
            fragment_home = inflater.inflate(R.layout.fragment_home,
                    container, false);
            return fragment_home;
        }
        return fragment_home;
    }

}
