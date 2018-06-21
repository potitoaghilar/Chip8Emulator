package com.potito.Chip8Emulator;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private int width;
    private int height;
    private int zoom;

    JPanel mainContent = new JPanel();

    public MainWindow(String title, int width, int height, int zoom) {
        super(title);

        this.width = width;
        this.height = height;
        this.zoom = zoom;

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(width * zoom, height * zoom);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        mainContent.setSize(width * zoom, height * zoom);
        this.add(mainContent);

        // Render first black frame
        render(new byte[width * height]);
    }

    public void render(byte[] pixels) {

        Graphics g = mainContent.getGraphics();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {

                int pixelColor = Utils.convertToUnsigned(pixels[y * width + x]);
                g.setColor(new Color(pixelColor, pixelColor, pixelColor));

                g.fillRect(x * zoom, y * zoom, zoom, zoom);

            }
        }

    }
}
