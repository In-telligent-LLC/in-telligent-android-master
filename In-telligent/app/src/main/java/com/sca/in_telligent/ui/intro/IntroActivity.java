package com.sca.in_telligent.ui.intro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.ui.auth.login.LoginActivity;
import com.sca.in_telligent.ui.auth.register.SignupDemographicsActivity;
import com.sca.in_telligent.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.Nullable;

public class IntroActivity extends BaseActivity implements IntroMvpView {

  @Inject
  IntroMvpPresenter<IntroMvpView> mPresenter;

  @BindView(R.id.first_time_user_btn)
  TextView firstTimeUserButton = null;

  @BindView(R.id.returning_subscriber_btn)
  TextView returningSubscriberButton;

  @BindView(R.id.imageAnimated)
  ImageView imageAnimated = null;


  int[] images = {R.drawable.slideshow_image_01, R.drawable.slideshow_image_02,
      R.drawable.slideshow_image_03, R.drawable.slideshow_image_04, R.drawable.slideshow_image_05,
      R.drawable.slideshow_image_06};

  public static Intent getStartIntent(Context context) {
    Intent intent = new Intent(context, IntroActivity.class);
    return intent;
  }

  @Nullable
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_intro);
    getActivityComponent().inject(this);

    imageAnimated = findViewById(R.id.imageAnimated);
    firstTimeUserButton = findViewById(R.id.first_time_user_btn);
    firstTimeUserButton.setOnClickListener(this::firstTimeClick);
    returningSubscriberButton = findViewById(R.id.returning_subscriber_btn);
    returningSubscriberButton.setOnClickListener(view -> returningSubscriberClick());

    returningSubscriberClick();


    ButterKnife.bind(this);

    mPresenter.onAttach(IntroActivity.this);
    animate(imageAnimated, images, 0);

  }


  @OnClick(R.id.first_time_user_btn)
  void firstTimeClick(View v) {
    startActivityWithDeeplink(SignupDemographicsActivity.getStartIntent(this));
  }

  @OnClick(R.id.returning_subscriber_btn)
  void returningSubscriberClick() {
    startActivityWithDeeplink(LoginActivity.getStartIntent(this));
  }

  @Override
  protected void setUp() {

  }

  @Override
  public void onDestroy() {
    mPresenter.onDetach();
    super.onDestroy();
  }

  private void animate(final ImageView imageView, final int[] images, final int imageIndex) {
    int fadeInDuration = 1000;
    int timeBetween = 1000;
    int fadeOutDuration = 1000;

    imageView.setVisibility(
        View.INVISIBLE);
    imageView.setImageResource(images[imageIndex]);

    Animation fadeIn = new AlphaAnimation(0, 1);
    fadeIn.setInterpolator(new DecelerateInterpolator());
    fadeIn.setDuration(fadeInDuration);

    Animation fadeOut = new AlphaAnimation(1, 0);
    fadeOut.setInterpolator(new AccelerateInterpolator());
    fadeOut.setStartOffset(fadeInDuration + timeBetween);
    fadeOut.setDuration(fadeOutDuration);

    AnimationSet animation = new AnimationSet(false);
    animation.addAnimation(fadeIn);
    animation.addAnimation(fadeOut);
    animation.setRepeatCount(1);
    imageView.setAnimation(animation);

    animation.setAnimationListener(new AnimationListener() {
      public void onAnimationEnd(Animation animation) {
        if (images.length - 1 > imageIndex) {
          animate(imageView, images,
              imageIndex + 1);
        } else {
          animate(imageView, images,
              0);
        }
      }

      public void onAnimationRepeat(Animation animation) {
      }

      public void onAnimationStart(Animation animation) {
      }
    });
  }

}
