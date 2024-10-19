package hu.devtools.devtoolsordermanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal amountPaid = BigDecimal.ZERO;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Transient
    private BigDecimal remainingPayment;

    @Transient
    private BigDecimal totalAmountPaid;

    public BigDecimal getRemainingPayment() {
        return (price != null ? price : BigDecimal.ZERO).subtract(getTotalAmountPaid());
    }

    public BigDecimal getTotalAmountPaid() {
        return payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}