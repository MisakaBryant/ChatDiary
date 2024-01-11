package com.mobileSE.chatdiary.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mobileSE.chatdiary.common.exception.BizError;
import com.mobileSE.chatdiary.common.response.CommonResponse;
import com.mobileSE.chatdiary.mapper.MessageMapper;
import com.mobileSE.chatdiary.pojo.entity.MessageEntity;
import com.mobileSE.chatdiary.pojo.vo.chat.ChatRequest;
import com.mobileSE.chatdiary.pojo.vo.chat.MessageVO;
import com.mobileSE.chatdiary.svc.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "", allowCredentials = "true")
@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @PostMapping("chat")
    public CommonResponse<?> putQuestion(@RequestBody ChatRequest chatRequest) {
        StpUtil.checkLogin();
        log.info(chatRequest.toString());
        Long id = (Long.valueOf("" + StpUtil.getLoginId()));
        try {
            chatService.addChats(id, chatRequest);
        } catch (Exception e) {
            log.error(e.getMessage());
            return CommonResponse.error(BizError.GPT_API_ERROR);
        }
        return CommonResponse.success();
    }

    @GetMapping("chat")
    public CommonResponse<?> getMessages() {
        StpUtil.checkLogin();
        Long id = (Long.valueOf("" + StpUtil.getLoginId()));
        List<MessageEntity> allChats = chatService.getAllChats(id);
        List<MessageVO> collect = allChats.stream().map(MessageMapper.INSTANCE::toMassageVO).collect(Collectors.toList());
        return CommonResponse.success(collect);
    }
}
