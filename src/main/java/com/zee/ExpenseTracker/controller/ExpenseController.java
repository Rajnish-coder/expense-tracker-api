package com.zee.ExpenseTracker.controller;

import com.zee.ExpenseTracker.entity.Expense;
import com.zee.ExpenseTracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(Pageable page){

        return expenseService.getAllExpenses(page).toList();
    }

    @GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable("id") Long id){
        return expenseService.getExpenseById(id);
    }
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam("id") Long id){
        expenseService.deleteExpenseById(id);
    }
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public Expense saveExpenses(@Valid @RequestBody Expense expense){
        return expenseService.saveExpenseDetails(expense);
    }

    @PutMapping("/expenses/{id}")
    public Expense updateExpenses(@RequestBody Expense expense,@PathVariable("id") Long id){
         return expenseService.updateExpenseDetails(expense,id);
    }
    @GetMapping("/expenses/category")
    public List<Expense> getExpenseByCategory(@RequestParam("category") String category,Pageable pageable){
        return expenseService.readByCategory(category,pageable);
    }

    @GetMapping("/expenses/name")
    public List<Expense> getByNameContaining(@RequestParam("name") String name,Pageable pageable){
        return expenseService.filterByName(name,pageable);
    }

}
