package com.librarymknw.bookService.infrastructure.adapters;

import com.librarymknw.bookService.infrastructure.exceptions.RepositoryJPAException;
import com.librarymknw.bookService.core.domain.models.Book;
import com.librarymknw.bookService.core.ports.BookRepositoryPort;
import com.librarymknw.bookService.infrastructure.persistence.entities.BookEntity;
import com.librarymknw.bookService.infrastructure.persistence.mappers.BookMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

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
    public Long create(Book book) throws RepositoryJPAException {
        try {
            em.getTransaction().begin();
            BookEntity bookEntity = bookMapper.toEntity(book);
            em.persist(bookEntity);
            em.getTransaction().commit();
            return bookEntity.getBookId();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RepositoryJPAException("Error creating book " + e.getMessage());
        }
    }

    @Override
    public List<Book> read() throws RepositoryJPAException {
        try {
            TypedQuery<BookEntity> query = em.createQuery("SELECT b FROM BookEntity b", BookEntity.class);
            List<BookEntity> bookEntities = query.getResultList();
            return bookMapper.toDomainList(bookEntities);
        } catch (Exception e) {
            throw new RepositoryJPAException("Error reading books " + e.getMessage());
        }
    }

    @Override
    public Long delete(Long id) throws RepositoryJPAException {
        try {
            em.getTransaction().begin();
            BookEntity bookEntity = em.find(BookEntity.class, id);
            if (bookEntity != null) {
                em.remove(bookEntity);
                em.getTransaction().commit();
                return id;
            } else {
                em.getTransaction().rollback();
                throw new RepositoryJPAException("Book not found with id " + id);
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RepositoryJPAException("Error deleting book " + e.getMessage());
        }
    }

    @Override
    public Long update(Long id, Book book) throws RepositoryJPAException {
        try {
            em.getTransaction().begin();
            BookEntity existingEntity = em.find(BookEntity.class, id);
            if (existingEntity == null) {
                throw new RepositoryJPAException("Book not found");
            }
            BookEntity updatedEntity = bookMapper.toEntity(book);
            updatedEntity.setBookId(id); // Assicura che l'ID rimanga lo stesso
            em.merge(updatedEntity);
            em.getTransaction().commit();
            return id;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RepositoryJPAException("Error updating book " + e.getMessage());
        }
    }

    @Override
    public Book getById(Long id) throws RepositoryJPAException {
        try {
            BookEntity bookEntity = em.find(BookEntity.class, id);
            return bookEntity != null ? bookMapper.toDomain(bookEntity) : null;
        } catch (Exception e) {
            throw new RepositoryJPAException("Error getting book by id " + e.getMessage());
        }
    }

    @Override
    public List<Book> findByText(String searchText) throws RepositoryJPAException {
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
            throw new RepositoryJPAException("Error finding books by text " + e.getMessage());
        }
    }

    @Override
    public List<Book> findByObject(Book searchBook) throws RepositoryJPAException {
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
