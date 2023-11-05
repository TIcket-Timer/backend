package com.tickettimer.backendserver.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberAlarmResponse {
    private boolean interAlarm;
    private boolean melonAlarm;
    private boolean yesAlarm;

    @Builder
    public MemberAlarmResponse(
            boolean interAlarm,
            boolean melonAlarm,
            boolean yesAlarm
    ) {
        this.interAlarm = interAlarm;
        this.melonAlarm = melonAlarm;
        this.yesAlarm = yesAlarm;
    }
}
