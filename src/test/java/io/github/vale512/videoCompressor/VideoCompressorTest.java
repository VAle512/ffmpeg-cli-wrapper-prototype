package io.github.vale512.videoCompressor;

import static io.github.vale512.videoCompression.FFProbeFactory.getInstance;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import io.github.vale512.videoCompression.VideoCompressor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegStream;

@RunWith(Parameterized.class)
public class VideoCompressorTest {

	private FFprobe ffprobe = getInstance();
	private Path input;
	private Path output;
	private VideoCompressor vc;

	@Parameters(name="Compression test: {0}x{1}")
	public static List<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{
				640
				,
				480
				,
				1439239
			}
			,
			{
				800
				,
				600
				,
				1808800
			}
		});
	}

	@Parameter
	public int width;

	@Parameter(1)
	public int height;
	
	@Parameter(2)
	public int size;


	@Before
	public void setUp() throws Exception {
		this.input= Paths.get("src/test/resources/samples/SampleVideo_1280x720_2mb.mp4");
		this.output = Paths.get(createTempFolder(null).toString(), "output.mp4");
		this.vc = new VideoCompressor(this.input, this.output);
	}

	@After
	public void tearDown() throws Exception {
		this.output.toFile().delete();
	}

	@Test
	public void testCompressedResolution() throws IOException {
		this.vc.compress(this.width,this.height);
		FFmpegStream stream = this.ffprobe.probe(this.output.toString()).getStreams().get(0);
		/*Testing if the resolution is correct*/
		assertEquals(this.width, stream.width);
		assertEquals(this.height, stream.height);
		/*Testing if the size is correct*/
		assertEquals(this.size, this.output.toFile().length());
	}
	
	private static Path createTempFolder(String prefix) throws IOException {
		File temp = Files.createTempDirectory(prefix).toFile();
		temp.deleteOnExit();
		return temp.toPath();
	}
	
}
