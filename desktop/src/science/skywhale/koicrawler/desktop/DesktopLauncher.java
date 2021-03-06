package science.skywhale.koicrawler.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import science.skywhale.koicrawler.HexTest;
import science.skywhale.koicrawler.KoiCrawler;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Koi Crawler!";
		config.width = 1280;
		config.height = 720;
		config.foregroundFPS = config.backgroundFPS = 120;
		//config.addIcon("bucket.png", Files.FileType.Internal);
		//config.addIcon("bucketSmall.png", Files.FileType.Internal);
		//config.addIcon("bucketTiny.png", Files.FileType.Internal);
		
		TexturePacker.Settings packerSettings = new TexturePacker.Settings();
		packerSettings.maxHeight = packerSettings.maxWidth = 512;
		TexturePacker.process(packerSettings, "animations", "./", "texturePack");
		
		new LwjglApplication(new KoiCrawler(), config);
	}
}
