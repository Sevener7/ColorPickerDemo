package colorpickview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * FileName: ColorPalette
 * Date: 2020/10/20 11:29
 * Author: SCL
 * e-mail: sucl@dqist.com
 **/
public class ColorPalette extends View {
    private float centerX;
    private float centerY;
    private float radius;

    private Paint huePaint;
    private Paint saturationPaint;

    public ColorPalette(Context context, AttributeSet attrs) {
        super(context, attrs);
        huePaint = new Paint();
        saturationPaint = new Paint();
    }

    public ColorPalette(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int newWidth = w - getPaddingLeft() - getPaddingRight();
        int newHeight = h - getPaddingTop() - getPaddingBottom();
        centerX = w * 0.5f;
        centerY = h * 0.5f;
        radius = Math.min(newWidth, newHeight) * 0.38f;
        Shader shaderHue = new SweepGradient(centerX, centerY, new int[]{Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED}, null);
        huePaint.setShader(shaderHue);
        Shader saturationShader = new RadialGradient(centerX, centerY, radius,
                Color.WHITE, 0x00FFFFFF, Shader.TileMode.CLAMP);
        saturationPaint.setShader(saturationShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerX, centerY, radius, huePaint);
        canvas.drawCircle(centerX, centerY, radius, saturationPaint);

    }
}
