package ru.netology.graphics;

import ru.netology.graphics.image.TextColorSchemaImpl;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.image.TextGraphicsConverterImpl;
import ru.netology.graphics.server.GServer;

public class Main {

    public static final char[] WIN_CHARS = {'#', '$', '@', '%', '*', '+', '-', '\''};
    public static final char[] OTHER_CHARS = {'▇', '●', '◉', '◍', '◎', '○', '☉', '◌', '-'};

    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new TextGraphicsConverterImpl();
        converter.setTextColorSchema(new TextColorSchemaImpl(WIN_CHARS));

        GServer server = new GServer(converter);
        server.start();

        // Или то же, но с сохранением в файл:
        /*
        PrintWriter fileWriter = new PrintWriter(new File("converted-image.txt"));
        converter.setMaxWidth(200);
        converter.setMaxHeight(300);
        fileWriter.write(converter.convert("https://i.ibb.co/6DYM05G/edu0.jpg"));
        fileWriter.close();
        */
    }
}
