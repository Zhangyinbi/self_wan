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
public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.MyViewHolder> {

    private ArrayList<Company> data;
    private Context context;

    private String targetName;
    private JoinCompanyContract.JoinCompanyPresenter presenter;

    private String currentUserName;

    public CompanyAdapter(Context context, ArrayList<Company> companyList, JoinCompanyContract.JoinCompanyPresenter presenter) {
        this.data = companyList;
        this.context = context;
        this.presenter = presenter;
        this.currentUserName = BaseApplication.getInstance().getUser() != null ? BaseApplication.getInstance().getUser().getName() : "请输入姓名";
    }

    public void updateData(ArrayList<Company> data, String targetName) {
        this.data = data;
        this.targetName = targetName;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.company_item, null);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        String currentItemName = data.get(position).getCompanyName();
        holder.tvCompanyName.setText(currentItemName);
        if (null != targetName && currentItemName.contains(targetName)) {
            int length = targetName.length();
            int start = currentItemName.indexOf(targetName);
            int end = start + length;
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.text_333));
            SpannableString spannableString = new SpannableString(currentItemName);
            spannableString.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvCompanyName.setText(spannableString);
        }

        holder.itemView.setOnClickListener(new ThrottleLastClickListener() {
            @Override
            public void onViewClick(View v) {
                new JoinCompanyDialog(context, data.get(position), currentUserName, presenter).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCompanyName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCompanyName = itemView.findViewById(R.id.tv_company_name);
        }
    }
}
