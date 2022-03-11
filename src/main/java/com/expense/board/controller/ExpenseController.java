package com.expense.board.controller;

import com.expense.board.model.Expense;
import com.expense.board.repository.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ExpenseController
{
    @Autowired
    private ExpenseRepo expenseRepo;
    @GetMapping("/expenses")
    Iterable<Expense> getExpenses()
        {
            return expenseRepo.findAll();
        }
    @DeleteMapping("/expenses/{id}")
    ResponseEntity<?> deleteExpense(@PathVariable Long id){
        expenseRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/expenses")
    ResponseEntity<Expense> createExpense( @RequestBody Expense expense) throws URISyntaxException {
        Expense result= expenseRepo.save(expense);
        return ResponseEntity.created(new URI("/api/expenses" + result.getId())).body(result);
    }
}
