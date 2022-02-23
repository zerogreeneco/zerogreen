package zerogreen.eco.security.dto;

import zerogreen.eco.entity.userentity.Member;

import java.io.Serializable;

public class SessionUser implements Serializable {
    private String nickname;
    private String username;

    public SessionUser(Member member) {
        this.nickname = member.getNickname();
        this.username = member.getUsername();
    }
}
