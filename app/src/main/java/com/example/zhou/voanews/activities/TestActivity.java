package com.example.zhou.voanews.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zhou.voanews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    @BindView(R.id.test_info)
    EditText testInfo;
    String url = "https://dict.hjapi.com/v10/dict/en/cn";
    @BindView(R.id.test_text)
    TextView testText;
    @BindView(R.id.test_selected)
    TextView testSelected;
    @BindView(R.id.test_launch)
    Button testLaunch;
    @BindView(R.id.test_pop)
    Button popButton;

    PopupWindow popupWindow;
    TextView popTitle;
    @BindView(R.id.test_linear)
    LinearLayout testLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        final ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        final String a = "Here, we assumed that the text was selected from left to right. If it was selected from right to left, then above would result in an exception so you can avoid that by checking if start position is higher than the end position.";
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                Headers headers=new Headers.Builder().add("Accept-Encoding","gzip")
//                        .add("Content-Type","application/x-www-form-urlencoded")
//                        .add("Host","dict.hjapi.com")
//                        .add("User-Agent","HJApp 1.0/android/H30-C00/0D31F55E75637E632055961/4.4.2/com.hujiang.dict/2.8.7.162/zhihuiyun/")
//                        .add("hujiang-appkey","b458dd683e237054f9a7302235dee675")
//                        .add("hujiang-appsign","C2C5558418476872ABFF982B93617C9D").build();
//                e.onNext(DownloadHelper.postCallback(url,"word=album",headers));
//            }
//        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
//        .subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//
//                String a=JSON.parseObject(s).getString("data");
//                a=OfflinewordAPI.getContent(a);
//                testInfo.setText(a);
//                Log.d(TAG, "accept: "+a);
////                System.out.println(a);
//            }
//        });

        testText.setText(a);
        testText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_POINTER_UP:
                int position = testText.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                String s = a.substring(a.lastIndexOf(" ", position), a.indexOf(" ", position));
                testSelected.setText(s);
                clipboardManager.setPrimaryClip(ClipData.newPlainText("simple text", s));

//                }
                return false;
            }
        });

        initPopup();

    }

    public void Click(View v) {
        switch (v.getId()) {
            case R.id.test_launch:
//                Intent intent=new Intent("switchWord");
//                intent.addFlags(0x10000000);
//                startActivity(intent);
                break;
            case R.id.test_pop:
                showPopup();
//                popTitle.setText("aaaaa");
                Log.d(TAG, "Click: .........");
                break;
        }
    }

    private void initPopup() {
        View v = LayoutInflater.from(this).inflate(R.layout.pop_wordmeaning, null);
//        popupWindow = new PopupWindow(v,300,300);
//        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//
//
//        popTitle=v.findViewById(R.id.pop_wm_title);
//
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
//        popupWindow.setOutsideTouchable(false);
//        popupWindow.setFocusable(true);

        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
        popupWindow = new PopupWindow(v, 400, 600);
        // TODO: 2016/5/17 设置动画
//        window.setAnimationStyle(R.style.popup_window_anim);
        // TODO: 2016/5/17 设置背景颜色
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        // TODO: 2016/5/17 设置可以获取焦点
        popupWindow.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        popupWindow.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        popupWindow.update();
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置

    }

    public void showPopup() {
//        popupWindow.showAsDropDown(popButton,10,10);
//        popupWindow.showAsDropDown(popButton, 0, 20);
            popupWindow.showAtLocation(testLinear, Gravity.CENTER,0,0);
    }
}
