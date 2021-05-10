#方法及接口备注

##book-4 上传书籍
###作用:
根据 book 对象上传书籍数据到数据库
###接口网址:
/ouranbook/book/add
###接口参数:
?book=#{book}

##book-5 修改书籍信息
###作用:
根据 book 对象修改数据库中对应书籍数据
###接口网址:
/ouranbook/book/update
###接口参数:
?book=#{book}

##book-6 删除书籍
###作用:
根据 bookId 删除数据库中对应书籍数据
###接口网址:
/ouranbook/book/delete
###接口参数:
?book=#{book}