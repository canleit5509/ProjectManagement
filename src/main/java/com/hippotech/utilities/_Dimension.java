package com.hippotech.utilities;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class _Dimension {
    Rectangle2D screenBound;
    public _Dimension() {
        screenBound = Screen.getPrimary().getBounds();
    }
    public double getMaxScreenWidth() {
        return screenBound.getMaxX();
    }
    public double getMaxScreenHeight() {
        return screenBound.getMaxY();
    }
    public double getScreenWidth() {
        return screenBound.getWidth();
    }
    public double getScreenHeight() {
        return screenBound.getHeight();
    }
}
