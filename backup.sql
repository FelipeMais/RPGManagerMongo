--
-- PostgreSQL database dump
--

\restrict pwVqI2vYN5vkeiVQmxLJxQ7EbjFEQQeQN62gLpABeFC7RTcGjCFGVf60HjsRVxv

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

-- Started on 2026-04-18 21:47:53

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- TOC entry 5223 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 246 (class 1259 OID 18256)
-- Name: acaocombate; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.acaocombate (
    id_action integer NOT NULL,
    id_combate integer NOT NULL,
    id_tipo_acao_combate integer NOT NULL,
    id_agente integer NOT NULL,
    id_alvo integer,
    id_item_usado integer,
    id_magia_usada integer,
    ordem_turno integer NOT NULL,
    valor_resultado integer NOT NULL
);


--
-- TOC entry 245 (class 1259 OID 18255)
-- Name: acaocombate_id_action_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.acaocombate_id_action_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5224 (class 0 OID 0)
-- Dependencies: 245
-- Name: acaocombate_id_action_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.acaocombate_id_action_seq OWNED BY public.acaocombate.id_action;


--
-- TOC entry 234 (class 1259 OID 18153)
-- Name: classe; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.classe (
    id_classe integer NOT NULL,
    nome_classe character varying(100) NOT NULL,
    descricao text
);


--
-- TOC entry 233 (class 1259 OID 18152)
-- Name: classe_id_classe_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.classe_id_classe_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5225 (class 0 OID 0)
-- Dependencies: 233
-- Name: classe_id_classe_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.classe_id_classe_seq OWNED BY public.classe.id_classe;


--
-- TOC entry 242 (class 1259 OID 18230)
-- Name: combate; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.combate (
    id_combate integer NOT NULL,
    id_local integer NOT NULL,
    data timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    sumario text
);


--
-- TOC entry 241 (class 1259 OID 18229)
-- Name: combate_id_combate_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.combate_id_combate_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5226 (class 0 OID 0)
-- Dependencies: 241
-- Name: combate_id_combate_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.combate_id_combate_seq OWNED BY public.combate.id_combate;


--
-- TOC entry 252 (class 1259 OID 18386)
-- Name: combatentes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.combatentes (
    id_combate integer NOT NULL,
    id_personagem integer NOT NULL
);


--
-- TOC entry 232 (class 1259 OID 18144)
-- Name: especie; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.especie (
    id_especie integer NOT NULL,
    nome_especie character varying(100) NOT NULL
);


--
-- TOC entry 231 (class 1259 OID 18143)
-- Name: especie_id_especie_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.especie_id_especie_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5227 (class 0 OID 0)
-- Dependencies: 231
-- Name: especie_id_especie_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.especie_id_especie_seq OWNED BY public.especie.id_especie;


--
-- TOC entry 236 (class 1259 OID 18164)
-- Name: ficha; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ficha (
    id_ficha integer NOT NULL,
    id_classe integer,
    id_especie integer,
    pontos_vida_max integer NOT NULL,
    pontos_mana_max integer NOT NULL,
    forca integer DEFAULT 10,
    destreza integer DEFAULT 10,
    constituicao integer DEFAULT 10,
    inteligencia integer DEFAULT 10,
    sabedoria integer DEFAULT 10,
    carisma integer DEFAULT 10,
    nivel integer DEFAULT 1
);


--
-- TOC entry 235 (class 1259 OID 18163)
-- Name: ficha_id_ficha_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ficha_id_ficha_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5228 (class 0 OID 0)
-- Dependencies: 235
-- Name: ficha_id_ficha_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ficha_id_ficha_seq OWNED BY public.ficha.id_ficha;


--
-- TOC entry 251 (class 1259 OID 18369)
-- Name: fichahabilidades; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.fichahabilidades (
    id_habilidade integer NOT NULL,
    id_ficha integer NOT NULL
);


--
-- TOC entry 230 (class 1259 OID 18132)
-- Name: habilidades; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.habilidades (
    id_habilidade integer NOT NULL,
    nome_habilidade character varying(100) NOT NULL,
    descr_habilidade text,
    atributo_base character varying(50) NOT NULL
);


--
-- TOC entry 229 (class 1259 OID 18131)
-- Name: habilidades_id_habilidade_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.habilidades_id_habilidade_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5229 (class 0 OID 0)
-- Dependencies: 229
-- Name: habilidades_id_habilidade_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.habilidades_id_habilidade_seq OWNED BY public.habilidades.id_habilidade;


--
-- TOC entry 249 (class 1259 OID 18334)
-- Name: inventario; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.inventario (
    id_item integer NOT NULL,
    id_personagem integer NOT NULL,
    quantidade integer NOT NULL
);


--
-- TOC entry 248 (class 1259 OID 18316)
-- Name: itemcaracteristicas; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.itemcaracteristicas (
    id_qualidade integer NOT NULL,
    id_item integer NOT NULL,
    valor integer DEFAULT 0
);


--
-- TOC entry 224 (class 1259 OID 18089)
-- Name: itens; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.itens (
    id_item integer NOT NULL,
    nome_item character varying(255) NOT NULL,
    descricao text,
    peso numeric(10,2),
    valor_monetario integer
);


--
-- TOC entry 223 (class 1259 OID 18088)
-- Name: itens_id_item_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.itens_id_item_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5230 (class 0 OID 0)
-- Dependencies: 223
-- Name: itens_id_item_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.itens_id_item_seq OWNED BY public.itens.id_item;


--
-- TOC entry 238 (class 1259 OID 18191)
-- Name: jogador; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.jogador (
    id_jogador integer NOT NULL,
    nome_jogador character varying(255) NOT NULL,
    data_entrada timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    ativo boolean DEFAULT true
);


--
-- TOC entry 237 (class 1259 OID 18190)
-- Name: jogador_id_jogador_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.jogador_id_jogador_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5231 (class 0 OID 0)
-- Dependencies: 237
-- Name: jogador_id_jogador_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.jogador_id_jogador_seq OWNED BY public.jogador.id_jogador;


--
-- TOC entry 228 (class 1259 OID 18111)
-- Name: local; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.local (
    id_local integer NOT NULL,
    local_pai integer,
    id_tipo_local integer,
    nome_local character varying(255) NOT NULL,
    descricao text
);


--
-- TOC entry 227 (class 1259 OID 18110)
-- Name: local_id_local_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.local_id_local_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5232 (class 0 OID 0)
-- Dependencies: 227
-- Name: local_id_local_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.local_id_local_seq OWNED BY public.local.id_local;


--
-- TOC entry 247 (class 1259 OID 18298)
-- Name: magiacaracteristicas; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.magiacaracteristicas (
    id_qualidade integer NOT NULL,
    id_magia integer NOT NULL,
    valor integer DEFAULT 0
);


--
-- TOC entry 220 (class 1259 OID 18068)
-- Name: magias; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.magias (
    id_magia integer NOT NULL,
    nome_magia character varying(255) NOT NULL,
    descricao text,
    custo_mana integer,
    nivel_minimo integer NOT NULL,
    dados character varying(50)
);


