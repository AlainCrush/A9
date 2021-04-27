package com.example.a9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int scoreA =0,scoreB = 0;
    TextView outA,outB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outA = findViewById(R.id.textView3);
        outB = findViewById(R.id.textView4);
    }
    private void showA(){
        outA.setText(Integer.toString(scoreA));
    }
    private void showB(){
        outB.setText(Integer.toString(scoreB));
    }
    public void Onclick1A(View v){
        scoreA+=1;
        showA();
    }
    public void Onclick1B(View v){
        scoreB+=1;
        showB();
    }
    public void Onclick2A(View v){
        scoreA+=2;
        showA();
    }
    public void Onclick2B(View v){
        scoreB+=2;
        showB();
    }
    public void Onclick3A(View v){
        scoreA+=3;
        showA();
    }
    public void Onclick3B(View v){
        scoreB+=3;
        showB();
    }
    public void Onclick4A(View v){
        scoreA = 0;
        showA();
    }
    public void Onclick4B(View v){
        scoreB = 0;
        showB();
    }
    protected  void onSaveInstanceSate(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("scoreA",scoreA);
        outState.putInt("ScoreB",scoreB);
    }
    protected void  onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);
        scoreA = saveInstanceState.getInt("scoreA");
        scoreB = saveInstanceState.getInt("scoreB");
    }
}