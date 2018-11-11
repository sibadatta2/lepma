package com.example.dewansh.lepma;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ashaOptions extends Fragment {
    private static final String TAG = "fragment1";



    public ashaOptions() {
        // Required empty public constructor
    }

    public static ashaOptions newInstance(String param1, String param2) {
        ashaOptions fragment = new ashaOptions();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
Button register ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ashaoption_layout, container, false);
        register=view.findViewById(R.id.register_sus_asha);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=null;
                fragment = new AshaFragmentSuspectRegisteration();
                loadFragment(fragment);

            }
        });
        return view;
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_asha, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
