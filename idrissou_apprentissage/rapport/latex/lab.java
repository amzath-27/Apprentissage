package aatp1;

public class LabelledData {
	private int cls;
	private byte[] glyph;
	
	public LabelledData(int cls, byte[] glyph) { this.cls=cls; this.glyph = glyph; }
	int getCls() { return cls; }
	byte[] getGlyph() {return glyph; }
}
