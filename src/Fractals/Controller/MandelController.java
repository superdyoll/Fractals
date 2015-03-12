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
public class MandelController extends JPanel implements MouseListener, KeyListener {

    private final MandelView view;
    private BufferedImage image;
    private int zoom, xCenter, yCenter, iterations;
    private boolean juliaSet, imageDrawn, onZoomMode;
    private Complex fixed;
    private double startMouseX, startMouseY, endMouseX, endMouseY, panX, panY;
    private AffineTransform transformer = new AffineTransform();

    public MandelController(MandelView controller) {
        this(controller, false, 150);
    }

    public MandelController(MandelView controller, boolean juliaSet) {
        this(controller, juliaSet, 150);
    }

    public MandelController(MandelView controller, boolean juliaSet, int zoom) {
        iterations = 570;

        this.juliaSet = juliaSet;
        setZoom(zoom);
        //setBounds(100, 100, 800, 600);

        addMouseListener(this);
        addKeyListener(this);
        view = controller;
        fixed = new Complex(0, 0);
    }

    public void initialiseXY() {
        initialiseXY(true);
    }

    public void initialiseXY(boolean doRepaint) {
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
    
    public void panImage(){
        AffineTransform panTranslator = new AffineTransform();
        double difX = getEndMouseX() - getStartMouseX();
        double difY = getEndMouseY() - getStartMouseY();
        //setPanX(getPanX() + difX/zoom);
        //setPanY(getPanY() + difY/zoom);
        System.out.println(panX + " " + panY);
        //panTranslator.setToTranslation(getPanX(),getPanY());
        //transformer.concatenate(panTranslator);
        
        setPanX(difX/zoom);
        setPanY(difY/zoom);
        setXCenter((int) (xCenter + panX));
        setYCenter((int) (yCenter + panY));
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        setImage(new MandelModel().drawFractal(getWidth(), getHeight(), getIterations(), this));
        g.drawImage(getImage(), 0, 0, this);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setTransform(transformer);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!isJuliaSet()) {
            Complex point = new Complex(((float) (e.getX() - getXCenter()) / getZoom()), ((float) (e.getY() - getYCenter()) / getZoom()));
            view.setComplex(point);
        }
        getTransformer().translate(5,5);
        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setStartMouseX(e.getX());
        setStartMouseY(e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setEndMouseX(e.getX());
        setEndMouseY(e.getY());
        double difX = getEndMouseX() - getStartMouseX();
        double difY = getEndMouseY() - getStartMouseY();
        setPanX(difX/zoom);
        setPanY(difY/zoom);
        System.out.println("X difference: " + difX + " Y difference: " + difY);
        double centerX = getXCenter() + panX;
        double centerY = getYCenter() + panY;
        System.out.println("X center " + centerX + " Y center " + centerY);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Key Typed");
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            goUp(1);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            goDown(1);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            goRight(1);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            goLeft(1);
        }
    }

    public void goLeft(int amount) {
        setXCenter(getXCenter() + amount);
    }

    public void goRight(int amount) {
        setXCenter(getXCenter() - amount);
    }

    public void goUp(int amount) {
        setYCenter(getYCenter() + amount);
    }

    public void goDown(int amount) {
        setYCenter(getYCenter() - amount);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed: " + e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key Released: " + e.getKeyCode());
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
            this.repaint();
        }
    }

    public void zoomIn() {
        zoom += zoom * 0.1;
        this.repaint();
    }

    public void zoomOut() {
        zoom -= zoom * 0.1;
        this.repaint();
    }

    /**
     * @return the xCenter
     */
    public int getXCenter() {
        return xCenter;
    }

    public void setXCenter(int currentXCenter) {
        setXCenter(currentXCenter, true);
    }

    /**
     * @param currentXCenter the xCenter to set
     */
    public void setXCenter(int currentXCenter, boolean doRepaint) {
        this.xCenter = currentXCenter;
        if (doRepaint) {
            this.repaint();
        }
    }

    /**
     * @return the yCenter
     */
    public int getYCenter() {
        return yCenter;
    }

    public void setYCenter(int currentYCenter) {
        setYCenter(currentYCenter, true);
    }

    /**
     * @param currentYCenter the yCenter to set
     */
    public void setYCenter(int currentYCenter, boolean doRepaint) {
        this.yCenter = currentYCenter;
        if (doRepaint) {
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

}
