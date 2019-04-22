package com.tensquare.friend.service;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: LUOLUO
 * @Date: 2019/4/22
 * @Description: com.tensquare.friend.service
 * @version: 1.0
 */
@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;

    /**
               *  向不喜欢列表中添加记录
               *  @param  userid
               *  @param  friendid
               */
    public int addNoFriend(String userid, String friendid){
        int flag = noFriendDao.selectCount(userid, friendid);
        if(flag > 0){
            return 0;
        }

        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);

        return 1;
    }

    /**
     * @param id
     * @param friendid
     * @return 1 成功 0 失败
     */
    public int addFriend(String id, String friendid) {
        int flag = friendDao.selectCount(id, friendid);
        if (flag > 0) return 0;

        Friend f = new Friend();
        f.setFriendid(friendid);
        f.setUserid(id);
        f.setIslike("0");//是否互相喜欢 0 否 1 是
        friendDao.save(f);

        userClient.updateFansCountAndFollowerCount(id,friendid, 1);

        flag = friendDao.selectCount(friendid, id);
        if (flag > 0) {
            friendDao.updateLike(id, friendid, "1");
            friendDao.updateLike(friendid, id, "1");
        }
        return 1;
    }

    /**
      *  删除好友
      *  @param  userid
      *  @param  friendid
      */
    public void deleteFriend(String userid,String friendid){
        friendDao.deleteFriend(userid,friendid);

        userClient.updateFansCountAndFollowerCount(userid, friendid, -1);

        friendDao.updateLike(friendid,userid,"0");
        addNoFriend(userid,friendid);//向不喜欢表中添加记录
    }
}
