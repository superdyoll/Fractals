/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.Controller;

import Fractals.Maths.Complex;
import Fractals.View.MandelView;
import Fractals.Model.MandelModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class MandelController extends JPanel implements MouseListener, KeyListener{

    private final MandelView view;
    private BufferedImage I;
    private int zoom, xCenter, yCenter;
    private boolean juliaSet, mousePressed;
    private Complex fixed;
    private JButton btnZoomIn = new JButton("Zoom In");
    private JButton btnZoomOut = new JButton("Zoom Out");

    public MandelController(MandelView controller) {
        this(controller, false, 150);
    }

    public MandelController(MandelView controller, boolean juliaSet) {
        this(controller, juliaSet, 150);
    }

    public MandelController(MandelView controller, boolean juliaSet, int zoom) {
        this.juliaSet = juliaSet;
        setCurrentZoom(zoom);
        setBounds(100, 100, 800, 600);

        //Make a navigation panel        
        btnZoomIn.addMouseListener(this);
        btnZoomOut.addMouseListener(this);

        JLayeredPane pnlNav = new JLayeredPane();
        pnlNav.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pnlNav.add(btnZoomIn);
        pnlNav.add(btnZoomOut);
        this.add(pnlNav, BorderLayout.NORTH);
        pnlNav.setVisible(true);

        addMouseListener(this);
        addKeyListener(this);
        view = controller;
        fixed = new Complex(0, 0);
    }

    public BufferedImage drawFractal(int width, int height, int maxIterations) {

        //Just some debugging parts
        System.out.println("Drawing mandel");
        System.out.println("width: " + width + "height: " + height);

        //Make a new model
        MandelModel newMandel = new MandelModel();

        //Create the new image for double buffering
        BufferedImage graph = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        System.out.println("Image created");

        //Do the calculation
        //Go through height
        for (int y = 0; y < height; y++) {
            //Go through width
            for (int x = 0; x < width; x++) {
                //Make new Complex point
                Complex point = new Complex(((float) (x - xCenter) / getCurrentZoom()), ((float) (y - yCenter) / getCurrentZoom()));
                //Work out the iterations taken diverge
                int iter;
                if (isJuliaSet()) {
                    iter = newMandel.calculateJuliaPoint(point, getFixed(), maxIterations);
                } else {
                    iter = newMandel.calculateMandelPoint(point, maxIterations);
                }
                //Get the colour from the colour map
                Color c = getColour(maxIterations, iter);
                //Get the RGB
                int rgb = c.getRGB();
                //Add the point on the image
                graph.setRGB(x, y, rgb);
            }
        }
        System.out.println("For loops finished");
        return graph;
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
        xCenter = getWidth() / 2;
        yCenter = getHeight() / 2;
        I = MandelController.this.drawFractal(getWidth(), getHeight(), 570);
        g.drawImage(I, 0, 0, this);
        System.out.println("Drawn");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!isJuliaSet()) {
            Complex point = new Complex(((float) (e.getX() - getCurrentXCenter()) / getCurrentZoom()), ((float) (e.getY() - getCurrentYCenter()) / getCurrentZoom()));
            System.out.println("Pont Clicked" + point);
            view.setComplex(point);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        Object src = e.getSource();
        new Thread() {
            public void run() {
                while (mousePressed) {
                    if (src == btnZoomIn) {
                        zoom += 10;
                    } else if (src == btnZoomOut) {
                        zoom -= 10;
                    }
                    repaint();
                    try {
                        sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MandelController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }.start();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
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
        System.out.println("Key Typed: " + e.getKeyChar());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed: " + e.getKeyChar());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key Released: " + e.getKeyChar());
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public int getCurrentZoom() {
        return zoom;
    }

    /**
     * @param currentZoom the zoom to set
     */
    public void setCurrentZoom(int currentZoom) {
        this.zoom = currentZoom;
    }

    /**
     * @return the xCenter
     */
    public int getCurrentXCenter() {
        return xCenter;
    }

    /**
     * @param currentXCenter the xCenter to set
     */
    public void setCurrentXCenter(int currentXCenter) {
        this.xCenter = currentXCenter;
    }

    /**
     * @return the yCenter
     */
    public int getCurrentYCenter() {
        return yCenter;
    }

    /**
     * @param currentYCenter the yCenter to set
     */
    public void setCurrentYCenter(int currentYCenter) {
        this.yCenter = currentYCenter;
    }

    /*@Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnZoomIn) {
            zoom += 10;
        } else if (src == btnZoomOut) {
            zoom -= 10;
        }
        repaint();
    }*/

}
