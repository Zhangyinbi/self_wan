package com.domain.operationrobot.app.company;

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

public class JoinCompanyActivity extends AbsActivity implements DeleteEdit.TextAfterChange, DeleteEdit.OnFocusChangeListener, JoinCompanyContract.JoinCompanyView<BasePresenter> {

    ThrottleLastClickListener listener = new ThrottleLastClickListener() {
        @Override
        public void onViewClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    private ImageView ivSearch;
    private DeleteEdit autoSearchCompany;
    private JoinCompanyContract.JoinCompanyPresenter presenter;

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
        if (!autoSearchCompany.getValue().isEmpty()) {
            presenter.getCompanyList(autoSearchCompany.getValue());
        }
    }

    private void updateIvSearchStatus(boolean focused) {
        if (autoSearchCompany.getValue().isEmpty() && !focused) {
            ivSearch.setVisibility(View.VISIBLE);
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
     *
     * @param companyList
     */
    @Override
    public void setCompanyList(ArrayList<Company> companyList) {

    }
}
