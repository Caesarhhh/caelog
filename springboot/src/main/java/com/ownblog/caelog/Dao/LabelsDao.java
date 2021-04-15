package com.ownblog.caelog.Dao;

import com.ownblog.caelog.pojo.Labels;
import org.apache.shiro.crypto.hash.Hash;

import java.util.HashMap;
import java.util.List;

public interface LabelsDao {
    List<Labels> getLabelslist(HashMap hashMap);
    List<Labels> getLabelslistall(HashMap hashMap);
    List<Labels> getLabelslistar(HashMap hashMap);
    void clearLabels(HashMap hashMap);
    void insertLabels(Labels labels);
    void deleteLabels(HashMap hashMap);
}
