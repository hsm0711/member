package com.member.listener;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.member.api.sample.entity.ServerSentEventsEmitterEntity;
import com.member.api.sample.model.SseDto;
import com.member.api.sample.repository.SeverSentEventsInMemoryRepository;
import com.member.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSeverSentEventsMessageSubscribeListener implements MessageListener {
  private final RedisTemplate<String, Object> redisTemplate;
  private final SeverSentEventsInMemoryRepository sseRepository;

  /**
   * Redis에서 메시지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메시지를 받아 처리한다.
   */
  @Override
  public void onMessage(Message message, byte[] pattern) {
    SseDto messageDto = (SseDto) redisTemplate.getHashValueSerializer().deserialize(message.getBody());
    log.debug(">>> onMessage.messageDto : {}", messageDto);
    if (messageDto != null) {
      String userId = messageDto.getId();

      if (StringUtils.isNotBlank(userId)) {
        try {
          ServerSentEventsEmitterEntity sseEmitterEntity = sseRepository.getEmitterEntityByStartWithIdLast(userId);

          if (sseEmitterEntity != null) {
            sseEmitterEntity.getSseEmitter()
                .send(SseEmitter.event().id(messageDto.getId() + "_" + messageDto.getSendDate()).name("sse").data(messageDto.getData()));
          }
        } catch (IOException e) {
          sseRepository.remove(messageDto.getId());

          throw new CustomException("연결 오류!");
        }
      }
    }
  }
}
