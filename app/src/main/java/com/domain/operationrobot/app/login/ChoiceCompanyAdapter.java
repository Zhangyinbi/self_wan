package com.domain.operationrobot.app.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.company.CompanyAdapter;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.User;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/24 12:20
 */
public class ChoiceCompanyAdapter extends RecyclerView.Adapter<ChoiceCompanyAdapter.ChoiceViewHolder> {
  private Context            mContext;
  private ArrayList<Company> mCompany;
  private LoginPresenterImpl mLoginPresenter;
  private String token;

  public ChoiceCompanyAdapter(Context context, ArrayList<Company> company, LoginPresenterImpl loginPresenter, String token) {
    mContext = context;
    mCompany = company;
    mLoginPresenter = loginPresenter;
    this.token = token;
  }

  @NonNull
  @Override
  public ChoiceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View inflate = LayoutInflater.from(mContext)
                                 .inflate(R.layout.choice_company_item, null);
    return new ChoiceCompanyAdapter.ChoiceViewHolder(inflate);
  }

  @Override
  public int getItemCount() {
    return mCompany != null ? mCompany.size() : 0;
  }

  @Override
  public void onBindViewHolder(@NonNull ChoiceViewHolder holder, int position) {
    Company company = mCompany.get(position);
    holder.tvCompanyName.setText(company.getCompanyname());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mLoginPresenter.setDefaultCompany(company.getCompanyid(),token);
      }
    });
  }

  public class ChoiceViewHolder extends RecyclerView.ViewHolder {
    public TextView tvCompanyName;

    public ChoiceViewHolder(@NonNull View itemView) {
      super(itemView);
      tvCompanyName = itemView.findViewById(R.id.tv_company_name);
    }
  }
}
