package studio.eyesthetics.rearranger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

public class UnderCover extends IOException {


    Context context;
    UnderCover(Context context) {
        this.context = context;
    }
    Set<String> set = new HashSet<>();
    Set<String> res = new HashSet<>();


    public void rearrange (String prefix, String str){
        if(prefix.matches("^[аеёиоуэюя]*$") || prefix.matches("^[бвгджзклмнпрстфхцчшщьъ]*$")) {

        } else {
            set.add(prefix);
        }
        for (char ch : str.toCharArray()) {
            rearrange(prefix + ch, str.replaceFirst(ch + "", ""));

        }
    }

    public void returnWord() {

        InputStream fileInputStream = null;

        try{
            AssetManager assetManager = context.getAssets();
            fileInputStream = assetManager.open("vocabulary.txt");

            byte[] textContent = new byte[fileInputStream.available()];
            fileInputStream.read(textContent);
            String[] lines = new String(textContent).split("\n");
            Set<String> hashLines = new HashSet<String>(Arrays.asList(lines));

            for(String str : set) {
                if(hashLines.contains(str)) {
                    this.res.add(str);
                }
            }

        } catch(Exception a){
            Toast.makeText(context, a.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fileInputStream!= null) {
                    fileInputStream.close();

                    //this.res.clear();
                    //set.clear();
                }
            } catch(IOException ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}