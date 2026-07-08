db.Especie.insertMany([
  { _id: 1, nome_especie: "Humano" },
  { _id: 2, nome_especie: "Elfo" },
  { _id: 3, nome_especie: "Ogro" },
  { _id: 4, nome_especie: "Besta" },
  { _id: 5, nome_especie: "Anão" },
  { _id: 6, nome_especie: "Tengu" },
  { _id: 7, nome_especie: "Cavalo" },
  { _id: 8, nome_especie: "Dracônico" }
]);

db.Classe.insertMany([
  { _id: 1, nome_classe: "Guerreiro", descricao: "Especialista em combate corporal" },
  { _id: 2, nome_classe: "Mago", descricao: "Especialista em conjuração de magias" },
  { _id: 3, nome_classe: "Arqueiro", descricao: "Especialista em arcos e bestas" },
  { _id: 4, nome_classe: "Paladino", descricao: "Cavaleiro defensor de causas nobres" },
  { _id: 5, nome_classe: "Pistoleiro", descricao: "Proficiente no uso de armas de fogo" },
  { _id: 6, nome_classe: "Sarcerdote", descricao: "Especializado em bençãos e exorcismos" },
  { _id: 7, nome_classe: "Alquimista", descricao: "Fabricante de poções e misturas" },
  { _id: 8, nome_classe: "Atirador Mágico", descricao: "Especialista em projéteis mágicos" },
  { _id: 9, nome_classe: "Reporter", descricao: "Especialista em buscar informação" },
  { _id: 10, nome_classe: "Pugilista", descricao: "Especialista em combate desarmado" },
  { _id: 11, nome_classe: "Jedi", descricao: "Monge guerreiro sensível à Força" },
  { _id: 12, nome_classe: "Assassino", descricao: "Especialista em sutileza e agilidade" },
  { _id: 13, nome_classe: "Predador Aéreo", descricao: "Criatura hostil e alada" },
  { _id: 14, nome_classe: "Brutamonte", descricao: "Indivíduo dotado de extrema força bruta" },
  { _id: 15, nome_classe: "Criatura Pequena", descricao: "Animal de pequeno porte" },
  { _id: 16, nome_classe: "Montaria", descricao: "Criatura que pode ser montada por outros personagens" }
]);

