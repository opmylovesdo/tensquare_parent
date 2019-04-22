package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/22
 * @Description: 交友数据访问层
 * @version: 1.0
 */
public interface NoFriendDao extends JpaRepository<NoFriend, String>{

    /**
     * 根据用户ID与被关注用户ID查询记录个数
     * @param userid
     * @param friendid
     * @return
     */
    @Query(value = "select count(1) from tb_nofriend f where f.userid=?1 and f.friendid=?2", nativeQuery = true)
    int selectCount(String userId, String friendId);



}
