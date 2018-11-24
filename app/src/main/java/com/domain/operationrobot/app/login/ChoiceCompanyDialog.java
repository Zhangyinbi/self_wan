package com.domain.operationrobot.app.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.domain.operationrobot.R;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.http.bean.User;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/13 19:08
 */
public class ChoiceCompanyDialog extends AppCompatDialog {

  private RecyclerView mRecyclerView;

  private ArrayList<Company> companys;
  private Context            mContext;

  private LoginPresenterImpl mLoginPresenter;
  private String token;

  public ChoiceCompanyDialog(@NonNull Context context, ArrayList<Company> companys, LoginPresenterImpl loginPresenter, String token) {
    super(context, R.style.ROBOT_Dialog);
    this.companys = companys;
    this.mContext = context;
    this.mLoginPresenter = loginPresenter;
    this.token = token;
    init();
  }

  private void init() {
    setContentView(R.layout.choice_company_dialog);
    getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    setCanceledOnTouchOutside(true);

    mRecyclerView = findViewById(R.id.rlv_recycler);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    mRecyclerView.setAdapter(new ChoiceCompanyAdapter(mContext, companys, mLoginPresenter,token));
  }
}
