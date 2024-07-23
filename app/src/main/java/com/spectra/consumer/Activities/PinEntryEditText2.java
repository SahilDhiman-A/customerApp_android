package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;

import android.text.Editable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.spectra.consumer.R;


@SuppressLint("AppCompatCustomView")
public class PinEntryEditText2 extends EditText {
    public static final String XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";

    private float mSpace = 24; //24 dp by default, space between the lines
    private float mCharSize;
    private float mNumChars = 4;
    private float mLineSpacing = 12; //8dp by default, height of the text from our lines
    private int mMaxLength = 4;
    private int colorGrey, colorAfterEnter;

    private OnClickListener mClickListener;

    private float mLineStroke = 1; //1dp by default
    private float mLineStrokeSelected = 2; //2dp by default
    private Paint mLinesPaint, mPaintW;
    int[][] mStates = new int[][]{
            new int[]{android.R.attr.state_selected}, // selected
            new int[]{android.R.attr.state_focused}, // focused
            new int[]{-android.R.attr.state_focused}, // unfocused
    };

    int[] mColors = new int[]{
            Color.BLUE,
            Color.LTGRAY,
            Color.RED
    };

    ColorStateList mColorStates = new ColorStateList(mStates, mColors);

    public PinEntryEditText2(Context context) {
        super(context);
    }

    public PinEntryEditText2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PinEntryEditText2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IconDescriptionViewItem, 0, 0);
        try {
            colorGrey = ta.getColor(R.styleable.IconDescriptionViewItem_text_color, getResources().getColor(R.color.colorPrimaryDark));
            colorAfterEnter = ta.getColor(R.styleable.IconDescriptionViewItem_text_color_entered, getResources().getColor(R.color.colorPrimaryDark));

        } finally {
            ta.recycle();
        }
        setLongClickable(false);

//        colorGrey = ContextCompat.getColor(context, R.color.white_pin);
        mColors = new int[]{ContextCompat.getColor(context, R.color.colorPrimaryDark),
                ContextCompat.getColor(context, R.color.colorPrimaryDark),
                ContextCompat.getColor(context, R.color.colorPrimaryDark)};
        mColorStates = new ColorStateList(mStates, mColors);

        float multi = context.getResources().getDisplayMetrics().density;
        mLineStroke = multi * mLineStroke;
        mLineStrokeSelected = multi * mLineStrokeSelected;
        mLinesPaint = new Paint(getPaint());
        mPaintW = new Paint();
        mPaintW.setColor(colorAfterEnter);
        mLinesPaint.setStrokeWidth(mLineStroke);
//        if (!isInEditMode()) {
//            TypedValue outValue = new TypedValue();
//            context.getTheme().resolveAttribute(R.attr.colorControlActivated,
//                    outValue, true);
//            final int colorActivated = outValue.data;
//            mColors[0] = colorActivated;
//
//            context.getTheme().resolveAttribute(R.attr.colorPrimaryDark,
//                    outValue, true);
//            final int colorDark = outValue.data;
//            mColors[1] = colorDark;
//
//            context.getTheme().resolveAttribute(R.attr.colorControlHighlight,
//                    outValue, true);
//            final int colorHighlight = outValue.data;
//            mColors[2] = colorHighlight;
//        }
        setBackgroundResource(0);
        mSpace = multi * mSpace; //convert to pixels for our density
        mLineSpacing = multi * mLineSpacing; //convert to pixels for our density
        mMaxLength = attrs.getAttributeIntValue(XML_NAMESPACE_ANDROID, "maxLength", 4);
        mNumChars = mMaxLength;

        //Disable copy paste
        super.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        // When tapped, move cursor to end of text.
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelection(getText().length());
                if (mClickListener != null) {
                    mClickListener.onClick(v);
                }
            }
        });

        requestFocus();

    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mClickListener = l;
    }

    @Override
    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        throw new RuntimeException("setCustomSelectionActionModeCallback() not supported.");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        int availableWidth = getWidth() - getPaddingRight() - getPaddingLeft();
        if (mSpace < 0) {
            mCharSize = (availableWidth / (mNumChars * 2 - 1));
        } else {
            mCharSize = (availableWidth - (mSpace * (mNumChars - 1))) / mNumChars;
        }

        int startX = getPaddingLeft();
        int bottom = getHeight() - getPaddingBottom();

        //Text Width
        Editable text = getText();
        int textLength = text.length();
        float[] textWidths = new float[textLength];
        getPaint().getTextWidths(getText(), 0, textLength, textWidths);

        for (int i = 0; i < mNumChars; i++) {
            updateColorForLines(i == textLength, i < textLength);
            canvas.drawLine(startX, bottom, startX + mCharSize, bottom, mLinesPaint);

            if (getText().length() > i) {

                if (mNumChars == getText().length() && i == getText().length() - 1) {
                    mHandler.sendEmptyMessageDelayed(100, 1000);
                }
                float middle = startX + mCharSize / 2;
                canvas.drawText(text, i, i + 1, middle - textWidths[0] / 2, bottom - mLineSpacing, getPaintText());

            }

            if (mSpace < 0) {
                startX += mCharSize * 2;
            } else {
                startX += mCharSize + mSpace;
            }


        }
    }

    boolean showDot = false;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100 && !showDot) {
                showDot = true;
                invalidate();
            } else {
                showDot = false;
            }
        }
    };

    private Paint mPaintT;

    private Paint getPaintText() {
        if (mPaintT == null) {
            mPaintT = getPaint();
            mPaintT.setColor(colorAfterEnter);

        }
        return mPaintT;
    }



    //private void drawCircle(Can)


    private int getColorForState(int... states) {
        return mColorStates.getColorForState(states, Color.GRAY);
    }

    /**
     * @param next      Is the current char the next character to be input?
     * @param hasFilled
     */
    private void updateColorForLines(boolean next, boolean hasFilled) {
        if (isFocused()) {
            mLinesPaint.setStrokeWidth(mLineStrokeSelected);
            mLinesPaint.setColor(colorGrey);
            if (hasFilled) {
                mLinesPaint.setColor(colorAfterEnter);
            }
        } else {
            mLinesPaint.setStrokeWidth(mLineStroke);
            mLinesPaint.setColor(Color.WHITE);
        }
    }
}