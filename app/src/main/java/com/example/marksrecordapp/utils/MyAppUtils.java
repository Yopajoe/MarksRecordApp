package com.example.marksrecordapp.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.marksrecordapp.db.DnevnikDbHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public interface MyAppUtils {


    public interface onClickListenerOpcijePredmeta{

        void onClickOpcijePredmeta(View view);
        default void onSelectedItemOpcijePredmeta(@Nullable View view, Bundle args) { };

    }


    static void test(Context context){
        DnevnikDbHelper dbHelper = new DnevnikDbHelper(context);
        //TEST CODE
        //dbHelper.addPredmet("Srpski jezik");
        //dbHelper.deletePredmet("Srpski jezik");
        //dbHelper.addOcena("Srpski jezik", 3);
        //String[] predmet  = dbHelper.getPredmet("Matematika");
        //ArrayList<String[]> predmeti = dbHelper.getPremet();

    }

    static ArrayList<String[]> getMockData(){
        ArrayList izlaz = new ArrayList();
        izlaz.add(new String[]{"Likovno"," 2 2 5"});
        izlaz.add(new String[]{"Fizicko"," 2 3 3 5"});
        return izlaz;
    }

    //STRING  " 5 4 5" -> INT [ 5, 4, 5]
    @NonNull
    static int[] getArrayOcena(@NonNull String ocena_str){
        final int CODE_0 = 48;
        final int CODE_6 = 54;
        int[] izlaz = new int[20];
        int j=0;
        for(int i=0; i<ocena_str.length();i++){
            int code = ocena_str.charAt(i);
            if(code > CODE_0 && code < CODE_6) izlaz[j++]= code - CODE_0;
        }
        return izlaz;
    }

    //INT [ 1, 2 5 ] -> "STRING 1 2 5"
    @NonNull
    static String getStringArray(@NonNull int[] ocene_int){
        StringBuilder izlaz = new StringBuilder();
        for(int o:ocene_int){
            if(o==0) return izlaz.toString();
            izlaz.append(" ").append(o); //  o = 1 -> izlaz = izlaz + " 0"
        }
        return izlaz.toString();
    }

    @NonNull
    static int findFirstIndexOfIntArray(int[] array, int element){
        for(int i=0; i<array.length;i++){
            if(array[i] == element) return i;
        }
        return -1;
    }

    @NonNull
    static String prosekOcena(int[] ocene){
        double izlaz = 0.0d;
        for(int i = 0; i<ocene.length;i++){
            if(ocene[i] == 0) {
                return new DecimalFormat("#.##").format(izlaz/i);
            }
            izlaz += ocene[i];
        }
        return new DecimalFormat("#.##").format(izlaz/ocene.length);
    }

   static CharSequence[] listaOcena(int[] ocena_int){
        int length = MyAppUtils.findFirstIndexOfIntArray(ocena_int,0);
        CharSequence[] izlaz = new CharSequence[length];
        for(int i =0; i<length;i++){
            izlaz[i]= String.valueOf(ocena_int[i]);
        }
        return izlaz;
   }

}
