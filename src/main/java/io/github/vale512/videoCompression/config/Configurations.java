package io.github.vale512.videoCompression.config;

import java.io.IOException;
import java.util.Properties;

public class Configurations {

	private static final String CONFIG_FILE = "config.properties";
	private static Properties prop = getProperties();
	public static final String FFPROBE_PATH = prop.getProperty("ffprobe.path");
	public static final String FFMPEG_PATH = prop.getProperty("ffmpeg.path");

	private static Properties getProperties() {
		Properties prop = new Properties();
		try {
			prop.load(Configurations.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
		} catch (IOException e) {
			System.err.println("Error while loading configurations file at " + CONFIG_FILE);
			e.printStackTrace();
		}
		return prop;
	}
}
