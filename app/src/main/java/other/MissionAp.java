package other;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.been.Missionbeen;
import com.been.Ranking;
import com.bumptech.glide.Glide;

import java.util.List;

import xiangzhigou.com.R;

/**
 * Created by Administrator on 2018/8/9.
 */

public class MissionAp extends RecyclerView.Adapter<MissionAp.ViewHolder>{

    private Context context;
    private List<Missionbeen.ItemsBean> Datalist;
    private List<Missionbeen.ItemsBean.HeadImgBean> headImgBeanListlist;
    public MissionAp(List<Missionbeen.ItemsBean> data_list,List<Missionbeen.ItemsBean.HeadImgBean> head_list) {
        Datalist = data_list;
        headImgBeanListlist = head_list;
    }
    @NonNull
    @Override
    public MissionAp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context==null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.activity_mission_item, parent, false);
        final MissionAp.ViewHolder holder = new MissionAp.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Missionbeen.ItemsBean missionbeen = Datalist.get(position);
                Toast.makeText(context, missionbeen.getMissionId()+"", Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MissionAp.ViewHolder holder, int position) {
        Missionbeen.ItemsBean dataBean = Datalist.get(position);
        Missionbeen.ItemsBean.HeadImgBean HeadListBean = headImgBeanListlist.get(position);
        holder.Tv_name.setText(dataBean.getPublisher());
        holder.Tv_missionname.setText(dataBean.getMissionName());
        holder.Tv_id.setText(dataBean.getMissionId()+"");
        holder.Tv_endDate.setText("截止至:"+dataBean.getEndDate());
        holder.Tv_jiafen.setText(dataBean.getAward()+"");
        holder.Tv_jianfen.setText("-"+dataBean.getDeduct()+"");
        Glide.with(context).load(HeadListBean.getHeadImg()).placeholder( R.drawable.cuowu).dontAnimate().error( R.drawable.cuowu).into(holder.Img_head);
    }

    @Override
    public int getItemCount() {
        return Datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Tv_name,Tv_missionname,Tv_jiafen,Tv_jianfen,Tv_endDate,Tv_id;
        ImageView Img_head;
        RelativeLayout paoList;
        public ViewHolder(View itemView) {
            super(itemView);
            paoList = (RelativeLayout) itemView;
            Tv_name = itemView.findViewById(R.id.tv_mission_name);
            Tv_missionname = itemView.findViewById(R.id.tv_mission_missionname);
            Tv_jiafen = itemView.findViewById(R.id.tv_mission_jia);
            Tv_jianfen = itemView.findViewById(R.id.tv_mission_jian);
            Tv_endDate = itemView.findViewById(R.id.tv_mission_date);
            Tv_id = itemView.findViewById(R.id.tv_mission_id);
            Img_head= itemView.findViewById(R.id.img_mission);
        }
    }
}
