package com.example.user.retrofitexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.retrofitexample.network.Response.Api;
import com.example.user.retrofitexample.network.Response.SignUpResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    SignUpResponse signUpResponsesData;
    EditText email, password, name;
    Button signUp,show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init the EditText and Button
        name = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signUp = (Button) findViewById(R.id.signUp);
        show = (Button) findViewById(R.id.show);

        // implement setOnClickListener event on sign up Button
        signUp.setOnClickListener(this);
        show.setOnClickListener(this);


            }



    private boolean validate(EditText editText) {
        // check the lenght of the enter data in EditText and give error if its empty
        if (editText.getText().toString().trim().length() > 0) {
            return true; // returs true if field is not empty
        }
        editText.setError("Please Fill This");
        editText.requestFocus();
        return false;
    }

    private void signUp() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        // Api is a class in which we define a method getClient() that returns the API Interface class object
        // registration is a POST request type method in which we are sending our field's data
        Api.getClient().registration(name.getText().toString().trim(),
                email.getText().toString().trim(),
                password.getText().toString().trim(),
                "email", new Callback<SignUpResponse>() {
                    @Override
                    public void success(SignUpResponse signUpResponse, Response response) {
                        // in this method we will get the response from API
                        progressDialog.dismiss(); //dismiss progress dialog
                        signUpResponsesData = signUpResponse;
                        // display the message getting from web api
                        Toast.makeText(MainActivity.this, signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        // if error occurs in network transaction then we can get the error in this method.
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss(); //dismiss progress dialog

                    }
                });
    }

    @Override
    public void onClick(View v) {
        // validate the fields and call sign method to implement the api
        if(v.getId()==R.id.signUp && validate(name) && validate(email) && validate(password)) {
            signUp();
        }
        if(v.getId()==R.id.show){
            Intent in=new Intent(MainActivity.this,Main2Activity.class);
            startActivity(in);
        }
    }
}