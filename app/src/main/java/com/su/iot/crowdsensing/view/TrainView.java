package com.su.iot.crowdsensing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.su.iot.crowdsensing.domain.Train;
import com.su.iot.crowdsensing.domain.TrainCoach;
import java.util.List;

public class TrainView extends View {

  private final Paint coachPaint;
  private final Rect coachRect;

  private Train train;

  public TrainView(Context c) {
    this(c, null);
  }

  public TrainView(Context context, AttributeSet attrs) {
    super(context, attrs);
    coachPaint = new Paint();
    coachRect = new Rect();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    int paddingX = (int) (this.getWidth() * 0.25);
    int paddingY = (int) (this.getHeight() * 0.1);

    int trainHeight = this.getHeight() - paddingY * 2;
    int coachPadding = (int) (trainHeight * 0.05);

    List<TrainCoach> trainCoaches = train.getTrainCoaches();
    int coachHeight = trainHeight / trainCoaches.size();
    int crowdPadding = (int) (coachHeight * 0.05);

    for (TrainCoach trainCoach : trainCoaches) {
      coachPaint.setColor(Color.LTGRAY);
      coachRect.set(
          paddingX,
          paddingY + (trainCoach.getCoachPosition() - 1) * coachHeight + coachPadding / 2,
          this.getWidth() - paddingX,
          paddingY + trainCoach.getCoachPosition() * coachHeight - coachPadding / 2
      );
      canvas.drawRect(coachRect, coachPaint);

      if (trainCoach.getCrowdedValue() <= 0.33) { // train coach is crowded till 33%
        coachPaint.setColor(Color.GREEN);
      } else if (trainCoach.getCrowdedValue() <= 0.67) { // train coach is crowded till 67%
        coachPaint.setColor(Color.YELLOW);
      } else {
        coachPaint.setColor(Color.RED);
      }

      coachRect.set(
          paddingX + crowdPadding,
          paddingY + (trainCoach.getCoachPosition() - 1) * coachHeight + coachPadding / 2
              + crowdPadding,
          this.getWidth() - paddingX - crowdPadding,
          paddingY + trainCoach.getCoachPosition() * coachHeight - coachPadding / 2 - crowdPadding
      );

      canvas.drawRect(coachRect, coachPaint);
    }
  }

  public Train getTrain() {
    return train;
  }

  public void setTrain(Train train) {
    this.train = train;
  }
}
