package word.freemaker.swing;

import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainFrame extends JFrame{
	public MainFrame(){  
        init();  
    }  
    private void init() {  
        this.setLayout(new FlowLayout());  
        Object[] value = new String[]{"基金一","基金二","基金三"};  
        Object[] defaultValue = new String[]{"请选择"};  
        final MulitCombobox mulit = new MulitCombobox(value, defaultValue);  
        final JTextField text = new JTextField(20);  
        mulit.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                Object[] v = mulit.getSelectedValues();  
                StringBuilder builder = new StringBuilder();  
                for(Object dv : v){  
                      
                builder.append(dv);  
                builder.append("--");  
                }  
                text.setText(builder.toString());  
            }  
        });  
        this.add(mulit);  
        this.add(text);  
    }  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        try {  
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");  
        } catch (ClassNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (InstantiationException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (UnsupportedLookAndFeelException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
          
          
        Window main = new MainFrame();  
        main.setSize(400,400);  
        main.setVisible(true);  
  
    }  
}
