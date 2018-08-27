package clue;


public class WeaponCard extends Card {
	
	public enum Name{
		CANDLESTICK,
		DAGGER,
		LEAD_PIPE,
		REVOLVER,
		ROPE,
		SPANNER;
	}
	
	private Name name;	
	
	public WeaponCard(Name name) {
		this.name = name;
		super.name=this.toString();
	}

	public Name getName() {
		return this.name;
	}
	
	private static String[] weaponCards = {"Candlestick","Dagger","Lead Pipe","Revolver","Rope","Spanner"};
	
	@Override
	public String toString() {
		return weaponCards[this.name.ordinal()];
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
		if(s.Weapon==this)return true;
		return false;
	}
}
