/*
 * ARX: Powerful Data Anonymization
 * Copyright 2012 - 2017 Fabian Prasser, Florian Kohlmayer and contributors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.deidentifier.arx.gui.view.impl.menu;

import org.deidentifier.arx.criteria.PrivacyCriterion;
import org.deidentifier.arx.gui.model.Model;
import org.deidentifier.arx.gui.resources.Resources;
import org.deidentifier.arx.gui.view.SWTUtil;
import org.deidentifier.arx.gui.view.impl.menu.DialogAnonymization.AnonymizationConfiguration.SearchType;
import org.deidentifier.arx.gui.view.impl.menu.DialogAnonymization.AnonymizationConfiguration.TransformationType;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A dialog for defining parameters of the anonymization method
 * 
 * @author Fabian Prasser
 */
public class DialogAnonymization extends TitleAreaDialog {

    /**
     * Result of this dialog
     * @author Fabian Prasser
     */
    public static class AnonymizationConfiguration {

        /**
         * Search type
         * @author Fabian Prasser
         */
        public static enum SearchType {
            OPTIMAL,
            STEP_LIMIT,
            TIME_LIMIT
        }

        /**
         * Transformation type
         * @author Fabian Prasser
         */
        public static enum TransformationType {
            GLOBAL,
            LOCAL
        }

        /** Result */
        private double             heuristicSearchTimeLimit = 0d;
        /** Result */
        private int                heuristicSearchStepLimit = 0;
        /** Result */
        private int                numIterations            = 100;
        /** Result */
        private boolean            valid                    = true;
        /** Result */
        private SearchType         searchType               = SearchType.STEP_LIMIT;
        /** Result */
        private TransformationType transformationType       = TransformationType.GLOBAL;
        
        /**
         * Search step limit for SearchType.STEP_LIMIT
         * 
         * @return the heuristicSearchStepLimit
         */
        public int getHeuristicSearchStepLimit() {
            return heuristicSearchStepLimit;
        }
        /**
         * Search time limit for SearchType.TIME_LIMIT
         * 
         * @return the heuristicSearchTimeLimit
         */
        public double getHeuristicSearchTimeLimit() {
            return heuristicSearchTimeLimit;
        }
        
        /**
         * Number of iterations for TransformationType.LOCAL
         * 
         * @return the numIterations
         */
        public int getNumIterations() {
            return numIterations;
        }
        
        /**
         * Returns the search type
         * 
         * @return the searchType
         */
        public SearchType getSearchType() {
            return searchType;
        }
        
        /**
         * Returns the transformation type
         * 
         * @return the transformationType
         */
        public TransformationType getTransformationType() {
            return transformationType;
        }
    }

    /** Model */
    private String  title;
    /** Model */
    private String  message;
    /** Model */
    private boolean optimalSearchAvailable;
    /** Model */
    private boolean localRecodingAvailable;

    /** View */
    private Button  okButton;
    /** View */
    private Text    txtHeuristicSearchTimeLimit;
    /** View */
    private Text    txtHeuristicSearchStepLimit;
    /** View */
    private Text    textNumIterations;

    /** Result */
    private AnonymizationConfiguration  result = new AnonymizationConfiguration();

    /**
     * Creates a new instance
     * 
     * @param shell
     * @param model
     */
    public DialogAnonymization(Shell shell, Model model) {
        super(shell);
        this.title = Resources.getMessage("DialogAnonymization.0"); //$NON-NLS-1$
        this.message = Resources.getMessage("DialogAnonymization.1"); //$NON-NLS-1$
        this.result.heuristicSearchTimeLimit = (double)model.getHeuristicSearchTimeLimit() / 1000d;
        this.result.heuristicSearchStepLimit = model.getHeuristicSearchStepLimit();
        this.result.numIterations = model.getLocalRecodingModel().getNumIterations();
        
        // Determine if optimal search is available
        this.optimalSearchAvailable = model.getSolutionSpaceSize() <= model.getHeuristicSearchThreshold();
        
        // Determine if local recoding is available
        this.localRecodingAvailable = true;
        for (PrivacyCriterion c : model.getInputConfig().getCriteria()) {
            if (!c.isLocalRecodingSupported()) {
                this.localRecodingAvailable = false;
                break;
            }
        }
    }

    /**
     * Returns the parameters selected by the user. Returns <code>null</code> if the selection has been canceled.
     * 
     * @return the value
     */
    public AnonymizationConfiguration getResult() {
        if (result.valid) {
            return result;
        } else {
            return null;
        }
    }

