package studio.eyesthetics.rearranger;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private EditText edit;
    private TextView textViewer;
    private UnderCover under;
    private String content;
    public MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = ViewModelProviders.of(this).get(MyView.class);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFAA3939, android.graphics.PorterDuff.Mode.MULTIPLY);
        if(myView.getProgressVisibility()) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }
        edit = (EditText) findViewById(R.id.editText);
        edit.setText(myView.getEditField());
        textViewer = (TextView) findViewById(R.id.textView3);
        textViewer.setMovementMethod(new ScrollingMovementMethod());
        textViewer.setText(myView.getTextField());
        content = "";
        under = new UnderCover(this);
    }

     class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected  void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
            myView.setProgressVisibility(true);
            textViewer.setText("");
        }
        @Override
        protected  Void doInBackground(Void... params) {
            under.rearrange("", content);
            under.returnWord();

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            myView.setProgressVisibility(false);

            if(under.res.size() != 0) {
                for(String s : under.res) {
                    myView.addTextField(s);
                }
                textViewer.setText(myView.getTextField());
            } else {
                textViewer.append("No words");
            }

            under.res.clear();
            under.set.clear();
        }
    }

    public void onClick(View view) {
        content = edit.getText().toString();
        myView.setEditField(content);

        MyTask myTask = new MyTask();
        myTask.execute();

    }
}