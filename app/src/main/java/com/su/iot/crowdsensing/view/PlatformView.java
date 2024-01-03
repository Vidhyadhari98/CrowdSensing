package com.su.iot.crowdsensing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PlatformView extends View {

  private int width;
  private int height;
  private int paddingX;
  private int paddingY;

  private Paint platformPaint;
  private Rect platformRect;

  private Paint textPaint;
  private String platformText;

  private Paint locationPaintStroke;
  private Paint locationPaintFill;
  private RectF locationRectF;
  private double locationPosition;

  public PlatformView(Context c) {
    this(c, null);
  }

  public PlatformView(Context context, AttributeSet attrs) {
    super(context, attrs);
    platformPaint = new Paint();
    platformRect = new Rect();

    textPaint = new Paint();
    platformText = "Platform";

    locationPaintStroke = new Paint();
    locationPaintFill = new Paint();
    locationRectF = new RectF();
    locationPosition = 0;
  }

  public void locationChanged(double position) {
    this.locationPosition = position;
    this.invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    width = this.getWidth();
    height = this.getHeight();

    paddingX = (int) (width * 0.25);
    paddingY = (int) (height * 0.1);

    drawPlatform(canvas);
    drawCircle(canvas);
  }

  private void drawPlatform(Canvas canvas) {
    int endX = width - paddingX;
    int endY = height - paddingY;

    platformPaint.setARGB(255, 202, 207, 210);

    platformRect.set(paddingX, paddingY, endX, endY);

    canvas.drawRect(platformRect, platformPaint);
  }

  private void drawCircle(Canvas canvas) {
    int x = width / 2;
    int offset = height - paddingY * 2;
    int y = paddingY + (int) (offset * locationPosition);
    int size = 25;

    locationPaintFill.setARGB(255, 52, 152, 219);
    locationPaintFill.setStyle(Paint.Style.FILL);
    locationRectF.set(x - size, y - size, x + size, y + size);
    canvas.drawOval(locationRectF, locationPaintFill);

    locationPaintStroke.setARGB(255, 255, 255, 255);
    locationPaintStroke.setStyle(Paint.Style.STROKE);
    locationPaintStroke.setStrokeWidth(6);
    locationPaintStroke.setStrokeCap(Paint.Cap.ROUND);
    locationRectF.set(x - size, y - size, x + size, y + size);
    canvas.drawOval(locationRectF, locationPaintStroke);
  }

}