--
-- TOC entry 219 (class 1259 OID 18067)
-- Name: magias_id_magia_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.magias_id_magia_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5233 (class 0 OID 0)
-- Dependencies: 219
-- Name: magias_id_magia_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.magias_id_magia_seq OWNED BY public.magias.id_magia;


--
-- TOC entry 250 (class 1259 OID 18352)
-- Name: magiasconhecidas; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.magiasconhecidas (
    id_magia integer NOT NULL,
    id_ficha integer NOT NULL
);


--
-- TOC entry 240 (class 1259 OID 18202)
-- Name: personagem; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.personagem (
    id_personagem integer NOT NULL,
    id_jogador integer,
    id_ficha integer,
    local_atual integer,
    nome_personagem character varying(255) NOT NULL,
    pontos_vida integer NOT NULL,
    pontos_mana integer NOT NULL,
    historia text
);


--
-- TOC entry 239 (class 1259 OID 18201)
-- Name: personagem_id_personagem_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.personagem_id_personagem_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5234 (class 0 OID 0)
-- Dependencies: 239
-- Name: personagem_id_personagem_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.personagem_id_personagem_seq OWNED BY public.personagem.id_personagem;


--
-- TOC entry 222 (class 1259 OID 18080)
-- Name: qualidades; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.qualidades (
    id_qualidade integer NOT NULL,
    nome_qualidade character varying(100) NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 18079)
-- Name: qualidades_id_qualidade_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.qualidades_id_qualidade_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5235 (class 0 OID 0)
-- Dependencies: 221
-- Name: qualidades_id_qualidade_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.qualidades_id_qualidade_seq OWNED BY public.qualidades.id_qualidade;


--
-- TOC entry 244 (class 1259 OID 18247)
-- Name: tipoacaocombate; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tipoacaocombate (
    id_tipo_acao_combate integer NOT NULL,
    nome_acao_combate character varying(100) NOT NULL
);


--
-- TOC entry 243 (class 1259 OID 18246)
-- Name: tipoacaocombate_id_tipo_acao_combate_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tipoacaocombate_id_tipo_acao_combate_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5236 (class 0 OID 0)
-- Dependencies: 243
-- Name: tipoacaocombate_id_tipo_acao_combate_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.tipoacaocombate_id_tipo_acao_combate_seq OWNED BY public.tipoacaocombate.id_tipo_acao_combate;


--
-- TOC entry 226 (class 1259 OID 18100)
-- Name: tipolocal; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tipolocal (
    id_tipo_local integer NOT NULL,
    nome_tipo_local character varying(100) NOT NULL,
    descricao text
);


--
-- TOC entry 225 (class 1259 OID 18099)
-- Name: tipolocal_id_tipo_local_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tipolocal_id_tipo_local_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5237 (class 0 OID 0)
-- Dependencies: 225
-- Name: tipolocal_id_tipo_local_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.tipolocal_id_tipo_local_seq OWNED BY public.tipolocal.id_tipo_local;


--
-- TOC entry 4968 (class 2604 OID 18259)
-- Name: acaocombate id_action; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.acaocombate ALTER COLUMN id_action SET DEFAULT nextval('public.acaocombate_id_action_seq'::regclass);


--
-- TOC entry 4952 (class 2604 OID 18156)
-- Name: classe id_classe; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.classe ALTER COLUMN id_classe SET DEFAULT nextval('public.classe_id_classe_seq'::regclass);


--
-- TOC entry 4965 (class 2604 OID 18233)
-- Name: combate id_combate; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.combate ALTER COLUMN id_combate SET DEFAULT nextval('public.combate_id_combate_seq'::regclass);


--
-- TOC entry 4951 (class 2604 OID 18147)
-- Name: especie id_especie; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.especie ALTER COLUMN id_especie SET DEFAULT nextval('public.especie_id_especie_seq'::regclass);


--
-- TOC entry 4953 (class 2604 OID 18167)
-- Name: ficha id_ficha; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ficha ALTER COLUMN id_ficha SET DEFAULT nextval('public.ficha_id_ficha_seq'::regclass);


--
-- TOC entry 4950 (class 2604 OID 18135)
-- Name: habilidades id_habilidade; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.habilidades ALTER COLUMN id_habilidade SET DEFAULT nextval('public.habilidades_id_habilidade_seq'::regclass);


--
-- TOC entry 4947 (class 2604 OID 18092)
-- Name: itens id_item; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.itens ALTER COLUMN id_item SET DEFAULT nextval('public.itens_id_item_seq'::regclass);


--
-- TOC entry 4961 (class 2604 OID 18194)
-- Name: jogador id_jogador; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.jogador ALTER COLUMN id_jogador SET DEFAULT nextval('public.jogador_id_jogador_seq'::regclass);


--
-- TOC entry 4949 (class 2604 OID 18114)
-- Name: local id_local; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.local ALTER COLUMN id_local SET DEFAULT nextval('public.local_id_local_seq'::regclass);


--
-- TOC entry 4945 (class 2604 OID 18071)
-- Name: magias id_magia; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.magias ALTER COLUMN id_magia SET DEFAULT nextval('public.magias_id_magia_seq'::regclass);


--
-- TOC entry 4964 (class 2604 OID 18205)
-- Name: personagem id_personagem; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.personagem ALTER COLUMN id_personagem SET DEFAULT nextval('public.personagem_id_personagem_seq'::regclass);


--
-- TOC entry 4946 (class 2604 OID 18083)
-- Name: qualidades id_qualidade; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.qualidades ALTER COLUMN id_qualidade SET DEFAULT nextval('public.qualidades_id_qualidade_seq'::regclass);


--
-- TOC entry 4967 (class 2604 OID 18250)
-- Name: tipoacaocombate id_tipo_acao_combate; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tipoacaocombate ALTER COLUMN id_tipo_acao_combate SET DEFAULT nextval('public.tipoacaocombate_id_tipo_acao_combate_seq'::regclass);


--
-- TOC entry 4948 (class 2604 OID 18103)
-- Name: tipolocal id_tipo_local; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tipolocal ALTER COLUMN id_tipo_local SET DEFAULT nextval('public.tipolocal_id_tipo_local_seq'::regclass);


--
-- TOC entry 5211 (class 0 OID 18256)
-- Dependencies: 246
-- Data for Name: acaocombate; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.acaocombate VALUES (1, 1, 1, 1, 2, 1, NULL, 1, 12);
INSERT INTO public.acaocombate VALUES (2, 1, 2, 2, 1, NULL, 1, 2, 28);
INSERT INTO public.acaocombate VALUES (3, 1, 1, 1, 2, 1, NULL, 3, 15);
INSERT INTO public.acaocombate VALUES (4, 2, 3, 10, 5, NULL, NULL, 1, 0);
INSERT INTO public.acaocombate VALUES (5, 2, 1, 5, 10, 16, NULL, 2, 45);
INSERT INTO public.acaocombate VALUES (6, 2, 4, 10, 5, NULL, NULL, 3, 0);
INSERT INTO public.acaocombate VALUES (7, 2, 1, 5, 10, 16, NULL, 4, 38);


