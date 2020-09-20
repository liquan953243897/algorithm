package com.pgz.net.websocket.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Websocket的配置
 * 引入Websocket至spring容器
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-18
 */
@Component
@Slf4j
@ServerEndpoint("/websocket/warning/{sid}")
public class WarningWebSocketServer {

    // JUC包的线程安全Set，用来存放每个客户端对应的WarningWebSocketServer对象。
    // 用ConcurrentHashMap也是可以的。说白了就是类似线程池中的BlockingQueue那样作为一个容器
    private static CopyOnWriteArraySet<WarningWebSocketServer> warningWebSocketSet = new CopyOnWriteArraySet<>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 接收sid
    private String sid = "";

    /**
     * 建立websocket连接
     * 看起来很像JSONP的回调，因为前端那里是Socket.onOpne()
     *
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        this.sid = sid;
        warningWebSocketSet.add(this);

        sendMessage("websocket connection has created.");
    }

    /**
     * 关闭websocket连接
     */
    @OnClose
    public void onClose() {
        warningWebSocketSet.remove(this);
        log.info("there is an wsConnect has close .");
    }

    /**
     * websocket连接出现问题时的处理
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("there is an error has happen ! error:{}", error);
    }

    /**
     * websocket的server端用于接收消息的（目测是用于接收前端通过Socket.onMessage发送的消息）
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("webSocketServer has received a message:{} from {}", message, this.sid);

        // 调用消息处理方法（此时针对的WarningWebSocektServer对象，只是一个实例。这里进行消息的单发）
        // 目前这里还没有处理逻辑。故为了便于前端调试，这里直接返回消息
        this.sendMessage(message);
    }

    /**
     * 服务器主动推送消息的方法
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.warn("there is an IOException:{}!", e.toString());
        }
    }

    public static void sendInfo(String sid, String message) {
        for (WarningWebSocketServer warningWebSocketServerItem : warningWebSocketSet) {
            if (StringUtils.isEmpty(sid)) {
                // 如果sid为空，即群发消息
                warningWebSocketServerItem.sendMessage(message);
                log.info("Mass messaging. the message({}) has sended to sid:{}.", message, warningWebSocketServerItem.sid);
            }
            if (!StringUtils.isEmpty(sid)) {
                if (warningWebSocketServerItem.sid.equals(sid)) {
                    warningWebSocketServerItem.sendMessage(message);
                    log.info("single messaging. message({}) has sended to sid:{}.", message, warningWebSocketServerItem.sid);
                }
            }
        }
    }
}
