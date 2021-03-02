package com.ownblog.caelog.Dao;

import com.ownblog.caelog.pojo.Change;

import java.util.HashMap;
import java.util.List;

public interface ChangeDao {
    List<Change> getChangelist(HashMap hashMap);
    void insertChange(Change change);
    void deleteChange(HashMap hashMap);
}
