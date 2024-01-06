#### ID des produits
SET @Produit_1_ID = UUID_TO_BIN(UUID());
SET @Produit_2_ID = UUID_TO_BIN(UUID());
SET @Produit_3_ID = UUID_TO_BIN(UUID());
SET @Produit_4_ID = UUID_TO_BIN(UUID());
SET @Produit_5_ID = UUID_TO_BIN(UUID());

##### LES PRODUITS
insert into produits (id, nom, prix, date_creation) values (@Produit_1_ID, 'TELEPHONE KUNFABO', 125.0, {ts '2023-12-31 20:30:45.0'});
insert into produits (id, nom, prix, date_creation) values (@Produit_2_ID, 'ORDINATEUR KUNFABO', 6500.0, {ts '2023-12-31 20:30:45.0'});
insert into produits (id, nom, prix, date_creation) values (@Produit_3_ID, 'BORNE MEDICAL TULIP INDUSTRY', 125000.0, {ts '2023-12-31 20:30:45.0'});
insert into produits (id, nom, prix, date_creation) values (@Produit_4_ID, 'DRONE AGRICOL TULIP INDUSTRY', 6500000.0, {ts '2023-12-31 20:30:45.0'});
insert into produits (id, nom, prix, date_creation) values (@Produit_5_ID, 'TABLETTE GUINEA', 5400.0, {ts '2023-12-31 20:30:45.0'});

#### STOCKS_PRODUITS
insert into stocks_produits (stock_actuel, date_creation, id, produit_id) values (5000, {ts '2023-12-31 20:30:45.0'}, UUID_TO_BIN(UUID()), @Produit_1_ID);
insert into stocks_produits (stock_actuel, date_creation, id, produit_id) values (1500, {ts '2023-12-31 20:30:45.0'}, UUID_TO_BIN(UUID()), @Produit_2_ID);
insert into stocks_produits (stock_actuel, date_creation, id, produit_id) values (500, {ts '2023-12-31 20:30:45.0'}, UUID_TO_BIN(UUID()), @Produit_3_ID);
insert into stocks_produits (stock_actuel, date_creation, id, produit_id) values (100, {ts '2023-12-31 20:30:45.0'}, UUID_TO_BIN(UUID()), @Produit_4_ID);
insert into stocks_produits (stock_actuel, date_creation, id, produit_id) values (5000, {ts '2023-12-31 20:30:45.0'}, UUID_TO_BIN(UUID()), @Produit_5_ID);


### COUNTER
insert into counter (current_value, type, date_changement) VALUES (0, 'COMMANDE', {ts '2023-12-31 20:30:45.0'});
insert into counter (current_value, type, date_changement) VALUES (0, 'LIVRAISON', {ts '2024-01-06 20:30:45.0'});