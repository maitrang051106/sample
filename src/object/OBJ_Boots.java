package object;

import javax.imageio.ImageIO;

public class OBJ_Boots extends SuperObject{

    public OBJ_Boots() {
        
        name = "Boots";
        try{
            image = ImageIO.read(getClass().getResource("/resources/objects/boots.png"));
            image = main.UtilityTool.scaleImage(image, 48, 48);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
