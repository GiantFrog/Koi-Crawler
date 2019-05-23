package science.skywhale.koicrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
	private MapProperties mapProperties;
	private boolean mapLeft, mapRight, mapUp, mapDown;
	private float unitScale, elapsedTime;
	private TextureRegion characterFrame;
	private Villager selected;

	final Vector3 curr, lastTouched, delta;

	public LevelScreen (final KoiCrawler game)
	{
		this.game = game;
		unitScale = 1/32f;
		stage = new Stage(new FitViewport(game.width, game.height));
		Gdx.input.setInputProcessor(this);
		map = new TmxMapLoader().load("badMap.tmx");
		mapProperties = map.getProperties();
		mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
		mapRenderer.setView(game.camera);
		game.camera.setToOrtho(false, 40, 22.5f);

		statsLabel = new Label("STR: " + game.character.getStr() + "\nITL: " + game.character.getItl() + "\nDEX: "
				+ game.character.getDex() + "\nCON: " + game.character.getCon() + "\nRES: " + game.character.getRes(), game.skin);

		sidePanel = new Table();
		sidePanel.add(statsLabel);

		sidePanel.setPosition(game.width*7/8, game.height*3/4);
		stage.addActor(sidePanel);

		elapsedTime = 0;

		curr = new Vector3();
		lastTouched = new Vector3(-1, -1, -1);
		delta = new Vector3();
		
		game.character.moveTo(1, 1);
	}

	@Override
	public void render (float delta)
	{
		elapsedTime += Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//move the camera around when the flag to is set!
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
		
		characterFrame = game.character.getAnimation().getKeyFrame(elapsedTime, true);

		mapRenderer.render();
		stage.draw();
		game.batch.begin();
		game.batch.draw(characterFrame, game.character.getX(), game.character.getY(), 1, 1);
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
				game.cameraSpeed /= 3;
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
		//if (button == Buttons.RIGHT) //this gets right clicks
		
		//make a vector and translate its coordinates from screen pixels into map tiles
		Vector3 touchVector = new Vector3(screenX, screenY, 0);
		game.camera.unproject(touchVector);
		
		//if we tap the character, select them!
		if ((int)touchVector.x == game.character.getX() && (int)touchVector.y == game.character.getY())
			selected = game.character;
		//if we tap nothing, but do it on the map and something is selected, move that something there!
		else if (selected != null && inBounds(touchVector))
		{
			selected.moveTo((int) touchVector.x, (int) touchVector.y);
			selected = null;
		}
		
		
		return false;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button)
	{
		lastTouched.set(-1, -1, -1);
		return false;
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer)
	{
		game.camera.unproject(curr.set(screenX, screenY, 0));
		if (!(lastTouched.x == -1 && lastTouched.y == -1 && lastTouched.z == -1)) {
			game.camera.unproject(delta.set(lastTouched.x, lastTouched.y, 0));
			delta.sub(curr);
			game.camera.position.add(delta.x, delta.y, 0);
		}
		lastTouched.set(screenX, screenY, 0);
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
	
	private boolean inBounds (Vector3 vector)
	{
		return vector.x >= 0 && vector.y >= 0 && vector.x < mapProperties.get("width", Integer.class)
				&& vector.y < mapProperties.get("height", Integer.class);
	}
}
