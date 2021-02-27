package com.ownblog.caelog.Dao;
import com.ownblog.caelog.pojo.Action;

import java.util.List;

public interface ActionDao {
    List<Action> getActionList();
    void insertAction(Action action);
    void deleteAction();
}
