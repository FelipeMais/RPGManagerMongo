-- Parte 1
-- Species and Classes
INSERT INTO Especie (nome_especie) 
VALUES ('Humano'), ('Elfo'), ('Ogro'), ('Besta'), ('Anão'), ('Tengu'), ('Cavalo'), ('Dracônico');
INSERT INTO Classe (nome_classe, descricao) 
VALUES ('Guerreiro', 'Especialista em combate corporal'),--1
 		('Mago', 'Especialista em conjuração de magias'),--2
		 ('Arqueiro', 'Especialista em arcos e bestas'),--3
		 ('Paladino', 'Cavaleiro defensor de causas nobres'),--4
		 ('Pistoleiro', 'Proficiente no uso de armas de fogo'),--5
		 ('Sarcerdote', 'Especializado em bençãos e exorcismos'),--6
		 ('Alquimista', 'Fabricante de poções e misturas'),--7
		 ('Atirador Mágico', 'Especialista em projéteis mágicos'),--8
		 ('Reporter', 'Especialista em buscar informação'),--9
		 ('Pugilista', 'Especialista em combate desarmado'),--10
		 ('Jedi', 'Monge guerreiro sensível à Força'),--11
		 ('Assassino', 'Especialista em sutileza e agilidade'),--12
		 ('Predador Aéreo', 'Criatura hostil e alada'),--13
		 ('Brutamonte', 'Indivíduo dotado de extrema força bruta'),--14
		 ('Criatura Pequena', 'Animal de pequeno porte'),--15
		 ('Montaria', 'Criatura que pode ser montada por outros personagens');--16
		 
-- Basic Magic Schools and Spells
INSERT INTO EscolaMagia (nome_escola_magia, descricao) VALUES 
('Evocação', 'Manipulação de energia'),
('Transmutação', 'Manipulação da matéria'),
('Divinação', 'Visualização do futuro e do que está oculto'),
('Abjuração', 'Proteção contra magias e outros males'),
('Encantamento', 'Persuação e manipulação da mente')
('Ilusão', 'Enganação dos sentidos');

INSERT INTO Magias (nome_magia, descricao, custo_mana, nivel_minimo, dados) 
VALUES ('Bola de Fogo', 'Mata geral. Explosão de fogo', 15, 3, '8d6'),
       ('Míssel Mágico', 'Dardo de força infálivel', 5, 1, '3d4+3'),
	   ('Corda Estática', 'Liga dois alvos com um raio.', 15, 2, '2d8'),
	   ('Ponto de Fulgor', 'Pequeno ponto que inflinge dano massivo apenas se o alvo for menor que um metro cúbico', 10, 1, '3d6'),
	   ('Soldagem Molecular', 'Instantaneamente funde dois objetos que estão se tocando a um nível atômico', 12, 2, ''),
	   ('Lábia Aúrea', 'Torna a saliva do feiticeiro em ouro liquido. Qualquer palavra dita tem um valor literal.', 20, 3, ''),
	   ('Ecolocalização', 'Vestigios de movimentos recentes se tornam visíveis. Se torna possível rastrear uma criatura que passou pela área recentemente, mesmo que invisível.' 8, 1, ''),
	   ('Clarividência', 'Pelo futuro próximo é possível conhecer exatamente as ações do alvo no próximo turno. Vantagem contra os ataques do alvo enquanto ativo.', 25, 4, ''),
	   ('Reflexão Cinética', 'Cria um plano invisível. Qualquer projétil que o acerte é parado e pode ser mandado de volta no próximo turno.', 18, 3, ''),
	   ('Aura de Sobriedade', 'Remove todos os efeitos de status para todos em alcance. Previne qualquer outro buff mágico de ser aplicado dentro de sua área de efeito', 12, 2, ''),
	   ('Pele de Pedra', 'Torna a pele do alvo resistente como pedra', 8, 4, ''),
	   ('Monólogo', 'Força o alvo a explicar seu plano por um determinado tempo, tornando-o incapaz de qualquer outra ação. Alvo permanece consciente do feitiço', 15, 2, ''),
	   ('Remorso', 'O alvo se arrepende de sua última ação. Seu próximo turno é gasto desfazendo essa ação', 22, 3, ''),
	   ('Membros Fantasma', 'Cria dois membros ilusórios no feiticeiro. Esses membros não tem substância, mas imitando movimentos, tornando impossível de se distinguir dos ataques reais', 10, 1, ''),
	   ('Borrão de Profundidade', 'O alvo tem sua percepção de profundidade manipulada, fazendo-o acreditar que coisas estão mais perto ou mais longe do que realmente estão. Desvantagem em ataques à distância e saltos', 20, 3, ''),
	   ('Limpeza', 'Instantaneamente remove qualquer sujeira em uma dada área ou individuo', 2, 1, ''),
	   ('Fragância Floral', 'Aplica uma frangância da flor escolhida a qualquer objeto', 2, 1, '');
	   
