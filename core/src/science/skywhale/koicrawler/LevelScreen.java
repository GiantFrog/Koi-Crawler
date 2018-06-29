package science.skywhale.koicrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LevelScreen implements Screen, InputProcessor
{
	private final KoiCrawler game;
	private Stage stage;
	private Table sidePanel;
	private Label statsLabel;
	private TiledMap map;
	private TiledMapRenderer mapRenderer;
	private boolean mapLeft, mapRight, mapUp, mapDown;
	private float unitScale, elapsedTime;
	//private TextureRegion characterFrame;
	//private TextureAtlas atlas;
	//private Animation<TextureRegion> characterAnimation;
	private Texture texture;
	private Sprite sprite;

	final Vector3 curr, last, delta;

	public LevelScreen (final KoiCrawler game)
	{
		this.game = game;
		unitScale = 1/32f;
		stage = new Stage(new FitViewport(game.width, game.height));
		Gdx.input.setInputProcessor(this);
		map = new TmxMapLoader().load("badMap.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
		mapRenderer.setView(game.camera);
		game.camera.setToOrtho(false, 40, 22.5f);

		statsLabel = new Label("STR: " + game.character.str + "\nITL: " + game.character.itl + "\nDEX: "
				+ game.character.dex + "\nCON: " + game.character.con + "\nRES: " + game.character.res, game.skin);

		sidePanel = new Table();
		sidePanel.add(statsLabel);

		sidePanel.setPosition(7*game.width/8, game.height/2);
		stage.addActor(sidePanel);

		//atlas = new TextureAtlas();
		switch (game.character.gender)
		{
			case (0):
				//characterAnimation = new Animation<TextureRegion>(0.17f, atlas.findRegions("n"), Animation.PlayMode.LOOP);
				texture = new Texture(Gdx.files.internal("animations/n_0.png"));
				break;
			case (1):
				//characterAnimation = new Animation<TextureRegion>(0.17f, atlas.findRegions("animations/m"), Animation.PlayMode.LOOP);
				texture = new Texture(Gdx.files.internal("animations/m_0.png"));
				break;
			case (2):
				//characterAnimation = new Animation<TextureRegion>(0.17f, atlas.findRegions("animations/f"), Animation.PlayMode.LOOP);
				texture = new Texture(Gdx.files.internal("animations/f_0.png"));
		}

		elapsedTime = 0;

		curr = new Vector3();
		last = new Vector3(-1, -1, -1);
		delta = new Vector3();
	}

	@Override
	public void render (float delta)
	{
		elapsedTime += Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (mapUp)
			game.camera.translate(0, game.cameraSpeed*Gdx.graphics.getDeltaTime());
		if (mapDown)
			game.camera.translate(0, -game.cameraSpeed*Gdx.graphics.getDeltaTime());
		if (mapLeft)
			game.camera.translate(-game.cameraSpeed*Gdx.graphics.getDeltaTime(), 0);
		if (mapRight)
			game.camera.translate(game.cameraSpeed*Gdx.graphics.getDeltaTime(), 0);
		game.camera.update();
		mapRenderer.setView(game.camera);
		//SpriteBatch renders based on camera's coordinate system
		game.batch.setProjectionMatrix(game.camera.combined);
		stage.act(Gdx.graphics.getDeltaTime());
		//characterFrame = characterAnimation.getKeyFrame(elapsedTime, true);

		mapRenderer.render();
		stage.draw();
		game.batch.begin();
		//game.batch.draw(characterFrame, 10, 10);
		game.batch.draw(texture, game.width/2, game.height/2);
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

	@Override
	public boolean keyDown (int keycode)
	{
		switch (keycode)
		{
			case (Input.Keys.W):			//move camera around the map
				mapUp = true;
				break;
			case (Input.Keys.A):
				mapLeft = true;
				break;
			case (Input.Keys.S):
				mapDown = true;
				break;
			case (Input.Keys.D):
				mapRight = true;
				break;
			case (Input.Keys.SHIFT_LEFT):	//modify cameraSpeed when shift is pressed
				game.cameraSpeed *= 3;
				break;
			default:
		}
		return false;
	}

	@Override
	public boolean keyUp (int keycode)
	{
		switch (keycode)
		{
			case (Input.Keys.W):
				mapUp = false;
				break;
			case (Input.Keys.A):
				mapLeft = false;
				break;
			case (Input.Keys.S):
				mapDown = false;
				break;
			case (Input.Keys.D):
				mapRight = false;
				break;
			case (Input.Keys.SHIFT_LEFT):
				game.cameraSpeed /= 2;
				break;
			default:
		}
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
		last.set(-1, -1, -1);
		return false;
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer)
	{
		game.camera.unproject(curr.set(screenX, screenY, 0));
		if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
			game.camera.unproject(delta.set(last.x, last.y, 0));
			delta.sub(curr);
			game.camera.position.add(delta.x, delta.y, 0);
		}
		last.set(screenX, screenY, 0);
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
