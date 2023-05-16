package com.flyjingfish.gradienttextviewlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.LeadingMarginSpan;
import android.util.AttributeSet;
import android.util.LayoutDirection;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.text.TextUtilsCompat;

import com.flyjingfish.perfecttextviewlib.PerfectTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class GradientTextView extends PerfectTextView {

    private int strokeWidth;
    private final List<ColorStateList> gradientStrokeColorStates = new ArrayList<>();
    private final List<ColorStateList> gradientColorStates = new ArrayList<>();
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
    private ColorStateList strokeTextColor;
    private int curStrokeTextColor;
    private Paint.Join strokeJoin;

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
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);
        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.GradientTextView_gradient_stroke_strokeWidth, 0);
        ColorStateList startStrokeColor = typedArray.getColorStateList(R.styleable.GradientTextView_gradient_stroke_startColor);
        ColorStateList centerStrokeColor = typedArray.getColorStateList(R.styleable.GradientTextView_gradient_stroke_centerColor);
        ColorStateList endStrokeColor = typedArray.getColorStateList(R.styleable.GradientTextView_gradient_stroke_endColor);
        strokeTextColor = typedArray.getColorStateList(R.styleable.GradientTextView_gradient_stroke_textColor);
        strokeAngle = typedArray.getFloat(R.styleable.GradientTextView_gradient_stroke_angle, 0);
        strokeRtlAngle = typedArray.getBoolean(R.styleable.GradientTextView_gradient_stroke_rtl_angle, false);

        ColorStateList startColor = typedArray.getColorStateList(R.styleable.GradientTextView_gradient_startColor);
        ColorStateList centerColor = typedArray.getColorStateList(R.styleable.GradientTextView_gradient_centerColor);
        ColorStateList endColor = typedArray.getColorStateList(R.styleable.GradientTextView_gradient_endColor);
        angle = typedArray.getFloat(R.styleable.GradientTextView_gradient_angle, 0);
        rtlAngle = typedArray.getBoolean(R.styleable.GradientTextView_gradient_rtl_angle, false);
        int strokeJoinInt = typedArray.getInt(R.styleable.GradientTextView_gradient_stroke_join, Paint.Join.ROUND.ordinal());


        typedArray.recycle();

        if (strokeTextColor == null){
            strokeTextColor = getTextColors();
        }
        if (startStrokeColor != null){
            gradientStrokeColorStates.add(startStrokeColor);
        }
        if (centerStrokeColor != null){
            gradientStrokeColorStates.add(centerStrokeColor);
        }
        if (endStrokeColor != null){
            gradientStrokeColorStates.add(endStrokeColor);
        }
        if (gradientStrokeColorStates.size() == 1){
            gradientStrokeColorStates.add(ColorStateList.valueOf(Color.TRANSPARENT));
        }

        if (startColor != null){
            gradientColorStates.add(startColor);
        }
        if (centerColor != null){
            gradientColorStates.add(centerColor);
        }
        if (endColor != null){
            gradientColorStates.add(endColor);
        }
        if (gradientColorStates.size() == 1){
            gradientColorStates.add(ColorStateList.valueOf(Color.TRANSPARENT));
        }
        gradientStrokeColor = gradientStrokeColorStates.size() > 0;
        gradientColor = gradientColorStates.size() > 0;
        updateColors();
        if (strokeJoinInt >=0 && strokeJoinInt<=2){
            strokeJoin = Paint.Join.values()[strokeJoinInt];
        }else {
            strokeJoin = Paint.Join.ROUND;
        }

        CharSequence text = getText();
        setText(text);
    }

    static CharSequence createIndentedText(CharSequence text, int marginFirstLine, int marginNextLines) {
        SpannableString result = new SpannableString(text);
        result.setSpan(new LeadingMarginSpan.Standard(marginFirstLine, marginNextLines), 0, text.length(), 0);
        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && strokeWidth > 0){
            int measureWidth = getMeasuredWidth();
            int width = MeasureSpec.getSize(widthMeasureSpec);
            if (measureWidth < width){
                int measureHeight = getMeasuredHeight();
//                int height = MeasureSpec.getSize(heightMeasureSpec);
                int heightMode = MeasureSpec.getMode(heightMeasureSpec);
                int newWidth = MeasureSpec.makeMeasureSpec(measureWidth+Math.min(strokeWidth/2,width-measureWidth), widthMode);
                setMeasuredDimension(newWidth,MeasureSpec.makeMeasureSpec(measureHeight, heightMode));
            }
        }
    }
    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateColors();
    }

    private boolean updateColors(){
        boolean inval = false;
        final int[] drawableState = getDrawableState();
        int color = strokeTextColor.getColorForState(drawableState, 0);
        if (color != curStrokeTextColor) {
            curStrokeTextColor = color;
            inval = true;
        }

        if (gradientStrokeColorStates != null && gradientStrokeColorStates.size() > 0){
            int[] gradientColors = new int[gradientStrokeColorStates.size()];
            for (int i = 0; i < gradientStrokeColorStates.size(); i++) {
                int gradientColor = gradientStrokeColorStates.get(i).getColorForState(drawableState, 0);
                gradientColors[i] = gradientColor;
            }
            if (gradientStrokeColors == null) {
                gradientStrokeColors = gradientColors;
                inval = true;
            } else if (gradientStrokeColors.length != gradientColors.length){
                gradientStrokeColors = gradientColors;
                inval = true;
            } else {
                boolean equals = true;
                for (int i = 0; i < gradientStrokeColors.length; i++) {
                    if (gradientStrokeColors[i] != gradientColors[i]){
                        equals = false;
                        break;
                    }
                }
                if (!equals){
                    gradientStrokeColors = gradientColors;
                    inval = true;
                }
            }
        }

        if (gradientColorStates != null && gradientColorStates.size() > 0){
            int[] gradientCls = new int[gradientColorStates.size()];
            for (int i = 0; i < gradientColorStates.size(); i++) {
                int gradientColor = gradientColorStates.get(i).getColorForState(drawableState, 0);
                gradientCls[i] = gradientColor;
            }
            if (gradientColors == null) {
                gradientColors = gradientCls;
                inval = true;
            } else if (gradientColors.length != gradientCls.length){
                gradientColors = gradientCls;
                inval = true;
            } else {
                boolean equals = true;
                for (int i = 0; i < gradientColors.length; i++) {
                    if (gradientColors[i] != gradientCls[i]){
                        equals = false;
                        break;
                    }
                }
                if (!equals){
                    gradientColors = gradientCls;
                    inval = true;
                }
            }
        }

        if (inval){
            invalidate();
        }
        return inval;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint textPaint = getPaint();
        Paint.Style oldStyle = textPaint.getStyle();
        textPaint.setStrokeWidth(strokeWidth);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setStrokeJoin(strokeJoin);
        LinearGradient linearGradient;
        if (gradientStrokeColor){
            float currentAngle = strokeAngle;
            if (strokeRtlAngle && isRtl){
                currentAngle = - strokeAngle;
            }
            float[] xy = getAngleXY(currentAngle);

            linearGradient = new LinearGradient(xy[0], xy[1], xy[2], xy[3],  gradientStrokeColors, gradientStrokePositions, Shader.TileMode.CLAMP);
        }else {
            linearGradient = new LinearGradient(0, 0,getWidth(),getHeight(),  new int[]{curStrokeTextColor,curStrokeTextColor}, null, Shader.TileMode.CLAMP);
        }
        textPaint.setShader(linearGradient);
        super.onDraw(canvas);

        textPaint.setStrokeWidth(0);
        textPaint.setStyle(oldStyle);
        if (gradientColor){
            float currentAngle = angle;
            if (rtlAngle && isRtl){
                currentAngle = - angle;
            }
            float[] xy = getAngleXY(currentAngle);

            linearGradient = new LinearGradient(xy[0], xy[1], xy[2], xy[3],  gradientColors, gradientPositions, Shader.TileMode.CLAMP);
        }else {
            linearGradient = null;
        }
        textPaint.setShader(linearGradient);
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
        invalidate();
    }

    public int[] getGradientStrokeColors() {
        return gradientStrokeColors;
    }

    public List<ColorStateList> getGradientStrokeColorStates() {
        return gradientStrokeColorStates;
    }

    public void setGradientStrokeColors(@Nullable @ColorInt int[] gradientStrokeColors) {
        ColorStateList[] colorStateLists;
        if (gradientStrokeColors != null){
            colorStateLists = new ColorStateList[gradientStrokeColors.length];
            for (int i = 0; i < gradientStrokeColors.length; i++) {
                colorStateLists[i] = ColorStateList.valueOf(gradientStrokeColors[i]);
            }
        }else {
            colorStateLists = null;
        }
        setGradientStrokeColors(colorStateLists);
    }

    public void setGradientStrokeColors(@Nullable ColorStateList[] colorStateLists) {
        gradientStrokeColorStates.clear();
        if (colorStateLists != null){
            gradientStrokeColorStates.addAll(Arrays.asList(colorStateLists));
            if (gradientStrokeColorStates.size() == 1){
                gradientStrokeColorStates.add(ColorStateList.valueOf(Color.TRANSPARENT));
            }
            gradientStrokeColor = gradientStrokeColorStates.size() > 0;
            if (gradientStrokePositions != null && gradientStrokeColorStates.size() != gradientStrokePositions.length){
                this.gradientStrokePositions = null;
            }
            updateColors();
        }else {
            gradientStrokeColor = false;
            if (!updateColors()){
                invalidate();
            }
        }
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

    public List<ColorStateList> getGradientColorStates() {
        return gradientColorStates;
    }
    public void setGradientColors(@Nullable @ColorInt int[] gradientColors) {
        ColorStateList[] colorStateLists;
        if (gradientColors != null){
            colorStateLists = new ColorStateList[gradientColors.length];
            for (int i = 0; i < gradientColors.length; i++) {
                colorStateLists[i] = ColorStateList.valueOf(gradientColors[i]);
            }
        }else {
            colorStateLists = null;
        }
        setGradientColors(colorStateLists);
    }

    public void setGradientColors(@Nullable ColorStateList[] colorStateLists) {
        gradientColorStates.clear();
        if (colorStateLists != null){
            gradientColorStates.addAll(Arrays.asList(colorStateLists));
            if (gradientColorStates.size() == 1){
                gradientColorStates.add(ColorStateList.valueOf(Color.TRANSPARENT));
            }
            gradientStrokeColor = gradientColorStates.size() > 0;
            if (gradientPositions != null && gradientColorStates.size() != gradientPositions.length){
                this.gradientPositions = null;
            }
            updateColors();
        }else {
            gradientStrokeColor = false;
            if (!updateColors()){
                invalidate();
            }
        }
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
        return curStrokeTextColor;
    }
    public ColorStateList getStrokeTextColors() {
        return strokeTextColor;
    }

    public void setStrokeTextColor(@ColorInt int strokeTextColor) {
        setStrokeTextColors(ColorStateList.valueOf(strokeTextColor));
    }

    public void setStrokeTextColors(ColorStateList strokeTextColor) {
        if (strokeTextColor == null){
            return;
        }
        this.strokeTextColor = strokeTextColor;
        gradientStrokeColor = false;
        updateColors();
    }

    @Override
    public void setTextColor(int color) {
        gradientColor = false;
        super.setTextColor(color);
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        gradientColor = false;
        super.setTextColor(colors);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (strokeWidth > 0){
            text = createIndentedText(text, strokeWidth/2, strokeWidth/2);
        }
        super.setText(text, type);
    }
    /**
     * 请于{@link android.widget.TextView#setText}之前调用，否则不起效果
     * @param join 粗边样式
     */
    public void setStrokeJoin(Paint.Join join){
        strokeJoin = join;
        invalidate();
    }
}
