package johnfatso.seriestimer;

/**
 * Created by janakiraman on 08-10-2017.
 */

class StatusControl {

    /**
     * the following items should be used to track the current screen
     */
    static final int SCREEN_LAUNCHER=1;
    static final int SCREEN_SETTIMER=2;
    static final int SCREEN_TIMER=3;
    static final int SCREEN_NONEXISTANT=0;

    /**
     * the following item should be used to track the current status of the screen
     * based upon the status of the screen only the UI should be updated
     */
    static final int STATUS_CREATED=1;
    static final int STATUS_STARTED=2;
    static final int STATUS_RESUMED=3;
    static final int STATUS_PAUSED=4;
    static final int STATUS_STOPPED=5;
    static final int STATUS_NONEXISTANT=0;

    /**
     * the following items are used for tracking weather the app is running or not
     */
    static final int APP_EXISTS=1;
    static final int APP_NONEXISANT=0;

    /**
     * the following are to be exposed to the app, so that the current status could be followed and tracked
     */
    static int isAppActive=APP_NONEXISANT;
    static int currentScreen=SCREEN_NONEXISTANT;
    static int statusOfTheCurrentScreen=STATUS_NONEXISTANT;

}
