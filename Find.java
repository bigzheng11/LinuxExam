
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import com.google.gson.reflect.TypeToken;
import redis.clients.jedis.Jedis;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/find")
public class Find extends HttpServlet {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://121.43.43.193/LinuxExam?useUnicode=true"
    		+ "&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
    static final String USER = "root";
    static final String PASS = "Wz1446982638";
    static final String SQL_BOOK_FIND = "SELECT * FROM book";
    
    static Connection conn = null;

    
    public void init() {
		try {
			Class.forName(JDBC_DRIVER);
	        conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
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
        List<Book> book = findBook();
        Gson gson = new Gson();
       String json = gson.toJson(book, new TypeToken<List<Book>>() {
            }.getType());
       out.println(json);
       out.flush();
       out.close();
       

}
    
    
    private List<Book> findBook() {
        List<Book> bookList = new ArrayList<Book>();
        
        Statement stmt = null;
        try {
            
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_BOOK_FIND);
            
            while (rs.next()) {
                Book book =new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));
                bookList.add(book);
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
        return bookList;
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
