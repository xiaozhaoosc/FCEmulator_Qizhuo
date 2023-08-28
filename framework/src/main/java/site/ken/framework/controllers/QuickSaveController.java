package site.ken.framework.controllers;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;

import site.ken.framework.Emulator;
import site.ken.framework.EmulatorController;
import site.ken.framework.base.EmulatorActivity;

import site.ken.framework.gamedata.dao.entity.GameEntity;
import site.ken.framework.ui.preferences.PreferenceUtil;

public class QuickSaveController implements EmulatorController {

    TouchController touchController;
    EmulatorActivity activity;
    private GestureDetectorCompat gestureDetector;
    private int screenCenterX;
    private boolean isEnabled;

    public QuickSaveController(EmulatorActivity activity,
                               TouchController touchController) {
        this.activity = activity;
        this.touchController = touchController;
        this.gestureDetector = new GestureDetectorCompat(activity, new GestureListener());
    }

    @Override
    public void onResume() {
        isEnabled = PreferenceUtil.isQuickSaveEnabled(activity);
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    }

    @Override
    public void onGameStarted(GameEntity game) {
    }

    @Override
    public void onGamePaused(GameEntity game) {
    }

    @Override
    public void connectToEmulator(int port, Emulator emulator) {
    }

    @Override
    public View getView() {
        return new View(activity) {
            @Override
            protected void onSizeChanged(int w, int h, int oldw, int oldh) {
                super.onSizeChanged(w, h, oldw, oldh);
                screenCenterX = w / 2;
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                if (!isEnabled) {
                    return true;
                }
                int pointerId = event.getPointerId(event.getActionIndex());
                return touchController.isPointerHandled(pointerId)
                        || gestureDetector.onTouchEvent(event);

            }
        };
    }

    @Override
    public void onDestroy() {
        activity = null;
        touchController = null;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }


        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float x = e.getX();

            if (x < screenCenterX) {
                activity.quickLoad();

            } else if (x > screenCenterX) {
                activity.quickSave();
            }

            return true;
        }
    }

}
