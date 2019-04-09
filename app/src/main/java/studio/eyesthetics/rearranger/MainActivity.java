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
        edit = (EditText) findViewById(R.id.editText);
        edit.setText(myView.getEditField());
        textViewer = (TextView) findViewById(R.id.textView3);
        textViewer.setText(myView.getTextField());
        content = "";
        under = new UnderCover(this);
    }

     class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected  void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
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
            textViewer.setMovementMethod(new ScrollingMovementMethod());
            progressBar.setVisibility(ProgressBar.INVISIBLE);

            if(under.res.size() != 0) {
                for(String s : under.res) {
                    myView.addTextField(s);
                }
            } else {
                textViewer.append("No words");
            }

            textViewer.setText(myView.getTextField());

            under.res.clear();
            under.set.clear();
        }
    }

    public void onClick(View view) {
        content = edit.getText().toString();
        myView.setEditField(content);
        myView.setTextField("");

        MyTask myTask = new MyTask();
        myTask.execute();

    }
}