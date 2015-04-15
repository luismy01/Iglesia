/*

Copyright (c) 2015 ophilbert
https://github.com/ophilbert/ActivityTransition

He develop the base code

Copyright (c) 2015 luismy01
https://github.com/luismy01

He modify th class to apply the singleton pattern

*/

package ophilbert.animation;

import android.app.Activity;
import co.lmejia.iglesia.R;

public class ActivityAnimator {

    private static ActivityAnimator animator;

    private ActivityAnimator() {}

    public static ActivityAnimator getInstance() {

        if (animator == null) animator = new ActivityAnimator();

        return animator;
    }

    public void flipHorizontalAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.flip_horizontal_in, R.anim.flip_horizontal_out);
    }

    public void flipVerticalAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.flip_vertical_in, R.anim.flip_vertical_out);
    }

    public void fadeAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void disappearTopLeftAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.disappear_top_left_in, R.anim.disappear_top_left_out);
    }

    public void appearTopLeftAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.appear_top_left_in, R.anim.appear_top_left_out);
    }

    public void disappearBottomRightAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.disappear_bottom_right_in, R.anim.disappear_bottom_right_out);
    }

    public void appearBottomRightAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.appear_bottom_right_in, R.anim.appear_bottom_right_out);
    }

    public void unzoomAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.unzoom_in, R.anim.unzoom_out);
    }

    public void PullRightPushLeft(Activity a)
    {
        a.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void PullLeftPushRight(Activity a)
    {
        a.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}