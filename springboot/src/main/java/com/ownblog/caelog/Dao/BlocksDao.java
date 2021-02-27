package com.ownblog.caelog.Dao;

import com.ownblog.caelog.pojo.Blocks;

import java.util.HashMap;
import java.util.List;

public interface BlocksDao {
    List<Blocks> getBlockslist(HashMap hashMap);
    void insertBlocks(Blocks blocks);
    void deleteBlocks(HashMap hashMap);
    void updateBlocks(HashMap hashMap);
}
