package com.aniskhenissa.ciphercaesar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.aniskhenissa.ciphercaesar.Interfaces.Alphabets;
import com.aniskhenissa.ciphercaesar.Services.SaveToDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;


public class MainActivity extends AppCompatActivity {
    private LinearLayout remove_shift_btn,add_shift_btn,result_layout;
    private MaterialEditText shift_edit_text,data_edit_text;
    public static int shift = 0;
    public static StringBuilder convert_word = new StringBuilder();
    public String data;
    public static TextView result_text;
    private RadioGroup subtitution_radio_groupe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_shift_btn = findViewById(R.id.add_shift_btn);
        remove_shift_btn = findViewById(R.id.remove_shift_btn);
        data_edit_text = findViewById(R.id.data_edit_text);
        shift_edit_text = findViewById(R.id.shift_edit_text);
        result_layout = findViewById(R.id.result_layout);
        result_text = findViewById(R.id.result_text);
        subtitution_radio_groupe = findViewById(R.id.subtitution_radio_groupe);
        //check when user click on check button
        remove_shift_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shift = Integer.parseInt(shift_edit_text.getText().toString());
                if (shift > 0){
                    shift_edit_text.setText(""+--shift);
                }else {
                    Toast.makeText(getApplicationContext(), "Only Positive Numbers Allowed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        add_shift_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shift = Integer.parseInt(shift_edit_text.getText().toString());
                shift++;
                shift_edit_text.setText(""+shift);
            }
        });
        findViewById(R.id.convert_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (data_edit_text.getText().toString().isEmpty() || shift_edit_text.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Please Fill correct Information", Toast.LENGTH_LONG).show();
                    }else {
                        data = data_edit_text.getText().toString();
                        shift = Integer.parseInt(shift_edit_text.getText().toString());
                        switch (subtitution_radio_groupe.getCheckedRadioButtonId()){
                            case R.id.subtitution_encode:
                                new Encrypt(data,shift).run();
                                break;
                            case R.id.subtitution_decode:
                                new Decrypt(data,shift).run();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "You Didn't check any type ,by default the method is Encode", Toast.LENGTH_LONG).show();
                                new Encrypt(data,shift).run();
                                break;
                        }
                    }
            }
        });
        //save to db section
        findViewById(R.id.save_result_to_db_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SaveToDatabase(MainActivity.this,data_edit_text.getText().toString(),
                        result_text.getText().toString(),shift).run();
            }
        });
        findViewById(R.id.go_to_saves_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SavesActivvity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        findViewById(R.id.refresh_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
    }
    public class Encrypt implements Runnable{
        public String data;
        public int shift;

        public Encrypt(String data, int shift) {
            this.data = data.toLowerCase();
            this.shift = shift;
        }
        @Override
        public void run() {
            convert_word = new StringBuilder();
            for (int i = 0; i < data.length(); i++) {
                int start_pos = Alphabets.letters.indexOf(data.charAt(i));
                int pos = (shift + start_pos) % 26;
                if (data.charAt(i) == ' '){
                    convert_word.append(' ');
                    continue;
                }
                convert_word.append(Alphabets.letters.charAt(pos));
            }
            result_layout.setVisibility(convert_word.length() == 0 ? View.GONE : View.VISIBLE);
            result_text.setText(convert_word);
        }
    }
    public class Decrypt implements Runnable{
        public String data;
        public int shift;

        public Decrypt(String data, int shift) {
            this.data = data;
            this.shift = shift;
        }

        @Override
        public void run() {
            convert_word = new StringBuilder();
            for (int i = 0; i < data.length(); i++) {
                int start_pos = Alphabets.letters.indexOf(data.charAt(i));
                int pos = (start_pos - shift) % 26;
                if (pos < 0) {
                    pos = Alphabets.letters.length() + pos;
                }
                if (data.charAt(i) == ' '){
                    convert_word.append(' ');
                    continue;
                }
                convert_word.append(Alphabets.letters.charAt(pos));
            }
            result_layout.setVisibility(convert_word.length() == 0 ? View.GONE : View.VISIBLE);
            result_text.setText(convert_word);
        }
    }
}