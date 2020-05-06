package io.github.vale512.videoCompression;

import static io.github.vale512.videoCompression.config.Configurations.FFPROBE_PATH;

import net.bramp.ffmpeg.FFprobe; 

public class FFProbeFactory {
	
	private static FFprobe INSTANCE = null;
	
	public static FFprobe getInstance() {
		if(INSTANCE == null)
			INSTANCE = new FFprobe(FFPROBE_PATH);
		return INSTANCE;
	}

}
