package main;
import main.GamePanel;
import object.SuperObject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    //screen settings
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = scale * originalTileSize; /// 48 x 48
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 15;
    public final int screenWidth = tileSize * maxScreenCol; // 768
    public final int screenHeight = tileSize * maxScreenRow; // 576
    
    //world settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    //public final int worldWidth = tileSize * maxWorldCol;
    //public final int worldHeight = tileSize * maxWorldRow;
    // FPS
    int FPS = 60;
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this,keyH);
    public SuperObject obj[] = new SuperObject[10];

    
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);// y vẽ mọi thứ vào một tấm ảnh ẩn trước, xong mới đẩy cả bức ảnh đó ra màn hình một lần → tránh nháy, hình ảnh mượt hơn
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run()
    {
        double drawInterval = 1000000000/FPS;// 0.01666 seconds
        double delta = 0;
        double lastTime = System.nanoTime();
        double currentTime;
        while(gameThread != null)
        {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            while(delta >= 1)
            {
                update();
                repaint();
                delta --;
            }
        }
    }
    public void update(){
        player.update();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //tile
        tileM.draw(g2);

        //object
        for (SuperObject obj : obj) {
            if (obj != null) {
                obj.draw(g2, this);
            }
        }
        //player
        player.draw(g2);
        g2.dispose(); //save mmr

    }
}
