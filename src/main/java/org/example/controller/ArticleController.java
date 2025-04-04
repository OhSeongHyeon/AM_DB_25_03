package org.example.controller;

import org.example.container.Container;
import org.example.dto.Article;
import org.example.service.ArticleService;

import java.util.List;
import java.util.Scanner;

public class ArticleController {

    private ArticleService articleService;
    private Scanner sc;

    public ArticleController() {
        this.articleService = Container.articleService;
        this.sc = Container.sc;
    }

    public void doWrite() {
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 후 이용하세요");
            return;
        }
        System.out.println("==글쓰기==");
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        int memberId = Container.session.loginedMemberId;

        int id = articleService.doWrite(memberId, title, body);

        System.out.println(id + "번 글이 생성됨");
    }

    public void showList(String cmd) {
        System.out.println("==목록==");

//        List<Article> articles = articleService.getArticles(); // 전체 리스트 가져와

        String[] cmdBits = cmd.split(" ");

        int page = 1;
        String searchKeyword = ""; // 길이 문제로 인하여 빈 문자열

        // 몇 페이지?
        if (cmdBits.length >= 3) {
            page = Integer.parseInt(cmdBits[2]);
        }
        // 검색어
        if (cmdBits.length >= 4) {
            searchKeyword = cmdBits[3];
        }
        // 한 페이지에 10개씩
        int itemsInAPage = 10;

        List<Article> articles = articleService.getForPrintArticles(page, itemsInAPage, searchKeyword);


        if (articles.size() == 0) {
            System.out.println("게시글이 없습니다");
            return;
        }

        System.out.println("  번호  /   작성자    /   제목  ");
        for (Article article : articles) {
            System.out.printf("  %d     /     %s      /   %s   \n", article.getId(), article.getName(), article.getTitle());
        }
    }

    public void doModify(String cmd) {
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 후 이용하세요");
            return;
        }

        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력해");
            return;
        }

        Article article = articleService.getArticleById(id);

        if (article == null) {
            System.out.println(id + "번 글은 없어");
            return;
        }

        if (article.getMemberId() != Container.session.loginedMemberId) {
            System.out.println("권한 없음");
            return;
        }

        System.out.println("==수정==");
        System.out.print("새 제목 : ");
        String title = sc.nextLine().trim();
        System.out.print("새 내용 : ");
        String body = sc.nextLine().trim();

        articleService.doUpdate(id, title, body);

        System.out.println(id + "번 글이 수정되었습니다.");

    }

    public void showDetail(String cmd) {

        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력해");
            return;
        }

        System.out.println("==상세보기==");

        Article article = articleService.getArticleById(id);

        if (article == null) {
            System.out.println(id + "번 글은 없어");
            return;
        }

        System.out.println("번호 : " + article.getId());
        System.out.println("작성날짜 : " + article.getRegDate());
        System.out.println("수정날짜 : " + article.getUpdateDate());
        System.out.println("작성자 : " + article.getName());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getBody());
    }

    public void doDelete(String cmd) {
        if (Container.session.isLogined() == false) {
            System.out.println("로그인 후 이용하세요");
            return;
        }

        int id = 0;

        try {
            id = Integer.parseInt(cmd.split(" ")[2]);
        } catch (Exception e) {
            System.out.println("번호는 정수로 입력해");
            return;
        }

        Article article = articleService.getArticleById(id);

        if (article == null) {
            System.out.println(id + "번 글은 없어");
            return;
        }

        if (article.getMemberId() != Container.session.loginedMemberId) {
            System.out.println("권한 없음");
            return;
        }

        System.out.println("==삭제==");

        articleService.doDelete(id);

        System.out.println(id + "번 글이 삭제되었습니다.");
    }
}
