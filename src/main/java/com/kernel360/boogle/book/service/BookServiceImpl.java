package com.kernel360.boogle.book.service;

import com.kernel360.boogle.book.db.BookEntity;
import com.kernel360.boogle.book.db.BookRepository;
import com.kernel360.boogle.book.model.BookDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<BookEntity> findAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public void saveBook(BookDTO book) {
        bookRepository.save(book.getBookEntity());
    }

    @Override
    public void updateBook(BookDTO book) {
        BookEntity existingBookEntity = bookRepository.findById(book.getBookEntity().getBookId()).orElse(null);
        // Save the updated book entity.
        if (existingBookEntity != null)
            bookRepository.save(existingBookEntity);
    }

    @Override
    public List<BookEntity> findBookBySearchWord(String searchWord) {
        Set<BookEntity> uniqueBooks = new HashSet<>();

        uniqueBooks.addAll(bookRepository.findBookEntitiesByBookTitleContaining(searchWord));
        uniqueBooks.addAll(bookRepository.findBookEntitiesByAuthorContaining(searchWord));
        uniqueBooks.addAll(bookRepository.findBookEntitiesByPublisherContaining(searchWord));

        return new ArrayList<>(uniqueBooks);
    }
}