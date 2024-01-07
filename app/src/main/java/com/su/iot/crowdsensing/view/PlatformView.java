package com.su.iot.crowdsensing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class PlatformView extends View {

  private int width;
  private int height;
  private int paddingX;
  private int paddingY;

  private final Paint platformPaint;

  private final Paint borderPaint;


  public PlatformView(Context c) {
    this(c, null);
  }

  public PlatformView(Context context, AttributeSet attrs) {
    super(context, attrs);
    platformPaint = new Paint();

    platformPaint.setColor(Color.BLACK);
    platformPaint.setTextSize(60);
    platformPaint.setTextAlign(Paint.Align.CENTER);

    borderPaint = new Paint();
    borderPaint.setColor(Color.BLACK);
    borderPaint.setStyle(Paint.Style.STROKE);
    borderPaint.setStrokeWidth(3); // Set border width
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    width = this.getWidth();
    height = this.getHeight();

    paddingX = (int) (width * 0.25);
    paddingY = (int) (height * 0.1);

    drawPlatform(canvas);
  }

  private void drawPlatform(Canvas canvas) {

    int endX = width - paddingX;
    int endY = height - paddingY;

    // Draw a border around the canvas
    canvas.drawRect(paddingX, paddingY, endX, endY, borderPaint);

    float x = getWidth() / 1.8f;
    float y = getHeight() / 2f;

    canvas.rotate(-90, x, y);

    canvas.drawText("PLATFORM", x, y, platformPaint);
  }
}
