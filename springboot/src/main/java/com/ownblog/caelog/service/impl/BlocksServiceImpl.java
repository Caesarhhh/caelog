package com.ownblog.caelog.service.impl;
import com.ownblog.caelog.Dao.BlocksDao;
import com.ownblog.caelog.Dao.SocialhrefDao;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.Blocks;
import com.ownblog.caelog.pojo.Socialhref;
import com.ownblog.caelog.service.BlocksService;
import com.ownblog.caelog.utils.BatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

public class BlocksServiceImpl implements BlocksService {
    @Override
    public Result addblocks(int userid, String imgsrc,String name_, String remark) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        BlocksDao blocksDao=sqlSession.getMapper(BlocksDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Blocks> socialhrefs=blocksDao.getBlockslist(hashMap);
        int id_=-1;
        for(int i=0;i<socialhrefs.size();i++){
            if(socialhrefs.get(i).getId()>id_){
                id_=socialhrefs.get(i).getId();
            }
        }
        Blocks blocks=new Blocks(id_+1,userid,imgsrc,name_,remark);
        blocksDao.insertBlocks(blocks);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(blocks);
    }

    @Override
    public Result deleteblocks(int userid, int id) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        BlocksDao blocksDao=sqlSession.getMapper(BlocksDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        hashMap.put("id",id);
        blocksDao.deleteBlocks(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ("delete successfully!");
    }

    @Override
    public Result getblocks(int userid) {
        SqlSession sqlSession= BatisUtils.getSqlSession();
        BlocksDao blocksDao=sqlSession.getMapper(BlocksDao.class);
        HashMap hashMap=new HashMap();
        hashMap.put("userid",userid);
        List<Blocks> blocks=blocksDao.getBlockslist(hashMap);
        sqlSession.commit();
        sqlSession.close();
        return Result.succ(blocks);
    }
}
