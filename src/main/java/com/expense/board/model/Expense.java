package com.expense.board.model;

import lombok.*;

//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
import java.time.Instant;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity(name="expense")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
//@Table(name="expense")
public class Expense
{
    @Id
    private Long id;
    private Instant expensedate;
    private String descript;
    private String category;

}
