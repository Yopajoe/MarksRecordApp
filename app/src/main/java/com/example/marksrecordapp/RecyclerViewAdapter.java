package com.example.marksrecordapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marksrecordapp.db.DnevnikDbHelper;
import com.example.marksrecordapp.utils.MyAppUtils;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context kontekst;
    private ArrayList<String[]> dataPredmet;

    public RecyclerViewAdapter(Context kontekst) {
        this.kontekst = kontekst;
        DnevnikDbHelper dbHelper = new DnevnikDbHelper(kontekst);
        //dataPredmet = dbHelper.getPremet();
        dataPredmet = MyAppUtils.getMockData();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(kontekst);
        View viewCard = inflater.inflate(R.layout.card_layout, parent, false);
        ViewHolder holder = new ViewHolder(viewCard);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      String ocene = dataPredmet.get(position)[1];
      String prosek = MyAppUtils.prosekOcena(MyAppUtils.getArrayOcena(ocene));
      holder.nazivPredmeta.setText(dataPredmet.get(position)[0]);
      holder.ocenePredmeta.setText(ocene);
      holder.prosekPredmeta.setText(prosek);
      holder.dugmePredmeta.setTag(position);
      holder.dugmePredmeta.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              MyAppUtils.onClickListenerOpcijePredmeta listener = (MyAppUtils.onClickListenerOpcijePredmeta) kontekst;
              listener.onClickOpcijePredmeta(view);
          }
      });
    }

    @Override
    public int getItemCount() {
        return dataPredmet.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nazivPredmeta;
        TextView ocenePredmeta;
        TextView prosekPredmeta;
        ImageButton dugmePredmeta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nazivPredmeta = itemView.findViewById(R.id.nazivCard);
            ocenePredmeta = itemView.findViewById(R.id.oceneCard);
            prosekPredmeta = itemView.findViewById(R.id.prosekCard);
            dugmePredmeta = itemView.findViewById(R.id.opcijeCard);
        }
    }


}
