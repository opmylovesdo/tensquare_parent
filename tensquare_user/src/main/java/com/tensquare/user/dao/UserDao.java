package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
    User findUserByMobile(String mobile);

    /**
     * 更新粉丝数
     * @param userId 用户ID
     * @param num 粉丝数
     */
    @Modifying
    @Query(value="UPDATE tb_user u SET u.fanscount=u.fanscount+?2 WHERE u.id=?1", nativeQuery = true)
    void updateFansCount(String userId, int num);

    /**
     * 更新关注数
     * @param userId 用户ID
     * @param num 关注数
     */
    @Modifying
    @Query(value="UPDATE tb_user u SET u.followcount=u.followcount+?2 WHERE u.id=?1", nativeQuery = true)
    void updateFollowerCount(String userId, int num);

}
