package display;



import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class BarSeries {
	
	public static Shell shell;
	public static Shell shell2;
	public static Button checkButton1;
	public static Button checkButton2;
	public static Button checkButton3;
	public static Button checkButton4;
	public static Button checkButton5;
	public static Button checkButton6;
	public static Button checkButton7;
	public static Button checkButton8;
	public static Button printButton;
	public static double[] series;
	public static int printLength=0;
	public static int valuesTransmitted=0;
	
	public static void Choices (Display display, double[] mathValues){
		
		shell=new Shell(display);
		shell.setText("Choose the values you want to display");
		
		initUI(mathValues, display);
		
		shell.setSize(500, 500);
		
		shell.open();
		
		while (!shell.isDisposed()) {
	          if (!display.readAndDispatch()) {
	            display.sleep();
	          }
	        }
		
	}
	
	
	public static void initUI(final double[] mV, final Display display){
		
		checkButton1=new Button(shell,SWT.CHECK);
		checkButton1.setText("Arithmetic Mean");
		checkButton1.setSelection(false);
		checkButton1.setLocation(50, 50);
		checkButton1.pack();
		
		checkButton2=new Button(shell,SWT.CHECK);
		checkButton2.setText("Geometric Mean");
		checkButton2.setSelection(false);
		checkButton2.setLocation(50, 70);
		checkButton2.pack();
		
		checkButton3=new Button(shell,SWT.CHECK);
		checkButton3.setText("Standard Deviance");
		checkButton3.setSelection(false);
		checkButton3.setLocation(50, 90);
		checkButton3.pack();
		
		checkButton4=new Button(shell,SWT.CHECK);
		checkButton4.setText("Variance");
		checkButton4.setSelection(false);
		checkButton4.setLocation(50, 110);
		checkButton4.pack();
		
		checkButton5=new Button(shell,SWT.CHECK);
		checkButton5.setText("etc 1");
		checkButton5.setSelection(false);
		checkButton5.setLocation(50, 130);
		checkButton5.pack();
		
		checkButton6=new Button(shell,SWT.CHECK);
		checkButton6.setText("etc 2");
		checkButton6.setSelection(false);
		checkButton6.setLocation(50, 150);
		checkButton6.pack();
		
		checkButton7=new Button(shell,SWT.CHECK);
		checkButton7.setText("etc 3");
		checkButton7.setSelection(false);
		checkButton7.setLocation(50, 170);
		checkButton7.pack();
		
		checkButton8=new Button(shell,SWT.CHECK);
		checkButton8.setText("etc 4");
		checkButton8.setSelection(false);
		checkButton8.setLocation(50, 190);
		checkButton8.pack();
		
		printButton=new Button(shell,SWT.PUSH);
		printButton.setText("Print");
		printButton.setBounds(50, 210, 50, 50);
		printButton.addSelectionListener(new SelectionAdapter (){
			@Override
			public void widgetSelected (SelectionEvent e){
				
				if(checkButton1.getSelection()){
					printLength++;
				}
				if(checkButton2.getSelection()){
					printLength++;
				}
				if(checkButton3.getSelection()){
					printLength++;
				}
				if(checkButton4.getSelection()){
					printLength++;
				}
				if(checkButton5.getSelection()){
					printLength++;
				}
				if(checkButton6.getSelection()){
					printLength++;
				}
				if(checkButton7.getSelection()){
					printLength++;
				}
				if(checkButton8.getSelection()){
					printLength++;
				}
				
				series=new double[printLength];

				if(checkButton1.getSelection()){
					series[valuesTransmitted]=mV[0];
					valuesTransmitted++;
				}
				if(checkButton2.getSelection()){
					series[valuesTransmitted]=mV[1];
					valuesTransmitted++;
				}
				if(checkButton3.getSelection()){
					series[valuesTransmitted]=mV[2];
					valuesTransmitted++;
				}
				if(checkButton4.getSelection()){
					series[valuesTransmitted]=mV[3];
					valuesTransmitted++;
				}
				if(checkButton5.getSelection()){
					series[valuesTransmitted]=mV[4];
					valuesTransmitted++;
				}
				if(checkButton6.getSelection()){
					series[valuesTransmitted]=mV[5];
					valuesTransmitted++;
				}
				if(checkButton7.getSelection()){
					series[valuesTransmitted]=mV[6];
					valuesTransmitted++;
				}
				if(checkButton8.getSelection()){
					series[valuesTransmitted]=mV[7];
					valuesTransmitted++;
				}
				
				Display(display, series);
				//give series to next class to print out the values!
			}
		});
		
		
		Button exitButton = new Button(shell, SWT.PUSH);
        exitButton.setText("Cancel");
        exitButton.setLayoutData(new RowData(80, 30));
	}
	public static void Display (Display display, double[] values){
		
		shell2=new Shell(display);
		shell2.setText("Choose the values you want to display");
		
		
		shell.setSize(500, 500);
		
		shell.open();
		
		while (!shell.isDisposed()) {
	          if (!display.readAndDispatch()) {
	            display.sleep();
	          }
	        }
		
	}

}
