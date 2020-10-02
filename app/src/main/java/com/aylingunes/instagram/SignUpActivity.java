package com.aylingunes.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth; //firebase nesnesini bildirelim
    EditText emailText, passwordText; // editTextleri id'lerini kullanarak sadece bildirimini yaptık override altında değer verilecek

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //initalize etme
        firebaseAuth = FirebaseAuth.getInstance(); //firebaseauth sınıfından bir instance oluşturduk
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

        // giriş yapmış bir kullanıcı var mı varsa tekrar şifre sorma ;
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null) {
            // getirilen değer null değilse kullanıcı girişi yapılmıştır;
            Intent intent = new Intent(SignUpActivity.this,FeedActivity.class);
            startActivity(intent); // uygulama açıldığında firebaseAuth nesnesinin içi doluysa yani girilmişse
            //direkt feedactivity ekranına yolla deniyor
                    finish();
        }


    }


    public void signInClicked(View view) { // button1 için click methodu
String email = emailText.getText().toString();
String password = passwordText.getText().toString();
// firebase ile giriş yaparken bir mail bir şifre bekleyecek, öncelikle onları tanımlamamız gerekiyor
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent = new Intent(SignUpActivity.this, FeedActivity.class);
                startActivity(intent);
            finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
// sign hatalrında exception toast edilmeli
                Toast.makeText(SignUpActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });


    }

    public void signUpClicked(View view) { // button2 için click methodu
        // henüz kullanıcımız olmadığı için ilk etapta burası kodlanacak

        String email = emailText.getText().toString(); // email olarak adı geçecek bu blokta ama idsi emailText olan textViewden bahsediliyor.
        String password = passwordText.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
            Toast.makeText(SignUpActivity.this, "User Cretaed", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignUpActivity.this,FeedActivity.class); // create user başarılı olduktan sonra kulanıcıyı bu aktiviteden alıp feed activity'e yönlendirmeyi amaçlar intent kullanarak
                startActivity(intent); //intenti başlatır
            finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) { // usercreate başarızı olursa verilen hata mesajını firebase gönderiyor bunu kullanıcıya çevirerrk toast edebiliriz
                Toast.makeText(SignUpActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });// yaratıcı methodun beklediği parametleri önce yarattık sonra gönderdik
        // create user sonunda ne olacağını belirlememiz gerekiyor burada email kontrolü gibi durumlara bakılır



    }
}