db.Magias.insertMany([
  { _id: 1, nome_magia: "Bola de Fogo", descricao: "Mata geral. Explosão de fogo", custo_mana: 15, nivel_minimo: 3, dados: "8d6" },
  { _id: 2, nome_magia: "Míssel Mágico", descricao: "Dardo de força infálivel", custo_mana: 5, nivel_minimo: 1, dados: "3d4+3" },
  { _id: 3, nome_magia: "Corda Estática", descricao: "Liga dois alvos com um raio.", custo_mana: 15, nivel_minimo: 2, dados: "2d8" },
  { _id: 4, nome_magia: "Ponto de Fulgor", descricao: "Pequeno ponto que inflinge dano massivo apenas se o alvo for menor que um metro cúbico", custo_mana: 10, nivel_minimo: 1, dados: "3d6" },
  { _id: 5, nome_magia: "Soldagem Molecular", descricao: "Instantaneamente funde dois objetos que estão se tocando a um nível atômico", custo_mana: 12, nivel_minimo: 2, dados: "" },
  { _id: 6, nome_magia: "Lábia Aúrea", descricao: "Torna a saliva do feiticeiro em ouro liquido. Qualquer palavra dita tem um valor literal.", custo_mana: 20, nivel_minimo: 3, dados: "" },
  { _id: 7, nome_magia: "Ecolocalização", descricao: "Vestigios de movimentos recentes se tornam visíveis. Se torna possível rastrear uma criatura que passou pela área recentemente, mesmo que invisível.", custo_mana: 8, nivel_minimo: 1, dados: "" },
  { _id: 8, nome_magia: "Clarividência", descricao: "Pelo futuro próximo é possível conhecer exatamente as ações do alvo no próximo turno. Vantagem contra os ataques do alvo enquanto ativo.", custo_mana: 25, nivel_minimo: 4, dados: "" },
  { _id: 9, nome_magia: "Reflexão Cinética", descricao: "Cria um plano invisível. Qualquer projétil que o acerte é parado e pode ser mandado de volta no próximo turno.", custo_mana: 18, nivel_minimo: 3, dados: "" },
  { _id: 10, nome_magia: "Aura de Sobriedade", descricao: "Remove todos os efeitos de status para todos em alcance. Previne qualquer outro buff mágico de ser aplicado dentro de sua área de efeito", custo_mana: 12, nivel_minimo: 2, dados: "" },
  { _id: 11, nome_magia: "Pele de Pedra", descricao: "Torna a pele do alvo resistente como pedra", custo_mana: 8, nivel_minimo: 4, dados: "" },
  { _id: 12, nome_magia: "Monólogo", descricao: "Força o alvo a explicar seu plano por um determinado tempo, tornando-o incapaz de qualquer outra ação. Alvo permanece consciente do feitiço", custo_mana: 15, nivel_minimo: 2, dados: "" },
  { _id: 13, nome_magia: "Remorso", descricao: "O alvo se arrepende de sua última ação. Seu próximo turno é gasto desfazendo essa ação", custo_mana: 22, nivel_minimo: 3, dados: "" },
  { _id: 14, nome_magia: "Membros Fantasma", descricao: "Cria dois membros ilusórios no feiticeiro. Esses membros não tem substância, mas imitando movimentos, tornando impossível de se distinguir dos ataques reais", custo_mana: 10, nivel_minimo: 1, dados: "" },
  { _id: 15, nome_magia: "Borrão de Profundidade", descricao: "O alvo tem sua percepção de profundidade manipulada, fazendo-o acreditar que coisas estão mais perto ou mais longe do que realmente estão. Desvantagem em ataques à distância e saltos", custo_mana: 20, nivel_minimo: 3, dados: "" },
  { _id: 16, nome_magia: "Limpeza", descricao: "Instantaneamente remove qualquer sujeira em uma dada área ou individuo", custo_mana: 2, nivel_minimo: 1, dados: "" },
  { _id: 17, nome_magia: "Fragância Floral", descricao: "Aplica uma frangância da flor escolhida a qualquer objeto", custo_mana: 2, nivel_minimo: 1, dados: "" }
]);

db.TipoLocal.insertMany([
  { _id: 1, nome_tipo_local: "Região", descricao: "Área extensa" },
  { _id: 2, nome_tipo_local: "Cômodo", descricao: "Espaço interno" },
  { _id: 3, nome_tipo_local: "Rio", descricao: "Fluxo extenso de água corrente" },
  { _id: 4, nome_tipo_local: "Vila", descricao: "Pequena comunidade" },
  { _id: 5, nome_tipo_local: "Cidade", descricao: "Grande comunidade" },
  { _id: 6, nome_tipo_local: "Caverna", descricao: "Cavidade natural subterrânea" },
  { _id: 7, nome_tipo_local: "Ninho", descricao: "Espaço onde criaturas e seus filhotes se recolhem" },
  { _id: 8, nome_tipo_local: "Floresta", descricao: "Supérficie extensa coberta de vegetação de grande porte" },
  { _id: 9, nome_tipo_local: "Mar", descricao: "Grande corpo de água" },
  { _id: 10, nome_tipo_local: "Oceano", descricao: "Gigantesco corpo de água" },
  { _id: 11, nome_tipo_local: "Guilda", descricao: "Espaço onde especialistas se reúnem" },
  { _id: 12, nome_tipo_local: "Cometa", descricao: "Corpo celestial composto primariamente de gelo" }
]);

