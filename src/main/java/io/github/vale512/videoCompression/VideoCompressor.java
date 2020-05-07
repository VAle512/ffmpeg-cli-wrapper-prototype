package io.github.vale512.videoCompression;

import static io.github.vale512.videoCompression.FFProbeFactory.getInstance;
import static io.github.vale512.videoCompression.config.Configurations.FFMPEG_PATH;

import java.io.IOException;
import java.nio.file.Path;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

public class VideoCompressor {

	private static final String MP4 = "mp4";
	private static final int SAMPLE_RATE = 48_000; // at 48KHz
	private static final String AUDIO_CODEC = "aac"; 
	private static final String VIDEO_CODEC = "libx264"; 
	private static final int FRAME_RATE = 24;

	private FFmpeg ffmpeg; 
	private final FFprobe ffprobe = getInstance(); 
	private Path output;
	private FFmpegProbeResult probeResult;

	public VideoCompressor(Path input, Path output) {
		try {
			this.probeResult = this.ffprobe.probe(input.toString());
		} catch (IOException e) {
			System.err.println("ffprobe can not gather informations from " + input.toString() + ".\n"
					+ "Is it correctly installed");
			e.printStackTrace();
		}
		this.output = output;
		try {
			this.ffmpeg = new FFmpeg(FFMPEG_PATH);
		} catch (IOException e) {
			System.err.println("Cannot fynd FFmpeg at " + FFMPEG_PATH);
			e.printStackTrace();
		}
	}

	public void compress(int width, int height) {
		FFmpegBuilder builder = new FFmpegBuilder()
				.setInput(this.probeResult)    
				.overrideOutputFiles(true)
				.addOutput(this.output.toString()) 
				.setFormat(MP4)
				.setAudioCodec(AUDIO_CODEC)
				.setAudioRate(SAMPLE_RATE) 
				.setVideoCodec(VIDEO_CODEC) 
				.setVideoFramerate(FRAME_RATE)
				.setVideoResolution(width, height)
				.setStrict(FFmpegBuilder.Strict.VERY)
				.done();
		FFmpegExecutor executor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
		executor.createJob(builder).run();
	}

}
