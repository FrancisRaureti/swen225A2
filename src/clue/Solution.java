package clue;
/**
 * this class holds the final solution
 * the only significant method is the accusation method which compares 
 * player accusation to solution
 * @author Raure
 *
 */
public class Solution {
	
	private WeaponCard weapon;
	private CharacterCard Character;
	private RoomCard room;

	public Solution(WeaponCard w, CharacterCard c, RoomCard r ) {
		weapon=w;
		Character=c;
		room=r;
	}
	
	
	/**
	 * compares accusation to solution
	 * @param w
	 * @param c
	 * @param r
	 * @return true if accusation is correct
	 * @return false if accusation incorrect
	 */
	public boolean accusation(WeaponCard w, CharacterCard c, RoomCard r) {
		if(w==weapon && c==Character && r==room)return true;
		return false;
	}

}