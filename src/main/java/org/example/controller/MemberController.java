package org.example.controller;

import org.example.container.Container;
import org.example.dto.Member;
import org.example.service.MemberService;

import java.util.Scanner;

public class MemberController {

    private MemberService memberService;
    Scanner sc;

    public MemberController() {
        this.memberService = Container.memberService;
        sc = Container.sc;
    }

    public void doJoin() {
        if (Container.session.isLogined()) {
            System.out.println("로그아웃 후 이용하세요");
            return;
        }
        String loginId = null;
        String loginPw = null;
        String loginPwConfirm = null;
        String name = null;
        System.out.println("==회원가입==");
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = sc.nextLine().trim();

            if (loginId.length() == 0 || loginId.contains(" ")) {
                System.out.println("아이디 똑바로 써");
                continue;
            }

            boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

            System.out.println(isLoginIdDup);

            if (isLoginIdDup) {
                System.out.println(loginId + "은(는) 이미 사용중");
                continue;
            }
            break;

        }
        while (true) {
            System.out.print("비밀번호 : ");
            loginPw = sc.nextLine().trim();

            if (loginPw.length() == 0 || loginPw.contains(" ")) {
                System.out.println("비번 똑바로 써");
                continue;
            }

            boolean loginCheckPw = true;

            while (true) {
                System.out.print("비번 확인 : ");
                loginPwConfirm = sc.nextLine().trim();

                if (loginPwConfirm.length() == 0 || loginPwConfirm.contains(" ")) {
                    System.out.println("비번 확인 똑바로 써");
                    continue;
                }

                if (loginPw.equals(loginPwConfirm) == false) {
                    System.out.println("일치하지 않아");
                    loginCheckPw = false;
                }
                break;
            }
            if (loginCheckPw) {
                break;
            }
        }
        while (true) {
            System.out.print("이름 : ");
            name = sc.nextLine();

            if (name.length() == 0 || name.contains(" ")) {
                System.out.println("이름 똑바로 써");
                continue;
            }
            break;
        }

        int id = memberService.doJoin(loginId, loginPw, name);

        System.out.println(id + "번 회원이 가입됨");
    }

    public void login() {
        if (Container.session.isLogined()) {
            System.out.println("로그아웃 후 이용하세요");
            return;
        }

        String loginId = null;
        String loginPw = null;

        System.out.println("==로그인==");
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = sc.nextLine().trim();

            if (loginId.length() == 0 || loginId.contains(" ")) {
                System.out.println("아이디 똑바로 써");
                continue;
            }

            boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

            if (isLoginIdDup == false) {
                System.out.println(loginId + "은(는) 없어");
                continue;
            }
            break;

        }

        Member member = memberService.getMemberByLoginId(loginId);

        int tryMaxCount = 3;
        int tryCount = 0;

        while (true) {
            if (tryCount >= tryMaxCount) {
                System.out.println("비번 다시 확인하고 시도해");
                break;
            }
            System.out.print("비밀번호 : ");
            loginPw = sc.nextLine().trim();

            if (loginPw.length() == 0 || loginPw.contains(" ")) {
                tryCount++;
                System.out.printf("비번 똑바로 써(%d/3)\n", tryCount);
                continue;
            }

            if (member.getLoginPw().equals(loginPw) == false) {
                tryCount++;
                System.out.printf("일치하지 않아(%d/3)\n", tryCount);
                continue;
            }


            Container.session.login(member);

            System.out.println(member.getName() + "님 환영합니다");
            break;
        }
    }

    public void showProfile() {
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 후 이용하세요");
            return;
        } else {
            System.out.println(Container.session.loginedMember);
        }

    }

    public void logout() {
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 후 이용하세요");
            return;
        }
        System.out.println("==로그아웃==");
        Container.session.logout();

    }
}
