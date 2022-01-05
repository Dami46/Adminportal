package com.adminportal.Controler;

import com.adminportal.Domain.Book;
import com.adminportal.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.Properties;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

@Controller
@RequestMapping("/book")
public class BookController {
    CloudBlobContainer container;

    @Autowired
    private BookService bookService;

    public BookController(){
        try{
            Properties prop = new Properties();
            prop.load(new FileInputStream("config.properties"));

            String storageConnectionString = prop.getProperty("storageConnectionString");

            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference("books");
        }
        catch(IOException | StorageException | URISyntaxException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    private void uploadBookImageToStorage(@ModelAttribute("book") Book book, MultipartFile bookImage) {
        try {
            byte[] bytes = bookImage.getBytes();
            String name = book.getId() + ".png";

            CloudBlockBlob blob = container.getBlockBlobReference(name);
            InputStream dataStream = new ByteArrayInputStream(bytes);
            blob.upload(dataStream, bytes.length);

        } catch (IOException | StorageException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "addBook";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBookPost(@ModelAttribute("book") Book book, HttpServletRequest request) {

        bookService.save(book);
        MultipartFile bookImage = book.getBookImage();

        uploadBookImageToStorage(book, bookImage);
        return "redirect:bookList";
    }

    @RequestMapping("/bookInfo")
    public String bookInfo(@RequestParam("id") Long id, Model model) {
        Book book = bookService.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "bookInfo";

    }

    @RequestMapping("/updateBook")
    public String updateBook(@RequestParam("id") Long id, Model model) {
        Book book = bookService.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "updateBook";
    }

    @RequestMapping(value = "/updateBook", method = RequestMethod.POST)
    public String updateBookPost(@ModelAttribute("book") Book book, HttpServletRequest request) {
        bookService.save(book);

        MultipartFile bookImage = book.getBookImage();
        if (!bookImage.isEmpty()) {
            uploadBookImageToStorage(book, bookImage);
        }
        return "redirect:/book/bookInfo?id=" + book.getId();
    }

    @RequestMapping("/bookList")
    public String bookList(Model model) {
        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);
        return "bookList";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(
            @ModelAttribute("id") String id, Model model
    ) {
        bookService.removeOne(Long.parseLong(id.substring(8)));
        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);

        return "redirect:/add";
    }


}