--
-- TOC entry 5199 (class 0 OID 18153)
-- Dependencies: 234
-- Data for Name: classe; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.classe VALUES (1, 'Guerreiro', 'Especialista em combate corporal');
INSERT INTO public.classe VALUES (2, 'Mago', 'Especialista em conjuração de magias');
INSERT INTO public.classe VALUES (3, 'Arqueiro', 'Especialista em arcos e bestas');
INSERT INTO public.classe VALUES (4, 'Paladino', 'Cavaleiro defensor de causas nobres');
INSERT INTO public.classe VALUES (5, 'Pistoleiro', 'Proficiente no uso de armas de fogo');
INSERT INTO public.classe VALUES (6, 'Sarcerdote', 'Especializado em bençãos e exorcismos');
INSERT INTO public.classe VALUES (7, 'Alquimista', 'Fabricante de poções e misturas');
INSERT INTO public.classe VALUES (8, 'Atirador Mágico', 'Especialista em projéteis mágicos');
INSERT INTO public.classe VALUES (9, 'Reporter', 'Especialista em buscar informação');
INSERT INTO public.classe VALUES (10, 'Pugilista', 'Especialista em combate desarmado');
INSERT INTO public.classe VALUES (11, 'Jedi', 'Monge guerreiro sensível à Força');
INSERT INTO public.classe VALUES (12, 'Assassino', 'Especialista em sutileza e agilidade');
INSERT INTO public.classe VALUES (13, 'Predador Aéreo', 'Criatura hostil e alada');
INSERT INTO public.classe VALUES (14, 'Brutamonte', 'Indivíduo dotado de extrema força bruta');
INSERT INTO public.classe VALUES (15, 'Criatura Pequena', 'Animal de pequeno porte');
INSERT INTO public.classe VALUES (16, 'Montaria', 'Criatura que pode ser montada por outros personagens');


--
-- TOC entry 5207 (class 0 OID 18230)
-- Dependencies: 242
-- Data for Name: combate; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.combate VALUES (1, 1, '2026-04-18 21:37:10.190116', 'Briga na taverna.');
INSERT INTO public.combate VALUES (2, 2, '2026-04-18 21:37:10.190116', 'Duelo de danmaku.');


--
-- TOC entry 5217 (class 0 OID 18386)
-- Dependencies: 252
-- Data for Name: combatentes; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.combatentes VALUES (1, 1);
INSERT INTO public.combatentes VALUES (1, 2);
INSERT INTO public.combatentes VALUES (2, 5);
INSERT INTO public.combatentes VALUES (2, 10);


--
-- TOC entry 5197 (class 0 OID 18144)
-- Dependencies: 232
-- Data for Name: especie; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.especie VALUES (1, 'Humano');
INSERT INTO public.especie VALUES (2, 'Elfo');
INSERT INTO public.especie VALUES (3, 'Ogro');
INSERT INTO public.especie VALUES (4, 'Besta');
INSERT INTO public.especie VALUES (5, 'Anão');
INSERT INTO public.especie VALUES (6, 'Tengu');
INSERT INTO public.especie VALUES (7, 'Cavalo');
INSERT INTO public.especie VALUES (8, 'Dracônico');


--
-- TOC entry 5201 (class 0 OID 18164)
-- Dependencies: 236
-- Data for Name: ficha; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ficha VALUES (1, 1, 3, 50, 0, 18, 10, 10, 8, 10, 10, 1);
INSERT INTO public.ficha VALUES (2, 2, 2, 20, 40, 8, 10, 10, 18, 10, 10, 1);
INSERT INTO public.ficha VALUES (3, 8, 1, 120, 400, 12, 10, 14, 20, 10, 10, 18);
INSERT INTO public.ficha VALUES (4, 1, 1, 160, 80, 16, 10, 15, 12, 10, 10, 12);
INSERT INTO public.ficha VALUES (5, 2, 2, 140, 60, 14, 10, 14, 13, 10, 10, 15);
INSERT INTO public.ficha VALUES (6, 9, 6, 110, 250, 13, 10, 12, 16, 10, 10, 14);
INSERT INTO public.ficha VALUES (7, 7, 1, 95, 350, 10, 10, 11, 19, 10, 10, 14);
INSERT INTO public.ficha VALUES (8, 12, 1, 85, 30, 13, 10, 12, 11, 10, 10, 8);
INSERT INTO public.ficha VALUES (9, 11, 1, 155, 180, 14, 10, 15, 17, 10, 10, 17);
INSERT INTO public.ficha VALUES (10, 10, 1, 220, 10, 17, 10, 20, 6, 10, 10, 5);
INSERT INTO public.ficha VALUES (11, 15, 4, 4, 0, 3, 10, 8, 2, 10, 10, 1);
INSERT INTO public.ficha VALUES (12, 16, 7, 25, 0, 18, 10, 15, 4, 10, 10, 2);
INSERT INTO public.ficha VALUES (13, 14, 1, 28, 10, 14, 10, 13, 10, 10, 10, 3);
INSERT INTO public.ficha VALUES (14, 13, 8, 180, 40, 22, 10, 19, 6, 10, 10, 10);


--
-- TOC entry 5216 (class 0 OID 18369)
-- Dependencies: 251
-- Data for Name: fichahabilidades; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.fichahabilidades VALUES (1, 1);
INSERT INTO public.fichahabilidades VALUES (6, 1);
INSERT INTO public.fichahabilidades VALUES (3, 2);
INSERT INTO public.fichahabilidades VALUES (4, 2);
INSERT INTO public.fichahabilidades VALUES (3, 3);
INSERT INTO public.fichahabilidades VALUES (7, 3);
INSERT INTO public.fichahabilidades VALUES (1, 4);
INSERT INTO public.fichahabilidades VALUES (8, 4);
INSERT INTO public.fichahabilidades VALUES (7, 6);
INSERT INTO public.fichahabilidades VALUES (4, 6);
INSERT INTO public.fichahabilidades VALUES (8, 9);
INSERT INTO public.fichahabilidades VALUES (4, 9);
INSERT INTO public.fichahabilidades VALUES (3, 9);
INSERT INTO public.fichahabilidades VALUES (1, 10);
INSERT INTO public.fichahabilidades VALUES (6, 10);


--
-- TOC entry 5195 (class 0 OID 18132)
-- Dependencies: 230
-- Data for Name: habilidades; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.habilidades VALUES (1, 'Atletismo', 'Capacidade de realizar proezas físicas como escalar, saltar e nadar.', 'Força');
INSERT INTO public.habilidades VALUES (2, 'Furtividade', 'Habilidade de se mover silenciosamente e se esconder.', 'Destreza');
INSERT INTO public.habilidades VALUES (3, 'Arcanismo', 'Conhecimento sobre magia, rituais e planos de existência.', 'Inteligência');
INSERT INTO public.habilidades VALUES (4, 'Percepção', 'Capacidade de notar detalhes, sons e presenças ocultas.', 'Sabedoria');
INSERT INTO public.habilidades VALUES (5, 'Medicina', 'Conhecimento técnico para tratar ferimentos e doenças.', 'Sabedoria');
INSERT INTO public.habilidades VALUES (6, 'Intimidação', 'Uso de força ou ameaças para influenciar outros.', 'Carisma');
INSERT INTO public.habilidades VALUES (7, 'Investigação', 'Habilidade de encontrar pistas e deduzir informações.', 'Inteligência');
INSERT INTO public.habilidades VALUES (8, 'Persuasão', 'Capacidade de convencer outros através da diplomacia.', 'Carisma');


