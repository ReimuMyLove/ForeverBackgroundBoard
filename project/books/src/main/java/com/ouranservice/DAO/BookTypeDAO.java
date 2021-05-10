package com.ouranservice.DAO;

import com.ouranservice.entity.Book;
import com.ouranservice.entity.BookTypes;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface BookTypeDAO {

    //根据书籍Id获取对应的Types        OK
    Set<BookTypes> getTypesByBookId(int bookId);

    //根据typeName查询对应typeId      OK
    BookTypes getIdByName(String typeName);

    //根据typeId查询对应type信息      OK
    BookTypes getTypeByTypeId(int typeId);
}
