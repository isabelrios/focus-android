/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.focus.activity.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.uiautomator.UiSelector
import junit.framework.TestCase.assertFalse
import org.junit.Assert.assertTrue
import org.mozilla.focus.R
import org.mozilla.focus.helpers.TestHelper.getStringResource
import org.mozilla.focus.helpers.TestHelper.mDevice
import org.mozilla.focus.helpers.TestHelper.packageName
import org.mozilla.focus.helpers.TestHelper.waitingTime

class HomeScreenRobot {

    fun verifyEmptySearchBar() {
        editURLBar.waitForExists(waitingTime)
        assertTrue(editURLBar.text.equals(getStringResource(R.string.urlbar_hint)))
    }

    fun skipFirstRun() = onView(withId(R.id.skip)).perform(click())

    fun verifyOnboardingFirstSlide() {
        firstSlide.check(matches(isDisplayed()))
    }

    fun verifyOnboardingSecondSlide() {
        secondSlide.check(matches(isDisplayed()))
    }

    fun verifyOnboardingThirdSlide() {
        thirdSlide.check(matches(isDisplayed()))
    }

    fun verifyOnboardingLastSlide() {
        lastSlide.check(matches(isDisplayed()))
    }

    fun clickOnboardingNextBtn() = nextBtn.click()

    fun clickOnboardingFinishBtn() = finishBtn.click()

    fun verifyHomeScreenTipIsDisplayed(isDisplayed: Boolean) {
        if (isDisplayed) {
            assertTrue(homeScreenTips.waitForExists(waitingTime))
        } else {
            assertFalse(homeScreenTips.waitForExists(waitingTime))
        }
    }

    fun scrollLeftTipsCarousel() {
        assertTrue(homeScreenTips.isScrollable)
        homeScreenTips.swipeLeft(4)
    }

    class Transition {
        fun openMainMenu(interact: ThreeDotMainMenuRobot.() -> Unit): ThreeDotMainMenuRobot.Transition {
            editURLBar.waitForExists(waitingTime)
            mainMenu
                .check(matches(isDisplayed()))
                .perform(click())

            ThreeDotMainMenuRobot().interact()
            return ThreeDotMainMenuRobot.Transition()
        }
    }
}

fun homeScreen(interact: HomeScreenRobot.() -> Unit): HomeScreenRobot.Transition {
    HomeScreenRobot().interact()
    return HomeScreenRobot.Transition()
}

private val editURLBar =
    mDevice.findObject(
        UiSelector().resourceId("$packageName:id/mozac_browser_toolbar_edit_url_view")
    )

private val mainMenu = onView(withId(R.id.menuView))

/********* First Run Locators  */
private val firstSlide = onView(withText(R.string.firstrun_defaultbrowser_title))

private val secondSlide = onView(withText(R.string.firstrun_search_title))

private val thirdSlide = onView(withText(R.string.firstrun_shortcut_title))

private val lastSlide = onView(withText(R.string.firstrun_privacy_title))

private val nextBtn = mDevice.findObject(
    UiSelector()
        .resourceId("$packageName:id/next")
        .enabled(true)
)

private val finishBtn = mDevice.findObject(
    UiSelector()
        .resourceId("$packageName:id/finish")
        .enabled(true)
)

private val homeScreenTips =
    mDevice.findObject(UiSelector().resourceId("$packageName:id/home_tips"))
