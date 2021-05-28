package com.ouranservice.controller;

import com.ouranservice.entity.Book;
import com.ouranservice.entity.BookComment;
import com.ouranservice.entity.BookList;
import com.ouranservice.entity.BookTypes;
import com.ouranservice.service.BookCommentService;
import com.ouranservice.service.BookListService;
import com.ouranservice.service.BookService;
import com.ouranservice.service.BookTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/manage")
public class ManagerController {

    private final BookService bookService;
    private final BookTypesService bookTypesService;
    private final BookListService bookListService;
    private final BookCommentService bookCommentService;

    @Autowired
    public ManagerController(BookService bookService,
                             BookTypesService bookTypesService,
                             BookListService bookListService,
                             BookCommentService bookCommentService) {
        this.bookService = bookService;
        this.bookTypesService = bookTypesService;
        this.bookListService = bookListService;
        this.bookCommentService = bookCommentService;
    }




    /**
     *
     * 页面跳转方法
     *
     */
    //跳转至登录页面
    @GetMapping("/welcome")
    public String toWelcome(){
        return "/welcome";
    }

    //跳转至登录页面
    @GetMapping("/")
    public String toWelcome2(){
        return "/welcome";
    }


    /**
     * 跳转至书籍类型管理页面
     */
    //跳转至书籍展示页面
    @GetMapping("/bookData")
    public String toBookData(@RequestParam("pageNumber") int pageNumber,
                             HttpServletRequest request){
        int number = bookService.getNumber();   //获取当前书籍数量
        float num = number;                     //将number转化为float
        number = (int) Math.ceil(num/10);       //书籍整除以10再向上取整 求出一共有多少页
        //对pageNumber进行判断
        if (pageNumber == 0){                   //如果是第一次进入该页面，页码从1开始
            pageNumber = 1;
        }else if (pageNumber > number){         //如果pageNumber超过书籍最大页数，pageNumber调整为最大值不再变化
            pageNumber = number;
        }
        List<Book> books = bookService.getAll(pageNumber);        //获取当前页数书籍
        request.setAttribute("books",books);                //添加到request中
        request.setAttribute("pageNumber",pageNumber);      //将页码传送到页面
        return "book/bookData";
    }

    //跳转至书籍信息管理
    @GetMapping("/bookManage")
    public String toBookManage(@RequestParam("bookId") int bookId,
                               HttpServletRequest request){
        Book book = bookService.getDetailById(bookId);
        request.setAttribute("book",book);
        return "book/bookManage";
    }

    //跳转至书籍添加页面
    @GetMapping("/bookAdd")
    public String toBookAdd(){
        return "book/bookAdd";
    }

    //跳转至书籍类型管理页面
    @GetMapping("/bookType")
    public String toBookType(HttpServletRequest request) {
        List<BookTypes> bookTypesList = bookTypesService.getAll();
        request.setAttribute("bookTypesList",bookTypesList);
        return "/book/bookType";
    }


    /**
     * 跳转至书单管理类型页面
     */
    //跳转至书单展示页面
    @GetMapping("/bookListData")
    public String toBookListData(@RequestParam("pageNumber")int pageNumber,
                                 HttpServletRequest request){
        int number = bookListService.getNumber();   //获取当前书籍数量
        float num = number;                         //将number转化为float
        number = (int) Math.ceil(num/10);           //书籍整除以10再向上取整 求出一共有多少页
        //对pageNumber进行判断
        if (pageNumber == 0){                       //如果是第一次进入该页面，页码从1开始
            pageNumber = 1;
        }else if (pageNumber > number){             //如果pageNumber超过书籍最大页数，pageNumber调整为最大值不再变化
            pageNumber = number;
        }
        List<BookList> bookLists = bookListService.getAll(pageNumber);
        request.setAttribute("bookLists",bookLists);
        request.setAttribute("pageNumber",pageNumber);
        return "/bookList/bookListData";
    }

    //跳转至书单管理页面
    @GetMapping("/bookListManage")
    public String toBookListManage(@RequestParam("bookListId")int bookListId,
                                   HttpServletRequest request){
        BookList bookList = bookListService.getBookListById(bookListId);
        request.setAttribute("bookList",bookList);
        return "/bookList/bookListManage";
    }

