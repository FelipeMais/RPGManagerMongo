-- 1. Core Lookup Tables (Independent)
CREATE TABLE Classe (
    id_classe SERIAL PRIMARY KEY,
    nome_classe VARCHAR(100) NOT NULL,
    descricao TEXT
);

CREATE TABLE Especie (
    id_especie SERIAL PRIMARY KEY,
    nome_especie VARCHAR(100) NOT NULL
);

CREATE TABLE Qualidades (
    id_qualidade SERIAL PRIMARY KEY,
    nome_qualidade VARCHAR(100) NOT NULL
);

CREATE TABLE EscolaMagia (
    id_escola_magia SERIAL PRIMARY KEY,
    nome_escola_magia VARCHAR(100) NOT NULL,
    descricao TEXT
);

CREATE TABLE TipoLocal (
    id_tipo_local SERIAL PRIMARY KEY,
    nome_tipo_local VARCHAR(100) NOT NULL,
    descricao TEXT
);

CREATE TABLE TipoAcaoCombate (
    id_tipo_acao_combate SERIAL PRIMARY KEY,
    nome_acao_combate VARCHAR(100) NOT NULL
);

-- 2. Primary Entities
CREATE TABLE Jogador (
    id_jogador SERIAL PRIMARY KEY,
    nome_jogador VARCHAR(255) NOT NULL,
    data_entrada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE Ficha (
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

CREATE TABLE Magias (
    id_magia SERIAL PRIMARY KEY,
    nome_magia VARCHAR(255) NOT NULL,
    descricao TEXT,
    custo_mana INT,
    nivel_minimo INT,
    dados VARCHAR(50) -- e.g., '2d6+4'
);

CREATE TABLE Itens (
    id_item SERIAL PRIMARY KEY,
    nome_item VARCHAR(255) NOT NULL,
    descricao TEXT,
    peso DECIMAL(10,2),
    valor_monetario INT
);

-- 3. Specialized/Inherited Entities
CREATE TABLE Local (
    id_local SERIAL PRIMARY KEY,
    local_pai INT REFERENCES Local(id_local), -- Self-reference for sub-regions
    id_tipo_local INT REFERENCES TipoLocal(id_tipo_local),
    nome_local VARCHAR(255) NOT NULL,
    descricao TEXT
);

-- 4. Associative Tables (Many-to-Many)
CREATE TABLE MagiaFicha (
    id_magia INT REFERENCES Magias(id_magia),
    id_ficha INT REFERENCES Ficha(id_ficha),
    PRIMARY KEY (id_magia, id_ficha)
);

CREATE TABLE EscolasMagiaFicha (
    id_ficha INT REFERENCES Ficha(id_ficha),
    id_escola_magia INT REFERENCES EscolaMagia(id_escola_magia),
    PRIMARY KEY (id_ficha, id_escola_magia)
);

-- 5. The Combat System
CREATE TABLE Combate (
    id_combate SERIAL PRIMARY KEY,
    id_local INT REFERENCES Local(id_local),
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sumario TEXT
);

CREATE TABLE AcaoCombate (
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