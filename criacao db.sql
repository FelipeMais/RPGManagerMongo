CREATE TABLE IF NOT EXISTS Magias (
    id_magia SERIAL PRIMARY KEY,
    nome_magia VARCHAR(255) NOT NULL,
    descricao TEXT,
    custo_mana INT,
    nivel_minimo INT,
    dados VARCHAR(50) -- e.g., '2d6+4'
);

CREATE TABLE IF NOT EXISTS EscolaMagia (
    id_escola_magia SERIAL PRIMARY KEY,
    nome_escola_magia VARCHAR(100) NOT NULL,
    descricao TEXT
);

CREATE TABLE IF NOT EXISTS Qualidades (
    id_qualidade SERIAL PRIMARY KEY,
    nome_qualidade VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Itens (
    id_item SERIAL PRIMARY KEY,
    nome_item VARCHAR(255) NOT NULL,
    descricao TEXT,
    peso DECIMAL(10,2),
    valor_monetario INT
);

CREATE TABLE IF NOT EXISTS TipoLocal (
    id_tipo_local SERIAL PRIMARY KEY,
    nome_tipo_local VARCHAR(100) NOT NULL,
    descricao TEXT
);

CREATE TABLE IF NOT EXISTS Local (
    id_local SERIAL PRIMARY KEY,
    local_pai INT REFERENCES Local(id_local),
    id_tipo_local INT REFERENCES TipoLocal(id_tipo_local),
    nome_local VARCHAR(255) NOT NULL,
    descricao TEXT
);

CREATE TABLE IF NOT EXISTS Combate (
    id_combate SERIAL PRIMARY KEY,
    id_local INT REFERENCES Local(id_local),
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sumario TEXT
);

CREATE TABLE IF NOT EXISTS TipoAcaoCombate (
    id_tipo_acao_combate SERIAL PRIMARY KEY,
    nome_acao_combate VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS AcaoCombate (
    id_action SERIAL PRIMARY KEY,
    id_combate INT REFERENCES Combate(id_combate),
    id_tipo_acao_combate INT REFERENCES TipoAcaoCombate(id_tipo_acao_combate),
    id_ator INT REFERENCES Ficha(id_ficha),
    id_alvo INT REFERENCES Ficha(id_ficha),
    id_item_usado INT REFERENCES Itens(id_item),
    id_magia_usada INT REFERENCES Magias(id_magia),
    ordem_turno INT,
    valor_resultado INT -- Damage, Healing, or Roll result
);

CREATE TABLE IF NOT EXISTS Vantagem (
    id_vantagem SERIAL PRIMARY KEY,
    nome_vantagem VARCHAR(100) NOT NULL,
    descr_vantagem TEXT
);

CREATE TABLE IF NOT EXISTS Habilidades (
    id_habilidade SERIAL PRIMARY KEY,
    nome_habilidade VARCHAR(100) NOT NULL,
    descr_habilidade TEXT,
    atributo_base VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Especie (
    id_especie SERIAL PRIMARY KEY,
    nome_especie VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Classe (
    id_classe SERIAL PRIMARY KEY,
    nome_classe VARCHAR(100) NOT NULL,
    descricao TEXT
);

CREATE TABLE IF NOT EXISTS Ficha (
    id_ficha SERIAL PRIMARY KEY,
    id_classe INT REFERENCES Classe(id_classe),
    id_especie INT REFERENCES Especie(id_especie),
    pontos_vida_max INT NOT NULL,
    pontos_mana_max INT NOT NULL,
    forca INT DEFAULT 10,
    destreza INT DEFAULT 10,
    constituicao INT DEFAULT 10,
    inteligencia INT DEFAULT 10,
    sabedoria INT DEFAULT 10,
    carisma INT DEFAULT 10,
    nivel INT DEFAULT 1
);

CREATE TABLE IF NOT EXISTS Personagem (
    id_personagem SERIAL PRIMARY KEY,
    id_ficha INT REFERENCES Ficha(id_ficha),
    local_atual INT REFERENCES Local(id_local),
    nome_personagem VARCHAR(255) NOT NULL,
    pontos_vida INT NOT NULL,
    pontos_mana INT NOT NULL,
    historia TEXT
);


CREATE TABLE IF NOT EXISTS Jogador (
    id_jogador SERIAL PRIMARY KEY,
    nome_jogador VARCHAR(255) NOT NULL,
    data_entrada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS TipoControle (
    id_tipo_controle SERIAL PRIMARY KEY,
    nome_tipo_controle VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS MagiaEscola(
    id_escola_magia INT REFERENCES EscolaMagia(id_escola_magia),
    id_magia INT REFERENCES Magias(id_magia),
    PRIMARY KEY (id_escola_magia, id_magia)
);

CREATE TABLE IF NOT EXISTS MagiaCaracteristicas(
    id_qualidade INT REFERENCES Qualidades(id_qualidade),
    id_magia INT REFERENCES Magias(id_magia),
    valor INT NOT NULL,
    PRIMARY KEY (id_qualidade, id_magia)
);

CREATE TABLE IF NOT EXISTS ItemCaracteristicas(
    id_qualidade INT REFERENCES Qualidades(id_qualidade),
    id_item INT REFERENCES Itens(id_item),
    valor INT NOT NULL,
    PRIMARY KEY (id_qualidade, id_item)
);

CREATE TABLE IF NOT EXISTS Inventario(
    id_item INT REFERENCES Itens(id_item),
    id_personagem INT REFERENCES Personagem(id_personagem),
    quantidade INT NOT NULL,
    PRIMARY KEY (id_item, id_personagem)
);

CREATE TABLE IF NOT EXISTS MagiasConhecidas (
    id_magia INT REFERENCES Magias(id_magia),
    id_ficha INT REFERENCES Ficha(id_ficha),
    PRIMARY KEY (id_magia, id_ficha)
);

CREATE TABLE IF NOT EXISTS FichaEscolaMagia (
    id_ficha INT REFERENCES Ficha(id_ficha),
    id_escola_magia INT REFERENCES EscolaMagia(id_escola_magia),
    PRIMARY KEY (id_ficha, id_escola_magia)
);

CREATE TABLE IF NOT EXISTS FichaVantagens (
    id_vantagem INT REFERENCES Vantagem(id_vantagem),
    id_ficha INT REFERENCES Ficha(id_ficha),
    PRIMARY KEY (id_ficha, id_vantagem)
);

CREATE TABLE IF NOT EXISTS FichaHabilidades (
    id_habilidade INT REFERENCES Habilidades(id_habilidade),
    id_ficha INT REFERENCES Ficha(id_ficha),
    PRIMARY KEY (id_ficha, id_habilidade)
);

CREATE TABLE IF NOT EXISTS Combatentes (
    id_combate INT REFERENCES Combate(id_combate),
    id_personagem INT REFERENCES Personagem(id_personagem),
    PRIMARY KEY (id_combate, id_personagem)
);

CREATE TABLE IF NOT EXISTS ControladorPersonagem(
    id_personagem INT REFERENCES Personagem(id_personagem),
    id_jogador INT REFERENCES Jogador(id_jogador),
    id_tipo_controle INT REFERENCES TipoControle(id_tipo_controle),
    data_posse TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_personagem, id_jogador, id_tipo_controle)
);