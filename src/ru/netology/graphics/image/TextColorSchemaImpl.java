package ru.netology.graphics.image;

public class TextColorSchemaImpl implements TextColorSchema {

    private final char[] symbols;

    public TextColorSchemaImpl(char[] symbols) {
        this.symbols = symbols;
    }

    @Override
    public char convert(int color) {
        return symbols[(int) Math.ceil(((symbols.length - 1) * color) / 255.0)];
    }
}
