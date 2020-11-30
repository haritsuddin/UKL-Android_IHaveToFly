package com.example.ihavetofly;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean playing;
    private Background background1, background2;
    private float screenRatioX, screenRatioY;
    private int ScreenX, ScreenY;
    private Paint paint;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.ScreenX = screenX;
        this.ScreenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        background2.x = screenX;

        paint = new Paint();
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {

        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;

        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = ScreenX;
        }

        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = ScreenX;
        }
    }

    private void draw() {

        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void sleep() {
        try {
            thread.sleep(17);
        } catch (InterruptedException e) {


        }
    }

    public void resume() {
        playing = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
