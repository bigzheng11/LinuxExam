

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import com.google.gson.reflect.TypeToken;
import redis.clients.jedis.Jedis;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/getName")
public class GetName extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://121.43.43.193/LinuxExam?useUnicode=true"
            + "&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
    static final String USER = "root";
    static final String PASS = "Wz1446982638";
    static final String SQL_BOOK_GETNAME = "SELECT * FROM book where id = ?";

    static Connection conn = null;
    static Jedis jedis = null;

    public void init() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            jedis = new Jedis("127.0.0.1"); 

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destory() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf8");
        response.setCharacterEncoding("utf8");
        PrintWriter out = response.getWriter();
        getServletContext().log(request.getParameter("id"));
        String json = jedis.get(request.getParameter("id"));
        if (json == null) {
            Book book = getName(Integer.parseInt(request.getParameter("id")));
            Gson gson = new Gson();
            json = gson.toJson(book, new TypeToken<Book>() {
            }.getType());
            jedis.set(request.getParameter("id"), json);

            out.println(json);
        } else {
            out.println(json);
        }

    }


    private Book getName(int id) {
        Book book =new Book();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(SQL_BOOK_GETNAME);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                book.id = rs.getInt("id");
                book.name = rs.getString("name");
                book.author = rs.getString("author");
            }
            
            
            rs.close();
            stmt.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return book;
    }
}
class Book {
    int id;
    String name;
    String author;
    public String toString() {
        return "BOOK:id="+id+",name="+name+",author="+author+".";

    }
    public void setId(int id) {

        this.id = id ;
    }
    public void setName(String name) {

        this.name = name ;
    }
    public void setAuthor(String author) {

        this.author = author ;
    }
    public String getName() {
        return name;

    }
    public String getAuthor() {
        return author;
    }
    public Integer getId() {
        return id;

    }
}
