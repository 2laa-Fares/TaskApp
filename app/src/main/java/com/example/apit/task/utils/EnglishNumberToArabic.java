package com.example.apit.task.utils;

public class EnglishNumberToArabic {
    public static String ArabicNumber(String number){
        char[] arabicChars = {'٠','١','٢','٣','٤','٥','٦','٧','٨','٩'};
        StringBuilder builder = new StringBuilder();
        for(int i =0;i<number.length();i++)
        {
            if(Character.isDigit(number.charAt(i)))
            {
                builder.append(arabicChars[(int)(number.charAt(i))-48]);
            }
            else
            {
                builder.append(number.charAt(i));
            }
        }
        return builder.toString();
    }
}
