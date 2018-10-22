package com.domain.operationrobot.app.company;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.ImageView;

import com.domain.library.base.AbsActivity;
import com.domain.library.base.BasePresenter;
import com.domain.library.widgets.DeleteEdit;
import com.domain.operationrobot.R;
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
          //TODO 创建公司
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
    updateIvSearchStatus(autoSearchCompany.isFocused());
    if (!autoSearchCompany.getValue()
                          .isEmpty()) {
      targetName = autoSearchCompany.getValue();
      presenter.getCompanyList(targetName);
    } else {
      presenter.getCompanyList("");
    }
  }

  private void updateIvSearchStatus(boolean focused) {
    if (autoSearchCompany.getValue()
                         .isEmpty() && !focused) {
      ivSearch.setVisibility(View.VISIBLE);
      adapter.updateData(this.companyList, targetName);
    } else {
      ivSearch.setVisibility(View.GONE);
    }
  }

  @Override
  public void onFocusChange(View view, boolean b) {
    updateIvSearchStatus(b);
  }

  /**
   * 更新数据
   */
  @Override
  public void setCompanyList(ArrayList<Company> companyList) {
    if (companyList == null) {
      return;
    }
    //this.companyList.add(new Company("成都云图什么名字2", "占隐蔽2", 89825));
    adapter.updateData(this.companyList, targetName);
  }
}
