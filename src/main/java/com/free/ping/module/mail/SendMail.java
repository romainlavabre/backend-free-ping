package com.free.ping.module.mail;

import com.free.ping.entity.Incident;

public interface SendMail {

    void send( Incident incident );
}
