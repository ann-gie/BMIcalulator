package com.example.anna.annagardynikpierwsze;

import java.text.DecimalFormat;

/**
 * Created by Anna on 4/5/2017.
 */

public class LiczBMI
{
    public float countBmi(float m, float h)
    {

        return (float) Math.round(m/(h*h)*100)/100;
    }
}
