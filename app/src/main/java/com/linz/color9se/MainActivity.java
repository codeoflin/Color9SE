package com.linz.color9se;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ImageView iv;
    private Bitmap baseBitmap;
    private Canvas canvas;
    private Paint greenpen;
    private Paint redpen;
    private String TAGi = "Information";
    private Handler handler = new Handler();
    Runnable runnable;
    private int ImgW = 0;
    private int ImgH = 0;
    private int Second = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Point screensize = new Point();
        super.onCreate(savedInstanceState);
        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        if (getActionBar() != null) getActionBar().hide();

        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        this.iv = (ImageView) this.findViewById(R.id.iv);
        Log.i(TAGi, "start!");
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm2 = getResources().getDisplayMetrics();
        display.getSize(screensize);
        ImgW = dm2.widthPixels;
        ImgH = dm2.heightPixels;

        //Log.i(TAGi, Integer.toString(h));

        // 创建一张空白图片
        baseBitmap = Bitmap.createBitmap(ImgW, ImgH, Bitmap.Config.ARGB_8888);
        // 创建一张画布
        canvas = new Canvas(baseBitmap);
        // 创建画笔
        greenpen = new Paint();
        greenpen.setColor(Color.argb(255, 0, 255, 0));
        // 创建画笔
        redpen = new Paint();
        redpen.setColor(Color.argb(255, 255, 0, 0));
        // 宽度1个像素
        greenpen.setStrokeWidth(1);
        // 先将灰色背景画上
        canvas.drawBitmap(baseBitmap, new Matrix(), greenpen);


        Timer mTimer = new Timer();
        TimerTask mTimerTask = new TimerTask() {//创建一个线程来执行run方法中的代码
            @Override
            public void run() {
                //要执行的代码
            }
        };


        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(runnable, 3);
                Second += 13;
                int radius = (ImgW < ImgH ? ImgW : ImgH) / 10;
                int mods = Second % (((ImgH + ImgW) * 2) - (radius * 8));
                Paint pen;
                pen = (Second % 500 > 250) ? redpen : greenpen;
                //Clear Screen
                canvas.drawColor(Color.BLACK);
                canvas.drawText(Integer.toString(Second), 20, 100, greenpen);
                //.drawText("一个测试", ImgW / 8, ImgH / 8, pen);

                //1
                if (mods < ImgW - (radius * 2)) {
                    canvas.drawCircle(mods + radius, radius, radius, pen);
                    iv.setImageBitmap(baseBitmap);
                    return;
                }
                mods -= ImgW - (radius * 2);

                //2
                if (mods < ImgH - (radius * 2)) {
                    canvas.drawCircle(ImgW - radius, radius + mods, radius, pen);
                    iv.setImageBitmap(baseBitmap);
                    return;
                }
                mods -= ImgH - (radius * 2);

                //3
                if (mods < ImgW - (radius * 2)) {
                    canvas.drawCircle(ImgW - mods - radius, ImgH-radius, radius, pen);
                    iv.setImageBitmap(baseBitmap);
                    return;
                }
                mods -= ImgW - (radius * 2);

                //4
                if (mods < ImgH - (radius * 2)) {
                    canvas.drawCircle(radius, ImgH - mods - radius, radius, pen);
                    iv.setImageBitmap(baseBitmap);
                    return;
                }


            }
        };
        handler.postDelayed(runnable, 10);
        //handler.removeCallbacks(runnable);
    }
}
