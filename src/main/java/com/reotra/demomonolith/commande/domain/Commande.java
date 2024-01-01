package com.reotra.demomonolith.commande.domain;

import com.reotra.demomonolith.livraison.domain.LivraisonCommande;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
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
@ToString(exclude = {"evolutions", "produits"})
public class Commande {

    @Id
    private String numCommande;

    private Double prixTotal;

    private Integer quantite;

    private StatutCommande statut;

    private LocalDateTime dateCreation;

    private LocalDateTime dateLivraison;

    @ManyToMany
    @JoinTable(
            name = "produit_commande",
            joinColumns = @JoinColumn(name = "num_commande"),
            inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
    private Collection<Produit> produits;

    @OneToMany(mappedBy = "commande", orphanRemoval = true)
    private Collection<EvolutionCommande> evolutions;

    @OneToMany(mappedBy = "commande", orphanRemoval = true)
    private Collection<LivraisonCommande> historiqueLivraison;
}