--
-- TOC entry 5214 (class 0 OID 18334)
-- Dependencies: 249
-- Data for Name: inventario; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.inventario VALUES (1, 1, 1);
INSERT INTO public.inventario VALUES (6, 1, 12);
INSERT INTO public.inventario VALUES (2, 2, 2);
INSERT INTO public.inventario VALUES (4, 2, 2);
INSERT INTO public.inventario VALUES (7, 2, 1);
INSERT INTO public.inventario VALUES (3, 3, 1);
INSERT INTO public.inventario VALUES (16, 5, 1);
INSERT INTO public.inventario VALUES (6, 6, 1);
INSERT INTO public.inventario VALUES (18, 6, 1);
INSERT INTO public.inventario VALUES (13, 9, 1);
INSERT INTO public.inventario VALUES (14, 9, 50);
INSERT INTO public.inventario VALUES (19, 18, 1);


--
-- TOC entry 5213 (class 0 OID 18316)
-- Dependencies: 248
-- Data for Name: itemcaracteristicas; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.itemcaracteristicas VALUES (2, 1, 5);
INSERT INTO public.itemcaracteristicas VALUES (5, 1, 2);
INSERT INTO public.itemcaracteristicas VALUES (7, 1, 6);
INSERT INTO public.itemcaracteristicas VALUES (14, 2, 4);
INSERT INTO public.itemcaracteristicas VALUES (14, 3, 20);
INSERT INTO public.itemcaracteristicas VALUES (15, 4, 4);
INSERT INTO public.itemcaracteristicas VALUES (15, 5, 2);
INSERT INTO public.itemcaracteristicas VALUES (6, 5, 20);
INSERT INTO public.itemcaracteristicas VALUES (18, 6, 3);
INSERT INTO public.itemcaracteristicas VALUES (3, 7, 1);
INSERT INTO public.itemcaracteristicas VALUES (5, 7, 1);
INSERT INTO public.itemcaracteristicas VALUES (2, 8, 16);
INSERT INTO public.itemcaracteristicas VALUES (5, 8, 3);
INSERT INTO public.itemcaracteristicas VALUES (20, 9, 30);
INSERT INTO public.itemcaracteristicas VALUES (10, 10, 6);
INSERT INTO public.itemcaracteristicas VALUES (11, 10, 10);
INSERT INTO public.itemcaracteristicas VALUES (11, 11, 4);
INSERT INTO public.itemcaracteristicas VALUES (11, 15, 4);
INSERT INTO public.itemcaracteristicas VALUES (21, 15, 60);
INSERT INTO public.itemcaracteristicas VALUES (2, 16, 20);
INSERT INTO public.itemcaracteristicas VALUES (5, 16, 3);
INSERT INTO public.itemcaracteristicas VALUES (7, 16, 50);
INSERT INTO public.itemcaracteristicas VALUES (22, 15, 0);
INSERT INTO public.itemcaracteristicas VALUES (22, 17, 0);
INSERT INTO public.itemcaracteristicas VALUES (22, 18, 0);
INSERT INTO public.itemcaracteristicas VALUES (22, 19, 0);
INSERT INTO public.itemcaracteristicas VALUES (22, 20, 0);
INSERT INTO public.itemcaracteristicas VALUES (19, 8, 0);
INSERT INTO public.itemcaracteristicas VALUES (17, 8, 0);
INSERT INTO public.itemcaracteristicas VALUES (17, 10, 0);
INSERT INTO public.itemcaracteristicas VALUES (17, 16, 0);
INSERT INTO public.itemcaracteristicas VALUES (12, 14, 0);


--
-- TOC entry 5189 (class 0 OID 18089)
-- Dependencies: 224
-- Data for Name: itens; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.itens VALUES (1, 'Espada de Aço', 'Uma lâmina regular', 3.50, 1500);
INSERT INTO public.itens VALUES (2, 'Poção de Mana', 'Restaura pontos de mana', 0.50, 500);
INSERT INTO public.itens VALUES (3, 'Galão de Mana', 'Restaura os pontos de mana ostensivamente', 5.00, 2000);
INSERT INTO public.itens VALUES (4, 'Poção de Vida', 'Restaura pontos de vida', 0.50, 300);
INSERT INTO public.itens VALUES (5, 'Poção de Restauração', 'Restaura pontos de vida gradativamente', 0.50, 500);
INSERT INTO public.itens VALUES (6, 'Latinha de Cerveja Brahma', 'Cerveja barata', 0.30, 150);
INSERT INTO public.itens VALUES (7, 'Livro de Matemática', 'Livro contendo ensinamentos matemáticos', 1.00, 1000);
INSERT INTO public.itens VALUES (8, 'Durandal', 'Lendária espada do paladino Rolando', 4.00, 50000);
INSERT INTO public.itens VALUES (9, 'Olifante', 'Chifre utilizado pelo paladino Rolando', 0.80, 40000);
INSERT INTO public.itens VALUES (10, 'Pridwen', 'Escudo do lendário Rei Arthur', 5.00, 44000);
INSERT INTO public.itens VALUES (11, 'Luva de Pedreiro', 'Protege as mãos durante o trabalhado árduo exercido pelos pedreiros.', 0.30, 2000);
INSERT INTO public.itens VALUES (12, 'Gorro do Saci', 'Item fonte do poder do Saci Pererê, concedendo total obediência para quem tiver o gorro em sua posse.', 0.50, 0);
INSERT INTO public.itens VALUES (13, 'Arco longo', 'Utilizado pelos ingleses com grande efetividade.', 3.00, 2000);
INSERT INTO public.itens VALUES (14, 'Flechas', 'Munição para arcos de diversos tipos', 0.01, 100);
INSERT INTO public.itens VALUES (15, 'Vestes de Huoshu', 'Túnica feita das peles de Ratos-do-Fogo.', 1.00, 100000);
INSERT INTO public.itens VALUES (16, 'Ascalon', 'Espada utilizada por São Jorge', 4.00, 100000);
INSERT INTO public.itens VALUES (17, 'Tigela de Pedra do Buda', 'Tigela utilizada pelo próprio Buda.', 0.50, 200000);
INSERT INTO public.itens VALUES (18, 'Ramo de Joias', 'Galho enfeitado de pedras preciosas. Encontrado apenas no Monte Hourai', 0.50, 300000);
INSERT INTO public.itens VALUES (19, 'Joia Colorida do Dragão', 'Joia de cinco cores. Encontrada no pescoço de certos tipos de dragão.', 0.30, 300000);
INSERT INTO public.itens VALUES (20, 'Búzio da Andorinha', 'Búzio nascido de uma andorinha. Praticalmente impossível de ser encontrado', 0.20, 100000);


