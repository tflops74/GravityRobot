/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotgravity;

/**
 *
 * @author Shun Baek  (BAEK Sung Hoon 백승훈)
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;


class MainPanel extends JPanel implements MouseListener, MouseMotionListener, Runnable {
    
    public Spacetime m_SpaceTime;
    public MovingRobot m_MovingObject;
    public SpacetimeFrame m_SpacetimeFrame;
    Image m_Image;
    //Image m_RobotImage;
    int m_nWidth;
    int m_nHeight;
    public Graphics m_GraphicsOnImage;
    public volatile boolean m_bRun=false;
    
    private Thread m_Thread;
    
    public MainPanel(SpacetimeFrame frame) {
        m_SpacetimeFrame = frame;
        m_SpaceTime = m_SpacetimeFrame.mSpaceTime;
        m_MovingObject = m_SpacetimeFrame.mMovingObject;
    }
     public void Init() {
        Toolkit toolkit = getToolkit();
        //m_RobotImage = toolkit.getImage("robot.png");
        addMouseListener(this);
        addMouseMotionListener(this);
       
        m_nWidth = getSize().width;
        m_nHeight = getSize().height;
        m_Image = createImage(m_nWidth, m_nHeight);
	m_GraphicsOnImage = m_Image.getGraphics();
        m_GraphicsOnImage.setColor(Color.WHITE);
        m_GraphicsOnImage.fillRect(0,0,m_nWidth, m_nHeight);
        for (int i=0; i<m_nWidth-(int)m_SpaceTime.dStarRadius; i+=3) {
            int grey = 255-i*255/m_nWidth;
            m_GraphicsOnImage.setColor(new Color(grey,grey,grey));
            m_GraphicsOnImage.fillOval(i,i, m_nWidth-i*2,m_nHeight-i*2);
        }
        
        m_GraphicsOnImage.setColor(Color.RED);
        m_GraphicsOnImage.fillOval((int)m_SpaceTime.dStarX - (int)m_SpaceTime.dStarRadius, (int)m_SpaceTime.dStarY - (int)m_SpaceTime.dStarRadius, 
                (int)m_SpaceTime.dStarRadius*2, (int)m_SpaceTime.dStarRadius*2 );
        /*
        m_LifeGame = new LifeGame(this, image);
        m_LifeGame.Init(m_GraphicsOnImage,m_nWidth, m_nHeight); */
        
        repaint();
    }

    public void Reset() {
        //m_LifeGame.Reset();
    }

    public void paint(Graphics g) // overriding
    {
        update(g);
    }

    public void update(Graphics g) // overriding
    {

        g.drawImage(m_Image, 0, 0, this);
        drawObject(g, (int)m_MovingObject.x, RobotGravity.SpaceHeight-1-(int)m_MovingObject.y);
        
        //g.drawImage(m_RobotImage, (int)m_MovingObject.x, RobotGravity.SpaceSize-1-(int)m_MovingObject.y, this);
    }

    public void Start()
    {
        if (m_bRun == false) {
            m_bRun = true;
            m_Thread = new Thread(this);
            m_Thread.start();
            repaint();
        }
    }
    
    public void Stop()
    {
        m_bRun = false;
        repaint();
    }
    
    
    public void drawObject(Graphics g, int x, int y) {
        int radius = 5;
        g.setColor(Color.BLUE);
        g.fillOval(x-radius, y-radius, radius*2, radius*2);
    
    }
    public void run() {
        while (m_bRun) {
            for (int i=0; i<100; i++)
                m_MovingObject.move(0.5, this);
            repaint();
            try {
                Thread.sleep(2);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void mousePressed(MouseEvent e)
    {
    }

    public void mouseReleased(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mouseClicked(MouseEvent e)
    {
        m_MovingObject.x = e.getX();
        m_MovingObject.y = m_nHeight - 1 - e.getY();
        
        repaint();
    }

    public void mouseDragged(MouseEvent e)
    {

    }

    public void mouseMoved(MouseEvent e)
    {
    }

}

class StepAction implements ActionListener {
    private SpacetimeFrame m_Frame;
    public StepAction(SpacetimeFrame frame)  {
        m_Frame = frame;
    }
    public void actionPerformed(ActionEvent e) {
        m_Frame.Start();
    }
}


class ResetAction implements ActionListener {
    private SpacetimeFrame m_Frame;
    public ResetAction(SpacetimeFrame frame)  {
        m_Frame = frame;
    }
    public void actionPerformed(ActionEvent e) {
        m_Frame.Stop();

    }
}


class ControlPanel extends JPanel {
    private JButton m_StepButton;
    private JButton m_ResetButton;
    private JLabel m_Label;
    private SpacetimeFrame m_Frame;

    public ControlPanel(SpacetimeFrame frame) {
        m_StepButton = new JButton("Start");
        m_StepButton.addActionListener(new StepAction(frame));
        m_ResetButton = new JButton("Stop");
        m_ResetButton.addActionListener(new ResetAction(frame));
        add(m_StepButton);
        add(m_ResetButton);
        m_Frame = frame;
    }
    public void SetLabel(String str) {
        m_Label.setText(str);
    }
}
        


public class SpacetimeFrame extends JFrame {
    public MainPanel m_MainPanel;
    public ControlPanel m_ControlPanel;
    public Spacetime mSpaceTime;
    public MovingRobot mMovingObject;

    public SpacetimeFrame(String str, Spacetime spacetime, MovingRobot object) {
        super(str);
        mSpaceTime = spacetime;
        mMovingObject = object;
        Container contentPane = getContentPane();
        m_MainPanel = new MainPanel(this);
        m_ControlPanel = new ControlPanel(this);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(m_MainPanel, BorderLayout.CENTER);
        contentPane.add(m_ControlPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(RobotGravity.SpaceWidth,RobotGravity.SpaceHeight+64);
        setVisible(true);
        m_MainPanel.Init();
    }


    public void Start() {
        m_MainPanel.Start();
    }
    
    public void Stop() {
        m_MainPanel.Stop();
    }
    
    public void drawObject(int x, int y) {
        m_MainPanel.drawObject(m_MainPanel.getGraphics(), x, y);
    }
}