package science.skywhale.koicrawler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class KoiCrawler extends Game
{
	SpriteBatch batch;
	FreeTypeFontGenerator fontGen;
	FreeTypeFontGenerator.FreeTypeFontParameter fontParam;
	BitmapFont bigFont, littleFont;
	Skin skin;
	OrthographicCamera camera;
	int width, height, cameraSpeed, zoomSpeed;
	Villager character;
	TextureAtlas atlas;
	
	@Override
	public void create()
	{
		atlas = new TextureAtlas(Gdx.files.internal("texturePack.atlas"));
		skin = new Skin(Gdx.files.internal("skin/skin.json"));
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		width = 1280;
		height = 720;
		cameraSpeed = 18;
		zoomSpeed = 10;

		fontGen = new FreeTypeFontGenerator(Gdx.files.internal("INFROMAN.TTF"));
		fontParam = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParam.shadowOffsetX = 3;
		fontParam.shadowOffsetY = 3;
		fontParam.size = 28;
		bigFont = fontGen.generateFont(fontParam);
		fontParam.size = 12;
		littleFont = fontGen.generateFont(fontParam);

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render()
	{
		super.render();
	}
	
	@Override
	public void dispose()
	{
		batch.dispose();
		this.getScreen().dispose();
	}
}
