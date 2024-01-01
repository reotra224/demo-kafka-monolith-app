package com.reotra.demomonolith.stock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "mouvement_stock")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MouvementStock {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private Integer ancienStock;

    private Integer nouveauStock;

    private LocalDateTime dateMouvement;

    @ManyToOne
    @JoinColumn(name = "stock_produit_id", nullable = false)
    private StockProduit stockProduit;
}
