package com.librarymknw.bookService.infrastructure.adapters;

import com.librarymknw.bookService.infrastructure.exceptions.BookRepositoryJPAException;
import com.librarymknw.bookService.core.domain.models.Book;
import com.librarymknw.bookService.core.ports.BookRepositoryPort;
import com.librarymknw.bookService.infrastructure.persistence.entities.BookEntity;
import com.librarymknw.bookService.infrastructure.persistence.mappers.BookMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryJPA implements BookRepositoryPort {

    @PersistenceContext
    private EntityManager em;

    private final BookMapper bookMapper;

    public BookRepositoryJPA(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional
    public Long create(Book book) throws BookRepositoryJPAException {
        try {
            BookEntity bookEntity = bookMapper.toEntity(book);
            em.persist(bookEntity);
            return bookEntity.getBookId();
        } catch (Exception e) {
            throw new BookRepositoryJPAException("Error creating book " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Book> read() throws BookRepositoryJPAException {
        try {
            TypedQuery<BookEntity> query = em.createQuery("SELECT b FROM BookEntity b", BookEntity.class);
            List<BookEntity> bookEntities = query.getResultList();
            return bookMapper.toDomainList(bookEntities);
        } catch (Exception e) {
            throw new BookRepositoryJPAException("Error reading books " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Long delete(Long id) throws BookRepositoryJPAException {
        try {
            BookEntity bookEntity = em.find(BookEntity.class, id);
            if (bookEntity != null) {
                em.remove(bookEntity);
                return id;
            } else {
                throw new BookRepositoryJPAException("Book not found with id " + id);
            }
        } catch (Exception e) {
            throw new BookRepositoryJPAException("Error deleting book " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Long update(Long id, Book book) throws BookRepositoryJPAException {
        try {
            BookEntity existingEntity = em.find(BookEntity.class, id);
            if (existingEntity == null) {
                throw new BookRepositoryJPAException("Book not found");
            }
            BookEntity updatedEntity = bookMapper.toEntity(book);
            updatedEntity.setBookId(id); // Assicura che l'ID rimanga lo stesso
            em.merge(updatedEntity);
            return id;
        } catch (Exception e) {
            throw new BookRepositoryJPAException("Error updating book " + e.getMessage());
        }
    }

    @Override
    public Book getById(Long id) throws BookRepositoryJPAException {
        try {
            BookEntity bookEntity = em.find(BookEntity.class, id);
            return bookEntity != null ? bookMapper.toDomain(bookEntity) : null;
        } catch (Exception e) {
            throw new BookRepositoryJPAException("Error getting book by id " + e.getMessage());
        }
    }

    @Override
    public List<Book> findByText(String searchText) throws BookRepositoryJPAException {
        try {
            String query = "SELECT b FROM BookEntity b " +
                    "LEFT JOIN b.editor e " +
                    "LEFT JOIN b.authors a " +
                    "WHERE b.title LIKE :searchText " +
                    "OR b.isbn LIKE :searchText " +
                    "OR e.name LIKE :searchText " +
                    "OR a.name LIKE :searchText " +
                    "OR a.surname LIKE :searchText " +
                    "GROUP BY b.bookId";

            List<BookEntity> bookEntities = em.createQuery(query, BookEntity.class)
                    .setParameter("searchText", "%" + searchText + "%")
                    .getResultList();

            return bookMapper.toDomainList(bookEntities);
        } catch (Exception e) {
            throw new BookRepositoryJPAException("Error finding books by text " + e.getMessage());
        }
    }

    @Override
    public List<Book> findByObject(Book searchBook) throws BookRepositoryJPAException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookEntity> cq = cb.createQuery(BookEntity.class);
        Root<BookEntity> book = cq.from(BookEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        if (searchBook.getTitle() != null && !searchBook.getTitle().isEmpty()) {
            predicates.add(cb.like(book.get("title"), "%" + searchBook.getTitle() + "%"));
        }
        if (searchBook.getIsbn() != null && !searchBook.getIsbn().isEmpty()) {
            predicates.add(cb.like(book.get("isbn"), "%" + searchBook.getIsbn() + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<BookEntity> query = em.createQuery(cq);
        List<BookEntity> bookEntities = query.getResultList();
        return bookMapper.toDomainList(bookEntities);
    }
}
