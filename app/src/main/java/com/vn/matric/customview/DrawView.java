package com.vn.matric.customview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.vn.matric.adapter.OnCheckEndListener;
import com.vn.matric.HomeActivity;

public class DrawView extends View {
    private Path path;
    private Paint paint;
    private boolean isDrawingFinished = false;
    private boolean isToastShown = false;
    private RelativeLayout pointRel;
    private OnCheckEndListener onCheckEndListener;

    // Biến mới để lưu trữ tọa độ và chiều dài
    private float oldX, oldY;
    private float totalLength = 0;

    public void setOnCheckEndListener(OnCheckEndListener listener) {
        this.onCheckEndListener = listener;
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paint = new Paint();
        paint.setColor(0xFFFFFFFF);
        paint.setStrokeWidth(12);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void setPointRel(RelativeLayout pointRel) {
        this.pointRel = pointRel;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                oldX = x;
                oldY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);

                // Tính chiều dài của đoạn đường
                float dx = x - oldX;
                float dy = y - oldY;
                float segmentLength = (float) Math.sqrt(dx * dx + dy * dy);
                totalLength += segmentLength;
                Log.d("Test_1", "onCreate: "+totalLength);

                oldX = x;
                oldY = y;

                // Kiểm tra vị trí của pointRel
                if (pointRel != null) {
                    int[] location = new int[2];
                    pointRel.getLocationOnScreen(location);
                    int pointRelX = location[0];
                    int pointRelY = location[1];
                    int pointRelWidth = pointRel.getWidth();
                    int pointRelHeight = pointRel.getHeight();

                    if (x >= pointRelX && x <= (pointRelX + pointRelWidth) &&
                            y >= pointRelY && y <= (pointRelY + pointRelHeight)) {
                        if (!isToastShown) {
                            if (onCheckEndListener != null) {
                                onCheckEndListener.onCheckEnd("WIN");
                            }
                            Intent intent = new Intent(getContext(), HomeActivity.class);
                            getContext().startActivity(intent);
                            isToastShown = true;
                        }
                    } else {
                        isToastShown = false;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                isDrawingFinished = true;
                invalidate();
                if (!isToastShown) {
                    if (onCheckEndListener != null) {
                        onCheckEndListener.onCheckEnd("LOSE");
                    }
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    getContext().startActivity(intent);
                }
                break;

            default:
                return false;
        }
        invalidate();
        return true;
    }

    public float getTotalLength() {
        return totalLength;
    }
}
