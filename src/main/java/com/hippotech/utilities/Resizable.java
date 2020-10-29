package com.hippotech.utilities;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Resizable {
    private static final OnResizeEventListener defaultListener = new OnResizeEventListener() {
        @Override
        public void onResize(Node node, double x, double y, double width, double height) {
            setNodeSize(node, x, y, width, height);
        }

        private void setNodeSize(Node node, double x, double y, double h, double w) {
            node.setLayoutX(x);
            node.setLayoutY(y);
            // here we cant set height and width to node directly.
            // or i just cant find how to do it,
            // so you have to wright resize code anyway for your Nodes...
            //something like this
            if (node instanceof Canvas) {
                ((Canvas) node).setWidth(w);
                ((Canvas) node).setHeight(h);
            } else if (node instanceof Rectangle) {
                ((Rectangle) node).setWidth(w);
                ((Rectangle) node).setHeight(h);
            }
            else if (node instanceof Pane) {
                ((Pane) node).setPrefSize(w, h);
            }
        }
    };
    private static final int MARGIN = 8;
    private static final double MIN_W = 70;
    private static final double MIN_H = 20;
    private final Node node;
    private final Node previousNode;
    private double clickX, clickY, nodeX, nodeY, nodeH, nodeW;
    private double previousNodeH, previousNodeW, previousNodeX, previousNodeY;
    private S state = S.DEFAULT;
    private OnResizeEventListener listener = defaultListener;

    private Resizable(Node node, Node previousNode, OnResizeEventListener listener) {
        this.node = node;
        this.previousNode = previousNode;
        if (listener != null) this.listener = listener;
    }

    public static void makeResizable(Node node, Node previousNode) {
        makeResizable(node, previousNode, null);
    }

    public static void makeResizable(Node node, Node previousNode, OnResizeEventListener listener) {
        final Resizable resizer = new Resizable(node, previousNode, listener);

        node.setOnMousePressed(resizer::mousePressed);
        node.setOnMouseDragged(resizer::mouseDragged);
        node.setOnMouseMoved(resizer::mouseOver);
        node.setOnMouseReleased(resizer::mouseReleased);
    }

    private static Cursor getCursorForState(S state) {
        switch (state) {
            case NW_RESIZE:
                return Cursor.NW_RESIZE;
            case SW_RESIZE:
                return Cursor.SW_RESIZE;
            case NE_RESIZE:
                return Cursor.NE_RESIZE;
            case SE_RESIZE:
                return Cursor.SE_RESIZE;
            case E_RESIZE:
                return Cursor.E_RESIZE;
            case W_RESIZE:
                return Cursor.W_RESIZE;
            case N_RESIZE:
                return Cursor.N_RESIZE;
            case S_RESIZE:
                return Cursor.S_RESIZE;
            default:
                return Cursor.DEFAULT;
        }
    }

    protected void mouseReleased(MouseEvent event) {
        node.setCursor(Cursor.DEFAULT);
        state = S.DEFAULT;
    }

    protected void mouseOver(MouseEvent event) {
        S state = currentMouseState(event);
        Cursor cursor = getCursorForState(state);
        node.setCursor(cursor);
    }

    private S currentMouseState(MouseEvent event) {
        S state = S.DEFAULT;
        boolean left = isLeftResizeZone(event);
        boolean right = isRightResizeZone(event);
        boolean top = isTopResizeZone(event);
        boolean bottom = isBottomResizeZone(event);

        if (left && top) state = S.NW_RESIZE;
        else if (left && bottom) state = S.SW_RESIZE;
        else if (right && top) state = S.NE_RESIZE;
        else if (right && bottom) state = S.SE_RESIZE;
        else if (right) state = S.E_RESIZE;
        else if (left) state = S.W_RESIZE;
        else if (top) state = S.N_RESIZE;
        else if (bottom) state = S.S_RESIZE;

        return state;
    }

    protected void mouseDragged(MouseEvent event) {
        if (listener != null) {
            double mouseX = parentX(event.getX());
            double mouseY = parentY(event.getY());
            if (state == S.DRAG) {

            } else if (state != S.DEFAULT) {
                double newX;
                double newY;
                double newH;
                double newW;

                // Right Resize
                if (state == S.E_RESIZE || state == S.NE_RESIZE || state == S.SE_RESIZE) {
                    //resizing
                    newX = nodeX;
                    newY = nodeY;
                    newH = nodeH;
                    newW = mouseX - nodeX;
                    if (newW < MIN_W) {
                        newW = MIN_W;
                    }
                    listener.onResize(node, newX, newY, newH, newW);
                }
                // Left Resize
                if (state == S.W_RESIZE || state == S.NW_RESIZE || state == S.SW_RESIZE) {
                    newX = previousNodeX;
                    newY = previousNodeY;
                    newW = mouseX-previousNodeX;
                    newH = previousNodeH;
                    if (newW < MIN_W) {
                        newW = MIN_W;
                    }
                    listener.onResize(previousNode, newX, newY, newH, newW);
                }
            }
        }
    }

    protected void mousePressed(MouseEvent event) {

        if (isInResizeZone(event)) {
            setNewInitialEventCoordinates(event);
            state = currentMouseState(event);
        } else {
            state = S.DEFAULT;
        }
    }

    private void setNewInitialEventCoordinates(MouseEvent event) {
        nodeX = nodeX();
        nodeY = nodeY();
        nodeH = nodeH();
        nodeW = nodeW();
        if (previousNode!= null) {
            previousNodeX = previousNodeX();
            previousNodeY = previousNodeY();
            previousNodeH = previousNodeH();
            previousNodeW = previousNodeW();
        }
        clickX = event.getX();
        clickY = event.getY();
    }

    private boolean isInResizeZone(MouseEvent event) {
        return isLeftResizeZone(event) || isRightResizeZone(event)
                || isBottomResizeZone(event) || isTopResizeZone(event);
    }

    private boolean isLeftResizeZone(MouseEvent event) {
        return intersect(0, event.getX());
    }

    private boolean isRightResizeZone(MouseEvent event) {
        return intersect(nodeW(), event.getX());
    }

    private boolean isTopResizeZone(MouseEvent event) {
        return intersect(0, event.getY());
    }

    private boolean isBottomResizeZone(MouseEvent event) {
        return intersect(nodeH(), event.getY());
    }

    private boolean intersect(double side, double point) {
        return side + MARGIN > point && side - MARGIN < point;
    }

    private double parentX(double localX) {
        return nodeX() + localX;
    }

    private double parentY(double localY) {
        return nodeY() + localY;
    }

    private double nodeX() {
        return node.getBoundsInParent().getMinX();
    }

    private double nodeY() {
        return node.getBoundsInParent().getMinY();
    }

    private double nodeW() {
        return node.getBoundsInParent().getWidth();
    }

    private double nodeH() {
        return node.getBoundsInParent().getHeight();
    }

    private double previousNodeX() {
        return previousNode.getBoundsInParent().getMinX();
    }

    private double previousNodeY() {
        return previousNode.getBoundsInParent().getMinY();
    }

    private double previousNodeW() {
        return previousNode.getBoundsInParent().getWidth();
    }

    private double previousNodeH() {
        return previousNode.getBoundsInParent().getHeight();
    }

    public enum S {
        DEFAULT,
        DRAG,
        NW_RESIZE,
        SW_RESIZE,
        NE_RESIZE,
        SE_RESIZE,
        E_RESIZE,
        W_RESIZE,
        N_RESIZE,
        S_RESIZE
    }

    public interface OnResizeEventListener {
        void onResize(Node node, double x, double y, double width, double height);
    }
}
