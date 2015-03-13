/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals.View;

import Fractals.Controller.MandelController;
import Fractals.Maths.Complex;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
public class MandelView implements MouseListener, KeyListener, MouseMotionListener, ActionListener {

    private final JLabel lblComplex = new JLabel();
    private MandelController pnlJulia, pnlMandel;

    private JPanel pnlFavourites;
    private JButton btnSet, btnExport, btnReset, btnSave;
    private JToggleButton btnZoomMode, btnShowJulia, btnShowFavourites;
    private JTextField txtIter;
    private boolean mousePressed, imageAdded = false;
    private FavButton latestButton;

    /**
     * Turn the image into a file
     *
     * filename denotes what the file should be called
     *
     * @param filename
     * @throws IOException
     */
    public void exportJulia(String filename) throws IOException {
        File outputfile = new File(filename + ".jpg");
        ImageIO.write(pnlJulia.getImage(), "jpg", outputfile);
    }

    public void saveAsFavourite(Complex fixed, int zoom) {
        FavButton favourite = getFavButton(fixed, zoom);
        favourite.addActionListener(this);
        pnlFavourites.add(favourite);
    }

    public FavButton getFavButton(Complex fixed, int zoom) {
        FavButton favourite = new FavButton("Julia " + fixed + " at zoom level " + zoom, fixed, zoom);
        favourite.setActionCommand("favourite");
        return favourite;
    }

    public void setComplex(Complex point) {
        lblComplex.setText(point.toString());
        pnlJulia.setFixed(point);
    }

    /**
     * Draw all the main frame objects
     *
     */
    public void drawFrame() {
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
        this.btnSet = new JButton("Set");
        this.btnExport = new JButton("Export");
        this.btnSave = new JButton("Save to favourites");
        this.btnReset = new JButton("Reset everything");
        this.btnZoomMode = new JToggleButton("Zoom Mode");
        this.btnShowJulia = new JToggleButton("Show Julia");
        this.btnShowFavourites = new JToggleButton("Show Favourites");
        this.txtIter = new JTextField("570", 5);

        //Add Mouse Listeners
        btnSave.addMouseListener(this);
        btnSet.addMouseListener(this);
        btnExport.addMouseListener(this);
        btnReset.addMouseListener(this);

        btnZoomMode.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (ev.getStateChange() == ItemEvent.SELECTED) {
                    btnZoomMode.setText("Pan Mode");
                    pnlMandel.setOnZoomMode(true);
                    pnlMandel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    pnlJulia.setOnZoomMode(true);
                    pnlJulia.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
                    btnZoomMode.setText("Zoom Mode");
                    pnlMandel.setOnZoomMode(false);
                    pnlMandel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    pnlJulia.setOnZoomMode(false);
                    pnlJulia.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
        });

        JPanel pnlControl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlControl.setOpaque(false);
        pnlControl.add(txtIter);
        pnlControl.add(btnSet);

        JPanel pnlNav = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlNav.setOpaque(false);
        pnlNav.add(pnlControl);
        pnlNav.add(btnReset);
        pnlNav.add(btnZoomMode);
        pnlNav.add(btnShowJulia);
        pnlNav.add(btnShowFavourites);

        layers.add(pnlNav, BorderLayout.NORTH, 0);
        pnlNav.setVisible(true);

        //Make mandel panel
        pnlMandel = new MandelController(this);
        pnlMandel.setIterations(570);
        layers.add(pnlMandel, BorderLayout.CENTER, 1);
        pnlMain.add(layers, BorderLayout.CENTER);
        frmOuter.addKeyListener(this);
        pnlMandel.setVisible(true);
        pnlMandel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlMandel.addMouseMotionListener(this);
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
        final JFrame frmJulia = new JFrame("Julia Set");
        frmJulia.setBounds(100, 100, 400, 300);

