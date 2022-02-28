package com.example.marksrecordapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.telephony.PreciseDataConnectionState;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.marksrecordapp.db.DnevnikDbHelper;
import com.example.marksrecordapp.utils.MyAppUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements  MyAppUtils.onClickListenerOpcijePredmeta{

    private Integer position;
    private DnevnikDbHelper dbHelper;
    private String[] predmet;
    private RecyclerView recycler;
    private RecyclerViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MyAppUtils.test(this);
       //dbHelper = new DnevnikDbHelper(this);
        recycler = findViewById(R.id.recycler);
        adapter = new RecyclerViewAdapter(this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

    }

    @Override
    public void onClickOpcijePredmeta(View view) {
        position = (Integer) view.getTag();
        //ArrayList<String[]> predmeti = dbHelper.getPremet();
        ArrayList<String[]> predmeti = MyAppUtils.getMockData();
        predmet = predmeti.get(position.intValue());
        PopupMenu popup = new PopupMenu(this,view);
        popup.getMenuInflater().inflate(R.menu.menu_card, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Bundle args = new Bundle();
                switch (menuItem.getItemId()){
                    case R.id.dodaj_ocenu:{
                        args.putString(PredmetDijalog.AKCIJA_DIJALOG,PredmetDijalog.DODAJ_OCENU);
                        PredmetDijalog dijalog = new PredmetDijalog(MainActivity.this);
                        dijalog.setArguments(args);
                        dijalog.show(getSupportFragmentManager(), PredmetDijalog.DODAJ_OCENU);
                        return true;
                    }
                    case R.id.obrisi_ocenu:{
                        args.putString(PredmetDijalog.AKCIJA_DIJALOG,PredmetDijalog.OBRISI_OCENU);
                        args.putString(PredmetDijalog.OBRISI_OCENU,predmet[1]);
                        PredmetDijalog dijalog = new PredmetDijalog(MainActivity.this);
                        dijalog.setArguments(args);
                        dijalog.show(getSupportFragmentManager(), PredmetDijalog.OBRISI_OCENU);
                        return true;
                    }
                    case R.id.obrisi_predmet:{
                        args.putString(PredmetDijalog.AKCIJA_DIJALOG,PredmetDijalog.OBRISI_PREDMET);
                        args.putString(PredmetDijalog.OBRISI_PREDMET,predmet[0]);
                        PredmetDijalog dijalog = new PredmetDijalog(MainActivity.this);
                        dijalog.setArguments(args);
                        dijalog.show(getSupportFragmentManager(), PredmetDijalog.OBRISI_PREDMET);
                        return true;
                    }
                    default: return false;
                }
            }
        });
        popup.show();

    }

    @Override
    public void onSelectedItemOpcijePredmeta(@Nullable View view, Bundle args) {
        String akcija = args.getString(PredmetDijalog.AKCIJA_DIJALOG);
        switch (akcija){
            case PredmetDijalog.DODAJ_OCENU:{
                int ocena = args.getInt(PredmetDijalog.DODAJ_OCENU);
                Toast.makeText(this, akcija+ " "+ocena, Toast.LENGTH_SHORT).show();
                break;
            }
            case PredmetDijalog.OBRISI_OCENU:{
                int index = args.getInt(PredmetDijalog.OBRISI_OCENU);
                Toast.makeText(this, akcija+ " "+index, Toast.LENGTH_SHORT).show();
                break;
            }
            case PredmetDijalog.OBRISI_PREDMET:{
                Toast.makeText(this, akcija+ " "+predmet[0], Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }


}