--
-- TOC entry 5203 (class 0 OID 18191)
-- Dependencies: 238
-- Data for Name: jogador; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.jogador VALUES (1, 'Alice', '2026-04-18 21:37:10.190116', true);
INSERT INTO public.jogador VALUES (2, 'Beto', '2026-04-18 21:37:10.190116', true);
INSERT INTO public.jogador VALUES (3, 'Carlos', '2026-04-18 21:37:10.190116', true);
INSERT INTO public.jogador VALUES (4, 'Vitória', '2026-04-18 21:37:10.190116', true);
INSERT INTO public.jogador VALUES (5, 'Feliplus', '2026-04-18 21:37:10.190116', true);
INSERT INTO public.jogador VALUES (6, 'Pietro', '2026-04-18 21:37:10.190116', true);
INSERT INTO public.jogador VALUES (7, 'Rodnei', '2026-04-18 21:37:10.190116', true);


--
-- TOC entry 5193 (class 0 OID 18111)
-- Dependencies: 228
-- Data for Name: local; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.local VALUES (1, NULL, 1, 'Reino de Eldoria', NULL);
INSERT INTO public.local VALUES (2, NULL, 1, 'Império de Erebonia', NULL);
INSERT INTO public.local VALUES (3, NULL, 5, 'Crossbell', NULL);
INSERT INTO public.local VALUES (4, NULL, 1, 'Reino de Liberl', NULL);
INSERT INTO public.local VALUES (5, NULL, 5, 'Quahog', NULL);
INSERT INTO public.local VALUES (6, 1, 2, 'A Taverna Dourada', NULL);
INSERT INTO public.local VALUES (7, 1, 6, 'Caverna do Dragão', NULL);
INSERT INTO public.local VALUES (8, 4, 4, 'Rolent', NULL);
INSERT INTO public.local VALUES (9, 2, 5, 'Heimdallr', 'Capital de Erebonia');
INSERT INTO public.local VALUES (10, 5, 2, 'The Druken Clam', 'Pub de Family Guy');
INSERT INTO public.local VALUES (11, 1, 8, 'Floresta Negra', 'Floresta de pinheiros altos. Habitada por fadas.');


--
-- TOC entry 5212 (class 0 OID 18298)
-- Dependencies: 247
-- Data for Name: magiacaracteristicas; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.magiacaracteristicas VALUES (1, 1, 6);
INSERT INTO public.magiacaracteristicas VALUES (3, 1, 1);
INSERT INTO public.magiacaracteristicas VALUES (4, 1, 2);
INSERT INTO public.magiacaracteristicas VALUES (5, 1, 10);
INSERT INTO public.magiacaracteristicas VALUES (9, 1, 2);
INSERT INTO public.magiacaracteristicas VALUES (4, 2, 7);
INSERT INTO public.magiacaracteristicas VALUES (3, 2, 1);
INSERT INTO public.magiacaracteristicas VALUES (5, 2, 30);
INSERT INTO public.magiacaracteristicas VALUES (8, 3, 3);
INSERT INTO public.magiacaracteristicas VALUES (9, 3, 10);
INSERT INTO public.magiacaracteristicas VALUES (5, 3, 3);
INSERT INTO public.magiacaracteristicas VALUES (20, 3, 1);
INSERT INTO public.magiacaracteristicas VALUES (5, 4, 1);
INSERT INTO public.magiacaracteristicas VALUES (6, 4, 1);
INSERT INTO public.magiacaracteristicas VALUES (9, 4, 1);
INSERT INTO public.magiacaracteristicas VALUES (1, 4, 40);
INSERT INTO public.magiacaracteristicas VALUES (3, 4, 40);
INSERT INTO public.magiacaracteristicas VALUES (18, 10, -100);
INSERT INTO public.magiacaracteristicas VALUES (11, 11, 20);
INSERT INTO public.magiacaracteristicas VALUES (10, 11, 10);


--
-- TOC entry 5185 (class 0 OID 18068)
-- Dependencies: 220
-- Data for Name: magias; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.magias VALUES (1, 'Bola de Fogo', 'Mata geral. Explosão de fogo', 15, 3, '8d6');
INSERT INTO public.magias VALUES (2, 'Míssel Mágico', 'Dardo de força infálivel', 5, 1, '3d4+3');
INSERT INTO public.magias VALUES (3, 'Corda Estática', 'Liga dois alvos com um raio.', 15, 2, '2d8');
INSERT INTO public.magias VALUES (4, 'Ponto de Fulgor', 'Pequeno ponto que inflinge dano massivo apenas se o alvo for menor que um metro cúbico', 10, 1, '3d6');
INSERT INTO public.magias VALUES (5, 'Soldagem Molecular', 'Instantaneamente funde dois objetos que estão se tocando a um nível atômico', 12, 2, '');
INSERT INTO public.magias VALUES (6, 'Lábia Aúrea', 'Torna a saliva do feiticeiro em ouro liquido. Qualquer palavra dita tem um valor literal.', 20, 3, '');
INSERT INTO public.magias VALUES (7, 'Ecolocalização', 'Vestigios de movimentos recentes se tornam visíveis. Se torna possível rastrear uma criatura que passou pela área recentemente, mesmo que invisível.', 8, 1, '');
INSERT INTO public.magias VALUES (8, 'Clarividência', 'Pelo futuro próximo é possível conhecer exatamente as ações do alvo no próximo turno. Vantagem contra os ataques do alvo enquanto ativo.', 25, 4, '');
INSERT INTO public.magias VALUES (9, 'Reflexão Cinética', 'Cria um plano invisível. Qualquer projétil que o acerte é parado e pode ser mandado de volta no próximo turno.', 18, 3, '');
INSERT INTO public.magias VALUES (10, 'Aura de Sobriedade', 'Remove todos os efeitos de status para todos em alcance. Previne qualquer outro buff mágico de ser aplicado dentro de sua área de efeito', 12, 2, '');
INSERT INTO public.magias VALUES (11, 'Pele de Pedra', 'Torna a pele do alvo resistente como pedra', 8, 4, '');
INSERT INTO public.magias VALUES (12, 'Monólogo', 'Força o alvo a explicar seu plano por um determinado tempo, tornando-o incapaz de qualquer outra ação. Alvo permanece consciente do feitiço', 15, 2, '');
INSERT INTO public.magias VALUES (13, 'Remorso', 'O alvo se arrepende de sua última ação. Seu próximo turno é gasto desfazendo essa ação', 22, 3, '');
INSERT INTO public.magias VALUES (14, 'Membros Fantasma', 'Cria dois membros ilusórios no feiticeiro. Esses membros não tem substância, mas imitando movimentos, tornando impossível de se distinguir dos ataques reais', 10, 1, '');
INSERT INTO public.magias VALUES (15, 'Borrão de Profundidade', 'O alvo tem sua percepção de profundidade manipulada, fazendo-o acreditar que coisas estão mais perto ou mais longe do que realmente estão. Desvantagem em ataques à distância e saltos', 20, 3, '');
INSERT INTO public.magias VALUES (16, 'Limpeza', 'Instantaneamente remove qualquer sujeira em uma dada área ou individuo', 2, 1, '');
INSERT INTO public.magias VALUES (17, 'Fragância Floral', 'Aplica uma frangância da flor escolhida a qualquer objeto', 2, 1, '');


