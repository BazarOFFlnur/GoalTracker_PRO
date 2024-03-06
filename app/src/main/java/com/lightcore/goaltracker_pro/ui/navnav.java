package com.lightcore.goaltracker_pro.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
//import com.lightcore.goaltracker_pro.test.Tefs;
import com.lightcore.goaltracker_pro.R;
import com.lightcore.goaltracker_pro.databinding.ActivityNavnavBinding;
import com.lightcore.goaltracker_pro.ui.testr.Sec;
import com.lightcore.goaltracker_pro.ui.testr.Tsd;
//import com.lightcore.goaltracker_pro.ui.testr.Tasf;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

//import dagger.hilt.android.AndroidEntryPoint;
@AndroidEntryPoint
public class navnav extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavnavBinding binding;
    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    TextView demail, dname;
    SignInButton mSignInButton;
    ListView lv;
    @Inject
    Tsd tsd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        Tasf tasf;
        Log.d("RSd", tsd.exec());

        mAuth = FirebaseAuth.getInstance();
        binding = ActivityNavnavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarNavnav.toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View v = navigationView.getHeaderView(0);
        mSignInButton = v.findViewById(R.id.sign_in_button);
        dname = v.findViewById(R.id.dname);
        demail = v.findViewById(R.id.Email);
        if (mAuth.getCurrentUser()==null){
            mSignInButton.setVisibility(View.VISIBLE);
            dname.setVisibility(View.INVISIBLE);
            demail.setVisibility(View.INVISIBLE);
        } else {
            dname.setText(mAuth.getCurrentUser().getDisplayName());
            demail.setText(mAuth.getCurrentUser().getEmail());
            demail.setOnClickListener(v1 -> {
                ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", demail.getText().toString());
                clipboard.setPrimaryClip(clip);
            });
            mSignInButton.setVisibility(View.INVISIBLE);
            dname.setVisibility(View.VISIBLE);
            demail.setVisibility(View.VISIBLE);
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navnav);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        lv.findViewById(R.id.lv);
//        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                return false;
//            }
//        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Запускаем намерение для входа
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Проверяем, вошел ли пользователь, и обновляем интерфейс
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Обрабатываем результат намерения для входа
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Вход успешен, аутентифицируемся с Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Вход не удался, выводим сообщение об ошибке
                Log.w(TAG, "Google sign in failed: ", e);
                Toast.makeText(this, "Google sign in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                updateUI(null);
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        // Получаем учетные данные
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        // Входим с помощью учетных данных
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Вход успешен, обновляем интерфейс с данными пользователя
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
//                            if (mAuth.getCurrentUser()==null){
//                                mSignInButton.setVisibility(View.VISIBLE);
//                                dname.setVisibility(View.INVISIBLE);
//                                demail.setVisibility(View.INVISIBLE);
//                            } else {
//                                dname.setText(mAuth.getCurrentUser().getDisplayName());
//                                demail.setText(mAuth.getCurrentUser().getEmail());
//                                demail.setOnClickListener(v1 -> {
//                                    ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
//                                    ClipData clip = ClipData.newPlainText("", demail.getText().toString());
//                                    clipboard.setPrimaryClip(clip);
//                                });
//                                mSignInButton.setVisibility(View.INVISIBLE);
//                                dname.setVisibility(View.VISIBLE);
//                                demail.setVisibility(View.VISIBLE);
//                            }
                        } else {
                            // Вход не удался, выводим сообщение об ошибке
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {

            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo = Objects.requireNonNull(user.getPhotoUrl()).toString();
            if (mAuth.getCurrentUser()==null){
                                mSignInButton.setVisibility(View.VISIBLE);
                                dname.setVisibility(View.INVISIBLE);
                                demail.setVisibility(View.INVISIBLE);
                            } else {
                                dname.setText(mAuth.getCurrentUser().getDisplayName());
                                demail.setText(mAuth.getCurrentUser().getEmail());
                                demail.setOnClickListener(v1 -> {
                                    ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("", demail.getText().toString());
                                    clipboard.setPrimaryClip(clip);
                                });
                                mSignInButton.setVisibility(View.INVISIBLE);
                                dname.setVisibility(View.VISIBLE);
                                demail.setVisibility(View.VISIBLE);
                            }
        } else {
            Log.d("asd","qwe");
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navnav, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if (itemId == R.id.action_settings) {
            mAuth.signOut();
            Log.d("ms", "set");
            updateUI(mAuth.getCurrentUser());
            return true;
        } else if (itemId == R.id.action_feedback) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:bazarovelnur@gmail.com")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, "bazarovelnur@gmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, "error");
            startActivity(intent);
        } else {
            super.onOptionsItemSelected(item);
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navnav);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}