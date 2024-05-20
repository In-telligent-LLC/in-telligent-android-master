package aje.android.sdk;

import android.util.Log;

@SuppressWarnings("UnusedDeclaration")
public class Logger {
	Logger() {}

	public static void v(String tag, String msg) {
		if (LogLevel.isLoggingEnabled(LogLevel.VERBOSE)) {
			Log.v(tag, msg);
		}
	}

	public static void v(String tag, String msg, Throwable ex) {
		if (LogLevel.isLoggingEnabled(LogLevel.VERBOSE)) {
			Log.v(tag, msg, ex);
		}
	}

	public static void d(String tag, String msg) {
		if (LogLevel.isLoggingEnabled(LogLevel.DEBUG)) {
			Log.d(tag, msg);
		}
	}

	public static void d(String tag, String msg, Throwable ex) {
		if (LogLevel.isLoggingEnabled(LogLevel.DEBUG)) {
			Log.d(tag, msg, ex);
		}
	}

	public static void i(String tag, String msg) {
		if (LogLevel.isLoggingEnabled(LogLevel.INFO)) {
			Log.i(tag, msg);
		}
	}

	public static void i(String tag, String msg, Throwable ex) {
		if (LogLevel.isLoggingEnabled(LogLevel.INFO)) {
			Log.i(tag, msg, ex);
		}
	}

	public static void w(String tag, String msg) {
		if (LogLevel.isLoggingEnabled(LogLevel.WARN)) {
			Log.w(tag, msg);
		}
	}

	public static void w(String tag, String msg, Throwable ex) {
		if (LogLevel.isLoggingEnabled(LogLevel.WARN)) {
			Log.w(tag, msg, ex);
		}
	}

	public static void w(String tag, Throwable ex) {
		if (LogLevel.isLoggingEnabled(LogLevel.WARN)) {
			Log.w(tag, ex);
		}
	}

	public static void e(String tag, String msg) {
		if (LogLevel.isLoggingEnabled(LogLevel.ERROR)) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable ex) {
		if (LogLevel.isLoggingEnabled(LogLevel.ERROR)) {
			Log.e(tag, msg, ex);
		}
	}
}
