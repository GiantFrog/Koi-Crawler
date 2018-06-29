package science.skywhale.koicrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class MainMenuScreen implements Screen, InputProcessor
{
	private final KoiCrawler game;
	private Texture background;
	private GlyphLayout glyphLayout;
	private Animation<Texture> animation;

	public MainMenuScreen (final KoiCrawler game)
	{
		this.game = game;

		background = new Texture("koi.jpg");
		glyphLayout = new GlyphLayout();
		glyphLayout.setText(game.bigFont, "KOI CRAWLER");

		game.camera.setToOrtho(false, game.width, game.height);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render (float delta)
	{
		game.camera.update();
		//SpriteBatch renders based on camera's coordinate system
		game.batch.setProjectionMatrix(game.camera.combined);

		game.batch.begin();
		game.batch.draw(background, 0, 0, game.width, game.height);
		game.bigFont.draw(game.batch, glyphLayout, (game.width - glyphLayout.width)/2, 50);
		game.batch.end();
	}

	@Override
	public void resize (int width, int height)
	{

	}
	@Override
	public void show()
	{

	}
	@Override
	public void hide()
	{

	}
	@Override
	public void pause()
	{

	}
	@Override
	public void resume()
	{

	}

	@Override
	public void dispose()
	{

	}

	@Override
	public boolean keyDown (int keycode)
	{
		return false;
	}

	@Override
	public boolean keyUp (int keycode)
	{
		return false;
	}

	@Override
	public boolean keyTyped (char character)
	{
		return false;
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button)
	{
		game.setScreen(new CharacterCreationScreen(game));
		dispose();
		return false;
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer)
	{
		return false;
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled (int amount)
	{
		return false;
	}
}
