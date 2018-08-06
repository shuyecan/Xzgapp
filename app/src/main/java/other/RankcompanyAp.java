package other;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.been.RankbycompanyBeen;
import com.bumptech.glide.Glide;

import java.util.List;

import xiangzhigou.com.R;

/**
 * Created by Administrator on 2018/8/6.
 */

public class RankcompanyAp extends RecyclerView.Adapter<RankcompanyAp.ViewHolder>{
    private Context context;
    private List<RankbycompanyBeen.ItemsBean> Datalist;
    private List<RankbycompanyBeen.ItemsBean.IntegralListBean> Integrallist;
    public RankcompanyAp(List<RankbycompanyBeen.ItemsBean> data_list, List<RankbycompanyBeen.ItemsBean.IntegralListBean> integral_list) {
        Datalist = data_list;
        Integrallist = integral_list;
    }


    @NonNull
    @Override
    public RankcompanyAp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context==null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.activity_paih_item, parent, false);
        final RankcompanyAp.ViewHolder holder = new RankcompanyAp.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder , int position) {
        RankbycompanyBeen.ItemsBean dataBean = Datalist.get(position);
        RankbycompanyBeen.ItemsBean.IntegralListBean integralListBean = Integrallist.get(position);
        holder.Tv_itempai.setText(position+1+"");
        holder.Tv_itemname.setText(dataBean.getDepartment()+":"+dataBean.getUsername());
        holder.Tv_itemfen.setText(integralListBean.getCountIntegral()+"");
        Glide.with(context).load(dataBean.getHeadImg()).placeholder( R.drawable.cuowu).dontAnimate().error( R.drawable.cuowu).into(holder.Img_itemp);
    }

    @Override
    public int getItemCount() {
        return  Datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Tv_itempai,Tv_itemname,Tv_itemfen;
        ImageView Img_itemp;
        LinearLayout paoList;
        public ViewHolder(View itemView) {
            super(itemView);
            paoList = (LinearLayout) itemView;
            Tv_itempai = itemView.findViewById(R.id.Tv_itempai);
            Tv_itemname = itemView.findViewById(R.id.Tv_itemname);
            Tv_itemfen = itemView.findViewById(R.id.Tv_itemfen);
            Img_itemp = itemView.findViewById(R.id.Img_itemp);
        }
    }
}
