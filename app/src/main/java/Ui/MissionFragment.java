package Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xiangzhigou.com.R;

public class MissionFragment extends android.app.Fragment{

    private View fragment_mission;
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
    }

    private void initMyView() {

    }
}
