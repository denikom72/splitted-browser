

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;
 
import static javafx.concurrent.Worker.State.FAILED;
  
public class myPan {
 
    /**
	 * 
	 */
	private final JFXPanel jfxPanel = new JFXPanel();

    
	private WebEngine engine;
 
  
	private final JPanel panel = new JPanel(new BorderLayout());
    
    
    private final JLabel lblStatus = new JLabel();
    private final JButton btnGo = new JButton("Go");
    private final JTextField txtURL = new JTextField();
    private final JProgressBar progressBar = new JProgressBar();

    public Stage stage;

    public myPan( String url ) {
        //super();
    	txtURL.setText( url );
    	//loadURL(url);
        initComponents();
        
    }

    public JPanel getPanel(){
    	//
    	return this.panel;
    }
    
    
    
    
    private void initComponents() {
        createScene();
 
        ActionListener al = new ActionListener() {
             
            public void actionPerformed(ActionEvent e) {
                loadURL( txtURL.getText() );
            }
        };
 
        btnGo.addActionListener(al);
        txtURL.addActionListener(al);
  
        progressBar.setPreferredSize(new Dimension(150, 18));
        progressBar.setStringPainted(true);
  
        /* JPanel topBar = *//*new JPanel(new BorderLayout(3 /* h_gap *//*, 2 /* v_gap *//*)); */
        JPanel topBar = new JPanel( new BorderLayout());
        //topBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        topBar.add(txtURL, BorderLayout.CENTER);
        topBar.add(btnGo, BorderLayout.EAST);
        
        //topBar.add(progressBar, BorderLayout.EAST);
        JPanel statusBar = new JPanel(new BorderLayout(5, 0));
        statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        statusBar.add(lblStatus, BorderLayout.CENTER);
        statusBar.add(progressBar, BorderLayout.EAST);
 
               
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(jfxPanel, BorderLayout.CENTER);
        panel.add(statusBar, BorderLayout.SOUTH);
        
    }
    
    public /* Thread */ void mkThread ( final JFXPanel jfxP ) {
      	Platform.runLater( new Runnable(){
			
			public void run() {
				// TODO Auto-generated method stub
				jfxP.setScene(new Scene( new WebView()));	
			}
        });
      }
 
    private void createScene() {
 
        Platform.runLater(new Runnable() {
             
            public void run() {
 
                WebView view = new WebView();
                
                jfxPanel.setScene(new Scene(view));
                
                engine = view.getEngine();

                // Threads in the method
                loadURL(txtURL.getText());
                
                
                engine.getLoadWorker().stateProperty().addListener(
                        new ChangeListener<State>() {
                            public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, State oldState, State newState) {
                                if (newState == State.SUCCEEDED) {
                                    //stage.setTitle(engine.getLocation());
                                    System.out.println(engine.getLocation() + " ................. ");
                         
                                   
                                }
                            }
                        });
  
                engine.titleProperty().addListener(new ChangeListener<String>() {
                    
                    public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {
                        SwingUtilities.invokeLater(new Runnable() {
                             
                            public void run() {
                            	System.out.println("dddddddddddddd");                            }
                        });
                    }
                });
 
                engine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
                     
                    public void handle(final WebEvent<String> event) {
                        SwingUtilities.invokeLater(new Runnable() {
                             
                            public void run() {
                                lblStatus.setText(event.getData());
                                System.out.println(event.getData());
                            }
                        });
                    }
                });
 
                engine.locationProperty().addListener(new ChangeListener<String>() {
                    
                    public void changed(ObservableValue<? extends String> ov, String oldValue, final String newValue) {
                        SwingUtilities.invokeLater(new Runnable() {
                             
                            public void run() {
                            	@SuppressWarnings("unused")
								Boolean newState = true;
								if (State.SUCCEEDED != null) {
                            		//engine.executeScript("alert('just a test')");
                            	}
                            	
                                txtURL.setText(newValue);
                            }
                        });
                    }
                });
 
               
                
                engine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>() {
                    
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) {
                    	//engine.executeScript("alert('fff')");
                    	SwingUtilities.invokeLater(new Runnable() {
                             
                            public void run() {
                                //engine.executeScript("alert('xxx');");
                            	progressBar.setValue(newValue.intValue());
                                System.out.println(newValue.intValue());
                                             }
                        });
                    }
                });

                engine.getLoadWorker()
                        .exceptionProperty()
                        .addListener(new ChangeListener<Throwable>() {
 
                            public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
                                if (engine.getLoadWorker().getState() == FAILED) {
                                    SwingUtilities.invokeLater(new Runnable() {
                                         public void run() {
                                            JOptionPane.showMessageDialog(
                                                    panel,
                                                    (value != null) ?
                                                    engine.getLocation() + "\n" + value.getMessage() :
                                                    engine.getLocation() + "\nUnexpected error.",
                                                    "Loading error...",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }
                                    });
                                } else {
                                	SwingUtilities.invokeLater(
                                			new Runnable(){
                                				public void run() {
                                					engine.executeScript("document.body.style.backgroundColor='#00f3f3';");
                                				}
                                			}
                                		);
                                }
                            }
                        });

                
            }
        });
    }
 
    public void loadURL(final String url) {
        Platform.runLater(new Runnable() {
             
            public void run() {
                String tmp = toURL(url);
 
                if (tmp == null) {
                    tmp = toURL("http://" + url);
                }
 
                engine.load(tmp);
                
            }
        });
    }

    private static String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
                return null;
        }
    }

  
}
