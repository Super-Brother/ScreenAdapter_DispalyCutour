package com.example.screenadapter_dispalycutour;

import android.os.Build;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1. 设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //华为，小米，oppo
        //1.判断手机厂商;
        // 2.判断手机是否刘海屏;
        //3. 设置是否让内容区域延伸进刘海；
        //4. 设置控件是否被开刘海区域
        //5. 获取刘海的高度

        //判断手机是否是刘海屏
        boolean hasDisplayCutout = hasDisplayCutout(window);
        if (hasDisplayCutout) {
            //2. 兼容刘海屏 内容区域延伸进刘海
            WindowManager.LayoutParams params = window.getAttributes();
            //全屏模式 内容下移 非全屏不受影响
//        params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
            //允许内容区延伸进刘海区
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            //不允许内容燕伸进刘海区
//        params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;

            //3. 设置成沉浸式
            int flags = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility |= flags;// 追加沉浸式设置
            window.getDecorView().setSystemUiVisibility(visibility);
        }

        setContentView(R.layout.activity_main);

//        Button button = findViewById(R.id.button);
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) button.getLayoutParams();
//        params.topMargin = heightForDisplayCutout();
//        button.setLayoutParams(params);

        RelativeLayout layout = findViewById(R.id.container);
        layout.setPadding(0, heightForDisplayCutout(), 0, 0);
    }

    /**
     * 判断是否刘海屏
     * 模拟器一直是false
     *
     * @param window
     * @return
     */
    private boolean hasDisplayCutout(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            DisplayCutout displayCutout;
            View rootView = window.getDecorView();
            WindowInsets insets = rootView.getRootWindowInsets();
            if (insets != null) {
                displayCutout = insets.getDisplayCutout();
                if (displayCutout != null) {
                    displayCutout.getBoundingRects();
                    if (displayCutout.getBoundingRects().size() > 0 && displayCutout.getSafeInsetTop() > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 通常情况下，刘海的高就是状态栏的高
     *
     * @return
     */
    public int heightForDisplayCutout() {
        int resID = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resID > 0) {
            return getResources().getDimensionPixelSize(resID);
        }
        return 96;
    }

}
