package science.skywhale.koicrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Villager extends Actor
{
	String firstName, lastName;
	int gender, str, con, dex, itl, res, maxHP, hp, consciousness;
	int[] deathRolls = {0, 0};
	//Tier1Class firstClass;
	//CharacterClass secondClass;
	//Weapon weapon;
	//Proficiency[] proficiencies;
	Animation<TextureRegion> animation;

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
}
