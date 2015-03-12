package Fractals.Controller;

import Fractals.Maths.Complex;
import Fractals.View.MandelView;
import Fractals.Model.MandelModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
public class MandelController extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private final MandelView view;
    private BufferedImage image;
    private int zoom, xCenter, yCenter, iterations;
    private boolean juliaSet, imageDrawn, onZoomMode;
    private Complex fixed;

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
    
    public void initialiseXY (){
        xCenter = this.getWidth() / 2;
        yCenter = this.getHeight() / 2;
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

    @Override
    public void paint(Graphics g) {
        setImage(new MandelModel().drawFractal(getWidth(), getHeight(), getIterations(), this));
        g.drawImage(getImage(), 0, 0, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!isJuliaSet()) {
            Complex point = new Complex(((float) (e.getX() - getXCenter()) / getZoom()), ((float) (e.getY() - getYCenter()) / getZoom()));
            view.setComplex(point);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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
        this.fixed = fixed;
    }

    /**
     * @return the zoom
     */
    public int getZoom() {
        return zoom;
    }

    /**
     * @param currentZoom the zoom to set
     */
    public void setZoom(int currentZoom) {
        this.zoom = currentZoom;
    }

    public void zoomIn() {
        zoom += zoom * 0.1;
    }

    public void zoomOut() {
        zoom -= zoom * 0.1;
    }

    /**
     * @return the xCenter
     */
    public int getXCenter() {
        return xCenter;
    }

    /**
     * @param currentXCenter the xCenter to set
     */
    public void setXCenter(int currentXCenter) {
        this.xCenter = currentXCenter;
    }

    /**
     * @return the yCenter
     */
    public int getYCenter() {
        return yCenter;
    }

    /**
     * @param currentYCenter the yCenter to set
     */
    public void setYCenter(int currentYCenter) {
        this.yCenter = currentYCenter;
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

    /**
     * @param iterations the iterations to set
     */
    public void setIterations(int iterations) {
        this.iterations = iterations;
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
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
