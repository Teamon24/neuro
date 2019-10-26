package com.home.utils;

import lombok.NonNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * Утилита для работы с изображениями. */
public final class ImageUtils {

    private static final String notFoundImagePath = null;

    /**
     * Изображение, которое возвращется при ошибке работы с изображением. */
    static final BufferedImage EXCEPTION_BUFFERED_IMAGE = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

    /**
     * Имя изображения, которое возвращется при ошибке работы с изображением. */
    private static final String NOT_FOUND_IMAGE = "not_found_image.png";

    /**
     * Скрытый конструктор для утилиты.
     */
    private ImageUtils() {}

    /**
     * @param img изображение, размеры которого изменяются.
     * @param height высота, до которой необходимо изменить высоту изображения.
     * @param width ширина, до которой необходимо изменить ширину изображения.
     * @return изображение с измененными размерами.
     */
    public static BufferedImage resize(@NonNull final BufferedImage img, final int height, final int width) {
        final Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        final BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public static ImageIcon resize(final ImageIcon imageIcon,
                                   final int newSize)
    {
        final BufferedImage originalImage = toBufferedImage(imageIcon.getImage());
        final BufferedImage buttonSizedProfileImage = resize(originalImage, newSize, newSize);
        return new ImageIcon(buttonSizedProfileImage);
    }

    public static BufferedImage getImageByUrl(@NonNull final File file) {
        if (file.isFile()) {
            try {
                return ImageIO.read(file);
            } catch (IOException e) {
                return getNotFoundImage();
            }
        } else {
            return getNotFoundImage();
        }
    }

    public static BufferedImage getImageByUrl(@NonNull final URL url) {
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            return ImageUtils.getNotFoundImage();
        }
    }

    /**
     * Получение изображения по url-адресу.
     * @param urlStr url-адрес в строковом представлении.
     * @return {@link BufferedImage}, сформированный из изображения по url-адресу.
     */
    public static BufferedImage getImageByUrl(@NonNull final String urlStr) {
        try {
            final URL url = new URL(urlStr);
            return ImageIO.read(url);
        } catch (IOException e) {
            return ImageUtils.getNotFoundImage();
        }
    }

    /**
     * Получение изображения по пути из локальной файловой системы.
     * @param filePath путь к файлу.
     * @return {@link BufferedImage}, сформированный из изображения по пути в файловой системе.
     */
    public static BufferedImage getImage(@NonNull final String filePath) {
        try (final FileInputStream input = new FileInputStream(filePath)) {
            return ImageIO.read(input);
        } catch (IOException e) {
            return ImageUtils.getNotFoundImage();
        }
    }

    /**
     * Получение изображения для отображения некорректно выполненного кода при получении изображения.
     * @return {@link BufferedImage}, сформированный из изображения для
     * отображения некорректно выполненного кода при получении изображения..
     */
    public static BufferedImage getNotFoundImage() {
        try {
            final String notFoundImageName = notFoundImagePath + NOT_FOUND_IMAGE;
            final URL resource = InputStream.class.getResource(notFoundImageName);
            return ImageIO.read(resource);
        } catch (IOException e) {
            return EXCEPTION_BUFFERED_IMAGE;
        }
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create history buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }


    public static BufferedImage setColor(@NonNull final Color toSetUpColor,
                                         @NonNull final BufferedImage img)
    {
        final int height = img.getHeight();
        final int width = img.getWidth();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final int imageARGB = img.getRGB(x, y);


                final int r = getR(imageARGB);
                final int g = getG(imageARGB);
                final int b = getB(imageARGB);

                if (isNotWhite(r, g, b) && getA(imageARGB) != 0) {
                    img.setRGB(x, y, toSetUpColor.getRGB());
                }
            }
        }
        return img;
    }

    private static boolean isNotWhite(final int r, final int g, final int b) {
        final Color white = Color.WHITE;
        return r != white.getRed() && g != white.getGreen() && b != white.getBlue();
    }

    public static int getA(final int argb) {
        return (argb >> 24) & 0x000000FF;
    }

    public static int getR(final int argb) {
        return (argb >> 16) & 0x000000FF;
    }

    public static int getG(final int argb) {
        return (argb >> 8) & 0x000000FF;
    }

    public static int getB(final int argb) {
        return (argb) & 0x000000FF;
    }
}
