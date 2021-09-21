package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class TextGraphicsConverterImpl implements TextGraphicsConverter {

    private Dimension resultImageDim;
    private double maxRatio = -1;
    private TextColorSchema schema;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        BufferedImage img = ImageIO.read(new URL(url));
        Dimension newDimension = calculateNewDimension(img);

        Image scaledImage = img.getScaledInstance(newDimension.width, newDimension.height, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newDimension.width, newDimension.height, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);

        WritableRaster bwRaster = bwImg.getRaster();

        String[][] result = new String[bwImg.getHeight()][bwImg.getWidth()];

        for (int w = 0; w < bwImg.getWidth(); w++) {
            for (int h = 0; h < bwImg.getHeight(); h++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                result[h][w] = String.valueOf(c) + c;
            }
        }
        return makeText(result);
    }

    private String makeText(String[][] text) {
        StringBuilder builder = new StringBuilder();
        for (String[] column : text) {
            for (String sym : column) {
                builder.append(sym);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        getResultDimLazy().width = width;
    }

    @Override
    public void setMaxHeight(int height) {
        getResultDimLazy().height = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }

    private Dimension getResultDimLazy() {
        if (resultImageDim == null) {
            resultImageDim = new Dimension(0, 0);
        }
        return resultImageDim;
    }

    private Dimension calculateNewDimension(BufferedImage image) throws BadImageSizeException {
        Dimension resultDim = getResultDimLazy();

        double aspectRatio = (double) image.getWidth() / image.getHeight();

        if (aspectRatio > maxRatio) {
            throw new BadImageSizeException(aspectRatio, maxRatio);
        }

        int maxLength = Math.max(resultDim.height, resultDim.width);

        int targetWidth = image.getWidth();
        int targetHeight = image.getHeight();
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();

        if (originalHeight > maxLength || originalWidth > maxLength) {
            if (originalWidth > originalHeight) {
                targetWidth = maxLength;
                targetHeight = (int) Math.round(targetWidth / aspectRatio);
            } else {
                aspectRatio = 1 / aspectRatio;
                targetHeight = maxLength;
                targetWidth = (int) Math.round(targetHeight / aspectRatio);
            }

        }
        return new Dimension(targetHeight, targetWidth);
    }
}
