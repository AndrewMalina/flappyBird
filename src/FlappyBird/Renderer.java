//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package FlappyBird;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Renderer extends JPanel {
    private static final long serialVersionUID = 1L;

    public Renderer() {
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        FlappyBird.flappyBird.repaint(g);
    }
}
