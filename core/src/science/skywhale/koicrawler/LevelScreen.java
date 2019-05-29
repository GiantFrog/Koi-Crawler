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
import com.badlogic.gdx.math.Vector3;
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
	double leftToZoom, leftToMoveX, leftToMoveY;
	private Stage stage;
	private Table sidePanel;
	private Label statsLabel;
	private TiledMap map;
	private TiledMapRenderer mapRenderer;
	private boolean mapLeft, mapRight, mapUp, mapDown;
	private float unitScale, elapsedTime;
	private TextureRegion characterFrame;
	private Villager selected;
	private final Vector3 targetPos, oldPos, newPos;

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
		leftToZoom = leftToMoveX = leftToMoveY = 0;
		targetPos = new Vector3(-1, -1, -1);
		oldPos = new Vector3(-1, -1, -1);
		newPos = new Vector3(-1, -1, -1);
		
		map = new TmxMapLoader().load("badMap.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
		mapRenderer.setView(camera);
		camera.setToOrtho(false, 40, 22.5f);

		statsLabel = new Label("STR: " + character.getStr() + "\nITL: " + character.getItl() + "\nDEX: "
				+ character.getDex() + "\nCON: " + character.getCon() + "\nRES: " + character.getRes(), game.skin);

		sidePanel = new Table();
		sidePanel.add(statsLabel);

		sidePanel.setPosition(game.width*7/8f, game.height*3/4f);
		stage.addActor(sidePanel);

		elapsedTime = 0;
		
		character.moveTo(1, 1);
	}

	@Override
	public void render (float delta)
	{
		elapsedTime += Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//move the camera around when the flag to do so is set! TODO make this smooth, update leftToMove?
		if (mapUp)
			camera.translate(0, game.cameraSpeed*Gdx.graphics.getDeltaTime());
		if (mapDown)
			camera.translate(0, -game.cameraSpeed*Gdx.graphics.getDeltaTime());
		if (mapLeft)
			camera.translate(-game.cameraSpeed*Gdx.graphics.getDeltaTime(), 0);
		if (mapRight)
			camera.translate(game.cameraSpeed*Gdx.graphics.getDeltaTime(), 0);
		//smoothly scroll to the target level of zoom
		//TODO save vector from camera to mouse. move camera to some distance every frame.
		if (leftToZoom <= -.005 || leftToZoom >= .005)
		{
			if (oldPos.x != -1 && oldPos.y != -1 && oldPos.z != -1)
			{
				//save the mouse's current location
				newPos.set(targetPos);
				camera.unproject(newPos);
				
				//calculate the change in position and translate the camera along it
				newPos.sub(oldPos);
				newPos.set(-newPos.x, -newPos.y, -newPos.z);
				camera.translate(newPos);
			}
			
			//zoom the camera by the amount we need to multiplied by the time passed and the zoom speed, both are <1
			double zooming = game.zoomSpeed * leftToZoom * Gdx.graphics.getDeltaTime();
			camera.zoom += zooming;
			leftToZoom -= zooming;
			
			if (leftToZoom <= -.005 || leftToZoom >= .005)
			{
				//save the target position after we zoom for use in the next frame
				oldPos.set(targetPos);
				camera.unproject(oldPos);
			}
			//otherwise, we're done zooming. reset oldPos.
			else oldPos.set(-1, -1, -1);
		}
		//smoothly move in the x and y directions if leftToMove is updated by something
		if (leftToMoveX <= -.005 || leftToMoveX >= .005 || leftToMoveY <= -.005 || leftToMoveY >= .005)
		{
			float movingX = game.zoomSpeed * (float)leftToMoveX * Gdx.graphics.getDeltaTime();
			float movingY = game.zoomSpeed * (float)leftToMoveY * Gdx.graphics.getDeltaTime();
			camera.translate(movingX, movingY);
			leftToMoveX -= movingX;
			leftToMoveY -= movingY;
		}
		camera.update();
		mapRenderer.setView(camera);
		
		//SpriteBatch renders based on camera's coordinate system
		game.batch.setProjectionMatrix(camera.combined);
		stage.act(Gdx.graphics.getDeltaTime());
		
		characterFrame = character.getAnimation().getKeyFrame(elapsedTime, true);

		mapRenderer.render();
		stage.draw();
		game.batch.begin();
		game.batch.draw(characterFrame, character.getX(), character.getY(), 1, 1);
		game.batch.end();
	}

	@Override
	public void resize (int width, int height)
	{
		stage.getViewport().update(width, height, true);
		System.out.println(game.width + ", " + game.height);
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
	
	//moves whatever is selected, if anything, to the given coordinates.
	public void tryMove (int x, int y)
	{
		if (selected != null)
		{
			selected.moveTo(x, y);
			selected = null;
		}
	}
	
	public MapProperties getMapProperties()
	{
		return map.getProperties();
	}
	public int getGameHeight()
	{
		return game.height;
	}
	public int getGameWidth()
	{
		return game.width;
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
	//unprojects a vector to map coordinates and sets it as the zoom anchor
	public void setTargetPos (Vector3 target)
	{
		targetPos.set(target);
	}
}
