package com.C4S.kaku_swing;

/***
 * LogLevels is an enum file that is used along-side the LogWriter class to allow signifying the level of a log in the log file
 */
public enum logLevels {
    /***
     * INFO - used to signal that nothing is wrong and gives information to the log file on what's happening during runtime
     */
    INFO,
    /***
     * WARNING - used to signal that something occurred that isn't devastating but could be the source of a FATAL or SEVERE issue later
     */
    WARNING,
    /***
     * SEVERE - used to signal that something is wrong and the program may not run properly because of this
     */
    SEVERE,
    /***
     * FATAL - used to signal a possible reason that the program crashed or could be crashing soon
     */
    FATAL,
    /***
     * THROWING - used to signal a thrown exception, should be used mainly in catch blocks
     */
    THROWING
}
