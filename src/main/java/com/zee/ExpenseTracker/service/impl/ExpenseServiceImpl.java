package com.zee.ExpenseTracker.service.impl;
import com.zee.ExpenseTracker.entity.Expense;
import com.zee.ExpenseTracker.entity.User;
import com.zee.ExpenseTracker.exception.ResourceNotFoundException;
import com.zee.ExpenseTracker.repository.ExpenseRepository;
import com.zee.ExpenseTracker.service.ExpenseService;
import com.zee.ExpenseTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserService userService;
    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepository.findByUserId(userService.getLoggedInUser().getId(),page);
    }
    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> result  = expenseRepository
                .findByUserIdAndId(userService.getLoggedInUser().getId(),id);
        if(result.isPresent())
            return result.get();
        throw new ResourceNotFoundException("Expense not found for the id " + id);
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense expense = getExpenseById(id);
        expenseRepository.delete(expense);
    }

    @Override
    public Expense saveExpenseDetails(Expense expense) {
        User user = userService.getLoggedInUser();
        expense.setUser(user);
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpenseDetails(Expense expense, Long id) {
        Expense existingExpense = expenseRepository.findById(id).get();
        existingExpense.setName(expense.getName()!=null ? expense.getName() : existingExpense.getName());
        existingExpense.setAmount(expense.getAmount()!=null ? expense.getAmount() : existingExpense.getAmount());
        existingExpense.setCategory(expense.getCategory()!=null ? expense.getCategory() : existingExpense.getCategory());
        existingExpense.setDescription(expense.getDescription()!=null ? expense.getDescription() : existingExpense.getDescription());
        expenseRepository.save(existingExpense);
        return existingExpense;
    }

    @Override
    public List<Expense> readByCategory(String category, Pageable pageable) {
        return expenseRepository.
                findByUserIdAndCategory(userService.getLoggedInUser().getId(),category,pageable).toList();
    }

    @Override
    public List<Expense> filterByName(String name, Pageable pageable) {
        return expenseRepository.
        findByUserIdAndNameContaining(userService.getLoggedInUser().getId(),name,pageable).toList();
    }

}
