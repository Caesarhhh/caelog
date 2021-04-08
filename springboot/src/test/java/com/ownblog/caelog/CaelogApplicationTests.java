package com.ownblog.caelog;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.ownblog.caelog.Dao.ArticleDao;
import com.ownblog.caelog.Dao.UserDao;
import com.ownblog.caelog.lang.Result;
import com.ownblog.caelog.pojo.Article;
import com.ownblog.caelog.pojo.User;
import com.ownblog.caelog.service.impl.ArticleServiceImpl;
import com.ownblog.caelog.service.impl.UserServiceImpl;
import com.ownblog.caelog.utils.BatisUtils;
import com.ownblog.caelog.utils.sendEmailUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
class CaelogApplicationTests {
    private static int total = 1000000;
    private static BloomFilter<Integer> bf = BloomFilter.create(Funnels.integerFunnel(), total);

    @Test
    void contextLoads() {
        // 初始化1000000条数据到过滤器中
        for (int i = 0; i < total; i++) {
            bf.put(i);
        }

        // 匹配已在过滤器中的值，是否有匹配不上的
        for (int i = 0; i < total; i++) {
            if (!bf.mightContain(i)) {
                System.out.println("有坏人逃脱了~~~");
            }
        }

        // 匹配不在过滤器中的10000个值，有多少匹配出来
        int count = 0;
        for (int i = total; i < total + 10000; i++) {
            if (bf.mightContain(i)) {
                count++;
            }
        }
        System.out.println("误伤的数量：" + count);
    }

        @Autowired
        private RedisTemplate redisTemplate;

        @Autowired
        private ArticleServiceImpl articleService;

        @Test
        public void set(){
            articleService.getarticle(4);
        }

        @Test
        public void testredis(){
            Stack stack=new Stack();
            System.out.println(stack.size());
            Jedis jedis=new Jedis("127.0.0.1",6379);
            System.out.println(jedis.ping());
        }

        @Test
        public void test(){
        }


}
