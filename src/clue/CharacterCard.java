package clue;


public class CharacterCard extends Card {
	
	
	
	public enum Name{
		MISS_SCARLET,
		COLONEL_MUSTARD,
		MRS_WHITE,
		MR_GREEN,
		MRS_PEACOCK,
		PROFESSOR_PLUM;
	}

	
	private Name name;

	public CharacterCard(Name name) {
		this.name=name;
		super.name=this.toString();
	}
	
	public Name getName() {
		return this.name;
	}
	
	private static String[] characterCards = {"Miss Scarlet","Col. Mustard","Mrs White","Mr Green","Mrs Peacock","Prof. Plum"};
	
	@Override
	public String toString() {
		return characterCards[this.name.ordinal()];
	}
	
	@Override
	public boolean isEqualTo(String name) {
		if (this.toString().equals(name))return true;
		return false;
	}
	
	@Override
	public Card stringToCard(String input) {
		if(input.equals(this.toString()))return (Card)this;
		return null;
	}

	@Override
	public boolean isRefutable(Suggestion s) {
		if(s.character==this)return true;
		return false;
	}
}