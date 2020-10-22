package com.cabinet.colorpicker.top.defaults.colorpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * FileName: ColorChoiceRectView
 * Date: 2020/10/20 17:05
 * Author: SCL
 * e-mail: sucl@dqist.com
 **/
public class ColorChoiceRectView extends View implements ColorObservable, Updatable {
    private Paint paint1, paint2, paint3, paint4, paint5, paint6;
    RectF rect1, rect2, rect3, rect4, rect5, rect6;
    private ColorObservableEmitter emitter = new ColorObservableEmitter();
    private boolean onlyUpdateOnTouchEventUp;
    private float mLength, mPanding, centerX;

    public ColorChoiceRectView(Context context) {
        this(context, null);
    }

    public ColorChoiceRectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorChoiceRectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setStyle(Paint.Style.FILL);
        paint1.setColor(Color.BLACK);
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(Color.RED);
        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setStyle(Paint.Style.FILL);
        paint3.setColor(Color.BLUE);
        paint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint4.setStyle(Paint.Style.FILL);
        paint4.setColor(Color.YELLOW);
        paint5 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint5.setStyle(Paint.Style.FILL);
        paint5.setColor(Color.GREEN);
        paint6 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint6.setStyle(Paint.Style.FILL);
        paint6.setColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(rect1, 3f, 3f, paint1);
        canvas.drawRoundRect(rect2, 3f, 3f, paint2);
        canvas.drawRoundRect(rect3, 3f, 3f, paint3);
        canvas.drawRoundRect(rect4, 3f, 3f, paint4);
        canvas.drawRoundRect(rect5, 3f, 3f, paint5);
        canvas.drawRoundRect(rect6, 3f, 3f, paint6);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLength = w / 8;
        mPanding = mLength / 3;
        centerX = w / 2;
        rect1 = new RectF(centerX - mPanding / 2 - mLength, 0, centerX - mPanding / 2, h);
        rect2 = new RectF(centerX + mPanding / 2, 0, centerX + mPanding / 2 + mLength, h);
        rect3 = new RectF(centerX - mPanding / 2 - mPanding - 2 * mLength, 0, centerX - mPanding / 2 - mPanding - mLength, h);
        rect4 = new RectF(centerX + mPanding / 2 + mPanding + mLength, 0, centerX + mPanding / 2 + mPanding + 2 * mLength, h);
        rect5 = new RectF(centerX - mPanding / 2 - 2 * mPanding - 3 * mLength, 0, centerX - mPanding / 2 - 2 * mPanding - 2 * mLength, h);
        rect6 = new RectF(centerX + mPanding / 2 + 2 * mPanding + 2 * mLength, 0, centerX + mPanding / 2 + 2 * mPanding + 3 * mLength, h);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
//                handler.onTouchEvent(event);
                update(event);
                return true;
            case MotionEvent.ACTION_UP:
//                update(event);
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void subscribe(ColorObserver observer) {
        emitter.subscribe(observer);
    }

    @Override
    public void unsubscribe(ColorObserver observer) {
        emitter.unsubscribe(observer);
    }

    @Override
    public int getColor() {
        return emitter.getColor();
    }

    @Override
    public void update(MotionEvent event) {
//        updateValue(event.getX());
        boolean isTouchUpEvent = event.getActionMasked() == MotionEvent.ACTION_UP;
        if (!onlyUpdateOnTouchEventUp || isTouchUpEvent) {
            emitter.onColor(getColorAtPoint(event.getX()), true, isTouchUpEvent);
        }

    }

    public void setOnlyUpdateOnTouchEventUp(boolean onlyUpdateOnTouchEventUp) {
        this.onlyUpdateOnTouchEventUp = onlyUpdateOnTouchEventUp;
    }

    private ColorObserver bindObserver = new ColorObserver() {
        @Override
        public void onColor(int color, boolean fromUser, boolean shouldPropagate) {
            if (!onlyUpdateOnTouchEventUp) {
                emitter.onColor(color, fromUser, shouldPropagate);
            } else if (shouldPropagate) {
                emitter.onColor(color, fromUser, true);
            }

        }
    };

    private ColorObservable boundObservable;

    public void bind(ColorObservable colorObservable) {
        if (colorObservable != null) {
            colorObservable.subscribe(bindObserver);
            if (!onlyUpdateOnTouchEventUp) {
                emitter.onColor(colorObservable.getColor(), true, true);
            }
        }
        boundObservable = colorObservable;
    }

    public void unbind() {
        if (boundObservable != null) {
            boundObservable.unsubscribe(bindObserver);
            boundObservable = null;
        }
    }

    private int getColorAtPoint(float x) {
        if (x < centerX) {
            if (centerX - mPanding / 2 - mLength < x && x < centerX - mPanding / 2) {
                return paint1.getColor();
            } else if (centerX - 3 * mPanding / 2 - 2 * mLength < x && x < centerX - 3 * mPanding / 2 - mLength) {
                return paint3.getColor();
            }else if (centerX - 5 * mPanding / 2 - 3 * mLength < x && x < centerX - 5 * mPanding / 2 - 2*mLength) {
                return paint5.getColor();
            }
        } else {
            if(centerX+mPanding/2<x&& x<centerX+mPanding/2+mLength){
                return paint2.getColor();
            }else if(centerX+3*mPanding/2+mLength<x && x<centerX+3*mPanding/2+2*mLength){
                return paint4.getColor();
            }else if(centerX+5*mPanding/2+2*mLength<x && x<centerX+5*mPanding/2+3*mLength){
                return paint6.getColor();
            }
        }
        return 0;
    }
}
