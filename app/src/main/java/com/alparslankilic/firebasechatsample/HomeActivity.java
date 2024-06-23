package com.alparslankilic.firebasechatsample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ListView listView;
    private FirebaseAuth fAuth;
    private ArrayList<String> subjectLists = new ArrayList<>();
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private ArrayAdapter<String> adapter;
    public static String seciliKonu = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseApp.initializeApp(this);

        fAuth = FirebaseAuth.getInstance();

        listView = (ListView)findViewById(R.id.listViewSubjects);

        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("ChatSubjects");

        adapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1,subjectLists);
        listView.setAdapter(adapter);

        //observer design pattern
        //firebase deki database yeni bir kayıt eklendiğinde tetiklenir
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subjectLists.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    subjectLists.add(ds.getKey());
                    Log.d("LOGVALUE",ds.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        });

        //listenen chat başlıklarından herhangi birinin tıklandığında o konu başlığıyla ilgili chat ekranına gidilecek.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seciliKonu = subjectLists.get(position);
                Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
                intent.putExtra("subject",subjectLists.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.exit)
        {
            fAuth.signOut();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}