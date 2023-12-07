package com.attraya.controller;

import com.attraya.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private JdbcClient jdbcClient;

    @PostMapping
    public String addNewBook(@RequestBody Book book) {
        jdbcClient.sql("INSERT INTO book(id, name, title) values(?, ?, ?)")
                .params(List.of(book.getId(), book.getName(), book.getTitle()))
                .update();
        return "book added to the system";
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return jdbcClient.sql("SELECT id, name, title FROM Book")
                .query(Book.class)
                .list();
    }

    @GetMapping("/{id}")
    public Optional<Book> getBooKkById(@PathVariable int id) {
        return jdbcClient.sql("SELECT id, name, title FROM Book where id = :id")
                .param("id", id)
                .query(Book.class).optional();
    }

    @PutMapping("/{id}")
    public String updateBook(@PathVariable int id, @RequestBody Book book) {
        jdbcClient.sql("UPDATE Book SET title = ?, name = ? WHERE id = ?")
                .params(List.of(book.getTitle(), book.getName(), id))
                .update();
        return "book modified in system";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id){
        jdbcClient.sql("DELETE FROM Book WHERE id = :id")
                .param("id", id)
                .update();
        return "book deleted with id "+id;
    }
}
