package com.example.duynguyen.game;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.duynguyen.game.Brick;
import com.example.duynguyen.game.R;
import com.example.duynguyen.game.SoundManager;

import java.util.ArrayList;
import java.util.Random;

public class myview extends View implements Runnable {

    private int x1=300, y1=300, dx1=5, dy1=5;
    private Bitmap ball,ball1;
    private  Bitmap ballResize, ballResize1;
    float width = 120;
    float height = 10;

    private SoundManager soundManager;

    float left = (getWidth() - width) / 2.0f;
    float top = (getHeight() - height) / 2.0f;
    boolean stop = false;

    private ArrayList<Brick> lists = new ArrayList<>();


    public myview(Context context, AttributeSet attrs) {
        super(context, attrs);


//        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ic_brightness_1_black_24dp);
//        ballResize = Bitmap.createScaledBitmap(ball,50,50,false);
//        ball1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
//        ballResize1 = Bitmap.createScaledBitmap(ball1,150,40,false);

        soundManager = SoundManager.getInstance();
        soundManager.init(context);

        for (int i = 0; i <7; i++) {
            Brick brick0 = new Brick(77 * i , 0, 72, 30);
            Brick brick1 = new Brick(77 * i , 33, 72, 30);
            Brick brick2 = new Brick(77 * i , 66, 72, 30);
            Brick brick3 = new Brick(77 * i , 99, 72, 30);
            Brick brick4 = new Brick(77 * i , 132, 72, 30);
            Brick brick5 = new Brick(77 * i , 165, 72, 30);
            Brick brick6 = new Brick(77 * i , 198, 72, 30);
            Brick brick7 = new Brick(77 * i , 231, 72, 30);
            Brick brick8 = new Brick(77 * i , 264, 72, 30);
            Brick brick9 = new Brick(77 * i , 297, 72, 30);

            lists.add(brick0);
            lists.add(brick1);
            lists.add(brick2);
            lists.add(brick3);
            lists.add(brick4);
            lists.add(brick5);
            lists.add(brick6);
            lists.add(brick7);
            lists.add(brick8);
            lists.add(brick9);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = getWidth();

        int y = getHeight();

        int radius;

        radius = 10;

        Paint paint = new Paint();

        paint.setStyle(Paint.Style.FILL);

        paint.setColor(Color.WHITE);

        canvas.drawPaint(paint);
        // Use Color.parseColor to define HTML colors

        paint.setColor(Color.parseColor("#CD5C5C"));

        canvas.drawCircle(x1, y1, 20, paint);
        canvas.drawCircle(x1,	y1,	radius,	paint);

        top = this.getHeight() - 80;
        canvas.drawRect(left, top, left + width, top + height, paint);
        //  canvas.drawBitmap(ballResize, x1, y1, null);

        if (x1 > x || x1 < 0){
            dx1 = -dx1;
        }
        if (y1 > y || y1 < 0){
            dy1 = -dy1;
        }
        if (y1 > y - 100){
            if (x1 > left - 10 && x1 < left + 120){
                dy1 = -dy1;
                soundManager.playSound(R.raw.gun);
            }
//           else {
//               Paint paint1 = new Paint();
//               paint1.setTextSize(50);
//               canvas.drawText("Game over",120,450,paint1);
//               soundManager.playSound(R.raw.destroy);
//               stop = true;
//           }
        }
        if (y1 > y - 20){
            Paint paint1 = new Paint();
            paint1.setTextSize(50);
            canvas.drawText("Game over",120,450,paint1);
            soundManager.playSound(R.raw.gun);
            stop = true;
        }


        for(Brick element : lists){
            element.drawBrick(canvas, paint);
            if(element.getVisibility()) {
                //kiểm tra ball va chạm với gạch
                if(y1 > element.getY() && y1 < (element.getY() + element.getHeight())){
                    if (x1 > element.getX() && x1 < (element.getX() + element.getWidth())){
                        // viên nào bể thì set visible = false
                        element.setInVisible();
                        dy1 = -dy1;
                        dx1 = -dx1;
                        soundManager.playSound(R.raw.gun);
                    }
                }
            }
        }
        if (!stop){
            invalidate();
        }
        update();
    }
    void update()
    {
        x1 += dx1;
        y1 += dy1;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        boolean handled = false;

        int xTouch;
        int yTouch;

        int actionIndex = event.getActionIndex();


        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                xTouch = (int) event.getX(0);
                yTouch = (int) event.getY(0);


                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                xTouch = (int) event.getX(actionIndex);
                yTouch = (int) event.getY(actionIndex);


                handled = true;
                break;

            case MotionEvent.ACTION_MOVE:
                final int pointerCount = event.getPointerCount();

                for (actionIndex = 0; actionIndex < pointerCount; actionIndex++) {

                    xTouch = (int) event.getX(actionIndex);
                    yTouch = (int) event.getY(actionIndex);
                    left = xTouch;

                }

                // invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_UP:

                // invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_UP:

                //  invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_CANCEL:

                handled = true;
                break;

            default:
                // do nothing
                break;
        }
        return super.onTouchEvent(event) || handled;
    }

    @Override
    public void run() {

    }
}
