//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package FlappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener, KeyListener {
    public static FlappyBird flappyBird;
    public final int WIDTH = 800;
    public final int HEIGHT = 600;
    public Renderer renderer;
    public Rectangle bird;
    public int ticks;
    public int yMotion;
    public int score = 0;
    public ArrayList<Rectangle> columns;
    public boolean gameOver;
    public boolean started;
    public Random rand;

    public FlappyBird() {
        JFrame jframe = new JFrame();
        Timer timer = new Timer(20, this);
        this.renderer = new Renderer();
        this.rand = new Random();
        jframe.add(this.renderer);
        jframe.setTitle("Flappy Bird");
        jframe.setDefaultCloseOperation(3);
        jframe.setSize(800, 600);
        jframe.addKeyListener(this);
        jframe.setResizable(false);
        jframe.setVisible(true);
        this.bird = new Rectangle(390, 290, 10, 10);
        this.columns = new ArrayList();
        this.addColunns(true);
        this.addColunns(true);
        this.addColunns(true);
        this.addColunns(true);
        timer.start();
    }

    public void addColunns(boolean start) {
        int space = 200;
        int width = 100;
        int height = 100 + this.rand.nextInt(300);
        if(start) {
            this.columns.add(new Rectangle(800 + width + this.columns.size() * 300, 600 - height - 100, width, height + 20));
            this.columns.add(new Rectangle(800 + width + (this.columns.size() - 1) * 300, 0, width, 600 - height - space));
        } else {
            this.columns.add(new Rectangle(((Rectangle)this.columns.get(this.columns.size() - 1)).x + 600, 600 - height - 100, width, height + 20));
            this.columns.add(new Rectangle(((Rectangle)this.columns.get(this.columns.size() - 1)).x, 0, width, 600 - height - space));
        }

    }

    public void paitColumns(Graphics g, Rectangle columns) {
        g.setColor(Color.green.darker());
        g.fillRect(columns.x, columns.y, columns.width, columns.height);
    }

    public void jump() {
        if(this.gameOver) {
            this.bird = new Rectangle(390, 290, 10, 10);
            this.columns.clear();
            this.yMotion = 0;
            this.score = 0;
            this.addColunns(true);
            this.addColunns(true);
            this.addColunns(true);
            this.addColunns(true);
            this.gameOver = false;
        }

        if(!this.started) {
            this.started = true;
        } else if(!this.gameOver) {
            if(this.yMotion > 0) {
                this.yMotion = 0;
            }

            this.yMotion -= 8;
        }

    }

    public void actionPerformed(ActionEvent e) {
        int spead = 5;
        if(this.score >= 1) {
            ++spead;
        }

        ++this.ticks;
        if(this.started) {
            int i;
            Rectangle column;
            for(i = 0; i < this.columns.size(); ++i) {
                column = (Rectangle)this.columns.get(i);
                column.x -= spead;
            }

            if(this.ticks % 2 == 0 && this.yMotion < 15) {
                this.yMotion += 2;
            }

            for(i = 0; i < this.columns.size(); ++i) {
                column = (Rectangle)this.columns.get(i);
                if(column.x + column.width < 0) {
                    this.columns.remove(column);
                    if(column.y == 0) {
                        this.addColunns(false);
                    }
                }
            }

            this.bird.y += this.yMotion;
            Iterator var5 = this.columns.iterator();

            while(var5.hasNext()) {
                column = (Rectangle)var5.next();
                if(column.y == 0 && this.bird.x + this.bird.width / 2 > column.x + column.width / 2 - 3 && this.bird.x + this.bird.width / 2 < column.x + column.width / 2 + 3) {
                    ++this.score;
                }

                if(column.intersects(this.bird)) {
                    this.gameOver = true;
                    if(this.bird.x < column.x) {
                        this.bird.x = column.x - this.bird.width;
                    } else if(column.y != 0) {
                        this.bird.y = column.y - this.bird.height;
                    } else if(this.bird.y < column.height) {
                        this.bird.y = column.height;
                    }
                }
            }

            if(this.bird.y > 495 || this.bird.y < 0) {
                this.gameOver = true;
                this.score = 0;
            }

            if(this.bird.y + this.yMotion >= 500) {
                this.bird.y = 500 - this.bird.height;
            }
        }

        this.renderer.repaint();
    }

    public void repaint(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.orange.darker().darker().darker());
        g.fillRect(0, 500, 800, 110);
        g.setColor(Color.green);
        g.fillRect(0, 500, 800, 20);
        g.setColor(Color.red);
        g.fillRect(this.bird.x, this.bird.y, this.bird.width, this.bird.height);
        Iterator var2 = this.columns.iterator();

        while(var2.hasNext()) {
            Rectangle column = (Rectangle)var2.next();
            this.paitColumns(g, column);
        }

        g.setColor(Color.red);
        g.setFont(new Font("Arial", 1, 50));
        if(!this.started) {
            g.drawString("Click SPACE to start.", 120, 280);
        }

        if(this.gameOver) {
            g.drawString("Game Over!!!", 250, 280);
        }

        if(!this.gameOver && this.started) {
            g.drawString(String.valueOf(this.score), 375, 100);
        }

    }

    public static void main(String[] args) {
        flappyBird = new FlappyBird();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 32) {
            this.jump();
        }

    }

    public void keyReleased(KeyEvent e) {
    }
}