--
-- TOC entry 5215 (class 0 OID 18352)
-- Dependencies: 250
-- Data for Name: magiasconhecidas; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.magiasconhecidas VALUES (1, 2);
INSERT INTO public.magiasconhecidas VALUES (1, 6);
INSERT INTO public.magiasconhecidas VALUES (1, 7);
INSERT INTO public.magiasconhecidas VALUES (1, 14);
INSERT INTO public.magiasconhecidas VALUES (2, 3);
INSERT INTO public.magiasconhecidas VALUES (2, 6);
INSERT INTO public.magiasconhecidas VALUES (2, 7);
INSERT INTO public.magiasconhecidas VALUES (7, 6);


--
-- TOC entry 5205 (class 0 OID 18202)
-- Dependencies: 240
-- Data for Name: personagem; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.personagem VALUES (1, 1, 1, 2, 'Grog, o Forte', 50, 0, NULL);
INSERT INTO public.personagem VALUES (2, 2, 2, 2, 'Valerius, o Sábio', 20, 40, NULL);
INSERT INTO public.personagem VALUES (3, 4, 3, 1, 'Aoko Aozaki', 120, 400, NULL);
INSERT INTO public.personagem VALUES (4, 3, 4, 4, 'Estelle Bright', 160, 80, NULL);
INSERT INTO public.personagem VALUES (5, 5, 7, 9, 'Marisa Kirisame', 95, 350, NULL);
INSERT INTO public.personagem VALUES (6, 6, 8, 1, 'Tohno Shiki', 85, 30, NULL);
INSERT INTO public.personagem VALUES (7, 1, 9, 1, 'Obi-Wan Kenobi', 155, 180, NULL);
INSERT INTO public.personagem VALUES (8, 7, 10, 10, 'Peter Griffin', 220, 10, NULL);
INSERT INTO public.personagem VALUES (9, NULL, 5, 1, 'Legolas', 140, 60, NULL);
INSERT INTO public.personagem VALUES (10, NULL, 6, 9, 'Aya Shameimaru', 110, 250, NULL);
INSERT INTO public.personagem VALUES (11, NULL, 11, 10, 'Rato A', 4, 0, NULL);
INSERT INTO public.personagem VALUES (12, NULL, 11, 10, 'Rato B', 2, 0, NULL);
INSERT INTO public.personagem VALUES (13, NULL, 11, 10, 'Rato C', 3, 0, NULL);
INSERT INTO public.personagem VALUES (14, NULL, 11, 7, 'Rato D', 4, 0, NULL);
INSERT INTO public.personagem VALUES (15, NULL, 12, 10, 'Jovial Merryment', 25, 0, NULL);
INSERT INTO public.personagem VALUES (16, NULL, 13, 7, 'Laslo, o Fora-da-lei', 13, 10, NULL);
INSERT INTO public.personagem VALUES (17, NULL, 13, 7, 'Lázaro', 28, 10, NULL);
INSERT INTO public.personagem VALUES (18, NULL, 14, 7, 'Dragonete', 175, 36, NULL);


--
-- TOC entry 5187 (class 0 OID 18080)
-- Dependencies: 222
-- Data for Name: qualidades; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.qualidades VALUES (1, 'Dano de Fogo');
INSERT INTO public.qualidades VALUES (2, 'Dano Físico (Cortante)');
INSERT INTO public.qualidades VALUES (3, 'Dano Físico (Contundente)');
INSERT INTO public.qualidades VALUES (4, 'Dano Mágico');
INSERT INTO public.qualidades VALUES (5, 'Alcance');
INSERT INTO public.qualidades VALUES (6, 'Duração');
INSERT INTO public.qualidades VALUES (7, 'Durabilidade');
INSERT INTO public.qualidades VALUES (8, 'Dano Elétrico');
INSERT INTO public.qualidades VALUES (9, 'Área de Efeito');
INSERT INTO public.qualidades VALUES (10, 'Resistência Mágica');
INSERT INTO public.qualidades VALUES (11, 'Resistência Física');
INSERT INTO public.qualidades VALUES (12, 'Cortante');
INSERT INTO public.qualidades VALUES (13, 'Contundente');
INSERT INTO public.qualidades VALUES (14, 'Restauração de Mana');
INSERT INTO public.qualidades VALUES (15, 'Restauração de Vida');
INSERT INTO public.qualidades VALUES (16, 'Amaldiçoado');
INSERT INTO public.qualidades VALUES (17, 'Abençoado');
INSERT INTO public.qualidades VALUES (18, 'Teor Alcoólico');
INSERT INTO public.qualidades VALUES (19, 'Inquebrável');
INSERT INTO public.qualidades VALUES (20, 'Dano Sonoro');
INSERT INTO public.qualidades VALUES (21, 'Resistência contra fogo');
INSERT INTO public.qualidades VALUES (22, 'Pedido de Kaguya');


--
-- TOC entry 5209 (class 0 OID 18247)
-- Dependencies: 244
-- Data for Name: tipoacaocombate; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tipoacaocombate VALUES (1, 'Ataque');
INSERT INTO public.tipoacaocombate VALUES (2, 'Magia');
INSERT INTO public.tipoacaocombate VALUES (3, 'Evasão');
INSERT INTO public.tipoacaocombate VALUES (4, 'Espera');


--
-- TOC entry 5191 (class 0 OID 18100)
-- Dependencies: 226
-- Data for Name: tipolocal; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tipolocal VALUES (1, 'Região', 'Área extensa');
INSERT INTO public.tipolocal VALUES (2, 'Cômodo', 'Espaço interno');
INSERT INTO public.tipolocal VALUES (3, 'Rio', 'Fluxo extenso de água corrente');
INSERT INTO public.tipolocal VALUES (4, 'Vila', 'Pequena comunidade');
INSERT INTO public.tipolocal VALUES (5, 'Cidade', 'Grande comunidade');
INSERT INTO public.tipolocal VALUES (6, 'Caverna', 'Cavidade natural subterrânea');
INSERT INTO public.tipolocal VALUES (7, 'Ninho', 'Espaço onde criaturas e seus filhotes se recolhem');
INSERT INTO public.tipolocal VALUES (8, 'Floresta', 'Supérficie extensa coberta de vegetação de grande porte');
INSERT INTO public.tipolocal VALUES (9, 'Mar', 'Grande corpo de água');
INSERT INTO public.tipolocal VALUES (10, 'Oceano', 'Gigantesco corpo de água');
INSERT INTO public.tipolocal VALUES (11, 'Guilda', 'Espaço onde especialistas se reúnem');
INSERT INTO public.tipolocal VALUES (12, 'Cometa', 'Corpo celestial composto primariamente de gelo');


--
-- TOC entry 5238 (class 0 OID 0)
-- Dependencies: 245
-- Name: acaocombate_id_action_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.acaocombate_id_action_seq', 7, true);


--
-- TOC entry 5239 (class 0 OID 0)
-- Dependencies: 233
-- Name: classe_id_classe_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.classe_id_classe_seq', 16, true);


