package com.aniskhenissa.ciphercaesar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aniskhenissa.ciphercaesar.Models.CaesarDocuementModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SavesActivvity extends AppCompatActivity {
    private FirestoreRecyclerAdapter<CaesarDocuementModel,CaesarHolder> caesar_adapter;
    private FirebaseFirestore db_ref;
    private RecyclerView doc_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saves_activvity);
        db_ref = FirebaseFirestore.getInstance();
        doc_recycler = findViewById(R.id.doc_recycler);
        Query query = db_ref.collection("SubstitutionDocuments");
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<CaesarDocuementModel>().setQuery(query,CaesarDocuementModel.class).build();
        caesar_adapter = new FirestoreRecyclerAdapter<CaesarDocuementModel, CaesarHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CaesarHolder holder, int position, @NonNull CaesarDocuementModel model) {
                holder.send(model.getConverted(),model.getData(),model.getKey(),model.getName(),model.getShift(),model.getTime());
            }

            @NonNull
            @Override
            public CaesarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View doc_view = LayoutInflater.from(SavesActivvity.this).inflate(R.layout.row_docs,parent,false);
                return new CaesarHolder(doc_view);
            }
        };
        doc_recycler.setAdapter(caesar_adapter);
        doc_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        caesar_adapter.startListening();
    }

    public class CaesarHolder extends RecyclerView.ViewHolder{
        private TextView doc_date,doc_convert,doc_data,doc_name,doc_shift;

        public CaesarHolder(@NonNull View itemView) {
            super(itemView);
            doc_date = itemView.findViewById(R.id.doc_date);
            doc_convert = itemView.findViewById(R.id.doc_convert);
            doc_name = itemView.findViewById(R.id.doc_name);
            doc_data = itemView.findViewById(R.id.doc_data);
            doc_shift = itemView.findViewById(R.id.doc_shift);
        }

        public void send(String converted, String data, int key, String name, int shift, long time) {
            doc_data.setText(data);
            doc_name.setText(name);
            doc_shift.setText("Shift: "+shift);
            Date date = new Date(time);
            doc_date.setText(new SimpleDateFormat("yyyy-MM-dd:hh:mm").format(date));
            doc_convert.setText(converted);
        }
    }
}