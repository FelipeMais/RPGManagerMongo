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
		 
-- Escola Magia
-- INSERT INTO EscolaMagia (nome_escola_magia, descricao) VALUES 
-- ('Evocação', 'Manipulação de energia'),
-- ('Transmutação', 'Manipulação da matéria'),
-- ('Divinação', 'Visualização do futuro e do que está oculto'),
-- ('Abjuração', 'Proteção contra magias e outros males'),
-- ('Encantamento', 'Persuação e manipulação da mente')
-- ('Ilusão', 'Enganação dos sentidos');

INSERT INTO Magias (nome_magia, descricao, custo_mana, nivel_minimo, dados) 
VALUES ('Bola de Fogo', 'Mata geral. Explosão de fogo', 15, 3, '8d6'),
       ('Míssel Mágico', 'Dardo de força infálivel', 5, 1, '3d4+3'),
	   ('Corda Estática', 'Liga dois alvos com um raio.', 15, 2, '2d8'),
	   ('Ponto de Fulgor', 'Pequeno ponto que inflinge dano massivo apenas se o alvo for menor que um metro cúbico', 10, 1, '3d6'),
	   ('Soldagem Molecular', 'Instantaneamente funde dois objetos que estão se tocando a um nível atômico', 12, 2, ''),
	   ('Lábia Aúrea', 'Torna a saliva do feiticeiro em ouro liquido. Qualquer palavra dita tem um valor literal.', 20, 3, ''),
	   ('Ecolocalização', 'Vestigios de movimentos recentes se tornam visíveis. Se torna possível rastrear uma criatura que passou pela área recentemente, mesmo que invisível.', 8, 1, ''),
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
	   
-- Relação Magia com Escola (MagiaEscola)
-- INSERT INTO MagiaEscola (id_escola_magia, id_magia) VALUES 
-- (1, 1), -- Bola de Fogo é Evocação
-- (1, 2), -- Míssel Mágico é Evocação
-- (1, 3), -- Corda Estática é Evocação
-- (1, 4), -- Ponto de Fulgor é Evocação
-- (2, 5), -- Soldagem Molecular é Transmutação
-- (2, 6), -- Lábia Aúrea é Transmutação
-- (3, 7), -- Ecolocalização é Divinação
-- (3, 8), -- Clarividência é Divinação
-- (4, 9), -- Reflexão Cinética é Abjuração
-- (4, 10), -- Aura de Sobriedade é Abjuração
-- (4, 11), -- Pele de Pedra é Abjuração
-- (5, 12), -- Monólogo é Encantamento
-- (5, 13), -- Remorso é Encantamento
-- (6, 14), -- Membros Fantasma é Ilusão
-- (6, 15), -- Borrão de Profundidade é Ilusão


INSERT INTO TipoLocal (nome_tipo_local, descricao) 
VALUES ('Região', 'Área extensa'), --1 
	   ('Cômodo', 'Espaço interno'), --2
	   ('Rio', 'Fluxo extenso de água corrente'), --3
	   ('Vila', 'Pequena comunidade'), --4
	   ('Cidade', 'Grande comunidade'), --5
	   ('Caverna', 'Cavidade natural subterrânea'), --6
	   ('Ninho', 'Espaço onde criaturas e seus filhotes se recolhem'), --7
	   ('Floresta', 'Supérficie extensa coberta de vegetação de grande porte'), --8
	   ('Mar', 'Grande corpo de água'), --9
	   ('Oceano', 'Gigantesco corpo de água'), --10
	   ('Guilda', 'Espaço onde especialistas se reúnem'), --11
	   ('Cometa', 'Corpo celestial composto primariamente de gelo');--12
INSERT INTO Local (nome_local, id_tipo_local) 
VALUES ('Reino de Eldoria', 1),--1
	   ('Império de Erebonia', 1),--2
	   ('Crossbell', 5),--3
	   ('Reino de Liberl', 1),--4
	   ('Quahog', 5);--5
INSERT INTO Local (nome_local, id_tipo_local, local_pai) 
VALUES ('A Taverna Dourada', 2, 1),--6
	   ('Caverna do Dragão', 6, 1),--7
	   ('Rolent', 4, 4);--8
INSERT INTO Local (nome_local, id_tipo_local, local_pai, descricao)
VALUES ('Heimdallr', 5, 2, 'Capital de Erebonia'),--9
	   ('The Druken Clam', 2, 5, 'Pub de Family Guy'),--10
	   ('Floresta Negra', 8, 1, 'Floresta de pinheiros altos. Habitada por fadas.');--11

-- Parte 2

