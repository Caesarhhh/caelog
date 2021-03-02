package com.ownblog.caelog.Dao;
import com.ownblog.caelog.pojo.Action;

import java.util.HashMap;
import java.util.List;

public interface ActionDao {
    List<Action> getActionListbyactor(HashMap hashMap);
    List<Action> ifin(HashMap hashMap);
    void insertAction(Action action);
    void deleteAction(Action action);
}
