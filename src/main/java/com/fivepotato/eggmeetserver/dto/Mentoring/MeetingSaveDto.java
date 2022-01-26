package com.fivepotato.eggmeetserver.dto.mentoring;

import com.fivepotato.eggmeetserver.domain.mentoring.Meeting;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@NoArgsConstructor
@Getter
public class MeetingSaveDto {

    private Long dateTime;

    @Builder
    public MeetingSaveDto(Long dateTime) {
        this.dateTime = dateTime;
    }

    public Meeting toEntity() {
        return Meeting.builder()
                .dateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(dateTime), ZoneId.systemDefault()))
                .build();
    }
}
