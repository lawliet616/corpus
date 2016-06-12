package hu.bugbusters.corpus.core.util;

public class StringUtils {
	private static final int PREVIEW_MAX_LENGTH = 100;

	public static String createPreviewFromMessage(String message) {
		if (message.length() < PREVIEW_MAX_LENGTH) {
			return message;
		} else {
			String preview = message.substring(0, PREVIEW_MAX_LENGTH);

			while (preview.endsWith(" ")) {
				preview = preview.substring(0, preview.length() - 1);
			}

			preview = preview + "...";

			return preview;
		}
	}
}
