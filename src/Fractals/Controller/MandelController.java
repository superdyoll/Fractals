package Fractals.Controller;

import Fractals.Maths.Complex;
import Fractals.View.MandelView;
import Fractals.Model.MandelModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author Lloyd
 */
public class MandelController extends JPanel implements MouseListener,
        MouseWheelListener {

    private final MandelView view;
    private BufferedImage image;
    private int zoom, iterations;
    private boolean juliaSet, imageDrawn, onZoomMode, thumbnail, imageRedrawn;
    private Complex fixed;
    private double startMouseX, startMouseY, endMouseX, endMouseY, panX, panY,
            xCenter, yCenter;
    private AffineTransform transformer = new AffineTransform();

    public MandelController(MandelView controller) {
        this(controller, false, 150);
    }

    public MandelController(MandelView controller, boolean juliaSet) {
        this(controller, juliaSet, 150);
    }

    public MandelController(MandelView controller, boolean juliaSet, int zoom) {
        iterations = 570;

        imageRedrawn = true;

        this.juliaSet = juliaSet;
        setZoom(zoom);
        // setBounds(100, 100, 800, 600);

        addMouseListener(this);
        addMouseWheelListener(this);

        view = controller;
        fixed = new Complex(0, 0);
    }

    public void initialiseXY() {
        initialiseXY(true);
    }

    public void initialiseXY(boolean doRepaint) {
        imageRedrawn = true;
        xCenter = this.getWidth() / 2;
        yCenter = this.getHeight() / 2;
        if (doRepaint) {
            this.repaint();
        }
    }

    /**
     * Returns the colour according to the colour map similar to the wikipedia
     * image
     *
     * @param maxIterations
     * @param iter
     * @return
     */
    public Color getColour(int maxIterations, int iter) {
        if (iter < maxIterations && iter > 0) {
            int i = iter % 16;
            Color c;
            switch (i) {
                case 0:
                    c = new Color(66, 30, 15);
                    break;
                case 1:
                    c = new Color(25, 7, 26);
                    break;
                case 2:
                    c = new Color(9, 1, 47);
                    break;
                case 3:
                    c = new Color(4, 4, 73);
                    break;
                case 4:
                    c = new Color(0, 7, 100);
                    break;
                case 5:
                    c = new Color(12, 44, 138);
                    break;
                case 6:
                    c = new Color(24, 82, 177);
                    break;
                case 7:
                    c = new Color(57, 125, 209);
                    break;
                case 8:
                    c = new Color(134, 181, 229);
                    break;
                case 9:
                    c = new Color(211, 236, 248);
                    break;
                case 10:
                    c = new Color(241, 233, 191);
                    break;
                case 11:
                    c = new Color(248, 201, 95);
                    break;
                case 12:
                    c = new Color(255, 170, 0);
                    break;
                case 13:
                    c = new Color(204, 128, 0);
                    break;
                case 14:
                    c = new Color(153, 87, 0);
                    break;
                case 15:
                    c = new Color(106, 52, 3);
                    break;
                default:
                    c = new Color(255, 255, 255);
                    break;
            }
            return c;
        } else if (iter == maxIterations) {
            return new Color(255, 255, 255);
        } else {
            return new Color(0, 0, 0);
        }
    }

    public void panImage() {
        double difX = getEndMouseX() - getStartMouseX();
        double difY = getEndMouseY() - getStartMouseY();

        setPanX(difX / (Math.log(zoom)));
        setPanY(difY / (Math.log(zoom)));
        setXCenter((int) (xCenter + panX), false);
        setYCenter((int) (yCenter + panY), false);
        imageRedrawn = true;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        try {
            if (imageRedrawn) {
                setImage(new MandelModel().drawFractal(getWidth(), getHeight(),
                        getIterations(), this));
                imageRedrawn = false;
            }
            g.drawImage(getImage(), 0, 0, this);
			// Graphics2D g2d = (Graphics2D) g;
            // g2d.setTransform(transformer);

            if (isOnZoomMode()) {
                Color zoomColour = new Color(255, 255, 255, 100);
                g.setColor(zoomColour);
                int topLeftX = (int) Math.min(getStartMouseX(), getEndMouseX());
                int topLeftY = (int) Math.min(getStartMouseY(), getEndMouseY());
                int botRightX = (int) Math
                        .max(getStartMouseX(), getEndMouseX());
                int botRightY = (int) Math
                        .max(getStartMouseY(), getEndMouseY());
                int difX = botRightX - topLeftX;
                int difY = botRightY - topLeftY;
                g.fillRect(topLeftX, topLeftY, difX, difY);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(MandelController.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!isThumbnail()) {
            if (!isJuliaSet()) {
                Complex point = new Complex(
                        ((float) (e.getX() - getXCenter()) / getZoom()),
                        ((float) -(e.getY() - getYCenter()) / getZoom()));
                view.setComplex(point);
            }
            getTransformer().translate(5, 5);
            imageRedrawn = true;
            this.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setStartMouseX(e.getX());
        setStartMouseY(e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (onZoomMode) {
            setEndMouseX(e.getX());
            setEndMouseY(e.getY());
            // Find the difference
            int topLeftX = (int) Math.min(getStartMouseX(), getEndMouseX());
            int topLeftY = (int) Math.min(getStartMouseY(), getEndMouseY());
            int botRightX = (int) Math.max(getStartMouseX(), getEndMouseX());
            int botRightY = (int) Math.max(getStartMouseY(), getEndMouseY());
            int difX = botRightX - topLeftX;
            int difY = botRightY - topLeftY;

            // Work out the center offset
            double offsetX = topLeftX + (difX / 2);
            double offsetY = topLeftY + (difY / 2);
            System.out.println("X difference: " + difX + " Y difference: "
                    + difY);
            
            //Add the center offset
            setXCenter((int) (offsetX), false);
            setYCenter((int) (offsetY), false);

            int incrZoom = 0;
            zoom += incrZoom;

            imageRedrawn = true;
            this.repaint();
            resetMouseXY();
        }
    }

    public void resetMouseXY() {
        setStartMouseX(0);
        setStartMouseY(0);
        setEndMouseX(0);
        setEndMouseY(0);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
		// throw new UnsupportedOperationException("Not supported yet."); //To
        // change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
		// throw new UnsupportedOperationException("Not supported yet."); //To
        // change body of generated methods, choose Tools | Templates.
    }

    public void goLeft(double amount) {
        setXCenter(getXCenter() + amount);
    }

    public void goRight(double amount) {
        setXCenter(getXCenter() - amount);
    }

    public void goUp(double amount) {
        setYCenter(getYCenter() + amount);
    }

    public void goDown(double amount) {
        setYCenter(getYCenter() - amount);
    }

    /**
     * @return the juliaSet
     */
    public boolean isJuliaSet() {
        return juliaSet;
    }

    /**
     * @param juliaSet the juliaSet to set
     */
    public void setJuliaSet(boolean juliaSet) {
        this.juliaSet = juliaSet;
    }

    /**
     * @return the fixed
     */
    public Complex getFixed() {
        return fixed;
    }

    /**
     * @param fixed the fixed to set
     */
    public void setFixed(Complex fixed) {
        setFixed(fixed, true);
    }

    /**
     * @param fixed the fixed to set
     */
    public void setFixed(Complex fixed, boolean doRepaint) {
        this.fixed = fixed;
        if (doRepaint) {
            imageRedrawn = true;
            this.repaint();
        }
    }

    /**
     * @return the zoom
     */
    public int getZoom() {
        return zoom;
    }

    public void setZoom(int currentZoom) {
        setZoom(currentZoom, true);
    }

    /**
     * @param currentZoom the zoom to set
     */
    public void setZoom(int currentZoom, boolean doRepaint) {
        this.zoom = currentZoom;
        if (doRepaint) {
            imageRedrawn = true;
            this.repaint();
        }
    }

    public void zoomIn() {
        zoom += zoom * 0.1;
        imageRedrawn = true;
        this.repaint();
    }

    public void zoomOut() {
        zoom -= zoom * 0.1;
        imageRedrawn = true;
        this.repaint();
    }

    /**
     * @return the xCenter
     */
    public double getXCenter() {
        return xCenter;
    }

    public void setXCenter(double currentXCenter) {
        setXCenter(currentXCenter, true);
    }

    /**
     * @param currentXCenter the xCenter to set
     */
    public void setXCenter(double currentXCenter, boolean doRepaint) {
        this.xCenter = currentXCenter;
        if (doRepaint) {
            imageRedrawn = true;
            this.repaint();
        }
    }

    /**
     * @return the yCenter
     */
    public double getYCenter() {
        return yCenter;
    }

    public void setYCenter(double currentYCenter) {
        setYCenter(currentYCenter, true);
    }

    /**
     * @param currentYCenter the yCenter to set
     */
    public void setYCenter(double currentYCenter, boolean doRepaint) {
        this.yCenter = currentYCenter;
        if (doRepaint) {
            imageRedrawn = true;
            this.repaint();
        }
    }

    /**
     * @return the imageDrawn
     */
    public boolean isImageDrawn() {
        return imageDrawn;
    }

    /**
     * @param imageDrawn the imageDrawn to set
     */
    public void setImageDrawn(boolean imageDrawn) {
        this.imageDrawn = imageDrawn;
    }

    /**
     * @return the iterations
     */
    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        setIterations(iterations, true);
    }

    /**
     * @param iterations the iterations
     * @param doRepaint
     */
    public void setIterations(int iterations, boolean doRepaint) {
        this.iterations = iterations;
        if (doRepaint) {
            imageRedrawn = true;
            this.repaint();
        }
    }

    /**
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(BufferedImage image) {
        this.image = image;
        imageRedrawn = true;
        this.repaint();
    }

    /**
     * @return the onZoomMode
     */
    public boolean isOnZoomMode() {
        return onZoomMode;
    }

    /**
     * @param onZoomMode the onZoomMode to set
     */
    public void setOnZoomMode(boolean onZoomMode) {
        this.onZoomMode = onZoomMode;
    }

    /**
     * @return the startMouseX
     */
    public double getStartMouseX() {
        return startMouseX;
    }

    /**
     * @param startMouseX the startMouseX to set
     */
    public void setStartMouseX(double startMouseX) {
        this.startMouseX = startMouseX;
    }

    /**
     * @return the startMouseY
     */
    public double getStartMouseY() {
        return startMouseY;
    }

    /**
     * @param startMouseY the startMouseY to set
     */
    public void setStartMouseY(double startMouseY) {
        this.startMouseY = startMouseY;
    }

    /**
     * @return the endMouseX
     */
    public double getEndMouseX() {
        return endMouseX;
    }

    /**
     * @param endMouseX the endMouseX to set
     */
    public void setEndMouseX(double endMouseX) {
        this.endMouseX = endMouseX;
    }

    /**
     * @return the endMouseY
     */
    public double getEndMouseY() {
        return endMouseY;
    }

    /**
     * @param endMouseY the endMouseY to set
     */
    public void setEndMouseY(double endMouseY) {
        this.endMouseY = endMouseY;
    }

    /**
     * @return the panX
     */
    public double getPanX() {
        return panX;
    }

    /**
     * @param panX the panX to set
     */
    public void setPanX(double panX) {
        this.panX = panX;
    }

    /**
     * @return the panY
     */
    public double getPanY() {
        return panY;
    }

    /**
     * @param panY the panY to set
     */
    public void setPanY(double panY) {
        this.panY = panY;
    }

    /**
     * @return the transformer
     */
    public AffineTransform getTransformer() {
        return transformer;
    }

    /**
     * @param transformer the transformer to set
     */
    public void setTransformer(AffineTransform transformer) {
        this.transformer = transformer;
    }

    /**
     * @return the thumbnail
     */
    public boolean isThumbnail() {
        return thumbnail;
    }

    /**
     * @param thumbnail the thumbnail to set
     */
    public void setThumbnail(boolean thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("Scroll amount " + e.getUnitsToScroll());
        int zoomAmount = e.getUnitsToScroll() * (int) (zoom / Math.log(zoom));
        zoom -= zoomAmount;
        System.out.println("Zoom " + zoom);
        System.out.println("Position " + e.getX() + " Y " + e.getY());
        setXCenter(xCenter + getWidth()/2, false);
        setYCenter(yCenter + getHeight()/2);
        imageRedrawn = true;
        this.repaint();
    }

}
