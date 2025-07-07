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

import com.sasadev.todo.MainActivity;
import com.sasadev.todo.R;
import com.sasadev.todo.models.User;
import com.sasadev.todo.utils.InputValidator;
import com.sasadev.todo.utils.PasswordUtils;
import com.shashank.sony.fancytoastlib.FancyToast;

import io.realm.Realm;


public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View inflateView = inflater.inflate(R.layout.fragment_login,container,false);
        TextView textView = inflateView.findViewById(R.id.sign_up_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.authFrameLayout, new SignupFragment())
                        .commit();
            }
        });

        EditText username = inflateView.findViewById(R.id.signin_username_edit_text);
        EditText password = inflateView.findViewById(R.id.signin_password_edit_text);
        Button button = inflateView.findViewById(R.id.sign_in_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordText = password.getText().toString().trim();
                String usernameText = username.getText().toString().trim();

                if(!InputValidator.isValidUsername(usernameText)){
                    FancyToast.makeText(inflateView.getContext(),"Invalid Username",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    return;
                }else if(!InputValidator.isPasswordValid(passwordText)){
                    FancyToast.makeText(inflateView.getContext(),"Invalid Password",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    return;
                }else{

                    Realm realm = Realm.getDefaultInstance();
                    User user = realm.where(User.class).equalTo("username",usernameText).findFirst();


                    Log.i("sasa",user.getUsername() + " " + user.getPassword() + " " + user.getSalt() + " " + user.getMobile());

                    if(user.getPassword() == null || user.getSalt() == null ){
                        FancyToast.makeText(inflateView.getContext(), "User not found. Please sign up.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        realm.close();
                        return;
                    }

                    if(user == null){
                        FancyToast.makeText(inflateView.getContext(), "User not found. Please sign up.", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        realm.close();
                        return;
                    }

                    //password check
                    boolean isPasswordCorrect = PasswordUtils.verifyPassword(passwordText.toCharArray(),user.getSalt(),user.getPassword());

                    if(isPasswordCorrect){

                        SharedPreferences sharedPreferences = inflateView.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username",usernameText);
                        editor.apply();

                        FancyToast.makeText(inflateView.getContext(),"Sign In Successful",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                        realm.close();
                        Intent intent = new Intent(inflateView.getContext(), MainActivity.class);
                        startActivity(intent);
                        requireActivity().finish();

                    }else{
                        FancyToast.makeText(inflateView.getContext(),"Invalid Username or Password",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        realm.close();
                        return;
                    }


                }
            }
        });


        return inflateView;
    }
}