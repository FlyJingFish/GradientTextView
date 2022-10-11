package com.flyjingfish.gradienttextviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

public class GradientColorStrokeTextView extends AppCompatTextView {

    private final TextView backGroundText;
    private int strokeWidth;
    private BaseGradientTextView.Orientation orientation;
    private int[] gradientColors;
    private float[] gradientPositions;

    public GradientColorStrokeTextView(Context context) {
        this(context, null);
    }

    public GradientColorStrokeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientColorStrokeTextView(Context context, AttributeSet attrs,
                                       int defStyle) {
        super(context, attrs, defStyle);
        backGroundText = new TextView(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientStrokeTextView);
        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.GradientStrokeTextView_gradient_stroke_strokeWidth, 0);
        int startColor = typedArray.getColor(R.styleable.GradientStrokeTextView_gradient_stroke_startColor, Color.BLACK);
        int centerColor = typedArray.getColor(R.styleable.GradientStrokeTextView_gradient_stroke_centerColor, 0);
        int endColor = typedArray.getColor(R.styleable.GradientStrokeTextView_gradient_stroke_endColor, Color.TRANSPARENT);
        orientation = BaseGradientTextView.Orientation.getOrientation(typedArray.getInteger(R.styleable.GradientStrokeTextView_gradient_stroke_orientation, BaseGradientTextView.Orientation.vertical.getValue()));
        typedArray.recycle();

        if (centerColor != 0) {
            gradientColors = new int[]{startColor, centerColor, endColor};
        } else {
            gradientColors = new int[]{startColor, endColor};
        }

        init();
    }


    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        backGroundText.setLayoutParams(params);
        super.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        CharSequence tt = backGroundText.getText();
        if (tt == null || !tt.equals(this.getText())) {
            backGroundText.setText(getText());
            this.postInvalidate();
        }
        backGroundText.measure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        backGroundText.layout(left, top, right, bottom);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint backGroundTextPaint = backGroundText.getPaint();
        LinearGradient linearGradient;
        if (orientation == BaseGradientTextView.Orientation.horizontal) {
            linearGradient = new LinearGradient(0, 0, getWidth(), getHeight(), gradientColors, gradientPositions, Shader.TileMode.MIRROR);
        } else {
            linearGradient = new LinearGradient(0, 0, 0, getHeight(), gradientColors, gradientPositions, Shader.TileMode.MIRROR);
        }
        backGroundTextPaint.setShader(linearGradient);
        backGroundText.draw(canvas);
        super.onDraw(canvas);
    }

    public void init() {
        TextPaint textPaint = backGroundText.getPaint();
        textPaint.setStrokeWidth(strokeWidth);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        backGroundText.setTextColor(Color.BLACK);
        backGroundText.setText(getText());
        backGroundText.setGravity(getGravity());
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        invalidate();
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
