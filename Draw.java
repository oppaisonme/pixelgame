import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;

public class Draw extends JComponent{

	public boolean runback = false;
	boolean jumping = false;
	boolean falling = false;
	public boolean right = true;
	public boolean notMoving = true;

	private BufferedImage image;
	private BufferedImage backgroundImage;
	public URL resource = getClass().getResource("hero/idle0.png");
	public URL Standing;
	// circle's position
	public int x = 300;
	public int y = 300;
	public int height = 0;
	public int width = 0;

	// animation states
	public int state = 0;

	// randomizer
	public Random randomizer;

	// enemy
	public int enemyCount;
	Monster[] monsters = new Monster[10];

	public Draw(){
		randomizer = new Random();
		spawnEnemy();
		idle();

		try{
			image = ImageIO.read(resource);
			backgroundImage = ImageIO.read(getClass().getResource("background.jpg"));
		}
		catch(IOException e){
			e.printStackTrace();
		}

		height = image.getHeight();
		width = image.getWidth();

		startGame();
	}

	public void startGame(){
		Thread gameThread = new Thread(new Runnable(){
			public void run(){
				while(true){
					try{
						for(int c = 0; c < monsters.length; c++){
							if(monsters[c]!=null){
								monsters[c].moveTo(x,y);
								repaint();
							}
						}
						Thread.sleep(100);
					} catch (InterruptedException e) {
							e.printStackTrace();
					}
				}
			}
		});
		gameThread.start();
	}

	public void spawnEnemy(){
		if(enemyCount < 10){
			monsters[enemyCount] = new Monster(randomizer.nextInt(500), randomizer.nextInt(500), this);
			enemyCount++;
		}
	}

	public void reloadImage0(){
		state++;
		runback = false;
		if(state == 0){
			resource = getClass().getResource("run/run0.png");
		}
		else if(state == 1){
			resource = getClass().getResource("run/run1.png");
		}
		else if(state == 2){
			resource = getClass().getResource("run/run2.png");
		}
		else if(state == 3){
			resource = getClass().getResource("run/run3.png");
		}
		else if(state == 4){
			resource = getClass().getResource("run/run4.png");
		}
		else if(state == 5){
			resource = getClass().getResource("run/run5.png");
			state = 0;
		}

		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void reloadImage1(){
		state++;
		runback = true;
		if(state == 0){
			resource = getClass().getResource("run/runback0.png");
		}
		else if(state == 1){
			resource = getClass().getResource("run/runback1.png");
		}
		else if(state == 2){
			resource = getClass().getResource("run/runback2.png");
		}
		else if(state == 3){
			resource = getClass().getResource("run/runback3.png");
		}
		else if(state == 4){
			resource = getClass().getResource("run/runback4.png");
		}
		else if(state == 5){
			resource = getClass().getResource("run/runback5.png");
			state = 0;
		}

		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void attackAnimation(){
		Thread thread1 = new Thread(new Runnable(){
			public void run(){
				for(int ctr = 0; ctr < 5; ctr++){
					try {
						if (right == true){
						if(ctr == 4){
							resource = getClass().getResource("hero/idle0.png");
						}
						else{
							resource = getClass().getResource("attack/attack"+ctr+".png");
						}
						}else{
						if(ctr == 4){
							resource = getClass().getResource("hero/idleback1.png");
						}
						else{
							resource = getClass().getResource("attack/attackback"+ctr+".png");
						}
						}
						try{
							image = ImageIO.read(resource);
						}
						catch(IOException e){
							e.printStackTrace();
						}
						repaint();
				        Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				for(int x=0; x<monsters.length; x++){
					if(monsters[x]!=null){
						if(monsters[x].contact){
							monsters[x].life = monsters[x].life - 10;
						}
					}
				}
			}
		});
		thread1.start();
	}

	public void idle(){
		notMoving = true;
		Thread thread = new Thread( new Runnable(){
			public void run(){
				while(notMoving){
					for(int i = 0; i < 3; i++){
						if(right == true){
							if(i == state){
								Standing = getClass().getResource("hero/idle" + i +".png");
							}else if(state > 3){
								Standing = getClass().getResource("hero/idle0.png");
								state = 0;
								i = 0;
							}
						}else{
							if(i == state){
								Standing = getClass().getResource("hero/idleback" + i +".png");
							}else if(state > 3){
								Standing = getClass().getResource("hero/idleback0.png");
								state = 0;
								i = 0;
							}
						}
					}
					repaint();
					state++;
						try{
							Thread.sleep(100);
							image = ImageIO.read(Standing);
						}
						catch(IOException e){
							e.printStackTrace();
						}catch(InterruptedException e){
							e.printStackTrace();
						}

					}
				}
			});
			thread.start();
		}

	public void attack(){
		attackAnimation();
	}

	public void moveUp(){
		y = y - 5;
		reloadImage0();
		repaint();
		checkCollision();
	}

	public void moveDown(){
		y = y + 5;
		reloadImage0();
		repaint();
		checkCollision();
	}

	public void moveLeft(){
		notMoving = false;
		right = false;
		x = x - 5;
		reloadImage1();
		repaint();
		checkCollision();
	}

	public void moveRight(){
		notMoving = false;
		right = true;
		x = x + 5;
		reloadImage0();
		repaint();
		checkCollision();
	}

	public void checkCollision(){
		int xChecker = x + width;
		int yChecker = y;

		for(int x=0; x<monsters.length; x++){
			boolean collideX = false;
			boolean collideY = false;

			if(monsters[x]!=null){
				monsters[x].contact = false;

				if(yChecker > monsters[x].yPos){
					if(yChecker-monsters[x].yPos < monsters[x].height){
						collideY = true;
						System.out.println("collideY");
					}
				}
				else{
					if(monsters[x].yPos - (yChecker+height) < monsters[x].height){
						collideY = true;
						System.out.println("collideY");
					}
				}

				if(xChecker > monsters[x].xPos){
					if((xChecker-width)-monsters[x].xPos < monsters[x].width){
						collideX = true;
						System.out.println("collideX");
					}
				}
				else{
					if(monsters[x].xPos-xChecker < monsters[x].width){
						collideX = true;
						System.out.println("collideX");
					}
				}
			}

			if(collideX && collideY){
				System.out.println("collision!");
				monsters[x].contact = true;
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this);
		g.drawImage(image, x, y, this);
		
		for(int c = 0; c < monsters.length; c++){		
			if(monsters[c]!=null){
				g.drawImage(monsters[c].image, monsters[c].xPos, monsters[c].yPos, this);
				g.setColor(Color.GREEN);
				g.fillRect(monsters[c].xPos+7, monsters[c].yPos, monsters[c].life, 2);
			}	
		}
	}

	public void checkDeath(){
		for(int c = 0; c < monsters.length; c++){	
			if(monsters[c]!=null){
				if(!monsters[c].alive){
				monsters[c] = null;
				}	
			}			
		}
	}
}