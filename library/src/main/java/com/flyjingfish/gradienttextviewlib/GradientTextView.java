package com.flyjingfish.gradienttextviewlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.text.TextUtilsCompat;

import com.flyjingfish.perfecttextviewlib.PerfectTextView;

import java.util.Locale;

public class GradientTextView extends PerfectTextView {

    private final PerfectTextView backGroundText;
    private int strokeWidth;
    private int[] gradientStrokeColors;
    private float[] gradientStrokePositions;
    private int[] gradientColors;
    private float[] gradientPositions;
    private boolean gradientColor,gradientStrokeColor;
    private float strokeAngle;
    private boolean strokeRtlAngle;
    private float angle;
    private boolean rtlAngle;
    private boolean isRtl;
    private int strokeTextColor;

    public GradientTextView(Context context) {
        this(context, null);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientTextView(Context context, AttributeSet attrs,
                            int defStyle) {
        super(context, attrs, defStyle);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            isRtl = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL;
        }
        backGroundText = new PerfectTextView(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);
        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.GradientTextView_gradient_stroke_strokeWidth, 0);
        int startStrokeColor = typedArray.getColor(R.styleable.GradientTextView_gradient_stroke_startColor, 0);
        int centerStrokeColor = typedArray.getColor(R.styleable.GradientTextView_gradient_stroke_centerColor, 0);
        int endStrokeColor = typedArray.getColor(R.styleable.GradientTextView_gradient_stroke_endColor, 0);
        strokeTextColor = typedArray.getColor(R.styleable.GradientTextView_gradient_stroke_textColor, getCurrentTextColor());
        strokeAngle = typedArray.getFloat(R.styleable.GradientTextView_gradient_stroke_angle, 0);
        strokeRtlAngle = typedArray.getBoolean(R.styleable.GradientTextView_gradient_stroke_rtl_angle, false);

        int startColor = typedArray.getColor(R.styleable.GradientTextView_gradient_startColor, 0);
        int centerColor = typedArray.getColor(R.styleable.GradientTextView_gradient_centerColor, 0);
        int endColor = typedArray.getColor(R.styleable.GradientTextView_gradient_endColor, 0);
        angle = typedArray.getFloat(R.styleable.GradientTextView_gradient_angle, 0);
        rtlAngle = typedArray.getBoolean(R.styleable.GradientTextView_gradient_rtl_angle, false);
        int strokeJoinInt = typedArray.getInt(R.styleable.GradientTextView_gradient_stroke_join, Paint.Join.ROUND.ordinal());


        typedArray.recycle();

        if (startStrokeColor != 0 || centerStrokeColor != 0 || endStrokeColor != 0){
            if (centerStrokeColor != 0) {
                gradientStrokeColors = new int[]{startStrokeColor, centerStrokeColor, endStrokeColor};
            } else {
                gradientStrokeColors = new int[]{startStrokeColor, endStrokeColor};
            }
            gradientStrokeColor = true;
        }else {
            gradientStrokeColor = false;
        }


        if (startColor != 0 || centerColor != 0 || endColor != 0){
            if (centerColor != 0) {
                gradientColors = new int[]{startColor, centerColor, endColor};
            } else {
                gradientColors = new int[]{startColor, endColor};
            }
            gradientColor = true;
        }else {
            gradientColor = false;
        }
        TextPaint textPaint = backGroundText.getPaint();
        textPaint.setStrokeWidth(strokeWidth);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        if (strokeJoinInt >=0 && strokeJoinInt<=2){
            textPaint.setStrokeJoin(Paint.Join.values()[strokeJoinInt]);
        }else {
            textPaint.setStrokeJoin(Paint.Join.ROUND);
        }
        backGroundText.setTextColor(strokeTextColor);
        backGroundText.setText(getText());
        backGroundText.setGravity(getGravity());