-- Link Spells to Schools (MagiaEscola)
INSERT INTO MagiaEscola (id_escola_magia, id_magia) VALUES 
(1, 1), -- Bola de Fogo é Evocação
(1, 2), -- Míssel Mágico é Evocação
(1, 3), -- Corda Estática é Evocação
(1, 4), -- Ponto de Fulgor é Evocação
(2, 5), -- Soldagem Molecular é Transmutação
(2, 6), -- Lábia Aúrea é Transmutação
(3, 7), -- Ecolocalização é Divinação
(3, 8), -- Clarividência é Divinação
(4, 9), -- Reflexão Cinética é Abjuração
(4, 10), -- Aura de Sobriedade é Abjuração
(4, 11), -- Pele de Pedra é Abjuração
(5, 12), -- Monólogo é Encantamento
(5, 13), -- Remorso é Encantamento
(6, 14), -- Membros Fantasma é Ilusão
(6, 15), -- Borrão de Profundidade é Ilusão


-- Geography
INSERT INTO TipoLocal (nome_tipo_local, descricao) 
VALUES ('Região', 'Área extensa'), 
	   ('Cômodo', 'Espaço interno'),
	   ('Rio', 'Fluxo extenso de água corrente'),
	   ('Vila', 'Pequena comunidade'),
	   ('Cidade', 'Grande comunidade'),
	   ('Caverna', 'Cavidade natural subterrânea'),
	   ('Ninho', 'Espaço onde criaturas e seus filhotes se recolhem'),
	   ('Floresta', 'Supérficie extensa coberta de vegetação de grande porte'),
	   ('Mar', 'Grande corpo de água'),
	   ('Oceano', 'Gigantesco corpo de água'),
	   ('Guilda', 'Espaço onde especialistas se reúnem'),
	   ('Cometa', 'Corpo celestial composto primariamente de gelo');
INSERT INTO Localidade (nome_local, id_tipo_local) 
VALUES ('Reino de Eldoria', 1),
	   ('Império de Erebonia', 1),
	   ('Crossbell', 5),
	   ('Reino de Liberl', 1),
	   ('Quahog', 5);
INSERT INTO Localidade (nome_local, id_tipo_local, local_pai) 
VALUES ('A Taverna Dourada', 2, 1),
	   ('Caverna do Dragão', 6, 1),
	   ('Rolent', 4, 4),
INSERT INTO Localidade (nome_local, id_tipo_local, local_pai, descricao)
	   ('Heimdallr', 5, 2, 'Capital de Erebonia'),
	   ('The Druken Clam', 2, 5, 'Pub de Family Guy');

-- Parte 2

-- Character Sheets (Fichas)
INSERT INTO Ficha (id_classe, id_especie, pontos_vida_max, pontos_mana_max, forca, inteligencia) 
VALUES (1, 3, 50, 0, 18, 8), -- Guerreiro Ogro
       (2, 2, 20, 40, 8, 18); -- Mago Elfo
INSERT INTO Ficha (id_classe, id_especie, pontos_vida_max, pontos_mana_max, forca, inteligencia, constituicao, nivel) 
VALUES (8, 1, 120, 400, 12, 20, 14, 18), -- Aoko Aozaki
       (1, 1, 160, 80, 16, 12, 15, 12), -- Estelle Bright
	   (2, 2, 140, 60, 14, 13, 14, 15), -- Legolas
	   (6, 9, 110, 250, 13, 16, 12, 14), -- Aya Shameimaru
	   (7, 1, 95, 350, 10, 19, 11, 14), -- Marisa Kirisame
	   (12, 1, 85, 30, 13, 11, 12, 8), -- Tohno Shiki
	   (11, 1, 155, 180, 14, 17, 15, 17), -- Obi-Wan Kenobi
	   (10, 1, 220, 10, 17, 6, 20, 5), -- Peter Griffin
	   (15, 4, 4, 0, 3, 2, 8, 1), -- Rato
	   (16, 7, 25, 0, 18, 4, 15, 2), -- Cavalo
	   (14, 1, 28, 10, 14, 10, 13, 3), -- Bandido
	   (13, 8, 180, 40, 22, 6, 19, 10); -- Wyvern	   

