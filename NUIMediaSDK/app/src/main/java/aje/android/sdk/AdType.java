package aje.android.sdk;

public enum AdType {
	/** Asks AdJuggler to return image ad **/
	Image("image"),
	/** Asks AdJuggler to return text ad **/
	Text("text");
	
	private String name;
	private AdType(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
