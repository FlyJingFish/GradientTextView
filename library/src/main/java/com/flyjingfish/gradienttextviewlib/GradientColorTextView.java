package com.flyjingfish.gradienttextviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.annotation.ColorRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

public class GradientColorTextView extends AppCompatTextView {
    private Paint mTextPaint;
    private BaseGradientTextView.Orientation orientation;
    private int[] gradientColors;
    private float[] gradientPositions;

    public GradientColorTextView(Context context) {
        this(context, null);
    }

    public GradientColorTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientColorTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);
        int startColor = typedArray.getColor(R.styleable.GradientTextView_gradient_startColor, Color.BLACK);
        int centerColor = typedArray.getColor(R.styleable.GradientTextView_gradient_centerColor, 0);
        int endColor = typedArray.getColor(R.styleable.GradientTextView_gradient_endColor, Color.TRANSPARENT);
        orientation = BaseGradientTextView.Orientation.getOrientation(typedArray.getInteger(R.styleable.GradientTextView_gradient_orientation, BaseGradientTextView.Orientation.vertical.getValue()));
        typedArray.recycle();

        if (centerColor != 0) {
            gradientColors = new int[]{startColor, centerColor, endColor};
        } else {
            gradientColors = new int[]{startColor, endColor};
        }
        mTextPaint = getPaint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        LinearGradient linearGradient;
        if (orientation == BaseGradientTextView.Orientation.horizontal) {
            linearGradient = new LinearGradient(0, 0, getWidth(), getHeight(), gradientColors, gradientPositions, Shader.TileMode.MIRROR);
        } else {
            linearGradient = new LinearGradient(0, 0, 0, getHeight(), gradientColors, gradientPositions, Shader.TileMode.MIRROR);
        }
        mTextPaint.setShader(linearGradient);
        super.onDraw(canvas);
    }

    public BaseGradientTextView.Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(BaseGradientTextView.Orientation orientation) {
        this.orientation = orientation;
        invalidate();
    }

    public int[] getGradientColors() {
        return gradientColors;
    }

    public void setGradientColors(int[] gradientColors) {
        this.gradientColors = gradientColors;
        invalidate();
    }

    public float[] getGradientPositions() {
        return gradientPositions;
    }

    public void setGradientPositions(float[] gradientPositions) {
        this.gradientPositions = gradientPositions;
        invalidate();
    }
}
