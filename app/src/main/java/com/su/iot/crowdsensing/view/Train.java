package com.su.iot.crowdsensing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.su.iot.crowdsensing.domain.Train;
import com.su.iot.crowdsensing.domain.TrainCoach;
import java.util.List;

public class TrainView extends View {

  private Paint carriagePaint;
  private Rect carriageRect;

  private Train train;

  public TrainView(Context c) {
    this(c, null);
  }

  public TrainView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public void setTrain(Train train) {
    this.train = train;
  }

  public Train getTrain() {
    return this.train;
  }

  private void init() {
    carriagePaint = new Paint();
    carriageRect = new Rect();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    int height;
    int width;
    super.onDraw(canvas);

    width = this.getWidth();
    height = this.getHeight();

    int paddingX = (int) (width * 0.25);
    int paddingY = (int) (height * 0.1);

    int trainHeight = height - paddingY * 2;
    int carriagePadding = (int) (trainHeight * 0.05);

    List<TrainCoach> trainCoaches = train.getTrainCoaches();
    int carriageHeight = trainHeight / trainCoaches.size();
    int crowdPadding = (int) (carriageHeight * 0.05);

    for (TrainCoach trainCoach : trainCoaches) {

      carriagePaint.setARGB(255, 202, 207, 210);
      carriageRect.set(
          paddingX,
          paddingY + (trainCoach.getPosition() - 1) * carriageHeight + carriagePadding / 2,
          width - paddingX,
          paddingY + trainCoach.getPosition() * carriageHeight - carriagePadding / 2
      );
      canvas.drawRect(carriageRect, carriagePaint);

      if (trainCoach.getCrowdedValue() <= 0.33) {
        carriagePaint.setARGB(255, 46, 204, 113);
      } else if (trainCoach.getCrowdedValue() <= 0.67) {
        carriagePaint.setARGB(255, 243, 156, 18);
      } else {
        carriagePaint.setARGB(255, 231, 76, 60);
      }
      carriageRect.set(
          paddingX + crowdPadding,
          paddingY + (trainCoach.getPosition() - 1) * carriageHeight + carriagePadding / 2
              + crowdPadding,
          width - paddingX - crowdPadding,
          paddingY + trainCoach.getPosition() * carriageHeight - carriagePadding / 2 - crowdPadding
      );
      canvas.drawRect(carriageRect, carriagePaint);

    }

  }
}
