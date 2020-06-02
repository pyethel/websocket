// function getUserInfo() {
//     var username, userPic, userId;
//     $.ajax({
//         type : 'POST',
//         url : '/getUserInfo',
//         dataType: 'json',
//         async : false,
//         success: function(user){
//             username = user.userName;
//             userPic = user.userPic;
//             userId = user.userId;
//         }
//     });
//     return [userId, username, userPic];
// }
//
// function getFriendInfo(friendId) {
//     var friendName, friendPic;
//     $.ajax({
//         type : 'POST',
//         url : '/getFriendInfo?friendId='+friendId,
//         dataType: 'json',
//         async : false,
//         success: function(friend){
//             friendName = friend.userName;
//             friendPic = friend.picture;
//         }
//     });
//     return [friendName, friendPic];
// }
app={
    CONNECT: 1,
    CHAT: 2,

    ONLINE: 1,
    OFFLINE: 0,
    SINGLE: 1,
    GROUP: 2,
    ChatMsg: function (msgId, senderId, senderName, senderPic, receiverId, localDateTime, msg, act, type) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderPic = senderPic;
        this.receiverId = receiverId;
        this.msg = msg;
        this.act = act;
        this.localDateTime = localDateTime;
        this.msgId = msgId;
        this.type = type;
    },
    setUserGlobalInfo: function (user) {
        var userInfoStr = JSON.stringify(user);
        sessionStorage.setItem("userInfo", userInfoStr);
    },
    getUserGlobalInfo: function () {
        var userInfoStr = sessionStorage.getItem("userInfo");
        return JSON.parse(userInfoStr);
    },
    setFriendInfo: function(friend){
        var friendStr = JSON.stringify(friend);
        sessionStorage.setItem("friendInfo", friendStr);
    },
    getFriendInfo: function () {
        var friendStr = sessionStorage.getItem("friendInfo");
        return JSON.parse(friendStr);
    },
    setGroupInfo: function(group){
        var groupStr = JSON.stringify(group);
        sessionStorage.setItem("groupInfo", groupStr);
    },
    getGroupInfo: function () {
        var groupStr = sessionStorage.getItem("groupInfo");
        return JSON.parse(groupStr);
    }

};

