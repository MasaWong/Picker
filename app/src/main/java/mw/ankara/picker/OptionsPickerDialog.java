package mw.ankara.picker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

import mw.ankara.picker.lib.WheelOptions;

/**
 * @author masawong
 * @since 7/12/15.
 */
public class OptionsPickerDialog extends DialogFragment implements OnClickListener {

    private WheelOptions mWheelOptions;
    private OnOptionsSelectListener mOptionsSelectListener;

    private ArrayList<String> mOptionItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Picker_Constant);
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.PickerAnim);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT);

        View view = inflater.inflate(R.layout.dialog_options_picker, container);

        // 确定和取消按钮
        view.findViewById(R.id.timepicker_b_submit).setOnClickListener(this);
        view.findViewById(R.id.timepicker_b_cancel).setOnClickListener(this);

        // 转轮
        final View optionspicker = view.findViewById(R.id.optionspicker);
        mWheelOptions = new WheelOptions(optionspicker);
        mWheelOptions.screenheight = getResources().getDisplayMetrics().heightPixels;
        mWheelOptions.setPicker(mOptionItems, null, null, false);

        return view;
    }

    public void setOnoptionsSelectListener(
        OnOptionsSelectListener optionsSelectListener) {
        this.mOptionsSelectListener = optionsSelectListener;
    }

    public void setPicker(ArrayList<String> optionsItems) {
        mOptionItems = optionsItems;
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        mWheelOptions.setCyclic(cyclic);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.timepicker_b_submit && mOptionsSelectListener != null) {
            int[] optionsCurrentItems = mWheelOptions.getCurrentItems();
            mOptionsSelectListener.onOptionsSelect(optionsCurrentItems[0],
                optionsCurrentItems[1], optionsCurrentItems[2]);
        }
        dismiss();
    }

    public interface OnOptionsSelectListener {
        void onOptionsSelect(int options1, int option2, int options3);

    }
}
