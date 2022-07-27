package 客户端.model;

import 客户端.view.CilentChatJframe;
import 客户端.view.WebChatFriendlist;

import java.net.Socket;
import java.util.HashMap;

/**
 * @author 郑志伟
 * @create 2020-05-27 8:28
 * 管理好友列表的类
 */
public class ManagerFirendList {
    static HashMap<Socket, WebChatFriendlist> hashMap = new HashMap<>();

    //把创建好的界面入hashmap

    public static void addWebChatFriendlist(Socket id, WebChatFriendlist cc) {
        hashMap.put(id, cc);
    }

    //获取map存放的界面
    public static WebChatFriendlist getWebChatFriendlist(Socket id) {
        return hashMap.get(id);
    }
}
