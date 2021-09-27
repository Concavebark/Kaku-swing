public class Glyph {
    public final int width;
    public final int height;
    public final int x;
    public final int y;
    public final float advance;

    public Glyph(int _width, int _height, int _x, int _y, float _advance) {
        this.width = _width;
        this.height = _height;
        this.x = _x;
        this.y = _y;
        this.advance = _advance;
    }
}