db.Local.insertMany([
  { _id: 1, nome_local: "Reino de Eldoria", id_tipo_local: 1, local_pai: null },
  { _id: 2, nome_local: "Império de Erebonia", id_tipo_local: 1, local_pai: null },
  { _id: 3, nome_local: "Crossbell", id_tipo_local: 5, local_pai: null },
  { _id: 4, nome_local: "Reino de Liberl", id_tipo_local: 1, local_pai: null },
  { _id: 5, nome_local: "Quahog", id_tipo_local: 5, local_pai: null },
  { _id: 6, nome_local: "A Taverna Dourada", id_tipo_local: 2, local_pai: 1 },
  { _id: 7, nome_local: "Caverna do Dragão", id_tipo_local: 6, local_pai: 1 },
  { _id: 8, nome_local: "Rolent", id_tipo_local: 4, local_pai: 4 },
  { _id: 9, nome_local: "Heimdallr", id_tipo_local: 5, local_pai: 2, descricao: "Capital de Erebonia" },
  { _id: 10, nome_local: "The Druken Clam", id_tipo_local: 2, local_pai: 5, descricao: "Pub de Family Guy" },
  { _id: 11, nome_local: "Floresta Negra", id_tipo_local: 8, local_pai: 1, descricao: "Floresta de pinheiros altos. Habitada por fadas." }
]);

db.Ficha.insertMany([
  { _id: 1, id_classe: 1, id_especie: 3, pontos_vida_max: 50, pontos_mana_max: 0, forca: 18, destreza: 10, constituicao: 10, inteligencia: 8, sabedoria: 10, carisma: 10, nivel: 1 },
  { _id: 2, id_classe: 2, id_especie: 2, pontos_vida_max: 20, pontos_mana_max: 40, forca: 8, destreza: 10, constituicao: 10, inteligencia: 18, sabedoria: 10, carisma: 10, nivel: 1 },
  { _id: 3, id_classe: 8, id_especie: 1, pontos_vida_max: 120, pontos_mana_max: 400, forca: 12, destreza: 10, constituicao: 14, inteligencia: 20, sabedoria: 10, carisma: 10, nivel: 18 },
  { _id: 4, id_classe: 1, id_especie: 1, pontos_vida_max: 160, pontos_mana_max: 80, forca: 16, destreza: 10, constituicao: 15, inteligencia: 12, sabedoria: 10, carisma: 10, nivel: 12 },
  { _id: 5, id_classe: 2, id_especie: 2, pontos_vida_max: 140, pontos_mana_max: 60, forca: 14, destreza: 10, constituicao: 14, inteligencia: 13, sabedoria: 10, carisma: 10, nivel: 15 },
  { _id: 6, id_classe: 9, id_especie: 6, pontos_vida_max: 110, pontos_mana_max: 250, forca: 13, destreza: 10, constituicao: 12, inteligencia: 16, sabedoria: 10, carisma: 10, nivel: 14 },
  { _id: 7, id_classe: 7, id_especie: 1, pontos_vida_max: 95, pontos_mana_max: 350, forca: 10, destreza: 10, constituicao: 11, inteligencia: 19, sabedoria: 10, carisma: 10, nivel: 14 },
  { _id: 8, id_classe: 12, id_especie: 1, pontos_vida_max: 85, pontos_mana_max: 30, forca: 13, destreza: 10, constituicao: 12, inteligencia: 11, sabedoria: 10, carisma: 10, nivel: 8 },
  { _id: 9, id_classe: 11, id_especie: 1, pontos_vida_max: 155, pontos_mana_max: 180, forca: 14, destreza: 10, constituicao: 15, inteligencia: 17, sabedoria: 10, carisma: 10, nivel: 17 },
  { _id: 10, id_classe: 10, id_especie: 1, pontos_vida_max: 220, pontos_mana_max: 10, forca: 17, destreza: 10, constituicao: 20, inteligencia: 6, sabedoria: 10, carisma: 10, nivel: 5 },
  { _id: 11, id_classe: 15, id_especie: 4, pontos_vida_max: 4, pontos_mana_max: 0, forca: 3, destreza: 10, constituicao: 8, inteligencia: 2, sabedoria: 10, carisma: 10, nivel: 1 },
  { _id: 12, id_classe: 16, id_especie: 7, pontos_vida_max: 25, pontos_mana_max: 0, forca: 18, destreza: 10, constituicao: 15, inteligencia: 4, sabedoria: 10, carisma: 10, nivel: 2 },
  { _id: 13, id_classe: 14, id_especie: 1, pontos_vida_max: 28, pontos_mana_max: 10, forca: 14, destreza: 10, constituicao: 13, inteligencia: 10, sabedoria: 10, carisma: 10, nivel: 3 },
  { _id: 14, id_classe: 13, id_especie: 8, pontos_vida_max: 180, pontos_mana_max: 40, forca: 22, destreza: 10, constituicao: 19, inteligencia: 6, sabedoria: 10, carisma: 10, nivel: 10 }
]);

