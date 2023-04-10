package com.pearlsea.sprinter;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FragmentTransitions {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void fragmentTransitions() {
        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.welcomeSignupButton),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                2),
                        isDisplayed()));
        frameLayout.perform(click());

        ViewInteraction frameLayout2 = onView(
                allOf(withId(R.id.signupBackToWelcome),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                9),
                        isDisplayed()));
        frameLayout2.perform(click());

        ViewInteraction frameLayout3 = onView(
                allOf(withId(R.id.welcomeLoginButton),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                1),
                        isDisplayed()));
        frameLayout3.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.txtHelloWelcome), withText("Login to Track your Runs"),
                        withParent(allOf(withId(R.id.linearLoginPage),
                                withParent(withId(R.id.loginFragmentContainer)))),
                        isDisplayed()));
        textView.check(matches(withText("Login to Track your Runs")));

        ViewInteraction frameLayout4 = onView(
                allOf(withId(R.id.loginBackToWelcome),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                7),
                        isDisplayed()));
        frameLayout4.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.txtHelloWelcome), withText("Hello! Welcome to Sprinter."),
                        withParent(allOf(withId(R.id.linearLoginPage),
                                withParent(withId(R.id.loginFragmentContainer)))),
                        isDisplayed()));
        textView2.check(matches(withText("Hello! Welcome to Sprinter.")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
