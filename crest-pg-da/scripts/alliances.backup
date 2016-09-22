--
-- PostgreSQL database cluster dump
--

-- Started on 2016-09-20 09:26:40

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'md53c417faecf34a537621067d372fc153f';






--
-- Database creation
--

CREATE DATABASE crestj WITH TEMPLATE = template0 OWNER = postgres;
CREATE DATABASE hello_phoenix_dev WITH TEMPLATE = template0 OWNER = postgres;
REVOKE ALL ON DATABASE template1 FROM PUBLIC;
REVOKE ALL ON DATABASE template1 FROM postgres;
GRANT ALL ON DATABASE template1 TO postgres;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;


\connect crestj

SET default_transaction_read_only = off;

--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.3
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-09-20 09:26:41

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2162 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 183 (class 1259 OID 16420)
-- Name: accessgroup; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE accessgroup (
    groupfk bigint,
    adminfk bigint,
    memberfk bigint
);


ALTER TABLE accessgroup OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 16626)
-- Name: alliance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE alliance (
    id bigint NOT NULL,
    shortname character varying(5) NOT NULL,
    name character varying(64) NOT NULL,
    page integer NOT NULL,
    allianceurl character varying(512)
);


ALTER TABLE alliance OWNER TO postgres;

--
-- TOC entry 2163 (class 0 OID 0)
-- Dependencies: 188
-- Name: TABLE alliance; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE alliance IS 'Eve Alliance table';


--
-- TOC entry 2164 (class 0 OID 0)
-- Dependencies: 188
-- Name: COLUMN alliance.id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN alliance.id IS 'alliance ID from ccp';


--
-- TOC entry 2165 (class 0 OID 0)
-- Dependencies: 188
-- Name: COLUMN alliance.shortname; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN alliance.shortname IS 'alliance short name';


--
-- TOC entry 189 (class 1259 OID 16635)
-- Name: alliances; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE alliances (
    "totalAlliances" bigint,
    pagecount bigint,
    countperpage integer
);


ALTER TABLE alliances OWNER TO postgres;

--
-- TOC entry 2166 (class 0 OID 0)
-- Dependencies: 189
-- Name: TABLE alliances; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE alliances IS 'The alliances table';


--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 189
-- Name: COLUMN alliances."totalAlliances"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN alliances."totalAlliances" IS 'Total number of alliances';


--
-- TOC entry 2168 (class 0 OID 0)
-- Dependencies: 189
-- Name: COLUMN alliances.pagecount; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN alliances.pagecount IS 'number of pages';


--
-- TOC entry 2169 (class 0 OID 0)
-- Dependencies: 189
-- Name: COLUMN alliances.countperpage; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN alliances.countperpage IS 'number of alliances per page';


--
-- TOC entry 185 (class 1259 OID 16443)
-- Name: capsuleer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE capsuleer (
    capsuleerpk bigint NOT NULL,
    capsuleer bigint,
    capsuleerid bigint,
    apikeyid bigint,
    apicode character varying(64),
    refreshtoken character varying(256)
);


ALTER TABLE capsuleer OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 16441)
-- Name: capsuleer_capsuleerpk_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE capsuleer_capsuleerpk_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE capsuleer_capsuleerpk_seq OWNER TO postgres;

--
-- TOC entry 2170 (class 0 OID 0)
-- Dependencies: 184
-- Name: capsuleer_capsuleerpk_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE capsuleer_capsuleerpk_seq OWNED BY capsuleer.capsuleerpk;


--
-- TOC entry 182 (class 1259 OID 16414)
-- Name: entity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE entity (
    entitypk bigint NOT NULL,
    name character varying(256),
    isgroup boolean
);


ALTER TABLE entity OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 16412)
-- Name: entity_entityPk_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "entity_entityPk_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "entity_entityPk_seq" OWNER TO postgres;

--
-- TOC entry 2171 (class 0 OID 0)
-- Dependencies: 181
-- Name: entity_entityPk_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "entity_entityPk_seq" OWNED BY entity.entitypk;


--
-- TOC entry 187 (class 1259 OID 16591)
-- Name: sharedrights; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE sharedrights (
    rightpk bigint NOT NULL,
    capsuleer1 bigint,
    capsuleer2 bigint,
    type interval
);


ALTER TABLE sharedrights OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 16589)
-- Name: sharedrights_rightpk_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sharedrights_rightpk_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sharedrights_rightpk_seq OWNER TO postgres;

--
-- TOC entry 2172 (class 0 OID 0)
-- Dependencies: 186
-- Name: sharedrights_rightpk_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE sharedrights_rightpk_seq OWNED BY sharedrights.rightpk;


--
-- TOC entry 2007 (class 2604 OID 16446)
-- Name: capsuleerpk; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY capsuleer ALTER COLUMN capsuleerpk SET DEFAULT nextval('capsuleer_capsuleerpk_seq'::regclass);


--
-- TOC entry 2006 (class 2604 OID 16417)
-- Name: entitypk; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY entity ALTER COLUMN entitypk SET DEFAULT nextval('"entity_entityPk_seq"'::regclass);


--
-- TOC entry 2008 (class 2604 OID 16594)
-- Name: rightpk; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sharedrights ALTER COLUMN rightpk SET DEFAULT nextval('sharedrights_rightpk_seq'::regclass);


--
-- TOC entry 2148 (class 0 OID 16420)
-- Dependencies: 183
-- Data for Name: accessgroup; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY accessgroup (groupfk, adminfk, memberfk) FROM stdin;
213	212	212
214	212	212
215	212	212
\.


--
-- TOC entry 2153 (class 0 OID 16626)
-- Dependencies: 188
-- Data for Name: alliance; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY alliance (id, shortname, name, page, allianceurl) FROM stdin;
99000006	666	Everto Rex Regis	1	https://crest-tq.eveonline.com/alliances/99000006/
99000008	PILS.	People in Lousy Ships	1	https://crest-tq.eveonline.com/alliances/99000008/
99000025	LE	League of Entities	1	https://crest-tq.eveonline.com/alliances/99000025/
99000026	HEL	Hell's Echo	1	https://crest-tq.eveonline.com/alliances/99000026/
99000036	HHE	Hellhound Empire	1	https://crest-tq.eveonline.com/alliances/99000036/
99000038	A.M.F	A.M.F. Alliance	1	https://crest-tq.eveonline.com/alliances/99000038/
99000050	C0CA	Conquerors of Coffee	1	https://crest-tq.eveonline.com/alliances/99000050/
99000051	TOOTH	Toothpic Alliance	1	https://crest-tq.eveonline.com/alliances/99000051/
99000054	PRMTR	Permanent Transience	1	https://crest-tq.eveonline.com/alliances/99000054/
99000063	BEST	Best Alliance	1	https://crest-tq.eveonline.com/alliances/99000063/
99000065	TARU	Tarukai Imperium	1	https://crest-tq.eveonline.com/alliances/99000065/
99000068	LUXUS	LUX aRe us	1	https://crest-tq.eveonline.com/alliances/99000068/
99000069	FF.A	Feindflug Alliance	1	https://crest-tq.eveonline.com/alliances/99000069/
99000075	-DME-	Deus Malus	1	https://crest-tq.eveonline.com/alliances/99000075/
99000076	YUM	Team America World Police	1	https://crest-tq.eveonline.com/alliances/99000076/
99000078	S-A-F	Solar Assault Fleet	1	https://crest-tq.eveonline.com/alliances/99000078/
99000083	I-SEN	The Imperial Senate	1	https://crest-tq.eveonline.com/alliances/99000083/
99000090	SCTRY	Sanctuary Pact	1	https://crest-tq.eveonline.com/alliances/99000090/
99000100	DIE	Detrimental Imperative	1	https://crest-tq.eveonline.com/alliances/99000100/
99000102	YF	Yulai Federation	1	https://crest-tq.eveonline.com/alliances/99000102/
99000103	SPEED	Terminal Ferocity	1	https://crest-tq.eveonline.com/alliances/99000103/
99000106	HORN	Ninja Unicorns with Huge Horns	1	https://crest-tq.eveonline.com/alliances/99000106/
99000107	M1NNY	The Midget Mafia	1	https://crest-tq.eveonline.com/alliances/99000107/
99000110	ACME	Advanced Combine of Miners and Explorers	1	https://crest-tq.eveonline.com/alliances/99000110/
99000112	FC0RD	Fleet Coordination Coalition	1	https://crest-tq.eveonline.com/alliances/99000112/
99000116	FERG	Ferguson Alliance	1	https://crest-tq.eveonline.com/alliances/99000116/
99000120	ARSON	Arson Industries	1	https://crest-tq.eveonline.com/alliances/99000120/
99000122	CAMEL	Combat Mining and Logistics	1	https://crest-tq.eveonline.com/alliances/99000122/
99000129	KAWA	Allianz Kaltes Wasser	1	https://crest-tq.eveonline.com/alliances/99000129/
99000130	CYNO	Drop the Hammer	1	https://crest-tq.eveonline.com/alliances/99000130/
99000133	NHUSA	BLack OPs Alliance	1	https://crest-tq.eveonline.com/alliances/99000133/
99000136	INS	Innectis Syndicate	1	https://crest-tq.eveonline.com/alliances/99000136/
99000137	RF	Renaissance Federation	1	https://crest-tq.eveonline.com/alliances/99000137/
99000138	DECO	Ded End Conglomerates	1	https://crest-tq.eveonline.com/alliances/99000138/
99000140	HMHA	Heavy Metal Hamsters Alliance	1	https://crest-tq.eveonline.com/alliances/99000140/
99000148	EONIA	Eonian Alliance	1	https://crest-tq.eveonline.com/alliances/99000148/
99000152	TRAP	The Randomly Active Players	1	https://crest-tq.eveonline.com/alliances/99000152/
99000153	T2N	Nulli Tertius	1	https://crest-tq.eveonline.com/alliances/99000153/
99000156	E.MSG	Catch Block	1	https://crest-tq.eveonline.com/alliances/99000156/
99000163	NA.	Northern Associates.	1	https://crest-tq.eveonline.com/alliances/99000163/
99000166	ERE	Endless Renaissance	1	https://crest-tq.eveonline.com/alliances/99000166/
99000174	SUMOS	Sukyoko-Moussa Ltd.	1	https://crest-tq.eveonline.com/alliances/99000174/
99000180	MENTL	Elemental Tide	1	https://crest-tq.eveonline.com/alliances/99000180/
99000182	20MIN	The 20 Minuters	1	https://crest-tq.eveonline.com/alliances/99000182/
99000184	SPQR	Imperius Legio Victrix	1	https://crest-tq.eveonline.com/alliances/99000184/
99000186	KNET	KAAII-NET	1	https://crest-tq.eveonline.com/alliances/99000186/
99000187	ISR	International Space Rangers	1	https://crest-tq.eveonline.com/alliances/99000187/
99000192	SHIM	The Shimmering Alliance	1	https://crest-tq.eveonline.com/alliances/99000192/
99000197	BCNCG	The Bacon Cage	1	https://crest-tq.eveonline.com/alliances/99000197/
99000200	DEVIL	The Devil's Warrior Alliance	1	https://crest-tq.eveonline.com/alliances/99000200/
99000203	NVS	Novus Dominatum	1	https://crest-tq.eveonline.com/alliances/99000203/
99000205	BSTRD	The Bastards.	1	https://crest-tq.eveonline.com/alliances/99000205/
99000210	NOHO	No Holes Barred	1	https://crest-tq.eveonline.com/alliances/99000210/
99000211	GENTS	Gentlemen's Agreement	1	https://crest-tq.eveonline.com/alliances/99000211/
99000223	-217-	Aggravated Assault..	1	https://crest-tq.eveonline.com/alliances/99000223/
99000224	ETHOS	Cognitive Distortion	1	https://crest-tq.eveonline.com/alliances/99000224/
99000230	TLC	The Lego Cartel	1	https://crest-tq.eveonline.com/alliances/99000230/
99000231	HAWX	BLACKHAWK ALLIANCE	1	https://crest-tq.eveonline.com/alliances/99000231/
99000234	ENEMY	Enemy-Fleet	1	https://crest-tq.eveonline.com/alliances/99000234/
99000236	BARE	Barely-Legal	1	https://crest-tq.eveonline.com/alliances/99000236/
99000239	-DOG-	D-O-G-M-A	1	https://crest-tq.eveonline.com/alliances/99000239/
99000253	KOF	Knights Of Freedoms	1	https://crest-tq.eveonline.com/alliances/99000253/
99000256	CLONE	Clone Vat	1	https://crest-tq.eveonline.com/alliances/99000256/
99000264	-R-D-	RONA Directorate	1	https://crest-tq.eveonline.com/alliances/99000264/
99000266	HAVOK	Hav0k.	1	https://crest-tq.eveonline.com/alliances/99000266/
99000267	BRBRS	Brave Bears' Alliance	1	https://crest-tq.eveonline.com/alliances/99000267/
99000270	NXS	Nox Stella Consortium	1	https://crest-tq.eveonline.com/alliances/99000270/
99000271	SA	Sukanan Alliance	1	https://crest-tq.eveonline.com/alliances/99000271/
99000276	III.	Intergalactic Interstellar Interns	1	https://crest-tq.eveonline.com/alliances/99000276/
99000277	YAWN.	The Insomniacs	1	https://crest-tq.eveonline.com/alliances/99000277/
99000279	U.N.L	United Nations Legion	1	https://crest-tq.eveonline.com/alliances/99000279/
99000281	CODEX	The CodeX Alliance	1	https://crest-tq.eveonline.com/alliances/99000281/
99000282	FUBB	Social Miscreants	1	https://crest-tq.eveonline.com/alliances/99000282/
99000285	MMMMM	Good Sax	1	https://crest-tq.eveonline.com/alliances/99000285/
99000289	-RDN-	Raiden.	1	https://crest-tq.eveonline.com/alliances/99000289/
99000290	DNA	The Dark Nation	1	https://crest-tq.eveonline.com/alliances/99000290/
99000293	TBOM	The Bohemians	1	https://crest-tq.eveonline.com/alliances/99000293/
99000295	-XFI-	Crossfire Initiative	1	https://crest-tq.eveonline.com/alliances/99000295/
99000296	DTU	DUCT TAPE UNION	1	https://crest-tq.eveonline.com/alliances/99000296/
99000308	QUO	Quo Vadis.	1	https://crest-tq.eveonline.com/alliances/99000308/
99000314	PVP	Sadistic Empire	1	https://crest-tq.eveonline.com/alliances/99000314/
99000315	NGU	NG United	1	https://crest-tq.eveonline.com/alliances/99000315/
99000316	-TEC-	The Exclusive Club	1	https://crest-tq.eveonline.com/alliances/99000316/
99000324	.M4D.	MAD3V1L	1	https://crest-tq.eveonline.com/alliances/99000324/
99000326	INDI	Independent Nation of Infomorphs	1	https://crest-tq.eveonline.com/alliances/99000326/
99000338	-DIE-	Deadly Insidious Experts	1	https://crest-tq.eveonline.com/alliances/99000338/
99000341	EPIC	EPIC Alliance	1	https://crest-tq.eveonline.com/alliances/99000341/
99000343	-NC-	Nuclear Confusion	1	https://crest-tq.eveonline.com/alliances/99000343/
99000344	TARTS	Minmatarts	1	https://crest-tq.eveonline.com/alliances/99000344/
99000348	VAGUE	Dread Playhouse	1	https://crest-tq.eveonline.com/alliances/99000348/
99000353	SPACE	W-Space	1	https://crest-tq.eveonline.com/alliances/99000353/
99000355	GROWL	Laika.	1	https://crest-tq.eveonline.com/alliances/99000355/
99000366	-VAS-	Vanguard Ascendants	1	https://crest-tq.eveonline.com/alliances/99000366/
99000385	CLE	Candy Land Express	1	https://crest-tq.eveonline.com/alliances/99000385/
99000397	NAM4H	BROTHERHOOD OF NAM	1	https://crest-tq.eveonline.com/alliances/99000397/
99000403	QUD	Quantum Decadence	1	https://crest-tq.eveonline.com/alliances/99000403/
99000405	CORAL	CORE Alliance	1	https://crest-tq.eveonline.com/alliances/99000405/
99000409	R-P	Alliance Russian Pack	1	https://crest-tq.eveonline.com/alliances/99000409/
99000410	STAG	Sticky Green Acres	1	https://crest-tq.eveonline.com/alliances/99000410/
99000412	PN	Peregrine Nation	1	https://crest-tq.eveonline.com/alliances/99000412/
99000419	FORGE	Viking Empire	1	https://crest-tq.eveonline.com/alliances/99000419/
99000423	A A	Ad-Astra	1	https://crest-tq.eveonline.com/alliances/99000423/
99000425	HFC	Home Front Coalition	1	https://crest-tq.eveonline.com/alliances/99000425/
99000426	A4	The Alternative 4	1	https://crest-tq.eveonline.com/alliances/99000426/
99000429	GONER	GONER PARADIGM	1	https://crest-tq.eveonline.com/alliances/99000429/
99000433	ERGNK	Ergenekon Alliance	1	https://crest-tq.eveonline.com/alliances/99000433/
99000448	5AVE5	The Good Word	1	https://crest-tq.eveonline.com/alliances/99000448/
99000456	JANKY	Janky ass Mo-Fos	1	https://crest-tq.eveonline.com/alliances/99000456/
99000457	S.O.B	Shadows Of Betrayal	1	https://crest-tq.eveonline.com/alliances/99000457/
99000459	TOTM	The One Track Mind Alliance	1	https://crest-tq.eveonline.com/alliances/99000459/
99000461	-SFA-	Sacred Fire Alliance	1	https://crest-tq.eveonline.com/alliances/99000461/
99000466	TCOA	The Citadel of Asgard	1	https://crest-tq.eveonline.com/alliances/99000466/
99000469	APES	Monkey Circus	1	https://crest-tq.eveonline.com/alliances/99000469/
99000476	TSCN	Trans Stellar Consolidation	1	https://crest-tq.eveonline.com/alliances/99000476/
99000477	3SG	3 Stars Gard	1	https://crest-tq.eveonline.com/alliances/99000477/
99000479	OTFED	Otas Federation	1	https://crest-tq.eveonline.com/alliances/99000479/
99000481	TALUN	Talocan United	1	https://crest-tq.eveonline.com/alliances/99000481/
99000483	42	Section 42	1	https://crest-tq.eveonline.com/alliances/99000483/
99000484	G-S-A	Grand Stellar Alliance	1	https://crest-tq.eveonline.com/alliances/99000484/
99000485	U.C.	The United Corporations	1	https://crest-tq.eveonline.com/alliances/99000485/
99000489	S-AL	Sexy Alliance	1	https://crest-tq.eveonline.com/alliances/99000489/
99000498	WALL	Wayina Alliance	1	https://crest-tq.eveonline.com/alliances/99000498/
99000501	-ORB-	Orbital Technology Syndicate	1	https://crest-tq.eveonline.com/alliances/99000501/
99000503	-XII	VIOLENCE INC.	1	https://crest-tq.eveonline.com/alliances/99000503/
99000507	EYRIE	Eyrie Alliance	1	https://crest-tq.eveonline.com/alliances/99000507/
99000510	FINE	Federation InterStellaris of New Eden	1	https://crest-tq.eveonline.com/alliances/99000510/
99000516	FUGEN	FutureGen Alliance	1	https://crest-tq.eveonline.com/alliances/99000516/
99000519	UCC	United Coalition of Corporations	1	https://crest-tq.eveonline.com/alliances/99000519/
99000520	JAK0	Jagdkommand0	1	https://crest-tq.eveonline.com/alliances/99000520/
99000522	N.E.O	NEW.ORDER	1	https://crest-tq.eveonline.com/alliances/99000522/
99000523	ADARK	Army of Dark Shadows	1	https://crest-tq.eveonline.com/alliances/99000523/
99000526	57TH	BR0WNC0ATS	1	https://crest-tq.eveonline.com/alliances/99000526/
99000531	GALT	We Are John Galt	1	https://crest-tq.eveonline.com/alliances/99000531/
99000534	TSA	The Sirius Alliance	1	https://crest-tq.eveonline.com/alliances/99000534/
99000540	GRIEF	I Got Banned For That	1	https://crest-tq.eveonline.com/alliances/99000540/
99000552	CARTM	Carthage Empires	1	https://crest-tq.eveonline.com/alliances/99000552/
99000553	ATVR	Brennivin Alliance	1	https://crest-tq.eveonline.com/alliances/99000553/
99000554	TDA.	The Ditanian Alliance	1	https://crest-tq.eveonline.com/alliances/99000554/
99000561	UAFMC	United Armed Forces Military Core	1	https://crest-tq.eveonline.com/alliances/99000561/
99000563	ORCA	Orbital Acquisition and Trade	1	https://crest-tq.eveonline.com/alliances/99000563/
99000570	HAMRD	Armed-n-Hammered	1	https://crest-tq.eveonline.com/alliances/99000570/
99000576	EBEM	V.L.A.S.T	1	https://crest-tq.eveonline.com/alliances/99000576/
99000580	ITDOT	Ignore This.	1	https://crest-tq.eveonline.com/alliances/99000580/
99000586	CLA	The Corcu Loigde Alliance	1	https://crest-tq.eveonline.com/alliances/99000586/
99000593	TMA	The Methodical Alliance	1	https://crest-tq.eveonline.com/alliances/99000593/
99000597	BRU	Black Revenge Union	1	https://crest-tq.eveonline.com/alliances/99000597/
99000598	A-LAW	CareBear Union	1	https://crest-tq.eveonline.com/alliances/99000598/
99000604	UNK	Empire Unknown	1	https://crest-tq.eveonline.com/alliances/99000604/
99000608	WAYTO	The Way to Amalthea	1	https://crest-tq.eveonline.com/alliances/99000608/
99000609	AURA.	Atrox Urbanis Respublique Abundatia	1	https://crest-tq.eveonline.com/alliances/99000609/
99000610	FTU	FALCON-TERROR-UNIVERSE	1	https://crest-tq.eveonline.com/alliances/99000610/
99000615	SV	Shadows Veil	1	https://crest-tq.eveonline.com/alliances/99000615/
99000617	NOTA	None Of The Above	1	https://crest-tq.eveonline.com/alliances/99000617/
99000619	OUZO	O.U.Z.O. Alliance	1	https://crest-tq.eveonline.com/alliances/99000619/
99000620	OPOSM	The Possum Lodge	1	https://crest-tq.eveonline.com/alliances/99000620/
99000627	BDP	Bruderschaft der Pilger	1	https://crest-tq.eveonline.com/alliances/99000627/
99000631	POPI	Pod Pilots Alliance	1	https://crest-tq.eveonline.com/alliances/99000631/
99000637	-----	Order of the Void	1	https://crest-tq.eveonline.com/alliances/99000637/
99000645	RVB-R	RvB - RED Federation	1	https://crest-tq.eveonline.com/alliances/99000645/
99000648	OB.	Outbreak.	1	https://crest-tq.eveonline.com/alliances/99000648/
99000652	RVB-B	RvB - BLUE Republic	1	https://crest-tq.eveonline.com/alliances/99000652/
99000653	CHICA	Cha Ching PLC	1	https://crest-tq.eveonline.com/alliances/99000653/
99000660	PMS	Property Management Solutions	1	https://crest-tq.eveonline.com/alliances/99000660/
99000661	WALLT	WALLTREIPERS ALLIANCE	1	https://crest-tq.eveonline.com/alliances/99000661/
99000664	ADULT	Adult Children	1	https://crest-tq.eveonline.com/alliances/99000664/
99000670	TECH	Technical Exploration Conglomerate of Hemera	1	https://crest-tq.eveonline.com/alliances/99000670/
99000677	DASH	Drake Ashigaru	1	https://crest-tq.eveonline.com/alliances/99000677/
99000679	KOT	Knights of Tomorrow	1	https://crest-tq.eveonline.com/alliances/99000679/
99000687	OCD	Order of Consumptive Desolation	1	https://crest-tq.eveonline.com/alliances/99000687/
99000693	MOOSE	Moose Alliance	1	https://crest-tq.eveonline.com/alliances/99000693/
99000700	UPSC	UPS Citizens	1	https://crest-tq.eveonline.com/alliances/99000700/
99000714	ISK.-	For a few ISK more	1	https://crest-tq.eveonline.com/alliances/99000714/
99000717	CT	Electra.	1	https://crest-tq.eveonline.com/alliances/99000717/
99000719	GUN	Gunboat Diplomacy	1	https://crest-tq.eveonline.com/alliances/99000719/
99000724	-CW-	Crimson Wings.	1	https://crest-tq.eveonline.com/alliances/99000724/
99000726	Q-O-P	Flying Burning Ships Alliance	1	https://crest-tq.eveonline.com/alliances/99000726/
99000733	KISS	BPLAN	1	https://crest-tq.eveonline.com/alliances/99000733/
99000739	SOUND	Of Sound Mind	1	https://crest-tq.eveonline.com/alliances/99000739/
99000745	ZN-SB	Zombie Ninja Space Bears	1	https://crest-tq.eveonline.com/alliances/99000745/
99000748	I Z I	I z i d a	1	https://crest-tq.eveonline.com/alliances/99000748/
99000749	GAARD	Ravensgaard	1	https://crest-tq.eveonline.com/alliances/99000749/
99000756	P O D	P O D	1	https://crest-tq.eveonline.com/alliances/99000756/
99000757	NX	Nexus Alliance	1	https://crest-tq.eveonline.com/alliances/99000757/
99000760	HAD	HumAnnoyeD	1	https://crest-tq.eveonline.com/alliances/99000760/
99000763	CREW	Crewed by the Damned	1	https://crest-tq.eveonline.com/alliances/99000763/
99000765	-CRC-	The Chogo Ri Commonwealth	1	https://crest-tq.eveonline.com/alliances/99000765/
99000767	SSC	Sleeper Social Club	1	https://crest-tq.eveonline.com/alliances/99000767/
99000770	ARSE	Another Really Stupid Enterprise	1	https://crest-tq.eveonline.com/alliances/99000770/
99000796	V.CO	Valkyrie Coalition	1	https://crest-tq.eveonline.com/alliances/99000796/
99000797	SU	Stardust Underground	1	https://crest-tq.eveonline.com/alliances/99000797/
99000798	FRAK	Fraggle Rock.	1	https://crest-tq.eveonline.com/alliances/99000798/
99000807	JBOC	Just a Bunch Of Corporations	1	https://crest-tq.eveonline.com/alliances/99000807/
99000812	U.O.R	Union 0f Revolution	1	https://crest-tq.eveonline.com/alliances/99000812/
99000813	SWE-X	Heavens Souls	1	https://crest-tq.eveonline.com/alliances/99000813/
99000814	E8OLA	E.B.O.L.A.	1	https://crest-tq.eveonline.com/alliances/99000814/
99000818	PONYX	HORSE-KILLERS	1	https://crest-tq.eveonline.com/alliances/99000818/
99000819	NEXUS	Nexus Fleet	1	https://crest-tq.eveonline.com/alliances/99000819/
99000820	IFF	Interstellar Frontier Federation	1	https://crest-tq.eveonline.com/alliances/99000820/
99000824	VOTUM	incendia equus	1	https://crest-tq.eveonline.com/alliances/99000824/
99000827	-TUF-	Terran United Federation	1	https://crest-tq.eveonline.com/alliances/99000827/
99000828	-FB-	Fearless Bears	1	https://crest-tq.eveonline.com/alliances/99000828/
99000833	BOW	Band of Wanderers	1	https://crest-tq.eveonline.com/alliances/99000833/
99000842	EXR	Equinox Rising	1	https://crest-tq.eveonline.com/alliances/99000842/
99000846	MONO	Monocle Overlords	1	https://crest-tq.eveonline.com/alliances/99000846/
99000850	ABH	Abh Alliance	1	https://crest-tq.eveonline.com/alliances/99000850/
99000852	LOLGF	Exodus.	1	https://crest-tq.eveonline.com/alliances/99000852/
99000854	ADHC	Adhocracy	1	https://crest-tq.eveonline.com/alliances/99000854/
99000866	IRON.	Iron Sky.	1	https://crest-tq.eveonline.com/alliances/99000866/
99000867	SOC	Stillness of Chaos	1	https://crest-tq.eveonline.com/alliances/99000867/
99000868	NSANE	Crazy by Nature	1	https://crest-tq.eveonline.com/alliances/99000868/
99000871	HAHA	Surely You're Joking	1	https://crest-tq.eveonline.com/alliances/99000871/
99000872	DINC	Absolute Damage Inc.	1	https://crest-tq.eveonline.com/alliances/99000872/
99000873	CNU	CONSORTIUM UNIVERSALIS	1	https://crest-tq.eveonline.com/alliances/99000873/
99000875	F0RCE	ZOMBIE KITTY FORCE	1	https://crest-tq.eveonline.com/alliances/99000875/
99000883	UHOHH	It's A Trap Alliance	1	https://crest-tq.eveonline.com/alliances/99000883/
99000888	P.P	Peace and Profit	1	https://crest-tq.eveonline.com/alliances/99000888/
99000899	HAMA	Hearts And Minds Alliance	1	https://crest-tq.eveonline.com/alliances/99000899/
99000902	CASA	The Lucian Alliance.	1	https://crest-tq.eveonline.com/alliances/99000902/
99000904	EVE-0	The Seventh Day	1	https://crest-tq.eveonline.com/alliances/99000904/
99000906	WORLD	Peacemakers. Coalition	1	https://crest-tq.eveonline.com/alliances/99000906/
99000908	IMS	Insane Muppet Society Alliance	1	https://crest-tq.eveonline.com/alliances/99000908/
99000909	CHAV	HOMELESS SOCIETY	1	https://crest-tq.eveonline.com/alliances/99000909/
99000912	EXARM	Exploratory Armaments Syndicate	1	https://crest-tq.eveonline.com/alliances/99000912/
99000914	YAHOO	Pernicious Intent	1	https://crest-tq.eveonline.com/alliances/99000914/
99000920	UFA.	Unforgiving.	1	https://crest-tq.eveonline.com/alliances/99000920/
99000921	VNDLX	Vandalism Expected	1	https://crest-tq.eveonline.com/alliances/99000921/
99000922	-FU-	Drunk 'n' Disorderly	1	https://crest-tq.eveonline.com/alliances/99000922/
99000924	SMG	MITSUI SUMITOMO GROUP	1	https://crest-tq.eveonline.com/alliances/99000924/
99000925	-ZPE-	Zombie Pony Express	1	https://crest-tq.eveonline.com/alliances/99000925/
99000931	O R E	Ore Kings	1	https://crest-tq.eveonline.com/alliances/99000931/
99000934	CUST	New Eden Customs Enforcement	1	https://crest-tq.eveonline.com/alliances/99000934/
99000947	V C F	VOGON CONSTRUCTION FLEETS	1	https://crest-tq.eveonline.com/alliances/99000947/
99000949	FUHRD	Broken Toys	1	https://crest-tq.eveonline.com/alliances/99000949/
99000950	CEDI	Central Directorate of Intelligence	1	https://crest-tq.eveonline.com/alliances/99000950/
99000954	UNBD	Un.Bound	1	https://crest-tq.eveonline.com/alliances/99000954/
99000962	NULLA	Nulla Clementia	1	https://crest-tq.eveonline.com/alliances/99000962/
99000967	PT	Paper Tiger Coalition	1	https://crest-tq.eveonline.com/alliances/99000967/
99000973	SYLPH	Sons of Sylph	1	https://crest-tq.eveonline.com/alliances/99000973/
99000975	SWINE	Swine Aviation Labs	1	https://crest-tq.eveonline.com/alliances/99000975/
99000976	LP	LazyProd	1	https://crest-tq.eveonline.com/alliances/99000976/
99000977	FREAL	The Free Ealurian State	1	https://crest-tq.eveonline.com/alliances/99000977/
99000987	COS	Cosmic Allianz	1	https://crest-tq.eveonline.com/alliances/99000987/
99000988	KINGS	Lizard Kings	1	https://crest-tq.eveonline.com/alliances/99000988/
99000996	STEAL	Stealth Syndicate	1	https://crest-tq.eveonline.com/alliances/99000996/
99000997	V0R	V0RTEX.	1	https://crest-tq.eveonline.com/alliances/99000997/
99001002	-SYN-	Synaptic Membrane	1	https://crest-tq.eveonline.com/alliances/99001002/
99001005	DUNKA	Dunka Dunka	1	https://crest-tq.eveonline.com/alliances/99001005/
99001006	TANK	Imperial Hull Tankers	1	https://crest-tq.eveonline.com/alliances/99001006/
99001010	JDI	Jack Daniel's Industries	1	https://crest-tq.eveonline.com/alliances/99001010/
99001011	OOS	Out of Sight.	1	https://crest-tq.eveonline.com/alliances/99001011/
99001015	NEEDS	The Needs Of The Few Many	2	https://crest-tq.eveonline.com/alliances/99001015/
99001017	DP-R	Dark Phoenix Rising.	2	https://crest-tq.eveonline.com/alliances/99001017/
99001021	-0-	Sparta.	2	https://crest-tq.eveonline.com/alliances/99001021/
99001024	-MUG-	Macchiato Mafia	2	https://crest-tq.eveonline.com/alliances/99001024/
99001032	DO	Daily Operations	2	https://crest-tq.eveonline.com/alliances/99001032/
99001047	THETA	Takahashi Alliance	2	https://crest-tq.eveonline.com/alliances/99001047/
99001048	FRICK	FreiTek Heavy Industries	2	https://crest-tq.eveonline.com/alliances/99001048/
99001052	SYNEX	Synthetic Existence	2	https://crest-tq.eveonline.com/alliances/99001052/
99001069	AGC	The Interstellar Contract Agency	2	https://crest-tq.eveonline.com/alliances/99001069/
99001072	FTRS	Ambriel's Harbor	2	https://crest-tq.eveonline.com/alliances/99001072/
99001075	SUD	Space Dust Union	2	https://crest-tq.eveonline.com/alliances/99001075/
99001077	SW.	Stainwagon.	2	https://crest-tq.eveonline.com/alliances/99001077/
99001078	LIBER	L I B E R T Y	2	https://crest-tq.eveonline.com/alliances/99001078/
99001079	SGA	StoneGuard Alliance	2	https://crest-tq.eveonline.com/alliances/99001079/
99001081	-REV-	The Revenant Order	2	https://crest-tq.eveonline.com/alliances/99001081/
99001082	-MDI-	Mutual Defense Initiative	2	https://crest-tq.eveonline.com/alliances/99001082/
99001083	KDD	Kin.Dza.Dza	2	https://crest-tq.eveonline.com/alliances/99001083/
99001091	URSI	Ursi Lienosus	2	https://crest-tq.eveonline.com/alliances/99001091/
99001093	FL---	Flatline.	2	https://crest-tq.eveonline.com/alliances/99001093/
99001099	TW	The Watchmen.	2	https://crest-tq.eveonline.com/alliances/99001099/
99001105	-7TH-	Seventh Sanctum.	2	https://crest-tq.eveonline.com/alliances/99001105/
99001109	SOLRQ	Solar Quest	2	https://crest-tq.eveonline.com/alliances/99001109/
99001111	LENS	Lensmen of the Galactic Patrol	2	https://crest-tq.eveonline.com/alliances/99001111/
99001115	GEARS	Gears Confederation	2	https://crest-tq.eveonline.com/alliances/99001115/
99001116	SHOT	Shotgun Weddings	2	https://crest-tq.eveonline.com/alliances/99001116/
99001123	SOTA	Shadow of the Apocalypse	2	https://crest-tq.eveonline.com/alliances/99001123/
99001127	SONE	Soldiers Of New Eve	2	https://crest-tq.eveonline.com/alliances/99001127/
99001129	JAGEN	Ruin Nation	2	https://crest-tq.eveonline.com/alliances/99001129/
99001132	NOWAY	Ineluctable.	2	https://crest-tq.eveonline.com/alliances/99001132/
99001134	PIRAT	P I R A T	2	https://crest-tq.eveonline.com/alliances/99001134/
99001137	HERD.	Thundering Herd	2	https://crest-tq.eveonline.com/alliances/99001137/
99001138	BHOLD	Behold.	2	https://crest-tq.eveonline.com/alliances/99001138/
99001139	RECUR	RECURSIVE ASCENSION	2	https://crest-tq.eveonline.com/alliances/99001139/
99001141	PLEAZ	MORE.DPS	2	https://crest-tq.eveonline.com/alliances/99001141/
99001146	EOS	Exiled Ones	2	https://crest-tq.eveonline.com/alliances/99001146/
99001152	DCA	Domini Canes	2	https://crest-tq.eveonline.com/alliances/99001152/
99001154	NZAU	NZAU Alliance	2	https://crest-tq.eveonline.com/alliances/99001154/
99001156	SLACK	Successful Slackers	2	https://crest-tq.eveonline.com/alliances/99001156/
99001157	DTAP	Double Tap.	2	https://crest-tq.eveonline.com/alliances/99001157/
99001158	WR3CK	Many Reckless Corps	2	https://crest-tq.eveonline.com/alliances/99001158/
99001159	BAW	Break-A-Wish Foundation	2	https://crest-tq.eveonline.com/alliances/99001159/
99001175	SANG	Sangar.	2	https://crest-tq.eveonline.com/alliances/99001175/
99001178	PHA	Phantom.	2	https://crest-tq.eveonline.com/alliances/99001178/
99001182	MOARR	Intergalactic Conservation Movement	2	https://crest-tq.eveonline.com/alliances/99001182/
99001184	S	Societas Liberalis Civitas	2	https://crest-tq.eveonline.com/alliances/99001184/
99001188	-DAC-	Dark Angels Chapter	2	https://crest-tq.eveonline.com/alliances/99001188/
99001191	AYN	Ayn Sof Aur	2	https://crest-tq.eveonline.com/alliances/99001191/
99001193	EA	Entropia Alliance	2	https://crest-tq.eveonline.com/alliances/99001193/
99001200	GFA	ZADA ALLIANCE	2	https://crest-tq.eveonline.com/alliances/99001200/
99001201	TNCA	The Night Crew Alliance	2	https://crest-tq.eveonline.com/alliances/99001201/
99001202	AL3X	AL3XAND3R.	2	https://crest-tq.eveonline.com/alliances/99001202/
99001203	B.P.A	Black Pearl Alliance	2	https://crest-tq.eveonline.com/alliances/99001203/
99001207	SLR	Solaris Mortis	2	https://crest-tq.eveonline.com/alliances/99001207/
99001208	PUNK	Punkz 'n Monkeys	2	https://crest-tq.eveonline.com/alliances/99001208/
99001215	-3X3-	Nine Worlds	2	https://crest-tq.eveonline.com/alliances/99001215/
99001218	FIRE	StructureDamage	2	https://crest-tq.eveonline.com/alliances/99001218/
99001225	GAG	Gears and Grease	2	https://crest-tq.eveonline.com/alliances/99001225/
99001227	TEDDY	The Carebears Alliance	2	https://crest-tq.eveonline.com/alliances/99001227/
99001230	IBC	Interstellar Banana Conglomerate	2	https://crest-tq.eveonline.com/alliances/99001230/
99001233	XTCBX	The Cauldron-Born	2	https://crest-tq.eveonline.com/alliances/99001233/
99001234	TOAST	Battlestar Resistance	2	https://crest-tq.eveonline.com/alliances/99001234/
99001242	-EOA-	Empire of Arcadia	2	https://crest-tq.eveonline.com/alliances/99001242/
99001243	G-SOC	Guardian Society	2	https://crest-tq.eveonline.com/alliances/99001243/
99001244	ELOS	Empire Logistics Syndicate	2	https://crest-tq.eveonline.com/alliances/99001244/
99001245	D4N4	D4MNED N4TION	2	https://crest-tq.eveonline.com/alliances/99001245/
99001250	PCA	Pina Colada Armada	2	https://crest-tq.eveonline.com/alliances/99001250/
99001252	ENDV	Endeavour.	2	https://crest-tq.eveonline.com/alliances/99001252/
99001254	TBK	The Big Kahunas	2	https://crest-tq.eveonline.com/alliances/99001254/
99001255	-ICE-	Nanook Inc.	2	https://crest-tq.eveonline.com/alliances/99001255/
99001258	LDB	La Division Bleue	2	https://crest-tq.eveonline.com/alliances/99001258/
99001260	RAND	Rand Protectorate	2	https://crest-tq.eveonline.com/alliances/99001260/
99001267	.THC.	THE COVERT ENCLAVE	2	https://crest-tq.eveonline.com/alliances/99001267/
99001268	DECKT	Dec Shield	2	https://crest-tq.eveonline.com/alliances/99001268/
99001273	APATH	True Apathy	2	https://crest-tq.eveonline.com/alliances/99001273/
99001274	CO2F	Flaming Nebula	2	https://crest-tq.eveonline.com/alliances/99001274/
99001276	FLUFR	Fluffeh Bunneh Murder Squad	2	https://crest-tq.eveonline.com/alliances/99001276/
99001278	P.H.	Pandora Hearts	2	https://crest-tq.eveonline.com/alliances/99001278/
99001281	PDRI	Pendragon.	2	https://crest-tq.eveonline.com/alliances/99001281/
99001287	-NO-	Unclaimable	2	https://crest-tq.eveonline.com/alliances/99001287/
99001288	DKONE	Dark Knights of New Eden	2	https://crest-tq.eveonline.com/alliances/99001288/
99001290	KRMT	Green Orchestra	2	https://crest-tq.eveonline.com/alliances/99001290/
99001306	POKR.	The House Of Cards.	2	https://crest-tq.eveonline.com/alliances/99001306/
99001308	-AVX-	Averos.	2	https://crest-tq.eveonline.com/alliances/99001308/
99001317	.RU	Banderlogs Alliance	2	https://crest-tq.eveonline.com/alliances/99001317/
99001320	NEFAR	Nefarious Intent.	2	https://crest-tq.eveonline.com/alliances/99001320/
99001323	POL	P O L A R I S	2	https://crest-tq.eveonline.com/alliances/99001323/
99001327	WFFS	Working Stiffs	2	https://crest-tq.eveonline.com/alliances/99001327/
99001330	WATCH	Black Watch.	2	https://crest-tq.eveonline.com/alliances/99001330/
99001331	WIFI	Project Wildfire	2	https://crest-tq.eveonline.com/alliances/99001331/
99001333	1917	R.E.V.O.L.U.T.I.O.N	2	https://crest-tq.eveonline.com/alliances/99001333/
99001338	JOIN	Joint Operation Involving Nobodys	2	https://crest-tq.eveonline.com/alliances/99001338/
99001342	FSKN	Forsaken Knights	2	https://crest-tq.eveonline.com/alliances/99001342/
99001343	FOUR	Four Ponies	2	https://crest-tq.eveonline.com/alliances/99001343/
99001345	...EM	EXPLO. KINETIK und ein wenig THERMAL	2	https://crest-tq.eveonline.com/alliances/99001345/
99001349	-MFP-	Mining For Profit Alliance	2	https://crest-tq.eveonline.com/alliances/99001349/
99001375	MCNRY	Sublime Machinery Inc.	2	https://crest-tq.eveonline.com/alliances/99001375/
99001376	ISAF	Independent Stars Allied Forces	2	https://crest-tq.eveonline.com/alliances/99001376/
99001382	-SD-	Silver Dragonz	2	https://crest-tq.eveonline.com/alliances/99001382/
99001383	U MAD	GaNg BaNg TeAm	2	https://crest-tq.eveonline.com/alliances/99001383/
99001391	DDA	Dorikan Defenders Alliance	2	https://crest-tq.eveonline.com/alliances/99001391/
99001392	COGU	Coalition of Galactic Unity	2	https://crest-tq.eveonline.com/alliances/99001392/
99001401	WRAKI	Wretches and Kings.	2	https://crest-tq.eveonline.com/alliances/99001401/
99001407	-GE-	The Gorgon Empire	2	https://crest-tq.eveonline.com/alliances/99001407/
99001410	-A-S-	Ace of Spades.	2	https://crest-tq.eveonline.com/alliances/99001410/
99001413	DIA	Dynamic Industries Alliance	2	https://crest-tq.eveonline.com/alliances/99001413/
99001416	SKYL	skylian Verge	2	https://crest-tq.eveonline.com/alliances/99001416/
99001417	ATLAS	Atlas Alliance	2	https://crest-tq.eveonline.com/alliances/99001417/
99001424	POINT	Point of Dispute	2	https://crest-tq.eveonline.com/alliances/99001424/
99001425	U210	U210	2	https://crest-tq.eveonline.com/alliances/99001425/
99001426	-GS-	The Gorgon Spawn	2	https://crest-tq.eveonline.com/alliances/99001426/
99001432	ENT	-Entropy-	2	https://crest-tq.eveonline.com/alliances/99001432/
99001434	YAR	Loosely Affiliated Pirates Alliance	2	https://crest-tq.eveonline.com/alliances/99001434/
99001441	MECI	Mineral Excavations and Combat Innovations	2	https://crest-tq.eveonline.com/alliances/99001441/
99001453	BUBBA	Good Ole Boys United	2	https://crest-tq.eveonline.com/alliances/99001453/
99001454	LEGIT	Legitimate Trade Establishments	2	https://crest-tq.eveonline.com/alliances/99001454/
99001460	TM	TEMNAVA	2	https://crest-tq.eveonline.com/alliances/99001460/
99001465	-KG-	Kleinrock Group	2	https://crest-tq.eveonline.com/alliances/99001465/
99001466	IVG	Invision Group	2	https://crest-tq.eveonline.com/alliances/99001466/
99001472	N-TEK	NanoTech Arms	2	https://crest-tq.eveonline.com/alliances/99001472/
99001476	RPA	Rising Phoenix Alliance	2	https://crest-tq.eveonline.com/alliances/99001476/
99001485	ATEAM	-affliction-	2	https://crest-tq.eveonline.com/alliances/99001485/
99001499	PSIHA	Patrician Space Incorporation Holding Alliance	2	https://crest-tq.eveonline.com/alliances/99001499/
99001503	UTWAT	Punch Drunk Lemmings	2	https://crest-tq.eveonline.com/alliances/99001503/
99001504	KOSA	Kindless Outer Space Absolute	2	https://crest-tq.eveonline.com/alliances/99001504/
99001510	CLOUD	Cloud Developments Networks	2	https://crest-tq.eveonline.com/alliances/99001510/
99001515	ROP	Rain of Pain	2	https://crest-tq.eveonline.com/alliances/99001515/
99001517	GAKEP	Gatekeepers Universe	2	https://crest-tq.eveonline.com/alliances/99001517/
99001518	-PI-	Predatory Instincts	2	https://crest-tq.eveonline.com/alliances/99001518/
99001523	SOSA	Sentinels of Sukanan Alliance	2	https://crest-tq.eveonline.com/alliances/99001523/
99001524	SNEAK	Tactical Invader Syndicate	2	https://crest-tq.eveonline.com/alliances/99001524/
99001525	M-U-P	space muppets inc. Alliance	2	https://crest-tq.eveonline.com/alliances/99001525/
99001528	7TH	Seventh Heaven	2	https://crest-tq.eveonline.com/alliances/99001528/
99001538	-NSE-	NightSong Directorate	2	https://crest-tq.eveonline.com/alliances/99001538/
99001539	P.I.S	A Point In Space	2	https://crest-tq.eveonline.com/alliances/99001539/
99001542	MAST	Masterminders.	2	https://crest-tq.eveonline.com/alliances/99001542/
99001552	-TOP-	The older Protection	2	https://crest-tq.eveonline.com/alliances/99001552/
99001555	HRESY	Heretic Nation	2	https://crest-tq.eveonline.com/alliances/99001555/
99001561	PIZZA	Confederation of xXPIZZAXx	2	https://crest-tq.eveonline.com/alliances/99001561/
99001562	KSYND	The Kage Syndicate	2	https://crest-tq.eveonline.com/alliances/99001562/
99001564	CRIME	Critical.Mess	2	https://crest-tq.eveonline.com/alliances/99001564/
99001566	STERN	Sternenflotten-Kommando	2	https://crest-tq.eveonline.com/alliances/99001566/
99001568	AACR	Alliance of Abandoned Cybernetic Rejects	2	https://crest-tq.eveonline.com/alliances/99001568/
99001583	HRSHY	The Big Dirty	2	https://crest-tq.eveonline.com/alliances/99001583/
99001585	INBEV	INBEV BENELUX Alliance	2	https://crest-tq.eveonline.com/alliances/99001585/
99001590	TASHA	The Aurora Shadow	2	https://crest-tq.eveonline.com/alliances/99001590/
99001596	ENIG	Enigmatic Cipher	2	https://crest-tq.eveonline.com/alliances/99001596/
99001600	ZAIB	Zaibatsu Mercantile	2	https://crest-tq.eveonline.com/alliances/99001600/
99001605	Q LT	Q Ltd. Alliance	2	https://crest-tq.eveonline.com/alliances/99001605/
99001610	IMT	Island of Misfit Toons	2	https://crest-tq.eveonline.com/alliances/99001610/
99001612	TNW	The Nights Watch.	2	https://crest-tq.eveonline.com/alliances/99001612/
99001615	INSID	Insidious Intent.	2	https://crest-tq.eveonline.com/alliances/99001615/
99001616	KNGH	Knights United	2	https://crest-tq.eveonline.com/alliances/99001616/
99001618	WOS	Wall of Shadow	2	https://crest-tq.eveonline.com/alliances/99001618/
99001619	PAYUS	Pirate Coalition	2	https://crest-tq.eveonline.com/alliances/99001619/
99001620	G.F	G.Y.R.O.- Federation	2	https://crest-tq.eveonline.com/alliances/99001620/
99001621	FEDAY	Fedaykin.	2	https://crest-tq.eveonline.com/alliances/99001621/
99001625	--D--	Daystrom Alliance	2	https://crest-tq.eveonline.com/alliances/99001625/
99001626	ESN	Eleven Signs Network	2	https://crest-tq.eveonline.com/alliances/99001626/
99001630	BVETS	Bittervet Mercenaries	2	https://crest-tq.eveonline.com/alliances/99001630/
99001633	PLM	PLATF0RM	2	https://crest-tq.eveonline.com/alliances/99001633/
99001634	GMVA	Villore Accords	2	https://crest-tq.eveonline.com/alliances/99001634/
99001635	GHEY	Late Night Alliance	2	https://crest-tq.eveonline.com/alliances/99001635/
99001637	C-A-C	CONFUSE-A-CAT LIMITED	2	https://crest-tq.eveonline.com/alliances/99001637/
99001638	SLTNS	Sultans.	2	https://crest-tq.eveonline.com/alliances/99001638/
99001641	BORG	B O R G	2	https://crest-tq.eveonline.com/alliances/99001641/
99001644	STA	Stalag 13	2	https://crest-tq.eveonline.com/alliances/99001644/
99001647	PLEX	Caldari State Capturing	2	https://crest-tq.eveonline.com/alliances/99001647/
99001648	PTR	P-A-T-R-I-O-T-S	2	https://crest-tq.eveonline.com/alliances/99001648/
99001650	BADF1	BadFellas.	2	https://crest-tq.eveonline.com/alliances/99001650/
99001651	TUS.	Tus Network	2	https://crest-tq.eveonline.com/alliances/99001651/
99001654	SAF	Somethin Awfull Forums	2	https://crest-tq.eveonline.com/alliances/99001654/
99001657	R-R	Rezeda Regnum	2	https://crest-tq.eveonline.com/alliances/99001657/
99001660	BORA	Bora Alis	2	https://crest-tq.eveonline.com/alliances/99001660/
99001662	INFC0	Infernal Coalition	2	https://crest-tq.eveonline.com/alliances/99001662/
99001666	FFA	Free Forces Alliance	2	https://crest-tq.eveonline.com/alliances/99001666/
99001669	TAL	The Aurora Legion	2	https://crest-tq.eveonline.com/alliances/99001669/
99001671	-D.R-	D.RACHE	2	https://crest-tq.eveonline.com/alliances/99001671/
99001672	SENTL	Sentinel Alliance	2	https://crest-tq.eveonline.com/alliances/99001672/
99001674	ASTRE	Voices of a Distant Star	2	https://crest-tq.eveonline.com/alliances/99001674/
99001677	QC	Quantum Cafe	2	https://crest-tq.eveonline.com/alliances/99001677/
99001678	FANIC	Fanatic Empire	2	https://crest-tq.eveonline.com/alliances/99001678/
99001679	HEJAZ	Hejaz State	2	https://crest-tq.eveonline.com/alliances/99001679/
99001681	THORK	Kadeshians	2	https://crest-tq.eveonline.com/alliances/99001681/
99001685	JOKER	Jokers.	2	https://crest-tq.eveonline.com/alliances/99001685/
99001689	2T20	2T20 Alliance	2	https://crest-tq.eveonline.com/alliances/99001689/
99001694	LYFT	Capital Punishment.	2	https://crest-tq.eveonline.com/alliances/99001694/
99001697	CYN0	Suddenly Spaceships.	2	https://crest-tq.eveonline.com/alliances/99001697/
99001698	KUNTZ	Mildly Sober	2	https://crest-tq.eveonline.com/alliances/99001698/
99001700	WAR	Warden.	2	https://crest-tq.eveonline.com/alliances/99001700/
99001701	CI	Criminal Intent.	2	https://crest-tq.eveonline.com/alliances/99001701/
99001704	.LI.	Lunar Industries Partnership	2	https://crest-tq.eveonline.com/alliances/99001704/
99001709	B A H	Lost sheep in space	2	https://crest-tq.eveonline.com/alliances/99001709/
99001716	BAMM	Business Alliance of Manufacturers and Miners	2	https://crest-tq.eveonline.com/alliances/99001716/
99001723	WBROS	Looney Toons Alliance	2	https://crest-tq.eveonline.com/alliances/99001723/
99001729	DUS	Darkside of Us	2	https://crest-tq.eveonline.com/alliances/99001729/
99001730	B U	BLOOD UNION	2	https://crest-tq.eveonline.com/alliances/99001730/
99001738	UNIFY	UNITED STAR FEDERATION	2	https://crest-tq.eveonline.com/alliances/99001738/
99001740	LIONS	Lion's Kingdom Alliance	2	https://crest-tq.eveonline.com/alliances/99001740/
99001742	CALSF	Templis CALSF	2	https://crest-tq.eveonline.com/alliances/99001742/
99001744	IOC	Independent Operators Consortium	2	https://crest-tq.eveonline.com/alliances/99001744/
99001745	GF	Ghostly Fleet	2	https://crest-tq.eveonline.com/alliances/99001745/
99001747	GAA	Gaelic Associates Alliance	2	https://crest-tq.eveonline.com/alliances/99001747/
99001752	JNK.A	Junk Alliance	2	https://crest-tq.eveonline.com/alliances/99001752/
99001760	IKR	I Know Right	2	https://crest-tq.eveonline.com/alliances/99001760/
99001763	DIV 9	Division Nine Alliance	2	https://crest-tq.eveonline.com/alliances/99001763/
99001767	GCOE	The Gentlemen's Club of EVE	2	https://crest-tq.eveonline.com/alliances/99001767/
99001768	ACMEX	Amarr-Caldari Mercantile Exchange	2	https://crest-tq.eveonline.com/alliances/99001768/
99001771	LUNIX	Lundstrom Interstellar Explorations	2	https://crest-tq.eveonline.com/alliances/99001771/
99001773	HUNTZ	Bounty Hunter Alliance	2	https://crest-tq.eveonline.com/alliances/99001773/
99001778	BFMA	Blackflint Mega Alliance	2	https://crest-tq.eveonline.com/alliances/99001778/
99001779	WHYSO	WHY so Seri0Us	2	https://crest-tq.eveonline.com/alliances/99001779/
99001783	SLR.C	Solar Citizens	2	https://crest-tq.eveonline.com/alliances/99001783/
99001791	H	Higorineth Corp Alliance	2	https://crest-tq.eveonline.com/alliances/99001791/
99001795	BNANA	Monkeys with Guns.	2	https://crest-tq.eveonline.com/alliances/99001795/
99001806	GRP-D	Le Groupe D	2	https://crest-tq.eveonline.com/alliances/99001806/
99001808	JRA	Jury Rigged Associates	2	https://crest-tq.eveonline.com/alliances/99001808/
99001809	A-7TH	Amarr 7th Fleet	2	https://crest-tq.eveonline.com/alliances/99001809/
99001814	FU.	Fusion.	2	https://crest-tq.eveonline.com/alliances/99001814/
99001818	HOSS	LIKE A HOSS	2	https://crest-tq.eveonline.com/alliances/99001818/
99001821	ANDO	Andorian Mining Consortium	2	https://crest-tq.eveonline.com/alliances/99001821/
99001822	GW	Greywinged Alliance	2	https://crest-tq.eveonline.com/alliances/99001822/
99001824	MI R	Mi Rovi	2	https://crest-tq.eveonline.com/alliances/99001824/
99001825	ANON	Allies with None	2	https://crest-tq.eveonline.com/alliances/99001825/
99001827	-BF-	Black Fence.	2	https://crest-tq.eveonline.com/alliances/99001827/
99001830	PYCB	Hard Alliance	2	https://crest-tq.eveonline.com/alliances/99001830/
99001832	MAUL	Primal Force	2	https://crest-tq.eveonline.com/alliances/99001832/
99001833	DOX	Paradox United	2	https://crest-tq.eveonline.com/alliances/99001833/
99001841	HOPE	Last Hope..	2	https://crest-tq.eveonline.com/alliances/99001841/
99001844	SWAG	SWAG Co	2	https://crest-tq.eveonline.com/alliances/99001844/
99001845	KRIAS	Azgoths of Kria	2	https://crest-tq.eveonline.com/alliances/99001845/
99001847	MOP	Masterz of Puppetz	2	https://crest-tq.eveonline.com/alliances/99001847/
99001853	FATE	Lost Obsession	2	https://crest-tq.eveonline.com/alliances/99001853/
99001854	- M -	Maelstrom Alliance	2	https://crest-tq.eveonline.com/alliances/99001854/
99001858	ROOT	ROOT Alliance	2	https://crest-tq.eveonline.com/alliances/99001858/
99001862	NUCIA	Inter Malleum et Incudem	2	https://crest-tq.eveonline.com/alliances/99001862/
99001868	HURT	Sadistic.	2	https://crest-tq.eveonline.com/alliances/99001868/
99001869	LV	Lotka Voltera	2	https://crest-tq.eveonline.com/alliances/99001869/
99001870	ECA.	Eagle Cross	2	https://crest-tq.eveonline.com/alliances/99001870/
99001876	GR1EF	Insurrection.	2	https://crest-tq.eveonline.com/alliances/99001876/
99001886	P0LAR	Polarised	2	https://crest-tq.eveonline.com/alliances/99001886/
99001887	PLO	Penguin Liberation Organization	2	https://crest-tq.eveonline.com/alliances/99001887/
99001892	BN	Blue Nation	2	https://crest-tq.eveonline.com/alliances/99001892/
99001896	US	US Alliance	2	https://crest-tq.eveonline.com/alliances/99001896/
99001904	HONOR	Spaceship Samurai	2	https://crest-tq.eveonline.com/alliances/99001904/
99001909	THUGS	Boomstick Thugs	2	https://crest-tq.eveonline.com/alliances/99001909/
99001910	-TM.	Thugz Mansion.	2	https://crest-tq.eveonline.com/alliances/99001910/
99001917	SIDE.	Side Effect.	2	https://crest-tq.eveonline.com/alliances/99001917/
99001919	DBLOC	Infernal Octopus	2	https://crest-tq.eveonline.com/alliances/99001919/
99001926	HULM	Solitude Empire	2	https://crest-tq.eveonline.com/alliances/99001926/
99001932	CHISP	COALICION HISPANA	2	https://crest-tq.eveonline.com/alliances/99001932/
99001934	4TUNA	Fortuna Alliance	3	https://crest-tq.eveonline.com/alliances/99001934/
99001936	EMPIR	Empire of the Never Setting Suns	3	https://crest-tq.eveonline.com/alliances/99001936/
99001938	O	Origins.	3	https://crest-tq.eveonline.com/alliances/99001938/
99001942	BAKED	Mission Control.	3	https://crest-tq.eveonline.com/alliances/99001942/
99001950	24FL	24eme Legion Etrangere	3	https://crest-tq.eveonline.com/alliances/99001950/
99001951	UFED	Umbrella Federation	3	https://crest-tq.eveonline.com/alliances/99001951/
99001952	PM	Polaris Mercenary Alliance	3	https://crest-tq.eveonline.com/alliances/99001952/
99001954	-000-	Caladrius Alliance	3	https://crest-tq.eveonline.com/alliances/99001954/
99001955	WHORE	Whores in space	3	https://crest-tq.eveonline.com/alliances/99001955/
99001959	BLEED	Let It Bleed	3	https://crest-tq.eveonline.com/alliances/99001959/
99001963	MOIST	Moist.	3	https://crest-tq.eveonline.com/alliances/99001963/
99001966	BDM	Brain Dead Muppets	3	https://crest-tq.eveonline.com/alliances/99001966/
99001968	CRIT	Verge of Collapse	3	https://crest-tq.eveonline.com/alliances/99001968/
99001969	.S0B.	SONS of BANE	3	https://crest-tq.eveonline.com/alliances/99001969/
99001970	RFRD	REFORD	3	https://crest-tq.eveonline.com/alliances/99001970/
99001972	MEEPE	Meep Beep Empire	3	https://crest-tq.eveonline.com/alliances/99001972/
99001976	I.F.F	Interstellar Freedom Frontier	3	https://crest-tq.eveonline.com/alliances/99001976/
99001990	PREDS	The Predictables	3	https://crest-tq.eveonline.com/alliances/99001990/
99001993	KITT	Hail the Hoff	3	https://crest-tq.eveonline.com/alliances/99001993/
99001994	-11-	The 11th Hour Alliance	3	https://crest-tq.eveonline.com/alliances/99001994/
99002003	-X3-	No Value	3	https://crest-tq.eveonline.com/alliances/99002003/
99002007	MIL	Milky Way.	3	https://crest-tq.eveonline.com/alliances/99002007/
99002010	STAR	Star Dynamics	3	https://crest-tq.eveonline.com/alliances/99002010/
99002015	YACHT	The Yacht and Whiskey Club	3	https://crest-tq.eveonline.com/alliances/99002015/
99002018	CIA	Caldari Industrialist Association	3	https://crest-tq.eveonline.com/alliances/99002018/
99002019	ASHES	Stellar Ashes	3	https://crest-tq.eveonline.com/alliances/99002019/
99002024	IXP	Varangon Tagma	3	https://crest-tq.eveonline.com/alliances/99002024/
99002025	JINN.	JINN.	3	https://crest-tq.eveonline.com/alliances/99002025/
99002031	M3LTD	Corrosive.	3	https://crest-tq.eveonline.com/alliances/99002031/
99002034	TRUNC	Truncated Minds	3	https://crest-tq.eveonline.com/alliances/99002034/
99002036	END	End Game Alliance	3	https://crest-tq.eveonline.com/alliances/99002036/
99002039	AMM	A M M	3	https://crest-tq.eveonline.com/alliances/99002039/
99002048	EXM	Exquisite Malevolence	3	https://crest-tq.eveonline.com/alliances/99002048/
99002051	-DIS-	Dishonored.	3	https://crest-tq.eveonline.com/alliances/99002051/
99002054	SIU	Strength In Unity	3	https://crest-tq.eveonline.com/alliances/99002054/
99002055	NACHO	NACHO Alliance	3	https://crest-tq.eveonline.com/alliances/99002055/
99002059	ABT	A BETTER TRUST	3	https://crest-tq.eveonline.com/alliances/99002059/
99002060	DFED	The Draconis Federation	3	https://crest-tq.eveonline.com/alliances/99002060/
99002062	STASH	Stash Alliance	3	https://crest-tq.eveonline.com/alliances/99002062/
99002069	BYZ	Byzantine Empire	3	https://crest-tq.eveonline.com/alliances/99002069/
99002072	FEMA	Frontier Emergency Management Agency	3	https://crest-tq.eveonline.com/alliances/99002072/
99002075	GII	Genesis II	3	https://crest-tq.eveonline.com/alliances/99002075/
99002078	BAD-A	Bad Alliance	3	https://crest-tq.eveonline.com/alliances/99002078/
99002085	SMIMC	Severasse Militarized Mining Union	3	https://crest-tq.eveonline.com/alliances/99002085/
99002087	10000	Gods and Monsters	3	https://crest-tq.eveonline.com/alliances/99002087/
99002088	ROFF	ROFF Alliance	3	https://crest-tq.eveonline.com/alliances/99002088/
99002089	D00M	DOOM Alliance	3	https://crest-tq.eveonline.com/alliances/99002089/
99002094	SK0L	0lfrygt	3	https://crest-tq.eveonline.com/alliances/99002094/
99002101	RAISE	Uprising.	3	https://crest-tq.eveonline.com/alliances/99002101/
99002104	RCA	Elemental Union	3	https://crest-tq.eveonline.com/alliances/99002104/
99002107	VEGA	V.e.G.A.	3	https://crest-tq.eveonline.com/alliances/99002107/
99002108	SIGMA	Aurora Foundation	3	https://crest-tq.eveonline.com/alliances/99002108/
99002109	KPEM	K P E M E H b	3	https://crest-tq.eveonline.com/alliances/99002109/
99002114	CEDEV	Cerebral Developments	3	https://crest-tq.eveonline.com/alliances/99002114/
99002119	PWN69	pwn-O-graphy	3	https://crest-tq.eveonline.com/alliances/99002119/
99002122	D3V1L	D3vil's Childr3n	3	https://crest-tq.eveonline.com/alliances/99002122/
99002124	EXIII	Enigma Project	3	https://crest-tq.eveonline.com/alliances/99002124/
99002125	BHP	Polished and Ported	3	https://crest-tq.eveonline.com/alliances/99002125/
99002132	EUA	Error Unkown	3	https://crest-tq.eveonline.com/alliances/99002132/
99002140	TEC	Tectora	3	https://crest-tq.eveonline.com/alliances/99002140/
99002145	WAS	W.A.S. Alliance - Weapons Armor or Shield	3	https://crest-tq.eveonline.com/alliances/99002145/
99002150	ANKM	Antykythera Mechanism	3	https://crest-tq.eveonline.com/alliances/99002150/
99002159	BDSMS	Sleepers Must Suffer	3	https://crest-tq.eveonline.com/alliances/99002159/
99002163	S B C	Special Brew Consortium	3	https://crest-tq.eveonline.com/alliances/99002163/
99002168	.US.	Unconscious State	3	https://crest-tq.eveonline.com/alliances/99002168/
99002169	KOH	The Kingdom of Heaven	3	https://crest-tq.eveonline.com/alliances/99002169/
99002170	4O1K	The Retirement Club	3	https://crest-tq.eveonline.com/alliances/99002170/
99002172	J4LP	I Whip My Slaves Back and Forth	3	https://crest-tq.eveonline.com/alliances/99002172/
99002177	UBET	UBET.	3	https://crest-tq.eveonline.com/alliances/99002177/
99002179	ROKH.	Can't Stop The Rokh	3	https://crest-tq.eveonline.com/alliances/99002179/
99002181	LOTF	League of the Forgotten	3	https://crest-tq.eveonline.com/alliances/99002181/
99002190	WISKY	My Other Laboratory is a Distillery	3	https://crest-tq.eveonline.com/alliances/99002190/
99002191	BANG	ARMAGEDDON LEGION	3	https://crest-tq.eveonline.com/alliances/99002191/
99002196	TCOY	The Council of Yggdrasil	3	https://crest-tq.eveonline.com/alliances/99002196/
99002208	O7M8	Mistakes Were Made.	3	https://crest-tq.eveonline.com/alliances/99002208/
99002214	RATS	Rat Pack Renegades	3	https://crest-tq.eveonline.com/alliances/99002214/
99002217	R1DER	The Devil's Tattoo	3	https://crest-tq.eveonline.com/alliances/99002217/
99002219	ERRRR	What Alliance	3	https://crest-tq.eveonline.com/alliances/99002219/
99002223	ENDVS	Endeavour Shipyards	3	https://crest-tq.eveonline.com/alliances/99002223/
99002226	BEER	Beer needs you	3	https://crest-tq.eveonline.com/alliances/99002226/
99002231	TOG	TOG - The Older Gamers Alliance	3	https://crest-tq.eveonline.com/alliances/99002231/
99002233	BOA	Brothers of Apocrypha.	3	https://crest-tq.eveonline.com/alliances/99002233/
99002239	I-CON	Insidious Consequences	3	https://crest-tq.eveonline.com/alliances/99002239/
99002243	PHOQC	Ouate de Phoque	3	https://crest-tq.eveonline.com/alliances/99002243/
99002246	OTH3R	The Other Sides	3	https://crest-tq.eveonline.com/alliances/99002246/
99002251	T-S-W	Terrible Space Warriors	3	https://crest-tq.eveonline.com/alliances/99002251/
99002253	USURP	Usurper.	3	https://crest-tq.eveonline.com/alliances/99002253/
99002254	SFI	Semper Fidelis Coalition	3	https://crest-tq.eveonline.com/alliances/99002254/
99002256	AURI	Proxima Centauri Alliance	3	https://crest-tq.eveonline.com/alliances/99002256/
99002260	HICKS	Space Hicks	3	https://crest-tq.eveonline.com/alliances/99002260/
99002269	MINE	Sigma Syndicate	3	https://crest-tq.eveonline.com/alliances/99002269/
99002283	PANDA	Panda Cave	3	https://crest-tq.eveonline.com/alliances/99002283/
99002290	45	Confederation of the 45	3	https://crest-tq.eveonline.com/alliances/99002290/
99002298	ATOM	The Atmosphere	3	https://crest-tq.eveonline.com/alliances/99002298/
99002302	-ZZZ-	End of Life	3	https://crest-tq.eveonline.com/alliances/99002302/
99002305	DIGOZ	DiGozinya Alliance	3	https://crest-tq.eveonline.com/alliances/99002305/
99002306	.S-F.	Symetrique Federation Inc.	3	https://crest-tq.eveonline.com/alliances/99002306/
99002307	DAD	Disturbed Acquaintance	3	https://crest-tq.eveonline.com/alliances/99002307/
99002309	HYBAM	Hybrid Hammer	3	https://crest-tq.eveonline.com/alliances/99002309/
99002311	FOCK	Flappy Chickens With Teeth	3	https://crest-tq.eveonline.com/alliances/99002311/
99002312	USA	United Systems of Aridia	3	https://crest-tq.eveonline.com/alliances/99002312/
99002313	MNDOS	The Mandalorians	3	https://crest-tq.eveonline.com/alliances/99002313/
99002315	SO5A	Sons of 5 Aces	3	https://crest-tq.eveonline.com/alliances/99002315/
99002318	SIN	Sindication	3	https://crest-tq.eveonline.com/alliances/99002318/
99002325	SN0R	SiNTaX err0r	3	https://crest-tq.eveonline.com/alliances/99002325/
99002329	CAS	CAStabouts	3	https://crest-tq.eveonline.com/alliances/99002329/
99002330	UOD	The Union of Devoted	3	https://crest-tq.eveonline.com/alliances/99002330/
99002331	THIMK	Cognitive Deterioration.	3	https://crest-tq.eveonline.com/alliances/99002331/
99002333	CB	Construimus Batuimus Alliance	3	https://crest-tq.eveonline.com/alliances/99002333/
99002342	WOHO	WO'S HO'S	3	https://crest-tq.eveonline.com/alliances/99002342/
99002347	FUSIN	Friends United Seeking Influence and Notoriety	3	https://crest-tq.eveonline.com/alliances/99002347/
99002348	IOS	Illusion of Solitude	3	https://crest-tq.eveonline.com/alliances/99002348/
99002356	AZULA	Liandri Covenant	3	https://crest-tq.eveonline.com/alliances/99002356/
99002358	WKD	Wicked Nations	3	https://crest-tq.eveonline.com/alliances/99002358/
99002359	SHAPO	Shadow Politics	3	https://crest-tq.eveonline.com/alliances/99002359/
99002360	FSTAR	Firestar Enterprises	3	https://crest-tq.eveonline.com/alliances/99002360/
99002367	IOU	Evictus.	3	https://crest-tq.eveonline.com/alliances/99002367/
99002370	PWNS	Pwnasaurus.	3	https://crest-tq.eveonline.com/alliances/99002370/
99002373	GB	Gl0rious Bastards	3	https://crest-tq.eveonline.com/alliances/99002373/
99002375	INFO	IT Infotech	3	https://crest-tq.eveonline.com/alliances/99002375/
99002384	OSC	Open Space Consultancy	3	https://crest-tq.eveonline.com/alliances/99002384/
99002385	SPHER	Sphere Alliance	3	https://crest-tq.eveonline.com/alliances/99002385/
99002388	FERNO	INFERNO ALLIANCE	3	https://crest-tq.eveonline.com/alliances/99002388/
99002392	NFA	NEXT FORCE	3	https://crest-tq.eveonline.com/alliances/99002392/
99002393	THE C	The Culture.	3	https://crest-tq.eveonline.com/alliances/99002393/
99002394	LKJAW	LockJaw Inc.	3	https://crest-tq.eveonline.com/alliances/99002394/
99002396	TEREC	TERRA REGNUM	3	https://crest-tq.eveonline.com/alliances/99002396/
99002410	W	Wormhole Holders	3	https://crest-tq.eveonline.com/alliances/99002410/
99002411	HAPPY	Happy Cartel	3	https://crest-tq.eveonline.com/alliances/99002411/
99002417	TXG	The Exiled Gaming	3	https://crest-tq.eveonline.com/alliances/99002417/
99002420	BOT	Just Another Alliance	3	https://crest-tq.eveonline.com/alliances/99002420/
99002422	A--A	Aureus Alae	3	https://crest-tq.eveonline.com/alliances/99002422/
99002432	YUMAD	fluffy bunnies.	3	https://crest-tq.eveonline.com/alliances/99002432/
99002434	ALTZ	Zombie Plague	3	https://crest-tq.eveonline.com/alliances/99002434/
99002436	TIB A	Tiberius Alliance	3	https://crest-tq.eveonline.com/alliances/99002436/
99002446	-TDC-	The Donut Club	3	https://crest-tq.eveonline.com/alliances/99002446/
99002447	.T.T.	Think Tank.	3	https://crest-tq.eveonline.com/alliances/99002447/
99002448	NAGA	Naga Please.	3	https://crest-tq.eveonline.com/alliances/99002448/
99002461	-01-	Lupus Ordo	3	https://crest-tq.eveonline.com/alliances/99002461/
99002465	NYET.	Not A Dot	3	https://crest-tq.eveonline.com/alliances/99002465/
99002471	HG	Headshot Gaming	3	https://crest-tq.eveonline.com/alliances/99002471/
99002474	WINRS	Touch Fiberoptic	3	https://crest-tq.eveonline.com/alliances/99002474/
99002477	VICIA	Veni Vidi Questus Sum	3	https://crest-tq.eveonline.com/alliances/99002477/
99002478	CACT	Class Act Cartel	3	https://crest-tq.eveonline.com/alliances/99002478/
99002479	- G -	G A L L I E N	3	https://crest-tq.eveonline.com/alliances/99002479/
99002487	ULTRA	Brainstorm Technologies	3	https://crest-tq.eveonline.com/alliances/99002487/
99002489	-LL-	Last Legends	3	https://crest-tq.eveonline.com/alliances/99002489/
99002490	QUEST	Victrix Mortalis	3	https://crest-tq.eveonline.com/alliances/99002490/
99002495	SCO	Suicide-Commando	3	https://crest-tq.eveonline.com/alliances/99002495/
99002496	ORBIS	Orbis Imperialis	3	https://crest-tq.eveonline.com/alliances/99002496/
99002498	WHY50	WHYS0 Expendable	3	https://crest-tq.eveonline.com/alliances/99002498/
99002508	BOSCH	Bosch Defence Industries Alliance	3	https://crest-tq.eveonline.com/alliances/99002508/
99002511	-S2T-	Shoot 2 Thrill	3	https://crest-tq.eveonline.com/alliances/99002511/
99002517	C-L	Crystal Lights	3	https://crest-tq.eveonline.com/alliances/99002517/
99002519	-UTE-	United Tech Enterprises	3	https://crest-tq.eveonline.com/alliances/99002519/
99002530	TEN21	0utNumbered	3	https://crest-tq.eveonline.com/alliances/99002530/
99002534	EPEE	La Foret de Broceliande	3	https://crest-tq.eveonline.com/alliances/99002534/
99002535	M.I.F	M.I.F	3	https://crest-tq.eveonline.com/alliances/99002535/
99002541	DISOR	Public Disorder.	3	https://crest-tq.eveonline.com/alliances/99002541/
99002542	CSE	Corelum Syndicate	3	https://crest-tq.eveonline.com/alliances/99002542/
99002546	NOPRO	Unnamed.	3	https://crest-tq.eveonline.com/alliances/99002546/
99002554	SVA	Space Vikings Alliance	3	https://crest-tq.eveonline.com/alliances/99002554/
99002555	CYNCA	Cynical Carebears	3	https://crest-tq.eveonline.com/alliances/99002555/
99002557	HYDRE	HYDRA REVOLUTIONS	3	https://crest-tq.eveonline.com/alliances/99002557/
99002558	BVB	Bear Vodka Balalaika	3	https://crest-tq.eveonline.com/alliances/99002558/
99002569	3RROR	Infinite Recursion	3	https://crest-tq.eveonline.com/alliances/99002569/
99002573	WEBAD	BadWrongFun	3	https://crest-tq.eveonline.com/alliances/99002573/
99002576	-GU-	Genesis Unveiled	3	https://crest-tq.eveonline.com/alliances/99002576/
99002579	EHUGE	ECHA Heavy Industry Group	3	https://crest-tq.eveonline.com/alliances/99002579/
99002581	BIRDS	Immortal Mockingbirds	3	https://crest-tq.eveonline.com/alliances/99002581/
99002584	WAVE	Smile 'n' Wave	3	https://crest-tq.eveonline.com/alliances/99002584/
99002589	TPYP	True Panacea	3	https://crest-tq.eveonline.com/alliances/99002589/
99002594	URA	RED University	3	https://crest-tq.eveonline.com/alliances/99002594/
99002600	BOZO	Clown Punchers Syndicate	3	https://crest-tq.eveonline.com/alliances/99002600/
99002602	GU	Global Union	3	https://crest-tq.eveonline.com/alliances/99002602/
99002603	F.U.	F.U.B.A.R Alliance	3	https://crest-tq.eveonline.com/alliances/99002603/
99002621	B.R.B	Blinky Red Brotherhood	3	https://crest-tq.eveonline.com/alliances/99002621/
99002622	TDPA	The Dark Plague Alliance	3	https://crest-tq.eveonline.com/alliances/99002622/
99002627	-BOD-	Bringers of Death.	3	https://crest-tq.eveonline.com/alliances/99002627/
99002629	ZION	Zion Innovations	3	https://crest-tq.eveonline.com/alliances/99002629/
99002630	NOSOV	No blues	3	https://crest-tq.eveonline.com/alliances/99002630/
99002632	AHCO	ACME Holding Conglomerate	3	https://crest-tq.eveonline.com/alliances/99002632/
99002634	EMP	Insidious Empire	3	https://crest-tq.eveonline.com/alliances/99002634/
99002636	RA.C	RA Citizens	3	https://crest-tq.eveonline.com/alliances/99002636/
99002644	DEADS	DEADSPACE SOCIETY	3	https://crest-tq.eveonline.com/alliances/99002644/
99002646	ALL	Allied Traders	3	https://crest-tq.eveonline.com/alliances/99002646/
99002648	NYC	Not Yet Critical	3	https://crest-tq.eveonline.com/alliances/99002648/
99002650	AEG	Aegis Solaris	3	https://crest-tq.eveonline.com/alliances/99002650/
99002653	SHRIF	Rock the Khamez	3	https://crest-tq.eveonline.com/alliances/99002653/
99002654	EMO	We're Emotionally Unstable	3	https://crest-tq.eveonline.com/alliances/99002654/
99002656	SIEV	Silent Eviction	3	https://crest-tq.eveonline.com/alliances/99002656/
99002661	RECCA	Recca Federation	3	https://crest-tq.eveonline.com/alliances/99002661/
99002670	PADRN	INTERNATIONAL IMMOBILIARE	3	https://crest-tq.eveonline.com/alliances/99002670/
99002671	KW	Kraftwerk.	3	https://crest-tq.eveonline.com/alliances/99002671/
99002675	IX	Ixtab.	3	https://crest-tq.eveonline.com/alliances/99002675/
99002677	B1TER	Bitten.	3	https://crest-tq.eveonline.com/alliances/99002677/
99002678	SCRAP	Scrap Iron Flotilla.	3	https://crest-tq.eveonline.com/alliances/99002678/
99002685	SYN	Synergy of Steel	3	https://crest-tq.eveonline.com/alliances/99002685/
99002703	TILCH	Trouble In Little China	3	https://crest-tq.eveonline.com/alliances/99002703/
99002726	PING	Ping Federation	3	https://crest-tq.eveonline.com/alliances/99002726/
99002732	CHICK	Chicken Wings.	3	https://crest-tq.eveonline.com/alliances/99002732/
99002737	SCARY	Nocturnal Legion	3	https://crest-tq.eveonline.com/alliances/99002737/
99002747	MUNTZ	Full Nelson Best Nelson	3	https://crest-tq.eveonline.com/alliances/99002747/
99002754	LOA	Legions of Ash	3	https://crest-tq.eveonline.com/alliances/99002754/
99002760	CMIND	Criminal Minds	3	https://crest-tq.eveonline.com/alliances/99002760/
99002764	-TSG-	The Scapegoats	3	https://crest-tq.eveonline.com/alliances/99002764/
99002766	AARGH	You've got RED on you	3	https://crest-tq.eveonline.com/alliances/99002766/
99002772	.IFAP	PLEASE NOT VIOLENCE OUR BOATS	3	https://crest-tq.eveonline.com/alliances/99002772/
99002773	-ROS-	Renegades Of Silence	3	https://crest-tq.eveonline.com/alliances/99002773/
99002775	CODE.	CODE.	3	https://crest-tq.eveonline.com/alliances/99002775/
99002777	FRG	Forge Ascendant	3	https://crest-tq.eveonline.com/alliances/99002777/
99002782	DSIM	DSIM	3	https://crest-tq.eveonline.com/alliances/99002782/
99002784	SPSCI	Spaen Scientific	3	https://crest-tq.eveonline.com/alliances/99002784/
99002812	GONE	Gathering Of Nomadic Explorers	3	https://crest-tq.eveonline.com/alliances/99002812/
99002814	COD	Circle of Destruction	3	https://crest-tq.eveonline.com/alliances/99002814/
99002816	PP	Paradise Players	3	https://crest-tq.eveonline.com/alliances/99002816/
99002819	SIA	Silent Ascension	3	https://crest-tq.eveonline.com/alliances/99002819/
99002821	SERIN	The Serenity Initiative	3	https://crest-tq.eveonline.com/alliances/99002821/
99002822	A.I.	N.O.M.A.D.S	3	https://crest-tq.eveonline.com/alliances/99002822/
99002823	DRED	Dark Star Empire	3	https://crest-tq.eveonline.com/alliances/99002823/
99002824	FIRES	Try Rerolling	3	https://crest-tq.eveonline.com/alliances/99002824/
99002826	KIA	KIA Alliance	3	https://crest-tq.eveonline.com/alliances/99002826/
99002829	TR0LL	Tactical Research Lab	3	https://crest-tq.eveonline.com/alliances/99002829/
99002838	TECMO	Nuclear Arms Exchange	3	https://crest-tq.eveonline.com/alliances/99002838/
99002840	1000	Gangs of Brothers	3	https://crest-tq.eveonline.com/alliances/99002840/
99002843	RIEOS	The Rieos Coalition	3	https://crest-tq.eveonline.com/alliances/99002843/
99002847	SATAN	Satan's Disciples.	3	https://crest-tq.eveonline.com/alliances/99002847/
99002856	5PY5	SPIAS LIKE US	3	https://crest-tq.eveonline.com/alliances/99002856/
99002859	CODE	Collateral Defect	3	https://crest-tq.eveonline.com/alliances/99002859/
99002869	LOL	Space Turtles	3	https://crest-tq.eveonline.com/alliances/99002869/
99002874	BRM	BERUM Alliance	3	https://crest-tq.eveonline.com/alliances/99002874/
99002879	CNNI	CNNI	3	https://crest-tq.eveonline.com/alliances/99002879/
99002880	DUTY	Pyre Falcon Defence Combine	3	https://crest-tq.eveonline.com/alliances/99002880/
99002883	SOMAC	Solarmark Coalition	3	https://crest-tq.eveonline.com/alliances/99002883/
99002889	M.I.C	Millitary Industrialists Coalition	3	https://crest-tq.eveonline.com/alliances/99002889/
99002891	BS	Blackwater Separatists	3	https://crest-tq.eveonline.com/alliances/99002891/
99002892	-AGR-	Aegis Requiem	3	https://crest-tq.eveonline.com/alliances/99002892/
99002894	RP.LS	Rock Paper Lasers	3	https://crest-tq.eveonline.com/alliances/99002894/
99002896	-666-	HeIIspawn	3	https://crest-tq.eveonline.com/alliances/99002896/
99002897	IDOSI	Illuminantur Dominium Sicarioum	3	https://crest-tq.eveonline.com/alliances/99002897/
99002902	GOD5	Ghosts of Deep Space	3	https://crest-tq.eveonline.com/alliances/99002902/
99002909	-EWC-	EveWager	3	https://crest-tq.eveonline.com/alliances/99002909/
99002911	TGA	The Gallows Alliance	3	https://crest-tq.eveonline.com/alliances/99002911/
99002913	DRAG	Sleeping Dragons	3	https://crest-tq.eveonline.com/alliances/99002913/
99002915	TERBL	xXPlease Pandemic Citizens Reloaded Alliance.Xx	3	https://crest-tq.eveonline.com/alliances/99002915/
99002918	BPFED	Blood Pact Federation	3	https://crest-tq.eveonline.com/alliances/99002918/
99002922	HERMI	Hermit Collective	3	https://crest-tq.eveonline.com/alliances/99002922/
99002925	ORIO	Orion Empire	3	https://crest-tq.eveonline.com/alliances/99002925/
99002926	WMC	White Mountain Coalition	3	https://crest-tq.eveonline.com/alliances/99002926/
99002929	HEART	Heart 0f Darkness	3	https://crest-tq.eveonline.com/alliances/99002929/
99002930	ACRNM	Clever Use of Neutral Toons	4	https://crest-tq.eveonline.com/alliances/99002930/
99002935	MIMMO	EVE Alliance 20130627	4	https://crest-tq.eveonline.com/alliances/99002935/
99002938	DARK.	DARKNESS.	4	https://crest-tq.eveonline.com/alliances/99002938/
99002941	BURNT	Scorched Earth Policy	4	https://crest-tq.eveonline.com/alliances/99002941/
99002942	EF	Etherlords Fleet	4	https://crest-tq.eveonline.com/alliances/99002942/
99002943	FURIA	Furia.	4	https://crest-tq.eveonline.com/alliances/99002943/
99002946	BYOND	0utlanders.	4	https://crest-tq.eveonline.com/alliances/99002946/
99002949	CK	Celestial Kingdom	4	https://crest-tq.eveonline.com/alliances/99002949/
99002950	LFTUX	Black Marker	4	https://crest-tq.eveonline.com/alliances/99002950/
99002951	INN	The InnerSphere	4	https://crest-tq.eveonline.com/alliances/99002951/
99002952	DEVHX	Negative-Feedback	4	https://crest-tq.eveonline.com/alliances/99002952/
99002953	LOLLI	Good Ship Lollipop	4	https://crest-tq.eveonline.com/alliances/99002953/
99002957	WORK.	Hostile Work Environment.	4	https://crest-tq.eveonline.com/alliances/99002957/
99002958	USAN	United Systems Alliance Navy	4	https://crest-tq.eveonline.com/alliances/99002958/
99002962	MASK	Maskhal Alliance	4	https://crest-tq.eveonline.com/alliances/99002962/
99002964	-RIM-	Rim Worlds Protectorate	4	https://crest-tq.eveonline.com/alliances/99002964/
99002969	CPOD	Coffee Podded	4	https://crest-tq.eveonline.com/alliances/99002969/
99002972	UCF	U.C.F. Alliance	4	https://crest-tq.eveonline.com/alliances/99002972/
99002974	UDIE	Vendetta Mercenary Group	4	https://crest-tq.eveonline.com/alliances/99002974/
99003003	SAGS	SaGs Alliance	4	https://crest-tq.eveonline.com/alliances/99003003/
99003005	KILLA	E X T E R M I N A T U S	4	https://crest-tq.eveonline.com/alliances/99003005/
99003006	B0T	Brothers of Tangra	4	https://crest-tq.eveonline.com/alliances/99003006/
99003009	DAKKA	Point Blank Alliance	4	https://crest-tq.eveonline.com/alliances/99003009/
99003019	NAKED	Naked Drunk and Immortal	4	https://crest-tq.eveonline.com/alliances/99003019/
99003021	LAST	Last Frontiers	4	https://crest-tq.eveonline.com/alliances/99003021/
99003022	SCA	Systems Commonwealth Alliance	4	https://crest-tq.eveonline.com/alliances/99003022/
99003028	THEAC	Amarrian Commandos	4	https://crest-tq.eveonline.com/alliances/99003028/
99003030	LCA	Libertus Coventu Alliance	4	https://crest-tq.eveonline.com/alliances/99003030/
99003033	QA	Q Alliance	4	https://crest-tq.eveonline.com/alliances/99003033/
99003035	MIGHT	Praetorian Directorate	4	https://crest-tq.eveonline.com/alliances/99003035/
99003038	LDK.	LDK.	4	https://crest-tq.eveonline.com/alliances/99003038/
99003043	DIV0	Divide By Zero Alliance	4	https://crest-tq.eveonline.com/alliances/99003043/
99003045	.UC.	UMBRELLA C0RP0RATI0N	4	https://crest-tq.eveonline.com/alliances/99003045/
99003047	TNOH	The Nightingales of Hades	4	https://crest-tq.eveonline.com/alliances/99003047/
99003049	LOAF	Legion of Alts	4	https://crest-tq.eveonline.com/alliances/99003049/
99003055	-END-	Terrible Ends	4	https://crest-tq.eveonline.com/alliances/99003055/
99003059	MXTXG	Mind the Gap.	4	https://crest-tq.eveonline.com/alliances/99003059/
99003060	URINE	Urine Alliance	4	https://crest-tq.eveonline.com/alliances/99003060/
99003067	PRIM	Project Immersion	4	https://crest-tq.eveonline.com/alliances/99003067/
99003069	PDGM	Paradigm.	4	https://crest-tq.eveonline.com/alliances/99003069/
99003072	OMG	OLD MAN GANG	4	https://crest-tq.eveonline.com/alliances/99003072/
99003074	XS	Xelas Alliance	4	https://crest-tq.eveonline.com/alliances/99003074/
99003077	VAN.	Get in the Van.	4	https://crest-tq.eveonline.com/alliances/99003077/
99003081	FREE.	Freeport Alliance	4	https://crest-tq.eveonline.com/alliances/99003081/
99003087	APRLN	Aprilon Dynasty	4	https://crest-tq.eveonline.com/alliances/99003087/
99003088	-G0T-	Game 0f Tears	4	https://crest-tq.eveonline.com/alliances/99003088/
99003091	AWE	A.W.E.S.O.M.E	4	https://crest-tq.eveonline.com/alliances/99003091/
99003093	VAST	V.A.S.T.	4	https://crest-tq.eveonline.com/alliances/99003093/
99003097	EXEMP	Exemplary Manufacturing L.L.C. Alliance	4	https://crest-tq.eveonline.com/alliances/99003097/
99003106	XSUIT	Unsuitable	4	https://crest-tq.eveonline.com/alliances/99003106/
99003109	WOSY	Wormhole Syndicate	4	https://crest-tq.eveonline.com/alliances/99003109/
99003113	MUSE	Metaphysical Utopian Society Enterprises	4	https://crest-tq.eveonline.com/alliances/99003113/
99003130	DARK	Dark Aether	4	https://crest-tq.eveonline.com/alliances/99003130/
99003134	-CFD-	The Confederation.	4	https://crest-tq.eveonline.com/alliances/99003134/
99003144	WHBOO	Scary Wormhole People	4	https://crest-tq.eveonline.com/alliances/99003144/
99003145	DARMY	DARKSTAR ARMY	4	https://crest-tq.eveonline.com/alliances/99003145/
99003146	S-C	Solo Cartel	4	https://crest-tq.eveonline.com/alliances/99003146/
99003147	UH-OH	Badguys.	4	https://crest-tq.eveonline.com/alliances/99003147/
99003148	ORDEX	Order of the Exalted	4	https://crest-tq.eveonline.com/alliances/99003148/
99003150	-CA-	Codex Aevum	4	https://crest-tq.eveonline.com/alliances/99003150/
99003152	CRITI	Critical-Mass	4	https://crest-tq.eveonline.com/alliances/99003152/
99003154	HLDON	Don't Hold Your Breath	4	https://crest-tq.eveonline.com/alliances/99003154/
99003156	DB	Drank Band	4	https://crest-tq.eveonline.com/alliances/99003156/
99003160	WEGE	West Germany Alliance	4	https://crest-tq.eveonline.com/alliances/99003160/
99003162	JAIL	Deadzoned	4	https://crest-tq.eveonline.com/alliances/99003162/
99003169	SEE-U	Cerberus Unleashed	4	https://crest-tq.eveonline.com/alliances/99003169/
99003170	GNIC	GANOR INC.	4	https://crest-tq.eveonline.com/alliances/99003170/
99003172	M C	M Corp Alliance	4	https://crest-tq.eveonline.com/alliances/99003172/
99003173	MYORE	Peaceful Industrialists	4	https://crest-tq.eveonline.com/alliances/99003173/
99003179	LR	Last Resort.	4	https://crest-tq.eveonline.com/alliances/99003179/
99003180	SETH	Silent Forge	4	https://crest-tq.eveonline.com/alliances/99003180/
99003182	M1C0	M1NER CONFL1CT	4	https://crest-tq.eveonline.com/alliances/99003182/
99003184	NIALA	Nia Lando	4	https://crest-tq.eveonline.com/alliances/99003184/
99003187	DE-NY	Disavowed.	4	https://crest-tq.eveonline.com/alliances/99003187/
99003191	-HRK-	Harkonnen Federation	4	https://crest-tq.eveonline.com/alliances/99003191/
99003199	--I--	Bloody Artillery	4	https://crest-tq.eveonline.com/alliances/99003199/
99003200	BB	Bloodbound.	4	https://crest-tq.eveonline.com/alliances/99003200/
99003205	I-MOC	Interstellar Murder of Crows	4	https://crest-tq.eveonline.com/alliances/99003205/
99003209	MOUSE	Glorious PC Gaming Master Race	4	https://crest-tq.eveonline.com/alliances/99003209/
99003210	NANO	Nanite Empire	4	https://crest-tq.eveonline.com/alliances/99003210/
99003212	RUST	RUST415	4	https://crest-tq.eveonline.com/alliances/99003212/
99003214	BRAVE	Brave Collective	4	https://crest-tq.eveonline.com/alliances/99003214/
99003216	GCLUB	Gentlemen's.Club	4	https://crest-tq.eveonline.com/alliances/99003216/
99003219	S.N.G	Sunshine and Gunpowder	4	https://crest-tq.eveonline.com/alliances/99003219/
99003220	KHAOS	Khaos Legion	4	https://crest-tq.eveonline.com/alliances/99003220/
99003221	HS	Hidden Society	4	https://crest-tq.eveonline.com/alliances/99003221/
99003222	R.F	Red.Faction	4	https://crest-tq.eveonline.com/alliances/99003222/
99003224	EDGE-	EDGE Alliance	4	https://crest-tq.eveonline.com/alliances/99003224/
99003230	ASTKA	Free Republic of Arstozka	4	https://crest-tq.eveonline.com/alliances/99003230/
99003232	C.R.S	Controlled Rage Syndicate	4	https://crest-tq.eveonline.com/alliances/99003232/
99003239	CHEQ	Invisible Exchequer	4	https://crest-tq.eveonline.com/alliances/99003239/
99003244	XPLCT	The Explicit Alliance	4	https://crest-tq.eveonline.com/alliances/99003244/
99003253	RIOT	Renegades Council	4	https://crest-tq.eveonline.com/alliances/99003253/
99003266	TERA	Teralition	4	https://crest-tq.eveonline.com/alliances/99003266/
99003271	D I G	Domino Industrial Group	4	https://crest-tq.eveonline.com/alliances/99003271/
99003281	CSTL	Castrum Imperium	4	https://crest-tq.eveonline.com/alliances/99003281/
99003285	WARD	WarDogs League	4	https://crest-tq.eveonline.com/alliances/99003285/
99003291	FUQYA	Team Amarrica	4	https://crest-tq.eveonline.com/alliances/99003291/
99003294	J-SIG	Upholders	4	https://crest-tq.eveonline.com/alliances/99003294/
99003297	RISKY	Risky Ventures and Associates	4	https://crest-tq.eveonline.com/alliances/99003297/
99003302	-312-	Lucky Town	4	https://crest-tq.eveonline.com/alliances/99003302/
99003307	KOG	KOG Industries	4	https://crest-tq.eveonline.com/alliances/99003307/
99003314	OTFE	Only The Famous	4	https://crest-tq.eveonline.com/alliances/99003314/
99003319	WEWIN	Windowlicking Ninja Turtles	4	https://crest-tq.eveonline.com/alliances/99003319/
99003322	-NEM-	Nemesis Enterprises.	4	https://crest-tq.eveonline.com/alliances/99003322/
99003329	NUBS	Non Unionised Bittervets Society	4	https://crest-tq.eveonline.com/alliances/99003329/
99003340	NOSEE	Now You See me	4	https://crest-tq.eveonline.com/alliances/99003340/
99003343	INCA	Indy Cartel	4	https://crest-tq.eveonline.com/alliances/99003343/
99003344	CRITM	CriticaI Mass	4	https://crest-tq.eveonline.com/alliances/99003344/
99003349	ARCH	Archetype Coalition	4	https://crest-tq.eveonline.com/alliances/99003349/
99003350	NICE	Nova Industrial Coalition Enterprises	4	https://crest-tq.eveonline.com/alliances/99003350/
99003355	IN.SP	Infinity Space.	4	https://crest-tq.eveonline.com/alliances/99003355/
99003359	DKSLZ	Dark Souls.	4	https://crest-tq.eveonline.com/alliances/99003359/
99003367	NEOS	NEOS FLEET	4	https://crest-tq.eveonline.com/alliances/99003367/
99003373	HTFU	Cup Of ConKrete.	4	https://crest-tq.eveonline.com/alliances/99003373/
99003374	GANK	Nomad Legion	4	https://crest-tq.eveonline.com/alliances/99003374/
99003376	ATONE	A T O N E M E N T	4	https://crest-tq.eveonline.com/alliances/99003376/
99003380	PIIS	Astral Peacekeepers	4	https://crest-tq.eveonline.com/alliances/99003380/
99003383	KIR	Khanid Interstellar Reconciliation	4	https://crest-tq.eveonline.com/alliances/99003383/
99003384	DAWWW	Test Friends Please Ignore	4	https://crest-tq.eveonline.com/alliances/99003384/
99003385	TOTA	Total Justification	4	https://crest-tq.eveonline.com/alliances/99003385/
99003393	R H -	Bloodline.	4	https://crest-tq.eveonline.com/alliances/99003393/
99003399	B.O.L	Beware of Local	4	https://crest-tq.eveonline.com/alliances/99003399/
99003403	SUFR	Setting The Universe on Fire	4	https://crest-tq.eveonline.com/alliances/99003403/
99003406	LT	The Lazy Town	4	https://crest-tq.eveonline.com/alliances/99003406/
99003408	PLSUX	HODOR.	4	https://crest-tq.eveonline.com/alliances/99003408/
99003409	CHRIO	Top Men.	4	https://crest-tq.eveonline.com/alliances/99003409/
99003411	RU	Reapers Unity	4	https://crest-tq.eveonline.com/alliances/99003411/
99003420	GRAV	Gravity Constant	4	https://crest-tq.eveonline.com/alliances/99003420/
99003426	DSTNY	Touched by Destiny	4	https://crest-tq.eveonline.com/alliances/99003426/
99003434	REPOS	Repossession	4	https://crest-tq.eveonline.com/alliances/99003434/
99003435	RCOAT	Red Coat Conspiracy	4	https://crest-tq.eveonline.com/alliances/99003435/
99003436	TI	Twilight Imperium	4	https://crest-tq.eveonline.com/alliances/99003436/
99003444	BUS	Short Bus Alliance	4	https://crest-tq.eveonline.com/alliances/99003444/
99003449	TRU	TRUE SITH ALLIANCE	4	https://crest-tq.eveonline.com/alliances/99003449/
99003458	TTC	The Tsunami Collective	4	https://crest-tq.eveonline.com/alliances/99003458/
99003460	YOINK	Snatch Victory	4	https://crest-tq.eveonline.com/alliances/99003460/
99003465	-VE-	Valhalla Empire	4	https://crest-tq.eveonline.com/alliances/99003465/
99003470	EVRY	EveryoneVersusEveryone.com.	4	https://crest-tq.eveonline.com/alliances/99003470/
99003471	OUT	Disorganized Outcasts	4	https://crest-tq.eveonline.com/alliances/99003471/
99003477	WMTR	WINMATAR.	4	https://crest-tq.eveonline.com/alliances/99003477/
99003478	J0KER	The Harlequin's	4	https://crest-tq.eveonline.com/alliances/99003478/
99003479	SBU	Sov Warfare Appreciation Station	4	https://crest-tq.eveonline.com/alliances/99003479/
99003482	MTTIA	Metatron Inc. Alliance	4	https://crest-tq.eveonline.com/alliances/99003482/
99003484	ME-SE	Method Alliance	4	https://crest-tq.eveonline.com/alliances/99003484/
99003486	TIGGH	the Independent Gaming Group	4	https://crest-tq.eveonline.com/alliances/99003486/
99003490	UNPRO	Strictly Unprofessional	4	https://crest-tq.eveonline.com/alliances/99003490/
99003497	XBIO	Axion Bionics	4	https://crest-tq.eveonline.com/alliances/99003497/
99003498	FML	Blackguard Mercenaries	4	https://crest-tq.eveonline.com/alliances/99003498/
99003499	CROSS	crawl on Serpens	4	https://crest-tq.eveonline.com/alliances/99003499/
99003500	X.W.X	Shadow of xXDEATHXx	4	https://crest-tq.eveonline.com/alliances/99003500/
99003501	-NISI	Meliora Cogito	4	https://crest-tq.eveonline.com/alliances/99003501/
99003503	H3R0.	Heroes and Villains.	4	https://crest-tq.eveonline.com/alliances/99003503/
99003505	JEDI	Jedi Path	4	https://crest-tq.eveonline.com/alliances/99003505/
99003506	G-F-A	Gun Fun Alliance	4	https://crest-tq.eveonline.com/alliances/99003506/
99003510	-SOD-	Spears of Destiny	4	https://crest-tq.eveonline.com/alliances/99003510/
99003517	-9.9	Pandamimic Legion	4	https://crest-tq.eveonline.com/alliances/99003517/
99003520	I.M.	Interstellar Mayhem	4	https://crest-tq.eveonline.com/alliances/99003520/
99003527	RTD	Rolling The Dice	4	https://crest-tq.eveonline.com/alliances/99003527/
99003535	EQNOX	Winter Solstice.	4	https://crest-tq.eveonline.com/alliances/99003535/
99003538	VOTE	Vote Steve Ronuken for CSM	4	https://crest-tq.eveonline.com/alliances/99003538/
99003539	L0G0F	League 0f Grumpy 0ld Farts	4	https://crest-tq.eveonline.com/alliances/99003539/
99003540	WHAT	What Was That.	4	https://crest-tq.eveonline.com/alliances/99003540/
99003541	NAGA.	Nerfed Alliance Go Away	4	https://crest-tq.eveonline.com/alliances/99003541/
99003548	DHARM	Dissonant Harmonics	4	https://crest-tq.eveonline.com/alliances/99003548/
99003549	PBLRD	Greater Western Co-Prosperity Sphere	4	https://crest-tq.eveonline.com/alliances/99003549/
99003555	ARS3	Anti-Social Misfits	4	https://crest-tq.eveonline.com/alliances/99003555/
99003557	-LSH-	LowSechnaya Sholupen	4	https://crest-tq.eveonline.com/alliances/99003557/
99003558	ZEBOV	UNITATO SQUAD	4	https://crest-tq.eveonline.com/alliances/99003558/
99003560	THG	The Honor Guard	4	https://crest-tq.eveonline.com/alliances/99003560/
99003564	.FFA.	Fire Fly Alliance	4	https://crest-tq.eveonline.com/alliances/99003564/
99003565	SCRWD	Unleashing Chaos	4	https://crest-tq.eveonline.com/alliances/99003565/
99003566	ED	Ethereal Dawn	4	https://crest-tq.eveonline.com/alliances/99003566/
99003568	OAK	Order of Allied Knights	4	https://crest-tq.eveonline.com/alliances/99003568/
99003574	U.SP	Ultimate Space	4	https://crest-tq.eveonline.com/alliances/99003574/
99003575	UCOL	Unorthodox Collective	4	https://crest-tq.eveonline.com/alliances/99003575/
99003577	T.S.I	The.Spanish.Inquisition	4	https://crest-tq.eveonline.com/alliances/99003577/
99003579	N0MAD	WAFFLES.	4	https://crest-tq.eveonline.com/alliances/99003579/
99003581	FRT	Fraternity.	4	https://crest-tq.eveonline.com/alliances/99003581/
99003588	-SSS-	The Suicidal Spaceship Squadron	4	https://crest-tq.eveonline.com/alliances/99003588/
99003589	LYCOS	Lycosidae Infernalis	4	https://crest-tq.eveonline.com/alliances/99003589/
99003590	D-DOT	Disband.	4	https://crest-tq.eveonline.com/alliances/99003590/
99003595	UI	United Interests	4	https://crest-tq.eveonline.com/alliances/99003595/
99003598	KK	Kamikazee Killers	4	https://crest-tq.eveonline.com/alliances/99003598/
99003602	DVD	Drunk Void Drifters	4	https://crest-tq.eveonline.com/alliances/99003602/
99003615	B.R.O	Brothers Of The Dark Sun	4	https://crest-tq.eveonline.com/alliances/99003615/
99003616	HGB	Hemoglobin.	4	https://crest-tq.eveonline.com/alliances/99003616/
99003619	EMPX	Insidious Auxiliaries	4	https://crest-tq.eveonline.com/alliances/99003619/
99003621	CRYPT	Cryptic Immortals	4	https://crest-tq.eveonline.com/alliances/99003621/
99003624	FEC	Fortis Et Certus	4	https://crest-tq.eveonline.com/alliances/99003624/
99003627	THE D	The Defiant Alliance	4	https://crest-tq.eveonline.com/alliances/99003627/
99003633	AMGIN	The Amalgamation Initiative	4	https://crest-tq.eveonline.com/alliances/99003633/
99003634	KRAZE	Forsaken Asylum	4	https://crest-tq.eveonline.com/alliances/99003634/
99003637	D.S.S	Dead Space Syndicate	4	https://crest-tq.eveonline.com/alliances/99003637/
99003638	-EH-	Sorry We're In Your Space Eh	4	https://crest-tq.eveonline.com/alliances/99003638/
99003640	INSP	infinita sapientia	4	https://crest-tq.eveonline.com/alliances/99003640/
99003641	KINKY	Dirty Bastards.	4	https://crest-tq.eveonline.com/alliances/99003641/
99003650	LEX	Limited Expectations	4	https://crest-tq.eveonline.com/alliances/99003650/
99003654	HSA	Hybrid-Synergy Authority	4	https://crest-tq.eveonline.com/alliances/99003654/
99003656	RIP	Rapidus Incitus Pactum	4	https://crest-tq.eveonline.com/alliances/99003656/
99003658	AS13	Advanced Space Technologies 2013	4	https://crest-tq.eveonline.com/alliances/99003658/
99003663	ANGRY	Angry Furries Brotherhood	4	https://crest-tq.eveonline.com/alliances/99003663/
99003667	-FAR-	Fifth Ancient Race	4	https://crest-tq.eveonline.com/alliances/99003667/
99003673	BORGA	BORG Alliance	4	https://crest-tq.eveonline.com/alliances/99003673/
99003674	SQEEK	Frankenmouse	4	https://crest-tq.eveonline.com/alliances/99003674/
99003682	UFORF	UFO RF Alliance	4	https://crest-tq.eveonline.com/alliances/99003682/
99003685	-LABS	Special Circumstances Alliance	4	https://crest-tq.eveonline.com/alliances/99003685/
99003686	NAQS	Naquatech Syndicate	4	https://crest-tq.eveonline.com/alliances/99003686/
99003695	GET J	Get Jiggy Wit It	4	https://crest-tq.eveonline.com/alliances/99003695/
99003699	MOROS	Cynosural Field Theory.	4	https://crest-tq.eveonline.com/alliances/99003699/
99003700	DURA	DURA LEXX	4	https://crest-tq.eveonline.com/alliances/99003700/
99003702	XPOD	Expand or Die	4	https://crest-tq.eveonline.com/alliances/99003702/
99003706	TIT	Two Inch Terror	4	https://crest-tq.eveonline.com/alliances/99003706/
99003707	DQ	Darling and Quafe	4	https://crest-tq.eveonline.com/alliances/99003707/
99003708	WBTC	Waffle Batter Trade Consortium	4	https://crest-tq.eveonline.com/alliances/99003708/
99003710	IPLEX	Intergalatic Complex Specialist	4	https://crest-tq.eveonline.com/alliances/99003710/
99003711	CORPU	Corpus not Delicti	4	https://crest-tq.eveonline.com/alliances/99003711/
99003713	WALTS	WALLTREIPERS SHADOW	4	https://crest-tq.eveonline.com/alliances/99003713/
99003714	EDEN	Imperium Eden	4	https://crest-tq.eveonline.com/alliances/99003714/
99003722	CORD	Advent of Fate	4	https://crest-tq.eveonline.com/alliances/99003722/
99003726	-AXI-	Axiom Initiative	4	https://crest-tq.eveonline.com/alliances/99003726/
99003728	3I4	Indecent-Exposure	4	https://crest-tq.eveonline.com/alliances/99003728/
99003730	ACART	A Cartel	4	https://crest-tq.eveonline.com/alliances/99003730/
99003731	COFS	Cutthroat Old Farts	4	https://crest-tq.eveonline.com/alliances/99003731/
99003732	CFW	conceptual framework	4	https://crest-tq.eveonline.com/alliances/99003732/
99003733	AWOL	eternals allies	4	https://crest-tq.eveonline.com/alliances/99003733/
99003734	HATO	Heimatar Alliance Treaty Organization	4	https://crest-tq.eveonline.com/alliances/99003734/
99003739	ADCT	AddictClan	4	https://crest-tq.eveonline.com/alliances/99003739/
99003742	-TCE-	The Crystal Empire.	4	https://crest-tq.eveonline.com/alliances/99003742/
99003744	GAWWY	Garys Most Noble Army of Third Place Mediocrity	4	https://crest-tq.eveonline.com/alliances/99003744/
99003745	D-T	Dei-Telum	4	https://crest-tq.eveonline.com/alliances/99003745/
99003746	BVHI	Blohm and Voss Shipyards Alliance	4	https://crest-tq.eveonline.com/alliances/99003746/
99003747	FNMU	Federation Navales et Minieres unies	4	https://crest-tq.eveonline.com/alliances/99003747/
99003751	-DC-	THE DARK CHAPTER	4	https://crest-tq.eveonline.com/alliances/99003751/
99003752	REBEL	Rebel Coalition	4	https://crest-tq.eveonline.com/alliances/99003752/
99003753	VD	Violent Declaration	4	https://crest-tq.eveonline.com/alliances/99003753/
99003759	NZERO	Neutral Zero	4	https://crest-tq.eveonline.com/alliances/99003759/
99003761	NEBCO	New Eden Brewing Co.	4	https://crest-tq.eveonline.com/alliances/99003761/
99003763	-BRO	The Pursuit of Happiness	4	https://crest-tq.eveonline.com/alliances/99003763/
99003764	PFV.	Proficiency V.	4	https://crest-tq.eveonline.com/alliances/99003764/
99003765	F-E	The Forsaken Empire	4	https://crest-tq.eveonline.com/alliances/99003765/
99003767	GASP	War Crimes Social Club	4	https://crest-tq.eveonline.com/alliances/99003767/
99003773	187	Infinite Anarchy	4	https://crest-tq.eveonline.com/alliances/99003773/
99003775	CYMRU	The Coelbren Collective	4	https://crest-tq.eveonline.com/alliances/99003775/
99003778	HIJAK	Somalia.	4	https://crest-tq.eveonline.com/alliances/99003778/
99003785	HVN	Haven.	4	https://crest-tq.eveonline.com/alliances/99003785/
99003788	ALLU	Zen Utyu Renmei	4	https://crest-tq.eveonline.com/alliances/99003788/
99003790	IOP	Illusion of Peace	4	https://crest-tq.eveonline.com/alliances/99003790/
99003792	PSYCO	Shattered Sanity Alliance	4	https://crest-tq.eveonline.com/alliances/99003792/
99003796	GOB	Game Of Bears	4	https://crest-tq.eveonline.com/alliances/99003796/
99003797	FUDA	Fuda-se Tudo	5	https://crest-tq.eveonline.com/alliances/99003797/
99003799	LOST	Transmission Lost	5	https://crest-tq.eveonline.com/alliances/99003799/
99003801	OHSWB	OuterHeaven-SoldiersWithoutBorders	5	https://crest-tq.eveonline.com/alliances/99003801/
99003805	ALTS	Alternate Allegiance	5	https://crest-tq.eveonline.com/alliances/99003805/
99003806	FEIGN	Feign Disorder	5	https://crest-tq.eveonline.com/alliances/99003806/
99003811	MEOW	Schrodinger Intergalactic Kitten Hoarders	5	https://crest-tq.eveonline.com/alliances/99003811/
99003817	INTER	Intercom.	5	https://crest-tq.eveonline.com/alliances/99003817/
99003821	NEIC	New Eden Industrialists Conglomerate	5	https://crest-tq.eveonline.com/alliances/99003821/
99003822	PIG	Pigships	5	https://crest-tq.eveonline.com/alliances/99003822/
99003824	QCA	Quantum Corp Alliance	5	https://crest-tq.eveonline.com/alliances/99003824/
99003827	TOBED	To Be Determined Alliance	5	https://crest-tq.eveonline.com/alliances/99003827/
99003834	BEARS	Chaotic Carebears	5	https://crest-tq.eveonline.com/alliances/99003834/
99003838	REQ	Requiem Eternal	5	https://crest-tq.eveonline.com/alliances/99003838/
99003847	SECAA	Sectio Aurea.	5	https://crest-tq.eveonline.com/alliances/99003847/
99003858	KRACK	McKRACKIN Inspectors	5	https://crest-tq.eveonline.com/alliances/99003858/
99003863	N0FUX	Almost Awesome.	5	https://crest-tq.eveonline.com/alliances/99003863/
99003864	UE	Unified-Empire	5	https://crest-tq.eveonline.com/alliances/99003864/
99003869	GIT-I	Git-R-Done Inc	5	https://crest-tq.eveonline.com/alliances/99003869/
99003870	-CPU-	P O W E R G R I D	5	https://crest-tq.eveonline.com/alliances/99003870/
99003874	TNUC	The Resurrected.	5	https://crest-tq.eveonline.com/alliances/99003874/
99003882	X.O.D	Expand or Die.	5	https://crest-tq.eveonline.com/alliances/99003882/
99003884	OHFUC	Retribution.	5	https://crest-tq.eveonline.com/alliances/99003884/
99003889	EXP-A	Explicit Associates	5	https://crest-tq.eveonline.com/alliances/99003889/
99003890	HOLJM	Random-Violence	5	https://crest-tq.eveonline.com/alliances/99003890/
99003891	IO	Infernal Order	5	https://crest-tq.eveonline.com/alliances/99003891/
99003894	TBC	The Bastard Cartel	5	https://crest-tq.eveonline.com/alliances/99003894/
99003896	WHITE	Russian Imperial Fleet	5	https://crest-tq.eveonline.com/alliances/99003896/
99003898	CBA	MOSTLY AFK	5	https://crest-tq.eveonline.com/alliances/99003898/
99003902	SR	The SoulRippers	5	https://crest-tq.eveonline.com/alliances/99003902/
99003903	CORUS	Corus Conglomerate	5	https://crest-tq.eveonline.com/alliances/99003903/
99003907	LEGUS	LEGIO SECUNDUS	5	https://crest-tq.eveonline.com/alliances/99003907/
99003911	CXCI	Legio immortales CXCI	5	https://crest-tq.eveonline.com/alliances/99003911/
99003912	D6	LOADED-DICE	5	https://crest-tq.eveonline.com/alliances/99003912/
99003913	IW	Immortals of War	5	https://crest-tq.eveonline.com/alliances/99003913/
99003915	TWSA	Alliance of Twilights	5	https://crest-tq.eveonline.com/alliances/99003915/
99003916	VIRUS	Viral Society	5	https://crest-tq.eveonline.com/alliances/99003916/
99003918	FLEET	Almost Broken	5	https://crest-tq.eveonline.com/alliances/99003918/
99003919	THC4U	420 Chronicles of EvE	5	https://crest-tq.eveonline.com/alliances/99003919/
99003921	GOMS	Guardians of the Morrigan	5	https://crest-tq.eveonline.com/alliances/99003921/
99003922	SPERG	Special Planetary Emergency Response Group	5	https://crest-tq.eveonline.com/alliances/99003922/
99003923	LUNAR	Imperium of Rising Luna	5	https://crest-tq.eveonline.com/alliances/99003923/
99003928	ORE	OREsterity	5	https://crest-tq.eveonline.com/alliances/99003928/
99003930	H-U-H	No Response	5	https://crest-tq.eveonline.com/alliances/99003930/
99003933	DU	Duckling Union	5	https://crest-tq.eveonline.com/alliances/99003933/
99003934	AF	Aegis Federation	5	https://crest-tq.eveonline.com/alliances/99003934/
99003936	ORNJ	ORANJE NV	5	https://crest-tq.eveonline.com/alliances/99003936/
99003940	M8WTF	That Escalated Quickly.	5	https://crest-tq.eveonline.com/alliances/99003940/
99003943	LIGA	Liga Hanseatica	5	https://crest-tq.eveonline.com/alliances/99003943/
99003947	YOLO	Grand Sky Wizards	5	https://crest-tq.eveonline.com/alliances/99003947/
99003949	THUMP	This is SPARTIDE	5	https://crest-tq.eveonline.com/alliances/99003949/
99003952	UNQ	Unique.	5	https://crest-tq.eveonline.com/alliances/99003952/
99003953	KHSSS	Imperium Mordor	5	https://crest-tq.eveonline.com/alliances/99003953/
99003956	PUSHX	Push Interstellar Network	5	https://crest-tq.eveonline.com/alliances/99003956/
99003958	.EBA.	EM-B.A.S.H.	5	https://crest-tq.eveonline.com/alliances/99003958/
99003961	SUSU	Unmentionables	5	https://crest-tq.eveonline.com/alliances/99003961/
99003964	IIIII	Ideal Society	5	https://crest-tq.eveonline.com/alliances/99003964/
99003972	DH-I	Darkhorse Industries	5	https://crest-tq.eveonline.com/alliances/99003972/
99003973	TCS	The Crimson Society	5	https://crest-tq.eveonline.com/alliances/99003973/
99003975	BEAN	Shadow of the Hegemon	5	https://crest-tq.eveonline.com/alliances/99003975/
99003978	TOWER	The Crimson Tower	5	https://crest-tq.eveonline.com/alliances/99003978/
99003983	LGC	Legion Galactic Council	5	https://crest-tq.eveonline.com/alliances/99003983/
99003991	3BMF	3 Black Men Fornicating	5	https://crest-tq.eveonline.com/alliances/99003991/
99003995	IGC	Invidia Gloriae Comes	5	https://crest-tq.eveonline.com/alliances/99003995/
99004000	-LEG.	Legion's.	5	https://crest-tq.eveonline.com/alliances/99004000/
99004002	NEMFA	New Eden's Misfits Alliance	5	https://crest-tq.eveonline.com/alliances/99004002/
99004004	NVKU	Novaku Alliance	5	https://crest-tq.eveonline.com/alliances/99004004/
99004009	.DACO	Dacian Dracos	5	https://crest-tq.eveonline.com/alliances/99004009/
99004012	MON	MONTAN UNION	5	https://crest-tq.eveonline.com/alliances/99004012/
99004013	ODIN	Odin's Call	5	https://crest-tq.eveonline.com/alliances/99004013/
99004017	TWHA	The Whale Hunters Association	5	https://crest-tq.eveonline.com/alliances/99004017/
99004018	-NRA-	Night Raven Alliance	5	https://crest-tq.eveonline.com/alliances/99004018/
99004019	CR0WZ	The CORVOS	5	https://crest-tq.eveonline.com/alliances/99004019/
99004031	ASF	Angel Swarm Federation	5	https://crest-tq.eveonline.com/alliances/99004031/
99004042	VUDU	Voodoo Conglomerate	5	https://crest-tq.eveonline.com/alliances/99004042/
99004046	DOEA	Defenders Of EVE Alliance	5	https://crest-tq.eveonline.com/alliances/99004046/
99004049	RULEM	The Ruling Empire	5	https://crest-tq.eveonline.com/alliances/99004049/
99004050	BMS	Black Mesa Security	5	https://crest-tq.eveonline.com/alliances/99004050/
99004057	JAMM	Jammin Mad	5	https://crest-tq.eveonline.com/alliances/99004057/
99004068	S3XY	Slightly Sexual	5	https://crest-tq.eveonline.com/alliances/99004068/
99004071	MRKZ	Merkez.	5	https://crest-tq.eveonline.com/alliances/99004071/
99004074	ATHN	Athena Alliance	5	https://crest-tq.eveonline.com/alliances/99004074/
99004080	MITG	Monyusaiya Industry Trade Group	5	https://crest-tq.eveonline.com/alliances/99004080/
99004081	DACUS	Dacic Wolves	5	https://crest-tq.eveonline.com/alliances/99004081/
99004084	NO-GF	Plexodus	5	https://crest-tq.eveonline.com/alliances/99004084/
99004091	FRODA	Frozen Dawn Alliance	5	https://crest-tq.eveonline.com/alliances/99004091/
99004093	THEN	The North is Coming	5	https://crest-tq.eveonline.com/alliances/99004093/
99004095	TGOP	The Gates Of Purgatory	5	https://crest-tq.eveonline.com/alliances/99004095/
99004098	PURR	Sex Panther.	5	https://crest-tq.eveonline.com/alliances/99004098/
99004103	INFAM	I.N.F.A.M.Y	5	https://crest-tq.eveonline.com/alliances/99004103/
99004107	GNCRI	Gone Critical	5	https://crest-tq.eveonline.com/alliances/99004107/
99004108	TGKA	The Great Knights Alliance	5	https://crest-tq.eveonline.com/alliances/99004108/
99004116	W4RP	Warped Intentions	5	https://crest-tq.eveonline.com/alliances/99004116/
99004122	FR	Foundations Rising	5	https://crest-tq.eveonline.com/alliances/99004122/
99004125	HECON	Heiian Conglomerate	5	https://crest-tq.eveonline.com/alliances/99004125/
99004126	FWALL	FACTION WARFARE ALLIANCE	5	https://crest-tq.eveonline.com/alliances/99004126/
99004127	BLOC	The Bloc	5	https://crest-tq.eveonline.com/alliances/99004127/
99004128	CARGO	Cargo Cult.	5	https://crest-tq.eveonline.com/alliances/99004128/
99004129	HORI	Horizon Industries Ltd.	5	https://crest-tq.eveonline.com/alliances/99004129/
99004132	B4DIN	B4D W0LF INDUSTRIES	5	https://crest-tq.eveonline.com/alliances/99004132/
99004133	TOON	TOONS OF EVE	5	https://crest-tq.eveonline.com/alliances/99004133/
99004136	DV	Dangerous Voltage	5	https://crest-tq.eveonline.com/alliances/99004136/
99004142	LAW	Lawful Rebellion	5	https://crest-tq.eveonline.com/alliances/99004142/
99004151	ETHC	Ethical Carnage	5	https://crest-tq.eveonline.com/alliances/99004151/
99004157	MOS	Margin of Silence	5	https://crest-tq.eveonline.com/alliances/99004157/
99004158	GUARD	Guarded Sanity of a Loon Alliance	5	https://crest-tq.eveonline.com/alliances/99004158/
99004159	FEMP	The Fearless Empire	5	https://crest-tq.eveonline.com/alliances/99004159/
99004165	-IVC-	xX In Varietate Concordia Xx	5	https://crest-tq.eveonline.com/alliances/99004165/
99004166	SAXN	Saxnot Alliance	5	https://crest-tq.eveonline.com/alliances/99004166/
99004170	IGA	Internet Guardians	5	https://crest-tq.eveonline.com/alliances/99004170/
99004172	KANE	Kanen Federation	5	https://crest-tq.eveonline.com/alliances/99004172/
99004173	B-A-T	Bas ar Teagmhail	5	https://crest-tq.eveonline.com/alliances/99004173/
99004177	SEAS	Shadow's Edge Alliance	5	https://crest-tq.eveonline.com/alliances/99004177/
99004180	CNC	Caldari Naval Command	5	https://crest-tq.eveonline.com/alliances/99004180/
99004181	VOTP	Vanguard of the Phoenix	5	https://crest-tq.eveonline.com/alliances/99004181/
99004184	-IFC-	Infernal Creations.	5	https://crest-tq.eveonline.com/alliances/99004184/
99004185	B	Barbarians Alliance	5	https://crest-tq.eveonline.com/alliances/99004185/
99004188	NDRAN	Ndrangheta.	5	https://crest-tq.eveonline.com/alliances/99004188/
99004191	ABSTD	Associated Basterds	5	https://crest-tq.eveonline.com/alliances/99004191/
99004196	ABAIT	Allibaitors	5	https://crest-tq.eveonline.com/alliances/99004196/
99004202	ISD	Infinite Spiral Drillings	5	https://crest-tq.eveonline.com/alliances/99004202/
99004208	MZR	Mozart Inc Alliance	5	https://crest-tq.eveonline.com/alliances/99004208/
99004211	YES	Affirmative.	5	https://crest-tq.eveonline.com/alliances/99004211/
99004213	ARCON	Arclight Consolidated	5	https://crest-tq.eveonline.com/alliances/99004213/
99004217	FRLRN	Forlorn.	5	https://crest-tq.eveonline.com/alliances/99004217/
99004218	SPIRT	Galaxy Spiritus	5	https://crest-tq.eveonline.com/alliances/99004218/
99004220	WEYR	Weyr Syndicate	5	https://crest-tq.eveonline.com/alliances/99004220/
99004222	U-WOT	404 Alliance Not Found	5	https://crest-tq.eveonline.com/alliances/99004222/
99004226	TUI	The Unknown Ideal	5	https://crest-tq.eveonline.com/alliances/99004226/
99004227	JMP-N	Did he say Jump	5	https://crest-tq.eveonline.com/alliances/99004227/
99004230	SUGAR	Sugar.	5	https://crest-tq.eveonline.com/alliances/99004230/
99004238	MAGI.	MAGI.	5	https://crest-tq.eveonline.com/alliances/99004238/
99004240	Y NOT	Repeat 0ffenders	5	https://crest-tq.eveonline.com/alliances/99004240/
99004245	-N2L-	Nothing2Lose.	5	https://crest-tq.eveonline.com/alliances/99004245/
99004248	BTL	Better Than Life.	5	https://crest-tq.eveonline.com/alliances/99004248/
99004250	V0ID	The Void Collective	5	https://crest-tq.eveonline.com/alliances/99004250/
99004252	EXP	E X P L O S I O N	5	https://crest-tq.eveonline.com/alliances/99004252/
99004253	PRO 1	Protocol One	5	https://crest-tq.eveonline.com/alliances/99004253/
99004254	SOTL	The Sword of the Lord	5	https://crest-tq.eveonline.com/alliances/99004254/
99004259	NUMB	nUMBRELLA Corporation	5	https://crest-tq.eveonline.com/alliances/99004259/
99004264	PLA	People's Liberation Alliance	5	https://crest-tq.eveonline.com/alliances/99004264/
99004269	C-FOA	Caldari Fleet and Operations Academy	5	https://crest-tq.eveonline.com/alliances/99004269/
99004271	N.L.D	Koninkrijk der Nederlanden	5	https://crest-tq.eveonline.com/alliances/99004271/
99004275	R-SYN	Ruined Syndicate	5	https://crest-tq.eveonline.com/alliances/99004275/
99004276	D3ATH	The Afterlife.	5	https://crest-tq.eveonline.com/alliances/99004276/
99004280	DCGL	The Deezl Conglomerate	5	https://crest-tq.eveonline.com/alliances/99004280/
99004282	DC0RE	The. Foundation.	5	https://crest-tq.eveonline.com/alliances/99004282/
99004288	VOTL.	Vicious OuTLaw	5	https://crest-tq.eveonline.com/alliances/99004288/
99004289	SHO	SHOVEL.OF.DEATH	5	https://crest-tq.eveonline.com/alliances/99004289/
99004295	-ABA-	A Band Apart.	5	https://crest-tq.eveonline.com/alliances/99004295/
99004300	THNGY	The Camel Empire	5	https://crest-tq.eveonline.com/alliances/99004300/
99004301	RSA	Reckoning Star Alliance	5	https://crest-tq.eveonline.com/alliances/99004301/
99004302	-EL-	Everyb0dy Lies	5	https://crest-tq.eveonline.com/alliances/99004302/
99004305	AMG	Amarr Mercenary Group	5	https://crest-tq.eveonline.com/alliances/99004305/
99004308	HL	Habe Lusus	5	https://crest-tq.eveonline.com/alliances/99004308/
99004309	POLAR	borealis	5	https://crest-tq.eveonline.com/alliances/99004309/
99004312	EOTE	Enemies Of The Empire	5	https://crest-tq.eveonline.com/alliances/99004312/
99004314	PAXBI	Pax Bisonica	5	https://crest-tq.eveonline.com/alliances/99004314/
99004316	.BHA.	Bleak Horizon Alliance.	5	https://crest-tq.eveonline.com/alliances/99004316/
99004318	WIYW	What's In Your Wallet	5	https://crest-tq.eveonline.com/alliances/99004318/
99004321	FMM	Full Metal Militia	5	https://crest-tq.eveonline.com/alliances/99004321/
99004324	FU8AR	Federal United Battalion of Armed Renegades	5	https://crest-tq.eveonline.com/alliances/99004324/
99004325	AS	Astroya Conglomerate.	5	https://crest-tq.eveonline.com/alliances/99004325/
99004327	ICHOR	Terranichor	5	https://crest-tq.eveonline.com/alliances/99004327/
99004328	RATHR	I'd Rather Be Roaming	5	https://crest-tq.eveonline.com/alliances/99004328/
99004332	QCC	Queen Capital Construction	5	https://crest-tq.eveonline.com/alliances/99004332/
99004334	B.0.B	BAND. 0F. BR0THERS	5	https://crest-tq.eveonline.com/alliances/99004334/
99004336	SECT	television sect	5	https://crest-tq.eveonline.com/alliances/99004336/
99004340	QUAMN	Quam'Nocent	5	https://crest-tq.eveonline.com/alliances/99004340/
99004344	HOLE	Hole Control	5	https://crest-tq.eveonline.com/alliances/99004344/
99004345	-CRT-	Creative Tendencies	5	https://crest-tq.eveonline.com/alliances/99004345/
99004347	T.W.	The Wardens	5	https://crest-tq.eveonline.com/alliances/99004347/
99004352	JLS	Justice League of Space	5	https://crest-tq.eveonline.com/alliances/99004352/
99004353	IBOTA	Bot Protection Alliance	5	https://crest-tq.eveonline.com/alliances/99004353/
99004355	BULLI	Internet Space Bullies Alliance	5	https://crest-tq.eveonline.com/alliances/99004355/
99004357	TUSK.	The Tuskers Co.	5	https://crest-tq.eveonline.com/alliances/99004357/
99004360	SEDWN	Second-Dawn	5	https://crest-tq.eveonline.com/alliances/99004360/
99004364	EXIT	Exit Strategy..	5	https://crest-tq.eveonline.com/alliances/99004364/
99004367	STARK	Stark Enterprises	5	https://crest-tq.eveonline.com/alliances/99004367/
99004368	-L0W-	Low-Class	5	https://crest-tq.eveonline.com/alliances/99004368/
99004374	PRO	Prometheus Allegiance	5	https://crest-tq.eveonline.com/alliances/99004374/
99004383	.PEST	The Pestilent Legion	5	https://crest-tq.eveonline.com/alliances/99004383/
99004384	SOF	Special Operators Federation Alliance	5	https://crest-tq.eveonline.com/alliances/99004384/
99004386	3NVY	Lasers Are Magic	5	https://crest-tq.eveonline.com/alliances/99004386/
99004389	-BAD	Bad-Intentions	5	https://crest-tq.eveonline.com/alliances/99004389/
99004390	D	Delta Alliance	5	https://crest-tq.eveonline.com/alliances/99004390/
99004391	DN	Dagger of the Nott	5	https://crest-tq.eveonline.com/alliances/99004391/
99004392	MARE	The Morrigan Irregulars	5	https://crest-tq.eveonline.com/alliances/99004392/
99004393	BIIND	Black Irish Industries	5	https://crest-tq.eveonline.com/alliances/99004393/
99004394	BLIIR	Black Irish.	5	https://crest-tq.eveonline.com/alliances/99004394/
99004396	MAMMA	Maggie's Magical Malliance	5	https://crest-tq.eveonline.com/alliances/99004396/
99004397	HLMAR	FEARLESS.	5	https://crest-tq.eveonline.com/alliances/99004397/
99004404	STLCO	Steel Conglomerate	5	https://crest-tq.eveonline.com/alliances/99004404/
99004405	DOGE	Real Alliance Such Relevance	5	https://crest-tq.eveonline.com/alliances/99004405/
99004410	NJOY	The Crystal Palace	5	https://crest-tq.eveonline.com/alliances/99004410/
99004414	SHU L	Shu long Industry	5	https://crest-tq.eveonline.com/alliances/99004414/
99004415	W.RAT	War Rats	5	https://crest-tq.eveonline.com/alliances/99004415/
99004417	EFORT	Effort.	5	https://crest-tq.eveonline.com/alliances/99004417/
99004418	-Y-	YouWhat.	5	https://crest-tq.eveonline.com/alliances/99004418/
99004423	DOTA	We need wards.	5	https://crest-tq.eveonline.com/alliances/99004423/
99004425	BASTN	The Bastion	5	https://crest-tq.eveonline.com/alliances/99004425/
99004428	SHHH.	Snuggle Struggle.	5	https://crest-tq.eveonline.com/alliances/99004428/
99004430	DBOM	Disorganized Band of Misfits	5	https://crest-tq.eveonline.com/alliances/99004430/
99004431	YGS	You Got Served.	5	https://crest-tq.eveonline.com/alliances/99004431/
99004432	A-FED	Aura Federation	5	https://crest-tq.eveonline.com/alliances/99004432/
99004448	METAL	Morbid Atrocity	5	https://crest-tq.eveonline.com/alliances/99004448/
99004449	UB3R	Ub3rus Universal	5	https://crest-tq.eveonline.com/alliances/99004449/
99004450	ATRAP	Asymmetric Threat Response and Analysis Program	5	https://crest-tq.eveonline.com/alliances/99004450/
99004457	ATP	Autopilot-Engaged	5	https://crest-tq.eveonline.com/alliances/99004457/
99004460	BAN	Bankers Haulers and Brawlers	5	https://crest-tq.eveonline.com/alliances/99004460/
99004461	420DD	Dank Dunks	5	https://crest-tq.eveonline.com/alliances/99004461/
99004462	54THR	The 54th Reich	5	https://crest-tq.eveonline.com/alliances/99004462/
99004463	RATIO	Ultima Ratio.	5	https://crest-tq.eveonline.com/alliances/99004463/
99004466	TSU	The Shadow Ultimatum	5	https://crest-tq.eveonline.com/alliances/99004466/
99004470	BURI.	STEALTHBENDERS.	5	https://crest-tq.eveonline.com/alliances/99004470/
99004471	OT	Ocularis Terribus	5	https://crest-tq.eveonline.com/alliances/99004471/
99004472	-MVP-	Most Valuable Player	5	https://crest-tq.eveonline.com/alliances/99004472/
99004473	ICEVE	Industrial Conglomerate of EVE	5	https://crest-tq.eveonline.com/alliances/99004473/
99004477	CHELO	Chelonaphobia	5	https://crest-tq.eveonline.com/alliances/99004477/
99004480	DRF	Dream Fleet	5	https://crest-tq.eveonline.com/alliances/99004480/
99004481	WU-FF	Worlds United Fedo Force	5	https://crest-tq.eveonline.com/alliances/99004481/
99004485	MNTR	You Are Being Monitored	5	https://crest-tq.eveonline.com/alliances/99004485/
99004486	SMP	Space Monkey Protectorate	5	https://crest-tq.eveonline.com/alliances/99004486/
99004488	BF4LF	Friends of Brave	5	https://crest-tq.eveonline.com/alliances/99004488/
99004491	PRILA	Private Label	5	https://crest-tq.eveonline.com/alliances/99004491/
99004492	CYB	Cybran Nation Alliance	5	https://crest-tq.eveonline.com/alliances/99004492/
99004502	ANT	Space-Ants	5	https://crest-tq.eveonline.com/alliances/99004502/
99004503	BLAST	Forged of Fire	5	https://crest-tq.eveonline.com/alliances/99004503/
99004506	GBA	Grizly Bears Alliance	5	https://crest-tq.eveonline.com/alliances/99004506/
99004507	LICMY	Public-Enemy	5	https://crest-tq.eveonline.com/alliances/99004507/
99004508	TETRA	Technical Trust	5	https://crest-tq.eveonline.com/alliances/99004508/
99004510	CUBE	Cube Alliance	5	https://crest-tq.eveonline.com/alliances/99004510/
99004511	-DE-	Deutschland.	5	https://crest-tq.eveonline.com/alliances/99004511/
99004512	PAL	Patriot Alliance	5	https://crest-tq.eveonline.com/alliances/99004512/
99004513	MOURN	Gone Before Mourning	5	https://crest-tq.eveonline.com/alliances/99004513/
99004514	CArFo	Caldari Armed Forces.	5	https://crest-tq.eveonline.com/alliances/99004514/
99004523	.WTT.	We trade things... I think	5	https://crest-tq.eveonline.com/alliances/99004523/
99004529	-PO-	Promethean Overlords.	5	https://crest-tq.eveonline.com/alliances/99004529/
99004530	-IPU-	Inter-Planktonic Universal	5	https://crest-tq.eveonline.com/alliances/99004530/
99004534	SMPL	Simple Group	5	https://crest-tq.eveonline.com/alliances/99004534/
99004537	SUFFR	The Tormented.	5	https://crest-tq.eveonline.com/alliances/99004537/
99004538	VEERG	Veerhouven Group Alliance	5	https://crest-tq.eveonline.com/alliances/99004538/
99004539	VM.	Virtues of our Mothers	5	https://crest-tq.eveonline.com/alliances/99004539/
99004541	EACS	Estel Arador Capsuleer Services	5	https://crest-tq.eveonline.com/alliances/99004541/
99004543	SYZYG	Syzygy Crux.	5	https://crest-tq.eveonline.com/alliances/99004543/
99004544	EHO	Epsilon Horizon	5	https://crest-tq.eveonline.com/alliances/99004544/
99004548	8TRON	Neo-Bushido Movement	5	https://crest-tq.eveonline.com/alliances/99004548/
99004549	DRS	Dream Squad.	5	https://crest-tq.eveonline.com/alliances/99004549/
99004552	-WAR-	Space Marines.	5	https://crest-tq.eveonline.com/alliances/99004552/
99004556	ALICE	A Mad Tea-Party	5	https://crest-tq.eveonline.com/alliances/99004556/
99004558	EOL	End of Natural Lifetime	5	https://crest-tq.eveonline.com/alliances/99004558/
99004562	MAD	Mutual Assured Destruction	6	https://crest-tq.eveonline.com/alliances/99004562/
99004563	USIF	UNITED STATES IMPERIAL FORCES	6	https://crest-tq.eveonline.com/alliances/99004563/
99004565	POLIS	Black Rise Police Department	6	https://crest-tq.eveonline.com/alliances/99004565/
99004566	OSHIT	Official Society of Honor in Trust	6	https://crest-tq.eveonline.com/alliances/99004566/
99004567	THIRD	Honorable Third Party	6	https://crest-tq.eveonline.com/alliances/99004567/
99004568	BL-HR	Blue-Horizon	6	https://crest-tq.eveonline.com/alliances/99004568/
99004570	SENSE	senseless intentions	6	https://crest-tq.eveonline.com/alliances/99004570/
99004576	MULLO	Mullo Hokema	6	https://crest-tq.eveonline.com/alliances/99004576/
99004577	BEBIG	Bohica Empire	6	https://crest-tq.eveonline.com/alliances/99004577/
99004582	H2LOL	Waterboard Comedy Tour	6	https://crest-tq.eveonline.com/alliances/99004582/
99004583	AGGRO	Aggressive Recycling Organization.	6	https://crest-tq.eveonline.com/alliances/99004583/
99004585	-AFK-	Suddenly AFK	6	https://crest-tq.eveonline.com/alliances/99004585/
99004591	I.C.M	International cartel of liberal Miningcorperations	6	https://crest-tq.eveonline.com/alliances/99004591/
99004592	HIPPO	When Hippos Attack	6	https://crest-tq.eveonline.com/alliances/99004592/
99004593	HRTZ	The Irukandji.	6	https://crest-tq.eveonline.com/alliances/99004593/
99004598	TARIS	Taurus Rising	6	https://crest-tq.eveonline.com/alliances/99004598/
99004604	PNS	Phoenix Naval Systems	6	https://crest-tq.eveonline.com/alliances/99004604/
99004608	-MAD-	Ministry of Agressive Destruction	6	https://crest-tq.eveonline.com/alliances/99004608/
99004615	GIF	Geonosis Industrial Foundries	6	https://crest-tq.eveonline.com/alliances/99004615/
99004622	DU5D	Dimensions Unlimited Alliance	6	https://crest-tq.eveonline.com/alliances/99004622/
99004623	B0VRD	The Bovril Collective	6	https://crest-tq.eveonline.com/alliances/99004623/
99004626	ETERN	Eternal Equilibrium Alliance	6	https://crest-tq.eveonline.com/alliances/99004626/
99004634	GOAT.	Guardians of the Galaxy.	6	https://crest-tq.eveonline.com/alliances/99004634/
99004641	PAIN.	Power Absolute Inc.	6	https://crest-tq.eveonline.com/alliances/99004641/
99004644	HNFWA	Help Newbes Find a Way Alliance	6	https://crest-tq.eveonline.com/alliances/99004644/
99004650	NOGO	Not Very Good	6	https://crest-tq.eveonline.com/alliances/99004650/
99004651	TBS	The Bakers Squad	6	https://crest-tq.eveonline.com/alliances/99004651/
99004658	LINKS	Together We Solo	6	https://crest-tq.eveonline.com/alliances/99004658/
99004661	BWS	Black Wing Syndicate	6	https://crest-tq.eveonline.com/alliances/99004661/
99004662	WZALL	WZ-Alliance	6	https://crest-tq.eveonline.com/alliances/99004662/
99004663	ATOMS	The Stratosphere	6	https://crest-tq.eveonline.com/alliances/99004663/
99004664	HSPN	HISPANIA.	6	https://crest-tq.eveonline.com/alliances/99004664/
99004666	INYOU	ButtSharpie	6	https://crest-tq.eveonline.com/alliances/99004666/
99004670	-GCA-	Galactic Conundrum Alliance	6	https://crest-tq.eveonline.com/alliances/99004670/
99004671	S.CF	Stain Confederation	6	https://crest-tq.eveonline.com/alliances/99004671/
99004675	TOOL	Multitool.	6	https://crest-tq.eveonline.com/alliances/99004675/
99004678	TCOW.	The Crossers of White	6	https://crest-tq.eveonline.com/alliances/99004678/
99004682	IPOT	Outlanders United	6	https://crest-tq.eveonline.com/alliances/99004682/
99004683	RISK	R.I.S.K	6	https://crest-tq.eveonline.com/alliances/99004683/
99004685	-PTA-	Pandora Trade Alliance	6	https://crest-tq.eveonline.com/alliances/99004685/
99004686	A.D.C	Academic Digital Commons	6	https://crest-tq.eveonline.com/alliances/99004686/
99004688	B-CRM	Brothers of Crimson	6	https://crest-tq.eveonline.com/alliances/99004688/
99004694	NWBE	NewBee Alliance	6	https://crest-tq.eveonline.com/alliances/99004694/
99004696	BOOTY	Pirate Nation.	6	https://crest-tq.eveonline.com/alliances/99004696/
99004702	SWAMP	Swamphole	6	https://crest-tq.eveonline.com/alliances/99004702/
99004709	LAZYA	Lazy Bastards Alliance	6	https://crest-tq.eveonline.com/alliances/99004709/
99004711	TNB	Toetags n Bodybags	6	https://crest-tq.eveonline.com/alliances/99004711/
99004712	ASSQ	Asgard Squadron	6	https://crest-tq.eveonline.com/alliances/99004712/
99004713	GHOST	New Frontiers Federation	6	https://crest-tq.eveonline.com/alliances/99004713/
99004714	HAWK.	The Ebon Hawk.	6	https://crest-tq.eveonline.com/alliances/99004714/
99004717	MAGIC	Magic People.	6	https://crest-tq.eveonline.com/alliances/99004717/
99004719	WEDID	We Didn't Mean It	6	https://crest-tq.eveonline.com/alliances/99004719/
99004722	-SET-	Settlers Federation	6	https://crest-tq.eveonline.com/alliances/99004722/
99004728	ASMBL	WE FORM VOLTRON	6	https://crest-tq.eveonline.com/alliances/99004728/
99004730	.JIDF	Shut It Down	6	https://crest-tq.eveonline.com/alliances/99004730/
99004732	OCA	Orbis Conquaerere	6	https://crest-tq.eveonline.com/alliances/99004732/
99004734	-DRG-	Dragon Empire.	6	https://crest-tq.eveonline.com/alliances/99004734/
99004737	FERAL	Feral Reflection	6	https://crest-tq.eveonline.com/alliances/99004737/
99004741	RE	Resonance.	6	https://crest-tq.eveonline.com/alliances/99004741/
99004742	SCORP	Scorpion Federation	6	https://crest-tq.eveonline.com/alliances/99004742/
99004743	BEBOP	Spaceship Bebop	6	https://crest-tq.eveonline.com/alliances/99004743/
99004744	THIMU	The Imperial Union	6	https://crest-tq.eveonline.com/alliances/99004744/
99004747	RATH	Random Thinking	6	https://crest-tq.eveonline.com/alliances/99004747/
99004757	MORTR	Mortum Ravagers	6	https://crest-tq.eveonline.com/alliances/99004757/
99004758	LI-LE	Limitless Redux	6	https://crest-tq.eveonline.com/alliances/99004758/
99004763	JPT	Jupiter Heavy Industries	6	https://crest-tq.eveonline.com/alliances/99004763/
99004764	C	Cattery	6	https://crest-tq.eveonline.com/alliances/99004764/
99004765	DDDDD	Dodge Duck Dip Dive and Dodge	6	https://crest-tq.eveonline.com/alliances/99004765/
99004771	WOLF.	Raised by Wolves.	6	https://crest-tq.eveonline.com/alliances/99004771/
99004778	.ASP.	AII ShaII Perish	6	https://crest-tq.eveonline.com/alliances/99004778/
99004779	CY-27	Soviet-Union	6	https://crest-tq.eveonline.com/alliances/99004779/
99004783	-SMP-	Slightly Misguided Pacifists	6	https://crest-tq.eveonline.com/alliances/99004783/
99004784	CRY	123.456	6	https://crest-tq.eveonline.com/alliances/99004784/
99004789	GSP0T	Gentlemen's.Parlor	6	https://crest-tq.eveonline.com/alliances/99004789/
99004790	ALLIN	Altogether Inadequate	6	https://crest-tq.eveonline.com/alliances/99004790/
99004792	FREGE	Free Galactic Enterprises Alliance	6	https://crest-tq.eveonline.com/alliances/99004792/
99004793	HST	High-Sec Tomfoolery	6	https://crest-tq.eveonline.com/alliances/99004793/
99004797	VMATE	Second Hand Strategy	6	https://crest-tq.eveonline.com/alliances/99004797/
99004798	-SKY-	Skyfire.	6	https://crest-tq.eveonline.com/alliances/99004798/
99004800	.N.R.	Nimble Rabbits	6	https://crest-tq.eveonline.com/alliances/99004800/
99004803	-ISH	No Holes Barred-ish	6	https://crest-tq.eveonline.com/alliances/99004803/
99004804	.-D-.	DarkSide.	6	https://crest-tq.eveonline.com/alliances/99004804/
99004807	C.A.T	Confederation of Advanced Technology	6	https://crest-tq.eveonline.com/alliances/99004807/
99004818	-EST-	Estamos Solos Alliance.	6	https://crest-tq.eveonline.com/alliances/99004818/
99004820	-YAR-	The Black Sails	6	https://crest-tq.eveonline.com/alliances/99004820/
99004823	POWER	The Blacklist.	6	https://crest-tq.eveonline.com/alliances/99004823/
99004827	AXIOS	AXIOS.	6	https://crest-tq.eveonline.com/alliances/99004827/
99004830	DOOM	Gunmen Of the Apocalypse	6	https://crest-tq.eveonline.com/alliances/99004830/
99004836	DIV	Divum Liberi	6	https://crest-tq.eveonline.com/alliances/99004836/
99004840	-PRE-	Prelium Ultima	6	https://crest-tq.eveonline.com/alliances/99004840/
99004841	SERCO	Serrice Council.	6	https://crest-tq.eveonline.com/alliances/99004841/
99004843	GREEN	Green Tea Alliance	6	https://crest-tq.eveonline.com/alliances/99004843/
99004849	FURR	Dead Cats Don't Meow	6	https://crest-tq.eveonline.com/alliances/99004849/
99004852	-XVX-	Void..	6	https://crest-tq.eveonline.com/alliances/99004852/
99004858	PFR	Phoebe Freeport Republic	6	https://crest-tq.eveonline.com/alliances/99004858/
99004863	OLN	Outlaw Nation.	6	https://crest-tq.eveonline.com/alliances/99004863/
99004867	UW	Unseen Wolves	6	https://crest-tq.eveonline.com/alliances/99004867/
99004874	-EK-	Everyb0dy Knows	6	https://crest-tq.eveonline.com/alliances/99004874/
99004878	D4RK	Darkhorse Nation	6	https://crest-tq.eveonline.com/alliances/99004878/
99004881	EVIAN	EVIAN NATION	6	https://crest-tq.eveonline.com/alliances/99004881/
99004882	DREDD	Dredd - The Purification Project	6	https://crest-tq.eveonline.com/alliances/99004882/
99004883	MIX	Dreamix.	6	https://crest-tq.eveonline.com/alliances/99004883/
99004885	DRWIN	Darwinism.	6	https://crest-tq.eveonline.com/alliances/99004885/
99004886	72.AU	Warp to Cyno.	6	https://crest-tq.eveonline.com/alliances/99004886/
99004887	-ROC-	ROC.	6	https://crest-tq.eveonline.com/alliances/99004887/
99004889	NEIA	New Eden Mining Co Alliance	6	https://crest-tq.eveonline.com/alliances/99004889/
99004891	NEED	BLACK NEEDLES	6	https://crest-tq.eveonline.com/alliances/99004891/
99004894	CHAPT	Chapters.	6	https://crest-tq.eveonline.com/alliances/99004894/
99004896	-VOX-	Vox Populi.	6	https://crest-tq.eveonline.com/alliances/99004896/
99004898	SPY	Smart Deploy	6	https://crest-tq.eveonline.com/alliances/99004898/
99004900	HFFAN	Hummus and Falafel	6	https://crest-tq.eveonline.com/alliances/99004900/
99004901	B B C	Snuffed Out	6	https://crest-tq.eveonline.com/alliances/99004901/
99004902	S-A-S	Silentia Audientes Societas	6	https://crest-tq.eveonline.com/alliances/99004902/
99004904	AWACS	A.W.A.C.S and Friends	6	https://crest-tq.eveonline.com/alliances/99004904/
99004905	TARD	Short Bus Syndicate	6	https://crest-tq.eveonline.com/alliances/99004905/
99004906	SHDR	SHKID Republic	6	https://crest-tq.eveonline.com/alliances/99004906/
99004907	INJUS	Injustice League.	6	https://crest-tq.eveonline.com/alliances/99004907/
99004910	4FUNN	Mining is Funn	6	https://crest-tq.eveonline.com/alliances/99004910/
99004911	R.I.P	R.I.P. Alliance	6	https://crest-tq.eveonline.com/alliances/99004911/
99004915	W4FF3	RAUMWAFFEn KOALITION	6	https://crest-tq.eveonline.com/alliances/99004915/
99004917	Y-M	Y-MPIRE	6	https://crest-tq.eveonline.com/alliances/99004917/
99004918	ULO	Unification the lost Orders	6	https://crest-tq.eveonline.com/alliances/99004918/
99004920	GEA	GFORCE Enterprises Alliance	6	https://crest-tq.eveonline.com/alliances/99004920/
99004922	ESTEC	Equinox Space Technologies	6	https://crest-tq.eveonline.com/alliances/99004922/
99004926	TAM	The Amish Mafia	6	https://crest-tq.eveonline.com/alliances/99004926/
99004927	BLUE.	BLUE Alliance	6	https://crest-tq.eveonline.com/alliances/99004927/
99004931	COK	Conduit of Knowledge	6	https://crest-tq.eveonline.com/alliances/99004931/
99004932	JCLAW	Steinberg Jewelry	6	https://crest-tq.eveonline.com/alliances/99004932/
99004933	.MCH.	Skeleton Citizens	6	https://crest-tq.eveonline.com/alliances/99004933/
99004934	ARX	Arx Alliance	6	https://crest-tq.eveonline.com/alliances/99004934/
99004935	SCOFI	Scofield Enterprises	6	https://crest-tq.eveonline.com/alliances/99004935/
99004936	IEE	Immortals Empire	6	https://crest-tq.eveonline.com/alliances/99004936/
99004939	PARAN	Paranoid Angels	6	https://crest-tq.eveonline.com/alliances/99004939/
99004942	CNFLG	CONFLAGBUSINESSMAN	6	https://crest-tq.eveonline.com/alliances/99004942/
99004943	TACIT	Favente Deo Supero	6	https://crest-tq.eveonline.com/alliances/99004943/
99004944	IS-S	Ishukone Syndicate	6	https://crest-tq.eveonline.com/alliances/99004944/
99004945	.IED	Immediate Destruction	6	https://crest-tq.eveonline.com/alliances/99004945/
99004956	P0CA	Phoenix Company Alliance	6	https://crest-tq.eveonline.com/alliances/99004956/
99004958	PAC5	Populus Alternis Conglomeratus	6	https://crest-tq.eveonline.com/alliances/99004958/
99004965	-ERA-	Eve Radio Alliance	6	https://crest-tq.eveonline.com/alliances/99004965/
99004968	AWRY	Misfit Armada	6	https://crest-tq.eveonline.com/alliances/99004968/
99004975	IS-B.	Interstellar Brotherhood	6	https://crest-tq.eveonline.com/alliances/99004975/
99004979	WHLEL	Filth.	6	https://crest-tq.eveonline.com/alliances/99004979/
99004980	DEEPS	DeepSpace.	6	https://crest-tq.eveonline.com/alliances/99004980/
99004986	TSQDC	The Southern Querious Drug Cartel	6	https://crest-tq.eveonline.com/alliances/99004986/
99004988	KRAK.	KRAKEN.	6	https://crest-tq.eveonline.com/alliances/99004988/
99004989	CRZY	GONE MAD.	6	https://crest-tq.eveonline.com/alliances/99004989/
99004993	TCAP	To catch a predator	6	https://crest-tq.eveonline.com/alliances/99004993/
99004997	ANOVA	Analysis of Variance	6	https://crest-tq.eveonline.com/alliances/99004997/
99004998	AAAAA	A.A.A.A.A	6	https://crest-tq.eveonline.com/alliances/99004998/
99005002	LIFI	Liberti Fidelis	6	https://crest-tq.eveonline.com/alliances/99005002/
99005005	RGROL	This Is How We Roll	6	https://crest-tq.eveonline.com/alliances/99005005/
99005006	DRC	Dream Citizens	6	https://crest-tq.eveonline.com/alliances/99005006/
99005011	NAGGR	People Who Annoy You	6	https://crest-tq.eveonline.com/alliances/99005011/
99005012	DOS	Disciples of The Spoon	6	https://crest-tq.eveonline.com/alliances/99005012/
99005014	PESKY	Galaxy Rangers.	6	https://crest-tq.eveonline.com/alliances/99005014/
99005015	16-13	Project.Mayhem.	6	https://crest-tq.eveonline.com/alliances/99005015/
99005017	BAMBI	The Asylum.	6	https://crest-tq.eveonline.com/alliances/99005017/
99005020	NWSIG	New Signature	6	https://crest-tq.eveonline.com/alliances/99005020/
99005021	CLOTH	Celestial League of Thugs	6	https://crest-tq.eveonline.com/alliances/99005021/
99005022	TAC	Tactical Terror Alliance	6	https://crest-tq.eveonline.com/alliances/99005022/
99005023	HMASS	Half Massed	6	https://crest-tq.eveonline.com/alliances/99005023/
99005027	SOLOE	Solo Enterprises	6	https://crest-tq.eveonline.com/alliances/99005027/
99005030	SU.	Slaver's Union	6	https://crest-tq.eveonline.com/alliances/99005030/
99005031	DW	DarkWings	6	https://crest-tq.eveonline.com/alliances/99005031/
99005034	W.SKY	White Sky.	6	https://crest-tq.eveonline.com/alliances/99005034/
99005035	M-7	Mercury Seven	6	https://crest-tq.eveonline.com/alliances/99005035/
99005037	OPE.	Magellan's Enterprises	6	https://crest-tq.eveonline.com/alliances/99005037/
99005042	DRM	Dreamcatchers.	6	https://crest-tq.eveonline.com/alliances/99005042/
99005043	MNUM	Murder By Numbers Alliance	6	https://crest-tq.eveonline.com/alliances/99005043/
99005046	POV	Paragons Of Virtue	6	https://crest-tq.eveonline.com/alliances/99005046/
99005047	GEAR.	Engineers.	6	https://crest-tq.eveonline.com/alliances/99005047/
99005049	AUROR	Aurora of Positron Robots	6	https://crest-tq.eveonline.com/alliances/99005049/
99005051	-WTF-	Indomitable Intentions	6	https://crest-tq.eveonline.com/alliances/99005051/
99005052	APA	Anonymous Pilots Alliance	6	https://crest-tq.eveonline.com/alliances/99005052/
99005053	DELVE	Sani-Sabik	6	https://crest-tq.eveonline.com/alliances/99005053/
99005057	FUZZY	Fuzzy Logic.	6	https://crest-tq.eveonline.com/alliances/99005057/
99005059	CNN	Carebear News Network	6	https://crest-tq.eveonline.com/alliances/99005059/
99005064	HANGR	Carebear Abortion Clinic	6	https://crest-tq.eveonline.com/alliances/99005064/
99005065	HKRAB	Hard Knocks Citizens	6	https://crest-tq.eveonline.com/alliances/99005065/
99005066	D.SYN	Draconian Syndicate	6	https://crest-tq.eveonline.com/alliances/99005066/
99005069	WWW	Wild Wild West.	6	https://crest-tq.eveonline.com/alliances/99005069/
99005070	I-A	Interstellar-Alliance	6	https://crest-tq.eveonline.com/alliances/99005070/
99005080	EVE	Recursion	6	https://crest-tq.eveonline.com/alliances/99005080/
99005081	TIMG	The Independent Mining Guild	6	https://crest-tq.eveonline.com/alliances/99005081/
99005088	OHSHI	Random Curse Maggots	6	https://crest-tq.eveonline.com/alliances/99005088/
99005092	NOSHT	Cheytac Intervention R and D	6	https://crest-tq.eveonline.com/alliances/99005092/
99005095	PILMO	Piloting Modestly	6	https://crest-tq.eveonline.com/alliances/99005095/
99005097	THNK	Thinkerbolt Family Holdings	6	https://crest-tq.eveonline.com/alliances/99005097/
99005099	0003	C.O.N.S.O.R.T.I.U.M.	6	https://crest-tq.eveonline.com/alliances/99005099/
99005100	.DED	All My Friends Are Ded	6	https://crest-tq.eveonline.com/alliances/99005100/
99005101	RUCEM	RUCA Emperor	6	https://crest-tq.eveonline.com/alliances/99005101/
99005103	IN-UN	Independent Union	6	https://crest-tq.eveonline.com/alliances/99005103/
99005108	SLR-W	SOLAR WING.	6	https://crest-tq.eveonline.com/alliances/99005108/
99005110	-OPS-	Operation Pointy Stick	6	https://crest-tq.eveonline.com/alliances/99005110/
99005111	VICP.	Virtus Crusade Protectorate	6	https://crest-tq.eveonline.com/alliances/99005111/
99005118	CHAO3	Chao3 Alliance	6	https://crest-tq.eveonline.com/alliances/99005118/
99005123	CPL	Beaver Bashers.	6	https://crest-tq.eveonline.com/alliances/99005123/
99005124	KAIJU	Kaiju.	6	https://crest-tq.eveonline.com/alliances/99005124/
99005125	D0T	Decisions of Truth	6	https://crest-tq.eveonline.com/alliances/99005125/
99005127	BY	Bystanders	6	https://crest-tq.eveonline.com/alliances/99005127/
99005128	-P.A-	Prodigal Alliance	6	https://crest-tq.eveonline.com/alliances/99005128/
99005130	SC0UT	EvE-Scout Enclave	6	https://crest-tq.eveonline.com/alliances/99005130/
99005131	BL4CK	Desmond Legion	6	https://crest-tq.eveonline.com/alliances/99005131/
99005133	GTGUD	The Good Christian Society	6	https://crest-tq.eveonline.com/alliances/99005133/
99005135	WAH	We ALL Hurt	6	https://crest-tq.eveonline.com/alliances/99005135/
99005136	PORKU	Aporkalypse Now	6	https://crest-tq.eveonline.com/alliances/99005136/
99005138	TRIBE	Ancient Tribe.	6	https://crest-tq.eveonline.com/alliances/99005138/
99005139	WONGH	It's the Wrong Hole	6	https://crest-tq.eveonline.com/alliances/99005139/
99005143	IDS	Independent Distribution Service United	6	https://crest-tq.eveonline.com/alliances/99005143/
99005146	E O A	Empire of Archlilia	6	https://crest-tq.eveonline.com/alliances/99005146/
99005148	HGPT	Hiigaran Protectorate	6	https://crest-tq.eveonline.com/alliances/99005148/
99005149	BIA	Brothers in Arms Alliance	6	https://crest-tq.eveonline.com/alliances/99005149/
99005150	TCF	Tau Ceti Federation	6	https://crest-tq.eveonline.com/alliances/99005150/
99005151	2LGIT	Warlords of the Deep	6	https://crest-tq.eveonline.com/alliances/99005151/
99005152	PAVED	The Good Intentions Paving Company	6	https://crest-tq.eveonline.com/alliances/99005152/
99005157	WIN	Winmatar Republic	6	https://crest-tq.eveonline.com/alliances/99005157/
99005162	VS	Vengeful Swarm	6	https://crest-tq.eveonline.com/alliances/99005162/
99005164	HELIX	Infinity Gene	6	https://crest-tq.eveonline.com/alliances/99005164/
99005168	DIP	Diplomatic Immunity.	6	https://crest-tq.eveonline.com/alliances/99005168/
99005169	PROPN	Property of Negative-Impact	6	https://crest-tq.eveonline.com/alliances/99005169/
99005170	BTDWN	Lunch Money Syndicate	6	https://crest-tq.eveonline.com/alliances/99005170/
99005171	V.I.P	EMPIRE OF LIBERTY	6	https://crest-tq.eveonline.com/alliances/99005171/
99005172	DONE	This can only end well	6	https://crest-tq.eveonline.com/alliances/99005172/
99005173	ST.RC	Strategic Tactics And Recon.	6	https://crest-tq.eveonline.com/alliances/99005173/
99005178	ART0N	Domain Research and Mining Inst.	6	https://crest-tq.eveonline.com/alliances/99005178/
99005179	SPTD	Stopped Out.	6	https://crest-tq.eveonline.com/alliances/99005179/
99005180	RUSS	RUSSIA Alliance	6	https://crest-tq.eveonline.com/alliances/99005180/
99005181	BACO	Bacon Family	6	https://crest-tq.eveonline.com/alliances/99005181/
99005182	PIXEL	IT'S ONLY PIXELS	6	https://crest-tq.eveonline.com/alliances/99005182/
99005184	KAOS	KAOS Unlimited	6	https://crest-tq.eveonline.com/alliances/99005184/
99005185	-NHC-	NoHo Citizen	6	https://crest-tq.eveonline.com/alliances/99005185/
99005189	TFV	Task Force Viper	6	https://crest-tq.eveonline.com/alliances/99005189/
99005194	-ACM-	An Cion Mythic	6	https://crest-tq.eveonline.com/alliances/99005194/
99005196	NEKU	New Eden Kingdom United	6	https://crest-tq.eveonline.com/alliances/99005196/
99005198	TAKSH	Official Winners Of Takeshi's Castle	6	https://crest-tq.eveonline.com/alliances/99005198/
99005200	DIS	Dis-Band	6	https://crest-tq.eveonline.com/alliances/99005200/
99005201	501ST	Night Raid.	6	https://crest-tq.eveonline.com/alliances/99005201/
99005205	ANSTR	The Ancestors	6	https://crest-tq.eveonline.com/alliances/99005205/
99005206	S.V	Space Violence	6	https://crest-tq.eveonline.com/alliances/99005206/
99005207	PR-R	Phoenix Revolution Republic	6	https://crest-tq.eveonline.com/alliances/99005207/
99005208	DKFLT	Dark Fleet	6	https://crest-tq.eveonline.com/alliances/99005208/
99005209	WH	Whatever.	6	https://crest-tq.eveonline.com/alliances/99005209/
99005214	ML	Molon.Labe	6	https://crest-tq.eveonline.com/alliances/99005214/
99005215	T P C	The Perfect Circle.	6	https://crest-tq.eveonline.com/alliances/99005215/
99005218	FNTN	Fail Nation	6	https://crest-tq.eveonline.com/alliances/99005218/
99005222	-SPX-	Space X Industries	6	https://crest-tq.eveonline.com/alliances/99005222/
99005224	HADES	Hell's Pirates	6	https://crest-tq.eveonline.com/alliances/99005224/
99005226	SICA	Strategically Integrated Coalition Alliance	6	https://crest-tq.eveonline.com/alliances/99005226/
99005227	3RDBN	Third Degree Burns	6	https://crest-tq.eveonline.com/alliances/99005227/
99005228	DEV	Devils From Heaven	7	https://crest-tq.eveonline.com/alliances/99005228/
99005235	M3RKD	Short Bus Mercenaries.	7	https://crest-tq.eveonline.com/alliances/99005235/
99005237	OWSLA	Rabbit Wholes	7	https://crest-tq.eveonline.com/alliances/99005237/
99005238	XENON	New Eden Terraform Alliance	7	https://crest-tq.eveonline.com/alliances/99005238/
99005239	XAAR	Balcora Gatekeepers	7	https://crest-tq.eveonline.com/alliances/99005239/
99005242	AP0	Against Probes	7	https://crest-tq.eveonline.com/alliances/99005242/
99005245	-GAG-	Good at this Game	7	https://crest-tq.eveonline.com/alliances/99005245/
99005248	DNIWE	Bottom Alliance	7	https://crest-tq.eveonline.com/alliances/99005248/
99005251	.TOV.	Trinity of Vice	7	https://crest-tq.eveonline.com/alliances/99005251/
99005253	LOLLY	Lollypop Alliance	7	https://crest-tq.eveonline.com/alliances/99005253/
99005254	AUSS	Allied Union of Secured Space	7	https://crest-tq.eveonline.com/alliances/99005254/
99005255	IGE	Imperium Galactic Empire	7	https://crest-tq.eveonline.com/alliances/99005255/
99005261	BORD2	Boredom Industries	7	https://crest-tq.eveonline.com/alliances/99005261/
99005264	ES	Eiderokh State	7	https://crest-tq.eveonline.com/alliances/99005264/
99005265	DEX	Dramatic Exit.	7	https://crest-tq.eveonline.com/alliances/99005265/
99005266	DEOS	DEOS Alliance	7	https://crest-tq.eveonline.com/alliances/99005266/
99005268	SWEET	Sweet Dreams Alliance	7	https://crest-tq.eveonline.com/alliances/99005268/
99005270	SEXY.	Somewhat Evil Xenophobic YOLOers	7	https://crest-tq.eveonline.com/alliances/99005270/
99005271	AEI	Annelid Explorations Inc.	7	https://crest-tq.eveonline.com/alliances/99005271/
99005273	GESKO	Gespenster Kompanie	7	https://crest-tq.eveonline.com/alliances/99005273/
99005274	LLDML	La Ligue des mondes libres	7	https://crest-tq.eveonline.com/alliances/99005274/
99005275	SOCKS	Sock Puppet Federation	7	https://crest-tq.eveonline.com/alliances/99005275/
99005277	BALLS	Great Blue Balls of Fire	7	https://crest-tq.eveonline.com/alliances/99005277/
99005280	LRF	Long Range Foundation	7	https://crest-tq.eveonline.com/alliances/99005280/
99005286	SNOVA	Stella Nova	7	https://crest-tq.eveonline.com/alliances/99005286/
99005289	ROFL.	Rise Of Legion.	7	https://crest-tq.eveonline.com/alliances/99005289/
99005291	IRAT	Imaginary Rats	7	https://crest-tq.eveonline.com/alliances/99005291/
99005292	420	TetraHydroC	7	https://crest-tq.eveonline.com/alliances/99005292/
99005295	TBA	The Bandit Alliance	7	https://crest-tq.eveonline.com/alliances/99005295/
99005296	SBRF	Sberbank Fatality	7	https://crest-tq.eveonline.com/alliances/99005296/
99005298	EGGS	Uncharted Space	7	https://crest-tq.eveonline.com/alliances/99005298/
99005299	MGRGY	FUBAR.	7	https://crest-tq.eveonline.com/alliances/99005299/
99005301	SII-A	Sanair Services	7	https://crest-tq.eveonline.com/alliances/99005301/
99005305	SCIBE	Scibere est Agere	7	https://crest-tq.eveonline.com/alliances/99005305/
99005309	IRD	Imperial Raata Directorate	7	https://crest-tq.eveonline.com/alliances/99005309/
99005312	CDP	Confederation des Pleiades	7	https://crest-tq.eveonline.com/alliances/99005312/
99005314	GATE	Gate Camp Theory	7	https://crest-tq.eveonline.com/alliances/99005314/
99005315	CHHY	Chaos Hegemony Alliance	7	https://crest-tq.eveonline.com/alliances/99005315/
99005318	HD-CK	Honey Duck Republic	7	https://crest-tq.eveonline.com/alliances/99005318/
99005320	.EMG.	Emergence.	7	https://crest-tq.eveonline.com/alliances/99005320/
99005321	.LRN	The Learn	7	https://crest-tq.eveonline.com/alliances/99005321/
99005324	-HIV-	Harakiri Intencao Vendetta	7	https://crest-tq.eveonline.com/alliances/99005324/
99005328	SSMK	Union of 1812	7	https://crest-tq.eveonline.com/alliances/99005328/
99005330	CASA-	Casa Repubblica	7	https://crest-tq.eveonline.com/alliances/99005330/
99005332	VIT.C	The Blood Covenant	7	https://crest-tq.eveonline.com/alliances/99005332/
99005333	UF	United Fleet	7	https://crest-tq.eveonline.com/alliances/99005333/
99005334	CYBER	CyberSphere	7	https://crest-tq.eveonline.com/alliances/99005334/
99005335	TROLL	Austrollia.	7	https://crest-tq.eveonline.com/alliances/99005335/
99005338	REKTD	Pandemic Horde	7	https://crest-tq.eveonline.com/alliances/99005338/
99005339	SERP	Serpentiss Associates	7	https://crest-tq.eveonline.com/alliances/99005339/
99005346	K.A.D	K.A.D.M.U.S Alliance	7	https://crest-tq.eveonline.com/alliances/99005346/
99005349	TS	The Shooters.	7	https://crest-tq.eveonline.com/alliances/99005349/
99005352	FOCKL	Lolsec Fockel	7	https://crest-tq.eveonline.com/alliances/99005352/
99005358	PEN1S	Pen Island Pens	7	https://crest-tq.eveonline.com/alliances/99005358/
99005360	PROT	Prothean Alliance	7	https://crest-tq.eveonline.com/alliances/99005360/
99005364	SRVUS	Cohortes Triarii	7	https://crest-tq.eveonline.com/alliances/99005364/
99005365	RSE	Red Sun Empire	7	https://crest-tq.eveonline.com/alliances/99005365/
99005369	FIGHT	The WeHurt Initiative	7	https://crest-tq.eveonline.com/alliances/99005369/
99005370	ZBDEE	The Magic Roundabout Alliance	7	https://crest-tq.eveonline.com/alliances/99005370/
99005372	SNE	Shards Of New Eden	7	https://crest-tq.eveonline.com/alliances/99005372/
99005376	CFA	Chaotic Forge	7	https://crest-tq.eveonline.com/alliances/99005376/
99005378	MCF1B	Multicultural F1 Brigade	7	https://crest-tq.eveonline.com/alliances/99005378/
99005379	ESA	Evil Syndicate Alliance.	7	https://crest-tq.eveonline.com/alliances/99005379/
99005381	SIN7	SE7EN-SINS	7	https://crest-tq.eveonline.com/alliances/99005381/
99005382	JITA	Jita Holding Inc.	7	https://crest-tq.eveonline.com/alliances/99005382/
99005383	JAG.	Just A Game.	7	https://crest-tq.eveonline.com/alliances/99005383/
99005385	KILLS	SYNDICATED	7	https://crest-tq.eveonline.com/alliances/99005385/
99005386	RDSQD	Red Squad Alliance	7	https://crest-tq.eveonline.com/alliances/99005386/
99005387	SOG	The Special Operations Group	7	https://crest-tq.eveonline.com/alliances/99005387/
99005390	8E6D	O.U.T.L.A.W.E.D.	7	https://crest-tq.eveonline.com/alliances/99005390/
99005391	STEL-	FEDERATION OF INDEPENDENT STELLAR SYSTEMS	7	https://crest-tq.eveonline.com/alliances/99005391/
99005392	AOL	Absence of Light	7	https://crest-tq.eveonline.com/alliances/99005392/
99005393	2GTHR	Blades of Grass	7	https://crest-tq.eveonline.com/alliances/99005393/
99005395	HEUDO	HEUL DOCH ... es war Notwehr	7	https://crest-tq.eveonline.com/alliances/99005395/
99005396	VICE	V.I.C.E	7	https://crest-tq.eveonline.com/alliances/99005396/
99005397	-CN-	Cede Nullis	7	https://crest-tq.eveonline.com/alliances/99005397/
99005398	SPQR.	RES PUBLICA POPULI ROMANI	7	https://crest-tq.eveonline.com/alliances/99005398/
99005400	.M08.	M O B I U S	7	https://crest-tq.eveonline.com/alliances/99005400/
99005401	DRAMA	Drama Sutra	7	https://crest-tq.eveonline.com/alliances/99005401/
99005402	BLOOD	Blood-Guard Syndicate	7	https://crest-tq.eveonline.com/alliances/99005402/
99005403	-SC-	Southern Sitizens	7	https://crest-tq.eveonline.com/alliances/99005403/
99005408	THE	The Otherworld	7	https://crest-tq.eveonline.com/alliances/99005408/
99005411	NOVAP	Nova Prospects	7	https://crest-tq.eveonline.com/alliances/99005411/
99005414	TIME	Perpetual Alliance	7	https://crest-tq.eveonline.com/alliances/99005414/
99005415	PLBAL	Poland Can Into Space	7	https://crest-tq.eveonline.com/alliances/99005415/
99005416	OMNI	Omnibus Mori	7	https://crest-tq.eveonline.com/alliances/99005416/
99005418	WSKY	Liquor Legion	7	https://crest-tq.eveonline.com/alliances/99005418/
99005421	ASA	Amarr Separatist Alliance	7	https://crest-tq.eveonline.com/alliances/99005421/
99005425	KMV	Kaer Morhen Valley	7	https://crest-tq.eveonline.com/alliances/99005425/
99005426	M.O.W	Moon Warriors	7	https://crest-tq.eveonline.com/alliances/99005426/
99005428	FINKC	F-I-N-K and Co.	7	https://crest-tq.eveonline.com/alliances/99005428/
99005430	DVS	Die Vereinigten Sonnen	7	https://crest-tq.eveonline.com/alliances/99005430/
99005433	HODOR	Hodor Holdings	7	https://crest-tq.eveonline.com/alliances/99005433/
99005435	MATOU	MATOU Alliance	7	https://crest-tq.eveonline.com/alliances/99005435/
99005440	ANN0Y	Mildly Annoyed	7	https://crest-tq.eveonline.com/alliances/99005440/
99005442	DHCOM	Dark Harvest.	7	https://crest-tq.eveonline.com/alliances/99005442/
99005443	-KWG-	Kids With Guns Alliance	7	https://crest-tq.eveonline.com/alliances/99005443/
99005444	.ICE.	Frozen Karma.	7	https://crest-tq.eveonline.com/alliances/99005444/
99005452	WEBO	Weaponized Boredom.	7	https://crest-tq.eveonline.com/alliances/99005452/
99005453	YOKAI	Yokai	7	https://crest-tq.eveonline.com/alliances/99005453/
99005454	-OBS-	Obsidian Descendants	7	https://crest-tq.eveonline.com/alliances/99005454/
99005455	SHKL.	Shekel Syndicate	7	https://crest-tq.eveonline.com/alliances/99005455/
99005456	SIMPL	Keep It Simple Software Group	7	https://crest-tq.eveonline.com/alliances/99005456/
99005459	SRDM	Size Really Doesn't Matter	7	https://crest-tq.eveonline.com/alliances/99005459/
99005460	DEM0N	DEM0N HUNTERS	7	https://crest-tq.eveonline.com/alliances/99005460/
99005462	WHPVP	WORMHOLE PVP CHANNEL	7	https://crest-tq.eveonline.com/alliances/99005462/
99005464	U-C-A	Uranium Chaotic Accelerator	7	https://crest-tq.eveonline.com/alliances/99005464/
99005466	L3THL	Lethal Intent.	7	https://crest-tq.eveonline.com/alliances/99005466/
99005469	ZTCI	Zefyrai Alliance	7	https://crest-tq.eveonline.com/alliances/99005469/
99005471	SORT	The Society of Radical Thought	7	https://crest-tq.eveonline.com/alliances/99005471/
99005476	SYNDE	Singularity Syndicate	7	https://crest-tq.eveonline.com/alliances/99005476/
99005477	T5E	The Fifth Element.	7	https://crest-tq.eveonline.com/alliances/99005477/
99005481	FLEEP	Iron Armada	7	https://crest-tq.eveonline.com/alliances/99005481/
99005483	GH0ST	Phantom-Recon	7	https://crest-tq.eveonline.com/alliances/99005483/
99005487	ENET	Eden Network	7	https://crest-tq.eveonline.com/alliances/99005487/
99005489	1488	The Pride of Palestine	7	https://crest-tq.eveonline.com/alliances/99005489/
99005490	GSB	Grumpy Space Bastards	7	https://crest-tq.eveonline.com/alliances/99005490/
99005492	CENTI	Centipede Caliphate.	7	https://crest-tq.eveonline.com/alliances/99005492/
99005493	D3	Kings of the North	7	https://crest-tq.eveonline.com/alliances/99005493/
99005498	MOSF	Ministry of Silly Fleets	7	https://crest-tq.eveonline.com/alliances/99005498/
99005499	QCHRY	Queen Chrysalis Minions	7	https://crest-tq.eveonline.com/alliances/99005499/
99005502	SWEG.	No Handlebars.	7	https://crest-tq.eveonline.com/alliances/99005502/
99005504	ROAL	Rogue Alliance	7	https://crest-tq.eveonline.com/alliances/99005504/
99005506	BONE	Brothers Of New Eden Alliance	7	https://crest-tq.eveonline.com/alliances/99005506/
99005507	IRF	Intergalactic Research Foundation	7	https://crest-tq.eveonline.com/alliances/99005507/
99005510	WN	White Noise Alliance	7	https://crest-tq.eveonline.com/alliances/99005510/
99005512	W84ME	Accretion Dynamics	7	https://crest-tq.eveonline.com/alliances/99005512/
99005513	GOG	I Aim To Misbehave	7	https://crest-tq.eveonline.com/alliances/99005513/
99005515	DRIP	Drip	7	https://crest-tq.eveonline.com/alliances/99005515/
99005516	REAPR	Reaper Federation	7	https://crest-tq.eveonline.com/alliances/99005516/
99005518	MI22	MIDAS 22	7	https://crest-tq.eveonline.com/alliances/99005518/
99005520	-SPR-	Separatists	7	https://crest-tq.eveonline.com/alliances/99005520/
99005521	FETID	FETID	7	https://crest-tq.eveonline.com/alliances/99005521/
99005528	FRAID	Fractal Russian Alliance	7	https://crest-tq.eveonline.com/alliances/99005528/
99005530	STYXX	Perfect Dark	7	https://crest-tq.eveonline.com/alliances/99005530/
99005532	MEDRA	Memento Draconis	7	https://crest-tq.eveonline.com/alliances/99005532/
99005534	U.N.S	Unsung Blades	7	https://crest-tq.eveonline.com/alliances/99005534/
99005535	LIPHA	Les Industries Phoenix Alliance	7	https://crest-tq.eveonline.com/alliances/99005535/
99005538	BW	Blue Wings	7	https://crest-tq.eveonline.com/alliances/99005538/
99005540	D-85	District-85	7	https://crest-tq.eveonline.com/alliances/99005540/
99005542	MUR34	YIN VLG	7	https://crest-tq.eveonline.com/alliances/99005542/
99005543	NUKED	ATOMIC.	7	https://crest-tq.eveonline.com/alliances/99005543/
99005544	SONS	Sons of D00m	7	https://crest-tq.eveonline.com/alliances/99005544/
99005547	SW1FT	Taylor Swift Empire	7	https://crest-tq.eveonline.com/alliances/99005547/
99005548	LIMP	Massive Erectile Dysfunction	7	https://crest-tq.eveonline.com/alliances/99005548/
99005549	TALON	TALON ALLIANCE	7	https://crest-tq.eveonline.com/alliances/99005549/
99005552	HALO	Heavens Angelic Locusts	7	https://crest-tq.eveonline.com/alliances/99005552/
99005555	NORUM	NINE PIECES OF EIGHT	7	https://crest-tq.eveonline.com/alliances/99005555/
99005559	PYRE	Empyreus	7	https://crest-tq.eveonline.com/alliances/99005559/
99005560	LAD	Loud and Dawn	7	https://crest-tq.eveonline.com/alliances/99005560/
99005561	TTYGH	Mae cadarnle olaf y Gorthrymedig	7	https://crest-tq.eveonline.com/alliances/99005561/
99005566	TAF	The Angry Fellows	7	https://crest-tq.eveonline.com/alliances/99005566/
99005569	SNOT	Band of Boogers	7	https://crest-tq.eveonline.com/alliances/99005569/
99005571	R64	Shadow Legion.	7	https://crest-tq.eveonline.com/alliances/99005571/
99005574	PONZI	Market and Contract PVP	7	https://crest-tq.eveonline.com/alliances/99005574/
99005577	QUACK	Sleepless Sitting Ducks	7	https://crest-tq.eveonline.com/alliances/99005577/
99005579	BBG	the Big Bang Gang	7	https://crest-tq.eveonline.com/alliances/99005579/
99005583	DK	Dragon Knights Inc	7	https://crest-tq.eveonline.com/alliances/99005583/
99005586	KC	Knights of the Clan	7	https://crest-tq.eveonline.com/alliances/99005586/
99005587	-D-A-	Dark - Alliance	7	https://crest-tq.eveonline.com/alliances/99005587/
99005589	C I M	Colonel's Intergalactic Marauders	7	https://crest-tq.eveonline.com/alliances/99005589/
99005590	ERB	Einstein-Rosen Brigade	7	https://crest-tq.eveonline.com/alliances/99005590/
99005592	TQC	Quantum Collective	7	https://crest-tq.eveonline.com/alliances/99005592/
99005594	SYMSY	Symmetric Symbiosis	7	https://crest-tq.eveonline.com/alliances/99005594/
99005595	TLSW	The Lone Space Wolves	7	https://crest-tq.eveonline.com/alliances/99005595/
99005597	GOTA	Guardians of the Asylum	7	https://crest-tq.eveonline.com/alliances/99005597/
99005599	OL.AS	Olympus Associates	7	https://crest-tq.eveonline.com/alliances/99005599/
99005601	DONUT	Rainbow Knights	7	https://crest-tq.eveonline.com/alliances/99005601/
99005611	CAGE	No Points Necessary	7	https://crest-tq.eveonline.com/alliances/99005611/
99005613	SOLA	Solar Solutions	7	https://crest-tq.eveonline.com/alliances/99005613/
99005614	S3	SL33PERS	7	https://crest-tq.eveonline.com/alliances/99005614/
99005619	SOZ	SORRY NOT SORRY	7	https://crest-tq.eveonline.com/alliances/99005619/
99005621	HYPER	Hypercube Alliance	7	https://crest-tq.eveonline.com/alliances/99005621/
99005622	24-7	Insomnia 24-7	7	https://crest-tq.eveonline.com/alliances/99005622/
99005623	SCRUB	SCRUBS.	7	https://crest-tq.eveonline.com/alliances/99005623/
99005627	SPORK	No Forks Given	7	https://crest-tq.eveonline.com/alliances/99005627/
99005628	HONE	HONE Industries	7	https://crest-tq.eveonline.com/alliances/99005628/
99005629	SLIP	Boys without pants	7	https://crest-tq.eveonline.com/alliances/99005629/
99005637	-ESA-	Essence Alliance	7	https://crest-tq.eveonline.com/alliances/99005637/
99005643	SEXYP	DK Shadow's and Pyr8's Sexy Party Alliance	7	https://crest-tq.eveonline.com/alliances/99005643/
99005644	BLKOP	BLACKLISTED OPERATIONS	7	https://crest-tq.eveonline.com/alliances/99005644/
99005650	NRA	Nan Roig Alliance	7	https://crest-tq.eveonline.com/alliances/99005650/
99005651	65TH.	Forge of Blood.	7	https://crest-tq.eveonline.com/alliances/99005651/
99005654	NIC.	Notoriously Incompetent.	7	https://crest-tq.eveonline.com/alliances/99005654/
99005655	BRYCE	Triggerkittens	7	https://crest-tq.eveonline.com/alliances/99005655/
99005656	.CYNO	Dragoon's	7	https://crest-tq.eveonline.com/alliances/99005656/
99005661	TSHY	Talu Shaya Empire	7	https://crest-tq.eveonline.com/alliances/99005661/
99005663	THE B	The Blue Falcon Alliance	7	https://crest-tq.eveonline.com/alliances/99005663/
99005665	UPAY.	Swiss National Bank	7	https://crest-tq.eveonline.com/alliances/99005665/
99005673	U-ROT	Reign of Olympus	7	https://crest-tq.eveonline.com/alliances/99005673/
99005675	WRG	The Wraithguard.	7	https://crest-tq.eveonline.com/alliances/99005675/
99005676	USNAK	United Snakes On a Pole Alliance	7	https://crest-tq.eveonline.com/alliances/99005676/
99005677	GLORY	I N F A M O U S	7	https://crest-tq.eveonline.com/alliances/99005677/
99005678	CTRLV	Local Is Primary	7	https://crest-tq.eveonline.com/alliances/99005678/
99005679	PONY	Pony Strippers	7	https://crest-tq.eveonline.com/alliances/99005679/
99005680	LEEKS	LEEKSWARM FEDERATION	7	https://crest-tq.eveonline.com/alliances/99005680/
99005685	HARD	Hard Rock Inc.	7	https://crest-tq.eveonline.com/alliances/99005685/
99005688	COGG	Central Omni Galactic Group	7	https://crest-tq.eveonline.com/alliances/99005688/
99005689	BAIT	The Glory Holers	7	https://crest-tq.eveonline.com/alliances/99005689/
99005690	EVL	EVEolution.	7	https://crest-tq.eveonline.com/alliances/99005690/
99005697	POPE	Sixth Empire	7	https://crest-tq.eveonline.com/alliances/99005697/
99005699	CCF	Crying Clowns Foundation	7	https://crest-tq.eveonline.com/alliances/99005699/
99005701	SYN-T	Syntec Galactic	7	https://crest-tq.eveonline.com/alliances/99005701/
99005702	BJH	BlackJack and Hooker	7	https://crest-tq.eveonline.com/alliances/99005702/
99005703	AHG	187 Coalition	7	https://crest-tq.eveonline.com/alliances/99005703/
99005705	NAUTY	Malicious Intent Gentleman's Club	7	https://crest-tq.eveonline.com/alliances/99005705/
99005706	NPC	Nuclear Penguin Coalition	7	https://crest-tq.eveonline.com/alliances/99005706/
99005707	GURTH	Death by Degrees	7	https://crest-tq.eveonline.com/alliances/99005707/
99005708	GR1TS	Ghost Riderz In The Sky	7	https://crest-tq.eveonline.com/alliances/99005708/
99005709	WILD	Wild Souls	7	https://crest-tq.eveonline.com/alliances/99005709/
99005711	LFEVE	The Lost Fleet of Eve	7	https://crest-tq.eveonline.com/alliances/99005711/
99005712	LANTE	Lan'Tean Empire	7	https://crest-tq.eveonline.com/alliances/99005712/
99005713	DSHIT	Plutocratic Solidarity of Dipshits	7	https://crest-tq.eveonline.com/alliances/99005713/
99005718	PROCO	Promethean Confederation	7	https://crest-tq.eveonline.com/alliances/99005718/
99005719	TENE	The Empire of New Eden	7	https://crest-tq.eveonline.com/alliances/99005719/
99005721	DOOMN	DOOM NAVY	7	https://crest-tq.eveonline.com/alliances/99005721/
99005722	DPSW	S P R U T	7	https://crest-tq.eveonline.com/alliances/99005722/
99005724	-TM-	The Minions.	7	https://crest-tq.eveonline.com/alliances/99005724/
99005725	BHAND	Black Hand Alliance	7	https://crest-tq.eveonline.com/alliances/99005725/
99005727	BK0UT	Blackout Theory	7	https://crest-tq.eveonline.com/alliances/99005727/
99005728	Z3.	Z3. Tag Empire	7	https://crest-tq.eveonline.com/alliances/99005728/
99005730	MALE	The Patriarchy.	7	https://crest-tq.eveonline.com/alliances/99005730/
99005731	HOLES	Holesale Operations	7	https://crest-tq.eveonline.com/alliances/99005731/
99005732	YOBRA	Yolo Brothers	7	https://crest-tq.eveonline.com/alliances/99005732/
99005733	UC-A	United Corporation Alliance	7	https://crest-tq.eveonline.com/alliances/99005733/
99005734	U.I.A	Universal Industry Alliance	7	https://crest-tq.eveonline.com/alliances/99005734/
99005735	MINER	Manic Miners Alliance	7	https://crest-tq.eveonline.com/alliances/99005735/
99005736	FCPLS	No good deed goes unpunished	7	https://crest-tq.eveonline.com/alliances/99005736/
99005738	AEGAS	AEGIS Assembly	7	https://crest-tq.eveonline.com/alliances/99005738/
99005739	SHART	Acme Demolition	7	https://crest-tq.eveonline.com/alliances/99005739/
99005740	BLOPS	Blacklist Operations	7	https://crest-tq.eveonline.com/alliances/99005740/
99005741	PVC	Que os den forsaken	7	https://crest-tq.eveonline.com/alliances/99005741/
99005742	EPHOR	Spartan Republic	7	https://crest-tq.eveonline.com/alliances/99005742/
99005744	HAE	Holy Arumbian Empire	7	https://crest-tq.eveonline.com/alliances/99005744/
99005746	WBBN	Wubbin' To Tha Max	7	https://crest-tq.eveonline.com/alliances/99005746/
99005748	COMET	Comet Empire Alliance	7	https://crest-tq.eveonline.com/alliances/99005748/
99005749	LNFMY	I N G L O R I O U S	7	https://crest-tq.eveonline.com/alliances/99005749/
99005751	HDU	Home Defense Union	7	https://crest-tq.eveonline.com/alliances/99005751/
99005752	DAS	Dominion Aerospace Systems	7	https://crest-tq.eveonline.com/alliances/99005752/
99005756	TROL.	The Moomins.	7	https://crest-tq.eveonline.com/alliances/99005756/
99005758	.I.	Infatuated	7	https://crest-tq.eveonline.com/alliances/99005758/
99005760	THRT	Imminent Threat	7	https://crest-tq.eveonline.com/alliances/99005760/
99005761	NO.GF	White Stag Exit Bag	7	https://crest-tq.eveonline.com/alliances/99005761/
99005762	9HELZ	7th Circle Ascension	7	https://crest-tq.eveonline.com/alliances/99005762/
99005764	CIP	Consortium of Industrious Planeteers	7	https://crest-tq.eveonline.com/alliances/99005764/
99005765	SWDOT	SWARTA.	7	https://crest-tq.eveonline.com/alliances/99005765/
99005766	CSN.P	Clueless Space Nerds Podcast	7	https://crest-tq.eveonline.com/alliances/99005766/
99005767	MEN	Men Of Mayhem.	7	https://crest-tq.eveonline.com/alliances/99005767/
99005768	LB	Laser Brethren	7	https://crest-tq.eveonline.com/alliances/99005768/
99005771	THERM	Thermodynamics	7	https://crest-tq.eveonline.com/alliances/99005771/
99005772	I.H.C	International Harvesters	8	https://crest-tq.eveonline.com/alliances/99005772/
99005773	STOIC	Stoic Bastards	8	https://crest-tq.eveonline.com/alliances/99005773/
99005776	-IRA-	The Ill Repute Accord	8	https://crest-tq.eveonline.com/alliances/99005776/
99005781	BAKA	Baka Legion	8	https://crest-tq.eveonline.com/alliances/99005781/
99005784	-PEW-	This Isn't Going To End Well	8	https://crest-tq.eveonline.com/alliances/99005784/
99005785	NACL	My Nuts.	8	https://crest-tq.eveonline.com/alliances/99005785/
99005786	HPLM	HPLM Group	8	https://crest-tq.eveonline.com/alliances/99005786/
99005787	GS	Geriatric Soldiers	8	https://crest-tq.eveonline.com/alliances/99005787/
99005789	NOBAD	Notorious Bastards	8	https://crest-tq.eveonline.com/alliances/99005789/
99005790	KNOT	It's Complicated	8	https://crest-tq.eveonline.com/alliances/99005790/
99005791	E-C-B	Evil Care Bears	8	https://crest-tq.eveonline.com/alliances/99005791/
99005795	BOOM	Steel-Inferno	8	https://crest-tq.eveonline.com/alliances/99005795/
99005798	-FGC-	FREE GATES COALITION	8	https://crest-tq.eveonline.com/alliances/99005798/
99005799	APEX	APEX Conglomerate	8	https://crest-tq.eveonline.com/alliances/99005799/
99005802	-PRO-	Prometheus Alliance	8	https://crest-tq.eveonline.com/alliances/99005802/
99005803	-INP-	inPanic	8	https://crest-tq.eveonline.com/alliances/99005803/
99005804	DI	Distant Origins	8	https://crest-tq.eveonline.com/alliances/99005804/
99005805	-T C-	The-Culture	8	https://crest-tq.eveonline.com/alliances/99005805/
99005807	KRAB-	Krab Republic	8	https://crest-tq.eveonline.com/alliances/99005807/
99005808	PALS	Paladins Inc.	8	https://crest-tq.eveonline.com/alliances/99005808/
99005809	CURSE	Cursed Legion.	8	https://crest-tq.eveonline.com/alliances/99005809/
99005817	R.N.R	Righteous Retribution Syndicate	8	https://crest-tq.eveonline.com/alliances/99005817/
99005818	C1A	The-Company	8	https://crest-tq.eveonline.com/alliances/99005818/
99005823	POST	POST Industrial Mining Haven	8	https://crest-tq.eveonline.com/alliances/99005823/
99005824	TAPAH	TAPAKAH ALLIANCE	8	https://crest-tq.eveonline.com/alliances/99005824/
99005825	VIGIL	Vigilia pretium libertatis.	8	https://crest-tq.eveonline.com/alliances/99005825/
99005826	SSCB	Swiggity Swooty Coming for the Booty	8	https://crest-tq.eveonline.com/alliances/99005826/
99005829	-SMF-	Space Mafia.	8	https://crest-tq.eveonline.com/alliances/99005829/
99005830	MEMOM	Memento Moriendo	8	https://crest-tq.eveonline.com/alliances/99005830/
99005831	3BBB	Bullets Bombs and Blondes	8	https://crest-tq.eveonline.com/alliances/99005831/
99005833	CARE	Care for Kids Empire	8	https://crest-tq.eveonline.com/alliances/99005833/
99005834	SPE	SPECTRE Alliance	8	https://crest-tq.eveonline.com/alliances/99005834/
99005839	GLHF	WE FORM V0LTA	8	https://crest-tq.eveonline.com/alliances/99005839/
99005841	FIRST	First Encounter	8	https://crest-tq.eveonline.com/alliances/99005841/
99005843	LUMPY	League of Unaligned Master Pilots	8	https://crest-tq.eveonline.com/alliances/99005843/
99005844	LIMBO	In Limbo	8	https://crest-tq.eveonline.com/alliances/99005844/
99005846	BVN	Bitter Vets n Noobs	8	https://crest-tq.eveonline.com/alliances/99005846/
99005849	B0SS	Brotherhood of Spacers	8	https://crest-tq.eveonline.com/alliances/99005849/
99005850	A I	Automata Integration	8	https://crest-tq.eveonline.com/alliances/99005850/
99005852	BARVE	Brave Squids	8	https://crest-tq.eveonline.com/alliances/99005852/
99005853	BLDSN	Bleeding Sun Conglomerate	8	https://crest-tq.eveonline.com/alliances/99005853/
99005854	2BAD	Easily Offended	8	https://crest-tq.eveonline.com/alliances/99005854/
99005855	BLAP	Zero.Four Ops	8	https://crest-tq.eveonline.com/alliances/99005855/
99005857	P	Phylogenesis	8	https://crest-tq.eveonline.com/alliances/99005857/
99005858	BHI	Baltakatei Heavy Industries	8	https://crest-tq.eveonline.com/alliances/99005858/
99005861	ORD3R	Order Sixty Six	8	https://crest-tq.eveonline.com/alliances/99005861/
99005864	WISK	We want your ISK	8	https://crest-tq.eveonline.com/alliances/99005864/
99005865	AUR	Aurora.	8	https://crest-tq.eveonline.com/alliances/99005865/
99005866	FUM8	Just let it happen	8	https://crest-tq.eveonline.com/alliances/99005866/
99005868	OUT.	Outnumbered.	8	https://crest-tq.eveonline.com/alliances/99005868/
99005870	MONEY	High Fortune Investments	8	https://crest-tq.eveonline.com/alliances/99005870/
99005874	MEMED	Living Breathing Fuel Blocks	8	https://crest-tq.eveonline.com/alliances/99005874/
99005877	BSING	Blacksmithing	8	https://crest-tq.eveonline.com/alliances/99005877/
99005878	-GAP-	Ginnungagap	8	https://crest-tq.eveonline.com/alliances/99005878/
99005881	WHATS	What Squad	8	https://crest-tq.eveonline.com/alliances/99005881/
99005882	PURE.	HYSTERIA.	8	https://crest-tq.eveonline.com/alliances/99005882/
99005884	IVCT	Invictum.	8	https://crest-tq.eveonline.com/alliances/99005884/
99005886	GOOSE	Mighty Wings.	8	https://crest-tq.eveonline.com/alliances/99005886/
99005887	ER40R	404 Hole Not Found	8	https://crest-tq.eveonline.com/alliances/99005887/
99005888	G-N-R	Guns-N-Roses	8	https://crest-tq.eveonline.com/alliances/99005888/
99005890	-EDP-	Elite Demons Playground	8	https://crest-tq.eveonline.com/alliances/99005890/
99005892	U-H-S	Unlimited High Society	8	https://crest-tq.eveonline.com/alliances/99005892/
99005894	LUCK.	Unfair Advantage.	8	https://crest-tq.eveonline.com/alliances/99005894/
99005895	ZCORP	ZOASOE Conglomerate	8	https://crest-tq.eveonline.com/alliances/99005895/
99005896	CANDY	Stranger Danger.	8	https://crest-tq.eveonline.com/alliances/99005896/
99005898	TOTAL	Totally Wasted Alliance	8	https://crest-tq.eveonline.com/alliances/99005898/
99005900	ROMA	Novus Res publica Romana	8	https://crest-tq.eveonline.com/alliances/99005900/
99005901	DWMG	Dark Wind Mercenary Guild	8	https://crest-tq.eveonline.com/alliances/99005901/
99005905	UN.	Unicorn Nation	8	https://crest-tq.eveonline.com/alliances/99005905/
99005908	FURY.	The Banished	8	https://crest-tq.eveonline.com/alliances/99005908/
99005909	HEII	DevilBear	8	https://crest-tq.eveonline.com/alliances/99005909/
99005911	MPS	Ministerstvo Pytey Soobsheniya	8	https://crest-tq.eveonline.com/alliances/99005911/
99005913	S.A.	Swift Alliance	8	https://crest-tq.eveonline.com/alliances/99005913/
99005914	ID	Infinite Delusions	8	https://crest-tq.eveonline.com/alliances/99005914/
99005915	DME	Dark Matter Empire	8	https://crest-tq.eveonline.com/alliances/99005915/
99005916	IDOH	Immortal Defenders	8	https://crest-tq.eveonline.com/alliances/99005916/
99005917	FEAR	Dystopian Federation	8	https://crest-tq.eveonline.com/alliances/99005917/
99005918	SHOOT	We shoot stuff	8	https://crest-tq.eveonline.com/alliances/99005918/
99005920	THEM	Theronian Empire	8	https://crest-tq.eveonline.com/alliances/99005920/
99005921	COP	Continuum Production	8	https://crest-tq.eveonline.com/alliances/99005921/
99005922	CAP.	Cap stable.	8	https://crest-tq.eveonline.com/alliances/99005922/
99005925	SFS	Star Forged Souls	8	https://crest-tq.eveonline.com/alliances/99005925/
99005928	RABLE	Rabble Alliance	8	https://crest-tq.eveonline.com/alliances/99005928/
99005929	SUDCO	Suddenly Content	8	https://crest-tq.eveonline.com/alliances/99005929/
99005934	PENI5	Proton-Enhanced Nuclear Induction Spectroscopy.	8	https://crest-tq.eveonline.com/alliances/99005934/
99005935	ALT E	Alternate Enterprises	8	https://crest-tq.eveonline.com/alliances/99005935/
99005939	STUHL	Stuhlkreis	8	https://crest-tq.eveonline.com/alliances/99005939/
99005940	REEK	Game Of Anomalies	8	https://crest-tq.eveonline.com/alliances/99005940/
99005942	NARM	Northern Army	8	https://crest-tq.eveonline.com/alliances/99005942/
99005950	2LATE	You've been Polarized	8	https://crest-tq.eveonline.com/alliances/99005950/
99005951	RAVE	Rave Templar Society	8	https://crest-tq.eveonline.com/alliances/99005951/
99005953	BNGLY	Bingley Holdings	8	https://crest-tq.eveonline.com/alliances/99005953/
99005954	AURA	AURA Alliance	8	https://crest-tq.eveonline.com/alliances/99005954/
99005955	KOS	ChaosTheory.	8	https://crest-tq.eveonline.com/alliances/99005955/
99005957	CHRCH	The Church.	8	https://crest-tq.eveonline.com/alliances/99005957/
99005958	BJ	Banana-Joe	8	https://crest-tq.eveonline.com/alliances/99005958/
99005959	REZZ.	Off The Reservation.	8	https://crest-tq.eveonline.com/alliances/99005959/
99005962	-NF-	N I G H T F A L L	8	https://crest-tq.eveonline.com/alliances/99005962/
99005969	-M3I-	M3 Industries	8	https://crest-tq.eveonline.com/alliances/99005969/
99005973	DOMI	Domination Cartel	8	https://crest-tq.eveonline.com/alliances/99005973/
99005975	SWARM	System Wide Adaptive Roam Massacres	8	https://crest-tq.eveonline.com/alliances/99005975/
99005977	OCT31	Dia de Muertos	8	https://crest-tq.eveonline.com/alliances/99005977/
99005978	AEQUI	Aequilibrium	8	https://crest-tq.eveonline.com/alliances/99005978/
99005982	WF-A	White Fleet of Eldamar Alliance	8	https://crest-tq.eveonline.com/alliances/99005982/
99005984	BLEMP	Black Emperors	8	https://crest-tq.eveonline.com/alliances/99005984/
99005986	MOM	Muffins of Mayhem	8	https://crest-tq.eveonline.com/alliances/99005986/
99005991	O-U	Other-Universe	8	https://crest-tq.eveonline.com/alliances/99005991/
99005998	AFK77	Mafia Horde	8	https://crest-tq.eveonline.com/alliances/99005998/
99006005	CDC	Center for Digital Chemistry	8	https://crest-tq.eveonline.com/alliances/99006005/
99006010	D-I-E	Dodixie Industrial Exports	8	https://crest-tq.eveonline.com/alliances/99006010/
99006012	VOS	Virtue of Selfishness	8	https://crest-tq.eveonline.com/alliances/99006012/
99006014	COUPS	Pandas McLegion	8	https://crest-tq.eveonline.com/alliances/99006014/
99006015	.H.V.	Heroes and Villains	8	https://crest-tq.eveonline.com/alliances/99006015/
99006020	-SAS-	Who Dares Wins.	8	https://crest-tq.eveonline.com/alliances/99006020/
99006028	DANG	Danger Close.	8	https://crest-tq.eveonline.com/alliances/99006028/
99006035	MORS	MORS CERTA	8	https://crest-tq.eveonline.com/alliances/99006035/
99006038	CLOAK	Covert Logistical Oversight Alliance of Kabbalists	8	https://crest-tq.eveonline.com/alliances/99006038/
99006039	ICF	Independent Corps Federation	8	https://crest-tq.eveonline.com/alliances/99006039/
99006041	MAND	Mandalorian Imperium	8	https://crest-tq.eveonline.com/alliances/99006041/
99006042	F	From The Abyss	8	https://crest-tq.eveonline.com/alliances/99006042/
99006043	SINXY	Sinister Conspiracy	8	https://crest-tq.eveonline.com/alliances/99006043/
99006044	NOSCU	No criminal scum allowed	8	https://crest-tq.eveonline.com/alliances/99006044/
99006045	P45	Complaints Department	8	https://crest-tq.eveonline.com/alliances/99006045/
99006049	BERGF	Bergmann Federation	8	https://crest-tq.eveonline.com/alliances/99006049/
99006050	BSE	Binary Star Enterprises	8	https://crest-tq.eveonline.com/alliances/99006050/
99006061	3DOTS	Dot Dot Dot	8	https://crest-tq.eveonline.com/alliances/99006061/
99006063	ATYPE	Archetype.	8	https://crest-tq.eveonline.com/alliances/99006063/
99006067	CLEAN	Viscera Cleanup Detail	8	https://crest-tq.eveonline.com/alliances/99006067/
99006069	TIKLE	Tactical Supremacy	8	https://crest-tq.eveonline.com/alliances/99006069/
99006074	SEOE	Sacred Empire of Ellyssium	8	https://crest-tq.eveonline.com/alliances/99006074/
99006075	BNMA	BlackNova Mercenary Alliance	8	https://crest-tq.eveonline.com/alliances/99006075/
99006079	BALD.	Brazilian Wax Co Space Invaders	8	https://crest-tq.eveonline.com/alliances/99006079/
99006082	LUMIN	Luminari Conglomerate	8	https://crest-tq.eveonline.com/alliances/99006082/
99006087	GRNPC	Sustainable Whaling Inc.	8	https://crest-tq.eveonline.com/alliances/99006087/
99006090	TIPS	The Intergalactic Panic Syndicate	8	https://crest-tq.eveonline.com/alliances/99006090/
99006093	VIOL8	Violence of Action.	8	https://crest-tq.eveonline.com/alliances/99006093/
99006096	LOOOB	Any Hoal Is A Goal	8	https://crest-tq.eveonline.com/alliances/99006096/
99006097	ADC	Aulari Defense Consortium	8	https://crest-tq.eveonline.com/alliances/99006097/
99006100	U.C.F	Upwell Financial Conglomerate	8	https://crest-tq.eveonline.com/alliances/99006100/
99006101	N0VA	The Nova Coalition	8	https://crest-tq.eveonline.com/alliances/99006101/
99006103	SHOCK	Electroshock Therapy	8	https://crest-tq.eveonline.com/alliances/99006103/
99006104	SPICE	Serious People in Capsules exploring	8	https://crest-tq.eveonline.com/alliances/99006104/
99006107	FUBR	FUBR Dominion	8	https://crest-tq.eveonline.com/alliances/99006107/
99006109	.M.	Manifesto.	8	https://crest-tq.eveonline.com/alliances/99006109/
99006111	DOMN8	Uncommon Denominator	8	https://crest-tq.eveonline.com/alliances/99006111/
99006112	C0RE	Friendly Probes	8	https://crest-tq.eveonline.com/alliances/99006112/
99006113	NV	No Vacancies.	8	https://crest-tq.eveonline.com/alliances/99006113/
99006115	SERSY	Serenisima Syndicus	8	https://crest-tq.eveonline.com/alliances/99006115/
99006116	CAGE.	Animal Farm.	8	https://crest-tq.eveonline.com/alliances/99006116/
99006117	EMBR.	Ember Sands	8	https://crest-tq.eveonline.com/alliances/99006117/
99006120	-AEA-	Aggressive Exploration Holding Alliance	8	https://crest-tq.eveonline.com/alliances/99006120/
99006123	PANZ	Panzer Blitzkrieg.	8	https://crest-tq.eveonline.com/alliances/99006123/
99006124	LYNNA	Sisters of XVII Alliance	8	https://crest-tq.eveonline.com/alliances/99006124/
99006125	ARGGG	SLYCE Pirates	8	https://crest-tq.eveonline.com/alliances/99006125/
99006131	T-U-C	The Underworld Consortium	8	https://crest-tq.eveonline.com/alliances/99006131/
99006132	BWI	Bad Wolf Industries Inc	8	https://crest-tq.eveonline.com/alliances/99006132/
99006135	UMBRG	Umbra Group	8	https://crest-tq.eveonline.com/alliances/99006135/
99006138	KATNA	SAMURAI SOUL'd OUT	8	https://crest-tq.eveonline.com/alliances/99006138/
99006140	TCC	The Crimson Consortium	8	https://crest-tq.eveonline.com/alliances/99006140/
99006142	GLOVE	The Gangsters of Love	8	https://crest-tq.eveonline.com/alliances/99006142/
99006144	PART	Partisanen	8	https://crest-tq.eveonline.com/alliances/99006144/
99006146	SOTU	Spirit of the Universe	8	https://crest-tq.eveonline.com/alliances/99006146/
99006148	VOICE	Voice of the Void	8	https://crest-tq.eveonline.com/alliances/99006148/
99006157	WY	Weyland Industries.	8	https://crest-tq.eveonline.com/alliances/99006157/
99006159	MOGUL	Mogul Financial	8	https://crest-tq.eveonline.com/alliances/99006159/
99006166	JPMCH	JP Morgan Chase Holdings	8	https://crest-tq.eveonline.com/alliances/99006166/
99006167	B0UR	Second Empire.	8	https://crest-tq.eveonline.com/alliances/99006167/
99006168	-ARC-	Arataka Research Consortium	8	https://crest-tq.eveonline.com/alliances/99006168/
99006171	M1LEY	Miley Cyrus Empire	8	https://crest-tq.eveonline.com/alliances/99006171/
99006172	COLD	The Black Parade Alliance	8	https://crest-tq.eveonline.com/alliances/99006172/
99006175	PORK	Porkpie Active	8	https://crest-tq.eveonline.com/alliances/99006175/
99006177	GRF	Gorathian Federation	8	https://crest-tq.eveonline.com/alliances/99006177/
99006178	WBG	Wolfsbrigade.	8	https://crest-tq.eveonline.com/alliances/99006178/
99006179	.PPTX	An Inconvenient Alliance	8	https://crest-tq.eveonline.com/alliances/99006179/
99006180	STUNT	Je Suis Stunt Flores	8	https://crest-tq.eveonline.com/alliances/99006180/
99006181	0-FUX	ZER0 FUX GIVEN	8	https://crest-tq.eveonline.com/alliances/99006181/
99006183	COST	Consortium of Space Travelers	8	https://crest-tq.eveonline.com/alliances/99006183/
99006186	FEUER	Feuerreich	8	https://crest-tq.eveonline.com/alliances/99006186/
99006187	ION	Interstellar Ogre Nation	8	https://crest-tq.eveonline.com/alliances/99006187/
99006189	SDLAW	Sods-Law	8	https://crest-tq.eveonline.com/alliances/99006189/
99006191	MEME6	Meme. Team. 6.	8	https://crest-tq.eveonline.com/alliances/99006191/
99006192	MNVE	Minerva Exalt Holdings	8	https://crest-tq.eveonline.com/alliances/99006192/
99006194	IMBRU	Imbrum Armada	8	https://crest-tq.eveonline.com/alliances/99006194/
99006197	T.I.C	Tactical Interstellar Command	8	https://crest-tq.eveonline.com/alliances/99006197/
99006199	VSHK	Project Vashkryaba	8	https://crest-tq.eveonline.com/alliances/99006199/
99006200	TAA	The Anubis Accord	8	https://crest-tq.eveonline.com/alliances/99006200/
99006201	DEADM	DeaDMan Wonderland Alliance	8	https://crest-tq.eveonline.com/alliances/99006201/
99006202	DRK	Dark Rangers	8	https://crest-tq.eveonline.com/alliances/99006202/
99006204	ANONY	Anonymous..	8	https://crest-tq.eveonline.com/alliances/99006204/
99006207	AA-CO	Asylum Consortium	8	https://crest-tq.eveonline.com/alliances/99006207/
99006211	VOP	Violently Optimistic Alliance	8	https://crest-tq.eveonline.com/alliances/99006211/
99006213	RAWDG	Wrong Hole.	8	https://crest-tq.eveonline.com/alliances/99006213/
99006214	0FUX	Nullfux Alliance	8	https://crest-tq.eveonline.com/alliances/99006214/
99006215	ALLY	Allied Industries	8	https://crest-tq.eveonline.com/alliances/99006215/
99006217	Z3R	Z3r0 Matter	8	https://crest-tq.eveonline.com/alliances/99006217/
99006218	ANTS	A N T H I L L	8	https://crest-tq.eveonline.com/alliances/99006218/
99006221	PND	Pandora's-Box	8	https://crest-tq.eveonline.com/alliances/99006221/
99006223	HP	Haighare Pirates	8	https://crest-tq.eveonline.com/alliances/99006223/
99006225	0MS	Rote Works	8	https://crest-tq.eveonline.com/alliances/99006225/
99006227	FAS	Freedom Among the Stars	8	https://crest-tq.eveonline.com/alliances/99006227/
99006229	JSWWR	Skalmeye Tidal Race	8	https://crest-tq.eveonline.com/alliances/99006229/
99006231	PINUP	Pinnacle Federation	8	https://crest-tq.eveonline.com/alliances/99006231/
99006232	NBFU	No Blue For You	8	https://crest-tq.eveonline.com/alliances/99006232/
99006233	-LLA-	Limited Liability Alliance	8	https://crest-tq.eveonline.com/alliances/99006233/
99006234	P-U-D	THE PART of DARKNESS UNIVERSE	8	https://crest-tq.eveonline.com/alliances/99006234/
99006235	-NTV-	Negative Waves	8	https://crest-tq.eveonline.com/alliances/99006235/
99006237	-F1-	Failed Diplomacy.	8	https://crest-tq.eveonline.com/alliances/99006237/
99006239	R.D.C	Red Dream Citizens	8	https://crest-tq.eveonline.com/alliances/99006239/
99006240	AHHH	Bisto Kids	8	https://crest-tq.eveonline.com/alliances/99006240/
99006241	CBF	Crispy Bacon Federation	8	https://crest-tq.eveonline.com/alliances/99006241/
99006242	COF	COF Alliance	8	https://crest-tq.eveonline.com/alliances/99006242/
99006243	OMEN.	Omen Inc.	8	https://crest-tq.eveonline.com/alliances/99006243/
99006246	PIXEM	Pixels Empire	8	https://crest-tq.eveonline.com/alliances/99006246/
99006249	DSS	DISASTER Delivery Service	8	https://crest-tq.eveonline.com/alliances/99006249/
99006250	V-F	Vanguard Fleet	8	https://crest-tq.eveonline.com/alliances/99006250/
99006251	SRDEV	The Strategic Development Group	8	https://crest-tq.eveonline.com/alliances/99006251/
99006254	CAUC.	White Legion.	8	https://crest-tq.eveonline.com/alliances/99006254/
99006255	INER	INTERNET SPACESHIP.	8	https://crest-tq.eveonline.com/alliances/99006255/
99006260	REPUB	The Republic.	8	https://crest-tq.eveonline.com/alliances/99006260/
99006261	WC	Wormhole Coalition	8	https://crest-tq.eveonline.com/alliances/99006261/
99006265	AGGR0	Aggression.	8	https://crest-tq.eveonline.com/alliances/99006265/
99006266	LCH	Le Consortium Horizon	8	https://crest-tq.eveonline.com/alliances/99006266/
99006267	RAGNA	Gleam of Ragnarok	8	https://crest-tq.eveonline.com/alliances/99006267/
99006268	CCK	Coalition of Carebear Killers	8	https://crest-tq.eveonline.com/alliances/99006268/
99006270	NATSA	National Space Alliance	8	https://crest-tq.eveonline.com/alliances/99006270/
99006271	4BOB	Sanity is for the Weak	8	https://crest-tq.eveonline.com/alliances/99006271/
99006272	GHOUL	The Devils' Rejects	8	https://crest-tq.eveonline.com/alliances/99006272/
99006273	ELH	Eridani Light Horse.	8	https://crest-tq.eveonline.com/alliances/99006273/
99006275	MOFO.	Masters of Flying Objects	8	https://crest-tq.eveonline.com/alliances/99006275/
99006277	HDN	Hunde der Nacht	8	https://crest-tq.eveonline.com/alliances/99006277/
99006279	DEATH	Death Machine.	8	https://crest-tq.eveonline.com/alliances/99006279/
99006280	DSA	Drifting Sleeper Alliance	8	https://crest-tq.eveonline.com/alliances/99006280/
99006281	-IS-	Ish-Stars	8	https://crest-tq.eveonline.com/alliances/99006281/
99006287	URKC	Urukian Collective	8	https://crest-tq.eveonline.com/alliances/99006287/
99006289	TRV	TRUE VINE	8	https://crest-tq.eveonline.com/alliances/99006289/
99006291	LIBE	Liberators of new eden	8	https://crest-tq.eveonline.com/alliances/99006291/
99006292	AMPD	Amplified.	8	https://crest-tq.eveonline.com/alliances/99006292/
99006293	ILLM.	The Illuminated Miner's	8	https://crest-tq.eveonline.com/alliances/99006293/
99006294	HONDA	Hang On Not Done Accelerating	8	https://crest-tq.eveonline.com/alliances/99006294/
99006295	ASSET	Clockwork AM	8	https://crest-tq.eveonline.com/alliances/99006295/
99006297	WALKA	DRONE WALKERS	8	https://crest-tq.eveonline.com/alliances/99006297/
99006300	BYOSS	Bring Your Own Ship Stupid	8	https://crest-tq.eveonline.com/alliances/99006300/
99006302	BUTT	The Buttstalion	8	https://crest-tq.eveonline.com/alliances/99006302/
99006303	BANK	Van Lanschot	8	https://crest-tq.eveonline.com/alliances/99006303/
99006306	NA-CL	Salt the Earth	8	https://crest-tq.eveonline.com/alliances/99006306/
99006308	SPOOK	Spooky Wormhole Goblins	8	https://crest-tq.eveonline.com/alliances/99006308/
99006309	STELA	STEL Academ	8	https://crest-tq.eveonline.com/alliances/99006309/
99006310	TERF	Terrestrial Fallout	8	https://crest-tq.eveonline.com/alliances/99006310/
99006313	SYNTH	Synthetic Life	8	https://crest-tq.eveonline.com/alliances/99006313/
99006314	R-O-A	Rise of Apocalypse	8	https://crest-tq.eveonline.com/alliances/99006314/
99006317	ISS	Interstellar Starbase Syndicate	9	https://crest-tq.eveonline.com/alliances/99006317/
99006318	CARTE	Pure Blind Cartel	9	https://crest-tq.eveonline.com/alliances/99006318/
99006319	WDN	WiNGSPAN Delivery Network	9	https://crest-tq.eveonline.com/alliances/99006319/
99006320	A4D	Appetite 4 Destruction.	9	https://crest-tq.eveonline.com/alliances/99006320/
99006321	BLK-T	BIack Tie Affairs	9	https://crest-tq.eveonline.com/alliances/99006321/
99006326	INRI	Play Hard Pray Harder	9	https://crest-tq.eveonline.com/alliances/99006326/
99006327	OLDS	Old Sch00l	9	https://crest-tq.eveonline.com/alliances/99006327/
99006331	R-U1N	PVRGATORIE.	9	https://crest-tq.eveonline.com/alliances/99006331/
99006333	RIZE	Recognize	9	https://crest-tq.eveonline.com/alliances/99006333/
99006334	EVIL	Evil At Work	9	https://crest-tq.eveonline.com/alliances/99006334/
99006336	IKEA.	Interstellar Krabbing Enforcement Agency	9	https://crest-tq.eveonline.com/alliances/99006336/
99006343	LORDE	Lord of Worlds Alliance	9	https://crest-tq.eveonline.com/alliances/99006343/
99006344	-RBL-	Rebel Squad	9	https://crest-tq.eveonline.com/alliances/99006344/
99006346	SALT.	Salty.	9	https://crest-tq.eveonline.com/alliances/99006346/
99006347	LBOTA	Wiking Were Wabbits	9	https://crest-tq.eveonline.com/alliances/99006347/
99006348	TYOOL	Of Course l Still Love You	9	https://crest-tq.eveonline.com/alliances/99006348/
99006350	PARMO	Empire of the Chicken Parmo	9	https://crest-tq.eveonline.com/alliances/99006350/
99006351	WAGON	Smokewagon Federation	9	https://crest-tq.eveonline.com/alliances/99006351/
99006352	WSHOT	Wings Wanderers	9	https://crest-tq.eveonline.com/alliances/99006352/
99006353	TLDR	Too Long Didn't Read.	9	https://crest-tq.eveonline.com/alliances/99006353/
99006355	WWSKY	Wenches and Whisky	9	https://crest-tq.eveonline.com/alliances/99006355/
99006356	BADI	Badfellas Inc.	9	https://crest-tq.eveonline.com/alliances/99006356/
99006357	LUNA	Deae Lunae	9	https://crest-tq.eveonline.com/alliances/99006357/
99006359	NUTZ	SANITARIVM	9	https://crest-tq.eveonline.com/alliances/99006359/
99006360	AEGI	Aegis Armada	9	https://crest-tq.eveonline.com/alliances/99006360/
99006362	MYMOM	My Mom Says Blobbing Is Bad	9	https://crest-tq.eveonline.com/alliances/99006362/
99006363	MACHE	Machiavellian Exercitus	9	https://crest-tq.eveonline.com/alliances/99006363/
99006364	-DFP-	Das Fornax Protektorat	9	https://crest-tq.eveonline.com/alliances/99006364/
99006369	TEEMO	The Result of Drinking	9	https://crest-tq.eveonline.com/alliances/99006369/
99006370	FMA	Free Market Alliance	9	https://crest-tq.eveonline.com/alliances/99006370/
99006371	FARMD	Simple Farmers	9	https://crest-tq.eveonline.com/alliances/99006371/
99006372	DE-D	Emergency Escape Pod Services	9	https://crest-tq.eveonline.com/alliances/99006372/
99006373	SPCTR	Spectre Fleet Alliance	9	https://crest-tq.eveonline.com/alliances/99006373/
99006375	CLOWN	The Clown Car	9	https://crest-tq.eveonline.com/alliances/99006375/
99006377	THINK	ThunderKings Alliance	9	https://crest-tq.eveonline.com/alliances/99006377/
99006380	UNI	United Storm Front	9	https://crest-tq.eveonline.com/alliances/99006380/
99006382	INST	Interstellar State	9	https://crest-tq.eveonline.com/alliances/99006382/
99006383	METER	Space Parking Alliance	9	https://crest-tq.eveonline.com/alliances/99006383/
99006384	DUTCH	Infensus	9	https://crest-tq.eveonline.com/alliances/99006384/
99006385	SCAA	Sleeper Conservation Association	9	https://crest-tq.eveonline.com/alliances/99006385/
99006387	ORDER	Cerulean Order	9	https://crest-tq.eveonline.com/alliances/99006387/
99006391	ITSIS	When It Rises	9	https://crest-tq.eveonline.com/alliances/99006391/
99006394	SALT	The Salt Works.	9	https://crest-tq.eveonline.com/alliances/99006394/
99006395	MOME	Morning star material extraction	9	https://crest-tq.eveonline.com/alliances/99006395/
99006396	P1NGU	Penguin Mafia.	9	https://crest-tq.eveonline.com/alliances/99006396/
99006397	FBANE	Frostbane	9	https://crest-tq.eveonline.com/alliances/99006397/
99006398	FREE	Anarchy - mother of order.	9	https://crest-tq.eveonline.com/alliances/99006398/
99006401	ARMY	Army of New Eden	9	https://crest-tq.eveonline.com/alliances/99006401/
99006404	KEATF	K.E.A.T.S Federation	9	https://crest-tq.eveonline.com/alliances/99006404/
99006405	MAGA	Make Anoikis Great Again	9	https://crest-tq.eveonline.com/alliances/99006405/
99006406	FRT-U	Fraternity University	9	https://crest-tq.eveonline.com/alliances/99006406/
99006408	CRUOR	Cruoris Imperium	9	https://crest-tq.eveonline.com/alliances/99006408/
99006410	NIGHT	Knightmare Of New Eden	9	https://crest-tq.eveonline.com/alliances/99006410/
99006411	-NSH-	NullSechnaya Sholupen	9	https://crest-tq.eveonline.com/alliances/99006411/
99006413	FK-IT	Do.It.Live	9	https://crest-tq.eveonline.com/alliances/99006413/
99006414	SLAVE	Slaveholders	9	https://crest-tq.eveonline.com/alliances/99006414/
99006415	C.F.C	TheImperium	9	https://crest-tq.eveonline.com/alliances/99006415/
99006416	ME4U	Missed Connections	9	https://crest-tq.eveonline.com/alliances/99006416/
99006419	GALAC	Galactic Conqueror Alliance	9	https://crest-tq.eveonline.com/alliances/99006419/
99006421	SINS	Sinful Legion	9	https://crest-tq.eveonline.com/alliances/99006421/
99006423	SECSI	SECS Industries	9	https://crest-tq.eveonline.com/alliances/99006423/
99006424	CHAOS	Escalating Entropy	9	https://crest-tq.eveonline.com/alliances/99006424/
99006426	RCORR	Relics of Corruption	9	https://crest-tq.eveonline.com/alliances/99006426/
99006427	8BALL	Hookers And Blow.	9	https://crest-tq.eveonline.com/alliances/99006427/
99006430	MNM	Minmatar Lives Matter.	9	https://crest-tq.eveonline.com/alliances/99006430/
99006431	YOOJ	Huge In Japan	9	https://crest-tq.eveonline.com/alliances/99006431/
99006432	ASUA	ArrowStar United Alliance	9	https://crest-tq.eveonline.com/alliances/99006432/
99006433	BRO	Brotherhood of the Shadow Alliance	9	https://crest-tq.eveonline.com/alliances/99006433/
99006434	-AU-	Rapacity	9	https://crest-tq.eveonline.com/alliances/99006434/
99006438	DIVE	Digital Vendetta	9	https://crest-tq.eveonline.com/alliances/99006438/
99006440	ORIS	Oris Autarchia	9	https://crest-tq.eveonline.com/alliances/99006440/
99006444	LINKK	Lin Kuei Kokuryukai.	9	https://crest-tq.eveonline.com/alliances/99006444/
99006445	EGC	Gold Digger's	9	https://crest-tq.eveonline.com/alliances/99006445/
99006446	VIKK	Valhalla Conglomerate	9	https://crest-tq.eveonline.com/alliances/99006446/
99006447	WF	Without Fears	9	https://crest-tq.eveonline.com/alliances/99006447/
99006448	FAINT	Explosion.	9	https://crest-tq.eveonline.com/alliances/99006448/
99006450	DANES	Danorum Consortium	9	https://crest-tq.eveonline.com/alliances/99006450/
99006451	R-I-P	Rezone Industrial Park	9	https://crest-tq.eveonline.com/alliances/99006451/
99006454	XIIM	Twelve Monkeys.	9	https://crest-tq.eveonline.com/alliances/99006454/
99006455	CGE	CGE Alliance	9	https://crest-tq.eveonline.com/alliances/99006455/
99006457	HISS	Heimatar Ice Salvage and Structures	9	https://crest-tq.eveonline.com/alliances/99006457/
99006460	MORTE	Legio De Mortem	9	https://crest-tq.eveonline.com/alliances/99006460/
99006461	-IWD-	Independent Wormhole Dominion	9	https://crest-tq.eveonline.com/alliances/99006461/
99006462	-PLA-	Poitot Liberation Army	9	https://crest-tq.eveonline.com/alliances/99006462/
99006463	RLH	Russian Legion of Honor	9	https://crest-tq.eveonline.com/alliances/99006463/
99006464	O7HH	Dumb Crippled Alliance	9	https://crest-tq.eveonline.com/alliances/99006464/
99006466	TCA	The Comet Alliance	9	https://crest-tq.eveonline.com/alliances/99006466/
99006467	RUSKS	Red Unicorn Syndicate	9	https://crest-tq.eveonline.com/alliances/99006467/
99006468	AT1TM	VYDRA RELOLDED	9	https://crest-tq.eveonline.com/alliances/99006468/
99006469	ROME	The Romulans	9	https://crest-tq.eveonline.com/alliances/99006469/
99006470	KSA	Killswitch Alliance	9	https://crest-tq.eveonline.com/alliances/99006470/
99006472	BFG	Brute Force Solutions	9	https://crest-tq.eveonline.com/alliances/99006472/
99006473	TC	Tech Cartel Alliance	9	https://crest-tq.eveonline.com/alliances/99006473/
99006475	BKSTB	Renegades.	9	https://crest-tq.eveonline.com/alliances/99006475/
99006477	OUTBO	O U T B O U N D	9	https://crest-tq.eveonline.com/alliances/99006477/
99006478	CRWN	Crown of Swords Heavy Industies	9	https://crest-tq.eveonline.com/alliances/99006478/
99006479	NO	Nobody-Aly	9	https://crest-tq.eveonline.com/alliances/99006479/
99006482	SO	Insignificant Others	9	https://crest-tq.eveonline.com/alliances/99006482/
99006483	WW	Warp Walkers	9	https://crest-tq.eveonline.com/alliances/99006483/
99006484	INFN	Infinite Monkey Theorem	9	https://crest-tq.eveonline.com/alliances/99006484/
99006486	JANSH	Janssens Holding	9	https://crest-tq.eveonline.com/alliances/99006486/
99006487	LZSUB	Lazy Submarine	9	https://crest-tq.eveonline.com/alliances/99006487/
99006488	-OWL-	The Offensive Wing of League	9	https://crest-tq.eveonline.com/alliances/99006488/
99006489	CRIPT	Crypto Aliiance	9	https://crest-tq.eveonline.com/alliances/99006489/
99006491	PH	Phantasm Capital	9	https://crest-tq.eveonline.com/alliances/99006491/
99006493	EKP	Ekliptika	9	https://crest-tq.eveonline.com/alliances/99006493/
99006494	SPRKY	Demonic Wheat Pineapple	9	https://crest-tq.eveonline.com/alliances/99006494/
99006495	WRECK	Wrecking Machine.	9	https://crest-tq.eveonline.com/alliances/99006495/
99006498	SUNCG	Sunshine Conglomerate	9	https://crest-tq.eveonline.com/alliances/99006498/
99006499	KRIT	Critical Distortion	9	https://crest-tq.eveonline.com/alliances/99006499/
99006501	DIPLO	Gunboat Diplomats	9	https://crest-tq.eveonline.com/alliances/99006501/
99006502	TAE	THE ADAMCORE EMPIRE	9	https://crest-tq.eveonline.com/alliances/99006502/
99006503	IYI	Sons of Tangra	9	https://crest-tq.eveonline.com/alliances/99006503/
99006505	JSIG	Shattered Foundations	9	https://crest-tq.eveonline.com/alliances/99006505/
99006506	BRS	Brisingamen	9	https://crest-tq.eveonline.com/alliances/99006506/
99006507	ACORD	Asteria Concord.	9	https://crest-tq.eveonline.com/alliances/99006507/
99006508	QCALL	Quasar's Capital Alliance	9	https://crest-tq.eveonline.com/alliances/99006508/
99006509	ZHOON	Z H O O N	9	https://crest-tq.eveonline.com/alliances/99006509/
99006510	KIL-S	No Kill Too Small	9	https://crest-tq.eveonline.com/alliances/99006510/
99006511	CREED	Sicariorum	9	https://crest-tq.eveonline.com/alliances/99006511/
99006512	OPERA	Opera Mundi	9	https://crest-tq.eveonline.com/alliances/99006512/
99006515	KRAK	K R A K E N	9	https://crest-tq.eveonline.com/alliances/99006515/
99006516	TROL	LOLCATS	9	https://crest-tq.eveonline.com/alliances/99006516/
99006518	PBEAR	Polarbears.	9	https://crest-tq.eveonline.com/alliances/99006518/
99006521	DDOS	Divine Descendants of Sol	9	https://crest-tq.eveonline.com/alliances/99006521/
99006522	MORIA	MIneral Ore Reclamation Industrial Alliance	9	https://crest-tq.eveonline.com/alliances/99006522/
99006523	MARKE	Markonius Empire	9	https://crest-tq.eveonline.com/alliances/99006523/
99006524	RPI	Remote Panda Industries	9	https://crest-tq.eveonline.com/alliances/99006524/
99006525	GINT	Grey Intentions	9	https://crest-tq.eveonline.com/alliances/99006525/
99006526	IMAGE	Intergalactic Mining Guild	9	https://crest-tq.eveonline.com/alliances/99006526/
99006527	2-BIG	Tight Holes Big Poles	9	https://crest-tq.eveonline.com/alliances/99006527/
99006528	SCFA	Shadow Consortium Federation Alliance	9	https://crest-tq.eveonline.com/alliances/99006528/
99006529	SPLAT	Cannon.Fodder	9	https://crest-tq.eveonline.com/alliances/99006529/
99006532	WINDO	Window Lickers United	9	https://crest-tq.eveonline.com/alliances/99006532/
99006533	- I -	INQUISITION.	9	https://crest-tq.eveonline.com/alliances/99006533/
99006534	S4UCE	Panic Attack.	9	https://crest-tq.eveonline.com/alliances/99006534/
99006537	NOFUN	N0FUN	9	https://crest-tq.eveonline.com/alliances/99006537/
99006539	PULSR	Plerion	9	https://crest-tq.eveonline.com/alliances/99006539/
99006540	OATH	Vow of Valor	9	https://crest-tq.eveonline.com/alliances/99006540/
99006541	CRPL3	Oh Crip its A Crapple	9	https://crest-tq.eveonline.com/alliances/99006541/
99006542	FEVER	Foreverich Revelion	9	https://crest-tq.eveonline.com/alliances/99006542/
99006543	DEAD	DEAD HAND..	9	https://crest-tq.eveonline.com/alliances/99006543/
99006545	N35T	N.E.S.T Alliance	9	https://crest-tq.eveonline.com/alliances/99006545/
99006547	VOXIS	Voxis Accord	9	https://crest-tq.eveonline.com/alliances/99006547/
99006548	SCAN	Scanner Recalibrating	9	https://crest-tq.eveonline.com/alliances/99006548/
99006549	2PJC	Pipe Jumpers Consortium	9	https://crest-tq.eveonline.com/alliances/99006549/
99006550	CTRL	Control-Alt-Duvolle	9	https://crest-tq.eveonline.com/alliances/99006550/
99006552	SOFUK	Soldiers 0f Fortune	9	https://crest-tq.eveonline.com/alliances/99006552/
99006557	WI.	Wildly Inappropriate.	9	https://crest-tq.eveonline.com/alliances/99006557/
99006560	WOW	Wolves of Wallstreet	9	https://crest-tq.eveonline.com/alliances/99006560/
99006561	PCN2	The Praxus Consortium... Consortium	9	https://crest-tq.eveonline.com/alliances/99006561/
99006562	SEC9	Section.Nine	9	https://crest-tq.eveonline.com/alliances/99006562/
99006563	SWRD	Sword Diplomacy	9	https://crest-tq.eveonline.com/alliances/99006563/
99006564	DOTS.	Arguably The Best	9	https://crest-tq.eveonline.com/alliances/99006564/
99006565	VEMAF	Veldspar.Mafia	9	https://crest-tq.eveonline.com/alliances/99006565/
99006568	D-PIE	Certificate of Girth	9	https://crest-tq.eveonline.com/alliances/99006568/
99006569	GEWNZ	Goonswarm Federation.	9	https://crest-tq.eveonline.com/alliances/99006569/
99006570	HUMAN	Duality Of Men	9	https://crest-tq.eveonline.com/alliances/99006570/
99006571	GETOU	GET OUT OF MY WAY I PRESSED THE WRONG BUTTON	9	https://crest-tq.eveonline.com/alliances/99006571/
99006572	OYVEY	Multi-Shekel Media Conglomerate LLC	9	https://crest-tq.eveonline.com/alliances/99006572/
99006573	ILLUM	illuminated.cartel	9	https://crest-tq.eveonline.com/alliances/99006573/
99006575	THC-H	Trigger Happy Cats in Hats	9	https://crest-tq.eveonline.com/alliances/99006575/
99006576	CANES	Canes Inferno	9	https://crest-tq.eveonline.com/alliances/99006576/
99006579	SKOTO	Skotobaza	9	https://crest-tq.eveonline.com/alliances/99006579/
99006581	CIS	Cooperative Industrial Syndicate	9	https://crest-tq.eveonline.com/alliances/99006581/
99006583	TLFE	The Last From Earth	9	https://crest-tq.eveonline.com/alliances/99006583/
99006584	CARNE	Carne Por la Machina.	9	https://crest-tq.eveonline.com/alliances/99006584/
99006585	-FOG-	FOG Alliance	9	https://crest-tq.eveonline.com/alliances/99006585/
99006588	C-ANT	Cosmic Ants	9	https://crest-tq.eveonline.com/alliances/99006588/
99006589	MONKY	Evil Monkies Incorporated	9	https://crest-tq.eveonline.com/alliances/99006589/
99006592	BLIP	Chain of Fukkery	9	https://crest-tq.eveonline.com/alliances/99006592/
99006593	COVER	COVERT SENTINEL	9	https://crest-tq.eveonline.com/alliances/99006593/
99006594	NRDF	New Republic Defence Force	9	https://crest-tq.eveonline.com/alliances/99006594/
99006595	POND	Pondorium	9	https://crest-tq.eveonline.com/alliances/99006595/
99006597	.AVA.	Axiom Vocation Alliance	9	https://crest-tq.eveonline.com/alliances/99006597/
99006598	IKUSA	Ikusaro	9	https://crest-tq.eveonline.com/alliances/99006598/
99006600	UCORE	United CORE Federation	9	https://crest-tq.eveonline.com/alliances/99006600/
99006602	MERC	NECRONOMIKON	9	https://crest-tq.eveonline.com/alliances/99006602/
99006603	SIMP	Simplicissimus Eve	9	https://crest-tq.eveonline.com/alliances/99006603/
99006606	ImBA	Imperial Blaze Army	9	https://crest-tq.eveonline.com/alliances/99006606/
99006608	WMD	WMD Engineers Alliance	9	https://crest-tq.eveonline.com/alliances/99006608/
99006610	SURV	Survival Instinct	9	https://crest-tq.eveonline.com/alliances/99006610/
99006611	BYOBS	Bring Your Own Batphone	9	https://crest-tq.eveonline.com/alliances/99006611/
99006612	GM-O7	GreenMark	9	https://crest-tq.eveonline.com/alliances/99006612/
99006613	SAFE	Safe Harbour Alliance	9	https://crest-tq.eveonline.com/alliances/99006613/
99006614	ETANG	The Singularity.	9	https://crest-tq.eveonline.com/alliances/99006614/
99006615	BPA	Black Phoenix Alliance	9	https://crest-tq.eveonline.com/alliances/99006615/
99006617	COMON	Cwealth	9	https://crest-tq.eveonline.com/alliances/99006617/
99006619	KING	The Kingdom	9	https://crest-tq.eveonline.com/alliances/99006619/
99006620	DECAY	Entropic Decay.	9	https://crest-tq.eveonline.com/alliances/99006620/
99006621	ZEUS	Olympus Rising	9	https://crest-tq.eveonline.com/alliances/99006621/
99006624	CNTC	CENTCOM	9	https://crest-tq.eveonline.com/alliances/99006624/
99006625	WANGS	Pen Is Out	9	https://crest-tq.eveonline.com/alliances/99006625/
99006626	TC.	Terran Confederation.	9	https://crest-tq.eveonline.com/alliances/99006626/
99006629	PHUEE	Phules Privateers	9	https://crest-tq.eveonline.com/alliances/99006629/
99006630	DH-C	Deep Horizon Consortium	9	https://crest-tq.eveonline.com/alliances/99006630/
99006631	SPCON	Supernus Consortium	9	https://crest-tq.eveonline.com/alliances/99006631/
99006633	W - H	War - Horse	9	https://crest-tq.eveonline.com/alliances/99006633/
99006634	BEND	Benders.	9	https://crest-tq.eveonline.com/alliances/99006634/
99006635	-S3-	Stage 3	9	https://crest-tq.eveonline.com/alliances/99006635/
99006636	FEDS	Sarcos Federation	9	https://crest-tq.eveonline.com/alliances/99006636/
99006637	ICL	Imperial Crimson Legion	9	https://crest-tq.eveonline.com/alliances/99006637/
99006638	BLACK	Black Hand.	9	https://crest-tq.eveonline.com/alliances/99006638/
99006639	NANO-	NANOBLACK	9	https://crest-tq.eveonline.com/alliances/99006639/
99006640	THE W	THE WALL.	9	https://crest-tq.eveonline.com/alliances/99006640/
99006642	BREAK	Break A Wish Foundation	9	https://crest-tq.eveonline.com/alliances/99006642/
99006643	FS	Fates of the Stonecutters	9	https://crest-tq.eveonline.com/alliances/99006643/
99006645	D3MON	D 3 M O N	9	https://crest-tq.eveonline.com/alliances/99006645/
99006647	CHANU	Chanur Alliance	9	https://crest-tq.eveonline.com/alliances/99006647/
99006649	-PUR-	Peoples United Republic Empire	9	https://crest-tq.eveonline.com/alliances/99006649/
99006650	GETIN	The Society For Unethical Treatment Of Sleepers	9	https://crest-tq.eveonline.com/alliances/99006650/
99006652	KANDY	Free Candy Get In The Van Alliance	9	https://crest-tq.eveonline.com/alliances/99006652/
99006654	TRLL	We are trolls	9	https://crest-tq.eveonline.com/alliances/99006654/
99006657	MISSK	The Missing K	9	https://crest-tq.eveonline.com/alliances/99006657/
99006658	RIS3N	ZETSURIN HOSHI	9	https://crest-tq.eveonline.com/alliances/99006658/
99006659	UB	Barbaric Tribes	9	https://crest-tq.eveonline.com/alliances/99006659/
99006660	MFED	Moose Federation	9	https://crest-tq.eveonline.com/alliances/99006660/
99006661	FRCRY	Outlanders.	9	https://crest-tq.eveonline.com/alliances/99006661/
99006662	ITF	Interstellar Trade Federation	9	https://crest-tq.eveonline.com/alliances/99006662/
99006663	HAVEN	Shoreline	9	https://crest-tq.eveonline.com/alliances/99006663/
99006664	CAC	Caelum Accensum	9	https://crest-tq.eveonline.com/alliances/99006664/
99006666	EU	North Atlantic Treaty	9	https://crest-tq.eveonline.com/alliances/99006666/
99006668	EC	Earth . Confederation	9	https://crest-tq.eveonline.com/alliances/99006668/
99006669	TIPSY	Thoroughly Inebriated	9	https://crest-tq.eveonline.com/alliances/99006669/
99006670	G-FOR	G-Force Koalition	9	https://crest-tq.eveonline.com/alliances/99006670/
99006671	HMREZ	Holographic Mushroom	9	https://crest-tq.eveonline.com/alliances/99006671/
99006673	ASS	All-Star Syndicate	9	https://crest-tq.eveonline.com/alliances/99006673/
99006675	-TAA-	The Amarrian Ascendancy.	9	https://crest-tq.eveonline.com/alliances/99006675/
99006676	CRAZY	They're Coming to Take Me Away	9	https://crest-tq.eveonline.com/alliances/99006676/
99006677	CAREB	Carebear Cannibals	9	https://crest-tq.eveonline.com/alliances/99006677/
99006678	-BS-	Nigrarum Ovium	9	https://crest-tq.eveonline.com/alliances/99006678/
99006679	WAY	Synnatha	9	https://crest-tq.eveonline.com/alliances/99006679/
99006680	BSTD	Bastards Bay	9	https://crest-tq.eveonline.com/alliances/99006680/
99006681	TOP	The Overseer Project	9	https://crest-tq.eveonline.com/alliances/99006681/
99006682	DCIF	Directions Unclear	9	https://crest-tq.eveonline.com/alliances/99006682/
99006684	RI.T	Up in Arms.	9	https://crest-tq.eveonline.com/alliances/99006684/
99006686	MN	Metaphysical Necrosis	9	https://crest-tq.eveonline.com/alliances/99006686/
99006687	SHE	Shadow Elite.	9	https://crest-tq.eveonline.com/alliances/99006687/
99006689	-SXA-	Socially Awkward	9	https://crest-tq.eveonline.com/alliances/99006689/
99006690	MCAV	Mouth Trumpet Cavalry	9	https://crest-tq.eveonline.com/alliances/99006690/
99006692	N0SOV	Rejection Of Sovereignty	9	https://crest-tq.eveonline.com/alliances/99006692/
99006693	OOPS	Technical Difficulties	9	https://crest-tq.eveonline.com/alliances/99006693/
99006694	PSYKO	Caldari alliance 54683212	9	https://crest-tq.eveonline.com/alliances/99006694/
99006695	DUI	Deepspace Unlimited Inc Alliance	9	https://crest-tq.eveonline.com/alliances/99006695/
99006696	HERO	Heroes of the Massacre	9	https://crest-tq.eveonline.com/alliances/99006696/
99006697	DEUSV	Deus Vult.	9	https://crest-tq.eveonline.com/alliances/99006697/
99006698	NHIA	North Heimatar Interstellar Alliance	9	https://crest-tq.eveonline.com/alliances/99006698/
99006699	5W4RM	5W4RM	9	https://crest-tq.eveonline.com/alliances/99006699/
99006700	K.O.K	Imperium Divine.	9	https://crest-tq.eveonline.com/alliances/99006700/
99006701	DLW	Dark Lone Wolves	9	https://crest-tq.eveonline.com/alliances/99006701/
99006702	NWA	Nerds With Attitude	10	https://crest-tq.eveonline.com/alliances/99006702/
99006704	ERA00	EVE Republic Alliance	10	https://crest-tq.eveonline.com/alliances/99006704/
99006705	BELT1	Top Belt for Fun	10	https://crest-tq.eveonline.com/alliances/99006705/
99006706	DSB	De Samlede Boerneorme	10	https://crest-tq.eveonline.com/alliances/99006706/
99006708	RIPLO	Riplomacy	10	https://crest-tq.eveonline.com/alliances/99006708/
99006709	LOTEK	Lotek of New Eden	10	https://crest-tq.eveonline.com/alliances/99006709/
99006710	LOJ	KillJoys Alliance	10	https://crest-tq.eveonline.com/alliances/99006710/
99006712	DETOX	The Drunk and Disorderly	10	https://crest-tq.eveonline.com/alliances/99006712/
99006713	SABIK	Bleeders	10	https://crest-tq.eveonline.com/alliances/99006713/
99006714	TED	The Emerald Dream	10	https://crest-tq.eveonline.com/alliances/99006714/
99006716	N0NE	An Alliance Has No Name	10	https://crest-tq.eveonline.com/alliances/99006716/
99006717	INFMY	Infamous Partners	10	https://crest-tq.eveonline.com/alliances/99006717/
99006718	TANG	Turds Asshats N Griefers	10	https://crest-tq.eveonline.com/alliances/99006718/
99006720	-TUA-	T U A R E G	10	https://crest-tq.eveonline.com/alliances/99006720/
99006721	S.I.N	Moral Ambiguity	10	https://crest-tq.eveonline.com/alliances/99006721/
99006722	-OK-	O U T K A S T	10	https://crest-tq.eveonline.com/alliances/99006722/
99006723	MERKI	Mercy Killers	10	https://crest-tq.eveonline.com/alliances/99006723/
99006726	HSHOT	Hassle	10	https://crest-tq.eveonline.com/alliances/99006726/
99006727	COSPL	Circle of Salt	10	https://crest-tq.eveonline.com/alliances/99006727/
99006728	CTS	Catastrophic Experiment	10	https://crest-tq.eveonline.com/alliances/99006728/
99006729	AR15	The Black Crow Alliance	10	https://crest-tq.eveonline.com/alliances/99006729/
99006730	ON	Ocean's Night	10	https://crest-tq.eveonline.com/alliances/99006730/
99006731	SW-DA	Swords of Damocles	10	https://crest-tq.eveonline.com/alliances/99006731/
99006732	I	Iron Crescent	10	https://crest-tq.eveonline.com/alliances/99006732/
99006734	ICON	Integritas Constans	10	https://crest-tq.eveonline.com/alliances/99006734/
99006735	PHALA	Phalanx Federation	10	https://crest-tq.eveonline.com/alliances/99006735/
99006738	NUSIG	Spacetime Manifold	10	https://crest-tq.eveonline.com/alliances/99006738/
99006739	-BH-	Beyond Horizons.	10	https://crest-tq.eveonline.com/alliances/99006739/
99006741	TWNCO	Twin-Tech Cooperative	10	https://crest-tq.eveonline.com/alliances/99006741/
99006742	LI	Lethal Ignorance	10	https://crest-tq.eveonline.com/alliances/99006742/
99006743	REDLN	Maximum Pressure	10	https://crest-tq.eveonline.com/alliances/99006743/
99006744	DI SH	Di Shi Inc. Alliance	10	https://crest-tq.eveonline.com/alliances/99006744/
99006745	0MBIL	L I M B O	10	https://crest-tq.eveonline.com/alliances/99006745/
99006746	NYX	Nyx Legion	10	https://crest-tq.eveonline.com/alliances/99006746/
99006747	V01D	V. O. I. D.	10	https://crest-tq.eveonline.com/alliances/99006747/
99006748	DD	Domestic Disturbance	10	https://crest-tq.eveonline.com/alliances/99006748/
99006749	SUBSP	Subspace Field	10	https://crest-tq.eveonline.com/alliances/99006749/
99006750	TNA	The Nameless Alliance	10	https://crest-tq.eveonline.com/alliances/99006750/
99006751	WR0NG	What Could Possibly Go Wr0ng	10	https://crest-tq.eveonline.com/alliances/99006751/
99006752	HATS	Men with Fancy Hats	10	https://crest-tq.eveonline.com/alliances/99006752/
99006753	QUSP	Querious Slave Pens	10	https://crest-tq.eveonline.com/alliances/99006753/
99006754	CSS	Crazy and Strange Syndicate	10	https://crest-tq.eveonline.com/alliances/99006754/
99006755	COKA	Consortium of Kor-Azor	10	https://crest-tq.eveonline.com/alliances/99006755/
99006756	-DNA-	Da Imbalance	10	https://crest-tq.eveonline.com/alliances/99006756/
99006757	COER	Coercion Phenomenon	10	https://crest-tq.eveonline.com/alliances/99006757/
99006758	ISKHR	ISK Effectiveness	10	https://crest-tq.eveonline.com/alliances/99006758/
99006759	UPCON	UpweIl Consortium	10	https://crest-tq.eveonline.com/alliances/99006759/
99006760	STRL	Starlight Federation	10	https://crest-tq.eveonline.com/alliances/99006760/
99006761	USALL	Useless Alliance	10	https://crest-tq.eveonline.com/alliances/99006761/
99006762	SHIR1	LeaveThisBlank.	10	https://crest-tq.eveonline.com/alliances/99006762/
99006763	RUM	Rum and Vodka	10	https://crest-tq.eveonline.com/alliances/99006763/
99006764	NOOBS	Entosers Without Boarders	10	https://crest-tq.eveonline.com/alliances/99006764/
99006765	EXPLT	Clever Use of Game Mechanics.	10	https://crest-tq.eveonline.com/alliances/99006765/
99006768	C.O.A	Citys of Angels	10	https://crest-tq.eveonline.com/alliances/99006768/
99006769	T4NKD	Far From Sober Alliance	10	https://crest-tq.eveonline.com/alliances/99006769/
99006770	DUCKS	Addicted To Quack	10	https://crest-tq.eveonline.com/alliances/99006770/
99006771	IMGAY	I too am gay	10	https://crest-tq.eveonline.com/alliances/99006771/
99006772	MLI	Mid-Life ISIS	10	https://crest-tq.eveonline.com/alliances/99006772/
99006773	COONS	Angry Raccoons	10	https://crest-tq.eveonline.com/alliances/99006773/
99006774	LBF	Lonetrek Bank Holdings	10	https://crest-tq.eveonline.com/alliances/99006774/
99006775	POKSU	Poksu Mineral Group Alliance	10	https://crest-tq.eveonline.com/alliances/99006775/
99006776	BLAKD	The Whorphanage	10	https://crest-tq.eveonline.com/alliances/99006776/
99006777	ASS.	Awoken Spaceballz Syndicate	10	https://crest-tq.eveonline.com/alliances/99006777/
99006778	WTO	New Eden Trading Company.	10	https://crest-tq.eveonline.com/alliances/99006778/
99006779	SLOTH	Getting There... Eventually...	10	https://crest-tq.eveonline.com/alliances/99006779/
99006780	2DADS	Reckless Ambition	10	https://crest-tq.eveonline.com/alliances/99006780/
99006781	SCHTY	SolneChny SisTemy	10	https://crest-tq.eveonline.com/alliances/99006781/
99006783	D420	DuSt4.2.0	10	https://crest-tq.eveonline.com/alliances/99006783/
99006784	REAL	Real Enemy of Angel Cartel	10	https://crest-tq.eveonline.com/alliances/99006784/
99006785	JUI-C	Gimme Da Loot	10	https://crest-tq.eveonline.com/alliances/99006785/
99006786	SOUTH	Southern EVE Empire.	10	https://crest-tq.eveonline.com/alliances/99006786/
99006787	KHNRE	Khanid Republic	10	https://crest-tq.eveonline.com/alliances/99006787/
99006788	NOCLU	Who.	10	https://crest-tq.eveonline.com/alliances/99006788/
99006789	SPIN	Spatial Instability	10	https://crest-tq.eveonline.com/alliances/99006789/
99006790	NTCL	Night Council	10	https://crest-tq.eveonline.com/alliances/99006790/
99006791	SHADE	League of Shadow	10	https://crest-tq.eveonline.com/alliances/99006791/
99006792	GL0RY	Church Of The Glory Hole	10	https://crest-tq.eveonline.com/alliances/99006792/
99006793	MA	Mercenary Academy	10	https://crest-tq.eveonline.com/alliances/99006793/
99006794	ROMEO	Violent Gentlemen	10	https://crest-tq.eveonline.com/alliances/99006794/
99006795	ELITE	Top Tier	10	https://crest-tq.eveonline.com/alliances/99006795/
99006796	MPART	Moose Partners	10	https://crest-tq.eveonline.com/alliances/99006796/
99006797	PAN1C	Pandorum Invictus	10	https://crest-tq.eveonline.com/alliances/99006797/
99006798	SQUID	CALAMARRI	10	https://crest-tq.eveonline.com/alliances/99006798/
99006799	405	THE 405	10	https://crest-tq.eveonline.com/alliances/99006799/
99006800	TRRM	Terrarium	10	https://crest-tq.eveonline.com/alliances/99006800/
99006801	STAT	Strange Attractor	10	https://crest-tq.eveonline.com/alliances/99006801/
99006802	GANGC	Gankers Collective	10	https://crest-tq.eveonline.com/alliances/99006802/
99006803	-69	Pandenic Lefion	10	https://crest-tq.eveonline.com/alliances/99006803/
99006804	BMASS	The Black Mass Syndicate	10	https://crest-tq.eveonline.com/alliances/99006804/
99006805	WORMS	Wormageddon	10	https://crest-tq.eveonline.com/alliances/99006805/
99006806	UCD	Unreal Clan Deutschland	10	https://crest-tq.eveonline.com/alliances/99006806/
99006807	BSF	Black Sun Fleet	10	https://crest-tq.eveonline.com/alliances/99006807/
99006808	FACE	Face.Rollers	10	https://crest-tq.eveonline.com/alliances/99006808/
99006809	LGBT	Liquor Guns Bacon and Tits	10	https://crest-tq.eveonline.com/alliances/99006809/
99006810	KILL	SUICIDE-MARINES	10	https://crest-tq.eveonline.com/alliances/99006810/
99006811	T.GG	The Greater Good Alliance	10	https://crest-tq.eveonline.com/alliances/99006811/
99006812	WORM	Wormlife	10	https://crest-tq.eveonline.com/alliances/99006812/
99006813	HAWTH	Hawthorne Consortium	10	https://crest-tq.eveonline.com/alliances/99006813/
99006814	.SCC.	Shadow Crusaders Council	10	https://crest-tq.eveonline.com/alliances/99006814/
99006815	OCIC	Organized Crime in Chicago	10	https://crest-tq.eveonline.com/alliances/99006815/
99006816	ICAN	Lazy Stoner's Alliance	10	https://crest-tq.eveonline.com/alliances/99006816/
99006817	TMACE	TMA Corps of Engineering	10	https://crest-tq.eveonline.com/alliances/99006817/
99006818	CCA	collision course Alliance	10	https://crest-tq.eveonline.com/alliances/99006818/
101677770	SLAP	United Freaks	10	https://crest-tq.eveonline.com/alliances/101677770/
106665679	BMWG	Babylon Project	10	https://crest-tq.eveonline.com/alliances/106665679/
117383987	AFK	Silent Infinity	10	https://crest-tq.eveonline.com/alliances/117383987/
121218520	Z-CON	Zeta Conglomerate	10	https://crest-tq.eveonline.com/alliances/121218520/
127459462	FCU	Fastcart Unlimited	10	https://crest-tq.eveonline.com/alliances/127459462/
130011153	AVA	Avaricious Cartel	10	https://crest-tq.eveonline.com/alliances/130011153/
131511956	TNT	Tactical Narcotics Team	10	https://crest-tq.eveonline.com/alliances/131511956/
132100088	PURG	PURgE Alliance	10	https://crest-tq.eveonline.com/alliances/132100088/
139606862	FUBAR	The Dominion Empire	10	https://crest-tq.eveonline.com/alliances/139606862/
144068363	U.S.A	United Stellar Alliance	10	https://crest-tq.eveonline.com/alliances/144068363/
148157637	CMNS	Communitas	10	https://crest-tq.eveonline.com/alliances/148157637/
150097440	LAWN	Get Off My Lawn	10	https://crest-tq.eveonline.com/alliances/150097440/
151380924	.EXE	Executive Outcomes	10	https://crest-tq.eveonline.com/alliances/151380924/
154104258	APOC	Apocalypse Now.	10	https://crest-tq.eveonline.com/alliances/154104258/
156258636	FIXED	Fixed Realms	10	https://crest-tq.eveonline.com/alliances/156258636/
159826257	OTHER	Otherworld Empire	10	https://crest-tq.eveonline.com/alliances/159826257/
171737622	-CE-	Cataclysm Empire	10	https://crest-tq.eveonline.com/alliances/171737622/
173714703	PHEW	PURPLE HELMETED WARRIORS	10	https://crest-tq.eveonline.com/alliances/173714703/
173739862	- V -	Veritas Immortalis	10	https://crest-tq.eveonline.com/alliances/173739862/
179540294	THIS	This Is An Alliance	10	https://crest-tq.eveonline.com/alliances/179540294/
183416405	WIND	Wings 0f Destiny	10	https://crest-tq.eveonline.com/alliances/183416405/
184078709	SGS	Shugosha	10	https://crest-tq.eveonline.com/alliances/184078709/
184652162	ARMED	Armada Assail	10	https://crest-tq.eveonline.com/alliances/184652162/
188922853	AVX	Averos Alliance	10	https://crest-tq.eveonline.com/alliances/188922853/
194763922	DUKW	Ducks of the Wooden Leg	10	https://crest-tq.eveonline.com/alliances/194763922/
197490338	RIBIT	Anuran Origin	10	https://crest-tq.eveonline.com/alliances/197490338/
203305301	BLTA	Beacon Light Alliance	10	https://crest-tq.eveonline.com/alliances/203305301/
215451578	II	Infinite Innovation	10	https://crest-tq.eveonline.com/alliances/215451578/
223369706	CORE.	CORE.	10	https://crest-tq.eveonline.com/alliances/223369706/
227819104	MECH	Mech Alliance	10	https://crest-tq.eveonline.com/alliances/227819104/
236588152	-UA-	Unprovoked Aggression	10	https://crest-tq.eveonline.com/alliances/236588152/
236805034	PB	PROBABLE CAUSE	10	https://crest-tq.eveonline.com/alliances/236805034/
237669767	AMEN	Amen Anera	10	https://crest-tq.eveonline.com/alliances/237669767/
239739827	AM	Aegis Militia	10	https://crest-tq.eveonline.com/alliances/239739827/
240835459	VOLT	The Volition Cult	10	https://crest-tq.eveonline.com/alliances/240835459/
240845814	CORE	C. O. R. E.	10	https://crest-tq.eveonline.com/alliances/240845814/
246151739	SEA	Stellar Eclipse	10	https://crest-tq.eveonline.com/alliances/246151739/
253447610	ARROW	Omega Vector	10	https://crest-tq.eveonline.com/alliances/253447610/
254590421	-RAN-	Ranton Alliance	10	https://crest-tq.eveonline.com/alliances/254590421/
261982681	FREKS	Freaks In The Sheets	10	https://crest-tq.eveonline.com/alliances/261982681/
262507827	P M S	Permanent Mental Syndrome	10	https://crest-tq.eveonline.com/alliances/262507827/
264393054	-V-V-	Vae. Victis.	10	https://crest-tq.eveonline.com/alliances/264393054/
270840362	RV0LT	INDUSTRIAL REV0LUTI0N	10	https://crest-tq.eveonline.com/alliances/270840362/
273335890	3	Meracom	10	https://crest-tq.eveonline.com/alliances/273335890/
274440326	MNLT	Monolith Alliance	10	https://crest-tq.eveonline.com/alliances/274440326/
276564333	BC	Beyond-Control	10	https://crest-tq.eveonline.com/alliances/276564333/
284263464	U RAT	United Resistance Against Tyranny	10	https://crest-tq.eveonline.com/alliances/284263464/
284737480	PODED	Acheron Consortium	10	https://crest-tq.eveonline.com/alliances/284737480/
288377808	.-A-.	Against ALL Authorities	10	https://crest-tq.eveonline.com/alliances/288377808/
292686846	DST	Dustm3n	10	https://crest-tq.eveonline.com/alliances/292686846/
293593523	FE3C	STEEL BROTHERHOOD	10	https://crest-tq.eveonline.com/alliances/293593523/
294473158	ELITA	E L I T E Alliance	10	https://crest-tq.eveonline.com/alliances/294473158/
295773986	IRON	Imperial Republic Of the North	10	https://crest-tq.eveonline.com/alliances/295773986/
302766138	X	Xenology Federation	10	https://crest-tq.eveonline.com/alliances/302766138/
304965162	NOX	Nox Draconum	10	https://crest-tq.eveonline.com/alliances/304965162/
308375748	JVC	Joint Venture Conglomerate	10	https://crest-tq.eveonline.com/alliances/308375748/
315351548	ANIX	Anix's stables	10	https://crest-tq.eveonline.com/alliances/315351548/
316255419	KOSIN	Knights Of The Singularity	10	https://crest-tq.eveonline.com/alliances/316255419/
317115174	BDR	Bueckdich Reloaded	10	https://crest-tq.eveonline.com/alliances/317115174/
319175599	LUCKY	LUCKY LEAGUE	10	https://crest-tq.eveonline.com/alliances/319175599/
334762665	BOSS	Without Remorse.	10	https://crest-tq.eveonline.com/alliances/334762665/
338644120	KSRI	Kusari House	10	https://crest-tq.eveonline.com/alliances/338644120/
344936067	SEC8	Section Eight's	10	https://crest-tq.eveonline.com/alliances/344936067/
347057845	DSE	Deep Space Engineering	10	https://crest-tq.eveonline.com/alliances/347057845/
347718026	BREW	STR8NGE BREW	10	https://crest-tq.eveonline.com/alliances/347718026/
354185913	-DM-	Dead Muppets	10	https://crest-tq.eveonline.com/alliances/354185913/
356346890	PRFIT	Profiteers.	10	https://crest-tq.eveonline.com/alliances/356346890/
356370505	VA	Void Alliance	10	https://crest-tq.eveonline.com/alliances/356370505/
361506519	RURA	Rura-Penthe	10	https://crest-tq.eveonline.com/alliances/361506519/
364590997	SEALS	Space Exploration and Logistic Services	10	https://crest-tq.eveonline.com/alliances/364590997/
365679010	SMART	Unknown and Beyond	10	https://crest-tq.eveonline.com/alliances/365679010/
372230301	SEX	Stellar Economy Experts	10	https://crest-tq.eveonline.com/alliances/372230301/
376654009	V	Vaccaei Imperial	10	https://crest-tq.eveonline.com/alliances/376654009/
381065059	OOPZ	The Conglomeration of Ill Advised Ideas	10	https://crest-tq.eveonline.com/alliances/381065059/
381182162	TIN	Tin Can Alliance	10	https://crest-tq.eveonline.com/alliances/381182162/
382176353	-3-	T E R C I O S	10	https://crest-tq.eveonline.com/alliances/382176353/
382951222	FUN	Noob Fleet	10	https://crest-tq.eveonline.com/alliances/382951222/
383722348	ETHIC	Etherium Cartel	10	https://crest-tq.eveonline.com/alliances/383722348/
386149285	OPIAT	Black Opiate	10	https://crest-tq.eveonline.com/alliances/386149285/
386292982	-10.0	Pandemic Legion	10	https://crest-tq.eveonline.com/alliances/386292982/
394979878	MOLLE	Band of Brothers	10	https://crest-tq.eveonline.com/alliances/394979878/
395432221	SCC	Sin City Coalition	10	https://crest-tq.eveonline.com/alliances/395432221/
396211967	ARCLI	Arctic Light	10	https://crest-tq.eveonline.com/alliances/396211967/
398002761	TEARS	Tear Extraction And Reclamation Service	10	https://crest-tq.eveonline.com/alliances/398002761/
399527366	-AE-	ANAHEIM ELECTRONICS Alliance	10	https://crest-tq.eveonline.com/alliances/399527366/
400550691	BOE	The Servant Brothers of EVE	10	https://crest-tq.eveonline.com/alliances/400550691/
400712573	SENEX	Perihelion Alliance	10	https://crest-tq.eveonline.com/alliances/400712573/
400804622	DUSK	Twilight Federation	10	https://crest-tq.eveonline.com/alliances/400804622/
403491609	SOD	Shroud Of Darkness	10	https://crest-tq.eveonline.com/alliances/403491609/
403588742	WANKR	Purpose Built	10	https://crest-tq.eveonline.com/alliances/403588742/
404987119	XHALE	Exhale.	10	https://crest-tq.eveonline.com/alliances/404987119/
405309793	-V-.	vae Victis .	10	https://crest-tq.eveonline.com/alliances/405309793/
405507722	ICK	ICKARU5	10	https://crest-tq.eveonline.com/alliances/405507722/
405592709	SSL	Stainless.	10	https://crest-tq.eveonline.com/alliances/405592709/
414645273	COMA	Cosmic Maniacs	10	https://crest-tq.eveonline.com/alliances/414645273/
417262909	YAMA	Naraka.	10	https://crest-tq.eveonline.com/alliances/417262909/
420279241	R	RED.Union	10	https://crest-tq.eveonline.com/alliances/420279241/
421038446	3XXXD	General Tso's Alliance	10	https://crest-tq.eveonline.com/alliances/421038446/
429076011	TNEC	The New Eden Coalition	10	https://crest-tq.eveonline.com/alliances/429076011/
429360640	TALOS	Talos Coalition	10	https://crest-tq.eveonline.com/alliances/429360640/
431502563	KICK	Ministry of Inappropriate Footwork	10	https://crest-tq.eveonline.com/alliances/431502563/
433894153	NSA	Night Sky Alliance	10	https://crest-tq.eveonline.com/alliances/433894153/
434160213	-V-	Articles of Allegiance	10	https://crest-tq.eveonline.com/alliances/434160213/
434243723	C C P	C C P Alliance	10	https://crest-tq.eveonline.com/alliances/434243723/
442595339	STD	Sundiver Technology Diversified	10	https://crest-tq.eveonline.com/alliances/442595339/
443039986	GONAD	Gods of Night and Day	10	https://crest-tq.eveonline.com/alliances/443039986/
443043624	CEE	Confederate Economic Enterprises	10	https://crest-tq.eveonline.com/alliances/443043624/
444404720	F-D	Forged Dominion	10	https://crest-tq.eveonline.com/alliances/444404720/
445129978	ER	Erebus Alliance	10	https://crest-tq.eveonline.com/alliances/445129978/
446531551	PH.D	Pride - Honor - Duty	10	https://crest-tq.eveonline.com/alliances/446531551/
448511760	BOO	Dark Taboo	10	https://crest-tq.eveonline.com/alliances/448511760/
455603391	XXX	Exxxotic	10	https://crest-tq.eveonline.com/alliances/455603391/
457703173	E.Y	E.Y	10	https://crest-tq.eveonline.com/alliances/457703173/
459486691	BITE	H Y E N A	10	https://crest-tq.eveonline.com/alliances/459486691/
463833192	VMME	Vita Mihi Mors Est	10	https://crest-tq.eveonline.com/alliances/463833192/
466862619	CZ	CZECH Alliance	10	https://crest-tq.eveonline.com/alliances/466862619/
468455704	ZERO	SUB ZERO Alliance	10	https://crest-tq.eveonline.com/alliances/468455704/
473668485	OWTF	Workers Trade Federation	10	https://crest-tq.eveonline.com/alliances/473668485/
476626924	C-RUS	Carebears 'R Us	10	https://crest-tq.eveonline.com/alliances/476626924/
477769446	VIP	VooDoo Technologies	10	https://crest-tq.eveonline.com/alliances/477769446/
480218736	PHU	Pinch Harmonics United	10	https://crest-tq.eveonline.com/alliances/480218736/
486420224	SICK	War and Pestilence	10	https://crest-tq.eveonline.com/alliances/486420224/
487280191	TSL	The Star League	10	https://crest-tq.eveonline.com/alliances/487280191/
491350469	FRONT	The Obsidian Front	10	https://crest-tq.eveonline.com/alliances/491350469/
492994173	AA	Arbeitaholics Anonymous	10	https://crest-tq.eveonline.com/alliances/492994173/
495729389	SHDWC	Shadow Cartel	10	https://crest-tq.eveonline.com/alliances/495729389/
495752022	DONKY	The Donkey Rollers	10	https://crest-tq.eveonline.com/alliances/495752022/
497302911	.5.	The Five	10	https://crest-tq.eveonline.com/alliances/497302911/
498125261	TEST	Test Alliance Please Ignore	10	https://crest-tq.eveonline.com/alliances/498125261/
499005583	IM	Initiative Mercenaries	10	https://crest-tq.eveonline.com/alliances/499005583/
501163976	M-P	Moonless Peak	10	https://crest-tq.eveonline.com/alliances/501163976/
501588784	FTW	Fate Weavers	10	https://crest-tq.eveonline.com/alliances/501588784/
503818424	CCP-E	CCP Engineering Alliance	10	https://crest-tq.eveonline.com/alliances/503818424/
505495351	TYGRS	Tygris Alliance	10	https://crest-tq.eveonline.com/alliances/505495351/
507087050	ANTEC	Independent Antec Federation	10	https://crest-tq.eveonline.com/alliances/507087050/
527015334	EE	Eve Engineering	10	https://crest-tq.eveonline.com/alliances/527015334/
529564179	COSMO	Crusaders of Space	10	https://crest-tq.eveonline.com/alliances/529564179/
529714524	NEST	Empyrean Nest	10	https://crest-tq.eveonline.com/alliances/529714524/
530276089	-BAD-	Bad Boys Alliance	10	https://crest-tq.eveonline.com/alliances/530276089/
530394031	1440	PowerDucks Alliance	10	https://crest-tq.eveonline.com/alliances/530394031/
531048647	NMTZ	Namtz' aar K'in	10	https://crest-tq.eveonline.com/alliances/531048647/
534416112	WEH8U	Primary.	10	https://crest-tq.eveonline.com/alliances/534416112/
536867108	RA	Reciprocal Aegis	10	https://crest-tq.eveonline.com/alliances/536867108/
538572103	NEVER	New EVE Rising	10	https://crest-tq.eveonline.com/alliances/538572103/
541437166	S.C.	SPACE CONTINUUM	10	https://crest-tq.eveonline.com/alliances/541437166/
545770730	WRONG	Wrong Alliance	10	https://crest-tq.eveonline.com/alliances/545770730/
548639681	-CF-	C0LD Fusion	10	https://crest-tq.eveonline.com/alliances/548639681/
550516881	-E0S-	Empire of Serenity	11	https://crest-tq.eveonline.com/alliances/550516881/
551692893	HYDRA	HYDRA RELOADED	11	https://crest-tq.eveonline.com/alliances/551692893/
555959926	FURY	Vanguard.	11	https://crest-tq.eveonline.com/alliances/555959926/
557004256	ATAK	ROMANIAN-LEGION	11	https://crest-tq.eveonline.com/alliances/557004256/
565148621	META4	The Third Rail	11	https://crest-tq.eveonline.com/alliances/565148621/
567550367	N.G.T	Not Goats Toe	11	https://crest-tq.eveonline.com/alliances/567550367/
570124878	NEXO	N E X O	11	https://crest-tq.eveonline.com/alliances/570124878/
575461427	SPEC	Spectrum Alliance	11	https://crest-tq.eveonline.com/alliances/575461427/
579558963	-74-	TERMINATI0N	11	https://crest-tq.eveonline.com/alliances/579558963/
581235492	-NGI-	NOVA GOTISCHES IMPERIUM	11	https://crest-tq.eveonline.com/alliances/581235492/
582539665	AMSER	Amber Star Empire	11	https://crest-tq.eveonline.com/alliances/582539665/
583204357	OMFG	ORDUM Mining and Fabrication Group	11	https://crest-tq.eveonline.com/alliances/583204357/
583754435	MCKRS	The Mockers AO	11	https://crest-tq.eveonline.com/alliances/583754435/
585252126	EQUIL	Effective Equilibrium	11	https://crest-tq.eveonline.com/alliances/585252126/
586234421	SKY	Blue Sky Consortium	11	https://crest-tq.eveonline.com/alliances/586234421/
588312332	RASA	Nebula Rasa	11	https://crest-tq.eveonline.com/alliances/588312332/
596408142	THORN	THORN Alliance	11	https://crest-tq.eveonline.com/alliances/596408142/
596752585	NS	Nabaal Syndicate	11	https://crest-tq.eveonline.com/alliances/596752585/
601696983	FOCU	Focused Intentions	11	https://crest-tq.eveonline.com/alliances/601696983/
606251311	ISK	The Starshadow Project	11	https://crest-tq.eveonline.com/alliances/606251311/
607212516	UNIT	United Front Alliance	11	https://crest-tq.eveonline.com/alliances/607212516/
608839271	FDN	FOUNDATI0N	11	https://crest-tq.eveonline.com/alliances/608839271/
614765194	SWAT	space weaponry and trade	11	https://crest-tq.eveonline.com/alliances/614765194/
621338554	MUS	Most Usual Suspects	11	https://crest-tq.eveonline.com/alliances/621338554/
621423934	LIGHT	Avateas Blessed	11	https://crest-tq.eveonline.com/alliances/621423934/
628991027	RAWR	Morsus Mihi	11	https://crest-tq.eveonline.com/alliances/628991027/
655846070	OSS	The OSS	11	https://crest-tq.eveonline.com/alliances/655846070/
657191413	ETU	Eve Trade Union	11	https://crest-tq.eveonline.com/alliances/657191413/
658977196	BLUE	The Big Blue	11	https://crest-tq.eveonline.com/alliances/658977196/
664167896	AWA	Art of War Alliance	11	https://crest-tq.eveonline.com/alliances/664167896/
664570058	FIGL	Flying Dangerous	11	https://crest-tq.eveonline.com/alliances/664570058/
665776658	JP	Joint Power	11	https://crest-tq.eveonline.com/alliances/665776658/
666300228	NSTK	Napalm Sticks To Kids	11	https://crest-tq.eveonline.com/alliances/666300228/
670303846	STEEL	Cold Steel Alliance	11	https://crest-tq.eveonline.com/alliances/670303846/
671935258	PSFY	Caretakers	11	https://crest-tq.eveonline.com/alliances/671935258/
672901401	L E M	LEM Alliance	11	https://crest-tq.eveonline.com/alliances/672901401/
673381830	IRC	Intrepid Crossing	11	https://crest-tq.eveonline.com/alliances/673381830/
675031356	X13	X13 Alliance	11	https://crest-tq.eveonline.com/alliances/675031356/
677188138	EXALT	Exalted.	11	https://crest-tq.eveonline.com/alliances/677188138/
678900003	GEMMA	All the things she said	11	https://crest-tq.eveonline.com/alliances/678900003/
678953987	-A.E-	Amarrian Kingdom	11	https://crest-tq.eveonline.com/alliances/678953987/
679584932	SMA	SpaceMonkey's Alliance	11	https://crest-tq.eveonline.com/alliances/679584932/
694867971	OTHEI	Otherworld Empire Inventions	11	https://crest-tq.eveonline.com/alliances/694867971/
696361139	OTHEC	Otherworld Empire High Command	11	https://crest-tq.eveonline.com/alliances/696361139/
697492701	-T-	Tamanium	11	https://crest-tq.eveonline.com/alliances/697492701/
697794276	TMI	Twilight Military Industrial Complex Alliance	11	https://crest-tq.eveonline.com/alliances/697794276/
701459600	-EM-	Electus Matari	11	https://crest-tq.eveonline.com/alliances/701459600/
703129312	C H	Cry Havoc.	11	https://crest-tq.eveonline.com/alliances/703129312/
703430781	PECAA	Yellowpage Alliance	11	https://crest-tq.eveonline.com/alliances/703430781/
707482380	-DTS-	Destiny's Call	11	https://crest-tq.eveonline.com/alliances/707482380/
711143518	-UCF-	United Corporate Futures	11	https://crest-tq.eveonline.com/alliances/711143518/
711204655	CHVS.	Reckless Chavs	11	https://crest-tq.eveonline.com/alliances/711204655/
716050598	UNITE	United Corporations Of Modern Eve	11	https://crest-tq.eveonline.com/alliances/716050598/
718438830	H4X0R	Eych Four Eks Zero Ahr	11	https://crest-tq.eveonline.com/alliances/718438830/
719960272	DMENT	Dead Moon Enterprises.	11	https://crest-tq.eveonline.com/alliances/719960272/
720698301	MOBY	Mobile Infantry	11	https://crest-tq.eveonline.com/alliances/720698301/
723015129	OWN	OWN Alliance	11	https://crest-tq.eveonline.com/alliances/723015129/
726217292	ZWART	Pitch Black Legion	11	https://crest-tq.eveonline.com/alliances/726217292/
735243183	SRS	SRS.	11	https://crest-tq.eveonline.com/alliances/735243183/
741557221	-RZR-	RAZOR Alliance	11	https://crest-tq.eveonline.com/alliances/741557221/
743826619	LSD	Legionnaire Services Ltd.	11	https://crest-tq.eveonline.com/alliances/743826619/
748008253	CR	Crematoria.	11	https://crest-tq.eveonline.com/alliances/748008253/
748169569	SF	Storm Fronts	11	https://crest-tq.eveonline.com/alliances/748169569/
751434080	MUNDI	Novus Ordo Mundi	11	https://crest-tq.eveonline.com/alliances/751434080/
752401026	SCAL	Scum Alliance	11	https://crest-tq.eveonline.com/alliances/752401026/
754957243	TUNA	The Unforgiven Alliance	11	https://crest-tq.eveonline.com/alliances/754957243/
756772516	-8-	INFINITY.	11	https://crest-tq.eveonline.com/alliances/756772516/
758025458	OMC	Obsidian Mining Coalition	11	https://crest-tq.eveonline.com/alliances/758025458/
760953291	CUTE	Cute Alliance	11	https://crest-tq.eveonline.com/alliances/760953291/
763889495	M-A-T	M-A-T-R-I-X Allianz	11	https://crest-tq.eveonline.com/alliances/763889495/
764651116	PTI	Pator Tech	11	https://crest-tq.eveonline.com/alliances/764651116/
765796221	LF	Liberi Fatales	11	https://crest-tq.eveonline.com/alliances/765796221/
767394088	FEA	Free EVE Alliance	11	https://crest-tq.eveonline.com/alliances/767394088/
768686236	KKK	Klingon Killer Kommando	11	https://crest-tq.eveonline.com/alliances/768686236/
772922578	HAWK	HAWK Alliance	11	https://crest-tq.eveonline.com/alliances/772922578/
776692692	WHACK	Underworld Excavators	11	https://crest-tq.eveonline.com/alliances/776692692/
781059915	NZ	Nair Al-Zaurak	11	https://crest-tq.eveonline.com/alliances/781059915/
782696351	ANO1	A Number One	11	https://crest-tq.eveonline.com/alliances/782696351/
785719634	AE	Active Endeavors	11	https://crest-tq.eveonline.com/alliances/785719634/
796578073	EPFLT	Epsilon Fleet	11	https://crest-tq.eveonline.com/alliances/796578073/
807486236	E0N	Echoes of Nowhere	11	https://crest-tq.eveonline.com/alliances/807486236/
815745036	.BO.	Boese Onkels	11	https://crest-tq.eveonline.com/alliances/815745036/
817618922	UMTA	United Manufacturing and Technology Alliance	11	https://crest-tq.eveonline.com/alliances/817618922/
819696659	KULT	THE KULT	11	https://crest-tq.eveonline.com/alliances/819696659/
822746991	VETO	Veto Corp	11	https://crest-tq.eveonline.com/alliances/822746991/
824518128	OHGOD	GoonSwarm	11	https://crest-tq.eveonline.com/alliances/824518128/
828884805	UN1TY	Unitary Enterprises	11	https://crest-tq.eveonline.com/alliances/828884805/
832386547	STAND	TOGETHER WE STAND	11	https://crest-tq.eveonline.com/alliances/832386547/
837572529	ZEKI	Joined Brotherhood	11	https://crest-tq.eveonline.com/alliances/837572529/
845358580	OXIDE	O X I D E	11	https://crest-tq.eveonline.com/alliances/845358580/
849555477	GSA	G Spot Alliance	11	https://crest-tq.eveonline.com/alliances/849555477/
854214958	HEVEN	Outer Heaven	11	https://crest-tq.eveonline.com/alliances/854214958/
857871183	OCKER	Terra Australis Ignota	11	https://crest-tq.eveonline.com/alliances/857871183/
863687177	TMC	The Matari Consortium	11	https://crest-tq.eveonline.com/alliances/863687177/
864733958	TISHU	Psychotic Tendencies.	11	https://crest-tq.eveonline.com/alliances/864733958/
872090397	FNREG	Family Renegades	11	https://crest-tq.eveonline.com/alliances/872090397/
874530890	SIGDI	Sigma Draconis Imperium	11	https://crest-tq.eveonline.com/alliances/874530890/
877811706	ANIMA	Aeterna Anima	11	https://crest-tq.eveonline.com/alliances/877811706/
878776074	-IT-	IT Alliance	11	https://crest-tq.eveonline.com/alliances/878776074/
879123743	FRE	Free Traders of EVE	11	https://crest-tq.eveonline.com/alliances/879123743/
880013768	SLMR	Slammer's Republic	11	https://crest-tq.eveonline.com/alliances/880013768/
883289363	THOR-	Einherjar Alliance	11	https://crest-tq.eveonline.com/alliances/883289363/
887111650	UNCO	Universal Consortium	11	https://crest-tq.eveonline.com/alliances/887111650/
894436655	CURRY	Curry Alliance	11	https://crest-tq.eveonline.com/alliances/894436655/
894532143	TAO	Shaolin Empire	11	https://crest-tq.eveonline.com/alliances/894532143/
897877797	--U--	Unaffiliated	11	https://crest-tq.eveonline.com/alliances/897877797/
898617242	CHAOT	Chaos Theory Alliance	11	https://crest-tq.eveonline.com/alliances/898617242/
902819350	OTHEP	Otherworld Empire Productions	11	https://crest-tq.eveonline.com/alliances/902819350/
903584172	PIZI	Pizi Industrial and Zience INC Alliance	11	https://crest-tq.eveonline.com/alliances/903584172/
905861359	BR1CK	BricK sQuAD.	11	https://crest-tq.eveonline.com/alliances/905861359/
906457582	DADDY	The G0dfathers	11	https://crest-tq.eveonline.com/alliances/906457582/
906957604	RELAX	Hedonistic Imperative	11	https://crest-tq.eveonline.com/alliances/906957604/
912567626	DOGS	D0GS OF WAR	11	https://crest-tq.eveonline.com/alliances/912567626/
917526329	DOG	Death or Glory	11	https://crest-tq.eveonline.com/alliances/917526329/
922190997	TRUE	True Reign	11	https://crest-tq.eveonline.com/alliances/922190997/
927292903	STUGH	Rote Kapelle	11	https://crest-tq.eveonline.com/alliances/927292903/
927761486	ZGA	Zeitgeist Allianz	11	https://crest-tq.eveonline.com/alliances/927761486/
933731581	TRI	Triumvirate.	11	https://crest-tq.eveonline.com/alliances/933731581/
935962111	UNL	United Legion	11	https://crest-tq.eveonline.com/alliances/935962111/
936887062	PAXR	Preatoriani	11	https://crest-tq.eveonline.com/alliances/936887062/
937872513	IVY	Ivy League	11	https://crest-tq.eveonline.com/alliances/937872513/
937904219	NBSI	NBSI Alliance	11	https://crest-tq.eveonline.com/alliances/937904219/
939740899	NOOBZ	Noobswarm.	11	https://crest-tq.eveonline.com/alliances/939740899/
949658937	GILDE	Gilde Alliance	11	https://crest-tq.eveonline.com/alliances/949658937/
952720760	EARTH	E.A.R.T.H. Federation	11	https://crest-tq.eveonline.com/alliances/952720760/
956618410	CANIS	Jakkaru Reikon	11	https://crest-tq.eveonline.com/alliances/956618410/
958411823	T.C.A	Terran Commonwealth	11	https://crest-tq.eveonline.com/alliances/958411823/
960513040	VEYR	The Veyr Collective	11	https://crest-tq.eveonline.com/alliances/960513040/
961192363	NMG.	Noir. Mercenary Group	11	https://crest-tq.eveonline.com/alliances/961192363/
964540764	RUMAD	Rookie Empire	11	https://crest-tq.eveonline.com/alliances/964540764/
966819037	MOVIE	MPA	11	https://crest-tq.eveonline.com/alliances/966819037/
967138236	COV	Ark of the Covenant	11	https://crest-tq.eveonline.com/alliances/967138236/
969064285	NULL	Nihilists Social Club	11	https://crest-tq.eveonline.com/alliances/969064285/
969357963	DAI	Daisho Syndicate	11	https://crest-tq.eveonline.com/alliances/969357963/
970210493	HYDRO	Hydroponic Zone	11	https://crest-tq.eveonline.com/alliances/970210493/
975499322	EOTS	Empire of the Senses	11	https://crest-tq.eveonline.com/alliances/975499322/
976104766	ARE-S	Arekin Syndicate	11	https://crest-tq.eveonline.com/alliances/976104766/
982284363	-7-	Sev3rance	11	https://crest-tq.eveonline.com/alliances/982284363/
991077915	MEWS	Sonic Kitties Preservation Society	11	https://crest-tq.eveonline.com/alliances/991077915/
994621555	DIV.A	Divine Alliance	11	https://crest-tq.eveonline.com/alliances/994621555/
1001642281	-ASN-	Beyond Ascension	11	https://crest-tq.eveonline.com/alliances/1001642281/
1002884793	47R	THE R0NIN	11	https://crest-tq.eveonline.com/alliances/1002884793/
1002902117	SOL	FEDERATION SOLARIS	11	https://crest-tq.eveonline.com/alliances/1002902117/
1006830534	FCON	Fidelas Constans	11	https://crest-tq.eveonline.com/alliances/1006830534/
1009247549	IMP-9	Impulse-9	11	https://crest-tq.eveonline.com/alliances/1009247549/
1009585407	STORM	STORM.	11	https://crest-tq.eveonline.com/alliances/1009585407/
1009843019	FAF	Gas Giant Inter Global	11	https://crest-tq.eveonline.com/alliances/1009843019/
1010899803	UMBRA	Shadow Empire.	11	https://crest-tq.eveonline.com/alliances/1010899803/
1014190948	STRI	Striker Extreme Alliance	11	https://crest-tq.eveonline.com/alliances/1014190948/
1015198609	K162	K162	11	https://crest-tq.eveonline.com/alliances/1015198609/
1017570423	SWAN	Black Swan.	11	https://crest-tq.eveonline.com/alliances/1017570423/
1020010775	A	Abbach Alliance	11	https://crest-tq.eveonline.com/alliances/1020010775/
1025783526	TAXUA	TAXU	11	https://crest-tq.eveonline.com/alliances/1025783526/
1028876240	U-RA	Ultima Rati0	11	https://crest-tq.eveonline.com/alliances/1028876240/
1031637154	DS	DarkStorm Enterprises	11	https://crest-tq.eveonline.com/alliances/1031637154/
1033444832	COMMC	Commonwealth Coalition	11	https://crest-tq.eveonline.com/alliances/1033444832/
1035178122	QF	Quantum Forge	11	https://crest-tq.eveonline.com/alliances/1035178122/
1035358987	KANAZ	The Kanazawai	11	https://crest-tq.eveonline.com/alliances/1035358987/
1039527124	-LA-	Libera Alliance	11	https://crest-tq.eveonline.com/alliances/1039527124/
1041482450	HF	Huzzah Federation	11	https://crest-tq.eveonline.com/alliances/1041482450/
1041918556	KURO	Black Cartel.	11	https://crest-tq.eveonline.com/alliances/1041918556/
1042504553	SLYCE	Solyaris Chtonium	11	https://crest-tq.eveonline.com/alliances/1042504553/
1046448680	BAD	Blood Alliance of Dragons	11	https://crest-tq.eveonline.com/alliances/1046448680/
1047487587	AOF	Agents of Fortune	11	https://crest-tq.eveonline.com/alliances/1047487587/
1054925052	RECLM	The Reclaimers	11	https://crest-tq.eveonline.com/alliances/1054925052/
1060992628	HOLE.	Donkey Punch.	11	https://crest-tq.eveonline.com/alliances/1060992628/
1068155174	OHNO	Malum Exuro	11	https://crest-tq.eveonline.com/alliances/1068155174/
1071023976	GOD	Games of Divinity	11	https://crest-tq.eveonline.com/alliances/1071023976/
1072824520	BAB.5	Babylon 5..	11	https://crest-tq.eveonline.com/alliances/1072824520/
1081578607	DROOG	Clockwork Pineapple	11	https://crest-tq.eveonline.com/alliances/1081578607/
1086308227	RANE	Rebel Alliance of New Eden	11	https://crest-tq.eveonline.com/alliances/1086308227/
1086877681	COR.E	Corporate Evolution	11	https://crest-tq.eveonline.com/alliances/1086877681/
1097355312	DUNE	Dune Heretics	11	https://crest-tq.eveonline.com/alliances/1097355312/
1097844206	COOL	The Cool Kids Club	11	https://crest-tq.eveonline.com/alliances/1097844206/
1099063355	DP	Die Patrizier	11	https://crest-tq.eveonline.com/alliances/1099063355/
1110172951	UNSO	Unorthodox Solution	11	https://crest-tq.eveonline.com/alliances/1110172951/
1119479143	AGONY	Agony Empire	11	https://crest-tq.eveonline.com/alliances/1119479143/
1125500255	.GRIM	BROTHERS GRIM.	11	https://crest-tq.eveonline.com/alliances/1125500255/
1132738066	C.A.P	C.A.P. Empire	11	https://crest-tq.eveonline.com/alliances/1132738066/
1133052404	STARC	Star Council	11	https://crest-tq.eveonline.com/alliances/1133052404/
1133932561	HARM	Harmonious Ascent	11	https://crest-tq.eveonline.com/alliances/1133932561/
1134020769	A-F	Astromechanica Federatis	11	https://crest-tq.eveonline.com/alliances/1134020769/
1136157539	C0VEN	C0VEN	11	https://crest-tq.eveonline.com/alliances/1136157539/
1141440417	LILI	Rock Ridge Alliance	11	https://crest-tq.eveonline.com/alliances/1141440417/
1142939290	WEPOD	Pendulum of Doom	11	https://crest-tq.eveonline.com/alliances/1142939290/
1144310670	0MG	TORTUGA.	11	https://crest-tq.eveonline.com/alliances/1144310670/
1147488332	THOR	The Kadeshi	11	https://crest-tq.eveonline.com/alliances/1147488332/
1153795557	CFUSE	Cold Fusion.	11	https://crest-tq.eveonline.com/alliances/1153795557/
1163497953	ORION	Orion Consortium	11	https://crest-tq.eveonline.com/alliances/1163497953/
1165741768	-IXC-	eXceed.	11	https://crest-tq.eveonline.com/alliances/1165741768/
1167803747	JNAUT	Juggernaut.	11	https://crest-tq.eveonline.com/alliances/1167803747/
1169814428	ASH	Ash Alliance	11	https://crest-tq.eveonline.com/alliances/1169814428/
1170757804	GOTHM	Gotham Knights	11	https://crest-tq.eveonline.com/alliances/1170757804/
1177084653	RSB	Roam's Serious Business	11	https://crest-tq.eveonline.com/alliances/1177084653/
1178248549	-AZZ-	Corcoran State	11	https://crest-tq.eveonline.com/alliances/1178248549/
1179850400	UO	United Outworlders	11	https://crest-tq.eveonline.com/alliances/1179850400/
1182801433	ICE	On the Rocks	11	https://crest-tq.eveonline.com/alliances/1182801433/
1186465684	PIE	Praetoria Imperialis Excubitoris	11	https://crest-tq.eveonline.com/alliances/1186465684/
1188261112	GR	Green Rhino	11	https://crest-tq.eveonline.com/alliances/1188261112/
1189243859	ZULU	Zulu People	11	https://crest-tq.eveonline.com/alliances/1189243859/
1194892549	4TH	The Fourth District	11	https://crest-tq.eveonline.com/alliances/1194892549/
1197953359	YARR	Exiled Collective	11	https://crest-tq.eveonline.com/alliances/1197953359/
1198020766	A.I.F	C0NVICTED	11	https://crest-tq.eveonline.com/alliances/1198020766/
1198164123	D S	Dark Stripes	11	https://crest-tq.eveonline.com/alliances/1198164123/
1199535134	QOOKI	Quantum Cookies	11	https://crest-tq.eveonline.com/alliances/1199535134/
1203105975	HELM.	HELM Alliance	11	https://crest-tq.eveonline.com/alliances/1203105975/
1204148554	EWOKS	Ewoks	11	https://crest-tq.eveonline.com/alliances/1204148554/
1208295500	SOLAR	SOLAR FLEET	11	https://crest-tq.eveonline.com/alliances/1208295500/
1208682272	FLAPS	Cleveland Steamship Co.	11	https://crest-tq.eveonline.com/alliances/1208682272/
1212162615	PEST	Pest Control Union	11	https://crest-tq.eveonline.com/alliances/1212162615/
1213157674	IRS	Integrity Respect Selflessness	11	https://crest-tq.eveonline.com/alliances/1213157674/
1217643171	CEIM	Celestial Imperative	11	https://crest-tq.eveonline.com/alliances/1217643171/
1220055227	N-R	NUNQUAM RETRO	11	https://crest-tq.eveonline.com/alliances/1220055227/
1220174199	-FA-	Fatal Ascension	11	https://crest-tq.eveonline.com/alliances/1220174199/
1220922756	RED	Red Alliance	11	https://crest-tq.eveonline.com/alliances/1220922756/
1227673604	CD0KN	Concordokken.	11	https://crest-tq.eveonline.com/alliances/1227673604/
1228472388	R.O.L	RED.OverLord	11	https://crest-tq.eveonline.com/alliances/1228472388/
1230815227	DECA	DRACONIAN COVENANT	11	https://crest-tq.eveonline.com/alliances/1230815227/
1230969720	SU-SA	Seekers of the Unseen	11	https://crest-tq.eveonline.com/alliances/1230969720/
1237151392	IPI	Intaki Prosperity Initiative	11	https://crest-tq.eveonline.com/alliances/1237151392/
1239533763	ANWAR	AntiWar Coalition	11	https://crest-tq.eveonline.com/alliances/1239533763/
1252236189	PETS	Guinea Pigs	11	https://crest-tq.eveonline.com/alliances/1252236189/
1254162155	PAIN	Spartan Alliance	11	https://crest-tq.eveonline.com/alliances/1254162155/
1254379905	FOE	Friend or Enemy	11	https://crest-tq.eveonline.com/alliances/1254379905/
1256786938	.ESA.	Eve Service Alliance	11	https://crest-tq.eveonline.com/alliances/1256786938/
1258727717	ECHO	Reverberation Project	11	https://crest-tq.eveonline.com/alliances/1258727717/
1261345544	DACO	Dara Cothrom	11	https://crest-tq.eveonline.com/alliances/1261345544/
1264419820	WRATH	World Allegiance of The Hunt	11	https://crest-tq.eveonline.com/alliances/1264419820/
1267893400	TVIH	The Viking Horde	11	https://crest-tq.eveonline.com/alliances/1267893400/
1269363671	ARISE	Ares Protectiva	11	https://crest-tq.eveonline.com/alliances/1269363671/
1271973829	TFS	The Final Stand.	11	https://crest-tq.eveonline.com/alliances/1271973829/
1286081470	COTU	Coalition of the Unfortunate	11	https://crest-tq.eveonline.com/alliances/1286081470/
1289056941	ALIGN	Unaligned.	11	https://crest-tq.eveonline.com/alliances/1289056941/
1291924757	DEMSA	DemSal Unlimited	11	https://crest-tq.eveonline.com/alliances/1291924757/
1294094452	ATA	Antesignani Alliance	11	https://crest-tq.eveonline.com/alliances/1294094452/
1296022029	QED	Quod Erat Demonstrandum	11	https://crest-tq.eveonline.com/alliances/1296022029/
1301367357	MOA	Mordus Angels	11	https://crest-tq.eveonline.com/alliances/1301367357/
1307452790	M34N	Mean Coalition	11	https://crest-tq.eveonline.com/alliances/1307452790/
1308130007	VAC	Virtual Alliance Core	11	https://crest-tq.eveonline.com/alliances/1308130007/
1310975756	HORNY	TENTIGO	11	https://crest-tq.eveonline.com/alliances/1310975756/
1312931530	VOV	Voice of Void Alliance	11	https://crest-tq.eveonline.com/alliances/1312931530/
1320161290	KTS	Khanid Trade Syndicate	11	https://crest-tq.eveonline.com/alliances/1320161290/
1321638688	NEL	New Eden Legion	11	https://crest-tq.eveonline.com/alliances/1321638688/
1323245561	10TH	Tenth Legion	11	https://crest-tq.eveonline.com/alliances/1323245561/
1324057257	LODI	Lodizal Conglomerate	11	https://crest-tq.eveonline.com/alliances/1324057257/
1325270310	SKY 7	Skynet 7	11	https://crest-tq.eveonline.com/alliances/1325270310/
1326438017	-KRA-	KnightRaven Alliance	11	https://crest-tq.eveonline.com/alliances/1326438017/
1327270923	LAGG	Excuses.	11	https://crest-tq.eveonline.com/alliances/1327270923/
1338932202	-ELT-	Erset La Tari	11	https://crest-tq.eveonline.com/alliances/1338932202/
1347627286	MOUNT	Mountain Sprouts	11	https://crest-tq.eveonline.com/alliances/1347627286/
1348760979	SC	Storm Coalition	12	https://crest-tq.eveonline.com/alliances/1348760979/
1350079892	OFF	ONLY FOR FUN	12	https://crest-tq.eveonline.com/alliances/1350079892/
1354830081	CONDI	Goonswarm Federation	12	https://crest-tq.eveonline.com/alliances/1354830081/
1360444521	VSO	Violent Society	12	https://crest-tq.eveonline.com/alliances/1360444521/
1366128581	TLB	The Last Brigade	12	https://crest-tq.eveonline.com/alliances/1366128581/
1368013332	CMS17	CMS-17 Expanse	12	https://crest-tq.eveonline.com/alliances/1368013332/
1370608164	HAH	Habitat Against Humanity	12	https://crest-tq.eveonline.com/alliances/1370608164/
1373411281	V-G	Voodoo Groove	12	https://crest-tq.eveonline.com/alliances/1373411281/
1377988574	KNOW	U N K N O W N	12	https://crest-tq.eveonline.com/alliances/1377988574/
1385163836	FUS	Free United Spirits	12	https://crest-tq.eveonline.com/alliances/1385163836/
1390853747	R3LM	Rogue Elements.	12	https://crest-tq.eveonline.com/alliances/1390853747/
1393073050	JE	Jovian Enterprises	12	https://crest-tq.eveonline.com/alliances/1393073050/
1394403348	ARTE	ARTESANOS	12	https://crest-tq.eveonline.com/alliances/1394403348/
1399057309	RAX	HUN Reloaded	12	https://crest-tq.eveonline.com/alliances/1399057309/
1401461156	BSI	B.S.I.	12	https://crest-tq.eveonline.com/alliances/1401461156/
1406015925	FOD	Flame of Destiny	12	https://crest-tq.eveonline.com/alliances/1406015925/
1409089077	HARK	Rooks and Kings	12	https://crest-tq.eveonline.com/alliances/1409089077/
1411711376	X.I.X	Legion of xXDEATHXx	12	https://crest-tq.eveonline.com/alliances/1411711376/
1420380335	D.S.E	Dark Solar Empire	12	https://crest-tq.eveonline.com/alliances/1420380335/
1424550893	URD	United Royal Dreams	12	https://crest-tq.eveonline.com/alliances/1424550893/
1433386454	CHAIN	Chained Reactions	12	https://crest-tq.eveonline.com/alliances/1433386454/
1433868460	CFS	Cold Fusion Syndicate	12	https://crest-tq.eveonline.com/alliances/1433868460/
1436717773	GOLD	Gold Star Alliance	12	https://crest-tq.eveonline.com/alliances/1436717773/
1438160193	EV0KE	Ev0ke	12	https://crest-tq.eveonline.com/alliances/1438160193/
1439416485	-SF-	The Star Fraction	12	https://crest-tq.eveonline.com/alliances/1439416485/
1441609042	OCP	Omega Consortium Projects	12	https://crest-tq.eveonline.com/alliances/1441609042/
1443952531	THF	THE FEW.	12	https://crest-tq.eveonline.com/alliances/1443952531/
1445032335	EVENT	Event Horizon Ergonomics	12	https://crest-tq.eveonline.com/alliances/1445032335/
1445778095	- N -	The Nephilim.	12	https://crest-tq.eveonline.com/alliances/1445778095/
1449737488	CTA	Comatose Alliance	12	https://crest-tq.eveonline.com/alliances/1449737488/
1453325109	FLAME	Flame Bridge	12	https://crest-tq.eveonline.com/alliances/1453325109/
1457577457	WOLF	Tokra Wolf	12	https://crest-tq.eveonline.com/alliances/1457577457/
1459096720	BOSS.	Brotherhood Of Silent Space	12	https://crest-tq.eveonline.com/alliances/1459096720/
1459132996	NOISE	Primal Noise	12	https://crest-tq.eveonline.com/alliances/1459132996/
1460367601	CRUEL	Cruel Intentions	12	https://crest-tq.eveonline.com/alliances/1460367601/
1463354890	S2N	Nulli Secunda	12	https://crest-tq.eveonline.com/alliances/1463354890/
1463419562	LEGIO	LEGIO ASTARTES ARCANUM	12	https://crest-tq.eveonline.com/alliances/1463419562/
1466346044	RISE	Rising Darkness	12	https://crest-tq.eveonline.com/alliances/1466346044/
1467181086	SSR	Second Sun Rising	12	https://crest-tq.eveonline.com/alliances/1467181086/
1468580260	AOA	Army Of Angels Alliance	12	https://crest-tq.eveonline.com/alliances/1468580260/
1470696988	NER	New Eden Research.	12	https://crest-tq.eveonline.com/alliances/1470696988/
1473825497	FTL	Fiat Lux.	12	https://crest-tq.eveonline.com/alliances/1473825497/
1475695446	-X-	The Last Chancers.	12	https://crest-tq.eveonline.com/alliances/1475695446/
1478762529	ENC	Enclave Alliance	12	https://crest-tq.eveonline.com/alliances/1478762529/
1484269300	-INC-	Interstellar Confederation	12	https://crest-tq.eveonline.com/alliances/1484269300/
1485500044	SWORD	Knights Collective	12	https://crest-tq.eveonline.com/alliances/1485500044/
1486022755	507	Soldiers of Thunderstorm	12	https://crest-tq.eveonline.com/alliances/1486022755/
1486306340	V8	Prestige Mode	12	https://crest-tq.eveonline.com/alliances/1486306340/
1489047551	POD	Preachers Of Dream	12	https://crest-tq.eveonline.com/alliances/1489047551/
1489655626	FR33	FREELANCER ALLIANCE	12	https://crest-tq.eveonline.com/alliances/1489655626/
1490024253	TAS	The Gurlstas Associates	12	https://crest-tq.eveonline.com/alliances/1490024253/
1491607438	BLOW	Blow Alliance	12	https://crest-tq.eveonline.com/alliances/1491607438/
1495222116	MTC	MinTek Conglomerate	12	https://crest-tq.eveonline.com/alliances/1495222116/
1496500070	FROG	Red-Frog	12	https://crest-tq.eveonline.com/alliances/1496500070/
1498078438	-SEA-	Subspace Exploration Agency	12	https://crest-tq.eveonline.com/alliances/1498078438/
1498727871	SOTEK	Sovereign Technologies	12	https://crest-tq.eveonline.com/alliances/1498727871/
1501613107	RC	Roamer Coalition	12	https://crest-tq.eveonline.com/alliances/1501613107/
1502920967	V3X	Vort3x.	12	https://crest-tq.eveonline.com/alliances/1502920967/
1508324559	PR	Dark Pride Alliance	12	https://crest-tq.eveonline.com/alliances/1508324559/
1510264505	-GRA-	Green Robe Alliance	12	https://crest-tq.eveonline.com/alliances/1510264505/
1511269779	MOASB	My other alliance sucks balls	12	https://crest-tq.eveonline.com/alliances/1511269779/
1513690947	CONQ	CONQUISTADORS	12	https://crest-tq.eveonline.com/alliances/1513690947/
1515270678	- S -	Supremacy.	12	https://crest-tq.eveonline.com/alliances/1515270678/
1517125351	RK-A	Raikiri Assasins	12	https://crest-tq.eveonline.com/alliances/1517125351/
1518185742	VOID	VOID Intergalactic Forces	12	https://crest-tq.eveonline.com/alliances/1518185742/
1522788853	ENGRE	En Garde	12	https://crest-tq.eveonline.com/alliances/1522788853/
1522842604	IMP-L	IMPERIAL LEGI0N	12	https://crest-tq.eveonline.com/alliances/1522842604/
1530983307	-DA-	Desman Alliance	12	https://crest-tq.eveonline.com/alliances/1530983307/
1534445160	NI4NI	AN EYE F0R AN EYE	12	https://crest-tq.eveonline.com/alliances/1534445160/
1537406862	IMEVE	Death from Above..	12	https://crest-tq.eveonline.com/alliances/1537406862/
1538108032	MLTOV	Molotov Coalition	12	https://crest-tq.eveonline.com/alliances/1538108032/
1538382401	IGB	Intergalactic Brotherhood	12	https://crest-tq.eveonline.com/alliances/1538382401/
1538759362	A.N.P	Alliance. Now. Please.	12	https://crest-tq.eveonline.com/alliances/1538759362/
1547299718	WTF	Controlled Chaos	12	https://crest-tq.eveonline.com/alliances/1547299718/
1548582972	MC	Mercenary Coalition	12	https://crest-tq.eveonline.com/alliances/1548582972/
1550724751	NEX	Nex Eternus	12	https://crest-tq.eveonline.com/alliances/1550724751/
1551928834	.BGT.	Fear My Baguette	12	https://crest-tq.eveonline.com/alliances/1551928834/
1555742183	NSPYS	Here Be Dragons	12	https://crest-tq.eveonline.com/alliances/1555742183/
1556969942	RAPAX	Rapax Hispaniae	12	https://crest-tq.eveonline.com/alliances/1556969942/
1558000341	DKHLE	Despoilment Alliance	12	https://crest-tq.eveonline.com/alliances/1558000341/
1559868580	EHHHH	The Fonz Presidium	12	https://crest-tq.eveonline.com/alliances/1559868580/
1562449472	VVVVV	Vi Veri Veniversum Vivus Vici	12	https://crest-tq.eveonline.com/alliances/1562449472/
1566707582	RE-AL	Republic Alliance	12	https://crest-tq.eveonline.com/alliances/1566707582/
1567141329	TLEAF	In Tea We Trust	12	https://crest-tq.eveonline.com/alliances/1567141329/
1568020072	CR1ME	CORPVS DELICTI	12	https://crest-tq.eveonline.com/alliances/1568020072/
1570418183	YU-ME	Banji wa Yume	12	https://crest-tq.eveonline.com/alliances/1570418183/
1573091213	UR	Unknown Reality	12	https://crest-tq.eveonline.com/alliances/1573091213/
1577922045	BDEAL	Important Internet Spaceship League	12	https://crest-tq.eveonline.com/alliances/1577922045/
1579310055	STRAT	Stratagem.	12	https://crest-tq.eveonline.com/alliances/1579310055/
1590772691	SCNR	Scheinfirma Inc	12	https://crest-tq.eveonline.com/alliances/1590772691/
1592222898	FWA	Free Worlds Alliance	12	https://crest-tq.eveonline.com/alliances/1592222898/
1597935035	SYS	System Alliance	12	https://crest-tq.eveonline.com/alliances/1597935035/
1603255562	ALLR	All Rights Reserved	12	https://crest-tq.eveonline.com/alliances/1603255562/
1603281980	FCKU	Anarchy.	12	https://crest-tq.eveonline.com/alliances/1603281980/
1606329389	.STAR	Brotherhood of Starbridge	12	https://crest-tq.eveonline.com/alliances/1606329389/
1609313558	HT	Hansa Teutonica	12	https://crest-tq.eveonline.com/alliances/1609313558/
1609780791	SAS	Saints Amongst Sinners	12	https://crest-tq.eveonline.com/alliances/1609780791/
1610529782	IPR	Imperial Research	12	https://crest-tq.eveonline.com/alliances/1610529782/
1613946799	IE	Intrepid Explorers	12	https://crest-tq.eveonline.com/alliances/1613946799/
1614483120	BSOD	Bright Side of Death	12	https://crest-tq.eveonline.com/alliances/1614483120/
1621208577	SRA	Shadow Rock Alliance	12	https://crest-tq.eveonline.com/alliances/1621208577/
1622450748	RED10	Negative Ten.	12	https://crest-tq.eveonline.com/alliances/1622450748/
1636356151	BLU	Blue Meanies	12	https://crest-tq.eveonline.com/alliances/1636356151/
1641039452	BRIG	Brig Consortium	12	https://crest-tq.eveonline.com/alliances/1641039452/
1641160483	SESO	Serenitas Solutus	12	https://crest-tq.eveonline.com/alliances/1641160483/
1644918530	-BCA-	Broken Chains Alliance	12	https://crest-tq.eveonline.com/alliances/1644918530/
1645234854	BTA	Black Thorne Alliance	12	https://crest-tq.eveonline.com/alliances/1645234854/
1645363239	-DNS-	Dirt Nap Squad.	12	https://crest-tq.eveonline.com/alliances/1645363239/
1652187896	AERO	Aerodyne Collective	12	https://crest-tq.eveonline.com/alliances/1652187896/
1652806367	WA	W.A.S.T.Y.A.	12	https://crest-tq.eveonline.com/alliances/1652806367/
1653205733	ROS	Rise of Sun	12	https://crest-tq.eveonline.com/alliances/1653205733/
1657542160	GRA	Galactic Republic Alliance	12	https://crest-tq.eveonline.com/alliances/1657542160/
1658478828	AC	Allied Resources Coalition	12	https://crest-tq.eveonline.com/alliances/1658478828/
1660676012	XSM	Excessum Gaming	12	https://crest-tq.eveonline.com/alliances/1660676012/
1660897578	CS	Copius Spectrum	12	https://crest-tq.eveonline.com/alliances/1660897578/
1662494689	INDY	Independent Faction	12	https://crest-tq.eveonline.com/alliances/1662494689/
1667318335	IDLE	IDLE EMPIRE	12	https://crest-tq.eveonline.com/alliances/1667318335/
1675006617	SINGE	Dystopia Alliance	12	https://crest-tq.eveonline.com/alliances/1675006617/
1675661691	-QUL-	Quebec United Legions	12	https://crest-tq.eveonline.com/alliances/1675661691/
1677336111	REAL.	Revenant Alliance	12	https://crest-tq.eveonline.com/alliances/1677336111/
1678085250	JAB	Joint Alliance Blue	12	https://crest-tq.eveonline.com/alliances/1678085250/
1678330850	SYND	Syndicate.	12	https://crest-tq.eveonline.com/alliances/1678330850/
1680888152	MYM8	The Marmite Collective	12	https://crest-tq.eveonline.com/alliances/1680888152/
1681527727	THC	The Craftsmen	12	https://crest-tq.eveonline.com/alliances/1681527727/
1682474357	TUBES	Twisted Tubes	12	https://crest-tq.eveonline.com/alliances/1682474357/
1683694957	-C.B-	COASTAL BROTHERHOOD	12	https://crest-tq.eveonline.com/alliances/1683694957/
1690442742	PRIME	P R I M E	12	https://crest-tq.eveonline.com/alliances/1690442742/
1691647675	I-RED	Ishuk-Raata Enforcement Directive	12	https://crest-tq.eveonline.com/alliances/1691647675/
1692501429	VATS	Van Terra Systems	12	https://crest-tq.eveonline.com/alliances/1692501429/
1695357456	CO2	Circle-Of-Two	12	https://crest-tq.eveonline.com/alliances/1695357456/
1698393001	E	Exiliar Syndicate	12	https://crest-tq.eveonline.com/alliances/1698393001/
1698638724	-SRA-	SUB ROSA ALLIANCE	12	https://crest-tq.eveonline.com/alliances/1698638724/
1698811717	FIX	Firmus Ixion	12	https://crest-tq.eveonline.com/alliances/1698811717/
1699714763	TSOEC	TSOE Consortium	12	https://crest-tq.eveonline.com/alliances/1699714763/
1700335011	FFG	Fall From Grace.	12	https://crest-tq.eveonline.com/alliances/1700335011/
1700517403	SILNT	S I L E N T.	12	https://crest-tq.eveonline.com/alliances/1700517403/
1701357977	AAA	AquariusArt Alliance	12	https://crest-tq.eveonline.com/alliances/1701357977/
1705810521	WERES	Werewolves.	12	https://crest-tq.eveonline.com/alliances/1705810521/
1708058043	IIA	Infinite Improbability Alliance	12	https://crest-tq.eveonline.com/alliances/1708058043/
1709829868	OC	Outlaw Coalition	12	https://crest-tq.eveonline.com/alliances/1709829868/
1711636893	STENT	Silver Twilight Enterprises	12	https://crest-tq.eveonline.com/alliances/1711636893/
1712346130	MYST	Mystic Dawn Alliance	12	https://crest-tq.eveonline.com/alliances/1712346130/
1714920227	EVF	EVE Federation	12	https://crest-tq.eveonline.com/alliances/1714920227/
1717045815	TWC	The War Consultants	12	https://crest-tq.eveonline.com/alliances/1717045815/
1720765895	PMA	Pacific Mining Alliance	12	https://crest-tq.eveonline.com/alliances/1720765895/
1723603722	AR	Alternative Realities	12	https://crest-tq.eveonline.com/alliances/1723603722/
1724591490	BHH	Black Hole Horizon	12	https://crest-tq.eveonline.com/alliances/1724591490/
1726398924	OCPS	Overclockers Podpilot Services	12	https://crest-tq.eveonline.com/alliances/1726398924/
1727758877	NC	Northern Coalition.	12	https://crest-tq.eveonline.com/alliances/1727758877/
1728396456	IKILU	Dead Terrorists	12	https://crest-tq.eveonline.com/alliances/1728396456/
1733643802	-42-	-Mostly Harmless-	12	https://crest-tq.eveonline.com/alliances/1733643802/
1742306959	O O E	ORPHANS OF EVE	12	https://crest-tq.eveonline.com/alliances/1742306959/
1744471691	-OOK-	The Funky Gibbon	12	https://crest-tq.eveonline.com/alliances/1744471691/
1744996824	SIRE	Silent Requiem	12	https://crest-tq.eveonline.com/alliances/1744996824/
1747592302	E V E	A T L A N T I S	12	https://crest-tq.eveonline.com/alliances/1747592302/
1752392266	OINK	WILD BOARS	12	https://crest-tq.eveonline.com/alliances/1752392266/
1767629342	D-FED	Dragoon Federation	12	https://crest-tq.eveonline.com/alliances/1767629342/
1768037588	NITRO	Cosmic Legion of Interstellar Travelers	12	https://crest-tq.eveonline.com/alliances/1768037588/
1778087706	AESIR	Aesir Empire	12	https://crest-tq.eveonline.com/alliances/1778087706/
1778325832	-SS-	Scorned Syndicate	12	https://crest-tq.eveonline.com/alliances/1778325832/
1781746572	4EVER	Forever Unbound	12	https://crest-tq.eveonline.com/alliances/1781746572/
1782806476	TCN	The Colbert Nation	12	https://crest-tq.eveonline.com/alliances/1782806476/
1782854772	SFCMD	StarFleet-Command	12	https://crest-tq.eveonline.com/alliances/1782854772/
1783407081	HOME	H.O.M.E. Coalition	12	https://crest-tq.eveonline.com/alliances/1783407081/
1785470636	AWFUL	Indecisive Certainty	12	https://crest-tq.eveonline.com/alliances/1785470636/
1789460651	C.F	Care Factor	12	https://crest-tq.eveonline.com/alliances/1789460651/
1793718161	TWV	TransWarp Ventures	12	https://crest-tq.eveonline.com/alliances/1793718161/
1794112889	-PH	Acid.	12	https://crest-tq.eveonline.com/alliances/1794112889/
1796417614	GLBL	Universal Domination Inc	12	https://crest-tq.eveonline.com/alliances/1796417614/
1803104011	UDEAD	Deadly Unknown	12	https://crest-tq.eveonline.com/alliances/1803104011/
1803519277	TUF	TalCorp United Federation	12	https://crest-tq.eveonline.com/alliances/1803519277/
1819478397	-EM0-	Electric Monkey Overlords	12	https://crest-tq.eveonline.com/alliances/1819478397/
1827461711	MODE	Mortal Destruction	12	https://crest-tq.eveonline.com/alliances/1827461711/
1835092947	ISKVI	ISK Six	12	https://crest-tq.eveonline.com/alliances/1835092947/
1838908040	FAIL	Cascade Imminent	12	https://crest-tq.eveonline.com/alliances/1838908040/
1839636692	MAD.S	Mad Scientists	12	https://crest-tq.eveonline.com/alliances/1839636692/
1841150447	V3	Veni Vidi Vici Alliance	12	https://crest-tq.eveonline.com/alliances/1841150447/
1843671294	C-EVO	Crimson Evolution	12	https://crest-tq.eveonline.com/alliances/1843671294/
1845350470	10L	X-Legio	12	https://crest-tq.eveonline.com/alliances/1845350470/
1850327045	CC	Crazy Chicks	12	https://crest-tq.eveonline.com/alliances/1850327045/
1851228421	BOAT	Brotherhood of Armed Traders	12	https://crest-tq.eveonline.com/alliances/1851228421/
1857454335	-B5-	The Babylon Consortium	12	https://crest-tq.eveonline.com/alliances/1857454335/
1858184168	5TH.	Fifth Freedom	12	https://crest-tq.eveonline.com/alliances/1858184168/
1862187089	.BTD.	Frater Adhuc Excessum	12	https://crest-tq.eveonline.com/alliances/1862187089/
1867653847	I-BRA	Inver Brass	12	https://crest-tq.eveonline.com/alliances/1867653847/
1868504543	F2B	Fade 2 Black	12	https://crest-tq.eveonline.com/alliances/1868504543/
1869281246	-TSI-	Trans-Stellar Industries	12	https://crest-tq.eveonline.com/alliances/1869281246/
1872065019	SOLO	Wayward Alliance	12	https://crest-tq.eveonline.com/alliances/1872065019/
1873258577	DAM	Dominatus Atrum Mortis	12	https://crest-tq.eveonline.com/alliances/1873258577/
1873372909	IRKN	Mighty Irken Empire Alliance	12	https://crest-tq.eveonline.com/alliances/1873372909/
1874081310	RED.C	RED Citizens	12	https://crest-tq.eveonline.com/alliances/1874081310/
1874353004	ICSA	Imperial Coalition of Space Agencies	12	https://crest-tq.eveonline.com/alliances/1874353004/
1875292563	BEEP	The Honda Accord	12	https://crest-tq.eveonline.com/alliances/1875292563/
1880001159	SCUM	SCUM.	12	https://crest-tq.eveonline.com/alliances/1880001159/
1884239677	SMASH	Violent Intent	12	https://crest-tq.eveonline.com/alliances/1884239677/
1884595530	OI	Ocularis Inferno	12	https://crest-tq.eveonline.com/alliances/1884595530/
1889367927	.DARK	D.A.R.K.	12	https://crest-tq.eveonline.com/alliances/1889367927/
1890405927	FRA	French Alliance	12	https://crest-tq.eveonline.com/alliances/1890405927/
1900696668	INIT.	The Initiative.	12	https://crest-tq.eveonline.com/alliances/1900696668/
1903500835	QGEN	Quasar Generation	12	https://crest-tq.eveonline.com/alliances/1903500835/
1904571621	STURM	STURM ZEIT	12	https://crest-tq.eveonline.com/alliances/1904571621/
1904739487	E C L	E C L I P S E	12	https://crest-tq.eveonline.com/alliances/1904739487/
1905250435	AAA C	AAA Citizens	12	https://crest-tq.eveonline.com/alliances/1905250435/
1907881941	DZULU	Dark Zulutas	12	https://crest-tq.eveonline.com/alliances/1907881941/
1911932230	-IA-	Initiative Associates	12	https://crest-tq.eveonline.com/alliances/1911932230/
1912584866	OBFU	Obsidian Fusion	12	https://crest-tq.eveonline.com/alliances/1912584866/
1923227030	PANIC	Middle of Nowhere	12	https://crest-tq.eveonline.com/alliances/1923227030/
1932631400	LSP	Lone Star Partners	12	https://crest-tq.eveonline.com/alliances/1932631400/
1933159389	LIB	Liberty Alliance	12	https://crest-tq.eveonline.com/alliances/1933159389/
1933319908	TQD	Taurus Quantum Dynamics	12	https://crest-tq.eveonline.com/alliances/1933319908/
1933713482	SKA	Sleepless Knights Alliance	12	https://crest-tq.eveonline.com/alliances/1933713482/
1941616664	TEA	EVE Trade Alliance	12	https://crest-tq.eveonline.com/alliances/1941616664/
1952770063	SDRR	Sex Drugs And Rock'N'Roll	12	https://crest-tq.eveonline.com/alliances/1952770063/
1958196311	ARC	Arcane Alliance	12	https://crest-tq.eveonline.com/alliances/1958196311/
1962631059	HUGME	Necrophiliacs	12	https://crest-tq.eveonline.com/alliances/1962631059/
1964395545	CAPS	Capital Storm	12	https://crest-tq.eveonline.com/alliances/1964395545/
1966049571	UNITY	Ushra'Khan	12	https://crest-tq.eveonline.com/alliances/1966049571/
1966919795	UNI.T	United Nations Intelligence Taskforces	12	https://crest-tq.eveonline.com/alliances/1966919795/
1975277995	TOHA	TOHA Conglomerate	12	https://crest-tq.eveonline.com/alliances/1975277995/
1983809465	PSY	Paisti Syndicate	12	https://crest-tq.eveonline.com/alliances/1983809465/
1985435220	BABY5	Station Babylon 5	12	https://crest-tq.eveonline.com/alliances/1985435220/
1987964414	DOT	Dawn of Transcendence	12	https://crest-tq.eveonline.com/alliances/1987964414/
1988009450	SE	Stain Empire	12	https://crest-tq.eveonline.com/alliances/1988009450/
1988009451	CVA	Curatores Veritatis Alliance	12	https://crest-tq.eveonline.com/alliances/1988009451/
1989262607	HRR	The Polaris Syndicate	12	https://crest-tq.eveonline.com/alliances/1989262607/
1992483819	SEE	Superior Eve Engineering	12	https://crest-tq.eveonline.com/alliances/1992483819/
2004363804	FISH	Fisherman's Friend's	12	https://crest-tq.eveonline.com/alliances/2004363804/
2008100782	BATH	Blood Alliance	12	https://crest-tq.eveonline.com/alliances/2008100782/
2008665267	.NASA	N.A.S.A	12	https://crest-tq.eveonline.com/alliances/2008665267/
2016585042	VAHA	THOR ALLIANCE	12	https://crest-tq.eveonline.com/alliances/2016585042/
2018896300	TAZ	Tinker's and Zed's Alliance	12	https://crest-tq.eveonline.com/alliances/2018896300/
2029809708	UMB	Umbrella Chemical Inc	12	https://crest-tq.eveonline.com/alliances/2029809708/
2031941852	IAC	Interstellar Alcohol Conglomerate	12	https://crest-tq.eveonline.com/alliances/2031941852/
2040806967	K-FED	KRAUTZ-FEDERATION	12	https://crest-tq.eveonline.com/alliances/2040806967/
2041275681	A.C.	Aeternum Cohors	12	https://crest-tq.eveonline.com/alliances/2041275681/
2041309524	JOMO	Burning Spear.	12	https://crest-tq.eveonline.com/alliances/2041309524/
2049763943	ENTRO	EntroPraetorian Aegis	12	https://crest-tq.eveonline.com/alliances/2049763943/
2057458625	JIHAD	JIHADASQUAD	12	https://crest-tq.eveonline.com/alliances/2057458625/
2077084692	OORT	The OORT Cloud	12	https://crest-tq.eveonline.com/alliances/2077084692/
\.


--
-- TOC entry 2154 (class 0 OID 16635)
-- Dependencies: 189
-- Data for Name: alliances; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY alliances ("totalAlliances", pagecount, countperpage) FROM stdin;
2989	12	250
\.


--
-- TOC entry 2150 (class 0 OID 16443)
-- Dependencies: 185
-- Data for Name: capsuleer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY capsuleer (capsuleerpk, capsuleer, capsuleerid, apikeyid, apicode, refreshtoken) FROM stdin;
163	216	95217276	-1	\N	5oabFDoPcIGuGXF87Ee0QChl5xUEbItyLt9Q3HUtwS2CUSsvx9OIy53Pn10paWSR0
164	217	93982333	5520467	2IyVcmIAxdJgQYP5LMLTTP9Uf8lVHXh6MNju7NCBbSavaSVnQOsX3zqTUuWYRWQ6	9t63pXC88RrIvgBkH9FB-xwUfkMeINcMXQ3bJnPe6H26BNKhSbjEADmbkPe2jl7Z0
165	212	1364371482	-1	\N	Ssxas79LwmRJIn8W54q1mCPDjr-k8TX4BHfn_FF7S-Pq6wMPAVZqU098yQuE6vyZ0
\.


--
-- TOC entry 2173 (class 0 OID 0)
-- Dependencies: 184
-- Name: capsuleer_capsuleerpk_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('capsuleer_capsuleerpk_seq', 166, true);


--
-- TOC entry 2147 (class 0 OID 16414)
-- Dependencies: 182
-- Data for Name: entity; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY entity (entitypk, name, isgroup) FROM stdin;
212	Salgare	f
213	anonymous	t
214	user	t
215	directors	t
216	Hazel Grayson	f
217	osgi	f
\.


--
-- TOC entry 2174 (class 0 OID 0)
-- Dependencies: 181
-- Name: entity_entityPk_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"entity_entityPk_seq"', 217, true);


--
-- TOC entry 2152 (class 0 OID 16591)
-- Dependencies: 187
-- Data for Name: sharedrights; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY sharedrights (rightpk, capsuleer1, capsuleer2, type) FROM stdin;
\.


--
-- TOC entry 2175 (class 0 OID 0)
-- Dependencies: 186
-- Name: sharedrights_rightpk_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('sharedrights_rightpk_seq', 1, false);


--
-- TOC entry 2025 (class 2606 OID 16630)
-- Name: alliance_Pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY alliance
    ADD CONSTRAINT "alliance_Pk" PRIMARY KEY (id);


--
-- TOC entry 2018 (class 2606 OID 16448)
-- Name: capsuleer_capsuleerpk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY capsuleer
    ADD CONSTRAINT capsuleer_capsuleerpk PRIMARY KEY (capsuleerpk);


--
-- TOC entry 2010 (class 2606 OID 16419)
-- Name: entity_entitypk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY entity
    ADD CONSTRAINT entity_entitypk PRIMARY KEY (entitypk);


--
-- TOC entry 2013 (class 2606 OID 16466)
-- Name: entity_nameunq; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY entity
    ADD CONSTRAINT entity_nameunq UNIQUE (name);


--
-- TOC entry 2023 (class 2606 OID 16596)
-- Name: sharedrights_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sharedrights
    ADD CONSTRAINT sharedrights_pk PRIMARY KEY (rightpk);


--
-- TOC entry 2011 (class 1259 OID 16455)
-- Name: entity_nameidx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX entity_nameidx ON entity USING btree (name);


--
-- TOC entry 2014 (class 1259 OID 16434)
-- Name: fki_accessgroup_adminfk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_accessgroup_adminfk ON accessgroup USING btree (adminfk);


--
-- TOC entry 2015 (class 1259 OID 16428)
-- Name: fki_accessgroup_groupfk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_accessgroup_groupfk ON accessgroup USING btree (groupfk);


--
-- TOC entry 2016 (class 1259 OID 16440)
-- Name: fki_accessgroup_memberfk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_accessgroup_memberfk ON accessgroup USING btree (memberfk);


--
-- TOC entry 2019 (class 1259 OID 16454)
-- Name: fki_capsuleer_capsuleerfk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_capsuleer_capsuleerfk ON capsuleer USING btree (capsuleer);


--
-- TOC entry 2020 (class 1259 OID 16602)
-- Name: fki_sharedrights_capsuleer1fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_sharedrights_capsuleer1fk ON sharedrights USING btree (capsuleer1);


--
-- TOC entry 2021 (class 1259 OID 16608)
-- Name: fki_sharedrights_capsuleer2fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_sharedrights_capsuleer2fk ON sharedrights USING btree (capsuleer2);


--
-- TOC entry 2027 (class 2606 OID 16429)
-- Name: accessgroup_adminfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY accessgroup
    ADD CONSTRAINT accessgroup_adminfk FOREIGN KEY (adminfk) REFERENCES entity(entitypk);


--
-- TOC entry 2026 (class 2606 OID 16423)
-- Name: accessgroup_groupfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY accessgroup
    ADD CONSTRAINT accessgroup_groupfk FOREIGN KEY (groupfk) REFERENCES entity(entitypk);


--
-- TOC entry 2028 (class 2606 OID 16435)
-- Name: accessgroup_memberfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY accessgroup
    ADD CONSTRAINT accessgroup_memberfk FOREIGN KEY (memberfk) REFERENCES entity(entitypk);


--
-- TOC entry 2029 (class 2606 OID 16449)
-- Name: capsuleer_capsuleerfk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY capsuleer
    ADD CONSTRAINT capsuleer_capsuleerfk FOREIGN KEY (capsuleer) REFERENCES entity(entitypk);


--
-- TOC entry 2030 (class 2606 OID 16597)
-- Name: sharedrights_capsuleer1fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sharedrights
    ADD CONSTRAINT sharedrights_capsuleer1fk FOREIGN KEY (capsuleer1) REFERENCES entity(entitypk);


--
-- TOC entry 2031 (class 2606 OID 16603)
-- Name: sharedrights_capsuleer2fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sharedrights
    ADD CONSTRAINT sharedrights_capsuleer2fk FOREIGN KEY (capsuleer2) REFERENCES entity(entitypk);


--
-- TOC entry 2161 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-09-20 09:26:41

--
-- PostgreSQL database dump complete
--

\connect hello_phoenix_dev

SET default_transaction_read_only = off;

--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.3
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-09-20 09:26:41

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2096 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 2095 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-09-20 09:26:43

--
-- PostgreSQL database dump complete
--

\connect postgres

SET default_transaction_read_only = off;

--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.3
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-09-20 09:26:43

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2095 (class 1262 OID 12373)
-- Dependencies: 2094
-- Name: postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- TOC entry 2 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2098 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 1 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 2099 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


--
-- TOC entry 2097 (class 0 OID 0)
-- Dependencies: 7
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-09-20 09:26:44

--
-- PostgreSQL database dump complete
--

\connect template1

SET default_transaction_read_only = off;

--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.3
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-09-20 09:26:44

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2094 (class 1262 OID 1)
-- Dependencies: 2093
-- Name: template1; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE template1 IS 'default template for new databases';


--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2097 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 2096 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-09-20 09:26:45

--
-- PostgreSQL database dump complete
--

-- Completed on 2016-09-20 09:26:45

--
-- PostgreSQL database cluster dump complete
--

