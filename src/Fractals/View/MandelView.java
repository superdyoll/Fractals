/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.View;

import Fractals.Controller.MandelController;
import Fractals.Maths.Complex;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Lloyd
 */
public class MandelView implements MouseListener, KeyListener {

    private final JLabel lblComplex = new JLabel();
    private MandelController pnlJulia, pnlMandel;

    private JButton btnZoomIn, btnZoomOut, btnLeft, btnRight, btnUp, btnDown, btnSave;
    private boolean mousePressed;

    public void saveJulia(String filename) throws IOException {
        File outputfile = new File(filename + ".jpg");
        ImageIO.write(pnlJulia.getImage(), "jpg", outputfile);
    }

    public void setComplex(Complex point) {
        lblComplex.setText(point.toString());
        pnlJulia.setFixed(point);
        pnlJulia.repaint();
    }

    public void drawStuff() {
        //Basic Frame stuff
        JFrame frmOuter = new JFrame("Mandlebrot Set");
        frmOuter.setBounds(100, 100, 800, 600);
        frmOuter.setDefaultCloseOperation(frmOuter.EXIT_ON_CLOSE);

        Container pnlMain = frmOuter.getContentPane();
        pnlMain.setBounds(100, 100, frmOuter.getWidth(), frmOuter.getHeight());

        JLayeredPane layers = new JLayeredPane();
        layers.setOpaque(false);
        layers.setLayout(new BorderLayout());
        layers.setBounds(100, 100, frmOuter.getWidth(), frmOuter.getHeight());

        //Create all Buttons
        this.btnZoomOut = new JButton("Zoom Out");
        this.btnZoomIn = new JButton("Zoom In");
        this.btnUp = new JButton("Up");
        this.btnDown = new JButton("Down");
        this.btnLeft = new JButton("Left");
        this.btnRight = new JButton("Right");
        this.btnSave = new JButton("Save");

        //Add Mouse Listeners        
        btnZoomIn.addMouseListener(this);
        btnZoomOut.addMouseListener(this);
        btnUp.addMouseListener(this);
        btnDown.addMouseListener(this);
        btnLeft.addMouseListener(this);
        btnRight.addMouseListener(this);
        btnSave.addMouseListener(this);

        JPanel pnlZoom = new JPanel();
        pnlZoom.setOpaque(false);
        pnlZoom.setLayout(new GridLayout(2, 1));
        pnlZoom.add(btnZoomIn);
        pnlZoom.add(btnZoomOut);

        JPanel pnlControl = new JPanel(new BorderLayout());
        pnlControl.setOpaque(false);
        pnlControl.add(btnUp, BorderLayout.NORTH);
        pnlControl.add(btnDown, BorderLayout.SOUTH);
        pnlControl.add(btnLeft, BorderLayout.WEST);
        pnlControl.add(btnRight, BorderLayout.EAST);
        pnlControl.add(pnlZoom, BorderLayout.CENTER);

        JPanel pnlNav = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlNav.setOpaque(false);
        pnlNav.add(pnlControl);

        layers.add(pnlNav, BorderLayout.NORTH, 0);
        btnZoomIn.setVisible(true);
        btnZoomOut.setVisible(true);
        pnlNav.setVisible(true);

        //Make mandel panel
        pnlMandel = new MandelController(this);
        pnlMandel.setIterations(570);
        layers.add(pnlMandel, BorderLayout.CENTER, 1);
        pnlMain.add(layers, BorderLayout.CENTER);
        frmOuter.addKeyListener(this);
        pnlMandel.setVisible(true);
        layers.setVisible(true);

        lblComplex.setText("Click on a point to view the complex number");

        //Make complex point bit
        JPanel pnlComplex = new JPanel();
        pnlComplex.setBounds(100, 100, frmOuter.getWidth(), (int) (frmOuter.getHeight() * 0.01));
        pnlComplex.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlComplex.add(lblComplex);
        pnlMain.add(pnlComplex, BorderLayout.SOUTH);
        pnlComplex.setVisible(true);

        //frmOuter.add(pnlMain, BorderLayout.CENTER);
        //frmOuter.pack();
        frmOuter.setVisible(true);

        //Make Julia frame
        JFrame frmJulia = new JFrame("Julia Set");
        frmJulia.setBounds(100, 100, 400, 300);

        Container pnlJuliaMain = frmJulia.getContentPane();

        //Make mandel panel
        pnlJulia = new MandelController(this, true, 50);
        pnlJuliaMain.add(pnlJulia, BorderLayout.CENTER);
        pnlJulia.setVisible(true);

        //MakeSave panel
        JPanel pnlSave = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlSave.add(btnSave);
        pnlJuliaMain.add(pnlSave, BorderLayout.NORTH);

        frmJulia.setVisible(true);
    }

    public static void main(String[] args) {
        MandelView mandel = new MandelView();
        mandel.drawStuff();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        final Object src = e.getSource();
        new Thread() {
            @Override
            public void run() {
                if (src == btnSave) {
                    Calendar calendar = new GregorianCalendar();
                    String time = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + 1 + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "_" + calendar.get(Calendar.MILLISECOND);
                    System.out.println(time);
                    try {
                        saveJulia("Julia_" + time);
                    } catch (IOException ex) {
                        Logger.getLogger(MandelView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }.start();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        final Object src = e.getSource();
        new Thread() {
            @Override
            public void run() {
                while (mousePressed) {
                    if (src == btnZoomIn) {
                        pnlMandel.zoomIn();
                        System.out.println(pnlMandel.getZoom());
                    } else if (src == btnZoomOut) {
                        pnlMandel.zoomOut();
                    } else if (src == btnUp) {
                        pnlMandel.goUp(1);
                    } else if (src == btnDown) {
                        pnlMandel.goDown(1);
                    } else if (src == btnLeft) {
                        pnlMandel.goLeft(1);
                    } else if (src == btnRight) {
                        pnlMandel.goRight(1);
                    }
                    pnlMandel.setImageDrawn(false);
                    pnlMandel.repaint();
                    while (!pnlMandel.isImageDrawn()) {
                        try {
                            sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MandelController.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
        System.out.println("Key Typed: " + e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed: " + e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key Released: " + e.getKeyCode());
    }

}
