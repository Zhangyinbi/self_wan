package com.domain.operationrobot.app.company;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.listener.ThrottleLastClickListener;

import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 17:34
 */
public class UserApplyAdapter extends RecyclerView.Adapter<UserApplyAdapter.MyViewHolder> {

    private Context context;

    public UserApplyAdapter(Context context) {
        this.context = context;
    }

    public UserApplyAdapter(Context context, ArrayList<Company> companyList, JoinCompanyContract.JoinCompanyPresenter presenter) {
        this.context = context;
    }

//    public void updateData(ArrayList<Company> data, String targetName) {
//        this.data = data;
//        this.targetName = targetName;
//        this.notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.user_apply_item, null);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        if (position == 1) {
            holder.btn_agree.setVisibility(View.GONE);
            holder.btn_refuse.setVisibility(View.GONE);
            holder.btn_result.setVisibility(View.VISIBLE);
            holder.btn_result.setBackgroundResource(R.drawable.yhsq_img_tongyi);
        } else if (position == 2) {
            holder.btn_agree.setVisibility(View.GONE);
            holder.btn_refuse.setVisibility(View.GONE);
            holder.btn_result.setVisibility(View.VISIBLE);
            holder.btn_result.setBackgroundResource(R.drawable.yhsq_img_jujue);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        Button btn_result;
        Button btn_refuse;
        Button btn_agree;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_result = itemView.findViewById(R.id.btn_result);
            btn_refuse = itemView.findViewById(R.id.btn_refuse);
            btn_agree = itemView.findViewById(R.id.btn_agree);
        }
    }
}
