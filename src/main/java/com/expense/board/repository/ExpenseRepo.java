package com.expense.board.repository;

import com.expense.board.model.Expense;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
//import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepo extends DatastoreRepository<Expense,Long>
{


}
