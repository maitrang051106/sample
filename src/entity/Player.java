package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gp,KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        // center player on the screen
        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;
        // adjust solid area for a 2x-tile player sprite (centered)

        solidArea = new Rectangle(8, 10, gp.tileSize - 16, gp.tileSize - 16);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gp.tileSize * 10;
        worldY = gp.tileSize * 10;
        speed = 4;
        direction = "stand";
    }
    public void getPlayerImage(){
    try{
        up1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/up1.png"));
        up2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/up2.png"));
        down1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/down1.png"));
        down2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/down2.png"));
        left1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/left1.png"));
        left2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/left2.png"));
        right1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/right1.png"));
        right2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/right2.png"));
    }
    catch(IOException e){
        e.printStackTrace();
    }
}
    public void update()
    {
        if(keyH.upPress == false && keyH.downPress == false && keyH.leftPress == false && keyH.rightPress == false){
            direction = "stand";
            spriteNum = 1;
            spriteCounter = 0;
        }
        if(keyH.upPress == true)
        {
            direction = "up";
            worldY -= speed;
        }
        else if(keyH.downPress == true)
        {
            direction = "down";
            worldY += speed;
        }
        else if(keyH.leftPress == true)
        {
            direction = "left";
            worldX -= speed;
        }
        else if(keyH.rightPress == true)
        {
            direction = "right";
            worldX += speed;
        }
        collisionOn = false;
        gp.cChecker.checkTile(this);
        //check object collision
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);
        if(collisionOn == true){
            switch(direction){
                case "up":
                    worldY += speed;
                    break;
                case "down":
                    worldY -= speed;
                    break;
                case "left":
                    worldX += speed;
                    break;
                case "right":
                    worldX -= speed;
                    break;
            }
        }
        else
        {
            System.out.println("update player pos: " + worldX/gp.tileSize + ", " + worldY/gp.tileSize);
        }
        spriteCounter++;
        if(spriteCounter > 12){
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    public void pickUpObject(int i)
    {
        if(i != 999)
        {
            String objectName = gp.obj[i].name;
            switch(objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if(hasKey > 0){
                        gp.playSE(3);
                        hasKey--;
                        gp.obj[i] = null;
                        gp.ui.showMessage("You opened the door!");
                    }
                    else{
                        gp.ui.showMessage("You need a key to open this door.");
                    }
                    break;
                case "Chest":
                    gp.playSE(3);
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed += 2;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got boots! Speed increased to " + speed);
                    break;

            }
        }
    }

    public void draw(Graphics2D g2)
    {
        // g2.setColor(Color.white);
        // g2.fillRect(x , y, gp.tileSize, gp.tileSize);
        BufferedImage image = up1;
        switch (direction) {
            case "up":
                if(spriteNum ==1){
                    image = up1;
                }
                else if(spriteNum ==2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum ==1){
                    image = down1;
                }
                else if(spriteNum ==2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum ==1){
                    image = left1;
                }
                else if(spriteNum ==2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum ==1){
                    image = right1;
                }
                else if(spriteNum ==2){
                    image = right2;
                }
                break;
            default:
                image = down1;
                break;
        }
        g2.drawImage(image , screenX , screenY , gp.tileSize , gp.tileSize , null);
    }
}
