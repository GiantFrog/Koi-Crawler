package science.skywhale.koicrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LevelScreen implements Screen
{
	private final KoiCrawler game;
	private Stage stage;
	private Table sidePanel;
	private Label statsLabel;
	private TiledMap map;
	private TiledMapRenderer mapRenderer;

	public LevelScreen (final KoiCrawler game)
	{
		this.game = game;
		stage = new Stage(new FitViewport(game.width, game.height));
		Gdx.input.setInputProcessor(stage);
		map = new TmxMapLoader().load("badMap.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		mapRenderer.setView(game.camera);

		statsLabel = new Label("STR: " + game.character.str + "\nITL: " + game.character.itl + "\nDEX: "
				+ game.character.dex + "\nCON: " + game.character.con + "\nRES: " + game.character.res, game.skin);

		sidePanel = new Table();
		sidePanel.add(statsLabel);

		sidePanel.setPosition(7*game.width/8, game.height/2);
		stage.addActor(sidePanel);
	}

	@Override
	public void render (float delta)
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.camera.update();
		//SpriteBatch renders based on camera's coordinate system
		game.batch.setProjectionMatrix(game.camera.combined);
		stage.act(Gdx.graphics.getDeltaTime());

		game.batch.begin();
		mapRenderer.render();
		stage.draw();
		game.batch.end();
	}

	@Override
	public void resize (int width, int height)
	{
		stage.getViewport().update(width, height, true);
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
}
