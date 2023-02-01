package com.codingrecipe.board.repository;

import com.codingrecipe.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final SqlSessionTemplate sql;
    public int save(BoardDTO boardDTO) {
        return sql.insert("Board.save", boardDTO);
    }

    public List<BoardDTO> findAll() {
        return sql.selectList("Board.findAll");
    }

    public BoardDTO findById(Long id) {
        return sql.selectOne("Board.findById", id);
    }

    public void updateHits(Long id) {
        sql.update("Board.updateHits", id);
    }

    public void delete(Long id) {
        sql.delete("Board.delete", id);
    }
}
