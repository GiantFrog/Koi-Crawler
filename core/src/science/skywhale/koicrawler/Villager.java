package science.skywhale.koicrawler;

public class Villager
{
	String firstName, lastName;
	int gender, str, con, dex, itl, res, maxHP, hp, consciousness;
	int[] deathRolls = {0, 0};
	//Tier1Class firstClass;
	//CharacterClass secondClass;
	//Weapon weapon;
	//Proficiency[] proficiencies;

	public Villager (String first, String last, int gender, int str, int itl, int dex, int con, int res)
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
	}
}
