package com.sasadev.todo.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sasadev.todo.MainActivity;
import com.sasadev.todo.R;
import com.sasadev.todo.models.User;
import com.sasadev.todo.utils.InputValidator;
import com.sasadev.todo.utils.PasswordUtils;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.bson.types.ObjectId;

import io.realm.Realm;
import io.realm.RealmResults;


public class SignupFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SignupFragment() {
        // Required empty public constructor
    }


    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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

        Realm realm = Realm.getDefaultInstance();

        // Inflate the layout for this fragment
        View inflateView  = inflater.inflate(R.layout.fragment_signup, container, false);
        TextView textView = inflateView.findViewById(R.id.sign_in_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.authFrameLayout, new LoginFragment())
                        .commit();
            }
        });

        EditText username = inflateView.findViewById(R.id.signup_username_edit_text);
        EditText password = inflateView.findViewById(R.id.signup_password_edit_text);
        EditText mobile = inflateView.findViewById(R.id.signup_mobile_edit_text);

        Button button = inflateView.findViewById(R.id.sign_up_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usernameText = username.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                String mobileText = mobile.getText().toString().trim();

                if(!InputValidator.isValidUsername(usernameText)){
                    FancyToast.makeText(inflateView.getContext(),"Invalid Username",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    return;
                }else if(!InputValidator.isMobileValid(mobileText)){
                    FancyToast.makeText(inflateView.getContext(),"Invalid Mobile",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    return;
                } else if(!InputValidator.isPasswordValid(passwordText)){
                    FancyToast.makeText(inflateView.getContext(),"Invalid Password",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    return;
                }else{

                    RealmResults<User> user = realm.where(User.class).equalTo("username",usernameText).findAll();
                    if(!user.isEmpty()){
                        FancyToast.makeText(inflateView.getContext(),"Username Already exist",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        return;
                    }

                    try {
                        //password hashing
                        char[] passwordChar = passwordText.toCharArray();
                        byte[] salt = PasswordUtils.generateSalt();
                        byte[] hashedPassword = PasswordUtils.hashPassword(passwordChar,salt);

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                User user = realm.createObject(User.class, usernameText);
                                user.setPassword(hashedPassword);
                                user.setMobile(mobileText);
                                user.setSalt(salt);

                            }
                        });


                        FancyToast.makeText(inflateView.getContext(),"Sign Up Successful",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                        //save details locally
                        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username",usernameText);
                        editor.putString("mobile",mobileText);
                        editor.apply();

                        realm.close();

                        Intent intent = new Intent(inflateView.getContext(), MainActivity.class);
                        startActivity(intent);
                        requireActivity().finish();

                    } catch (Exception e) {
                        FancyToast.makeText(inflateView.getContext(),"Something went wrong!",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        throw new RuntimeException(e);
                    }


                }


            }
        });

        return inflateView;
    }
}