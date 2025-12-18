package ntu.edu.nguyenquockhanh.btl_quizzing.style;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;

public class GradientTextView extends androidx.appcompat.widget.AppCompatTextView {

    public GradientTextView(Context context) {
        super(context);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w <= 0) return;
        Shader shader = new LinearGradient(
                0, 0,          // Top Right
                w, 0,          // Bottom Left
                new int[]{
                        Color.parseColor("#4136F0"),
                        Color.parseColor("#7A53F4")
                },
                null,
                Shader.TileMode.CLAMP
        );

        getPaint().setShader(shader);
    }
}
