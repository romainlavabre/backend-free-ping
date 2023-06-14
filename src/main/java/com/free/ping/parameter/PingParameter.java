package com.free.ping.parameter;

public interface PingParameter {
    String PREFIX                       = "ping_";
    String TITLE                        = PREFIX + "title";
    String PING_URL                     = PREFIX + "ping_url";
    String SLOW_DOWN_SECONDS            = PREFIX + "slow_down_seconds";
    String VERSION_FIELD                = PREFIX + "field_version";
    String ALERT_TECHNICAL_EMAILS       = PREFIX + "alert_technical_emails";
    String ALERT_TECHNICAL_PHONES       = PREFIX + "alert_technical_phones";
    String ALERT_USER_EMAILS            = PREFIX + "alert_user_emails";
    String INTERVAL                     = PREFIX + "interval";
    String DOWN_TIME_DETECTED_TEMPLATE  = PREFIX + "down_time_detected_template";
    String DOWN_TIME_ENDED_TEMPLATE     = PREFIX + "down_time_ended_template";
    String SLOW_DOWN_DETECTED_TEMPLATE  = PREFIX + "slow_down_detected_template";
    String SLOW_DOWN_ENDED_TEMPLATE     = PREFIX + "slow_down_ended_template";
    String DOWN_TIME_TECHNICAL_TEMPLATE = PREFIX + "down_time_technical_template";
    String SLOW_DOWN_TECHNICAL_TEMPLATE = PREFIX + "slow_down_technical_template";
}