--
-- TOC entry 5240 (class 0 OID 0)
-- Dependencies: 241
-- Name: combate_id_combate_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.combate_id_combate_seq', 2, true);


--
-- TOC entry 5241 (class 0 OID 0)
-- Dependencies: 231
-- Name: especie_id_especie_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.especie_id_especie_seq', 8, true);


--
-- TOC entry 5242 (class 0 OID 0)
-- Dependencies: 235
-- Name: ficha_id_ficha_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ficha_id_ficha_seq', 14, true);


--
-- TOC entry 5243 (class 0 OID 0)
-- Dependencies: 229
-- Name: habilidades_id_habilidade_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.habilidades_id_habilidade_seq', 8, true);


--
-- TOC entry 5244 (class 0 OID 0)
-- Dependencies: 223
-- Name: itens_id_item_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.itens_id_item_seq', 20, true);


--
-- TOC entry 5245 (class 0 OID 0)
-- Dependencies: 237
-- Name: jogador_id_jogador_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.jogador_id_jogador_seq', 7, true);


--
-- TOC entry 5246 (class 0 OID 0)
-- Dependencies: 227
-- Name: local_id_local_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.local_id_local_seq', 11, true);


--
-- TOC entry 5247 (class 0 OID 0)
-- Dependencies: 219
-- Name: magias_id_magia_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.magias_id_magia_seq', 17, true);


--
-- TOC entry 5248 (class 0 OID 0)
-- Dependencies: 239
-- Name: personagem_id_personagem_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.personagem_id_personagem_seq', 18, true);


--
-- TOC entry 5249 (class 0 OID 0)
-- Dependencies: 221
-- Name: qualidades_id_qualidade_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.qualidades_id_qualidade_seq', 22, true);


--
-- TOC entry 5250 (class 0 OID 0)
-- Dependencies: 243
-- Name: tipoacaocombate_id_tipo_acao_combate_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.tipoacaocombate_id_tipo_acao_combate_seq', 4, true);


--
-- TOC entry 5251 (class 0 OID 0)
-- Dependencies: 225
-- Name: tipolocal_id_tipo_local_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.tipolocal_id_tipo_local_seq', 12, true);


--
-- TOC entry 4998 (class 2606 OID 18267)
-- Name: acaocombate acaocombate_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.acaocombate
    ADD CONSTRAINT acaocombate_pkey PRIMARY KEY (id_action);


--
-- TOC entry 4986 (class 2606 OID 18162)
-- Name: classe classe_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.classe
    ADD CONSTRAINT classe_pkey PRIMARY KEY (id_classe);


--
-- TOC entry 4994 (class 2606 OID 18240)
-- Name: combate combate_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.combate
    ADD CONSTRAINT combate_pkey PRIMARY KEY (id_combate);


--
-- TOC entry 5010 (class 2606 OID 18392)
-- Name: combatentes combatentes_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.combatentes
    ADD CONSTRAINT combatentes_pkey PRIMARY KEY (id_combate, id_personagem);


--
-- TOC entry 4984 (class 2606 OID 18151)
-- Name: especie especie_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.especie
    ADD CONSTRAINT especie_pkey PRIMARY KEY (id_especie);


--
-- TOC entry 4988 (class 2606 OID 18179)
-- Name: ficha ficha_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ficha
    ADD CONSTRAINT ficha_pkey PRIMARY KEY (id_ficha);


--
-- TOC entry 5008 (class 2606 OID 18375)
-- Name: fichahabilidades fichahabilidades_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fichahabilidades
    ADD CONSTRAINT fichahabilidades_pkey PRIMARY KEY (id_ficha, id_habilidade);


--
-- TOC entry 4982 (class 2606 OID 18142)
-- Name: habilidades habilidades_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.habilidades
    ADD CONSTRAINT habilidades_pkey PRIMARY KEY (id_habilidade);


--
-- TOC entry 5004 (class 2606 OID 18341)
-- Name: inventario inventario_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.inventario
    ADD CONSTRAINT inventario_pkey PRIMARY KEY (id_item, id_personagem);


--
-- TOC entry 5002 (class 2606 OID 18323)
-- Name: itemcaracteristicas itemcaracteristicas_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.itemcaracteristicas
    ADD CONSTRAINT itemcaracteristicas_pkey PRIMARY KEY (id_qualidade, id_item);


--
-- TOC entry 4976 (class 2606 OID 18098)
-- Name: itens itens_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.itens
    ADD CONSTRAINT itens_pkey PRIMARY KEY (id_item);


--
-- TOC entry 4990 (class 2606 OID 18200)
-- Name: jogador jogador_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.jogador
    ADD CONSTRAINT jogador_pkey PRIMARY KEY (id_jogador);


--
-- TOC entry 4980 (class 2606 OID 18120)
-- Name: local local_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.local
    ADD CONSTRAINT local_pkey PRIMARY KEY (id_local);


--
-- TOC entry 5000 (class 2606 OID 18305)
-- Name: magiacaracteristicas magiacaracteristicas_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.magiacaracteristicas
    ADD CONSTRAINT magiacaracteristicas_pkey PRIMARY KEY (id_qualidade, id_magia);


--
-- TOC entry 4972 (class 2606 OID 18078)
-- Name: magias magias_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.magias
    ADD CONSTRAINT magias_pkey PRIMARY KEY (id_magia);


--
-- TOC entry 5006 (class 2606 OID 18358)
-- Name: magiasconhecidas magiasconhecidas_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.magiasconhecidas
    ADD CONSTRAINT magiasconhecidas_pkey PRIMARY KEY (id_magia, id_ficha);


--
-- TOC entry 4992 (class 2606 OID 18213)
-- Name: personagem personagem_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.personagem
    ADD CONSTRAINT personagem_pkey PRIMARY KEY (id_personagem);


--
-- TOC entry 4974 (class 2606 OID 18087)
-- Name: qualidades qualidades_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.qualidades
    ADD CONSTRAINT qualidades_pkey PRIMARY KEY (id_qualidade);


--
-- TOC entry 4996 (class 2606 OID 18254)
-- Name: tipoacaocombate tipoacaocombate_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tipoacaocombate
    ADD CONSTRAINT tipoacaocombate_pkey PRIMARY KEY (id_tipo_acao_combate);


--
-- TOC entry 4978 (class 2606 OID 18109)
-- Name: tipolocal tipolocal_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tipolocal
    ADD CONSTRAINT tipolocal_pkey PRIMARY KEY (id_tipo_local);


--
-- TOC entry 5019 (class 2606 OID 18278)
-- Name: acaocombate acaocombate_id_agente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.acaocombate
    ADD CONSTRAINT acaocombate_id_agente_fkey FOREIGN KEY (id_agente) REFERENCES public.personagem(id_personagem);


--
-- TOC entry 5020 (class 2606 OID 18283)
-- Name: acaocombate acaocombate_id_alvo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.acaocombate
    ADD CONSTRAINT acaocombate_id_alvo_fkey FOREIGN KEY (id_alvo) REFERENCES public.personagem(id_personagem);


