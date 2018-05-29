package com.example.anna.annagardynikpierwsze;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BMIcalculator extends AppCompatActivity
{
    @BindView(R.id.mass_) EditText masa;
    @BindView(R.id.height_) EditText wzrost;
    @BindView(R.id.liczButton) Button policz;

    float wynik = 0;
    String status = " ";
    boolean isGood = false;

    public void statusChange (float wynik)
    {
        if(wynik <= 18.5)
            status = "Niedowaga";
        if(wynik > 18.5 && wynik <= 25.0)
            status = "W normie";
        if(wynik > 25.0 && wynik <= 30.0)
            status = "Nadwaga";
        if(wynik > 30.0)
            status = "Otylosc";
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        final TextView result = (TextView) findViewById(R.id.rezultatTextView);
        final Button countbtn = (Button) findViewById(R.id.liczButton);
        final TextView stat = (TextView) findViewById(R.id.statusTextView);

        countbtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Switch switchUnit = (Switch)findViewById(R.id.units);
                    if (switchUnit.isChecked())
                        result.setText(PoliczBMI_units()+ "\n"+status);
                    else
                        result.setText(PoliczBMI()+ "\n"+status);
                    if(wynik <= 18.5)
                    {
                        result.setTextColor(Color.BLUE);
                        stat.setTextColor(Color.BLUE);
                    }
                    if(wynik > 18.5 && wynik <= 25.0)
                    {
                        result.setTextColor(Color.GREEN);
                        stat.setTextColor(Color.GREEN);
                    }
                    if(wynik > 25.0 && wynik <= 30.0)
                    {
                        result.setTextColor(Color.LTGRAY);
                        stat.setTextColor(Color.LTGRAY);
                    }
                    if(wynik > 30.0)
                    {
                        result.setTextColor(Color.RED);
                        stat.setTextColor(Color.RED);
                    }

                }
            });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);


        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.item_about_author:
                Intent intent = new Intent(BMIcalculator.this, About_author.class);
                startActivity(intent);
        }
        switch (item.getItemId())
        {
            case R.id.item_save:
                if(isGood == true)
                {
                    save();
                    Toast.makeText(BMIcalculator.this, "Zapisano",
                            Toast.LENGTH_LONG).show();
                    isGood = false;
                }
                else
                    Toast.makeText(BMIcalculator.this, "Podano nieprawidlowe wartosci",
                            Toast.LENGTH_LONG).show();

        }
        switch (item.getItemId())
        {
            case R.id.item_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, ("Moj wynik BMI: " + wynik));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
        }

        return super.onOptionsItemSelected(item);
    }


    public String PoliczBMI()
    {
        float m = new Float(masa.getText().toString());
        float h = new Float(wzrost.getText().toString());
        if(isGoodValue(m,h))
        {
            float licz_bmi = new LiczBMI().countBmi(m,h);
            wynik = licz_bmi;
            statusChange(wynik);
            return (Float.toString(licz_bmi));
        }
        return ("podaj inna wartosc");

    }
    public String PoliczBMI_units()
    {
        float m1 = new Float(masa.getText().toString());
        float h1 = new Float(wzrost.getText().toString());
        float m = (float) (m1*0.4536);
        float h = (float) ( h1* 0.3048);
        if(isGoodValue(m,h))
        {
            float licz_bmi = new LiczBMI().countBmi(m,h);
            wynik = licz_bmi;
            statusChange(wynik);
            return (Float.toString(licz_bmi));
        }
        return ("podaj inna wartosc");
    }

    public boolean isGoodValue(float m, float h)
    {
        if(m>40.0 && m<200.0)
        {
            if(h>1.40 && h<2.30)
            {
                isGood = true;
                return true;
            }
        }
        status = " ";
        isGood = false;
        return false;
    }



    public void save()
    {

        String filename = "wynikBMI";

        String string =  Float.toString(wynik);
        FileOutputStream outputStream = null;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
