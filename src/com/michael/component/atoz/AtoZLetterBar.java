package com.michael.component.atoz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * The component of A to Z letter Bar.
 * 这个就是A-Z字符选择器
 * 
 * @author MichaelYe
 * @since 2012-8-22
 * */
public class AtoZLetterBar extends View 
{

	OnTouchingLetterChangedListener onTouchingLetterChangedListener;

	String[] b = {"#","A","B","C","D","E","F","G","H","I","J","K","L"
        ,"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

	int choose = -1;

	Paint paint = new Paint();

	boolean showBkg = false;

	private Drawable thumbDrawable;

	public AtoZLetterBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public AtoZLetterBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public AtoZLetterBar(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context)
	{
		thumbDrawable = context.getResources().getDrawable(R.drawable.ic_launcher);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
	    super.onDraw(canvas);
	    if(showBkg)
	    {
	        canvas.drawColor(Color.parseColor("#000000"));//#40000000 3399ff
	    }
	    
				//在手指接触屏幕的地方绘制图片
		thumbDrawable.setBounds(0, location - 60, this.getWidth(), location + 60);
		thumbDrawable.draw(canvas);
	
	    int height = getHeight();
	    int width = getWidth();
	    int singleHeight = height / b.length;
	    for(int i=0;i<b.length;i++)
	    {
	       paint.setColor(Color.GRAY);
	       paint.setTypeface(Typeface.DEFAULT_BOLD);
	       paint.setAntiAlias(true);
	       if(i == choose)
	       {
	         paint.setColor(Color.parseColor("#ff3355"));//手指选中时的颜色ff3355
	         paint.setTextSize(24);
	         paint.setFakeBoldText(true);
	         
	       }
	       float xPos = width/2  - paint.measureText(b[i])/2;
	       float yPos = singleHeight * i + singleHeight;
	       canvas.drawText(b[i], xPos, yPos, paint);
	       paint.reset();
	      
	    }
    
	}

	private int location = 0;//为了得到当前用户手指点击的位置
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) 
	{
	    final int action = event.getAction();
	    final float y = event.getY();
	    final int oldChoose = choose;
	    final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
	    final int c = (int) (y/getHeight()*b.length);
	
	    location = (int)y;
	    
	    switch (action) 
	    {
	        case MotionEvent.ACTION_DOWN:
	            showBkg = true;
	            if(oldChoose != c && listener != null)
	            {
	                if(c >= 0 && c< b.length)//这里的c要>=0，不然第一个字母不能正常显示
	                {
	                    listener.onTouchingLetterChanged(b[c]);
	                    choose = c;
	                    invalidate();
	                }
	            }
	
	            break;
	        case MotionEvent.ACTION_MOVE:
	            if(oldChoose != c && listener != null)
	            {
	                if(c >= 0 && c< b.length){//这里的c要>=0，不然第一个字母不能正常显示
	                    listener.onTouchingLetterChanged(b[c]);
	                    choose = c;
	                    invalidate();
	                }
	            }
	            break;
	        case MotionEvent.ACTION_UP:
	            showBkg = false;
	            choose = -1;
	            invalidate();
	            break;
	    }
	    return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
	    return super.onTouchEvent(event);
	}
	
	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener)
	{
	    this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}
	
	/**
	 * 定义一个接口，用来传递所触摸的字符
	 * 
	 * */
	public interface OnTouchingLetterChangedListener
	{
	    public void onTouchingLetterChanged(String s);
	}

}

