db.Jogador.insertMany([
  { _id: 1, nome_jogador: "Alice", data_entrada: new Date(), ativo: true },
  { _id: 2, nome_jogador: "Beto", data_entrada: new Date(), ativo: true },
  { _id: 3, nome_jogador: "Carlos", data_entrada: new Date(), ativo: true },
  { _id: 4, nome_jogador: "Vitória", data_entrada: new Date(), ativo: true },
  { _id: 5, nome_jogador: "Feliplus", data_entrada: new Date(), ativo: true },
  { _id: 6, nome_jogador: "Pietro", data_entrada: new Date(), ativo: true },
  { _id: 7, nome_jogador: "Rodnei", data_entrada: new Date(), ativo: true }
]);

db.Personagem.insertMany([
  { _id: 1, id_jogador: 1, id_ficha: 1, local_atual: 2, nome_personagem: "Grog, o Forte", pontos_vida: 50, pontos_mana: 0 },
  { _id: 2, id_jogador: 2, id_ficha: 2, local_atual: 2, nome_personagem: "Valerius, o Sábio", pontos_vida: 20, pontos_mana: 40 },
  { _id: 3, id_jogador: 4, id_ficha: 3, local_atual: 1, nome_personagem: "Aoko Aozaki", pontos_vida: 120, pontos_mana: 400 },
  { _id: 4, id_jogador: 3, id_ficha: 4, local_atual: 4, nome_personagem: "Estelle Bright", pontos_vida: 160, pontos_mana: 80 },
  { _id: 5, id_jogador: 5, id_ficha: 7, local_atual: 9, nome_personagem: "Marisa Kirisame", pontos_vida: 95, pontos_mana: 350 },
  { _id: 6, id_jogador: 6, id_ficha: 8, local_atual: 1, nome_personagem: "Tohno Shiki", pontos_vida: 85, pontos_mana: 30 },
  { _id: 7, id_jogador: 1, id_ficha: 9, local_atual: 1, nome_personagem: "Obi-Wan Kenobi", pontos_vida: 155, pontos_mana: 180 },
  { _id: 8, id_jogador: 7, id_ficha: 10, local_atual: 10, nome_personagem: "Peter Griffin", pontos_vida: 220, pontos_mana: 10 },
  { _id: 9, id_jogador: null, id_ficha: 5, local_atual: 1, nome_personagem: "Legolas", pontos_vida: 140, pontos_mana: 60 },
  { _id: 10, id_jogador: null, id_ficha: 6, local_atual: 9, nome_personagem: "Aya Shameimaru", pontos_vida: 110, pontos_mana: 250 },
  { _id: 11, id_jogador: null, id_ficha: 11, local_atual: 10, nome_personagem: "Rato A", pontos_vida: 4, pontos_mana: 0 },
  { _id: 12, id_jogador: null, id_ficha: 11, local_atual: 10, nome_personagem: "Rato B", pontos_vida: 2, pontos_mana: 0 },
  { _id: 13, id_jogador: null, id_ficha: 11, local_atual: 10, nome_personagem: "Rato C", pontos_vida: 3, pontos_mana: 0 },
  { _id: 14, id_jogador: null, id_ficha: 11, local_atual: 7, nome_personagem: "Rato D", pontos_vida: 4, pontos_mana: 0 },
  { _id: 15, id_jogador: null, id_ficha: 12, local_atual: 10, nome_personagem: "Jovial Merryment", pontos_vida: 25, pontos_mana: 0 },
  { _id: 16, id_jogador: null, id_ficha: 13, local_atual: 7, nome_personagem: "Laslo, o Fora-da-lei", pontos_vida: 13, pontos_mana: 10 },
  { _id: 17, id_jogador: null, id_ficha: 13, local_atual: 7, nome_personagem: "Lázaro", pontos_vida: 28, pontos_mana: 10 },
  { _id: 18, id_jogador: null, id_ficha: 14, local_atual: 7, nome_personagem: "Dragonete", pontos_vida: 175, pontos_mana: 36 }
]);

