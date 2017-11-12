

import javax.swing.*;
  
@SuppressWarnings("serial")
public class MainPanel extends JFrame {
 
    /**
	 * 
	 */
	//private static final long serialVersionUID = 2799228623150856689L;
	/**
	 * 
	 */ 
    private final JPanel mainPanel = new JPanel(new GridLayout(2, 2));
    
    // Browserpanels
 
    public MainPanel() {
        super();
        initComponents();
    }

    private void initComponents() {
    	
        mainPanel.add(new myPan("").getPanel());
        mainPanel.add(new myPan("").getPanel());
        mainPanel.add(new myPan("").getPanel());
        mainPanel.add(new myPan("").getPanel());
        
        getContentPane().add(mainPanel);
        
        setPreferredSize(new Dimension(1224, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                MainPanel browser = new MainPanel();
                browser.setVisible(true);
                //browser.loadURL();
               
           }     
       });
    }
}
