package hr.java.web.zdelarec.moneyapp.enumeration;

public enum Type {
	FOOD("Hrana"),
	DRINK("Pice"),
	RENT("Najam"),
	BILLS("Rezije"),
	SHOP("Ducan"),
	OTHER("Ostalo");
	
	public String desc; 
	
	public String getDesc() {
		return desc;
	}
	
	private Type (String s) {
		desc = s;
	}
}
