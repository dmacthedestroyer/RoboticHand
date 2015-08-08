package graphicalComponents;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * This was a convenience class created to quickly add components vertically and horizontally centered.
 * It was the best way I knew how to do this.
 * @author dmac
 *
 */
public class CenteredVerticalJPanel extends JPanel {
	
	private CenteredVerticalJPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
	
	public static CenteredVerticalJPanel createPanel(){
		CenteredVerticalJPanel jp = new CenteredVerticalJPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		
		return jp;
	}
	
	public static CenteredVerticalJPanel createPanel(String title){
		CenteredVerticalJPanel jp = new CenteredVerticalJPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		jp.setBorder(title);
		return jp;
	}
	
	public void setBorder(String title){
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 2), BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED), BorderFactory.createTitledBorder(title))));
		
	}
	
	public Component addComponent(Component c){
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(Box.createHorizontalGlue());
		panel.add(Box.createHorizontalStrut(20));
		panel.add(c);
		panel.add(Box.createHorizontalStrut(20));
		panel.add(Box.createHorizontalGlue());
		
		add(panel);
		
		return c;
	}
}
