package site.ken.framework.controllers;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import site.ken.framework.Emulator;
import site.ken.framework.EmulatorController;
import site.ken.framework.base.EmulatorActivity;
import site.ken.framework.base.ViewPort;

import site.ken.framework.gamedata.dao.entity.GameEntity;
import site.ken.framework.ui.preferences.PreferenceUtil;

public class ZapperGun implements EmulatorController {

    private float startX;
    private float startY;
    private boolean startedInside = false;
    private Context context;
    private Emulator emulator;
    private EmulatorActivity emulatorActivity;
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;
    private float vpw;
    private float vph;
    private boolean inited = false;
    private boolean isEnabled = false;

    public ZapperGun(Context context, EmulatorActivity activity) {
        this.context = context;
        this.emulatorActivity = activity;
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void connectToEmulator(int port, Emulator emulator) {
        this.emulator = emulator;
    }

    @Override
    public View getView() {
        return new View(context) {
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                if (!isEnabled) {
                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float x = event.getX();
                    float y = event.getY();

                    if (!startedInside
                            && (x >= minX && y >= minY && x <= maxX && y <= maxY)) {
                        emulator.fireZapper(-1, -1);
                    }
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!inited) {
                        ViewPort viewPort = emulatorActivity.getViewPort();

                        if (viewPort == null) {
                            return true;
                        }

                        minX = viewPort.x;
                        minY = viewPort.y;
                        maxX = minX + viewPort.width - 1;
                        maxY = minY + viewPort.height - 1;
                        vpw = viewPort.width;
                        vph = viewPort.height;
                        inited = true;
                    }

                    float x = event.getX();
                    float y = event.getY();
                    startedInside = false;

                    if (x >= minX && y >= minY && x <= maxX && y <= maxY) {
                        startedInside = true;
                        float tx = (x - minX) / vpw;
                        float ty = (y - minY) / vph;
                        emulator.fireZapper(tx, ty);
                    }
                }

                return true;
            }
        };
    }

    @Override
    public void onDestroy() {
        context = null;
        emulator = null;
        emulatorActivity = null;
    }

    @Override
    public void onGameStarted(GameEntity game) {
        isEnabled = PreferenceUtil.isZapperEnabled(context, game.checksum);
    }

    @Override
    public void onGamePaused(GameEntity game) {
    }

}
