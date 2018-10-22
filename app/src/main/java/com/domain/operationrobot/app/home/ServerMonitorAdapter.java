package com.domain.operationrobot.app.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.domain.operationrobot.R;
import com.domain.operationrobot.app.company.JoinCompanyContract;
import com.domain.operationrobot.http.bean.Company;

import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 17:34
 */
public class ServerMonitorAdapter extends RecyclerView.Adapter<ServerMonitorAdapter.MyViewHolder> {

    private Context context;

    public ServerMonitorAdapter(Context context) {
        this.context = context;
    }

    public ServerMonitorAdapter(Context context, ArrayList<Company> companyList, JoinCompanyContract.JoinCompanyPresenter presenter) {
        this.context = context;
    }

//    public void updateData(ArrayList<Company> data, String targetName) {
//        this.data = data;
//        this.targetName = targetName;
//        this.notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View inflate;
        if (viewType == 0) {
            inflate = LayoutInflater.from(context).inflate(R.layout.server_monitor_item, null);
        } else {
            inflate = LayoutInflater.from(context).inflate(R.layout.server_monitor_last_item, null);
        }
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        if (position == 6) {
            holder.view.setVisibility(View.VISIBLE);
        }
//        holder.pb.setProgress(50);
//        holder.pb.setProgressDrawable();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 6 ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        View view;

        //        ProgressBar pb;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view);
//            pb = itemView.findViewById(R.id.pb);
        }
    }
}
