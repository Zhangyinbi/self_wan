package com.domain.operationrobot.app.company;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.domain.library.base.AbsActivity;
import com.domain.library.base.BasePresenter;
import com.domain.library.utils.ActivityStackManager;
import com.domain.library.widgets.DeleteEdit;
import com.domain.operationrobot.R;
import com.domain.operationrobot.app.home.MainActivity;
import com.domain.operationrobot.http.bean.Company;
import com.domain.operationrobot.listener.ThrottleLastClickListener;

import java.util.ArrayList;

/**
 * 加入公司
 */
public class JoinCompanyActivity extends AbsActivity implements DeleteEdit.TextAfterChange, DeleteEdit.OnFocusChangeListener, JoinCompanyContract.JoinCompanyView<BasePresenter> {

  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.iv_back:
          finish();
          break;
        case R.id.btn_create:
          createCompany();
          break;
        default:
          break;
      }
    }
  };
  private ImageView                                ivSearch;
  private DeleteEdit                               autoSearchCompany;
  private JoinCompanyContract.JoinCompanyPresenter presenter;
  private RecyclerView                             recyclerView;
  private ArrayList<Company>                       companyList;
  private CompanyAdapter                           adapter;

  private String targetName;

  private void createCompany() {
    startActivity(new Intent(this, CreateCompanyActivity.class));
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_join_company;
  }

  @Override
  protected void newInstancePresenter() {
    presenter = new JoinCompanyPresenterImpl(this);
  }

  @Override
  protected void initView() {
    findViewById(R.id.iv_back).setOnClickListener(listener);
    findViewById(R.id.btn_create).setOnClickListener(listener);
    ivSearch = findViewById(R.id.iv_search);
    autoSearchCompany = findViewById(R.id.auto_search_company);
    autoSearchCompany.setTextAfterChange(this);
    autoSearchCompany.setFocusChangeListener(this);
    findViewById(R.id.root_view).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        view.requestFocus();
      }
    });
    companyList = new ArrayList<>();
    adapter = new CompanyAdapter(this, companyList, presenter);
    recyclerView = findViewById(R.id.rlv_recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
  }

  @Override
  protected void initData() {

  }

  @Override
  public void showEmptyView() {

  }

  @Override
  public void afterTextChanged(Editable editable) {
    targetName = autoSearchCompany.getValue();
    if (TextUtils.isEmpty(targetName)) {
      presenter.getCompanyList(targetName);
    }else {
      presenter.getCompanyTargetList(targetName);
    }
  }

  @Override
  public void onFocusChange(View view, boolean focused) {
    if (!focused) {
      ivSearch.setVisibility(View.VISIBLE);
      adapter.updateData(this.companyList, targetName);
    } else {
      if (autoSearchCompany.getValue()
                           .isEmpty()) {
        presenter.getCompanyList("");
      }
      ivSearch.setVisibility(View.GONE);
    }
  }

  /**
   * 更新数据
   */
  @Override
  public void setCompanyList(ArrayList<Company> companyList) {
    if (companyList == null) {
      return;
    }
    this.companyList = companyList;
    adapter.updateData(companyList, targetName);
  }

  /**
   * 加入公司成功
   */
  @Override
  public void joinSuss() {
    startActivity(new Intent(this, MainActivity.class));
    ActivityStackManager.getInstance()
                        .killActivity(RegisterSussActivity.class);

    finish();
  }
}
