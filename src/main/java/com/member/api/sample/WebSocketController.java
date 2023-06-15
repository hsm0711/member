package com.member.api.sample;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.member.api.sample.model.WebSocketDto;
import com.member.api.sample.service.WebSocketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "websocket 샘플 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/public/sample/websocket")
public class WebSocketController {
  private final WebSocketService websocketService;

  /**
   * websocket publish 개인
   */
  @ApiOperation(value = "websocket 개인 메시지 발송", notes = "websocket의 연결된 사용자중 특정 사용자(receiverId)에게 메시지 전송")
  @PostMapping("/send-message")
  public ResponseEntity<Void> sendMessage(@RequestBody WebSocketDto webSocketDto) {
    websocketService.sendMessage(webSocketDto);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * websocket publish 전체
   */
  @ApiOperation(value = "websocket 전체 메시지 발송", notes = "websocket의 구독중인 채널의 전체 사용자에게 메시지 전송")
  @PostMapping("/send-all-message")
  public ResponseEntity<Void> sendAllMessage(@RequestBody WebSocketDto webSocketDto) {
    websocketService.sendAllMessage(webSocketDto);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
