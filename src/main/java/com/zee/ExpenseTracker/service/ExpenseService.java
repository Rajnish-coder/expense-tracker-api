package com.zee.ExpenseTracker.service;

import com.zee.ExpenseTracker.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpenseService {
    Page<Expense> getAllExpenses(Pageable page);
    Expense getExpenseById(Long id);
    void deleteExpenseById(Long id);
    Expense saveExpenseDetails(Expense expense);
    Expense updateExpenseDetails(Expense expense,Long id);
    List<Expense> readByCategory(String category,Pageable pageable);
    List<Expense> filterByName(String name,Pageable pageable);
}
