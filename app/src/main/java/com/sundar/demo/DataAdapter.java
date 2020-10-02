package com.sundar.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.sundar.demo.Model.Banner;
import com.sundar.demo.Model.Data;
import com.sundar.demo.Model.DtaClass;
import com.sundar.demo.Model.TopItem;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

import static android.content.ContentValues.TAG;
import static com.sundar.demo.R.id.top;
import static com.sundar.demo.R.id.tv_Product_Quantity;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {


    private ArrayList<TopItem> dataModelList;
    // private List<TopItem> dataModelList = new ArrayList<>();

    private Context context;
    int counter = 0;

    private static int Total_Price;
    private static int remove;

    public DataAdapter(ArrayList<TopItem> dataModelList, Context context) {
        this.dataModelList = dataModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        TopItem topItem = dataModelList.get(position);

        if (!dataModelList.get(position).getImage().isEmpty()) {
            Picasso.with(context)
                    .load(dataModelList.get(position).getImage())
                    .placeholder(R.drawable.ic_launcher_background) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.ic_launcher_background) //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(holder.Image_Product);
        }

        // Log.d("Adapter_ID: ", DataAdapter.class.toString());

        //  Log.d(TAG, "AdapteData" + DataAdapter.class.toString());

        holder.Tv_id.setText("ID No = " + dataModelList.get(position).getId());
        holder.Tv_Name.setText(dataModelList.get(position).getName()); //Data show in rec
        holder.Tv_Quantity.setText("Quantity = " + dataModelList.get(position).getQuantity());
        holder.Tv_Price.setText("Price ₹ = " + dataModelList.get(position).getPrice());

//        holder.Tv_Unit.setText(dataModelList.get(position).getUnit()); //Data show in rec
        //       holder.Tv_Product_Categrory_ID.setText(dataModelList.get(position).getProduct_category_id()); //Data show in rec


        //    holder.Tv_Price.setText(dataModelList.get(position).getPrice());
        ///    holder.Tv_Sale.setText(dataModelList.get(position).getSalePrice());
        //    holder.Tv_Sale_Price.setText(dataModelList.get(position).getSalePrice());
        // holder.Tv_ProductBrand_ID.setText(dataModelList.get(position).getProductBrandId());

//        holder.Tv_Prices.setText(dataModelList.get(position).getPrice_off());

        holder.tv_Product_Quantity.setText(counter + "");
        holder.Btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                holder.tv_Product_Quantity.setText(counter + "");
                int price = dataModelList.get(position).price;
                Total_Price = price * counter;
                holder.Tv_Price_Total.setText("Total Price ₹ = " + Total_Price);
                System.out.println(Total_Price);
                Log.d("total", "tl ===>" + Total_Price);


            }
        });

        holder.Btn_Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter != 0) {

                    holder.tv_Product_Quantity.setText(counter + "");
                    int price = dataModelList.get(position).price;
                    remove = Total_Price - price;
                    Log.d("Decrease", "D========>" + remove);
                    holder.Tv_Price_Total.setText("Total Price ₹ = " + remove);
                    counter--;

                }

            }
        });

       /* holder.BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {


               // holder.tv_Product_Quantity.setHint(Toast.makeText(context, ""));
              //  Toast.makeText(context, "Quqlity" + tv_Product_Quantity.getText.toSting, Toast.LENGTH_SHORT).show();

                //Toast.makeText(context,"Hello"+ tv.getText().toString(),Toast.LENGTH_SHORT).show();
              //  Toast.makeText(context, "counter", Toast.LENGTH_SHORT).show();

                Toast toast = Toast.makeText(context,"Total Value",Toast.LENGTH_LONG);
                toast.setText(tv_Product_Quantity);
                toast.setGravity(Gravity.BOTTOM,0,250);
                toast.show();



            }
        });*/

    }


    @Override
    public int getItemCount() {
        return dataModelList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView Image_Product;
        TextView Tv_id, Tv_Name, Tv_Quantity, Tv_Unit, Tv_Sale, Tv_Sale_Price, Tv_Product_Categrory_ID,
                Tv_ProductBrand_ID, Tv_Prices, Tv_Price, tv_Product_Quantity, Tv_Price_Total;
        Button Btn_Add, Btn_Remove, BtnSubmit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Image_Product = (ImageView) itemView.findViewById(R.id.Image_Product);

            Tv_id = (TextView) itemView.findViewById(R.id.Tv_id);

            Tv_Name = (TextView) itemView.findViewById(R.id.Tv_Name);

            Tv_Quantity = (TextView) itemView.findViewById(R.id.Tv_Quantity);

            /*Tv_Unit = (TextView) itemView.findViewById(R.id.Tv_Unit);

            Tv_Sale = (TextView) itemView.findViewById(R.id.Tv_Sale);

            Tv_Sale_Price = (TextView) itemView.findViewById(R.id.Tv_Sale_Price);

            Tv_Product_Categrory_ID = (TextView) itemView.findViewById(R.id.Tv_Product_Categrory_ID);

            Tv_ProductBrand_ID = (TextView) itemView.findViewById(R.id.Tv_ProductBrand_ID);

            Tv_Prices = (TextView) itemView.findViewById(R.id.Tv_Prices);
*/
            Tv_Price = (TextView) itemView.findViewById(R.id.Tv_Price);
            Tv_Price_Total = (TextView) itemView.findViewById(R.id.Tv_Price_Total);


            //Button Action in Display Data in text view
            tv_Product_Quantity = (TextView) itemView.findViewById(R.id.tv_Product_Quantity);
            Btn_Add = (Button) itemView.findViewById(R.id.Btn_Add);
            Btn_Remove = (Button) itemView.findViewById(R.id.Btn_Remove);
            // BtnSubmit = (Button) itemView.findViewById(R.id.BtnSubmit);


        }
    }



}
