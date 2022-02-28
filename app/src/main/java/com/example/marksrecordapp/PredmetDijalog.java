package com.example.marksrecordapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.marksrecordapp.utils.MyAppUtils;

public class PredmetDijalog extends DialogFragment {

    public static final String AKCIJA_DIJALOG = "Akcija Dijalog";
    public static final String DODAJ_OCENU = "Dodaj Ocenu";
    public static final String OBRISI_OCENU = "Obrisi Ocenu";
    public static final String OBRISI_PREDMET = "Obrisi Predmet";
    public static final String DODAJ_PREDMET = "Dodaj Predmet";

    public PredmetDijalog(MyAppUtils.onClickListenerOpcijePredmeta listenerOpcijePredmeta) {
        this.listenerOpcijePredmeta = listenerOpcijePredmeta;
    }

    private MyAppUtils.onClickListenerOpcijePredmeta listenerOpcijePredmeta;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        String akcija = args.getString(PredmetDijalog.AKCIJA_DIJALOG);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Dialog dijalog;
        switch (akcija){
            case DODAJ_OCENU:{
                builder.setTitle("Odaberi ocenu");
                builder.setView(R.layout.dialog_dodaj_ocenu);
                builder.setPositiveButton("Potvrdi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Dialog dialog = (Dialog) dialogInterface;
                        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroupDijalog);
                        RadioButton radioButton = dialog.findViewById(radioGroup.getCheckedRadioButtonId());
                        int ocena  = Integer.parseInt(radioButton.getText().toString());
                        Bundle args = new Bundle();
                        args.putInt(PredmetDijalog.DODAJ_OCENU,ocena);
                        args.putString(PredmetDijalog.AKCIJA_DIJALOG,PredmetDijalog.DODAJ_OCENU);
                        listenerOpcijePredmeta.onSelectedItemOpcijePredmeta(null,args);
                    }
                });
                dijalog = builder.create();
                return dijalog;
            }
            case OBRISI_OCENU:{
                builder.setTitle("Odaberi ocenu");
                // "1 4 3" -> -> int [1 4 3] -> ["1","4","3"]
                int[] ocene_int = MyAppUtils.getArrayOcena(args.getString(OBRISI_OCENU));
                CharSequence[] ocene = MyAppUtils.listaOcena(ocene_int);
                builder.setItems(ocene, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle args = new Bundle();
                        args.putInt(OBRISI_OCENU,i);
                        args.putString(AKCIJA_DIJALOG,OBRISI_OCENU);
                        listenerOpcijePredmeta.onSelectedItemOpcijePredmeta(null,args);
                    }
                });
                builder.setNegativeButton("Ponisti", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dijalog = builder.create();
                return dijalog;
            }
            case OBRISI_PREDMET:{
                builder.setTitle("Brisanje predmeta");
                String predmet = getArguments().getString(OBRISI_PREDMET);
                builder.setMessage("Potvrdite da bi ste obrisali "+ predmet+".");
                builder.setPositiveButton("Potvrdi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle args = new Bundle();
                        args.putString(AKCIJA_DIJALOG,OBRISI_PREDMET);
                        listenerOpcijePredmeta.onSelectedItemOpcijePredmeta(null,args);
                    }
                });
                builder.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dijalog = builder.create();
                return dijalog;
            }
        }
        return super.onCreateDialog(savedInstanceState);
    }
}
