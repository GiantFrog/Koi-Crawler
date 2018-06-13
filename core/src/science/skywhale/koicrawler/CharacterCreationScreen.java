package science.skywhale.koicrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class CharacterCreationScreen implements Screen
{
	private final KoiCrawler game;
	private Stage stage;
	private Table table, statTable;
	private TextButton strPlus, strMinus, itlPlus, itlMinus, dexPlus, dexMinus, conPlus, conMinus, resPlus, resMinus, submitButton;
	private Label nameLabel, genderLabel, statDescriptions, strLabel, itlLabel, dexLabel, conLabel, resLabel, pointLabel;
	private TextField firstNameField, lastNameField;
	private CheckBox maleBox, femaleBox, nahBox;
	private ButtonGroup genderButtons;
	private int str, itl, dex, con, res, points;

	public CharacterCreationScreen (final KoiCrawler game)
	{
		this.game = game;
		stage = new Stage(new FitViewport(game.width, game.height));
		Gdx.input.setInputProcessor(stage);

		points = 21;
		str = itl = dex = con = res = 8;
		nameLabel = new Label("Name:", game.skin);
		firstNameField = new TextField("", game.skin);
		firstNameField.setAlignment(Align.center);
		lastNameField = new TextField("", game.skin);
		lastNameField.setAlignment(Align.center);

		genderLabel = new Label("Gender:", game.skin);
		maleBox = new CheckBox("male", game.skin);
		femaleBox = new CheckBox("female", game.skin);
		nahBox = new CheckBox("nah.", game.skin);
		genderButtons = new ButtonGroup(nahBox, maleBox, femaleBox);
		genderButtons.setMinCheckCount(1);
		genderButtons.setMaxCheckCount(1);
		genderButtons.setUncheckLast(true);
		genderButtons.setChecked("nah.");

		//god this is awful. there must be a better way.
		strPlus = new TextButton("+", game.skin);
		strPlus.addListener(new ClickListener()
		{
			@Override
			public void clicked (InputEvent event, float x, float y)
			{
				switch(str)
				{
					case 14:
					case 15:
						str++;
						points -= 2;
						break;
					case 16:
					case 17:
						str++;
						points -= 3;
						break;
					case 18:
						break;
					default:
						str++;
						points--;
				}
				strLabel.setText("Strength: " + str);
				pointLabel.setText("Points to spend: " + points);
			}
		});
		strMinus = new TextButton("-", game.skin);
		strMinus.addListener(new ClickListener()
		{
			@Override
			public void clicked (InputEvent event, float x, float y)
			{
				switch(str)
				{
					case 15:
					case 16:
						str--;
						points += 2;
						break;
					case 17:
					case 18:
						str--;
						points += 3;
						break;
					case 6:
						break;
					default:
						str--;
						points++;
				}
				strLabel.setText("Strength: " + str);
				pointLabel.setText("Points to spend: " + points);
			}
		});
		itlPlus = new TextButton("+", game.skin);
		itlPlus.addListener(new ClickListener()
		{
			@Override
			public void clicked (InputEvent event, float x, float y)
			{
				switch(itl)
				{
					case 14:
					case 15:
						itl++;
						points -= 2;
						break;
					case 16:
					case 17:
						itl++;
						points -= 3;
						break;
					case 18:
						break;
					default:
						itl++;
						points--;
				}
				itlLabel.setText("Intelligence: " + itl);
				pointLabel.setText("Points to spend: " + points);
			}
		});
		itlMinus = new TextButton("-", game.skin);
		itlMinus.addListener(new ClickListener()
		{
			@Override
			public void clicked (InputEvent event, float x, float y)
			{
				switch(itl)
				{
					case 15:
					case 16:
						itl--;
						points += 2;
						break;
					case 17:
					case 18:
						itl--;
						points += 3;
						break;
					case 6:
						break;
					default:
						itl--;
						points++;
				}
				itlLabel.setText("Intelligence: " + itl);
				pointLabel.setText("Points to spend: " + points);
			}
		});
		dexPlus = new TextButton("+", game.skin);
		dexPlus.addListener(new ClickListener()
		{
			@Override
			public void clicked (InputEvent event, float x, float y)
			{
				switch(dex)
				{
					case 14:
					case 15:
						dex++;
						points -= 2;
						break;
					case 16:
					case 17:
						dex++;
						points -= 3;
						break;
					case 18:
						break;
					default:
						dex++;
						points--;
				}
				dexLabel.setText("Dexterity: " + dex);
				pointLabel.setText("Points to spend: " + points);
			}
		});
		dexMinus = new TextButton("-", game.skin);
		dexMinus.addListener(new ClickListener()
		{
			@Override
			public void clicked (InputEvent event, float x, float y)
			{
				switch(dex)
				{
					case 15:
					case 16:
						dex--;
						points += 2;
						break;
					case 17:
					case 18:
						dex--;
						points += 3;
						break;
					case 6:
						break;
					default:
						dex--;
						points++;
				}
				dexLabel.setText("Dexterity: " + dex);
				pointLabel.setText("Points to spend: " + points);
			}
		});
		conPlus = new TextButton("+", game.skin);
		conPlus.addListener(new ClickListener()
		{
			@Override
			public void clicked (InputEvent event, float x, float y)
			{
				switch(con)
				{
					case 14:
					case 15:
						con++;
						points -= 2;
						break;
					case 16:
					case 17:
						con++;
						points -= 3;
						break;
					case 18:
						break;
					default:
						con++;
						points--;
				}
				conLabel.setText("Constitution: " + con);
				pointLabel.setText("Points to spend: " + points);
			}
		});
		conMinus = new TextButton("-", game.skin);
		conMinus.addListener(new ClickListener()
		{
			@Override
			public void clicked (InputEvent event, float x, float y)
			{
				switch(con)
				{
					case 15:
					case 16:
						con--;
						points += 2;
						break;
					case 17:
					case 18:
						con--;
						points += 3;
						break;
					case 6:
						break;
					default:
						con--;
						points++;
				}
				conLabel.setText("Constitution: " + con);
				pointLabel.setText("Points to spend: " + points);
			}
		});
		resPlus = new TextButton("+", game.skin);
		resPlus.addListener(new ClickListener()
		{
			@Override
			public void clicked (InputEvent event, float x, float y)
			{
				switch(res)
				{
					case 14:
					case 15:
						res++;
						points -= 2;
						break;
					case 16:
					case 17:
						res++;
						points -= 3;
						break;
					case 18:
						break;
					default:
						res++;
						points--;
				}
				resLabel.setText("Resistance: " + res);
				pointLabel.setText("Points to spend: " + points);
			}
		});
		resMinus = new TextButton("-", game.skin);
		resMinus.addListener(new ClickListener()
		{
			@Override
			public void clicked (InputEvent event, float x, float y)
			{
				switch(res)
				{
					case 15:
					case 16:
						res--;
						points += 2;
						break;
					case 17:
					case 18:
						res--;
						points += 3;
						break;
					case 6:
						break;
					default:
						res--;
						points++;
				}
				resLabel.setText("Resistance: " + res);
				pointLabel.setText("Points to spend: " + points);
			}
		});

		statDescriptions = new Label("-Strength determines the power and hit chance of attacks made\nwith melee and thrown weapons. It also affects your carrying capacity.\n" +
				"-Intelligence determines the strength of your spells, including the\npower and hit chance of magical attacks.\n" +
				"-Dexterity determines how difficult you are to hit, how far you can\nmove, and your hit chance with certain nimble weapons.\n" +
				"-Constitution raises your HP and reduces physical damage taken.\nIt is also a good measure of your alcohol, poison, and pain tolerances.\n" +
				"-Resistance raises your HP and reduces magical damage taken.\nIt is also a good measure of your willpower.", game.skin);
		strLabel = new Label("Strength: " + str, game.skin);
		itlLabel = new Label("Intelligence: " + itl, game.skin);
		dexLabel = new Label("Dexterity: " + dex, game.skin);
		conLabel = new Label("Constitution: " + con, game.skin);
		resLabel = new Label("Resistance: " + res, game.skin);

		pointLabel = new Label("Points to spend: " + points, game.skin);
		submitButton = new TextButton("Begin!", game.skin);
		submitButton.addListener(new ClickListener()
		{
			@Override
			public void clicked (InputEvent event, float x, float y)
			{
				if (points >= 0)
				{
					game.character = new Villager(firstNameField.getText(), lastNameField.getText(), genderButtons.getCheckedIndex(), str, itl, dex, con, res);
					game.setScreen(new LevelScreen(game));
					dispose();
				}
			}
		});

		statTable = new Table();
		statTable.add(strLabel).right().space(10);
		statTable.add(strMinus).width(20).space(10);
		statTable.add(strPlus).width(20).space(10);
		statTable.row();
		statTable.add(itlLabel).right().space(10);
		statTable.add(itlMinus).width(20).space(10);
		statTable.add(itlPlus).width(20).space(10);
		statTable.row();
		statTable.add(dexLabel).right().space(10);
		statTable.add(dexMinus).width(20).space(10);
		statTable.add(dexPlus).width(20).space(10);
		statTable.row();
		statTable.add(conLabel).right().space(10);
		statTable.add(conMinus).width(20).space(10);
		statTable.add(conPlus).width(20).space(10);
		statTable.row();
		statTable.add(resLabel).right().space(10);
		statTable.add(resMinus).width(20).space(10);
		statTable.add(resPlus).width(20).space(10);

		table = new Table();
		table.setFillParent(true);
		//table.setDebug(true);
		table.add(nameLabel).space(10).right();
		table.add(firstNameField).width(150).space(10);
		table.add(lastNameField).width(150).space(10).left();
		table.add(genderLabel).space(10);
		table.add(maleBox).space(10);
		table.add(femaleBox).space(10);
		table.add(nahBox).space(10).left();
		table.row();
		table.add(statTable).colspan(2);
		table.add(statDescriptions).colspan(5);
		table.row();
		table.add(pointLabel).colspan(3).space(30);
		table.add(submitButton).colspan(4).space(30).width(80).height(60);

		stage.addActor(table);
	}
	@Override
	public void render (float delta)
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.camera.update();
		//SpriteBatch renders based on camera's coordinate system
		game.batch.setProjectionMatrix(game.camera.combined);
		//stage.act(Gdx.graphics.getDeltaTime());

		game.batch.begin();
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
		stage.dispose();
	}
}
