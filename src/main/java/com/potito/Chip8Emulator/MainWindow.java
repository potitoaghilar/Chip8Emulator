package com.potito.Chip8Emulator;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private int width;
    private int height;
    private int zoom;

    public MainWindow(String title, int width, int height, int zoom) {
        super(title);

        this.width = width;
        this.height = height;
        this.zoom = zoom;

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        Insets insets = this.getInsets();
        this.setBounds(0, 0, width + insets.left + insets.right, height + insets.top + insets.bottom);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void render(byte[] pixels) {

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {

                int pixelColor = pixels[]
                this.getGraphics().setColor(new Color());

                this.getGraphics().fillRect(x, y, zoom, zoom);

            }
        }

    }

}
