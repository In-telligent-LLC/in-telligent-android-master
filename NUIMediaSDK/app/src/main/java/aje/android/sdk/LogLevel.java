package aje.android.sdk;

public enum LogLevel {
	VERBOSE(2),
	DEBUG(3),
	INFO(4),
	WARN(5),
	ERROR(6),
	ASSERT(7),
	OFF(999);

	private int value;

	public static LogLevel currentLevel = LogLevel.VERBOSE;

	private LogLevel(int value) {
		this.value = value;
	}

	public static boolean isLoggingEnabled(LogLevel level) {
		return level.value >= currentLevel.value;
	}
}
