package org.example.session;

import org.example.container.Container;
import org.example.dto.Member;

public class Session {
    public Member loginedMember;
    public int loginedMemberId;

    public Session() {
        loginedMember = null;
        loginedMemberId = -1;
    }

    public void logout() {
        loginedMember = null;
        loginedMemberId = -1;
    }

    public void login(Member member) {
        Container.session.loginedMember = member;
        Container.session.loginedMemberId = member.getId();
    }

    public boolean isLogined() {
        return loginedMemberId != -1;
    }
}
