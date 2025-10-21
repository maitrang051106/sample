package object;

import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject{
    
    public OBJ_Door()
    {
        name = "Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/resources/objects/door.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
