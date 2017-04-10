package com.example.c4cib.map1111.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.c4cib.map1111.R;

/**
 * Created by c4cib on 21/10/2016.
 */

public class fragAct extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
                // Inflate the layout for
                View v = inflater.inflate(R.layout.fragmentact, container, false);
                String userName=null;
                Button action = (Button) v.findViewById(R.id.a_b);
                Button logout = (Button) v.findViewById(R.id.logout);
                Button user = (Button) v.findViewById(R.id.a_user);

                action.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), actHome.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);


                        }
                });
                logout.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), actLogin.class);

                                startActivity(intent);


                        }
                });

                user.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), actUserManage.class);

                                startActivity(intent);

                        }
                });

                return v;
        }
}