        initCompoundDrawables();


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
        }
        backGroundText.measure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        backGroundText.layout(left, top, right, bottom);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint backGroundTextPaint = backGroundText.getPaint();
        if (gradientStrokeColor){
            float currentAngle = strokeAngle;
            if (strokeRtlAngle && isRtl){
                currentAngle = - strokeAngle;
            }
            float[] xy = getAngleXY(currentAngle);

            @SuppressLint("DrawAllocation") LinearGradient linearGradient = new LinearGradient(xy[0], xy[1], xy[2], xy[3],  gradientStrokeColors, gradientStrokePositions, Shader.TileMode.CLAMP);
            backGroundTextPaint.setShader(linearGradient);
        }else {
            backGroundTextPaint.setShader(null);
        }
        backGroundText.draw(canvas);


        if (gradientColor){
            float currentAngle = angle;
            if (rtlAngle && isRtl){
                currentAngle = - angle;
            }
            float[] xy = getAngleXY(currentAngle);

            @SuppressLint("DrawAllocation") LinearGradient linearGradient = new LinearGradient(xy[0], xy[1], xy[2], xy[3],  gradientColors, gradientPositions, Shader.TileMode.CLAMP);
            getPaint().setShader(linearGradient);
        }else {
            getPaint().setShader(null);
        }
        super.onDraw(canvas);

    }

    protected float[] getAngleXY(float currentAngle){
        Layout layout = getLayout();
        int height = layout.getHeight();
        int width = layout.getWidth();

        float angle = currentAngle % 360;
        if (angle < 0) {
            angle = 360 + angle;
        }
        float x0, y0, x1, y1;
        if (angle >= 0 && angle <= 45) {
            float percent = angle / 45;
            x0 = width / 2f + width / 2f * percent;
            y0 = 0;
        } else if (angle <= 90) {
            float percent = (angle - 45) / 45;
            x0 = width;
            y0 = height / 2f * percent;
        } else if (angle <= 135) {
            float percent = (angle - 90) / 45;
            x0 = width;
            y0 = height / 2f * percent + height / 2f;
        } else if (angle <= 180) {
            float percent = (angle - 135) / 45;
            x0 = width / 2f + width / 2f * (1-percent);
            y0 = height;
        } else if (angle <= 225) {
            float percent = (angle - 180) / 45;
            x0 = width / 2f - width / 2f * percent;
            y0 = height;
        } else if (angle <= 270) {
            float percent = (angle - 225) / 45;
            x0 = 0;
            y0 = height - height / 2f * percent;
        } else if (angle <= 315) {
            float percent = (angle - 270) / 45;
            x0 = 0;
            y0 = height / 2f - height / 2f * percent;
        } else {
            float percent = (angle - 315) / 45;
            x0 = width / 2f * percent;
            y0 = 0;
        }

        x1 = width - x0;
        y1 = height - y0;

        return new float[]{x0, y0, x1, y1};
    }


    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        TextPaint textPaint = backGroundText.getPaint();
        textPaint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public int[] getGradientStrokeColors() {
        return gradientStrokeColors;
    }

    public void setGradientStrokeColors(int[] gradientStrokeColors) {
        this.gradientStrokeColors = gradientStrokeColors;
        gradientStrokeColor = gradientStrokeColors != null;
        if (gradientStrokePositions != null && gradientStrokeColors != null && gradientStrokeColors.length != gradientStrokePositions.length){
            this.gradientStrokePositions = null;
        }
        invalidate();
    }

    public float[] getGradientStrokePositions() {
        return gradientStrokePositions;
    }

    public void setGradientStrokePositions(float[] gradientStrokePositions) {
        this.gradientStrokePositions = gradientStrokePositions;

        invalidate();
    }

    public int[] getGradientColors() {
        return gradientColors;
    }

    public void setGradientColors(int[] gradientColors) {
        this.gradientColors = gradientColors;
        if (gradientPositions != null && gradientColors != null && gradientColors.length != gradientPositions.length){
            this.gradientPositions = null;
        }
        invalidate();
    }

    public float[] getGradientPositions() {
        return gradientPositions;
    }

    public void setGradientPositions(float[] gradientPositions) {
        this.gradientPositions = gradientPositions;
        gradientColor = gradientPositions != null;
        invalidate();
    }

    public float getStrokeAngle() {
        return strokeAngle;
    }

    public void setStrokeAngle(float strokeAngle) {
        this.strokeAngle = strokeAngle;
        invalidate();
    }

    public boolean isStrokeRtlAngle() {
        return strokeRtlAngle;
    }

    public void setStrokeRtlAngle(boolean strokeRtlAngle) {
        this.strokeRtlAngle = strokeRtlAngle;
        invalidate();
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
        invalidate();
    }

    public boolean isRtlAngle() {
        return rtlAngle;
    }

    public void setRtlAngle(boolean rtlAngle) {
        this.rtlAngle = rtlAngle;
        invalidate();
    }

    public int getStrokeTextColor() {
        return strokeTextColor;
    }

    public void setStrokeTextColor(int strokeTextColor) {
        this.strokeTextColor = strokeTextColor;
        backGroundText.setTextColor(strokeTextColor);
        gradientStrokeColor = false;
        invalidate();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (backGroundText != null){
            backGroundText.setText(text, type);
        }
        super.setText(text, type);
    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        super.setCompoundDrawables(left, top, right, bottom);
        initCompoundDrawables();
    }

    @Override
    public void setCompoundDrawablesRelative(@Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        super.setCompoundDrawablesRelative(start, top, end, bottom);
        initCompoundDrawables();
    }

    @Override
    public void setCompoundDrawablePadding(int pad) {
        super.setCompoundDrawablePadding(pad);
        if (backGroundText != null){
            backGroundText.setCompoundDrawablePadding(pad);
        }
    }

    private void initCompoundDrawables(){
        if (backGroundText == null){
            return;
        }
        Drawable[] drawablesRelative = getCompoundDrawablesRelative();

        Drawable[] drawables = getCompoundDrawables();

        Drawable drawableLeft;
        Drawable drawableRight;
        Drawable drawableTop = null;
        Drawable drawableBottom = null;
        if (isRtl){
            if (drawablesRelative[0] != null || drawablesRelative[2] != null){
                drawableLeft = drawablesRelative[2];
                drawableRight = drawablesRelative[0];
            }else {
                drawableLeft = drawables[0];
                drawableRight = drawables[2];
            }

        }else {
            if (drawablesRelative[0] != null || drawablesRelative[2] != null){
                drawableLeft = drawablesRelative[0];
                drawableRight = drawablesRelative[2];
            }else {
                drawableLeft = drawables[0];
                drawableRight = drawables[2];
            }

        }

        if (drawablesRelative[1] != null){
            drawableTop = drawablesRelative[1];
        }else if (drawables[1] != null){
            drawableTop = drawables[1];
        }

        if (drawablesRelative[3] != null){
            drawableBottom = drawablesRelative[3];
        }else if (drawables[3] != null){
            drawableBottom = drawables[3];
        }

        backGroundText.setCompoundDrawables(drawableLeft,drawableTop,drawableRight,drawableBottom);
        backGroundText.setDrawableStartPadding(getDrawableStartPadding());
        backGroundText.setDrawableEndPadding(getDrawableEndPadding());
        backGroundText.setDrawableLeftPadding(getDrawableLeftPadding());
        backGroundText.setDrawableRightPadding(getDrawableRightPadding());
        backGroundText.setDrawableTopPadding(getDrawableTopPadding());
        backGroundText.setDrawableBottomPadding(getDrawableBottomPadding());
    }

    @Override
    public void setDrawableStart(int drawableStart) {
        backGroundText.setDrawableStart(drawableStart);
        super.setDrawableStart(drawableStart);
    }

    @Override
    public void setDrawableEnd(int drawableEnd) {
        backGroundText.setDrawableEnd(drawableEnd);
        super.setDrawableEnd(drawableEnd);
    }

    @Override
    public void setDrawableTop(int drawableTop) {
        backGroundText.setDrawableTop(drawableTop);
        super.setDrawableTop(drawableTop);
    }

    @Override
    public void setDrawableBottom(int drawableBottom) {
        backGroundText.setDrawableBottom(drawableBottom);
        super.setDrawableBottom(drawableBottom);
    }

    @Override
    public void setDrawableLeft(int drawableLeft) {
        backGroundText.setDrawableLeft(drawableLeft);
        super.setDrawableLeft(drawableLeft);
    }

    @Override
    public void setDrawableRight(int drawableRight) {
        backGroundText.setDrawableRight(drawableRight);
        super.setDrawableRight(drawableRight);
    }

    @Override
    public void setDrawableStart(Drawable drawableStart) {
        backGroundText.setDrawableStart(drawableStart);
        super.setDrawableStart(drawableStart);
    }

    @Override
    public void setDrawableEnd(Drawable drawableEnd) {
        backGroundText.setDrawableEnd(drawableEnd);
        super.setDrawableEnd(drawableEnd);
    }

    @Override
    public void setDrawableLeft(Drawable drawableLeft) {
        backGroundText.setDrawableLeft(drawableLeft);
        super.setDrawableLeft(drawableLeft);
    }

    @Override
    public void setDrawableRight(Drawable drawableRight) {
        backGroundText.setDrawableRight(drawableRight);
        super.setDrawableRight(drawableRight);
    }

    @Override
    public void setDrawableTop(Drawable drawableTop) {
        backGroundText.setDrawableTop(drawableTop);
        super.setDrawableTop(drawableTop);
    }

    @Override
    public void setDrawableBottom(Drawable drawableBottom) {
        backGroundText.setDrawableBottom(drawableBottom);
        super.setDrawableBottom(drawableBottom);
    }


    @Override
    public void setSelectedText(CharSequence selectedText) {
        backGroundText.setSelectedText(selectedText);
        super.setSelectedText(selectedText);
    }

    @Override
    public void setSelectedText(int resid) {
        backGroundText.setSelectedText(resid);
        super.setSelectedText(resid);
    }

    @Override
    public void setDefaultHint(CharSequence defaultHint) {
        backGroundText.setDefaultHint(defaultHint);
        super.setDefaultHint(defaultHint);
    }

    @Override
    public void setSelectedHint(CharSequence selectedHint) {
        backGroundText.setSelectedHint(selectedHint);
        super.setSelectedHint(selectedHint);
    }

    @Override
    public void setDefaultHint(int resid) {
        backGroundText.setDefaultHint(resid);
        super.setDefaultHint(resid);
    }

    @Override
    public void setSelectedHint(int resid) {
        backGroundText.setSelectedHint(resid);
        super.setSelectedHint(resid);
    }

    @Override
    public void setDrawableStartWidthHeight(int width, int height) {
        backGroundText.setDrawableStartWidthHeight(width, height);
        super.setDrawableStartWidthHeight(width, height);
    }

    @Override
    public void setDrawableTopWidthHeight(int width, int height) {
        backGroundText.setDrawableTopWidthHeight(width, height);
        super.setDrawableTopWidthHeight(width, height);
    }

    @Override
    public void setDrawableEndWidthHeight(int width, int height) {
        backGroundText.setDrawableEndWidthHeight(width, height);
        super.setDrawableEndWidthHeight(width, height);
    }

    @Override
    public void setDrawableBottomWidthHeight(int width, int height) {
        backGroundText.setDrawableBottomWidthHeight(width, height);
        super.setDrawableBottomWidthHeight(width, height);
    }

    @Override
    public void setDrawableLeftWidthHeight(int width, int height) {
        backGroundText.setDrawableLeftWidthHeight(width, height);
        super.setDrawableLeftWidthHeight(width, height);
    }

    @Override
    public void setDrawableRightWidthHeight(int width, int height) {
        backGroundText.setDrawableRightWidthHeight(width, height);
        super.setDrawableRightWidthHeight(width, height);
    }

    @Override
    public void setDrawableStartPadding(int drawableStartPadding) {
        backGroundText.setDrawableStartPadding(drawableStartPadding);
        super.setDrawableStartPadding(drawableStartPadding);
    }

    @Override
    public void setDrawableTopPadding(int drawableTopPadding) {
        backGroundText.setDrawableTopPadding(drawableTopPadding);
        super.setDrawableTopPadding(drawableTopPadding);
    }

    @Override
    public void setDrawableEndPadding(int drawableEndPadding) {
        backGroundText.setDrawableEndPadding(drawableEndPadding);
        super.setDrawableEndPadding(drawableEndPadding);
    }

    @Override
    public void setDrawableBottomPadding(int drawableBottomPadding) {
        backGroundText.setDrawableBottomPadding(drawableBottomPadding);
        super.setDrawableBottomPadding(drawableBottomPadding);
    }

    @Override
    public void setDrawableLeftPadding(int drawableLeftPadding) {
        backGroundText.setDrawableLeftPadding(drawableLeftPadding);
        super.setDrawableLeftPadding(drawableLeftPadding);
    }

    @Override
    public void setDrawableRightPadding(int drawableRightPadding) {
        backGroundText.setDrawableRightPadding(drawableRightPadding);
        super.setDrawableRightPadding(drawableRightPadding);
    }

    /**
     * 请于{@link android.widget.TextView#setText}之前调用，否则不起效果
     * @param join 粗边样式
     */
    public void setStrokeJoin(Paint.Join join){
        final TextPaint textPaint = backGroundText.getPaint();
        textPaint.setStrokeJoin(join);
    }
}
