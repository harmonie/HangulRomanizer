import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

  public class Window extends JFrame implements ActionListener {
	  
	  public static void main(String[] args){       
		  new Window();
		    
		  }//fin main 
	  
	  private JLabel label = new JLabel("Entrez une phrase en coréen");
	  private JTextArea kr = new JTextArea();
	  private JButton btn = new JButton("Romanize");
	  
	  public Window(){
	    this.setTitle("Hangul Romanizer");
	    this.setSize(400,230);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    this.setLocationRelativeTo(null);
	    
	    kr.setPreferredSize(new Dimension(300,120));
	    kr.setForeground(Color.blue);
	    
	    btn.addActionListener(this);

	    JPanel panel = new JPanel();
	    
	    panel.add(label);
	    panel.add(kr);
	    panel.add(btn);

	 
	    this.setContentPane(panel);
	    this.setVisible(true);
	  }// fin conteneur

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, kr.getText() + "\n" + 
				KRomanization.romanize(kr.getText()), "Romanization", 
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon("img.png"));
		}// fin actionPerformed
	
}//fin Window


	 

