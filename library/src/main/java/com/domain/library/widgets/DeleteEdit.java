package com.domain.library.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.domain.library.R;
import com.domain.library.utils.ToastUtils;
import com.domain.library.utils.inputfilter.NormalFilter;

import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/9 20:55
 */
public class DeleteEdit extends LinearLayout implements TextWatcher, View.OnClickListener, View.OnFocusChangeListener {
    private ArrayList<InputFilter> filters;
    private EditText etValue;

    private String value = "";
    private String valueHint = "";
    private String toastHint = "";
    private Button btnDelete;
    private int hintColor;
    private int textColor;
    private TextAfterChange textAfterChange;
    private OnFocusChangeListener onFocusChangeListener;

    public DeleteEdit(Context context) {
        this(context, null);
    }

    public DeleteEdit(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DeleteEdit(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs, defStyleAttr);
    }

    private void initView(AttributeSet attrs, int defStyleAttr) {
        View.inflate(getContext(), R.layout.delete_edit, this);
        initDefaultValue(attrs, defStyleAttr);
        filters = new ArrayList<>();
        etValue = findViewById(R.id.et_value);
        addFilter(new NormalFilter());
        etValue.addTextChangedListener(this);
        etValue.setOnFocusChangeListener(this);
        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);
        etValue.setHintTextColor(hintColor);
        etValue.setTextColor(textColor);
        initData();
    }


    private void initDefaultValue(AttributeSet attrs, int defStyleAttr) {
        TypedArray attributes =
                getContext().obtainStyledAttributes(attrs, R.styleable.delete_edit, defStyleAttr, 0);
        value = attributes.getString(R.styleable.delete_edit_value);
        toastHint = attributes.getString(R.styleable.delete_edit_toast_hint);
        valueHint = attributes.getString(R.styleable.delete_edit_value_hint);
        hintColor = attributes.getColor(R.styleable.delete_edit_hint_color, 0XFFC6C6C6);
        textColor = attributes.getColor(R.styleable.delete_edit_text_color, 0XFF333333);
        attributes.recycle();
    }

    /**
     * 获取value内容
     *
     * @return
     */
    public String getValue() {
        value = etValue.getText().toString().trim();
        if (TextUtils.isEmpty(value)) {
            if (!TextUtils.isEmpty(toastHint)) {
                ToastUtils.showToast(toastHint);
            }
            return "";
        }
        return value;
    }

    /**
     * 设置value
     *
     * @param value
     */
    public void setValue(String value) {
        if (null == etValue) return;
        etValue.setText(value);
    }

    public void setKeyListener(String digits) {
        if (null == etValue) return;
        etValue.setKeyListener(DigitsKeyListener.getInstance(digits));
    }

    /**
     * Sets the list of input filters that will be used if the buffer is
     * Editable. Has no effect otherwise.
     *
     * @param filters
     */
    private void setFilters(InputFilter[] filters) {
        if (filters == null) {
            throw new IllegalArgumentException("Filter must be not null");
        }
        etValue.setFilters(filters);
    }


    /**
     * 设置输入inputType类型
     *
     * @param inputType
     */
    public void setInputType(int inputType) {
        etValue.setInputType(inputType);
    }


    private void initData() {
        if (!TextUtils.isEmpty(value)) {
            etValue.setText(value);
        }
        if (!TextUtils.isEmpty(valueHint)) {
            etValue.setHint(valueHint);
        }
    }

    /**
     * 设置过滤
     *
     * @param filter
     */
    public void addFilter(InputFilter filter) {
        if (null != filter) {
            this.filters.add(filter);
        }
        if (null != this.filters && this.filters.size() > 0) {
            InputFilter[] inputFilters = new InputFilter[this.filters.size()];
            int j;
            for (j = 0; j < this.filters.size(); j++) {
                inputFilters[j] = this.filters.get(j);
            }
            setFilters(inputFilters);
        }
    }

    /**
     * 设置过滤
     *
     * @param filters
     */
    public void addFilters(ArrayList<InputFilter> filters) {
        if (null != filters) {
            this.filters.addAll(filters);
        }
        if (null != this.filters && this.filters.size() > 0) {
            InputFilter[] inputFilters = new InputFilter[this.filters.size()];
            int j;
            for (j = 0; j < this.filters.size(); j++) {
                inputFilters[j] = this.filters.get(j);
            }
            setFilters(inputFilters);
        }
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String input = etValue.getText().toString().trim();
        if (TextUtils.isEmpty(input)) {
            btnDelete.setVisibility(INVISIBLE);
        } else {
            btnDelete.setVisibility(VISIBLE);
        }
        if (null != this.textAfterChange) {
            textAfterChange.afterTextChanged(editable);
        }
    }

    @Override
    public void onClick(View view) {
        etValue.setText("");
    }

    public void setTextAfterChange(TextAfterChange textAfterChange) {
        this.textAfterChange = textAfterChange;
    }

    public void setFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (onFocusChangeListener != null) {
            onFocusChangeListener.onFocusChange(view, b);
        }
    }

    public void setSelection(int index) {
        etValue.setSelection(index);
    }

    public interface TextAfterChange {
        void afterTextChanged(Editable editable);
    }

    public interface OnFocusChangeListener {
        void onFocusChange(View view, boolean b);
    }
}
