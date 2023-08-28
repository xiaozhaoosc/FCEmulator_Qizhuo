package site.ken.framework.ui.multitouchbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

public class MultitouchButton extends AppCompatButton
        implements MultitouchBtnInterface {

    protected boolean repaint = true;
    OnMultitouchEventListener listener;

    public MultitouchButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MultitouchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onTouchEnter(MotionEvent event) {
        setPressed(true);

        if (listener != null) {
            listener.onMultitouchEnter(this);
        }
    }

    @Override
    public void onTouchExit(MotionEvent event) {
        setPressed(false);

        if (listener != null) {
            listener.onMultitouchExit(this);
        }
    }

    @Override
    public void setOnMultitouchEventlistener(OnMultitouchEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void requestRepaint() {
        repaint = true;
    }

    @Override
    public void removeRequestRepaint() {
        repaint = false;
    }

    @Override
    public boolean isRepaintState() {
        return repaint;
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

}
