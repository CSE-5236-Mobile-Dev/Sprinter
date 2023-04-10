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
public class SignupTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void signupTest() {
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

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.nameTextBox),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("b"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.nameTextBox), withText("b"),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.emailAddressTextBox),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("b"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.emailAddressTextBox), withText("b"),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatEditText4.perform(pressImeActionButton());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.passwordTextBox),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                7),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("b"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.passwordTextBox), withText("b"),
                        childAtPosition(
                                allOf(withId(R.id.linearLoginPage),
                                        childAtPosition(
                                                withId(R.id.loginFragmentContainer),
                                                0)),
                                7),
                        isDisplayed()));
        appCompatEditText6.perform(pressImeActionButton());

        ViewInteraction editText = onView(
                allOf(withId(R.id.nameTextBox), withText("b"),
                        withParent(allOf(withId(R.id.linearLoginPage),
                                withParent(withId(R.id.loginFragmentContainer)))),
                        isDisplayed()));
        editText.check(matches(withText("b")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.emailAddressTextBox), withText("b"),
                        withParent(allOf(withId(R.id.linearLoginPage),
                                withParent(withId(R.id.loginFragmentContainer)))),
                        isDisplayed()));
        editText2.check(matches(withText("b")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.passwordTextBox), withText("•"),
                        withParent(allOf(withId(R.id.linearLoginPage),
                                withParent(withId(R.id.loginFragmentContainer)))),
                        isDisplayed()));
        editText3.check(matches(withText("•")));
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
