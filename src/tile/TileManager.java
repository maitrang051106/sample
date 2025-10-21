package tile;
import main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNumber[][];
    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[38]; // Example size
        mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/resources/maps.txt");
    }
    public void getTileImage(){
        try {
            for (int i = 0; i < tile.length; i++) { 
                tile[i] = new Tile();
                tile[i].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/" + String.format("%03d", i) + ".png"));
                if(i > 15  && i != 17){
                    tile[i].collision = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            if (is == null) {
                System.err.println("Could not find map file: " + filePath);
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for(int row = 0; row < gp.maxWorldRow; row++){
                String line = br.readLine();
                if (line == null) break;
                String[] numbers = line.trim().split("\\s+");
                for(int col = 0; col < gp.maxWorldCol; col++){
                    if (col < numbers.length){
                        try{
                            mapTileNumber[col][row] = Integer.parseInt(numbers[col]);
                        }catch(NumberFormatException nfe){
                            System.err.println("TileManager.loadMap: invalid number at row="+row+" col="+col+" -> '"+numbers[col]+"'");
                            mapTileNumber[col][row] = 0;
                        }
                    } else {
                        mapTileNumber[col][row] = 0;
                    }
                }
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {
        for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++){
            for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++){
                int tileIndex = mapTileNumber[worldCol][worldRow];
                if (tileIndex < 0 || tileIndex >= tile.length) continue;
                Tile t = tile[tileIndex];
                if (t == null || t.image == null) continue;
                int x = worldCol * gp.tileSize;
                int y = worldRow * gp.tileSize;
                int screenX = x - gp.player.worldX + gp.player.screenX;
                int screenY = y - gp.player.worldY + gp.player.screenY;
                // Only draw if on screen (basic culling)
                if (screenX + gp.tileSize > 0 && screenX - gp.tileSize < gp.screenWidth && screenY + gp.tileSize > 0 && screenY - gp.tileSize < gp.screenHeight) {
                    g2.drawImage(t.image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }
}