    //跳转至书单添加页面
    @GetMapping("/bookListAdd")
    public String toBookListAdd(){
        return "bookList/bookListAdd";
    }

    /**
     * 跳转至评论管理类页面
     */
    @GetMapping("/bookCommentManage")
    public String toBookCommentManage(@RequestParam("pageNumber")int pageNumber,
                                      HttpServletRequest request){
        int number = bookCommentService.getNumber();   //获取当前书评数量（50为一页）
        float num = number;                             //将number转化为float
        number = (int) Math.ceil(num/50);               //书籍整除以50再向上取整 求出一共有多少页
        //对pageNumber进行判断
        if (pageNumber == 0){                           //如果是第一次进入该页面，页码从1开始
            pageNumber = 1;
        }else if (pageNumber > number){                 //如果pageNumber超过书籍最大页数，pageNumber调整为最大值不再变化
            pageNumber = number;
        }
        List<BookComment> bookComments = bookCommentService.getAllComment(pageNumber);
        request.setAttribute("bookComments",bookComments);
        request.setAttribute("pageNumber",pageNumber);
        return "bookComment/bookCommentManage";
    }


    /**
     *
     * 页面添加逻辑类代码
     *
     */
    //书籍添加方法
    @PostMapping("/bookAdd")
    public synchronized String bookAdd(@RequestParam("bookName") String bookName,                    //书名
                                       @RequestParam("bookWriter") String bookWriter,                //作者
                                       //bookPublishTime
                                       @RequestParam("year") String year,                            //出版年份
                                       @RequestParam("month") String month,                          //出版月份
                                       @RequestParam("day") String day,                              //出版日期

                                       @RequestParam("bookPublishArea") String bookPublishArea,      //出版地区
                                       //bookPic
                                       @RequestParam("bookPic") MultipartFile file,                  //封面文件

                                       @RequestParam("bookIntroduction")String bookIntroduction,     //简介
                                       @RequestParam("bookPic") float bookStar,                      //评分
                                       @RequestParam("bookDownAddress") String bookDownAddress,      //下载地址
                                       //bookType
                                       @RequestParam("typeId") int typeId,                           //类型Id
                                       @RequestParam("typeName") String typeName,                    //类型名
                                       HttpServletRequest request){
        //合成出版时间
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = year+"-"+month+"-"+day;
        Timestamp bookPublishTime = null;
        try {
            bookPublishTime = new Timestamp(format.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //合成type类型
        BookTypes bookTypes = new BookTypes();
        bookTypes.setTypeId(typeId);
        bookTypes.setTypeName(typeName);
        //如果存在封面，则保存图片文件 并为其命名，命名内容为bookId
        String bookPic = null;
        if (file!=null){
            try {
                //保存地址
                String uploadFilePath = "C:\\Users\\Admin\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\OuranServices\\imgs\\book_pic";
                //如果没有文件夹,则生成文件夹
                if(!new File(uploadFilePath).isDirectory()){
                    new File(uploadFilePath).mkdirs();
                }
                //获取文件后缀名
                String[] fileNames = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
                //获取当前表格中书籍Id最大值
                int number = bookService.getMaxId();
                //Id自动加1作为新书籍Id
                number+=1;
                //合成图片名字(  #{bookId}.#{文件后缀}  ）
                bookPic = number+"."+fileNames[fileNames.length-1];
                //生成IO流
                FileOutputStream fileOutputStream = new FileOutputStream(uploadFilePath+bookPic);
                if(! new File(uploadFilePath+bookPic).exists()) {
                    fileOutputStream.write(file.getBytes());
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //添加Book属性
        Book book = new Book();
        book.setBookName(bookName);                     //书名
        book.setBookWriter(bookWriter);                 //作者名
        book.setBookPublishTime(bookPublishTime);       //出版时间
        book.setBookPublishArea(bookPublishArea);       //出版地区
        if(bookPic!= null){
            book.setBookPic(bookPic);                   //封面
        }
        book.setBookIntroduction(bookIntroduction);     //简介
        book.setBookStar(bookStar);                     //评分
        book.setBookDownAddress(bookDownAddress);       //下载地址
        bookService.addBook(book);
        request.setAttribute("pageNumber",1);
        return "book/bookData";
    }
}
