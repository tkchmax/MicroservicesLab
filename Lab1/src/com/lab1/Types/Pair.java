package com.lab1.Types;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;


public class Pair<U, T>
{
    public U first;
    public T second;

    private Pair(U first, T second){
        this.first = first;
        this.second = second;
    }
    public static <U, T> Pair<U,T> of(U a, T b){
        return new Pair<>(a,b);
    }

}