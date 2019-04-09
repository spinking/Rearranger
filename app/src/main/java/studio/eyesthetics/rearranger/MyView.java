package studio.eyesthetics.rearranger;

import android.arch.lifecycle.ViewModel;

public class MyView extends ViewModel {

    private String editField = "";
    private String textField = "";
    private boolean progressVisibility = false;

    public MyView(String editField, String textField) {
        this.editField = editField;
        this.textField = textField;
    }
    public MyView() {

    }

    public String getEditField() {
        return editField;
    }
    public void setEditField(String editText) {
        this.editField = editText;
    }

    public String getTextField() {
        return textField;
    }
    public void addTextField(String textView) {
        this.textField += textView + "\n";
    }
    public  void setTextField(String textView) {
        this.textField = textView;
    }

    public boolean getProgressVisibility() {
        return progressVisibility;
    }
    public void setProgressVisibility(boolean progVis) {
        this.progressVisibility = progVis;
    }
}
