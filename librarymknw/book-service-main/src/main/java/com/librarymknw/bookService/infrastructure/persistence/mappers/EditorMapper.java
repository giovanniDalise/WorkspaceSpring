package com.librarymknw.bookService.infrastructure.persistence.mappers;

import com.librarymknw.bookService.core.domain.models.Editor;
import com.librarymknw.bookService.infrastructure.persistence.entities.EditorEntity;
import org.springframework.stereotype.Component;

@Component
public class EditorMapper {
    public static Editor toDomain(EditorEntity entity) {
        if (entity == null) return null;
        Editor editor = new Editor();
        editor.setId(entity.getId());
        editor.setName(entity.getName());
        // Books mapping can be added if necessary
        return editor;
    }

    public static EditorEntity toEntity(Editor editor) {
        if (editor == null) return null;
        EditorEntity entity = new EditorEntity();
        entity.setId(editor.getId());
        entity.setName(editor.getName());
        // Books mapping can be added if necessary
        return entity;
    }
}
