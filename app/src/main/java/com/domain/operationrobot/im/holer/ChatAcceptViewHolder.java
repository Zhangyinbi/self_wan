package com.domain.operationrobot.im.holer;

import android.view.View;
import android.widget.TextView;
import com.domain.operationrobot.im.base.BaseHolderRV;
import com.domain.operationrobot.im.bean.MessageRecord;

public class ChatAcceptViewHolder extends BaseHolderRV<MessageRecord> {

    TextView mNameTv;
    TextView mContentTv;

    public ChatAcceptViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void bindData() {
        mNameTv.setText(mDataBean.getUserName() + ":");
        mContentTv.setText(mDataBean.getContent());
    }


}
