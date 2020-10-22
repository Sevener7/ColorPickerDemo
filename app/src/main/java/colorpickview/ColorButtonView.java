package colorpickview;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * FileName: ColorButtonView
 * Date: 2020/10/21 11:10
 * Author: SCL
 * e-mail: sucl@dqist.com
 **/
public class ColorButtonView extends View {
    private Paint paint;
    public ColorButtonView(Context context) {
        this(context,null);
    }

    public ColorButtonView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