db.Qualidades.insertMany([
  { _id: 1, nome_qualidade: "Dano de Fogo" },
  { _id: 2, nome_qualidade: "Dano Físico (Cortante)" },
  { _id: 3, nome_qualidade: "Dano Físico (Contundente)" },
  { _id: 4, nome_qualidade: "Dano Mágico" },
  { _id: 5, nome_qualidade: "Alcance" },
  { _id: 6, nome_qualidade: "Duração" },
  { _id: 7, nome_qualidade: "Durabilidade" },
  { _id: 8, nome_qualidade: "Dano Elétrico" },
  { _id: 9, nome_qualidade: "Área de Efeito" },
  { _id: 10, nome_qualidade: "Resistência Mágica" },
  { _id: 11, nome_qualidade: "Resistência Física" },
  { _id: 12, nome_qualidade: "Cortante" },
  { _id: 13, nome_qualidade: "Contundente" },
  { _id: 14, nome_qualidade: "Restauração de Mana" },
  { _id: 15, nome_qualidade: "Restauração de Vida" },
  { _id: 16, nome_qualidade: "Amaldiçoado" },
  { _id: 17, nome_qualidade: "Abençoado" },
  { _id: 18, nome_qualidade: "Teor Alcoólico" },
  { _id: 19, nome_qualidade: "Inquebrável" },
  { _id: 20, nome_qualidade: "Dano Sonoro" },
  { _id: 21, nome_qualidade: "Resistência contra fogo" },
  { _id: 22, nome_qualidade: "Pedido de Kaguya" }
]);

