package com.pgz.net.websocket.rest;

import com.pgz.net.websocket.server.WarningWebSocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * //TODO 添加类/接口功能描述
 *
 * @author liquan_pgz@qq.com
 * @date 2020-09-18
 */
@Controller
@RequestMapping("/websocket/test/")
public class WarningWebsocketController {

    @GetMapping("link.do")
    @ResponseBody
    public String link(@RequestParam(name = "sid") int sid) {
        return "link : " + sid;
    }

    /**
     * 调用WarningWebsocketServer的消息推送方法，从而进行消息推送
     *
     * @param sid     连接WarningWebsocketServer的前端的唯一标识。如果sid为空，
     *                即表示向所有连接WarningWebsocketServer的前端发送相关消息
     * @param message 需要发送的内容主体
     * @return
     */
    @ResponseBody
    @RequestMapping("push.do")
    public String pushToWeb(@RequestParam(name = "sid", defaultValue = "") String sid, @RequestParam(name = "message") String message) {
        WarningWebSocketServer.sendInfo(sid, message);
        return message + "@" + sid + " has send to target.";
    }
}
