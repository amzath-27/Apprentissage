package util;

import java.util.Arrays;

public class LabelledData {
	private int cls;
	private byte[] glyph;
	
	public LabelledData(int cls, byte[] glyph) {
        this.cls = cls;
        this.glyph = glyph;
    }

	public int getCls() {
        return cls;
    }

	public byte[] getGlyph() {
        return glyph;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != getClass()) {
            return false;
        }

        LabelledData ld = (LabelledData) o;
        return ld.cls == cls && Arrays.equals(glyph, ld.glyph);
    }

    @Override
    public int hashCode() {
        return 31 * cls + (glyph != null ? Arrays.hashCode(glyph) : 0);
    }
}
