package com.lightcore.goaltracker_pro.ui.Profile;

import static com.lightcore.goaltracker_pro.ui.navnav.RC_SIGN_IN;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lightcore.goaltracker_pro.R;
import com.lightcore.goaltracker_pro.ui.navnav;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String uid = "";
    private String uname = "";

    TextView userName, userEmail;
    ImageView ava;
    FirebaseUser user;
    LinearLayout reglayout, deflayout;
    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
            uid = getArguments().getString(ARG_PARAM1);
            uname = getArguments().getString(ARG_PARAM2);
            Log.d("ARGS", uid + uname);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);
        View root = view.getRootView();
        reglayout = root.findViewById(R.id.register);
        deflayout = root.findViewById(R.id.defProfile);
        userName = (TextView) root.findViewById(R.id.profileName);
        userEmail = (TextView) root.findViewById(R.id.profileMail);
        ava = (ImageView) root.findViewById(R.id.avaInProfile);
        userName.setText(uname);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            reglayout.setVisibility(View.INVISIBLE);
            deflayout.setVisibility(View.VISIBLE);
            userName.setText(user.getDisplayName());
            userEmail.setText(user.getEmail());
            Picasso.get().load(user.getPhotoUrl()).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_menu_camera).into(ava);
            Log.d("TAG", user.getDisplayName()+ uname+ user.getPhotoUrl());
        } else {
            reglayout.setVisibility(View.VISIBLE);
            deflayout.setVisibility(View.INVISIBLE);
            Log.d("TAG", uname+ "sd");
            SignInButton signInButton = (SignInButton) root.findViewById(R.id.sign_in_button_in_profile);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
        // Inflate the layout for this fragment
        return view;
    }
}