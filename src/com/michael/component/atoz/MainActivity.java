package com.michael.component.atoz;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.michael.component.atoz.AtoZLetterBar.OnTouchingLetterChangedListener;

/**
 * This Activity shows how to use the component of AtoZLetterListview
 * 
 * 这个类演示了如何使用字符选择器
 * 
 * @author MichaelYe
 * @since 2012-8-22
 * */
public class MainActivity extends Activity 
{
	
	private TextView tv;
	private Handler handler;
    private OverlayThread overlayThread;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tv = (TextView)findViewById(R.id.textview);
        AtoZLetterBar azBar = (AtoZLetterBar)findViewById(R.id.selectbar);
        
        handler = new Handler();
        overlayThread = new OverlayThread();
        
        OnTouchingLetterChangedListener listener = new OnTouchingLetterChangedListener() 
        {
        	/**
        	 * Use Handler to remove call backs.
        	 * Make TextView Gone 1.5 second later
        	 * 
        	 * 使用Handler来移除让TextView不可见的Runnable
        	 * 并设置1.5秒后TextView不可见
        	 * 
        	 * */
			@Override
			public void onTouchingLetterChanged(String s) 
			{
				tv.setText(s);
				tv.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				handler.postDelayed(overlayThread, 1500);
			}
		};
		azBar.setOnTouchingLetterChangedListener(listener);
    }
    
    /**
     * Use Runnable to make TextView Gone
     * 
     * 通过使用Runnable来控制TextView的不可见
     * */									
    private class OverlayThread implements Runnable 
    {
        @Override
        public void run() 
        {
            tv.setVisibility(View.GONE);
        }
    }

}
