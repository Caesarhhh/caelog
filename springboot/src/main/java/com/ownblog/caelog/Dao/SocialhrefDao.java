package com.ownblog.caelog.Dao;

import com.ownblog.caelog.pojo.Socialhref;

import java.util.HashMap;
import java.util.List;

public interface SocialhrefDao {
    List<Socialhref> getSocialhreflist(HashMap hashMap);
    void insertSocialhref(Socialhref socialhref);
    void deleteSocialhref(HashMap hashMap);
    void updateSocialhref(HashMap hashMap);
}
