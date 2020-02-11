package com.example.mick.dockandroidlogin.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mick.dockandroidlogin.DatabaseHandler;
import com.example.mick.dockandroidlogin.R;
import com.example.mick.dockandroidlogin.sampling.TripArchieve;
import com.example.mick.dockandroidlogin.sampling.TripProcess;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import static com.example.mick.dockandroidlogin.Login.my_shared_preferences;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Trip.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Trip#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Trip extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SharedPreferences sharedpreferences ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Intent i , j ;
    BarChart barChart ;

    private OnFragmentInteractionListener mListener;

    public Trip() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Trip.
     */
    // TODO: Rename and change types and number of parameters
    public static Trip newInstance(String param1, String param2) {
        Trip fragment = new Trip();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        i = new Intent(Trip.this.getActivity(),TripProcess.class);
        getActivity().findViewById(R.id.process).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        j = new Intent(Trip.this.getActivity(),TripArchieve.class);
        getActivity().findViewById(R.id.archieve).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(j);
            }
        });



        barChart = (BarChart) getActivity().findViewById(R.id.barGraph) ;

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> theDates = new ArrayList<String>();

        /*
        barEntries.add(new BarEntry(20, 0));
        barEntries.add(new BarEntry(4000, 1));
        barEntries.add(new BarEntry(6000, 2));
        barEntries.add(new BarEntry(800, 3));
        barEntries.add(new BarEntry(7000, 4));
        barEntries.add(new BarEntry(3000, 5));
        theDates.add("January");
        theDates.add("February");
        theDates.add("March");
        theDates.add("April");
        theDates.add("May");
        theDates.add("June");
        */

        BarDataSet barDataSet = new BarDataSet(barEntries, "Kode Pendaratan") ;

        SharedPreferences sharedpreferences = this.getContext().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        String idUser = sharedpreferences.getString("id", null);

        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(" select distinct(t.k_tpi) , sum(i.total_tangkapan) from tb_master_tpi t ,  tb_trip_lists l , tb_trip_info i  " +
                "where t.k_tpi = l.k_tpi and l.id = i.idTrip and l.status in ('process' , 'archieve') and l.id_pengguna = '"+ idUser +"' " +
                " group by t.k_tpi order by n_tpi ", null);
        if (cursor.getCount() > 0) {
            int i = 0;
            if (cursor.moveToFirst()) {
                do {


                    System.out.println(cursor.getString(0));
                    System.out.println(cursor.getString(1));

                    //String nilai = cursor.getString(1).replace(".", "");
                    String nilai = cursor.getString(1);
                    double nilaii = Double.parseDouble(nilai);
                    double nilaiii = Math.floor(nilaii);
                    int nilaiiii = (int) nilaiii;

                    //barEntries.add(new BarEntry(Math.round(Integer.parseInt ( nilaiiii )), i));
                    barEntries.add(new BarEntry(Math.round(  nilaiiii ), i));
                    theDates.add(cursor.getString(0));

                    i++;
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();

        BarData theData = new BarData(theDates, barDataSet);
        barChart.setData(theData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
