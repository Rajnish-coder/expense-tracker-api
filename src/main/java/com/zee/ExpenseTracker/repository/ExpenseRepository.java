package com.zee.ExpenseTracker.repository;

import com.zee.ExpenseTracker.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    //SELECT * FROM tbl_expenses WHERE category = ?
    Page<Expense> findByUserIdAndCategory(Long id,String category, Pageable pageable);



    //SELECT * FROM tbl_expenses WHERE name LIKE '%name%'
    Page<Expense> findByUserIdAndNameContaining(Long id,String name,Pageable pageable);


    Page<Expense> findByUserId(Long id,Pageable page);

    Optional<Expense> findByUserIdAndId(Long userId,Long expenseId);
}
