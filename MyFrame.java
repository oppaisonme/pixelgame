import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyFrame extends JFrame implements KeyListener{

	Draw drawing;

	public MyFrame(){
		this.drawing = new Draw();
	}

	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_UP){
			drawing.moveUp();
			System.out.println("pos: " + drawing.x + "," + drawing.y);
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			drawing.moveDown();
			System.out.println("pos: " + drawing.x + "," + drawing.y);
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			drawing.moveRight();
			System.out.println("pos: " + drawing.x + "," + drawing.y);
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			drawing.moveLeft();
			System.out.println("pos: " + drawing.x + "," + drawing.y);
		}
		else if(e.getKeyCode() == KeyEvent.VK_Z){
			drawing.attack();
			System.out.println("pos: " + drawing.x + "," + drawing.y);
		}				
		else if(e.getKeyCode() == KeyEvent.VK_P){
			drawing.spawnEnemy();
			System.out.println("pos: " + drawing.x + "," + drawing.y);
		}
	}

	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_UP){
			drawing.idle();
			System.out.println("pos: " + drawing.x + "," + drawing.y);
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			drawing.idle();
			System.out.println("pos: " + drawing.x + "," + drawing.y);
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			drawing.idle();
			System.out.println("pos: " + drawing.x + "," + drawing.y);
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			drawing.idle();
			System.out.println("pos: " + drawing.x + "," + drawing.y);
		}
		else if(e.getKeyCode() == KeyEvent.VK_Z){
			drawing.idle();
			System.out.println("pos: " + drawing.x + "," + drawing.y);
		}
	}

	public void keyTyped(KeyEvent e){
		
	}

	public static void main(String args[]){
		MyFrame gameFrame = new MyFrame();
		gameFrame.setSize(600,600);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true);
		gameFrame.getContentPane().add(gameFrame.drawing);
		gameFrame.addKeyListener(gameFrame);
		System.out.println("practical programming");
	}
}	