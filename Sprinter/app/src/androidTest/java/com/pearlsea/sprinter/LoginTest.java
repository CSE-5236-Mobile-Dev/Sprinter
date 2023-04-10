package com.pearlsea.sprinter;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
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
public class LoginTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void loginTest() {
        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.welcomeLoginButton),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                1),
                        isDisplayed()));
        frameLayout.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.emailAddressTextBox),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("a"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.emailAddressTextBox), withText("a"),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.passwordTextBox),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("a"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.passwordTextBox), withText("a"),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());

        ViewInteraction textView = onView(
                allOf(withId(R.id.txtHelloWelcome), withText("Login to Track your Runs"),
                        withParent(allOf(withId(R.id.linearLoginPage),
                                withParent(withId(R.id.loginFragmentContainer)))),
                        isDisplayed()));
        textView.check(matches(withText("Login to Track your Runs")));

        ViewInteraction view = onView(
                allOf(withId(R.id.viewEllipseOne),
                        withParent(allOf(withId(R.id.loginButton),
                                withParent(withId(R.id.linearLoginPage)))),
                        isDisplayed()));
        view.check(matches(isDisplayed()));
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
