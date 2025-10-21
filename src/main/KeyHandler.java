package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPress, downPress, leftPress, rightPress;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W)
            upPress = true;
        else if (code == KeyEvent.VK_S)
            downPress = true;
        else if (code == KeyEvent.VK_A)
            leftPress = true;
        else if (code == KeyEvent.VK_D)
            rightPress = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W)
            upPress = false;
        else if (code == KeyEvent.VK_S)
            downPress = false;
        else if (code == KeyEvent.VK_A)
            leftPress = false;
        else if (code == KeyEvent.VK_D)
            rightPress = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // không cần dùng, để trống
    }
}
