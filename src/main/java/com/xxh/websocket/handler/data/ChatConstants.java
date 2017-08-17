package com.xxh.websocket.handler.data;

/**
 * Created by wulongtao on 2017/7/3.
 *
 */
public class ChatConstants {
    //socket session绑定用户ID的键
    public static final String CLIENT_ID = "clientId";

    /**
     * type类型
     */
    public static final int TYPE_CUSTOMER_SERVICE_LOGIN = 1; //客服登录
    public static final int TYPE_CUSTOMER_LOGIN = 2; //客户登录
    public static final int TYPE_CUSTOMER_SERVICE_LOGOUT = 3; //客服登出
    public static final int TYPE_CUS_LOGOUT = 4; //客户登出
    public static final int TYPE_SERVER_NOTICE = 5; //服务器返回消息
    public static final int TYPE_CLIENT_NOTICE = 6; //客户端返回消息
    public static final int TYPE_SAY = 7; // 说话
    public static final int TYPE_SERVICE_WAITING_USER = 8; //登录成功后请求服务等待队列中的客户
    public static final int TYPE_NEED_SERVICE_USER = 9; //发送给客服端，表示有用户需要服务
    public static final int TYPE_WANT_SERVICE_USER = 10; //客服端回应服务端是否需要服务此用户
    public static final int TYPE_ATTRIBUTE_USER = 11; //推送用户
    public static final int TYPE_ATTRIBUTE_USER_CHAT = 12; //用户可以开始聊天标记
    public static final int TYPE_SUPERVISOR_LOGIN = 13; //监管登录
    public static final int TYPE_SUPERVISOR_ENTER_ROOM = 14; //监管加入房间
    public static final int TYPE_SUPERVISOR_INTERCHANGE_ROOM = 15; //监管转接房间
    public static final int TYPE_SUPERVISOR_LEAVE_ROOM = 16; //监管转接房间
    public static final int TYPE_EVALUATE = 17; //咨询用户退出评价
    public static final int TYPE_SERVICVE_INTERCHANGE = 18; //客服之间的转接
    public static final int TYPE_SOLVE_INTERCHANGE = 19; //是否同意转接
    public static final int TYPE_INTERCHANGE_USER = 20; //
    public static final int TYPE_CHANGE_ONLINE_STATUS = 21; //修改在线状态
    public static final int TYPE_INVITE_ASSISTANT = 22;
    public static final int TYPE_SOLVE_INVITE = 23; //邀请失败
    public static final int TYPE_REPLY_NOTICE = 24; //通知用户没回复多久了
    public static final int TYPE_STAY_TIMEOUT = 25; //通知用户没回复多久了
    public static final int TYPE_CLOSE_CLIENT_SESSION = 26; //客服主动关闭与用户的会话
    public static final int TYPE_PING = 999; //ping

    /**
     * result
     * 0（固定）:表示成功
     * 101（固定）：WebSocket连接成功
     * 负数<-1000：错误消息
     * 正数>1000：失败消息
     */
    public static final int RESULT_OK = 0;
    public static final int RESULT_WEBSOCKET_CONNECT_SUCCESS = 101;
    public static final int RESULT_BIND_USER_FAILED_ERROR = -1000;
    public static final int RESULT_NEET_JSON_DATA_ERROR = -1001;
    public static final int RESULT_NEET_CLIENT_ID_ERROR = -1002;
    public static final int RESULT_BIND_CLIENT_ID_ERROR = -1003;
    public static final int RESULT_CLIENT_NEED_LOGIN_FAILED = 1001;
    public static final int RESULT_CLIENT_ID_MATCH_FAILED = 1002;
    public static final int RESULT_LOGIN_FAILED = 1003;
    public static final int RESULT_HAS_LOGINED_FAILED = 1004;
    public static final int RESULT_LOGOUT_FAILED = 1005;
    public static final int RESULT_USER_ID_NULL = 1006;

}
