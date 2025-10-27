package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Key extends SuperObject{
    
    public OBJ_Key()
    {
        name = "Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/resources/objects/key.png"));
            image = main.UtilityTool.scaleImage(image, 48, 48);
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }


}
