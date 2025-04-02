package org.example;

import org.example.util.AppUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDateTime;

import static org.example.config.DataBaseConfig.*;
import static org.example.config.DataBaseConfig.PASSWORD;

public class App {

    public void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        while (true) {
            System.out.println("cmd help - article write, article list, article modify, article delete");
            System.out.print("명령어 > ");
            String cmd = br.readLine().trim();
            if (cmd.startsWith("exit")) break;//return;

            if (cmd.equals("article write")) {
                System.out.println("==글쓰기==");
                System.out.print("제목 : ");
                String title = br.readLine().trim();
                System.out.print("내용 : ");
                String body = br.readLine().trim();

                try {
                    Class.forName(DRIVER_CLASS_NAME);
                    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

                    String sql = "insert into article";
                    sql += " set regDate = now(),";
                    sql += " updateDate = now(),";
                    sql += " title = '" + title + "',";
                    sql += " `body` = '" + body + "';";
                    System.out.println(sql);

                    pstmt = conn.prepareStatement(sql);

                    int affectedRows = pstmt.executeUpdate();

                    System.out.println("글쓰기 성공 affected rows: " + affectedRows);

                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    connFinally(rs, pstmt, conn);
                }

            } else if (cmd.equals("article list")) {
                try {
                    Class.forName(DRIVER_CLASS_NAME);
                    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

                    String sql = "select * from article order by id desc";
                    System.out.println(sql + "\n");

                    pstmt = conn.prepareStatement(sql);

                    rs = pstmt.executeQuery();

                    if (rs.next()) {
                        System.out.println("==목록==");
                        System.out.println("   번호    /    제목    ");
                        StringBuilder sb = new StringBuilder();

                        do {
                            int id = rs.getInt("id");
                            LocalDateTime regDate = rs.getTimestamp("regDate").toLocalDateTime();
                            LocalDateTime updateDate = rs.getTimestamp("updateDate").toLocalDateTime();
                            String title = rs.getString("title");
                            String body = rs.getString("body");

                            sb.append("번호: ").append(id).append("\n");
                            sb.append("작성일: ").append(AppUtil.dateTimeParser(regDate)).append("\n");
                            sb.append("수정일: ").append(AppUtil.dateTimeParser(updateDate)).append("\n");
                            sb.append("제목: ").append(title).append("\n");
                            sb.append("내용: ").append(body).append("\n\n");
                        } while (rs.next());
                        System.out.println(sb);
                    } else {
                        System.out.println("게시글 없음\n");
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    connFinally(rs, pstmt, conn);
                }

            } else if (cmd.equals("article modify")) {
                try {
                    Class.forName(DRIVER_CLASS_NAME);
                    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    System.out.println("==글수정==");
                    System.out.print("번호 : ");
                    int id = Integer.parseInt(br.readLine().trim());

                    String sql = "select * from article where id = ?";
                    System.out.println(sql + "\n");

                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, id);

                    rs = pstmt.executeQuery();

                    if (rs.next()) {
                        System.out.print("제목 : ");
                        String title = br.readLine().trim();
                        System.out.print("내용 : ");
                        String body = br.readLine().trim();

                        sql = "UPDATE article SET updateDate = NOW()";
                        sql += ", title = ?";
                        sql += ", body = ?";
                        sql += " WHERE id = ?";
                        System.out.println(sql + "\n");

                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, title.isEmpty() ? rs.getString("title") : title);
                        pstmt.setString(2, body.isEmpty() ? rs.getString("body") : body);
                        pstmt.setInt(3, id);

                        int affectedRows = pstmt.executeUpdate();
                        System.out.println("수정 성공 affectedRows = " + affectedRows);
                    } else {
                        System.out.println("게시글 없음\n");
                    }

                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    connFinally(rs, pstmt, conn);
                }
            } else if (cmd.equals("article delete")) {
                try {
                    Class.forName(DRIVER_CLASS_NAME);
                    conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    System.out.println("==글삭제==");
                    System.out.print("번호 : ");
                    int id = Integer.parseInt(br.readLine().trim());

                    String sql = "select * from article where id = ?";
                    System.out.println(sql + "\n");

                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, id);

                    rs = pstmt.executeQuery();

                    if (rs.next()) {
                        sql = "delete from article WHERE id = ?";
                        System.out.println(sql + "\n");

                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, id);

                        int affectedRows = pstmt.executeUpdate();
                        System.out.println("삭제 성공 affectedRows = " + affectedRows);
                    } else {
                        System.out.println("존재 하지 않는 게시글\n");
                    }

                } catch (ClassNotFoundException e) {
                    System.out.println("드라이버 로딩 실패" + e);
                } catch (SQLException e) {
                    System.out.println("에러 : " + e);
                } finally {
                    connFinally(rs, pstmt, conn);
                }
            }
        }

        System.out.println("==프로그램 종료==");
        br.close();
    }

    private void connFinally(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null && !rs.isClosed()) rs.close();
            if (pstmt != null && !pstmt.isClosed()) pstmt.close();
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