db.Itens.insertMany([
  { _id: 1, nome_item: "Espada de Aço", descricao: "Uma lâmina regular", peso: 3.5, valor_monetario: 1500 },
  { _id: 2, nome_item: "Poção de Mana", descricao: "Restaura pontos de mana", peso: 0.5, valor_monetario: 500 },
  { _id: 3, nome_item: "Galão de Mana", descricao: "Restaura os pontos de mana ostensivamente", peso: 5, valor_monetario: 2000 },
  { _id: 4, nome_item: "Poção de Vida", descricao: "Restaura pontos de vida", peso: 0.5, valor_monetario: 300 },
  { _id: 5, nome_item: "Poção de Restauração", descricao: "Restaura pontos de vida gradativamente", peso: 0.5, valor_monetario: 500 },
  { _id: 6, nome_item: "Latinha de Cerveja Brahma", descricao: "Cerveja barata", peso: 0.3, valor_monetario: 150 },
  { _id: 7, nome_item: "Livro de Matemática", descricao: "Livro contendo ensinamentos matemáticos", peso: 1, valor_monetario: 1000 },
  { _id: 8, nome_item: "Durandal", descricao: "Lendária espada do paladino Rolando", peso: 4, valor_monetario: 50000 },
  { _id: 9, nome_item: "Olifante", descricao: "Chifre utilizado pelo paladino Rolando", peso: 0.8, valor_monetario: 40000 },
  { _id: 10, nome_item: "Pridwen", descricao: "Escudo do lendário Rei Arthur", peso: 5, valor_monetario: 44000 },
  { _id: 11, nome_item: "Luva de Pedreiro", descricao: "Protege as mãos durante o trabalhado árduo exercido pelos pedreiros.", peso: 0.3, valor_monetario: 2000 },
  { _id: 12, nome_item: "Gorro do Saci", descricao: "Item fonte do poder do Saci Pererê, concedendo total obediência para quem tiver o gorro em sua posse.", peso: 0.5, valor_monetario: 0 },
  { _id: 13, nome_item: "Arco longo", descricao: "Utilizado pelos ingleses com grande efetividade.", peso: 3, valor_monetario: 2000 },
  { _id: 14, nome_item: "Flechas", descricao: "Munição para arcos de diversos tipos", peso: 0.01, valor_monetario: 100 },
  { _id: 15, nome_item: "Vestes de Huoshu", descricao: "Túnica feita das peles de Ratos-do-Fogo.", peso: 1, valor_monetario: 100000 },
  { _id: 16, nome_item: "Ascalon", descricao: "Espada utilizada por São Jorge", peso: 4, valor_monetario: 100000 },
  { _id: 17, nome_item: "Tigela de Pedra do Buda", descricao: "Tigela utilizada pelo próprio Buda.", peso: 0.5, valor_monetario: 200000 },
  { _id: 18, nome_item: "Ramo de Joias", descricao: "Galho enfeitado de pedras preciousas. Encontrado apenas no Monte Hourai", peso: 0.5, valor_monetario: 300000 },
  { _id: 19, nome_item: "Joia Colorida do Dragão", descricao: "Joia de cinco cores. Encontrada no pescoço de certos tipos de dragão.", peso: 0.3, valor_monetario: 300000 },
  { _id: 20, nome_item: "Búzio da Andorinha", descricao: "Búzio nascido de uma andorinha. Praticalmente impossível de ser encontrado", peso: 0.2, valor_monetario: 100000 }
]);

db.ItemCaracteristicas.insertMany([
  { id_qualidade: 2, id_item: 1, valor: 5 },
  { id_qualidade: 5, id_item: 1, valor: 2 },
  { id_qualidade: 7, id_item: 1, valor: 6 },
  { id_qualidade: 14, id_item: 2, valor: 4 },
  { id_qualidade: 14, id_item: 3, valor: 20 },
  { id_qualidade: 15, id_item: 4, valor: 4 },
  { id_qualidade: 15, id_item: 5, valor: 2 },
  { id_qualidade: 6, id_item: 5, valor: 20 },
  { id_qualidade: 18, id_item: 6, valor: 3 },
  { id_qualidade: 3, id_item: 7, valor: 1 },
  { id_qualidade: 5, id_item: 7, valor: 1 },
  { id_qualidade: 2, id_item: 8, valor: 16 },
  { id_qualidade: 5, id_item: 8, valor: 3 },
  { id_qualidade: 20, id_item: 9, valor: 30 },
  { id_qualidade: 10, id_item: 10, valor: 6 },
  { id_qualidade: 11, id_item: 10, valor: 10 },
  { id_qualidade: 11, id_item: 11, valor: 4 },
  { id_qualidade: 11, id_item: 15, valor: 4 },
  { id_qualidade: 21, id_item: 15, valor: 60 },
  { id_qualidade: 2, id_item: 16, valor: 20 },
  { id_qualidade: 5, id_item: 16, valor: 3 },
  { id_qualidade: 7, id_item: 16, valor: 50 },
  { id_qualidade: 22, id_item: 15, valor: 0 },
  { id_qualidade: 22, id_item: 17, valor: 0 },
  { id_qualidade: 22, id_item: 18, valor: 0 },
  { id_qualidade: 22, id_item: 19, valor: 0 },
  { id_qualidade: 22, id_item: 20, valor: 0 },
  { id_qualidade: 19, id_item: 8, valor: 0 },
  { id_qualidade: 17, id_item: 8, valor: 0 },
  { id_qualidade: 17, id_item: 10, valor: 0 },
  { id_qualidade: 17, id_item: 16, valor: 0 },
  { id_qualidade: 12, id_item: 14, valor: 0 }
]);

