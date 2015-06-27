package word.freemaker.swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class MulitPopup extends JPopupMenu{
	private List<ActionListener> listeners = new ArrayList<ActionListener>();  
	  
    private Object[] values;  
      
    private Object[] defaultValues;  
      
    private List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();  
      
    private JButton commitButton ;  
      
    private JButton cancelButton;  
      
    public static final String COMMIT_EVENT = "commit";  
      
    public static final String CANCEL_EVENT = "cancel";  
      
    public MulitPopup(Object[] value , Object[] defaultValue) {  
        super();  
        values = value;  
        defaultValues = defaultValue;  
        initComponent();  
    }  
  
    public void addActionListener(ActionListener listener) {  
        if (!listeners.contains(listener))  
            listeners.add(listener);  
    }  
  
    public void removeActionListener(ActionListener listener) {  
        if (listeners.contains(listener))  
            listeners.remove(listener);  
    }  
  
    private void initComponent() {  
          
        JPanel checkboxPane = new JPanel();  
          
        JPanel buttonPane = new JPanel();  
          
        this.setLayout(new BorderLayout());  
          
        for(Object v : values){  
            JCheckBox temp = new JCheckBox(v.toString() , selected(v));  
            checkBoxList.add(temp);  
        }  
        checkboxPane.setLayout(new GridLayout(checkBoxList.size() , 1 ,3, 3));  
        for(JCheckBox box : checkBoxList){  
            checkboxPane.add(box);  
        }  
          
        commitButton = new JButton("ok");  
          
        commitButton.addActionListener(new ActionListener(){  
  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                commit();  
            }  
              
        });  
          
        cancelButton = new JButton("cancel");  
          
        cancelButton.addActionListener(new ActionListener(){  
  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                cancel();  
            }  
              
        });  
          
        buttonPane.add(commitButton);  
          
        buttonPane.add(cancelButton);  
          
        this.add(checkboxPane , BorderLayout.CENTER);  
          
        this.add(buttonPane , BorderLayout.SOUTH);  
          
          
    }  
  
    private boolean selected(Object v) {  
        for(Object dv : defaultValues){  
            if( dv .equals(v) ){  
                return true;  
            }  
        }  
        return false;  
    }  
  
    protected void fireActionPerformed(ActionEvent e) {  
        for (ActionListener l : listeners) {  
            l.actionPerformed(e);  
        }  
    }  
      
    public Object[] getSelectedValues(){  
        List<Object> selectedValues = new ArrayList<Object>();  
        for(int i = 0 ; i < checkBoxList.size() ; i++){
        	if(checkBoxList.get(i).isSelected()){
	        	selectedValues.add(values[i]);
        	}
        }  
        return selectedValues.toArray(new Object[selectedValues.size()]);  
    }  
  
    public void setDefaultValue(Object[] defaultValue) {  
        defaultValues = defaultValue;  
          
    }  
      
    public void commit(){  
        fireActionPerformed(new ActionEvent(this, 0, COMMIT_EVENT));  
    }  
      
    public void cancel(){  
        fireActionPerformed(new ActionEvent(this, 0, CANCEL_EVENT));  
    }  
}
