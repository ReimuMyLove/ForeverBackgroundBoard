package com.ouranservice.controller;

import com.ouranservice.entity.*;
import com.ouranservice.service.BookCommentService;
import com.ouranservice.service.BookListService;
import com.ouranservice.service.BookService;
import com.ouranservice.service.BookTypesService;
import com.ouranservice.util.StaticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
        List<Book> books = bookService.getAll((pageNumber-1)*10);  //获取当前页数书籍
        request.setAttribute("books",books);                            //添加到request中
        request.setAttribute("pageNumber",pageNumber);                  //将页码传送到页面
        request.setAttribute("maxNumber",number);                       //最大页码
        return "book/bookData";
    }

    //跳转至书籍信息管理
    @GetMapping("/bookManage")
    public String toBookManage(@RequestParam("bookId") int bookId,
                               @RequestParam("pageNumber")int pageNumber,
                               HttpServletRequest request){
        int number = bookCommentService.getNumberById(bookId);      //获取当前书评数量（50为一页）
        float num = number;                                         //将number转化为float
        number = (int) Math.ceil(num/50);                           //书籍整除以50再向上取整 求出一共有多少页
        //对pageNumber进行判断
        if (pageNumber == 0){                                       //如果是第一次进入该页面，页码从1开始
            pageNumber = 1;
        }else if (pageNumber > number){                             //如果pageNumber超过书籍最大页数，调整为最大值不再变化
            pageNumber = number;
        }
        Book book = bookService.getDetailById(bookId);
        //获取评论分页列表
        List<BookComment> bookComments =
                bookCommentService.getCommentByBookId(bookId,(pageNumber)*10);
        //获取书籍类型分页列表
        List<BookTypes> bookTypesList = bookTypesService.getAll();

        request.setAttribute("book",book);
        request.setAttribute("bookComments",bookComments);
        request.setAttribute("pageNumber",pageNumber);
        request.setAttribute("maxNumber",number);
        request.setAttribute("bookTypesList",bookTypesList);
        return "book/bookManage";
    }

    //跳转至书籍添加页面
    @GetMapping("/bookAdd")
    public String toBookAdd(HttpServletRequest request){
        List<BookTypes> bookTypesList = bookTypesService.getAll();
        request.setAttribute("bookTypesList",bookTypesList);
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
     *  book添加页面逻辑类代码
     *
     */
    //书籍添加方法
    @PostMapping("/bookAdd")
    public synchronized String bookAdd(@RequestParam(value = "bookName") String bookName,            //书名
                                       @RequestParam("bookWriter") String bookWriter,                //作者
                                       //bookPublishTime
                                       @RequestParam("year") String year,                            //出版年份
                                       @RequestParam("month") String month,                          //出版月份
                                       @RequestParam("day") String day,                              //出版日期

                                       @RequestParam("bookPublishArea") String bookPublishArea,      //出版地区
                                       //bookPic
                                       @RequestParam("bookPic") MultipartFile file,                  //封面文件

                                       @RequestParam("bookIntroduction")String bookIntroduction,     //简介
                                       @RequestParam("bookStar") float bookStar,                     //评分
                                       @RequestParam(value = "bookDownAddress",
                                               required = false) String bookDownAddress,             //下载地址
                                       //bookType
                                       @RequestParam("typeName1") String typeName1,                    //类型名
                                       @RequestParam("typeName2") String typeName2,                    //类型名
                                       @RequestParam("typeName3") String typeName3){
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
        Set<Integer> integerSet = new HashSet<>();
        if (!typeName1.equals("未选择")){
            int typeId1 = bookTypesService.getIdByName(typeName1);
            integerSet.add(typeId1);
        }
        if (!typeName2.equals("未选择")){
            int typeId2 = bookTypesService.getIdByName(typeName2);
            integerSet.add(typeId2);
        }
        if (!typeName3.equals("未选择")){
            int typeId3 = bookTypesService.getIdByName(typeName3);
            integerSet.add(typeId3);
        }
        //如果存在封面，则保存图片文件 并为其命名，命名内容为bookId
        String bookPic = null;
        if (file!=null){
            try {
                //保存地址
                String uploadFilePath = StaticData.UPLOAD_PIC_PATH;
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
                File dest = new File(uploadFilePath+bookPic);
                file.transferTo(dest);
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
        }else{
            book.setBookPic("BookPic.png");             //默认封面
        }
        book.setBookIntroduction(bookIntroduction);     //简介
        book.setBookStar(bookStar);                     //评分
        book.setBookDownAddress(bookDownAddress);       //下载地址
        bookService.addBook(book);
        //添加书籍类型对照表
        int bookId = bookService.getMaxId();            //获取添加后的最大Id（即新书Id）
        //for循环读取typeId
        for(Integer integer:integerSet){
            BookToType bookToType = new BookToType(bookId,integer);
            bookService.addBookType(bookToType);
        }
        return "redirect:bookData?pageNumber=1";
    }


    //删除书籍到回收站
    @GetMapping("/bookRecycle")
    public String bookRecycle(@RequestParam("bookId") int bookId){
        //存入书籍数据
        Book book = bookService.getDetailById(bookId);
        //获取属性列表
        Set<BookTypes> bookTypesSet = book.getBookTypes();
        List<BookComment> bookComments = book.getBookComments();
        book.setBookId(0);
        int count = bookService.recycle(book);
        for(BookTypes bookTypes:bookTypesSet){
            BookToType bookToType = new BookToType(bookId,bookTypes.getTypeId());
            bookService.recycleType(bookToType);
        }
        if (bookComments!= null){
            for(BookComment bookComment:bookComments){
                bookCommentService.recycleComment(bookComment);
            }
        }
        if (count == 1){
            int count1 = bookService.deleteBook(bookId);
            if (count1 > 0){
                System.out.println("删除成功：已添加到回收站");
            }else{
                System.out.println("删除失败：无法添加到数据表中");
            }
        }else{
            System.out.println("删除失败：无法添加到回收站中");
        }
        return "redirect:bookData?pageNumber=1";
    }

    //添加书籍属性
    @PostMapping("/typeAdd")
    public String typeAdd(@RequestParam("typeName")String typeName,
                          HttpServletRequest request){
        BookTypes bookTypes = new BookTypes();
        bookTypes.setTypeName(typeName);
        int count = bookTypesService.typeAdd(bookTypes);
        if (count == 0){
            System.out.println("添加失败");
        }
        List<BookTypes> bookTypesList = bookTypesService.getAll();
        request.setAttribute("bookTypesList",bookTypesList);
        return "book/bookType";
    }

    /**
     *  bookManage可视化界面的数据交互方法
     */
    //书籍添加方法
    @PostMapping("/bookUpdate")
    public synchronized String bookUpdate(@RequestParam("bookId")int bookId,                         //Id
                                        @RequestParam(value = "bookName") String bookName,           //书名
                                       @RequestParam("bookWriter") String bookWriter,                //作者
                                       //bookPublishTime
                                       @RequestParam("year") String year,                            //出版年份
                                       @RequestParam("month") String month,                          //出版月份
                                       @RequestParam("day") String day,                              //出版日期

                                       @RequestParam("bookPublishArea") String bookPublishArea,      //出版地区
                                       //bookPic
                                       @RequestParam("bookPic") MultipartFile file,                  //封面文件

                                       @RequestParam("bookIntroduction")String bookIntroduction,     //简介
                                       @RequestParam("bookStar") float bookStar,                     //评分
                                       @RequestParam(value = "bookDownAddress",
                                               required = false) String bookDownAddress,             //下载地址
                                       //bookType
                                       @RequestParam("typeName1") String typeName1,                    //类型名
                                       @RequestParam("typeName2") String typeName2,                    //类型名
                                       @RequestParam("typeName3") String typeName3){
        //获取原始书籍数据
        Book book = bookService.getDetailById(bookId);
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
        Set<Integer> integerSet = new HashSet<>();//采用set防止多选重复
        if (!typeName1.equals("未选择")){
            int typeId1 = bookTypesService.getIdByName(typeName1);
            integerSet.add(typeId1);
        }
        if (!typeName2.equals("未选择")){
            int typeId2 = bookTypesService.getIdByName(typeName2);
            integerSet.add(typeId2);
        }
        if (!typeName3.equals("未选择")){
            int typeId3 = bookTypesService.getIdByName(typeName3);
            integerSet.add(typeId3);
        }
        //获取原书封面
        String bookPic = book.getBookPic();
        //判断是否是默认封面文件
        if (bookPic.equals("BookPic.png")){     //如果是，则改名为当前书籍Id
            bookPic = bookId+"";
        }
        if (file!=null){
            try {
                //保存地址
                String uploadFilePath = StaticData.UPLOAD_PIC_PATH;
                //如果没有文件夹,则生成文件夹
                if(!new File(uploadFilePath).isDirectory()){
                    new File(uploadFilePath).mkdirs();
                }
                //获取文件后缀名
                String[] fileNames = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
                //生成文件
                File goalPic = new File(uploadFilePath+bookPic);
                if (goalPic.exists()){
                    goalPic.delete();
                }
                //生成IO流
                file.transferTo(goalPic);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //开始添加修改后的Book属性
        book.setBookName(bookName);                     //书名
        book.setBookWriter(bookWriter);                 //作者名
        book.setBookPublishTime(bookPublishTime);       //出版时间
        book.setBookPublishArea(bookPublishArea);       //出版地区
        book.setBookPic(bookPic);                       //封面
        book.setBookIntroduction(bookIntroduction);     //简介
        book.setBookStar(bookStar);                     //评分
        book.setBookDownAddress(bookDownAddress);       //下载地址
        bookService.updateBook(book);
        //清除书籍原有属性,防止出现属性重复现象
        bookService.deleteBookType(bookId);
        //for循环读取typeId
        for(Integer integer:integerSet){
            BookToType bookToType = new BookToType(bookId,integer);
            bookService.addBookType(bookToType);
        }
        return "redirect:bookData?pageNumber=1";
    }




    /**
     *  type属性的可视化界面数据交互管理
     */

    @GetMapping("/typeDelete")
    public String typeDelete(@RequestParam("typeId") int typeId,
                             HttpServletRequest request){
        int count = bookTypesService.typeDelete(typeId);
        if (count == 0){
            System.out.println("删除失败");
        }
        List<BookTypes> bookTypesList = bookTypesService.getAll();
        request.setAttribute("bookTypesList",bookTypesList);
        return "book/bookType";
    }

    @GetMapping("/typeUpdate")
    public String typeUpdate(@RequestParam("typeId") int typeId,
                             @RequestParam("typeName")String typeName,
                             HttpServletRequest request){
        int count = bookTypesService.typeUpdate(typeId,typeName);
        if (count == 0){
            System.out.println("修改失败");
        }
        List<BookTypes> bookTypesList = bookTypesService.getAll();
        request.setAttribute("bookTypesList",bookTypesList);
        return "book/bookType";
    }
}