db.MagiaCaracteristicas.insertMany([
  { id_magia: 1, id_qualidade: 1, valor: 6 },
  { id_magia: 1, id_qualidade: 3, valor: 1 },
  { id_magia: 1, id_qualidade: 4, valor: 2 },
  { id_magia: 1, id_qualidade: 5, valor: 10 },
  { id_magia: 1, id_qualidade: 9, valor: 2 },
  { id_magia: 2, id_qualidade: 4, valor: 7 },
  { id_magia: 2, id_qualidade: 3, valor: 1 },
  { id_magia: 2, id_qualidade: 5, valor: 30 },
  { id_magia: 3, id_qualidade: 8, valor: 3 },
  { id_magia: 3, id_qualidade: 9, valor: 10 },
  { id_magia: 3, id_qualidade: 5, valor: 3 },
  { id_magia: 3, id_qualidade: 20, valor: 1 },
  { id_magia: 4, id_qualidade: 5, valor: 1 },
  { id_magia: 4, id_qualidade: 6, valor: 1 },
  { id_magia: 4, id_qualidade: 9, valor: 1 },
  { id_magia: 4, id_qualidade: 1, valor: 40 },
  { id_magia: 4, id_qualidade: 3, valor: 40 },
  { id_magia: 10, id_qualidade: 18, valor: -100 },
  { id_magia: 11, id_qualidade: 11, valor: 20 },
  { id_magia: 11, id_qualidade: 10, valor: 10 }
]);

db.MagiasConhecidas.insertMany([
  { id_ficha: 2, id_magia: 1 },
  { id_ficha: 6, id_magia: 1 },
  { id_ficha: 7, id_magia: 1 },
  { id_ficha: 14, id_magia: 1 },
  { id_ficha: 3, id_magia: 2 },
  { id_ficha: 6, id_magia: 2 },
  { id_ficha: 7, id_magia: 2 },
  { id_ficha: 6, id_magia: 7 }
]);

db.Inventario.insertMany([
  { id_personagem: 1, id_item: 1, quantidade: 1 },
  { id_personagem: 1, id_item: 6, quantidade: 12 },
  { id_personagem: 2, id_item: 2, quantidade: 2 },
  { id_personagem: 2, id_item: 4, quantidade: 2 },
  { id_personagem: 2, id_item: 7, quantidade: 1 },
  { id_personagem: 3, id_item: 3, quantidade: 1 },
  { id_personagem: 5, id_item: 16, quantidade: 1 },
  { id_personagem: 6, id_item: 6, quantidade: 1 },
  { id_personagem: 6, id_item: 18, quantidade: 1 },
  { id_personagem: 9, id_item: 13, quantidade: 1 },
  { id_personagem: 9, id_item: 14, quantidade: 50 },
  { id_personagem: 18, id_item: 19, quantidade: 1 }
]);

db.Combate.insertMany([
  { _id: 1, id_local: 1, data: new Date(), sumario: "Briga na taverna." },
  { _id: 2, id_local: 2, data: new Date(), sumario: "Duelo de danmaku." }
]);

db.Combatentes.insertMany([
  { id_combate: 1, id_personagem: 1 },
  { id_combate: 1, id_personagem: 2 },
  { id_combate: 2, id_personagem: 5 },
  { id_combate: 2, id_personagem: 10 }
]);

db.TipoAcaoCombate.insertMany([
  { _id: 1, nome_acao_combate: "Ataque" },
  { _id: 2, nome_acao_combate: "Magia" },
  { _id: 3, nome_acao_combate: "Evasão" },
  { _id: 4, nome_acao_combate: "Espera" }
]);

