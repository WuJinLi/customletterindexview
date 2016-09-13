package com.steven.android33_customletterindexview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 自定义view实现字母索引
 */
public class LetterIndexView extends View {
    private Paint mPaint = null;
    private String[] arrLetters = new String[]{"#", "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z"};
    private int choosedPosition = -1;
    private int lineHeight = 0;
    private TextView textView_dialog;

    private OnLetterClickedListener listener = null;

    public LetterIndexView(Context context) {
        super(context);
    }

    public LetterIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LetterIndexView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void initTextView(TextView textView_dialog) {
        this.textView_dialog = textView_dialog;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画笔设置文字的大小
        float density = getResources().getDisplayMetrics().density;
        mPaint.setTextSize(14 * density);

        int viewHeight = getHeight();
        int viewWidth = getWidth();

        lineHeight = viewHeight / arrLetters.length;
        for (int i = 0; i < arrLetters.length; i++) {
            if (choosedPosition == i) {
                mPaint.setColor(Color.RED);
            } else {
                mPaint.setColor(Color.GRAY);
            }
            int textWidth = (int) mPaint.measureText(arrLetters[i]);
            canvas.drawText(arrLetters[i], (viewWidth - textWidth) / 2, lineHeight * (i + 1),
                    mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        int position = (int) (y / lineHeight);

        if (position >= 0 && position < arrLetters.length) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    setBackgroundColor(Color.TRANSPARENT);
                    if (textView_dialog != null && textView_dialog.getVisibility() == View
                            .VISIBLE) {
                        textView_dialog.setVisibility(View.GONE);
                    }
                    //擦除当前View，重新绘制
                    invalidate();

                    break;
                default:
                    setBackgroundColor(Color.parseColor("#cccccc"));
                    if (textView_dialog != null) {
                        textView_dialog.setVisibility(View.VISIBLE);
                        textView_dialog.setText(arrLetters[position]);
                    }
                    //接口回调
                    if (listener != null) {
                        listener.onLetterClicked(arrLetters[position]);
                    }

                    choosedPosition = position;
                    invalidate();
                    break;
            }
        }
        return true;
    }

    // 设置内部监听器接口
    public interface OnLetterClickedListener {
        public void onLetterClicked(String letter);
    }

    public void setOnLetterClickedListener(OnLetterClickedListener listener) {
        this.listener = listener;
    }
}