-- Character Sheets (Fichas)
INSERT INTO Ficha (id_classe, id_especie, pontos_vida_max, pontos_mana_max, forca, inteligencia) 
VALUES (1, 3, 50, 0, 18, 8), -- Guerreiro Ogro
       (2, 2, 20, 40, 8, 18); -- Mago Elfo
INSERT INTO Ficha (id_classe, id_especie, pontos_vida_max, pontos_mana_max, forca, inteligencia, constituicao, nivel) 
VALUES (8, 1, 120, 400, 12, 20, 14, 18), -- Aoko Aozaki
       (1, 1, 160, 80, 16, 12, 15, 12), -- Estelle Bright
	   (2, 2, 140, 60, 14, 13, 14, 15), -- Legolas
	   (9, 6, 110, 250, 13, 16, 12, 14), -- Aya Shameimaru
	   (7, 1, 95, 350, 10, 19, 11, 14), -- Marisa Kirisame
	   (12, 1, 85, 30, 13, 11, 12, 8), -- Tohno Shiki
	   (11, 1, 155, 180, 14, 17, 15, 17), -- Obi-Wan Kenobi
	   (10, 1, 220, 10, 17, 6, 20, 5), -- Peter Griffin
	   (15, 4, 4, 0, 3, 2, 8, 1), -- Rato
	   (16, 7, 25, 0, 18, 4, 15, 2), -- Cavalo
	   (14, 1, 28, 10, 14, 10, 13, 3), -- Bandido
	   (13, 8, 180, 40, 22, 6, 19, 10); -- Wyvern	   

INSERT INTO Jogador (nome_jogador) 
VALUES ('Alice'), --1
	   ('Beto'), --2
	   ('Carlos'), --3
	   ('Vitória'), --4
	   ('Feliplus'), --5
	   ('Pietro'), --6
	   ('Rodnei'); --7

--PCs
INSERT INTO Personagem (id_jogador, id_ficha, local_atual, nome_personagem, pontos_vida, pontos_mana)
VALUES (1, 1, 2, 'Grog, o Forte', 50, 0),--1
       (2, 2, 2, 'Valerius, o Sábio', 20, 40),--2
	   (4, 3, 1, 'Aoko Aozaki', 120, 400),--3
	   (3, 4, 4, 'Estelle Bright', 160, 80),--4
	   (5, 7, 9, 'Marisa Kirisame', 95, 350),--5
	   (6, 8, 1, 'Tohno Shiki', 85, 30),--6
	   (1, 9, 1, 'Obi-Wan Kenobi', 155, 180),--7
	   (7, 10, 10, 'Peter Griffin', 220, 10);--8

--NPCs
INSERT INTO Personagem (id_ficha, local_atual, nome_personagem, pontos_vida, pontos_mana)	
VALUES (5, 1, 'Legolas', 140, 60),--9
	   (6, 9, 'Aya Shameimaru', 110, 250),--10
	   (11, 10, 'Rato A', 4, 0),--11
	   (11, 10, 'Rato B', 2, 0),--12
	   (11, 10, 'Rato C', 3, 0),--13
	   (11, 7, 'Rato D', 4, 0),--14
	   (12, 10, 'Jovial Merryment', 25, 0),--15
	   (13, 7, 'Laslo, o Fora-da-lei', 13, 10),--16
	   (13, 7, 'Lázaro', 28, 10),--17
	   (14, 7, 'Dragonete', 175, 36);--18

-- Parte 3	   

-- Items and Qualities
INSERT INTO Qualidades (nome_qualidade) 
VALUES ('Dano de Fogo'),--1 
	   ('Dano Físico (Cortante)'),--2
	   ('Dano Físico (Contundente)'),--3
	   ('Dano Mágico'),--4
	   ('Alcance'),--5
	   ('Duração'),--6
	   ('Durabilidade'),--7
	   ('Dano Elétrico'),--8
	   ('Área de Efeito'),--9
	   ('Resistência Mágica'),--10
	   ('Resistência Física'),--11
	   ('Cortante'),--12
	   ('Contundente'),--13
	   ('Restauração de Mana'),--14
	   ('Restauração de Vida'),--15
	   ('Amaldiçoado'),--16
	   ('Abençoado'),--17
	   ('Teor Alcoólico'),--18
	   ('Inquebrável'),--19
	   ('Dano Sonoro'),--20
	   ('Resistência contra fogo'),--21
	   ('Pedido de Kaguya');--22

