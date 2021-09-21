package ru.netology.graphics.image;

public class Dimension {
    int height;
    int width;

    public Dimension(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public String toString() {
        return "Dimension{" +
                "height=" + height +
                ", width=" + width +
                '}';
    }
}
