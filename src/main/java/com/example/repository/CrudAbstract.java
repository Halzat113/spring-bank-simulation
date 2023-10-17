package com.example.repository;

import java.util.ArrayList;
import java.util.List;

public abstract class CrudAbstract<T> {

    List<T> accountList = new ArrayList<>();
    public T save(T t){
        accountList.add(t);
        return t;
    }

    public List<T> findAll(){
        return accountList;
    }




}
