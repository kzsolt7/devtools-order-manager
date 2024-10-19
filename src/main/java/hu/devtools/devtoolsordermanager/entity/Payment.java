package hu.devtools.devtoolsordermanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String description;
    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}