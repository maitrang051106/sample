package object;

public class OBJ_Chest extends SuperObject{
    
    public OBJ_Chest()
    {
        name = "Chest";
        try{
            image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/resources/objects/chest.png"));
            image = main.UtilityTool.scaleImage(image, 48, 48);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
