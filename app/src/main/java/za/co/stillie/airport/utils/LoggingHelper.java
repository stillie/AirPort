package za.co.stillie.airport.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import timber.log.Timber;
import za.co.stillie.airport.BuildConfig;

public class LoggingHelper {

    private static final int VERBOSE = Log.VERBOSE;
    private static final int DEBUG = Log.DEBUG;
    private static final int INFO = Log.INFO;
    private static final int WARN = Log.WARN;
    private static final int ERROR = Log.ERROR;
    private static final int WTF = Log.ASSERT;

    private static String TAG = BuildConfig.APPLICATION_ID;

    protected LoggingHelper() {
        // Empty
    }

    private static void log(final int level, final String msg, final Throwable throwable) {
        final StackTraceElement[] elements = new Throwable().getStackTrace();
        String callerClassName = "?";
        String callerMethodName = "?";
        String callerLineNumber = "?";
        if (elements.length >= 4) {
            callerClassName = elements[2].getClassName();
            callerClassName = callerClassName.substring(callerClassName.lastIndexOf('.') + 1);
            if (callerClassName.indexOf("$") > 0) {
                callerClassName = callerClassName.substring(0, callerClassName.indexOf("$"));
            }
            callerMethodName = elements[2].getMethodName();
            callerMethodName = callerMethodName.substring(callerMethodName.lastIndexOf('_') + 1);
            if (callerMethodName.equals("<init>")) {
                callerMethodName = callerClassName;
            }
            callerLineNumber = String.valueOf(elements[2].getLineNumber());
        }

        final String stack = "[" + callerClassName + "." + callerMethodName + "():" + callerLineNumber + "]" + (TextUtils.isEmpty(msg) ? "" : " ");
        switch (level) {
            case VERBOSE:
                if (BuildConfig.DEBUG)
                    Timber.tag(TAG).v(throwable, "%s: %s", stack, msg);
                break;
            case DEBUG:
                if (BuildConfig.DEBUG)
                    Timber.d(throwable, "%s: %s", stack, msg);
                break;
            case INFO:
                // Never hide Info logs
                Timber.i(throwable, "%s: %s", stack, msg);
                break;
            case WARN:
                if (BuildConfig.DEBUG)
                    Timber.tag(TAG).w(throwable, "%s: %s", stack, msg);
                break;
            case ERROR:
                if (BuildConfig.DEBUG)
                    Timber.e(throwable, "%s: %s", stack, msg);
                break;
            case WTF:
                if (BuildConfig.DEBUG)
                    Timber.tag(TAG).wtf(throwable, "%s: %s", stack, msg);
                break;
            default:
                break;
        }
    }

    public static void debug(@NonNull final String msg) {
        log(DEBUG, msg, null);
    }

    public static void debug(final String msg, @NonNull final Throwable throwable) {
        log(DEBUG, msg, throwable);
    }

    public static void verbose(@NonNull final String msg) {
        log(VERBOSE, msg, null);
    }

    public static void verbose(final String msg, @NonNull final Throwable throwable) {
        log(VERBOSE, msg, throwable);
    }

    public static void information(@NonNull final String msg) {
        log(INFO, msg, null);
    }

    public static void information(final String msg, @NonNull final Throwable throwable) {
        log(INFO, msg, throwable);
    }

    public static void warning(@NonNull final String msg) {
        log(WARN, msg, null);
    }

    public static void warning(final String msg, @NonNull final Throwable throwable) {
        log(WARN, msg, throwable);
    }

    public static void error(@NonNull final String msg) {
        log(ERROR, msg, null);
    }

    public static void error(final String msg, @NonNull final Throwable throwable) {
        log(ERROR, msg, throwable);
    }

    public static void wtf(@NonNull final String msg) {
        log(WTF, msg, null);
    }

    public static void wtf(final String msg, @NonNull final Throwable throwable) {
        log(WTF, msg, throwable);
    }

    @Deprecated
    public static void wtf(@NonNull final Throwable throwable) {
        log(WTF, null, throwable);
    }
}