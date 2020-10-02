package com.sundar.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sundar.demo.Model.Banner;
import com.sundar.demo.Model.Categroy;
import com.sundar.demo.Model.Data;
import com.sundar.demo.Model.DtaClass;
import com.sundar.demo.Model.TopItem;
import com.sundar.demo.Model.TopSelling;
import com.sundar.demo.Retrofit.ApiClient;
import com.sundar.demo.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //implements DataAdapter

    DtaClass dtaClass = new DtaClass();

    private ProgressDialog progressDialog;
    private DataAdapter adapter;

    //All list available
    private ApiInterface apiInterface;
    private ArrayList<Banner> banners;
    private ArrayList<TopItem> topItems;
    private List<Categroy> categroy;
    private List<TopSelling> topSelling;
    private RecyclerView recyclerview;


    Button BtnSubmit;
    private int Total_Amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtnSubmit = findViewById(R.id.BtnSubmit);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        generateData();
    }

    private void generateData() {
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        loadJson();

    }

    private void loadJson() {

        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        final Call<DtaClass> call = apiInterface.getData();

        call.enqueue(new Callback<DtaClass>() {
            @Override
            public void onResponse(Call<DtaClass> call, Response<DtaClass> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Log.e("TAG", "==success==>" + response.body());
                    dtaClass = response.body();

                    List<TopItem> topItems = new ArrayList<>();
                    topItems = dtaClass.getData().getTopItems();
                    // dtaClass = response.body();

                    Log.d("Sunder", topItems.toString());

                    adapter = new DataAdapter((ArrayList<TopItem>) topItems, MainActivity.this);
                    recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<DtaClass> call, Throwable t) {
                Log.e("TAG", "==failure==>" + t.getMessage());
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "went wrong !", Toast.LENGTH_SHORT).show();

            }
        });

        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Total_Amount==> ", Toast.LENGTH_SHORT).show();


              //  Log.i("Back", "CallBack===> ₹ = 80" + call);

                databaseList().notifyAll();;
                dtaClass.getData().getTopItems();
                databaseList().getClass();

               dtaClass.getData().setTopItems(topItems);
              //  TopItem topItem = dataModelList.get(position);

                Log.e("sub", "dtaData-------->" + databaseList().length);

            }
        });

    }
}