    /**
     * Checks if all input is valid
     */
    private void checkValidity() {
        
        // Handle parameter
        if (getHeuristicSearchTimeLimit() == null) {
            setErrorMessage(Resources.getMessage("DialogAnonymization.5")); //$NON-NLS-1$
            if (okButton != null) {
                result.valid = false;
                okButton.setEnabled(false);
                return;
            }
        } else {
            result.heuristicSearchTimeLimit = getHeuristicSearchTimeLimit();
        }
        
        // Handle parameter
        if (getNumIterations() == null) {
            setErrorMessage(Resources.getMessage("DialogAnonymization.4")); //$NON-NLS-1$
            if (okButton != null) {
                result.valid = false;
                okButton.setEnabled(false);
                return;
            }
        } else {
            result.numIterations = getNumIterations();
        }
        
        // Handle parameter
        if (getHeuristicSearchStepLimit() == null) {
            setErrorMessage(Resources.getMessage("DialogAnonymization.8")); //$NON-NLS-1$
            if (okButton != null) {
                result.valid = false;
                okButton.setEnabled(false);
                return;
            }
        } else {
            result.heuristicSearchStepLimit = getHeuristicSearchStepLimit();
        }

        // Everything is fine
        setErrorMessage(null);
        if (okButton != null) {
            result.valid = true;
            okButton.setEnabled(true);
        }
    }
    
    /**
     * Adds a message to the given group
     * @param group
     * @param message
     */
    private void createMessage(Group group, String message) {
        Label label = new Label(group, SWT.NONE);
        label.setText(Resources.getMessage("DialogAnonymization.14") + message); //$NON-NLS-1$
        label.setLayoutData(GridDataFactory.fillDefaults().span(2, 1).create());
    }

   /**
    * Converts to int, returns null if not valid
    * @return
    */
   private Integer getHeuristicSearchStepLimit() {
       int value = 0;
       try {
           value = Integer.valueOf(txtHeuristicSearchStepLimit.getText());
       } catch (Exception e) {
           return null;
       }
       if (((int)value) > 0d) {
           return value;
       } else {
           return null;
       }
   }

    /**
     * Converts to double, returns null if not valid
     * @return
     */
    private Double getHeuristicSearchTimeLimit() {
        double value = 0d;
        try {
            value = Double.valueOf(txtHeuristicSearchTimeLimit.getText());
        } catch (Exception e) {
            return null;
        }
        if (((int)value) > 0d) {
            return value;
        } else {
            return null;
        }
    }
   
    /**
     * Converts to double, returns null if not valid
     * @return
     */
    private Integer getNumIterations() {
        int value = 0;
        try {
            value = Integer.valueOf(textNumIterations.getText());
        } catch (Exception e) {
            return null;
        }
        if (value > 0d) {
            return value;
        } else {
            return null;
        }
    }

    @Override
    protected void buttonPressed(int buttonId) {
        if (buttonId == IDialogConstants.OK_ID) {
            result.valid = true;
        } else if (buttonId == IDialogConstants.CANCEL_ID) {
            result.valid = false;
        }
        super.buttonPressed(buttonId);
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setImages(Resources.getIconSet(newShell.getDisplay()));
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
    }
    
    @Override
    protected Control createContents(Composite parent) {
        Control contents = super.createContents(parent);
        setTitle(title); 
        setMessage(message);
        return contents;
    }

