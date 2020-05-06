package io.github.vale512.videoCompression.config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Configurations {
	private static final String BUNDLE_NAME = "io.github.vale512.videoCompressor.config.configurations";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	public static final String FFPROBE_PATH = getString("FFProbePath");
	public static final String FFMPEG_PATH = getString("FFMpegPath");

	private static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
