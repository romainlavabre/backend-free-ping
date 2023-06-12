package com.free.ping.parameter;

public interface PingParameter {
    String PREFIX                 = "ping_";
    String TITLE                  = PREFIX + "title";
    String PING_URL               = PREFIX + "ping_url";
    String SLOW_DOWN_SECONDS      = PREFIX + "slow_down_seconds";
    String VERSION_FIELD          = PREFIX + "field_version";
    String ALERT_TECHNICAL_EMAILS = PREFIX + "alert_technical_emails";
    String ALERT_TECHNICAL_PHONES = PREFIX + "alert_technical_phones";
    String ALERT_USER_EMAILS      = PREFIX + "alert_user_emails";
    String INTERVAL               = PREFIX + "interval";
}
