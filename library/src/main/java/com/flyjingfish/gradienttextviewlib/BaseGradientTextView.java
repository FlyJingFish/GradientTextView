package com.flyjingfish.gradienttextviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

public class BaseGradientTextView extends AppCompatTextView {

    public BaseGradientTextView(@NonNull Context context) {
        super(context);
    }

    public BaseGradientTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseGradientTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public enum Orientation {// 渐变方向
        vertical(0x01),

        horizontal(0x02);

        private int value;

        public int getValue() {
            return value;
        }

        Orientation(int value) {
            this.value = value;
        }

        public static Orientation getOrientation(int orientation){
            if (orientation == vertical.getValue()){
                return vertical;
            }else {
                return horizontal;
            }
        }
    }

}