--
-- TOC entry 5021 (class 2606 OID 18268)
-- Name: acaocombate acaocombate_id_combate_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.acaocombate
    ADD CONSTRAINT acaocombate_id_combate_fkey FOREIGN KEY (id_combate) REFERENCES public.combate(id_combate);


--
-- TOC entry 5022 (class 2606 OID 18288)
-- Name: acaocombate acaocombate_id_item_usado_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.acaocombate
    ADD CONSTRAINT acaocombate_id_item_usado_fkey FOREIGN KEY (id_item_usado) REFERENCES public.itens(id_item);


--
-- TOC entry 5023 (class 2606 OID 18293)
-- Name: acaocombate acaocombate_id_magia_usada_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.acaocombate
    ADD CONSTRAINT acaocombate_id_magia_usada_fkey FOREIGN KEY (id_magia_usada) REFERENCES public.magias(id_magia);


--
-- TOC entry 5024 (class 2606 OID 18273)
-- Name: acaocombate acaocombate_id_tipo_acao_combate_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.acaocombate
    ADD CONSTRAINT acaocombate_id_tipo_acao_combate_fkey FOREIGN KEY (id_tipo_acao_combate) REFERENCES public.tipoacaocombate(id_tipo_acao_combate);


--
-- TOC entry 5018 (class 2606 OID 18241)
-- Name: combate combate_id_local_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.combate
    ADD CONSTRAINT combate_id_local_fkey FOREIGN KEY (id_local) REFERENCES public.local(id_local);


--
-- TOC entry 5035 (class 2606 OID 18393)
-- Name: combatentes combatentes_id_combate_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.combatentes
    ADD CONSTRAINT combatentes_id_combate_fkey FOREIGN KEY (id_combate) REFERENCES public.combate(id_combate);


--
-- TOC entry 5036 (class 2606 OID 18398)
-- Name: combatentes combatentes_id_personagem_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.combatentes
    ADD CONSTRAINT combatentes_id_personagem_fkey FOREIGN KEY (id_personagem) REFERENCES public.personagem(id_personagem);


--
-- TOC entry 5013 (class 2606 OID 18180)
-- Name: ficha ficha_id_classe_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ficha
    ADD CONSTRAINT ficha_id_classe_fkey FOREIGN KEY (id_classe) REFERENCES public.classe(id_classe);


--
-- TOC entry 5014 (class 2606 OID 18185)
-- Name: ficha ficha_id_especie_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ficha
    ADD CONSTRAINT ficha_id_especie_fkey FOREIGN KEY (id_especie) REFERENCES public.especie(id_especie);


--
-- TOC entry 5033 (class 2606 OID 18381)
-- Name: fichahabilidades fichahabilidades_id_ficha_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fichahabilidades
    ADD CONSTRAINT fichahabilidades_id_ficha_fkey FOREIGN KEY (id_ficha) REFERENCES public.ficha(id_ficha);


--
-- TOC entry 5034 (class 2606 OID 18376)
-- Name: fichahabilidades fichahabilidades_id_habilidade_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.fichahabilidades
    ADD CONSTRAINT fichahabilidades_id_habilidade_fkey FOREIGN KEY (id_habilidade) REFERENCES public.habilidades(id_habilidade);


--
-- TOC entry 5029 (class 2606 OID 18342)
-- Name: inventario inventario_id_item_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.inventario
    ADD CONSTRAINT inventario_id_item_fkey FOREIGN KEY (id_item) REFERENCES public.itens(id_item);


--
-- TOC entry 5030 (class 2606 OID 18347)
-- Name: inventario inventario_id_personagem_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.inventario
    ADD CONSTRAINT inventario_id_personagem_fkey FOREIGN KEY (id_personagem) REFERENCES public.personagem(id_personagem);


--
-- TOC entry 5027 (class 2606 OID 18329)
-- Name: itemcaracteristicas itemcaracteristicas_id_item_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.itemcaracteristicas
    ADD CONSTRAINT itemcaracteristicas_id_item_fkey FOREIGN KEY (id_item) REFERENCES public.itens(id_item);


--
-- TOC entry 5028 (class 2606 OID 18324)
-- Name: itemcaracteristicas itemcaracteristicas_id_qualidade_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.itemcaracteristicas
    ADD CONSTRAINT itemcaracteristicas_id_qualidade_fkey FOREIGN KEY (id_qualidade) REFERENCES public.qualidades(id_qualidade);


--
-- TOC entry 5011 (class 2606 OID 18126)
-- Name: local local_id_tipo_local_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.local
    ADD CONSTRAINT local_id_tipo_local_fkey FOREIGN KEY (id_tipo_local) REFERENCES public.tipolocal(id_tipo_local);


--
-- TOC entry 5012 (class 2606 OID 18121)
-- Name: local local_local_pai_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.local
    ADD CONSTRAINT local_local_pai_fkey FOREIGN KEY (local_pai) REFERENCES public.local(id_local);


--
-- TOC entry 5025 (class 2606 OID 18311)
-- Name: magiacaracteristicas magiacaracteristicas_id_magia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.magiacaracteristicas
    ADD CONSTRAINT magiacaracteristicas_id_magia_fkey FOREIGN KEY (id_magia) REFERENCES public.magias(id_magia);


--
-- TOC entry 5026 (class 2606 OID 18306)
-- Name: magiacaracteristicas magiacaracteristicas_id_qualidade_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.magiacaracteristicas
    ADD CONSTRAINT magiacaracteristicas_id_qualidade_fkey FOREIGN KEY (id_qualidade) REFERENCES public.qualidades(id_qualidade);


--
-- TOC entry 5031 (class 2606 OID 18364)
-- Name: magiasconhecidas magiasconhecidas_id_ficha_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.magiasconhecidas
    ADD CONSTRAINT magiasconhecidas_id_ficha_fkey FOREIGN KEY (id_ficha) REFERENCES public.ficha(id_ficha);


--
-- TOC entry 5032 (class 2606 OID 18359)
-- Name: magiasconhecidas magiasconhecidas_id_magia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.magiasconhecidas
    ADD CONSTRAINT magiasconhecidas_id_magia_fkey FOREIGN KEY (id_magia) REFERENCES public.magias(id_magia);


--
-- TOC entry 5015 (class 2606 OID 18219)
-- Name: personagem personagem_id_ficha_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.personagem
    ADD CONSTRAINT personagem_id_ficha_fkey FOREIGN KEY (id_ficha) REFERENCES public.ficha(id_ficha);


--
-- TOC entry 5016 (class 2606 OID 18214)
-- Name: personagem personagem_id_jogador_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.personagem
    ADD CONSTRAINT personagem_id_jogador_fkey FOREIGN KEY (id_jogador) REFERENCES public.jogador(id_jogador);


--
-- TOC entry 5017 (class 2606 OID 18224)
-- Name: personagem personagem_local_atual_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.personagem
    ADD CONSTRAINT personagem_local_atual_fkey FOREIGN KEY (local_atual) REFERENCES public.local(id_local);


-- Completed on 2026-04-18 21:47:53

--
-- PostgreSQL database dump complete
--

\unrestrict pwVqI2vYN5vkeiVQmxLJxQ7EbjFEQQeQN62gLpABeFC7RTcGjCFGVf60HjsRVxv