db.AcaoCombate.insertMany([
  { id_combate: 1, id_tipo_acao_combate: 1, id_agente: 1, id_alvo: 2, id_item_usado: 1, id_magia_usada: null, ordem_turno: 1, valor_resultado: 12 },
  { id_combate: 1, id_tipo_acao_combate: 2, id_agente: 2, id_alvo: 1, id_item_usado: null, id_magia_usada: 1, ordem_turno: 2, valor_resultado: 28 },
  { id_combate: 1, id_tipo_acao_combate: 1, id_agente: 1, id_alvo: 2, id_item_usado: 1, id_magia_usada: null, ordem_turno: 3, valor_resultado: 15 },
  { id_combate: 2, id_tipo_acao_combate: 3, id_agente: 10, id_alvo: 5, id_item_usado: null, id_magia_usada: null, ordem_turno: 1, valor_resultado: 0 },
  { id_combate: 2, id_tipo_acao_combate: 1, id_agente: 5, id_alvo: 10, id_item_usado: 16, id_magia_usada: null, ordem_turno: 2, valor_resultado: 45 },
  { id_combate: 2, id_tipo_acao_combate: 4, id_agente: 10, id_alvo: 5, id_item_usado: null, id_magia_usada: null, ordem_turno: 3, valor_resultado: 0 },
  { id_combate: 2, id_tipo_acao_combate: 1, id_agente: 5, id_alvo: 10, id_item_usado: 16, id_magia_usada: null, ordem_turno: 4, valor_resultado: 38 }
]);

db.Habilidades.insertMany([
  { _id: 1, nome_habilidade: "Atletismo", descr_habilidade: "Capacidade de realizar proezas físicas como escalar, saltar e nadar.", atributo_base: "Força" },
  { _id: 2, nome_habilidade: "Furtividade", descr_habilidade: "Habilidade de se mover silenciosamente e se esconder.", atributo_base: "Destreza" },
  { _id: 3, nome_habilidade: "Arcanismo", descr_habilidade: "Conhecimento sobre magia, rituais e planos de existência.", atributo_base: "Inteligência" },
  { _id: 4, nome_habilidade: "Percepção", descr_habilidade: "Capacidade de notar detalhes, sons e presenças ocultas.", atributo_base: "Sabedoria" },
  { _id: 5, nome_habilidade: "Medicina", descr_habilidade: "Conhecimento técnico para tratar ferimentos e doenças.", atributo_base: "Sabedoria" },
  { _id: 6, nome_habilidade: "Intimidação", descr_habilidade: "Uso de força ou ameaças para influenciar outros.", atributo_base: "Carisma" },
  { _id: 7, nome_habilidade: "Investigação", descr_habilidade: "Habilidade de encontrar pistas e deduzir informações.", atributo_base: "Inteligência" },
  { _id: 8, nome_habilidade: "Persuasão", descr_habilidade: "Capacidade de convencer outros através da diplomacia.", atributo_base: "Carisma" }
]);

db.FichaHabilidades.insertMany([
  { id_ficha: 1, id_habilidade: 1 },
  { id_ficha: 1, id_habilidade: 6 },
  { id_ficha: 2, id_habilidade: 3 },
  { id_ficha: 2, id_habilidade: 4 },
  { id_ficha: 3, id_habilidade: 3 },
  { id_ficha: 3, id_habilidade: 7 },
  { id_ficha: 4, id_habilidade: 1 },
  { id_ficha: 4, id_habilidade: 8 },
  { id_ficha: 6, id_habilidade: 7 },
  { id_ficha: 6, id_habilidade: 4 },
  { id_ficha: 9, id_habilidade: 8 },
  { id_ficha: 9, id_habilidade: 4 },
  { id_ficha: 9, id_habilidade: 3 },
  { id_ficha: 10, id_habilidade: 1 },
  { id_ficha: 10, id_habilidade: 6 }
]);