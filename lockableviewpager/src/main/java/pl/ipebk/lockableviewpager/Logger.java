/*
* author: Bartlomiej Kierys
* date: 2016-09-15
* email: bskierys@gmail.com
*/
package pl.ipebk.lockableviewpager;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Generic class to simplify using android log. Credits goes to Timber
 */
class Logger {

    private static final String TAG = LockableViewPager.class.getSimpleName();

    static void d(String message, Object... args) {
        prepareLog(Log.DEBUG, null, message, args);
    }

    static void e(Throwable t, String message, Object... args) {
        prepareLog(Log.ERROR, t, message, args);
    }

    private static void prepareLog(int priority, Throwable t, String message, Object... args) {
        if (message != null && message.length() == 0) {
            message = null;
        }
        if (message == null) {
            if (t == null) {
                return; // Swallow message if it's null and there's no throwable.
            }
            message = getStackTraceString(t);
        } else {
            if (args.length > 0) {
                message = String.format(message, args);
            }
            if (t != null) {
                message += "\n" + getStackTraceString(t);
            }
        }

        Log.println(priority, TAG, message);
    }

    private static String getStackTraceString(Throwable t) {
        StringWriter sw = new StringWriter(256);
        PrintWriter pw = new PrintWriter(sw, false);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