INSERT INTO Itens (nome_item, descricao, peso, valor_monetario) 
VALUES ('Espada de Aço', 'Uma lâmina regular', 3.5, 1500),--1
       ('Poção de Mana', 'Restaura pontos de mana', 0.5, 500),--2
	   ('Galão de Mana', 'Restaura os pontos de mana ostensivamente', 5, 2000),--3
	   ('Poção de Vida', 'Restaura pontos de vida', 0.5, 300),--4
	   ('Poção de Restauração', 'Restaura pontos de vida gradativamente', 0.5, 500),--5
	   ('Latinha de Cerveja Brahma', 'Cerveja barata', 0.3, 150),--6
	   ('Livro de Matemática', 'Livro contendo ensinamentos matemáticos', 1, 1000),--7
	   ('Durandal', 'Lendária espada do paladino Rolando', 4, 50000),--8
	   ('Olifante', 'Chifre utilizado pelo paladino Rolando', 0.8, 40000),--9
	   ('Pridwen', 'Escudo do lendário Rei Arthur', 5, 44000),--10
	   ('Luva de Pedreiro', 'Protege as mãos durante o trabalhado árduo exercido pelos pedreiros.', 0.3, 2000),--11
	   ('Gorro do Saci', 'Item fonte do poder do Saci Pererê, concedendo total obediência para quem tiver o gorro em sua posse.', 0.5, 0),--12
	   ('Arco longo', 'Utilizado pelos ingleses com grande efetividade.', 3, 2000),--13
	   ('Flechas', 'Munição para arcos de diversos tipos', 0.01, 100),--14
	   ('Vestes de Huoshu', 'Túnica feita das peles de Ratos-do-Fogo.', 1, 100000),--15
	   ('Ascalon', 'Espada utilizada por São Jorge', 4, 100000),--16
	   ('Tigela de Pedra do Buda', 'Tigela utilizada pelo próprio Buda.', 0.5, 200000),--17
	   ('Ramo de Joias', 'Galho enfeitado de pedras preciosas. Encontrado apenas no Monte Hourai', 0.5, 300000),--18
	   ('Joia Colorida do Dragão', 'Joia de cinco cores. Encontrada no pescoço de certos tipos de dragão.', 0.3, 300000),--19
	   ('Búzio da Andorinha', 'Búzio nascido de uma andorinha. Praticalmente impossível de ser encontrado', 0.2, 100000);--20

-- Relacao Qualidade e Item
 INSERT INTO ItemCaracteristicas (id_qualidade, id_item, valor)
  VALUES (2, 1, 5),
		 (5, 1, 2),
		 (7, 1, 6),
		 (14, 2, 4),
		 (14, 3, 20),
		 (15, 4, 4),
		 (15, 5, 2),
		 (6, 5, 20),
		 (18, 6, 3),
		 (3, 7, 1),
		 (5, 7, 1),
		 (2, 8, 16),
		 (5, 8, 3),
		 (20, 9, 30),
		 (10, 10, 6),
		 (11, 10, 10),
		 (11, 11, 4),
		 (11, 15, 4),
		 (21, 15, 60),
		 (2, 16, 20),
		 (5, 16, 3),
		 (7, 16, 50);

INSERT INTO ItemCaracteristicas (id_qualidade, id_item)
VALUES (22, 15), (22, 17), (22, 18), (22, 19), (22, 20),
  	   (19, 8), (17, 8), (17, 10), (17, 16), (12 ,14);

INSERT INTO MagiaCaracteristicas(id_magia, id_qualidade, valor)
VALUES (1, 1, 6), (1, 3, 1), (1, 4, 2), (1, 5, 10), (1, 9, 2),
	   (2, 4, 7), (2, 3, 1), (2, 5, 30),
	   (3, 8, 3), (3, 9, 10), (3, 5, 3), (3, 20, 1),
	   (4, 5, 1), (4, 6, 1), (4, 9, 1), (4, 1, 40), (4, 3, 40),
	   (10, 18, -100),
	   (11, 11, 20), (11, 10, 10);

INSERT INTO MagiasConhecidas(id_ficha, id_magia)
VALUES (2, 1), (6, 1), (7, 1), (14, 1),
	   (3, 2), (6, 2), (7, 2), (6, 7);

INSERT INTO Inventario (id_personagem, id_item, quantidade)
VALUES (1, 1, 1), (1, 6, 12),
	   (2, 2, 2), (2, 4, 2), (2, 7, 1),
	   (3, 3, 1),
	   (5, 16, 1),
	   (6, 6, 1), (6, 18, 1),
	   (9, 13, 1), (9, 14, 50),
	   (18, 19, 1);


INSERT INTO Combate (id_local, sumario) 
VALUES (1, 'Briga na taverna.'),
	   (2, 'Duelo de danmaku.');

