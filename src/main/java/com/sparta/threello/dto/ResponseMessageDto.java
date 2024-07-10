package com.sparta.threello.dto;

import com.sparta.threello.enums.ResponseStatus;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ResponseMessageDto {
    private int status;
    private String message;

    public ResponseMessageDto(ResponseStatus status) {
        this.status = status.getHttpStatus().value();
        this.message = status.getMessage();
    }

    //사용 예시
    //return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.COMMENT_DELETE_SUCCESS));
}
