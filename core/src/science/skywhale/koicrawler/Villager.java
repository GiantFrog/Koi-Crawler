package science.skywhale.koicrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Villager
{
	private String firstName, lastName;
	private int x, y, gender, str, con, dex, itl, res, maxHP, hp, consciousness;
	private int[] deathRolls = {0, 0};
	//Tier1Class firstClass;
	//CharacterClass secondClass;
	//Weapon weapon;
	//Proficiency[] proficiencies;
	private Animation<TextureRegion> animation;

	public Villager (String first, String last, int gender, int str, int itl, int dex, int con, int res, String spriteName, TextureAtlas atlas)
	{
		firstName = first;
		lastName = last;
		this.gender = gender;
		this.str = str;
		this.itl = itl;
		this.dex = dex;
		this.con = con;
		this.res = res;
		hp = maxHP = con + res;
		consciousness = 2;
		animation = new Animation<TextureRegion>(0.17f, atlas.findRegions(spriteName), Animation.PlayMode.LOOP);
	}
	
	boolean moveTo (int x, int y)
	{
		this.x = x;
		this.y = y;
		return true;
	}
	
	public String getFirstName ()
	{
		return firstName;
	}
	public String getLastName ()
	{
		return lastName;
	}
	public int getX ()
	{
		return x;
	}
	public int getY ()
	{
		return y;
	}
	public int getGender ()
	{
		return gender;
	}
	public int getStr ()
	{
		return str;
	}
	public int getCon ()
	{
		return con;
	}
	public int getDex ()
	{
		return dex;
	}
	public int getItl ()
	{
		return itl;
	}
	public int getRes ()
	{
		return res;
	}
	public int getMaxHP ()
	{
		return maxHP;
	}
	public int getHp ()
	{
		return hp;
	}
	public int getConsciousness ()
	{
		return consciousness;
	}
	public int[] getDeathRolls ()
	{
		return deathRolls;
	}
	public Animation<TextureRegion> getAnimation ()
	{
		return animation;
	}
}