-- Characters (Physical instances in the world)
INSERT INTO Personagem (id_ficha, local_atual, nome_personagem, pontos_vida, pontos_mana)
VALUES (1, 2, 'Grog, o Forte', 50, 0),
       (2, 2, 'Valerius, o Sábio', 20, 40),
	   (3, 1, 'Aoko Aozaki', 120, 400),
	   (4, 4, 'Estelle Bright', 160, 80),
	   (5, 1, 'Legolas', 140, 60),
	   (6, 9, 'Aya Shameimaru', 110, 250),
	   (7, 9, 'Marisa Kirisame', 95, 350),
	   (8, 1, 'Tohno Shiki', 85, 30),
	   (9, 1, 'Obi-Wan Kenobi', 155, 180),
	   (10, 10, 'Peter Griffin', 220, 10),
	   (11, 10, 'Rato A', 4, 0),
	   (11, 10, 'Rato B', 2, 0),
	   (11, 10, 'Rato C', 3, 0),
	   (11, 7, 'Rato D', 4, 0),
	   (12, 10, 'Jovial Merryment', 25, 0),
	   (13, 7, 'Laslo, o Fora-da-lei', 13, 10),
	   (13, 7, 'Lázaro', 28, 10),
	   (14, 7, 'Dragonete', 175, 36);

-- Player and Control
INSERT INTO Jogador (nome_jogador) VALUES ('Alice'), ('Beto'), ('Carlos'), ('Vitória'), ('Feliplus'), ('Pietro'), ('Rodnei');
INSERT INTO TipoControle (nome_tipo_controle) VALUES ('PC'), ('Grupo'), ('Substituto');

INSERT INTO ControladorPersonagem (id_personagem, id_jogador, id_tipo_controle)
VALUES (1, 1, 1), -- Alice controla Grog
       (2, 2, 1), -- Beto controla Valerius
	   (5, 3, 1), --
	   (9, 4, 1), -- 
	   (15, 1, 2), -- Jovial é do grupo da Alice
	   (4, 6, 1), -- Pietro controla Estelle Bright
	   (4, 5, 3), -- Feliplus substituiu Estelle Bright certa vez
	   (10, 5, 1), -- Feliplus controla Peter Griffin
	   (7, 7, 1); --

-- Parte 3	   

-- -- Items and Qualities
-- INSERT INTO Qualidades (nome_qualidade) VALUES ('Sharpness'), ('Weight');
-- INSERT INTO Itens (nome_item, descricao, peso, valor_monetario) 
-- VALUES ('Steel Sword', 'A standard blade', 3.5, 150),
--        ('Mana Potion', 'Restores 10 MP', 0.5, 50);

-- -- Link Qualities to Items
-- INSERT INTO ItemCaracteristicas (id_qualidade, id_item, valor) VALUES (1, 1, 5);

-- -- Assign to Inventory
-- INSERT INTO Inventario (id_item, id_personagem, quantidade) VALUES (1, 1, 1), (2, 2, 2);


-- -- Parte 4

-- -- Initialize the Combat Encounter
-- INSERT INTO Combate (id_localidade, sumario) VALUES (2, 'Brawl at the tavern');

-- -- Register Combatants
-- INSERT INTO Combatentes (id_combate, id_personagem) VALUES (1, 1), (1, 2);

-- -- Action Log
-- INSERT INTO TipoAcaoCombate (nome_acao_combate) VALUES ('Attack'), ('Spellcast');

-- -- Grog attacks Valerius (Action 1)
-- INSERT INTO AcaoCombate (id_combate, id_tipo_acao_combate, id_agente, id_alvo, id_item_usado, ordem_turno, valor_resultado)
-- VALUES (1, 1, 1, 2, 1, 1, 12);

-- -- Valerius casts Fireball (Action 2)
-- INSERT INTO AcaoCombate (id_combate, id_tipo_acao_combate, id_agente, id_alvo, id_magia_usada, ordem_turno, valor_resultado)
-- VALUES (1, 2, 2, 1, 1, 2, 24);

-- --


-- -- Define Specific Magic/Item Qualities (MagiaCaracteristicas & ItemCaracteristicas)
-- -- Let's assume Quality 1 is 'Power' and 2 is 'Durability'
-- INSERT INTO Qualidades (nome_qualidade) VALUES ('Durability');

-- INSERT INTO MagiaCaracteristicas (id_qualidade, id_magia, valor) VALUES 
-- (1, 1, 10), -- Fireball has Power 10
-- (1, 2, 4);  -- Magic Missile has Power 4

-- INSERT INTO ItemCaracteristicas (id_qualidade, id_item, valor) VALUES 
-- (2, 1, 100); -- Steel Sword has Durability 100