    @Override
    protected Control createDialogArea(Composite parent) {

        Composite composite = (Composite) super.createDialogArea(parent);        
        Composite base = new Composite(composite, SWT.NONE);
        base.setLayoutData(SWTUtil.createFillGridData());
        base.setLayout(SWTUtil.createGridLayout(1, false));
        
        // Upper group
        Group group1 = new Group(parent, SWT.SHADOW_ETCHED_IN);
        group1.setText(Resources.getMessage("DialogAnonymization.6")); //$NON-NLS-1$
        GridData data1 = SWTUtil.createFillGridData();
        data1.horizontalIndent = 5;
        group1.setLayoutData(data1);
        group1.setLayout(GridLayoutFactory.swtDefaults().numColumns(2).create());

        // Optimal
        final Button radio11 = new Button(group1, SWT.RADIO);
        radio11.setText(Resources.getMessage("DialogAnonymization.11")); //$NON-NLS-1$
        radio11.setLayoutData(GridDataFactory.swtDefaults().span(2, 1).create());
        radio11.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                if (radio11.getSelection()) {
                    result.searchType = SearchType.OPTIMAL;
                }
            }
        });

        // Steps
        final Button radio12 = new Button(group1, SWT.RADIO);
        radio12.setText(Resources.getMessage("DialogAnonymization.7")); //$NON-NLS-1$
        radio12.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                if (radio12.getSelection()) {
                    result.searchType = SearchType.STEP_LIMIT;
                }
            }
        });

        this.txtHeuristicSearchStepLimit = new Text(group1, SWT.BORDER);
        this.txtHeuristicSearchStepLimit.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
        this.txtHeuristicSearchStepLimit.setText(String.valueOf(result.heuristicSearchStepLimit));
        this.txtHeuristicSearchStepLimit.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent arg0) {
                radio12.setSelection(true);
                result.searchType = SearchType.STEP_LIMIT;
                checkValidity();
            }
        });
        
        // Time
        final Button radio13 = new Button(group1, SWT.RADIO);
        radio13.setText(Resources.getMessage("DialogAnonymization.2")); //$NON-NLS-1$
        radio13.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                if (radio13.getSelection()) {
                    result.searchType = SearchType.TIME_LIMIT;
                }
            }
        });

        this.txtHeuristicSearchTimeLimit = new Text(group1, SWT.BORDER);
        this.txtHeuristicSearchTimeLimit.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
        this.txtHeuristicSearchTimeLimit.setText(String.valueOf(result.heuristicSearchTimeLimit));
        this.txtHeuristicSearchTimeLimit.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent arg0) {
                radio13.setSelection(true);
                result.searchType = SearchType.TIME_LIMIT;
                checkValidity();
            }
        });
        
        // Prepare radio buttons
        if (this.optimalSearchAvailable) {
            radio11.setSelection(true);
            result.searchType = SearchType.OPTIMAL;
        } else {
            radio11.setEnabled(false);
            radio12.setSelection(true);
            result.searchType = SearchType.STEP_LIMIT;
            createMessage(group1, Resources.getMessage("DialogAnonymization.13")); //$NON-NLS-1$
        }

        // Lower group
        Group group2 = new Group(parent, SWT.SHADOW_ETCHED_IN);
        group2.setText(Resources.getMessage("DialogAnonymization.9")); //$NON-NLS-1$
        GridData data2 = SWTUtil.createFillGridData();
        data2.horizontalIndent = 5;
        group2.setLayoutData(data2);
        group2.setLayout(GridLayoutFactory.swtDefaults().numColumns(2).create());

        // Global transformation
        final Button radio21 = new Button(group2, SWT.RADIO);
        radio21.setText(Resources.getMessage("DialogAnonymization.10")); //$NON-NLS-1$
        radio21.setLayoutData(GridDataFactory.swtDefaults().span(2, 1).create());
        radio21.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                if (radio21.getSelection()) {
                    result.transformationType = TransformationType.GLOBAL;
                }
            }
        });

        // Local transformation
        final Button radio22 = new Button(group2, SWT.RADIO);
        radio22.setText(Resources.getMessage("DialogAnonymization.3")); //$NON-NLS-1$
        radio22.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                if (radio22.getSelection()) {
                    result.transformationType = TransformationType.LOCAL;
                }
            }
        });

        this.textNumIterations = new Text(group2, SWT.BORDER);
        this.textNumIterations.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
        this.textNumIterations.setText(String.valueOf(result.numIterations));
        this.textNumIterations.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent arg0) {
                radio22.setSelection(true);
                result.transformationType = TransformationType.LOCAL;
                checkValidity();
            }
        });

        // Prepare radio buttons
        radio21.setSelection(true);
        result.transformationType = TransformationType.GLOBAL;
        
        // Show message
        if (this.localRecodingAvailable) {
            radio22.setEnabled(true);
        } else {
            radio22.setEnabled(false);
            textNumIterations.setEnabled(false);
            createMessage(group2, Resources.getMessage("DialogAnonymization.12")); //$NON-NLS-1$
        }

        applyDialogFont(base);
        checkValidity();
        return composite;
    }

    @Override
    protected ShellListener getShellListener() {
        return new ShellAdapter() {
            @Override
            public void shellClosed(final ShellEvent event) {
                result.valid = false;
                setReturnCode(Window.CANCEL);
            }
        };
    }
}