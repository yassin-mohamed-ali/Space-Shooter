import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main extends JPanel {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    Image spaceship = new ImageIcon("assets/spaceship.png").getImage();
    Image bg = new ImageIcon("assets/background.png").getImage();
    int moving_bg = 0;
    boolean hold_d = false;
    double hold_d_s = 0;
    boolean hold_a = false;
    double hold_a_s = 0;
    double player_x = 380;
    boolean alive = true;
    Image asteroidImage = new ImageIcon("assets/asteroid.png").getImage();
    double speed = 2;
    int score = 0;
    int high_score = 0;
    ArrayList<Asteroid> asteroids = new ArrayList<>();
    public class Asteroid{
    int x,y, size;
    
        public Asteroid(int x, int y, int size) {
            this.x=x;
            this.y=y;
            this.size = size;
        }
        public void update() {
        y += speed;
        if (y > HEIGHT) {
            x = (int)(Math.random() * (WIDTH - size));
            y = -size;
}
    }
    
    public void draw(Graphics g) {
        g.drawImage(asteroidImage, x, y, size, size, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}


    
    

    public Main() {
        
        

    

    
        setupKeyBindings();
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        for (int i = 0; i < 5; i++) {
            int x = 800 + (int) (Math.random() * 400);
            int y = (int) (Math.random() * 400);
            int size = 50 + (int) (Math.random() * 30);
            asteroids.add(new Asteroid(x, y, size));
        }
        Timer timer = new Timer(1, e -> {
            moving_bg += 1;
            if (alive){
            score += 1;}
            if (moving_bg > getHeight()) {
                moving_bg = 0;
            }
            if (player_x < 750){
            if (hold_d){
                hold_d_s+=0.1;
                player_x+=Math.ceil(1.1*hold_d_s);
            }}
            if (player_x > -20) {
            if (hold_a) {
                hold_a_s+=0.1;
                player_x-=Math.ceil(1.1*hold_a_s);
            }
        }
            
         for (Asteroid a : asteroids) a.update();
        if (asteroids.size() < 10){
        if (Math.random() < 0.005) { 
                int x = 800 + (int) (Math.random() * 400);
                int y = (int) (Math.random() * 400);
                int size = 40 + (int) (Math.random() * 30);
                asteroids.add(new Asteroid(x, y, size));
            }}
            else {
                speed += 0.001;
            }
            repaint();
            
        });
        
        timer.start();
        }
    
    
    private void setupKeyBindings() {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed D"), "dpressed");
        getActionMap().put("dpressed", new AbstractAction() {
            @Override
                public void actionPerformed(ActionEvent e) {
                    hold_d = true;
        }
    });

    getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), "dreleased");
    getActionMap().put("dreleased", new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            hold_a_s = 0;
            hold_d_s = 0;
            hold_d = false;
        }
    });
    getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed A"), "apressed");
        getActionMap().put("apressed", new AbstractAction() {
            @Override
                public void actionPerformed(ActionEvent e) {
                    hold_a = true;
        }
    });

    getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), "areleased");
    getActionMap().put("areleased", new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            hold_a = false;
        }
    });
    getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "respawn");
    getActionMap().put("respawn", new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!alive) {
                alive = true;
                moving_bg = 0;
                hold_d = false;
                hold_a = false;
                player_x = 380;
                score = 0;
                asteroids = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    int x = 800 + (int) (Math.random() * 400);
                    int y = (int) (Math.random() * 400);
                    int size = 50 + (int) (Math.random() * 30);
                    asteroids.add(new Asteroid(x, y, size));
        }
            }
        }
    });
}
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setFont(new Font("Press Start 2P", Font.BOLD, 40));
        if (alive) {
        g.drawImage(bg, 0, moving_bg, WIDTH, HEIGHT, this);
        g.drawImage(bg, 0, moving_bg-600, WIDTH, HEIGHT, this);
        
        g.setColor(Color.RED);
        g.drawString("Score: "+score, 20, 50);
        g.drawImage(spaceship, (int) player_x, 500, 70, 70, this);
        Rectangle playerRectangle = new Rectangle((int) player_x, 500, 70,70);

        for (Asteroid a : asteroids) {
            a.draw(g);
            
            
        };
        for (Asteroid a : asteroids) {
            if (a.getBounds().intersects(playerRectangle)) {
                alive = false;
                if (score > high_score) {
                    high_score = score;
                }
            }}}
            else {
                g.setColor(Color.RED);
                g.drawString("GAME OVER! ", 200, 200);
                g.drawString("PRESS ENTER TO RESTART", 200, 300);
                g.drawString("Score: "+score, 200, 400);
                g.drawString("High Score: "+high_score, 200, 500);
            }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Shooter");
        Main gamePanel = new Main();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
