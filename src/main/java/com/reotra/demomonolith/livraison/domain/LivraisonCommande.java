package com.reotra.demomonolith.livraison.domain;

import com.reotra.demomonolith.commande.domain.Commande;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity(name = "livraison_commande")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivraisonCommande {
    @Id
    private String numeroLivraison;

    private String adresse;

    private StatutLivraison statut;

    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "num_commande", nullable = false)
    private Commande commande;

    @OneToMany(mappedBy = "livraison", orphanRemoval = true)
    private Collection<SuiviLivraison> historiques;
}
