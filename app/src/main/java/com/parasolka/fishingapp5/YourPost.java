package com.parasolka.fishingapp5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class YourPost extends AppCompatActivity {
    RecyclerView recyclerView;
    List<upload> prolist;
    FirebaseUser currentUser;
    Query postsQuery;
    MyAdapter adapter;
    ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_post);

        // Установка цвета статус-бара
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.blue));

        // Инициализация RecyclerView
        recyclerView = findViewById(R.id.yourpostrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Инициализация списка для хранения постов
        prolist = new ArrayList<>();

        // Получение текущего пользователя
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Получение ссылки на базу данных "posts" для текущего пользователя
        postsQuery = FirebaseDatabase.getInstance().getReference().child("posts").orderByChild("id2").equalTo(currentUser.getUid());

        // Создание адаптера и установка его в RecyclerView
        adapter = new MyAdapter(this, prolist);
        recyclerView.setAdapter(adapter);

        // Установка слушателя для получения данных из Firebase
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Очистка списка перед загрузкой новых данных
                prolist.clear();

                // Итерация по всем постам в базе данных
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Получение данных каждого поста и добавление их в список
                    upload upload = dataSnapshot.getValue(upload.class);
                    if (upload != null) {
                        prolist.add(upload);
                    } else {
                        Log.e("YourPost", "Upload is null");
                    }
                }

                // Уведомление адаптера об изменении данных
                adapter.notifyDataSetChanged();

                // Вывод отладочного сообщения с количеством загруженных постов
                Log.d("YourPost", "Data changed. Number of posts: " + prolist.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Вывод сообщения об ошибке при загрузке данных из Firebase
                Log.e("YourPost", "Error fetching data: " + error.getMessage());
            }
        };

        // Добавление слушателя к базе данных "posts"
        postsQuery.addValueEventListener(eventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Удаление слушателя при уничтожении активити
        if (postsQuery != null && eventListener != null) {
            postsQuery.removeEventListener(eventListener);
        }
    }
}
