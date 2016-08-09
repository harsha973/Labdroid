package sha.com.ind.labapp;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;

import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.allOf;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import sha.com.ind.labapp.home.HomeActivity;

/**
 * Created by sreepolavarapu on 13/07/16.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void iDontKnowWhatToTest()
    {

        onData(allOf(is(instanceOf(String.class)), is("Media")))
                .inAdapterView(withId(android.R.id.list))
                .perform(click());
    }

    @Test
    public void moveToJunkFragment()
    {
        //  Move to Junk Activity
        onData(allOf(is(instanceOf(String.class)), is("Junk Activity (Test purpose)")))
                .inAdapterView(withId(android.R.id.list))
                .perform(click());

        //  Move to Junk fragment
        onData(allOf(is(instanceOf(String.class)), is("Junk Frag")))
                .inAdapterView(withId(android.R.id.list))
                .perform(click());

        //  Idle for 3 seconds
        IdlingPolicies.setIdlingResourceTimeout(3, TimeUnit.SECONDS);

        //  Click on Junk checkbox
        onView(withId(R.id.junk_checkbox))
                .perform(click());
    }
}