        btnShowJulia.setSelected(true);
        btnShowJulia.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (ev.getStateChange() == ItemEvent.SELECTED) {
                    frmJulia.setVisible(true);
                } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
                    frmJulia.setVisible(false);
                }
            }
        });

        Container pnlJuliaMain = frmJulia.getContentPane();

        //Make mandel panel
        pnlJulia = new MandelController(this, true, 50);
        pnlJuliaMain.add(pnlJulia, BorderLayout.CENTER);
        pnlJulia.addMouseMotionListener(this);
        pnlJulia.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlJulia.setVisible(true);

        //MakeSave panel
        JPanel pnlSave = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlSave.add(btnExport);
        pnlSave.add(btnSave);
        pnlJuliaMain.add(pnlSave, BorderLayout.NORTH);

        frmJulia.setVisible(true);

        //Make Favourites Panel
        final JFrame frmFavourites = new JFrame("Favourites");
        frmFavourites.setBounds(100, 100, 400, 300);

        btnShowFavourites.setSelected(false);
        btnShowFavourites.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (ev.getStateChange() == ItemEvent.SELECTED) {
                    frmFavourites.setVisible(true);
                } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
                    frmFavourites.setVisible(false);
                }
            }
        });

        pnlFavourites = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlFavourites.setBounds(100, 100, 400, 300);
        pnlFavourites.setVisible(true);

        JScrollPane scroller = new JScrollPane();
        scroller.add(pnlFavourites);
        scroller.setVisible(true);

        FavButton testButton = new FavButton("Test", new Complex(0,0), 100);
        testButton.setVisible(true);
        testButton.setActionCommand("favourite");
        testButton.addActionListener(this);
        pnlFavourites.add(testButton);
        pnlJuliaMain.add(pnlFavourites, BorderLayout.SOUTH);
        
        
        Container pnlFavMain = frmFavourites.getContentPane();
        pnlFavMain.add(pnlFavourites);
        pnlFavMain.setVisible(true);

        pnlMandel.initialiseXY();
        pnlJulia.initialiseXY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        final Object src = e.getSource();
        new Thread() {
            @Override
            public void run() {
                if (src == btnExport) {
                    Calendar calendar = new GregorianCalendar();
                    String time = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + 1 + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "_" + calendar.get(Calendar.MILLISECOND);
                    System.out.println(time);
                    try {
                        exportJulia("Julia_" + time);
                    } catch (IOException ex) {
                        Logger.getLogger(MandelView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (src == btnReset) {
                    pnlMandel.initialiseXY(false);
                    pnlMandel.setZoom(100);
                    pnlJulia.initialiseXY(false);
                    pnlJulia.setZoom(100);
                    txtIter.setText("570");
                    setIterations();
                } else if (src == btnSave) {
                    System.out.println("Saving " + pnlJulia.getFixed());
                    latestButton = MandelView.this.getFavButton(pnlJulia.getFixed(), pnlJulia.getZoom());
                    imageAdded = true;
                } else if (src == btnSet) {
                    System.out.println("Setting " + txtIter.getText());
                    setIterations();
                }
            }

        }.start();
        if (imageAdded) {
            imageAdded = false;
            pnlFavourites.add(latestButton);
            latestButton.addActionListener(this);
            latestButton.setVisible(true);
            System.out.println("Added button " + latestButton);
        }
    }

    public void setIterations() {
        int iterations = Integer.parseInt(txtIter.getText());
        pnlMandel.setIterations(iterations);
        pnlJulia.setIterations(iterations);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //mousePressed = true;

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //mousePressed = false;
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

    @Override
    public void mouseDragged(MouseEvent e) {
        Object src = e.getSource();
        //Check the object clicked on is a fractal
        if (src instanceof MandelController) {
            //If it is cast it as one to enable methods
            MandelController mdlControl = (MandelController) src;
            //See if zoom mode is enabled
            mdlControl.setEndMouseX(e.getX());
            mdlControl.setEndMouseY(e.getY());
            if (!mdlControl.isOnZoomMode()) {
                mdlControl.panImage();
            }else{
                mdlControl.repaint();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("favourite")) {
            Object src = e.getSource();
            if (src instanceof FavButton) {
                FavButton favourite = (FavButton) src;
                pnlJulia.setFixed(favourite.getFixed());
                pnlJulia.setZoom(favourite.getZoom());
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MandelView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MandelView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MandelView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MandelView.class.getName()).log(Level.SEVERE, null, ex);
        }
        MandelView mandel = new MandelView();
        mandel.drawFrame();
    }
}
