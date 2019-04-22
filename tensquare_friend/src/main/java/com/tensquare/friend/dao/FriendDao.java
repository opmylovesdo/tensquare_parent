package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/22
 * @Description: 交友数据访问层
 * @version: 1.0
 */
public interface FriendDao extends JpaRepository<Friend, String>{

    /**
     * 根据用户ID与被关注用户ID查询记录个数
     * @param userid
     * @param friendid
     * @return
     */
    @Query(value = "select count(1) from tb_friend f where f.userid=?1 and f.friendid=?2", nativeQuery = true)
    int selectCount(String userId, String friendId);


    /**
     * 更新为互相喜欢
     * @param userId
     * @param friendId
     */
    @Modifying
    @Query(value = "update tb_friend f set f.islike=?3 where f.userid=?1 and f.friendid=?2", nativeQuery = true)
    void updateLike(String userId, String friendId, String isLike);

    /**
     * 删除喜欢(好友)
     * @param userId
     * @param friendId
     */
    @Modifying
    @Query(value = "DELETE from tb_friend WHERE userid=? and friendid=?", nativeQuery = true)
    void deleteFriend(String userId, String friendId);

}
