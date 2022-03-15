package com.expense.board.controller;

import com.expense.board.exception.ResourceNotFoundException;
import com.expense.board.model.Expense;
import com.expense.board.repository.ExpenseRepo;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class ExpenseController
{
    @Autowired
     ExpenseRepo expenseRepo;
    @GetMapping("/expenses")
    Iterable<Expense> getExpenses()
        {
            if((Iterables.size(expenseRepo.findAll())<=0))
            {
                throw new ResourceNotFoundException("Data not available");
            }
            return expenseRepo.findAll();
        }
    @DeleteMapping("/expenses/{id}")
    public void deleteExpense(@PathVariable(value="id") Long id) throws ResourceNotFoundException
    {
        if (expenseRepo.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Expense with ID " + id + " does not exist.");
        }
        expenseRepo.deleteById(id);

    }

    @PostMapping("/expenses")
    ResponseEntity<Expense> createExpense( @RequestBody Expense expense) throws URISyntaxException {
        Expense result= expenseRepo.save(expense);
        return ResponseEntity.created(new URI("/api/expenses" + result.getId())).body(result);
    }
}