INSERT INTO Combatentes (id_combate, id_personagem) 
VALUES (1, 1), (1, 2),
	   (2, 5), (2, 10);

INSERT INTO TipoAcaoCombate (nome_acao_combate)
 VALUES ('Ataque'), ('Magia'), ('Evasão'), ('Espera');

-- Turno 1: Grog ataca Valerius com sua Espada de Aço
INSERT INTO AcaoCombate (id_combate, id_tipo_acao_combate, id_agente, id_alvo, id_item_usado, ordem_turno, valor_resultado)
VALUES (1, 1, 1, 2, 1, 1, 12); -- Ataque Físico: 12 de dano

-- Turno 2: Valerius tenta usar Bola de Fogo
INSERT INTO AcaoCombate (id_combate, id_tipo_acao_combate, id_agente, id_alvo, id_magia_usada, ordem_turno, valor_resultado)
VALUES (1, 2, 2, 1, 1, 2, 28); -- Magia: 28 de dano

-- Turno 3: Grog finaliza com um ataque de espada
INSERT INTO AcaoCombate (id_combate, id_tipo_acao_combate, id_agente, id_alvo, id_item_usado, ordem_turno, valor_resultado)
VALUES (1, 1, 1, 2, 1, 3, 15); -- Ataque Físico: 15 de dano

-- Turno 1: Aya usa Evasão para se posicionar
INSERT INTO AcaoCombate (id_combate, id_tipo_acao_combate, id_agente, id_alvo, ordem_turno, valor_resultado)
VALUES (2, 3, 10, 5, 1, 0); -- Evasão (Sucesso)

-- Turno 2: Marisa ataca com Ascalon
INSERT INTO AcaoCombate (id_combate, id_tipo_acao_combate, id_agente, id_alvo, id_item_usado, ordem_turno, valor_resultado)
VALUES (2, 1, 5, 10, 16, 2, 45); -- Ataque com Espada Sagrada: 45 de dano

-- Turno 3: Aya aguarda uma abertura
INSERT INTO AcaoCombate (id_combate, id_tipo_acao_combate, id_agente, id_alvo, ordem_turno, valor_resultado)
VALUES (2, 4, 10, 5, 3, 0); -- Espera

-- Turno 4: Marisa ataca novamente
INSERT INTO AcaoCombate (id_combate, id_tipo_acao_combate, id_agente, id_alvo, id_item_usado, ordem_turno, valor_resultado)
VALUES (2, 1, 5, 10, 16, 4, 38); -- Ataque com Espada Sagrada: 38 de dano

INSERT INTO Habilidades (nome_habilidade, descr_habilidade, atributo_base) 
VALUES 
('Atletismo', 'Capacidade de realizar proezas físicas como escalar, saltar e nadar.', 'Força'),
('Furtividade', 'Habilidade de se mover silenciosamente e se esconder.', 'Destreza'),
('Arcanismo', 'Conhecimento sobre magia, rituais e planos de existência.', 'Inteligência'),
('Percepção', 'Capacidade de notar detalhes, sons e presenças ocultas.', 'Sabedoria'),
('Medicina', 'Conhecimento técnico para tratar ferimentos e doenças.', 'Sabedoria'),
('Intimidação', 'Uso de força ou ameaças para influenciar outros.', 'Carisma'),
('Investigação', 'Habilidade de encontrar pistas e deduzir informações.', 'Inteligência'),
('Persuasão', 'Capacidade de convencer outros através da diplomacia.', 'Carisma');

-- Relacionando as habilidades às fichas (FichaHabilidades)
INSERT INTO FichaHabilidades (id_ficha, id_habilidade)
VALUES 
-- Guerreiro Ogro (Ficha 1): Foco em força bruta
(1, 1), -- Atletismo
(1, 6), -- Intimidação

-- Mago Elfo (Ficha 2): Foco em conhecimento místico
(2, 3), -- Arcanismo
(2, 4), -- Percepção

-- Aoko Aozaki (Ficha 3): Atiradora Mágica
(3, 3), -- Arcanismo
(3, 7), -- Investigação

-- Estelle Bright (Ficha 4): Guerreira versátil
(4, 1), -- Atletismo
(4, 8), -- Persuasão

-- Aya Shameimaru (Ficha 6): Reporter
(6, 7), -- Investigação
(6, 4), -- Percepção

-- Obi-Wan Kenobi (Ficha 9): Mestre Jedi
(9, 8), -- Persuasão
(9, 4), -- Percepção
(9, 3), -- Arcanismo (Conexão com a Força)

-- Peter Griffin (Ficha 10): Pugilista (ou quase isso)
(10, 1), -- Atletismo
(10, 6); -- Intimidação (Involuntária)