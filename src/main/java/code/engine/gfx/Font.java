package code.engine.gfx;

public class Font {
	public static final Font STANDARD = new Font("/resources/fonts/fira.png"); 

	private Image fontImage;
	private int[] offsets;
	private int[] widths;

	//#region setters y getters
	public Image getFontImage() {
		return fontImage;
	}

	public void setFontImage(Image fontImage) {
		this.fontImage = fontImage;
	}

	public int[] getOffsets() {
		return offsets;
	}

	public void setOffsets(int[] offsets) {
		this.offsets = offsets;
	}

	public int[] getWidths() {
		return widths;
	}

	public void setWidths(int[] widths) {
		this.widths = widths;
	}
	//#endregion

	public Font(String path) {
		fontImage = new Image(path);

		offsets = new int[256];
		widths = new int[256];

		int Unicode = 0;

		for (int i = 0; i < fontImage.getW(); i++) {
			if (fontImage.getP()[i] == 0xff0000ff) {
				offsets[Unicode] = i;
			}
			if (fontImage.getP()[i] == 0xffffff00) {
				widths[Unicode] = i - offsets[Unicode];
				Unicode++;
			}
		}
	}
}
