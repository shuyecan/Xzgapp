package Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xiangzhigou.com.R;

public class ShenpiFragment extends android.app.Fragment {
    private View fragment_shenpi;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if ( fragment_shenpi == null) {
            fragment_shenpi = inflater.inflate(R.layout.fragment_shenpi,
                    container, false);
            return  fragment_shenpi;
        }
        return  fragment_shenpi;
    }
}
