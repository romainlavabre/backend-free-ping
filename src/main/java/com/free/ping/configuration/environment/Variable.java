package com.free.ping.configuration.environment;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface Variable {

    String PAGINATION_COUNT_CACHE_SECONDS = "pagination.count.cache.seconds";
    String JWT_SECRET                     = "jwt.secret";
    String JWT_LIFE_TIME                  = "jwt.life-time";
    String SMS_TWILIO_SID                 = "sms.twilio.sid";
    String SMS_PRIVATE_KEY                = "sms.twilio.auth-token";
    String SMS_FROM                       = "sms.from";
    String MAILGUN_DOMAIN                 = "";
    String MAILGUN_PRIVATE_KEY            = "";
    String MAILGUN_FROM                   = "";
    String SMTP_HOST                      = "smtp.host";
    String SMTP_PORT                      = "smtp.port";
    String SMTP_USERNAME                  = "smtp.username";
    String SMTP_PASSWORD                  = "smtp.password";
    String DEFAULT_ADMIN_USERNAME         = "default.admin.username";
    String DEFAULT_ADMIN_PASSWORD         = "default.admin.password";
    String BASE_TEMPLATE_PATH             = "base.template.path";
}
