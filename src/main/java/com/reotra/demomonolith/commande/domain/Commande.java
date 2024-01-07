package com.reotra.demomonolith.commande.domain;

import com.reotra.demomonolith.livraison.domain.LivraisonCommande;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity(name = "commandes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"evolutions", "historiqueLivraison"})
@Builder
public class Commande {

    @Id
    private String numCommande;

    private Double prixTotal;

    private Integer quantite;

    @Enumerated(EnumType.STRING)
    private StatutCommande statut;

    private LocalDateTime dateCreation;

    private LocalDateTime dateLivraison;

    private String adresseLivraison;

    @OneToOne
    @JoinColumn(name = "produit_id", referencedColumnName = "id")
    private Produit produit;

    @OneToMany(mappedBy = "commande", orphanRemoval = true)
    private Collection<EvolutionCommande> evolutions;

    @OneToMany(mappedBy = "commande", orphanRemoval = true)
    private Collection<LivraisonCommande> historiqueLivraison;
}
