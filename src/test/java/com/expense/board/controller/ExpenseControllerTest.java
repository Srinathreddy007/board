package com.expense.board.controller;

import com.expense.board.controller.ExpenseController;
import com.expense.board.model.Expense;
import com.expense.board.repository.ExpenseRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTest
{
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectmapper;
    @MockBean
    ExpenseRepo expenseRepo;

    Expense record3=new Expense(1L,"Bussiness Trip to Canada","Travel",20000L,new Date(22-02-27),"USD");
    Expense record2=new Expense(2L,"Family trip to Manali","Travel", 10000L,new Date(22-02-02),"USD");
    Expense record1=new Expense(3L,"FordMustangEMI","AutoLoan",20000L,new Date(22-02-05),"USD");
    @Test
    public void getAllExpenses_success() throws Exception
    {
        List<Expense> record =new ArrayList<>(Arrays.asList(record1,record2,record3));
        Mockito.when(expenseRepo.findAll()).thenReturn(record);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/expenses").
                        contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].category", Matchers.is("Travel")));
        //.andExpect(jsonPath("$[1].firstName",is("nk")));



    }
    @Test
    public void getAllExpenses_Failure() throws Exception
    {
        List<Expense> record1 =new ArrayList<>();
        Mockito.when(expenseRepo.findAll()).thenReturn(record1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/expenses").
                        contentType(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertEquals("Data not available", result.getResolvedException().getMessage()));
        //.andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
        //.andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("nk")));
        //.andExpect(jsonPath("$[1].firstName",is("nk")));
    }
    @Test
    public void getAllExpenses_failure() throws Exception
    {
        List<Expense> record =new ArrayList<>(Arrays.asList(record1,record2,record3));
        Mockito.when(expenseRepo.findAll()).thenReturn(record);
        //Mockito.when(employeeRepo.findAll()).thenReturn(record);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/q")
                        .contentType(MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().is4xxClientError());
        //.andExpect(jsonPath("$[1].firstName",is("nk")));



    }

    @Test
    public void deleteExpenseById_success() throws Exception {
        Mockito.when(expenseRepo.findById(record2.getId())).thenReturn(Optional.of(record2));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/expenses/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteExpenseById_notFound() throws Exception {
        Mockito.when(expenseRepo.findById(5L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/expenses/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                // .andExpect(result ->
                //         assertTrue(result.getResolvedException() instanceof ChangeSetPersister.NotFoundException))
                .andExpect(result ->
                        assertEquals("Expense with ID 2 does not exist.", result.getResolvedException().getMessage()));
    }
}




