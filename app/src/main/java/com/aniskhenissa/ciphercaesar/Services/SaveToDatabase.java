package com.aniskhenissa.ciphercaesar.Services;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.aniskhenissa.ciphercaesar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Random;

public class SaveToDatabase implements Runnable {
    public FirebaseFirestore db_ref;
    public BottomSheetDialog save_dialog;
    public Activity context;
    private Random random;
    private int key,shift;
    private TextView save_to_db_key;
    private CardView save_to_db_btn;
    private MaterialEditText save_to_db_name;
    public String data,converted_data;
    public SaveToDatabase(Activity context, String data, String converted_data, int shift) {
        this.context = context;
        this.data = data;
        this.converted_data = converted_data;
        this.shift = shift;
    }

    @Override
    public void run() {
        db_ref = FirebaseFirestore.getInstance();
        save_dialog = new BottomSheetDialog(context);
        View save_view = LayoutInflater.from(context).inflate(R.layout.bottom_save_to_db,null);
        save_dialog.setContentView(save_view);
        save_to_db_key = save_view.findViewById(R.id.save_to_db_key);
        save_to_db_btn = save_view.findViewById(R.id.save_to_db_btn);
        save_to_db_name = save_view.findViewById(R.id.save_to_db_name);
        save_dialog.show();
        //random key
        random = new Random();
        key = random.nextInt(9000000);
        save_to_db_key.setText(""+key);
        save_to_db_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> data_hash = new HashMap<>();
                if (!save_to_db_name.getText().toString().isEmpty()){
                    data_hash.put("key",key);
                    data_hash.put("name",save_to_db_name.getText().toString());
                    data_hash.put("time",System.currentTimeMillis());
                    data_hash.put("data",data);
                    data_hash.put("converted",converted_data);
                    data_hash.put("shift",shift);
                    db_ref.collection("SubstitutionDocuments").add(data_hash).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(context, "Added To Database Under Key: "+documentReference.getId()  , Toast.LENGTH_SHORT).show();
                            save_dialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "There is some error during adding data please try again later", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(context, "Please Enter All Infos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
