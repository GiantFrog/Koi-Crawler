package science.skywhale.koicrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapProperties;
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
	OrthographicCamera camera;
	Villager character;
	private MouseKeyboardInput mouseKeyboardInput;
	private TouchInput touchInput;
	private InputMultiplexer inputMultiplexer;
	double leftToZoom;
	private Stage stage;
	private Table sidePanel;
	private Label statsLabel;
	private TiledMap map;
	private TiledMapRenderer mapRenderer;
	private boolean mapLeft, mapRight, mapUp, mapDown;
	private float unitScale, elapsedTime;
	private TextureRegion characterFrame;
	private Villager selected;
	

	public LevelScreen (final KoiCrawler game)
	{
		this.game = game;
		unitScale = 1/32f;
		camera = game.camera;
		character = game.character;
		stage = new Stage(new FitViewport(game.width, game.height));
		
		mouseKeyboardInput = new MouseKeyboardInput(this);
		touchInput = new TouchInput(this);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(new GestureDetector(touchInput));
		inputMultiplexer.addProcessor(mouseKeyboardInput);
		Gdx.input.setInputProcessor(inputMultiplexer);
		leftToZoom = 0;
		
		map = new TmxMapLoader().load("badMap.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
		mapRenderer.setView(camera);
		camera.setToOrtho(false, 40, 22.5f);

		statsLabel = new Label("STR: " + game.character.getStr() + "\nITL: " + game.character.getItl() + "\nDEX: "
				+ game.character.getDex() + "\nCON: " + game.character.getCon() + "\nRES: " + game.character.getRes(), game.skin);

		sidePanel = new Table();
		sidePanel.add(statsLabel);

		sidePanel.setPosition(game.width*7/8, game.height*3/4);
		stage.addActor(sidePanel);

		elapsedTime = 0;
		
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
			camera.translate(0, game.cameraSpeed*Gdx.graphics.getDeltaTime());
		if (mapDown)
			camera.translate(0, -game.cameraSpeed*Gdx.graphics.getDeltaTime());
		if (mapLeft)
			camera.translate(-game.cameraSpeed*Gdx.graphics.getDeltaTime(), 0);
		if (mapRight)
			camera.translate(game.cameraSpeed*Gdx.graphics.getDeltaTime(), 0);
		if (leftToZoom <= -.005 || leftToZoom >= .005)
		{
			//zoom the camera by the amount we need to multiplied by the time passed and the zoom speed, both are <1
			camera.zoom += game.zoomSpeed * leftToZoom * Gdx.graphics.getDeltaTime();
			leftToZoom -= game.zoomSpeed * leftToZoom * Gdx.graphics.getDeltaTime();
		}
		camera.update();
		mapRenderer.setView(camera);
		
		//SpriteBatch renders based on camera's coordinate system
		game.batch.setProjectionMatrix(camera.combined);
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
	
	public MapProperties getMapProperties()
	{
		return map.getProperties();
	}
	
	public void setMapLeft (boolean mapLeft)
	{
		this.mapLeft = mapLeft;
	}
	
	public void setMapRight (boolean mapRight)
	{
		this.mapRight = mapRight;
	}
	
	public void setMapUp (boolean mapUp)
	{
		this.mapUp = mapUp;
	}
	
	public void setMapDown (boolean mapDown)
	{
		this.mapDown = mapDown;
	}
	
	public void makeCameraSpeedy (boolean speedy)
	{
		if (speedy)
			game.cameraSpeed *= 3;
		else
			game.cameraSpeed /= 3;
	}
	
	public void setSelected (Villager selected)
	{
		this.selected = selected;
	}
	
	//moves whatever is selected, if anything, to the given coordinates.
	public void tryMove (int x, int y)
	{
		if (selected != null)
		{
			selected.moveTo(x, y);
			selected = null;
		}
	}
}
